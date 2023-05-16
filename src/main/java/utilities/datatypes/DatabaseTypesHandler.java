package utilities.datatypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



@SuppressWarnings("ALL")
public class DatabaseTypesHandler {
    private DatabaseTypesHandler(){}

    private static final String integerType = "java.lang.Integer";
    private static final String doubleType = "java.lang.Double";
    private static final String dateType = "java.util.Date";
    private static final String stringType = "java.lang.String";

    private static final Set<String> supportedTypes = new HashSet<>(Arrays.asList(integerType, doubleType, stringType, dateType));
    private static final String sqlINTType = "INT";
    private static final String sqlVARCHARType = "VARCHAR";
    private static final String sqlFLOATType = "FLOAT";
    private static final String sqlDATEType = "DATE";

    public static Set<String> getSupportedTypes() {
        return supportedTypes;
    }
    public static String getIntegerType() {
        return integerType;
    }
    public static String getDoubleType() {
        return doubleType;
    }
    public static String getStringType() {
        return stringType;
    }
    public static String getDateType() {
        return dateType;
    }

    public static String getSqlVarcharType() {return sqlVARCHARType;}

    public static Date getDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean isInteger(String s) {
        try{
            Integer.parseInt(s);
        }catch(Exception e) {
            return false;
        }
        return true;
    }
    private static boolean isDouble(String s) {
       try{
           Double.parseDouble(s);
       }catch (Exception e) {
           return false;
       }
       return true;
    }
    private static boolean isDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
    public static String getType(String value) {
        if(isDate(value)) {
            return dateType;
        }
        if(isInteger(value)) {
            return integerType;
        }
        if(isDouble(value)) {
            return doubleType;
        }
        return stringType;
    }
    public static Comparable getObject(String object, String objectType) {
        if(object.toLowerCase().equals(DBAppNull.getValue())) {
            return new DBAppNull();
        }
        if(objectType.equals(stringType)) {
            return object;
        }
        if(objectType.equals(integerType)) {
            return Integer.parseInt(object);
        }
        if(objectType.equals(doubleType)) {
            return Double.parseDouble(object);
        }
        if(objectType.equals(dateType)) {
            return getDate(object);
        }
        return object;
    }
    public static boolean isCompatibleTypes(String type, String value) {
        if(value.toLowerCase().equals(DBAppNull.getValue())) {
            return true;
        }
        return type.equals(stringType) || type.equals(doubleType) && getType(value).equals(integerType) || type.equals(getType(value));
    }
    public static boolean isCompatibleTypes(String type, Object value) {
        return value instanceof DBAppNull || type.equals(value.getClass().getName());
    }
    private static String getIdIntegerMinValue() {
        return "0";
    }
    private static String getMinimumIntegerValue() {
        return Integer.MIN_VALUE + "";
    }
    private static String getMinimumDoubleValue() {
        return Double.MIN_VALUE + "";
    }
    private static String getMinimumStringValue() {
        return Character.MIN_VALUE + " ";
    }
    private static String getMinimumDateValue() {
        return "1000-01-01";
    }
    private static String getMaximumIntegerValue() {
        return Integer.MAX_VALUE + "";
    }
    private static String getMaximumDoubleValue() {
        return Double.MAX_VALUE + "";
    }
    private static String getMaximumStringValue(int maxLength) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        while(maxLength-- > 0) {
            sb.append(Character.MAX_VALUE);
            sb1.append('z');
        }
        return sb.toString().compareTo(sb1.toString()) < 0? sb1.toString(): sb.toString();
    }
    private static String getMaximumDateValue() {
        Date max = new Date(Long.MAX_VALUE);
        return getDateFormat(max);
    }
    public static String getDateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
    public static String getString(Object obj) {
        if(obj instanceof Date) {
            return getDateFormat((Date)obj);
        }
        return obj.toString();
    }
    public static Bound getBoundDataValues(String type, boolean isPrimaryKey, Integer maxStringLength) {
        switch (type) {
            case sqlINTType -> {
                String max = getMaximumIntegerValue();
                if (isPrimaryKey) {
                    return new Bound(getIdIntegerMinValue(), max);
                }
                return new Bound(getMinimumIntegerValue(), max);
            }
            case sqlFLOATType -> {
                return new Bound(getMinimumDoubleValue(), getMaximumDoubleValue());
            }
            case sqlDATEType -> {
                return new Bound(getMinimumDateValue(), getMaximumDateValue());
            }
            default -> {
                return new Bound(getMinimumStringValue(), getMaximumStringValue(maxStringLength));
            }
        }
    }
    public static String getCorrespondingJavaType(String sqlType) {
        if (sqlType.contains(sqlVARCHARType)) {
            return stringType;
        }
        return switch (sqlType) {
            case sqlINTType -> integerType;
            case sqlFLOATType -> doubleType;
            case sqlDATEType -> dateType;
            default -> null;
        };
    }
    private static String addStringValue(String word, int addedValue) {
        char[] chars = word.toCharArray();
        int i = chars.length - 1;
        chars[i] += addedValue;
        if(addedValue < 0) {
            while(i >= 0 && chars[i] == Character.MIN_VALUE) {
                chars[i] = Character.MAX_VALUE;
                i--;
            }
            if(i == -1) {
                return new String(chars).substring(1);
            }
            chars[i] += addedValue;
        }
        else {
            while(i >= 0 && chars[i] == Character.MAX_VALUE) {
                chars[i] = Character.MIN_VALUE;
                i--;
            }
            if(i == -1) {
                return Character.MIN_VALUE + new String(chars);
            }
            chars[i] += addedValue;
        }
        return new String(chars);
    }
    public static Object addObjectValue(Object value, int addedValue) {
        switch (value.getClass().getName()) {
            case integerType -> {
                return ((int) value) + addedValue;
            }
            case dateType -> {
                Calendar cal = Calendar.getInstance();
                cal.setTime((Date) value);
                cal.add(Calendar.DATE, addedValue);
                return cal.getTime();
            }
            case doubleType -> {
                double smallestFloatingPointDifference = 0.0001;
                return ((double) value) + addedValue * smallestFloatingPointDifference;
            }
            case stringType -> {
                return addStringValue((String)value, addedValue);
            }
        }
        return null;
    }
    private static String getMiddleStringValue(String str1, String str2) {
        int minLength = Math.min(str1.length(), str2.length());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < minLength; i++) {
            char firstChar = str1.charAt(i);
            char secondChar = str2.charAt(i);
            int asciiAverage = (int) ((firstChar + secondChar) / 2);
            char middleChar = (char) asciiAverage;
            sb.append(middleChar);
        }
        if (str1.length() > str2.length()) {
            sb.append(str1.substring(minLength));
        } else if (str2.length() > str1.length()) {
            sb.append(str2.substring(minLength));
        }
        return sb.toString();
    }
    private static Date getMiddleDateValue(Date date1, Date date2) {
        long timestamp1 = date1.getTime();
        long timestamp2 = date2.getTime();
        long avgTimestamp = (timestamp1 + timestamp2) / 2;
        return new Date(avgTimestamp);
    }
    private static int getMiddleIntegerValue(int i1, int i2) {
        return (i1 + i2) / 2;
    }
    private static double getMiddleDoubleValue(double d1, double d2) {
        return (d1 + d2) / 2;
    }
    public static Comparable getMiddleValue(Object obj1, Object obj2) {
        if(obj1 instanceof String) {
            return getMiddleStringValue((String)obj1, (String)obj2);
        }
        if(obj1 instanceof Date) {
            return getMiddleDateValue((Date)obj1, (Date)obj2);
        }
        if(obj1 instanceof Integer) {
            return getMiddleIntegerValue((Integer)obj1, (Integer)obj2);
        }
        if(obj1 instanceof Double) {
            return getMiddleDoubleValue((Double)obj1, (Double)obj2);
        }
        return null;
    }
}

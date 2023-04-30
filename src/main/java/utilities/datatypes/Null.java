package utilities.datatypes;

import java.io.Serializable;

public class Null implements Serializable, Comparable {

    @Override
    public boolean equals(Object obj) {
        return false;
    }
    @Override
    public String toString() {
        return null;
    }

    public static String getValue() {
        return "null";
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

package engine.operations.selection;

import engine.elements.index.IndexMetaInfo;
import engine.exceptions.DBAppException;

import java.util.Hashtable;

@SuppressWarnings("RedundantThrows")
public class SQLTerm {
    public String _strTableName, _strColumnName, _strOperator;
    public Object _objValue;

    public SQLTerm() {

    }

    public boolean isValidSqlTerm() {
        return _strTableName != null &&
                (_strColumnName != null && _strOperator != null && _objValue != null ||
                _strColumnName == null && _strOperator == null && _objValue == null);
    }
    public SQLTerm(String _strTableName, String _strColumnName, String _strOperator, Object _objValue) throws DBAppException {
        this._strTableName = _strTableName;
        this._strColumnName = _strColumnName;
        this._strOperator = _strOperator;
        this._objValue = _objValue;
    }

    boolean isANegationQuery() {
        return _strOperator.equals("!=");
    }

    @Override
    public String toString() {
        return "SQLTerm{" +
                "_strTableName='" + _strTableName + '\'' +
                ", _strColumnName='" + _strColumnName + '\'' +
                ", _strOperator='" + _strOperator + '\'' +
                ", _objValue=" + _objValue +
                '}';
    }
}

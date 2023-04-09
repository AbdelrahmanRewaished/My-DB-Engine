grammar SQL;

query: selectStatement | insertStatement | updateStatement | deleteStatement | createTableStatement ;

selectStatement: SELECT columnList FROM tableList WHERE conditionList | SELECT columnList FROM tableList | SELECT '*' FROM tableList | SELECT '*' FROM tableList WHERE conditionList;

insertStatement: INSERT INTO tableName columnList VALUES valueList ;

updateStatement: UPDATE tableName SET updateList WHERE conditionList | UPDATE tableName SET updateList;

deleteStatement: DELETE FROM tableName WHERE conditionList | DELETE FROM tableName;

createTableStatement: CREATE TABLE tableName '(' columnDefinition (',' columnDefinition)* ')' ;

columnList: columnName (',' columnName)* ;

tableList: tableName (',' tableName)* ;

valueList: value (',' value)* ;

updateList: columnName '=' value (',' columnName '=' value)* ;

conditionList: columnName operator value (logicalOperator conditionList)*;

columnDefinition: columnName dataType ;

dataType: INT | FLOAT | VARCHAR '(' (DIGIT)+ ')' | DATE ;

columnName: (LETTER)+ (DIGIT)*;

tableName: (LETTER)+ (DIGIT)*;

value: (DIGIT)+ | (LETTER)+ | DIGIT DIGIT DIGIT DIGIT'-'DIGIT DIGIT'-'DIGIT DIGIT | (DIGIT)+'.'(DIGIT)+;

operator: '=' | '<' | '>' | '<=' | '>=' ;

logicalOperator: AND | OR | XOR;

SELECT: 'SELECT' ;

INSERT: 'INSERT' ;

INTO: 'INTO' ;

UPDATE: 'UPDATE' ;

SET: 'SET' ;

DELETE: 'DELETE' ;

FROM: 'FROM' ;

VALUES: 'VALUES' ;

AND: 'AND' ;

OR: 'OR';

XOR: 'XOR';

CREATE: 'CREATE' ;

TABLE: 'TABLE' ;

WHERE: 'WHERE';

INT: 'INT';

FLOAT: 'FLOAT';

VARCHAR: 'VARCHAR';

DATE: 'Date';

LETTER: [a-zA-Z] ;

DIGIT: [0-9] ;

WS: [ \t\r\n]+ -> skip ;

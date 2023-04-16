grammar SQL;

query: selectStatement | insertStatement | updateStatement | deleteStatement | createTableStatement ;

selectStatement:  SELECT '*' FROM tableName WHERE conditionList |  SELECT '*' FROM tableList | SELECT '*' FROM tableList WHERE tableReferencedConditionList;

insertStatement: INSERT INTO tableName '('columnList')' VALUES '('valueList')';

updateStatement: UPDATE tableName SET updateList WHERE columnName '=' value | UPDATE tableName SET updateList;

deleteStatement: DELETE FROM tableName WHERE deleteConditionList | DELETE FROM tableName;

createTableStatement: CREATE TABLE tableName '(' columnDefinition PRIMARYKEY (',' columnDefinition)* ')' ;

columnList: columnName (',' columnName)* ;

tableList: tableName tableReference (',' tableName tableReference)+ ;

valueList: value (',' value)* ;

updateList: columnName '=' value (',' columnName '=' value)* ;

conditionList: columnName operator value (logicalOperator conditionList)*;

deleteConditionList: columnName '=' value (logicalOperator deleteConditionList)*;

tableReference: string;

tableReferencedConditionList: tableReference + '.' + columnName operator value (logicalOperator tableReferencedConditionList)*;

columnDefinition: columnName dataType;

PRIMARYKEY: 'PRIMARY KEY';

dataType: INT | FLOAT | VARCHAR '(' integer ')' | DATE ;

columnName: (LETTER)+ (DIGIT)*;

tableName: (LETTER)+ (DIGIT)*;

value: integer | string | date | double;

operator: '=' | '<' | '>' | '<=' | '>=' ;

logicalOperator: AND | OR | XOR;

integer: (DIGIT)+;

double: (DIGIT)+'.'(DIGIT)+;

string: (LETTER)+;

date: DIGIT DIGIT DIGIT DIGIT'-'DIGIT DIGIT'-'DIGIT DIGIT;

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

DATE: 'DATE';

LETTER: [a-zA-Z] ;

DIGIT: [0-9] ;

WS: [ \t\r\n]+ -> skip ;

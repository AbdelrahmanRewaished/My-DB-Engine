grammar SQL;

query: selectStatement | insertStatement | updateStatement | deleteStatement | createTableStatement ;

selectStatement:  SELECT '*' FROM tableName WHERE conditionList |  SELECT '*' FROM tableList | SELECT '*' FROM tableList WHERE tableReferencedConditionList;

insertStatement: INSERT INTO tableName '('columnList')' VALUES '('valueList')';

updateStatement: UPDATE tableName SET updateList WHERE equalityExpression | UPDATE tableName SET updateList;

deleteStatement: DELETE FROM tableName WHERE deleteConditionList | DELETE FROM tableName;

createTableStatement: CREATE TABLE tableName '(' columnDefinition PRIMARYKEY (',' columnDefinition)* ')' ;

columnList: columnName (',' columnName)* ;

tableList: tableName tableReference (',' tableName tableReference)+ ;

valueList: value (',' value)* ;

conditionList: conditionExpression (logicalOperator conditionList)*;

conditionExpression: columnName operator value;

deleteConditionList: equalityExpression (AND deleteConditionList)*;

updateList: equalityExpression (',' updateList)* ;

equalityExpression: columnName '=' value;

tableReference: string;

tableReferencedConditionList: tableReference '.' columnName operator value (logicalOperator tableReferencedConditionList)*;

columnDefinition: columnName dataType | columnName dataType CHECK '(' columnDefinitionConditionList ')';

columnDefinitionConditionList: conditionExpression (AND columnDefinitionConditionList)*;

dataType: INT | FLOAT | VARCHAR '(' integer ')' | DATE ;

word: (LETTER)+ (DIGIT)*;

columnName: word;

tableName: word;

value: integer | string | date | double | NULL;

operator: '=' | '<' | '>' | '<=' | '>=' ;

logicalOperator: AND | OR | XOR;

integer: (DIGIT)+;

double: (DIGIT)+'.'(DIGIT)+;

string : '\'' word '\'';

dateValue: DIGIT DIGIT DIGIT DIGIT'-'DIGIT DIGIT'-'DIGIT DIGIT;

date: '\'' dateValue '\'';

SELECT: 'select' | 'selecT' | 'seleCt' | 'seleCT' | 'selEct' | 'selEcT' | 'selECt' | 'selECT' | 'seLect' | 'seLecT' | 'seLeCt' | 'seLeCT' | 'seLEct' | 'seLEcT' | 'seLECt' | 'seLECT' | 'sElect' | 'sElecT' | 'sEleCt' | 'sEleCT' | 'sElEct' | 'sElEcT' | 'sElECt' | 'sElECT' | 'sELect' | 'sELecT' | 'sELeCt' | 'sELeCT' | 'sELEct' | 'sELEcT' | 'sELECt' | 'sELECT' | 'Select' | 'SelecT' | 'SeleCt' | 'SeleCT' | 'SelEct' | 'SelEcT' | 'SelECt' | 'SelECT' | 'SeLect' | 'SeLecT' | 'SeLeCt' | 'SeLeCT' | 'SeLEct' | 'SeLEcT' | 'SeLECt' | 'SeLECT' | 'SElect' | 'SElecT' | 'SEleCt' | 'SEleCT' | 'SElEct' | 'SElEcT' | 'SElECt' | 'SElECT' | 'SELect' | 'SELecT' | 'SELeCt' | 'SELeCT' | 'SELEct' | 'SELEcT' | 'SELECt' | 'SELECT' ;

INSERT: 'insert' | 'inserT' | 'inseRt' | 'inseRT' | 'insErt' | 'insErT' | 'insERt' | 'insERT' | 'inSert' | 'inSerT' | 'inSeRt' | 'inSeRT' | 'inSErt' | 'inSErT' | 'inSERt' | 'inSERT' | 'iNsert' | 'iNserT' | 'iNseRt' | 'iNseRT' | 'iNsErt' | 'iNsErT' | 'iNsERt' | 'iNsERT' | 'iNSert' | 'iNSerT' | 'iNSeRt' | 'iNSeRT' | 'iNSErt' | 'iNSErT' | 'iNSERt' | 'iNSERT' | 'Insert' | 'InserT' | 'InseRt' | 'InseRT' | 'InsErt' | 'InsErT' | 'InsERt' | 'InsERT' | 'InSert' | 'InSerT' | 'InSeRt' | 'InSeRT' | 'InSErt' | 'InSErT' | 'InSERt' | 'InSERT' | 'INsert' | 'INserT' | 'INseRt' | 'INseRT' | 'INsErt' | 'INsErT' | 'INsERt' | 'INsERT' | 'INSert' | 'INSerT' | 'INSeRt' | 'INSeRT' | 'INSErt' | 'INSErT' | 'INSERt' | 'INSERT' ;

INTO: 'into' | 'intO' | 'inTo' | 'inTO' | 'iNto' | 'iNtO' | 'iNTo' | 'iNTO' | 'Into' | 'IntO' | 'InTo' | 'InTO' | 'INto' | 'INtO' | 'INTo' | 'INTO' ;

UPDATE: 'update' | 'updatE' | 'updaTe' | 'updaTE' | 'updAte' | 'updAtE' | 'updATe' | 'updATE' | 'upDate' | 'upDatE' | 'upDaTe' | 'upDaTE' | 'upDAte' | 'upDAtE' | 'upDATe' | 'upDATE' | 'uPdate' | 'uPdatE' | 'uPdaTe' | 'uPdaTE' | 'uPdAte' | 'uPdAtE' | 'uPdATe' | 'uPdATE' | 'uPDate' | 'uPDatE' | 'uPDaTe' | 'uPDaTE' | 'uPDAte' | 'uPDAtE' | 'uPDATe' | 'uPDATE' | 'Update' | 'UpdatE' | 'UpdaTe' | 'UpdaTE' | 'UpdAte' | 'UpdAtE' | 'UpdATe' | 'UpdATE' | 'UpDate' | 'UpDatE' | 'UpDaTe' | 'UpDaTE' | 'UpDAte' | 'UpDAtE' | 'UpDATe' | 'UpDATE' | 'UPdate' | 'UPdatE' | 'UPdaTe' | 'UPdaTE' | 'UPdAte' | 'UPdAtE' | 'UPdATe' | 'UPdATE' | 'UPDate' | 'UPDatE' | 'UPDaTe' | 'UPDaTE' | 'UPDAte' | 'UPDAtE' | 'UPDATe' | 'UPDATE' ;

SET: 'set' | 'seT' | 'sEt' | 'sET' | 'Set' | 'SeT' | 'SEt' | 'SET' ;

DELETE: 'delete' | 'deletE' | 'deleTe' | 'deleTE' | 'delEte' | 'delEtE' | 'delETe' | 'delETE' | 'deLete' | 'deLetE' | 'deLeTe' | 'deLeTE' | 'deLEte' | 'deLEtE' | 'deLETe' | 'deLETE' | 'dElete' | 'dEletE' | 'dEleTe' | 'dEleTE' | 'dElEte' | 'dElEtE' | 'dElETe' | 'dElETE' | 'dELete' | 'dELetE' | 'dELeTe' | 'dELeTE' | 'dELEte' | 'dELEtE' | 'dELETe' | 'dELETE' | 'Delete' | 'DeletE' | 'DeleTe' | 'DeleTE' | 'DelEte' | 'DelEtE' | 'DelETe' | 'DelETE' | 'DeLete' | 'DeLetE' | 'DeLeTe' | 'DeLeTE' | 'DeLEte' | 'DeLEtE' | 'DeLETe' | 'DeLETE' | 'DElete' | 'DEletE' | 'DEleTe' | 'DEleTE' | 'DElEte' | 'DElEtE' | 'DElETe' | 'DElETE' | 'DELete' | 'DELetE' | 'DELeTe' | 'DELeTE' | 'DELEte' | 'DELEtE' | 'DELETe' | 'DELETE' ;

FROM: 'from' | 'froM' | 'frOm' | 'frOM' | 'fRom' | 'fRoM' | 'fROm' | 'fROM' | 'From' | 'FroM' | 'FrOm' | 'FrOM' | 'FRom' | 'FRoM' | 'FROm' | 'FROM' ;

VALUES: 'values' | 'valueS' | 'valuEs' | 'valuES' | 'valUes' | 'valUeS' | 'valUEs' | 'valUES' | 'vaLues' | 'vaLueS' | 'vaLuEs' | 'vaLuES' | 'vaLUes' | 'vaLUeS' | 'vaLUEs' | 'vaLUES' | 'vAlues' | 'vAlueS' | 'vAluEs' | 'vAluES' | 'vAlUes' | 'vAlUeS' | 'vAlUEs' | 'vAlUES' | 'vALues' | 'vALueS' | 'vALuEs' | 'vALuES' | 'vALUes' | 'vALUeS' | 'vALUEs' | 'vALUES' | 'Values' | 'ValueS' | 'ValuEs' | 'ValuES' | 'ValUes' | 'ValUeS' | 'ValUEs' | 'ValUES' | 'VaLues' | 'VaLueS' | 'VaLuEs' | 'VaLuES' | 'VaLUes' | 'VaLUeS' | 'VaLUEs' | 'VaLUES' | 'VAlues' | 'VAlueS' | 'VAluEs' | 'VAluES' | 'VAlUes' | 'VAlUeS' | 'VAlUEs' | 'VAlUES' | 'VALues' | 'VALueS' | 'VALuEs' | 'VALuES' | 'VALUes' | 'VALUeS' | 'VALUEs' | 'VALUES' ;

AND: 'and' | 'anD' | 'aNd' | 'aND' | 'And' | 'AnD' | 'ANd' | 'AND' ;

OR: 'or' | 'oR' | 'Or' | 'OR';

XOR: 'xor' | 'xoR' | 'xOr' | 'xOR' | 'Xor' | 'XoR' | 'XOr' | 'XOR';

CREATE: 'create' | 'creatE' | 'creaTe' | 'creaTE' | 'creAte' | 'creAtE' | 'creATe' | 'creATE' | 'crEate' | 'crEatE' | 'crEaTe' | 'crEaTE' | 'crEAte' | 'crEAtE' | 'crEATe' | 'crEATE' | 'cReate' | 'cReatE' | 'cReaTe' | 'cReaTE' | 'cReAte' | 'cReAtE' | 'cReATe' | 'cReATE' | 'cREate' | 'cREatE' | 'cREaTe' | 'cREaTE' | 'cREAte' | 'cREAtE' | 'cREATe' | 'cREATE' | 'Create' | 'CreatE' | 'CreaTe' | 'CreaTE' | 'CreAte' | 'CreAtE' | 'CreATe' | 'CreATE' | 'CrEate' | 'CrEatE' | 'CrEaTe' | 'CrEaTE' | 'CrEAte' | 'CrEAtE' | 'CrEATe' | 'CrEATE' | 'CReate' | 'CReatE' | 'CReaTe' | 'CReaTE' | 'CReAte' | 'CReAtE' | 'CReATe' | 'CReATE' | 'CREate' | 'CREatE' | 'CREaTe' | 'CREaTE' | 'CREAte' | 'CREAtE' | 'CREATe' | 'CREATE';

TABLE: 'table' | 'tablE' | 'tabLe' | 'tabLE' | 'taBle' | 'taBlE' | 'taBLe' | 'taBLE' | 'tAble' | 'tAblE' | 'tAbLe' | 'tAbLE' | 'tABle' | 'tABlE' | 'tABLe' | 'tABLE' | 'Table' | 'TablE' | 'TabLe' | 'TabLE' | 'TaBle' | 'TaBlE' | 'TaBLe' | 'TaBLE' | 'TAble' | 'TAblE' | 'TAbLe' | 'TAbLE' | 'TABle' | 'TABlE' | 'TABLe' | 'TABLE' ;

WHERE: 'where' | 'wherE' | 'wheRe' | 'wheRE' | 'whEre' | 'whErE' | 'whERe' | 'whERE' | 'wHere' | 'wHerE' | 'wHeRe' | 'wHeRE' | 'wHEre' | 'wHErE' | 'wHERe' | 'wHERE' | 'Where' | 'WherE' | 'WheRe' | 'WheRE' | 'WhEre' | 'WhErE' | 'WhERe' | 'WhERE' | 'WHere' | 'WHerE' | 'WHeRe' | 'WHeRE' | 'WHEre' | 'WHErE' | 'WHERe' | 'WHERE';

PRIMARYKEY: 'primary key' | 'primary keY' | 'primary kEy' | 'primary kEY' | 'primary Key' | 'primary KeY' | 'primary KEy' | 'primary KEY' | 'primarY key' | 'primarY keY' | 'primarY kEy' | 'primarY kEY' | 'primarY Key' | 'primarY KeY' | 'primarY KEy' | 'primarY KEY' | 'primaRy key' | 'primaRy keY' | 'primaRy kEy' | 'primaRy kEY' | 'primaRy Key' | 'primaRy KeY' | 'primaRy KEy' | 'primaRy KEY' | 'primaRY key' | 'primaRY keY' | 'primaRY kEy' | 'primaRY kEY' | 'primaRY Key' | 'primaRY KeY' | 'primaRY KEy' | 'primaRY KEY' | 'primAry key' | 'primAry keY' | 'primAry kEy' | 'primAry kEY' | 'primAry Key' | 'primAry KeY' | 'primAry KEy' | 'primAry KEY' | 'primArY key' | 'primArY keY' | 'primArY kEy' | 'primArY kEY' | 'primArY Key' | 'primArY KeY' | 'primArY KEy' | 'primArY KEY' | 'primARy key' | 'primARy keY' | 'primARy kEy' | 'primARy kEY' | 'primARy Key' | 'primARy KeY' | 'primARy KEy' | 'primARy KEY' | 'primARY key' | 'primARY keY' | 'primARY kEy' | 'primARY kEY' | 'primARY Key' | 'primARY KeY' | 'primARY KEy' | 'primARY KEY' | 'priMary key' | 'priMary keY' | 'priMary kEy' | 'priMary kEY' | 'priMary Key' | 'priMary KeY' | 'priMary KEy' | 'priMary KEY' | 'priMarY key' | 'priMarY keY' | 'priMarY kEy' | 'priMarY kEY' | 'priMarY Key' | 'priMarY KeY' | 'priMarY KEy' | 'priMarY KEY' | 'priMaRy key' | 'priMaRy keY' | 'priMaRy kEy' | 'priMaRy kEY' | 'priMaRy Key' | 'priMaRy KeY' | 'priMaRy KEy' | 'priMaRy KEY' | 'priMaRY key' | 'priMaRY keY' | 'priMaRY kEy' | 'priMaRY kEY' | 'priMaRY Key' | 'priMaRY KeY' | 'priMaRY KEy' | 'priMaRY KEY' | 'priMAry key' | 'priMAry keY' | 'priMAry kEy' | 'priMAry kEY' | 'priMAry Key' | 'priMAry KeY' | 'priMAry KEy' | 'priMAry KEY' | 'priMArY key' | 'priMArY keY' | 'priMArY kEy' | 'priMArY kEY' | 'priMArY Key' | 'priMArY KeY' | 'priMArY KEy' | 'priMArY KEY' | 'priMARy key' | 'priMARy keY' | 'priMARy kEy' | 'priMARy kEY' | 'priMARy Key' | 'priMARy KeY' | 'priMARy KEy' | 'priMARy KEY' | 'priMARY key' | 'priMARY keY' | 'priMARY kEy' | 'priMARY kEY' | 'priMARY Key' | 'priMARY KeY' | 'priMARY KEy' | 'priMARY KEY' | 'prImary key' | 'prImary keY' | 'prImary kEy' | 'prImary kEY' | 'prImary Key' | 'prImary KeY' | 'prImary KEy' | 'prImary KEY' | 'prImarY key' | 'prImarY keY' | 'prImarY kEy' | 'prImarY kEY' | 'prImarY Key' | 'prImarY KeY' | 'prImarY KEy' | 'prImarY KEY' | 'prImaRy key' | 'prImaRy keY' | 'prImaRy kEy' | 'prImaRy kEY' | 'prImaRy Key' | 'prImaRy KeY' | 'prImaRy KEy' | 'prImaRy KEY' | 'prImaRY key' | 'prImaRY keY' | 'prImaRY kEy' | 'prImaRY kEY' | 'prImaRY Key' | 'prImaRY KeY' | 'prImaRY KEy' | 'prImaRY KEY' | 'prImAry key' | 'prImAry keY' | 'prImAry kEy' | 'prImAry kEY' | 'prImAry Key' | 'prImAry KeY' | 'prImAry KEy' | 'prImAry KEY' | 'prImArY key' | 'prImArY keY' | 'prImArY kEy' | 'prImArY kEY' | 'prImArY Key' | 'prImArY KeY' | 'prImArY KEy' | 'prImArY KEY' | 'prImARy key' | 'prImARy keY' | 'prImARy kEy' | 'prImARy kEY' | 'prImARy Key' | 'prImARy KeY' | 'prImARy KEy' | 'prImARy KEY' | 'prImARY key' | 'prImARY keY' | 'prImARY kEy' | 'prImARY kEY' | 'prImARY Key' | 'prImARY KeY' | 'prImARY KEy' | 'prImARY KEY' | 'prIMary key' | 'prIMary keY' | 'prIMary kEy' | 'prIMary kEY' | 'prIMary Key' | 'prIMary KeY' | 'prIMary KEy' | 'prIMary KEY' | 'prIMarY key' | 'prIMarY keY' | 'prIMarY kEy' | 'prIMarY kEY' | 'prIMarY Key' | 'prIMarY KeY' | 'prIMarY KEy' | 'prIMarY KEY' | 'prIMaRy key' | 'prIMaRy keY' | 'prIMaRy kEy' | 'prIMaRy kEY' | 'prIMaRy Key' | 'prIMaRy KeY' | 'prIMaRy KEy' | 'prIMaRy KEY' | 'prIMaRY key' | 'prIMaRY keY' | 'prIMaRY kEy' | 'prIMaRY kEY' | 'prIMaRY Key' | 'prIMaRY KeY' | 'prIMaRY KEy' | 'prIMaRY KEY' | 'prIMAry key' | 'prIMAry keY' | 'prIMAry kEy' | 'prIMAry kEY' | 'prIMAry Key' | 'prIMAry KeY' | 'prIMAry KEy' | 'prIMAry KEY' | 'prIMArY key' | 'prIMArY keY' | 'prIMArY kEy' | 'prIMArY kEY' | 'prIMArY Key' | 'prIMArY KeY' | 'prIMArY KEy' | 'prIMArY KEY' | 'prIMARy key' | 'prIMARy keY' | 'prIMARy kEy' | 'prIMARy kEY' | 'prIMARy Key' | 'prIMARy KeY' | 'prIMARy KEy' | 'prIMARy KEY' | 'prIMARY key' | 'prIMARY keY' | 'prIMARY kEy' | 'prIMARY kEY' | 'prIMARY Key' | 'prIMARY KeY' | 'prIMARY KEy' | 'prIMARY KEY' | 'pRimary key' | 'pRimary keY' | 'pRimary kEy' | 'pRimary kEY' | 'pRimary Key' | 'pRimary KeY' | 'pRimary KEy' | 'pRimary KEY' | 'pRimarY key' | 'pRimarY keY' | 'pRimarY kEy' | 'pRimarY kEY' | 'pRimarY Key' | 'pRimarY KeY' | 'pRimarY KEy' | 'pRimarY KEY' | 'pRimaRy key' | 'pRimaRy keY' | 'pRimaRy kEy' | 'pRimaRy kEY' | 'pRimaRy Key' | 'pRimaRy KeY' | 'pRimaRy KEy' | 'pRimaRy KEY' | 'pRimaRY key' | 'pRimaRY keY' | 'pRimaRY kEy' | 'pRimaRY kEY' | 'pRimaRY Key' | 'pRimaRY KeY' | 'pRimaRY KEy' | 'pRimaRY KEY' | 'pRimAry key' | 'pRimAry keY' | 'pRimAry kEy' | 'pRimAry kEY' | 'pRimAry Key' | 'pRimAry KeY' | 'pRimAry KEy' | 'pRimAry KEY' | 'pRimArY key' | 'pRimArY keY' | 'pRimArY kEy' | 'pRimArY kEY' | 'pRimArY Key' | 'pRimArY KeY' | 'pRimArY KEy' | 'pRimArY KEY' | 'pRimARy key' | 'pRimARy keY' | 'pRimARy kEy' | 'pRimARy kEY' | 'pRimARy Key' | 'pRimARy KeY' | 'pRimARy KEy' | 'pRimARy KEY' | 'pRimARY key' | 'pRimARY keY' | 'pRimARY kEy' | 'pRimARY kEY' | 'pRimARY Key' | 'pRimARY KeY' | 'pRimARY KEy' | 'pRimARY KEY' | 'pRiMary key' | 'pRiMary keY' | 'pRiMary kEy' | 'pRiMary kEY' | 'pRiMary Key' | 'pRiMary KeY' | 'pRiMary KEy' | 'pRiMary KEY' | 'pRiMarY key' | 'pRiMarY keY' | 'pRiMarY kEy' | 'pRiMarY kEY' | 'pRiMarY Key' | 'pRiMarY KeY' | 'pRiMarY KEy' | 'pRiMarY KEY' | 'pRiMaRy key' | 'pRiMaRy keY' | 'pRiMaRy kEy' | 'pRiMaRy kEY' | 'pRiMaRy Key' | 'pRiMaRy KeY' | 'pRiMaRy KEy' | 'pRiMaRy KEY' | 'pRiMaRY key' | 'pRiMaRY keY' | 'pRiMaRY kEy' | 'pRiMaRY kEY' | 'pRiMaRY Key' | 'pRiMaRY KeY' | 'pRiMaRY KEy' | 'pRiMaRY KEY' | 'pRiMAry key' | 'pRiMAry keY' | 'pRiMAry kEy' | 'pRiMAry kEY' | 'pRiMAry Key' | 'pRiMAry KeY' | 'pRiMAry KEy' | 'pRiMAry KEY' | 'pRiMArY key' | 'pRiMArY keY' | 'pRiMArY kEy' | 'pRiMArY kEY' | 'pRiMArY Key' | 'pRiMArY KeY' | 'pRiMArY KEy' | 'pRiMArY KEY' | 'pRiMARy key' | 'pRiMARy keY' | 'pRiMARy kEy' | 'pRiMARy kEY' | 'pRiMARy Key' | 'pRiMARy KeY' | 'pRiMARy KEy' | 'pRiMARy KEY' | 'pRiMARY key' | 'pRiMARY keY' | 'pRiMARY kEy' | 'pRiMARY kEY' | 'pRiMARY Key' | 'pRiMARY KeY' | 'pRiMARY KEy' | 'pRiMARY KEY' | 'pRImary key' | 'pRImary keY' | 'pRImary kEy' | 'pRImary kEY' | 'pRImary Key' | 'pRImary KeY' | 'pRImary KEy' | 'pRImary KEY' | 'pRImarY key' | 'pRImarY keY' | 'pRImarY kEy' | 'pRImarY kEY' | 'pRImarY Key' | 'pRImarY KeY' | 'pRImarY KEy' | 'pRImarY KEY' | 'pRImaRy key' | 'pRImaRy keY' | 'pRImaRy kEy' | 'pRImaRy kEY' | 'pRImaRy Key' | 'pRImaRy KeY' | 'pRImaRy KEy' | 'pRImaRy KEY' | 'pRImaRY key' | 'pRImaRY keY' | 'pRImaRY kEy' | 'pRImaRY kEY' | 'pRImaRY Key' | 'pRImaRY KeY' | 'pRImaRY KEy' | 'pRImaRY KEY' | 'pRImAry key' | 'pRImAry keY' | 'pRImAry kEy' | 'pRImAry kEY' | 'pRImAry Key' | 'pRImAry KeY' | 'pRImAry KEy' | 'pRImAry KEY' | 'pRImArY key' | 'pRImArY keY' | 'pRImArY kEy' | 'pRImArY kEY' | 'pRImArY Key' | 'pRImArY KeY' | 'pRImArY KEy' | 'pRImArY KEY' | 'pRImARy key' | 'pRImARy keY' | 'pRImARy kEy' | 'pRImARy kEY' | 'pRImARy Key' | 'pRImARy KeY' | 'pRImARy KEy' | 'pRImARy KEY' | 'pRImARY key' | 'pRImARY keY' | 'pRImARY kEy' | 'pRImARY kEY' | 'pRImARY Key' | 'pRImARY KeY' | 'pRImARY KEy' | 'pRImARY KEY' | 'pRIMary key' | 'pRIMary keY' | 'pRIMary kEy' | 'pRIMary kEY' | 'pRIMary Key' | 'pRIMary KeY' | 'pRIMary KEy' | 'pRIMary KEY' | 'pRIMarY key' | 'pRIMarY keY' | 'pRIMarY kEy' | 'pRIMarY kEY' | 'pRIMarY Key' | 'pRIMarY KeY' | 'pRIMarY KEy' | 'pRIMarY KEY' | 'pRIMaRy key' | 'pRIMaRy keY' | 'pRIMaRy kEy' | 'pRIMaRy kEY' | 'pRIMaRy Key' | 'pRIMaRy KeY' | 'pRIMaRy KEy' | 'pRIMaRy KEY' | 'pRIMaRY key' | 'pRIMaRY keY' | 'pRIMaRY kEy' | 'pRIMaRY kEY' | 'pRIMaRY Key' | 'pRIMaRY KeY' | 'pRIMaRY KEy' | 'pRIMaRY KEY' | 'pRIMAry key' | 'pRIMAry keY' | 'pRIMAry kEy' | 'pRIMAry kEY' | 'pRIMAry Key' | 'pRIMAry KeY' | 'pRIMAry KEy' | 'pRIMAry KEY' | 'pRIMArY key' | 'pRIMArY keY' | 'pRIMArY kEy' | 'pRIMArY kEY' | 'pRIMArY Key' | 'pRIMArY KeY' | 'pRIMArY KEy' | 'pRIMArY KEY' | 'pRIMARy key' | 'pRIMARy keY' | 'pRIMARy kEy' | 'pRIMARy kEY' | 'pRIMARy Key' | 'pRIMARy KeY' | 'pRIMARy KEy' | 'pRIMARy KEY' | 'pRIMARY key' | 'pRIMARY keY' | 'pRIMARY kEy' | 'pRIMARY kEY' | 'pRIMARY Key' | 'pRIMARY KeY' | 'pRIMARY KEy' | 'pRIMARY KEY' | 'Primary key' | 'Primary keY' | 'Primary kEy' | 'Primary kEY' | 'Primary Key' | 'Primary KeY' | 'Primary KEy' | 'Primary KEY' | 'PrimarY key' | 'PrimarY keY' | 'PrimarY kEy' | 'PrimarY kEY' | 'PrimarY Key' | 'PrimarY KeY' | 'PrimarY KEy' | 'PrimarY KEY' | 'PrimaRy key' | 'PrimaRy keY' | 'PrimaRy kEy' | 'PrimaRy kEY' | 'PrimaRy Key' | 'PrimaRy KeY' | 'PrimaRy KEy' | 'PrimaRy KEY' | 'PrimaRY key' | 'PrimaRY keY' | 'PrimaRY kEy' | 'PrimaRY kEY' | 'PrimaRY Key' | 'PrimaRY KeY' | 'PrimaRY KEy' | 'PrimaRY KEY' | 'PrimAry key' | 'PrimAry keY' | 'PrimAry kEy' | 'PrimAry kEY' | 'PrimAry Key' | 'PrimAry KeY' | 'PrimAry KEy' | 'PrimAry KEY' | 'PrimArY key' | 'PrimArY keY' | 'PrimArY kEy' | 'PrimArY kEY' | 'PrimArY Key' | 'PrimArY KeY' | 'PrimArY KEy' | 'PrimArY KEY' | 'PrimARy key' | 'PrimARy keY' | 'PrimARy kEy' | 'PrimARy kEY' | 'PrimARy Key' | 'PrimARy KeY' | 'PrimARy KEy' | 'PrimARy KEY' | 'PrimARY key' | 'PrimARY keY' | 'PrimARY kEy' | 'PrimARY kEY' | 'PrimARY Key' | 'PrimARY KeY' | 'PrimARY KEy' | 'PrimARY KEY' | 'PriMary key' | 'PriMary keY' | 'PriMary kEy' | 'PriMary kEY' | 'PriMary Key' | 'PriMary KeY' | 'PriMary KEy' | 'PriMary KEY' | 'PriMarY key' | 'PriMarY keY' | 'PriMarY kEy' | 'PriMarY kEY' | 'PriMarY Key' | 'PriMarY KeY' | 'PriMarY KEy' | 'PriMarY KEY' | 'PriMaRy key' | 'PriMaRy keY' | 'PriMaRy kEy' | 'PriMaRy kEY' | 'PriMaRy Key' | 'PriMaRy KeY' | 'PriMaRy KEy' | 'PriMaRy KEY' | 'PriMaRY key' | 'PriMaRY keY' | 'PriMaRY kEy' | 'PriMaRY kEY' | 'PriMaRY Key' | 'PriMaRY KeY' | 'PriMaRY KEy' | 'PriMaRY KEY' | 'PriMAry key' | 'PriMAry keY' | 'PriMAry kEy' | 'PriMAry kEY' | 'PriMAry Key' | 'PriMAry KeY' | 'PriMAry KEy' | 'PriMAry KEY' | 'PriMArY key' | 'PriMArY keY' | 'PriMArY kEy' | 'PriMArY kEY' | 'PriMArY Key' | 'PriMArY KeY' | 'PriMArY KEy' | 'PriMArY KEY' | 'PriMARy key' | 'PriMARy keY' | 'PriMARy kEy' | 'PriMARy kEY' | 'PriMARy Key' | 'PriMARy KeY' | 'PriMARy KEy' | 'PriMARy KEY' | 'PriMARY key' | 'PriMARY keY' | 'PriMARY kEy' | 'PriMARY kEY' | 'PriMARY Key' | 'PriMARY KeY' | 'PriMARY KEy' | 'PriMARY KEY' | 'PrImary key' | 'PrImary keY' | 'PrImary kEy' | 'PrImary kEY' | 'PrImary Key' | 'PrImary KeY' | 'PrImary KEy' | 'PrImary KEY' | 'PrImarY key' | 'PrImarY keY' | 'PrImarY kEy' | 'PrImarY kEY' | 'PrImarY Key' | 'PrImarY KeY' | 'PrImarY KEy' | 'PrImarY KEY' | 'PrImaRy key' | 'PrImaRy keY' | 'PrImaRy kEy' | 'PrImaRy kEY' | 'PrImaRy Key' | 'PrImaRy KeY' | 'PrImaRy KEy' | 'PrImaRy KEY' | 'PrImaRY key' | 'PrImaRY keY' | 'PrImaRY kEy' | 'PrImaRY kEY' | 'PrImaRY Key' | 'PrImaRY KeY' | 'PrImaRY KEy' | 'PrImaRY KEY' | 'PrImAry key' | 'PrImAry keY' | 'PrImAry kEy' | 'PrImAry kEY' | 'PrImAry Key' | 'PrImAry KeY' | 'PrImAry KEy' | 'PrImAry KEY' | 'PrImArY key' | 'PrImArY keY' | 'PrImArY kEy' | 'PrImArY kEY' | 'PrImArY Key' | 'PrImArY KeY' | 'PrImArY KEy' | 'PrImArY KEY' | 'PrImARy key' | 'PrImARy keY' | 'PrImARy kEy' | 'PrImARy kEY' | 'PrImARy Key' | 'PrImARy KeY' | 'PrImARy KEy' | 'PrImARy KEY' | 'PrImARY key' | 'PrImARY keY' | 'PrImARY kEy' | 'PrImARY kEY' | 'PrImARY Key' | 'PrImARY KeY' | 'PrImARY KEy' | 'PrImARY KEY' | 'PrIMary key' | 'PrIMary keY' | 'PrIMary kEy' | 'PrIMary kEY' | 'PrIMary Key' | 'PrIMary KeY' | 'PrIMary KEy' | 'PrIMary KEY' | 'PrIMarY key' | 'PrIMarY keY' | 'PrIMarY kEy' | 'PrIMarY kEY' | 'PrIMarY Key' | 'PrIMarY KeY' | 'PrIMarY KEy' | 'PrIMarY KEY' | 'PrIMaRy key' | 'PrIMaRy keY' | 'PrIMaRy kEy' | 'PrIMaRy kEY' | 'PrIMaRy Key' | 'PrIMaRy KeY' | 'PrIMaRy KEy' | 'PrIMaRy KEY' | 'PrIMaRY key' | 'PrIMaRY keY' | 'PrIMaRY kEy' | 'PrIMaRY kEY' | 'PrIMaRY Key' | 'PrIMaRY KeY' | 'PrIMaRY KEy' | 'PrIMaRY KEY' | 'PrIMAry key' | 'PrIMAry keY' | 'PrIMAry kEy' | 'PrIMAry kEY' | 'PrIMAry Key' | 'PrIMAry KeY' | 'PrIMAry KEy' | 'PrIMAry KEY' | 'PrIMArY key' | 'PrIMArY keY' | 'PrIMArY kEy' | 'PrIMArY kEY' | 'PrIMArY Key' | 'PrIMArY KeY' | 'PrIMArY KEy' | 'PrIMArY KEY' | 'PrIMARy key' | 'PrIMARy keY' | 'PrIMARy kEy' | 'PrIMARy kEY' | 'PrIMARy Key' | 'PrIMARy KeY' | 'PrIMARy KEy' | 'PrIMARy KEY' | 'PrIMARY key' | 'PrIMARY keY' | 'PrIMARY kEy' | 'PrIMARY kEY' | 'PrIMARY Key' | 'PrIMARY KeY' | 'PrIMARY KEy' | 'PrIMARY KEY' | 'PRimary key' | 'PRimary keY' | 'PRimary kEy' | 'PRimary kEY' | 'PRimary Key' | 'PRimary KeY' | 'PRimary KEy' | 'PRimary KEY' | 'PRimarY key' | 'PRimarY keY' | 'PRimarY kEy' | 'PRimarY kEY' | 'PRimarY Key' | 'PRimarY KeY' | 'PRimarY KEy' | 'PRimarY KEY' | 'PRimaRy key' | 'PRimaRy keY' | 'PRimaRy kEy' | 'PRimaRy kEY' | 'PRimaRy Key' | 'PRimaRy KeY' | 'PRimaRy KEy' | 'PRimaRy KEY' | 'PRimaRY key' | 'PRimaRY keY' | 'PRimaRY kEy' | 'PRimaRY kEY' | 'PRimaRY Key' | 'PRimaRY KeY' | 'PRimaRY KEy' | 'PRimaRY KEY' | 'PRimAry key' | 'PRimAry keY' | 'PRimAry kEy' | 'PRimAry kEY' | 'PRimAry Key' | 'PRimAry KeY' | 'PRimAry KEy' | 'PRimAry KEY' | 'PRimArY key' | 'PRimArY keY' | 'PRimArY kEy' | 'PRimArY kEY' | 'PRimArY Key' | 'PRimArY KeY' | 'PRimArY KEy' | 'PRimArY KEY' | 'PRimARy key' | 'PRimARy keY' | 'PRimARy kEy' | 'PRimARy kEY' | 'PRimARy Key' | 'PRimARy KeY' | 'PRimARy KEy' | 'PRimARy KEY' | 'PRimARY key' | 'PRimARY keY' | 'PRimARY kEy' | 'PRimARY kEY' | 'PRimARY Key' | 'PRimARY KeY' | 'PRimARY KEy' | 'PRimARY KEY' | 'PRiMary key' | 'PRiMary keY' | 'PRiMary kEy' | 'PRiMary kEY' | 'PRiMary Key' | 'PRiMary KeY' | 'PRiMary KEy' | 'PRiMary KEY' | 'PRiMarY key' | 'PRiMarY keY' | 'PRiMarY kEy' | 'PRiMarY kEY' | 'PRiMarY Key' | 'PRiMarY KeY' | 'PRiMarY KEy' | 'PRiMarY KEY' | 'PRiMaRy key' | 'PRiMaRy keY' | 'PRiMaRy kEy' | 'PRiMaRy kEY' | 'PRiMaRy Key' | 'PRiMaRy KeY' | 'PRiMaRy KEy' | 'PRiMaRy KEY' | 'PRiMaRY key' | 'PRiMaRY keY' | 'PRiMaRY kEy' | 'PRiMaRY kEY' | 'PRiMaRY Key' | 'PRiMaRY KeY' | 'PRiMaRY KEy' | 'PRiMaRY KEY' | 'PRiMAry key' | 'PRiMAry keY' | 'PRiMAry kEy' | 'PRiMAry kEY' | 'PRiMAry Key' | 'PRiMAry KeY' | 'PRiMAry KEy' | 'PRiMAry KEY' | 'PRiMArY key' | 'PRiMArY keY' | 'PRiMArY kEy' | 'PRiMArY kEY' | 'PRiMArY Key' | 'PRiMArY KeY' | 'PRiMArY KEy' | 'PRiMArY KEY' | 'PRiMARy key' | 'PRiMARy keY' | 'PRiMARy kEy' | 'PRiMARy kEY' | 'PRiMARy Key' | 'PRiMARy KeY' | 'PRiMARy KEy' | 'PRiMARy KEY' | 'PRiMARY key' | 'PRiMARY keY' | 'PRiMARY kEy' | 'PRiMARY kEY' | 'PRiMARY Key' | 'PRiMARY KeY' | 'PRiMARY KEy' | 'PRiMARY KEY' | 'PRImary key' | 'PRImary keY' | 'PRImary kEy' | 'PRImary kEY' | 'PRImary Key' | 'PRImary KeY' | 'PRImary KEy' | 'PRImary KEY' | 'PRImarY key' | 'PRImarY keY' | 'PRImarY kEy' | 'PRImarY kEY' | 'PRImarY Key' | 'PRImarY KeY' | 'PRImarY KEy' | 'PRImarY KEY' | 'PRImaRy key' | 'PRImaRy keY' | 'PRImaRy kEy' | 'PRImaRy kEY' | 'PRImaRy Key' | 'PRImaRy KeY' | 'PRImaRy KEy' | 'PRImaRy KEY' | 'PRImaRY key' | 'PRImaRY keY' | 'PRImaRY kEy' | 'PRImaRY kEY' | 'PRImaRY Key' | 'PRImaRY KeY' | 'PRImaRY KEy' | 'PRImaRY KEY' | 'PRImAry key' | 'PRImAry keY' | 'PRImAry kEy' | 'PRImAry kEY' | 'PRImAry Key' | 'PRImAry KeY' | 'PRImAry KEy' | 'PRImAry KEY' | 'PRImArY key' | 'PRImArY keY' | 'PRImArY kEy' | 'PRImArY kEY' | 'PRImArY Key' | 'PRImArY KeY' | 'PRImArY KEy' | 'PRImArY KEY' | 'PRImARy key' | 'PRImARy keY' | 'PRImARy kEy' | 'PRImARy kEY' | 'PRImARy Key' | 'PRImARy KeY' | 'PRImARy KEy' | 'PRImARy KEY' | 'PRImARY key' | 'PRImARY keY' | 'PRImARY kEy' | 'PRImARY kEY' | 'PRImARY Key' | 'PRImARY KeY' | 'PRImARY KEy' | 'PRImARY KEY' | 'PRIMary key' | 'PRIMary keY' | 'PRIMary kEy' | 'PRIMary kEY' | 'PRIMary Key' | 'PRIMary KeY' | 'PRIMary KEy' | 'PRIMary KEY' | 'PRIMarY key' | 'PRIMarY keY' | 'PRIMarY kEy' | 'PRIMarY kEY' | 'PRIMarY Key' | 'PRIMarY KeY' | 'PRIMarY KEy' | 'PRIMarY KEY' | 'PRIMaRy key' | 'PRIMaRy keY' | 'PRIMaRy kEy' | 'PRIMaRy kEY' | 'PRIMaRy Key' | 'PRIMaRy KeY' | 'PRIMaRy KEy' | 'PRIMaRy KEY' | 'PRIMaRY key' | 'PRIMaRY keY' | 'PRIMaRY kEy' | 'PRIMaRY kEY' | 'PRIMaRY Key' | 'PRIMaRY KeY' | 'PRIMaRY KEy' | 'PRIMaRY KEY' | 'PRIMAry key' | 'PRIMAry keY' | 'PRIMAry kEy' | 'PRIMAry kEY' | 'PRIMAry Key' | 'PRIMAry KeY' | 'PRIMAry KEy' | 'PRIMAry KEY' | 'PRIMArY key' | 'PRIMArY keY' | 'PRIMArY kEy' | 'PRIMArY kEY' | 'PRIMArY Key' | 'PRIMArY KeY' | 'PRIMArY KEy' | 'PRIMArY KEY' | 'PRIMARy key' | 'PRIMARy keY' | 'PRIMARy kEy' | 'PRIMARy kEY' | 'PRIMARy Key' | 'PRIMARy KeY' | 'PRIMARy KEy' | 'PRIMARy KEY' | 'PRIMARY key' | 'PRIMARY keY' | 'PRIMARY kEy' | 'PRIMARY kEY' | 'PRIMARY Key' | 'PRIMARY KeY' | 'PRIMARY KEy' | 'PRIMARY KEY';

INT: 'int' | 'inT' | 'iNt' | 'iNT' | 'Int' | 'InT' | 'INt' | 'INT';

FLOAT: 'float' | 'floaT' | 'floAt' | 'floAT' | 'flOat' | 'flOaT' | 'flOAt' | 'flOAT' | 'fLoat' | 'fLoaT' | 'fLoAt' | 'fLoAT' | 'fLOat' | 'fLOaT' | 'fLOAt' | 'fLOAT' | 'Float' | 'FloaT' | 'FloAt' | 'FloAT' | 'FlOat' | 'FlOaT' | 'FlOAt' | 'FlOAT' | 'FLoat' | 'FLoaT' | 'FLoAt' | 'FLoAT' | 'FLOat' | 'FLOaT' | 'FLOAt' | 'FLOAT';

VARCHAR: 'varchar' | 'varchaR' | 'varchAr' | 'varchAR' | 'varcHar' | 'varcHaR' | 'varcHAr' | 'varcHAR' | 'varChar' | 'varChaR' | 'varChAr' | 'varChAR' | 'varCHar' | 'varCHaR' | 'varCHAr' | 'varCHAR' | 'vaRchar' | 'vaRchaR' | 'vaRchAr' | 'vaRchAR' | 'vaRcHar' | 'vaRcHaR' | 'vaRcHAr' | 'vaRcHAR' | 'vaRChar' | 'vaRChaR' | 'vaRChAr' | 'vaRChAR' | 'vaRCHar' | 'vaRCHaR' | 'vaRCHAr' | 'vaRCHAR' | 'vArchar' | 'vArchaR' | 'vArchAr' | 'vArchAR' | 'vArcHar' | 'vArcHaR' | 'vArcHAr' | 'vArcHAR' | 'vArChar' | 'vArChaR' | 'vArChAr' | 'vArChAR' | 'vArCHar' | 'vArCHaR' | 'vArCHAr' | 'vArCHAR' | 'vARchar' | 'vARchaR' | 'vARchAr' | 'vARchAR' | 'vARcHar' | 'vARcHaR' | 'vARcHAr' | 'vARcHAR' | 'vARChar' | 'vARChaR' | 'vARChAr' | 'vARChAR' | 'vARCHar' | 'vARCHaR' | 'vARCHAr' | 'vARCHAR' | 'Varchar' | 'VarchaR' | 'VarchAr' | 'VarchAR' | 'VarcHar' | 'VarcHaR' | 'VarcHAr' | 'VarcHAR' | 'VarChar' | 'VarChaR' | 'VarChAr' | 'VarChAR' | 'VarCHar' | 'VarCHaR' | 'VarCHAr' | 'VarCHAR' | 'VaRchar' | 'VaRchaR' | 'VaRchAr' | 'VaRchAR' | 'VaRcHar' | 'VaRcHaR' | 'VaRcHAr' | 'VaRcHAR' | 'VaRChar' | 'VaRChaR' | 'VaRChAr' | 'VaRChAR' | 'VaRCHar' | 'VaRCHaR' | 'VaRCHAr' | 'VaRCHAR' | 'VArchar' | 'VArchaR' | 'VArchAr' | 'VArchAR' | 'VArcHar' | 'VArcHaR' | 'VArcHAr' | 'VArcHAR' | 'VArChar' | 'VArChaR' | 'VArChAr' | 'VArChAR' | 'VArCHar' | 'VArCHaR' | 'VArCHAr' | 'VArCHAR' | 'VARchar' | 'VARchaR' | 'VARchAr' | 'VARchAR' | 'VARcHar' | 'VARcHaR' | 'VARcHAr' | 'VARcHAR' | 'VARChar' | 'VARChaR' | 'VARChAr' | 'VARChAR' | 'VARCHar' | 'VARCHaR' | 'VARCHAr' | 'VARCHAR';

DATE: 'date' | 'datE' | 'daTe' | 'daTE' | 'dAte' | 'dAtE' | 'dATe' | 'dATE' | 'Date' | 'DatE' | 'DaTe' | 'DaTE' | 'DAte' | 'DAtE' | 'DATe' | 'DATE';

CHECK: 'check' | 'checK' | 'cheCk' | 'cheCK' | 'chEck' | 'chEcK' | 'chECk' | 'chECK' | 'cHeck' | 'cHecK' | 'cHeCk' | 'cHeCK' | 'cHEck' | 'cHEcK' | 'cHECk' | 'cHECK' | 'Check' | 'ChecK' | 'CheCk' | 'CheCK' | 'ChEck' | 'ChEcK' | 'ChECk' | 'ChECK' | 'CHeck' | 'CHecK' | 'CHeCk' | 'CHeCK' | 'CHEck' | 'CHEcK' | 'CHECk' | 'CHECK';

NULL: 'null'| 'nulL' | 'nuLl'| 'nuLL' | 'nUll' | 'nUlL' | 'nULl' | 'nULL'| 'Null' | 'NulL' | 'NuLl' | 'NuLL' | 'NUll'| 'NUlL' | 'NULl' | 'NULL';

LETTER: [a-zA-Z] | '_';

DIGIT: [0-9] ;

WS: [ \t\r\n]+ -> skip ;

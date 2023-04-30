package compiler;// Generated from C:/Users/dell/IdeaProjects/MyDBEngine/src/main/sqlgrammar\SQL.g4 by ANTLR 4.12.0
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SQLParser}.
 */
public interface SQLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SQLParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(SQLParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(SQLParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#selectStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectStatement(SQLParser.SelectStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#selectStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectStatement(SQLParser.SelectStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#insertStatement}.
	 * @param ctx the parse tree
	 */
	void enterInsertStatement(SQLParser.InsertStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#insertStatement}.
	 * @param ctx the parse tree
	 */
	void exitInsertStatement(SQLParser.InsertStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#updateStatement}.
	 * @param ctx the parse tree
	 */
	void enterUpdateStatement(SQLParser.UpdateStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#updateStatement}.
	 * @param ctx the parse tree
	 */
	void exitUpdateStatement(SQLParser.UpdateStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#deleteStatement}.
	 * @param ctx the parse tree
	 */
	void enterDeleteStatement(SQLParser.DeleteStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#deleteStatement}.
	 * @param ctx the parse tree
	 */
	void exitDeleteStatement(SQLParser.DeleteStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#createTableStatement}.
	 * @param ctx the parse tree
	 */
	void enterCreateTableStatement(SQLParser.CreateTableStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#createTableStatement}.
	 * @param ctx the parse tree
	 */
	void exitCreateTableStatement(SQLParser.CreateTableStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#columnList}.
	 * @param ctx the parse tree
	 */
	void enterColumnList(SQLParser.ColumnListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#columnList}.
	 * @param ctx the parse tree
	 */
	void exitColumnList(SQLParser.ColumnListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#tableList}.
	 * @param ctx the parse tree
	 */
	void enterTableList(SQLParser.TableListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#tableList}.
	 * @param ctx the parse tree
	 */
	void exitTableList(SQLParser.TableListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#valueList}.
	 * @param ctx the parse tree
	 */
	void enterValueList(SQLParser.ValueListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#valueList}.
	 * @param ctx the parse tree
	 */
	void exitValueList(SQLParser.ValueListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#conditionList}.
	 * @param ctx the parse tree
	 */
	void enterConditionList(SQLParser.ConditionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#conditionList}.
	 * @param ctx the parse tree
	 */
	void exitConditionList(SQLParser.ConditionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#conditionExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionExpression(SQLParser.ConditionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#conditionExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionExpression(SQLParser.ConditionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#deleteConditionList}.
	 * @param ctx the parse tree
	 */
	void enterDeleteConditionList(SQLParser.DeleteConditionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#deleteConditionList}.
	 * @param ctx the parse tree
	 */
	void exitDeleteConditionList(SQLParser.DeleteConditionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#updateList}.
	 * @param ctx the parse tree
	 */
	void enterUpdateList(SQLParser.UpdateListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#updateList}.
	 * @param ctx the parse tree
	 */
	void exitUpdateList(SQLParser.UpdateListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(SQLParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(SQLParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#tableReference}.
	 * @param ctx the parse tree
	 */
	void enterTableReference(SQLParser.TableReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#tableReference}.
	 * @param ctx the parse tree
	 */
	void exitTableReference(SQLParser.TableReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#tableReferencedConditionList}.
	 * @param ctx the parse tree
	 */
	void enterTableReferencedConditionList(SQLParser.TableReferencedConditionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#tableReferencedConditionList}.
	 * @param ctx the parse tree
	 */
	void exitTableReferencedConditionList(SQLParser.TableReferencedConditionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#columnDefinition}.
	 * @param ctx the parse tree
	 */
	void enterColumnDefinition(SQLParser.ColumnDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#columnDefinition}.
	 * @param ctx the parse tree
	 */
	void exitColumnDefinition(SQLParser.ColumnDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#columnDefinitionConditionList}.
	 * @param ctx the parse tree
	 */
	void enterColumnDefinitionConditionList(SQLParser.ColumnDefinitionConditionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#columnDefinitionConditionList}.
	 * @param ctx the parse tree
	 */
	void exitColumnDefinitionConditionList(SQLParser.ColumnDefinitionConditionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterDataType(SQLParser.DataTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitDataType(SQLParser.DataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#word}.
	 * @param ctx the parse tree
	 */
	void enterWord(SQLParser.WordContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#word}.
	 * @param ctx the parse tree
	 */
	void exitWord(SQLParser.WordContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#columnName}.
	 * @param ctx the parse tree
	 */
	void enterColumnName(SQLParser.ColumnNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#columnName}.
	 * @param ctx the parse tree
	 */
	void exitColumnName(SQLParser.ColumnNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#tableName}.
	 * @param ctx the parse tree
	 */
	void enterTableName(SQLParser.TableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#tableName}.
	 * @param ctx the parse tree
	 */
	void exitTableName(SQLParser.TableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(SQLParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(SQLParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOperator(SQLParser.OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOperator(SQLParser.OperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#logicalOperator}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOperator(SQLParser.LogicalOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#logicalOperator}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOperator(SQLParser.LogicalOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(SQLParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(SQLParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#double}.
	 * @param ctx the parse tree
	 */
	void enterDouble(SQLParser.DoubleContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#double}.
	 * @param ctx the parse tree
	 */
	void exitDouble(SQLParser.DoubleContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(SQLParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(SQLParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#dateValue}.
	 * @param ctx the parse tree
	 */
	void enterDateValue(SQLParser.DateValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#dateValue}.
	 * @param ctx the parse tree
	 */
	void exitDateValue(SQLParser.DateValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLParser#date}.
	 * @param ctx the parse tree
	 */
	void enterDate(SQLParser.DateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLParser#date}.
	 * @param ctx the parse tree
	 */
	void exitDate(SQLParser.DateContext ctx);
}
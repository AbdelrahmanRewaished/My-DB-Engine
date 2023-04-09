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
}
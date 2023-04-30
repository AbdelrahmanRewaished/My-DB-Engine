package compiler;// Generated from C:/Users/dell/IdeaProjects/MyDBEngine/src/main/sqlgrammar\SQL.g4 by ANTLR 4.12.0
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SQLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SQLParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(SQLParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#selectStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectStatement(SQLParser.SelectStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#insertStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsertStatement(SQLParser.InsertStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#updateStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdateStatement(SQLParser.UpdateStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#deleteStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteStatement(SQLParser.DeleteStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#createTableStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateTableStatement(SQLParser.CreateTableStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#columnList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnList(SQLParser.ColumnListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#tableList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableList(SQLParser.TableListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#valueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueList(SQLParser.ValueListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#conditionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionList(SQLParser.ConditionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#conditionExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionExpression(SQLParser.ConditionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#deleteConditionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteConditionList(SQLParser.DeleteConditionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#updateList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdateList(SQLParser.UpdateListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(SQLParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#tableReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableReference(SQLParser.TableReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#tableReferencedConditionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableReferencedConditionList(SQLParser.TableReferencedConditionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#columnDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnDefinition(SQLParser.ColumnDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#columnDefinitionConditionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnDefinitionConditionList(SQLParser.ColumnDefinitionConditionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataType(SQLParser.DataTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#word}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWord(SQLParser.WordContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#columnName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnName(SQLParser.ColumnNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#tableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableName(SQLParser.TableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(SQLParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperator(SQLParser.OperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#logicalOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOperator(SQLParser.LogicalOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#integer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(SQLParser.IntegerContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#double}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDouble(SQLParser.DoubleContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(SQLParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#dateValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateValue(SQLParser.DateValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#date}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate(SQLParser.DateContext ctx);
}
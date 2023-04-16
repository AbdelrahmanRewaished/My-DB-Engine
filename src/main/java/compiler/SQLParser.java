package compiler;// Generated from C:/Users/dell/IdeaProjects/MyDBEngine/src/main/sqlgrammar\SQL.g4 by ANTLR 4.12.0
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class SQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, PRIMARYKEY=12, SELECT=13, INSERT=14, INTO=15, UPDATE=16, 
		SET=17, DELETE=18, FROM=19, VALUES=20, AND=21, OR=22, XOR=23, CREATE=24, 
		TABLE=25, WHERE=26, INT=27, FLOAT=28, VARCHAR=29, DATE=30, LETTER=31, 
		DIGIT=32, WS=33;
	public static final int
		RULE_query = 0, RULE_selectStatement = 1, RULE_insertStatement = 2, RULE_updateStatement = 3, 
		RULE_deleteStatement = 4, RULE_createTableStatement = 5, RULE_columnList = 6, 
		RULE_tableList = 7, RULE_valueList = 8, RULE_updateList = 9, RULE_conditionList = 10, 
		RULE_deleteConditionList = 11, RULE_tableReference = 12, RULE_tableReferencedConditionList = 13, 
		RULE_columnDefinition = 14, RULE_dataType = 15, RULE_columnName = 16, 
		RULE_tableName = 17, RULE_value = 18, RULE_operator = 19, RULE_logicalOperator = 20, 
		RULE_integer = 21, RULE_double = 22, RULE_string = 23, RULE_date = 24;
	private static String[] makeRuleNames() {
		return new String[] {
			"query", "selectStatement", "insertStatement", "updateStatement", "deleteStatement", 
			"createTableStatement", "columnList", "tableList", "valueList", "updateList", 
			"conditionList", "deleteConditionList", "tableReference", "tableReferencedConditionList", 
			"columnDefinition", "dataType", "columnName", "tableName", "value", "operator", 
			"logicalOperator", "integer", "double", "string", "date"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'*'", "'('", "')'", "'='", "','", "'.'", "'<'", "'>'", "'<='", 
			"'>='", "'-'", "'PRIMARY KEY'", "'SELECT'", "'INSERT'", "'INTO'", "'UPDATE'", 
			"'SET'", "'DELETE'", "'FROM'", "'VALUES'", "'AND'", "'OR'", "'XOR'", 
			"'CREATE'", "'TABLE'", "'WHERE'", "'INT'", "'FLOAT'", "'VARCHAR'", "'DATE'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"PRIMARYKEY", "SELECT", "INSERT", "INTO", "UPDATE", "SET", "DELETE", 
			"FROM", "VALUES", "AND", "OR", "XOR", "CREATE", "TABLE", "WHERE", "INT", 
			"FLOAT", "VARCHAR", "DATE", "LETTER", "DIGIT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QueryContext extends ParserRuleContext {
		public SelectStatementContext selectStatement() {
			return getRuleContext(SelectStatementContext.class,0);
		}
		public InsertStatementContext insertStatement() {
			return getRuleContext(InsertStatementContext.class,0);
		}
		public UpdateStatementContext updateStatement() {
			return getRuleContext(UpdateStatementContext.class,0);
		}
		public DeleteStatementContext deleteStatement() {
			return getRuleContext(DeleteStatementContext.class,0);
		}
		public CreateTableStatementContext createTableStatement() {
			return getRuleContext(CreateTableStatementContext.class,0);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_query);
		try {
			setState(55);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SELECT:
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				selectStatement();
				}
				break;
			case INSERT:
				enterOuterAlt(_localctx, 2);
				{
				setState(51);
				insertStatement();
				}
				break;
			case UPDATE:
				enterOuterAlt(_localctx, 3);
				{
				setState(52);
				updateStatement();
				}
				break;
			case DELETE:
				enterOuterAlt(_localctx, 4);
				{
				setState(53);
				deleteStatement();
				}
				break;
			case CREATE:
				enterOuterAlt(_localctx, 5);
				{
				setState(54);
				createTableStatement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectStatementContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(SQLParser.SELECT, 0); }
		public TerminalNode FROM() { return getToken(SQLParser.FROM, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(SQLParser.WHERE, 0); }
		public ConditionListContext conditionList() {
			return getRuleContext(ConditionListContext.class,0);
		}
		public TableListContext tableList() {
			return getRuleContext(TableListContext.class,0);
		}
		public TableReferencedConditionListContext tableReferencedConditionList() {
			return getRuleContext(TableReferencedConditionListContext.class,0);
		}
		public SelectStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterSelectStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitSelectStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitSelectStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectStatementContext selectStatement() throws RecognitionException {
		SelectStatementContext _localctx = new SelectStatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_selectStatement);
		try {
			setState(75);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(57);
				match(SELECT);
				setState(58);
				match(T__0);
				setState(59);
				match(FROM);
				setState(60);
				tableName();
				setState(61);
				match(WHERE);
				setState(62);
				conditionList();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(64);
				match(SELECT);
				setState(65);
				match(T__0);
				setState(66);
				match(FROM);
				setState(67);
				tableList();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(68);
				match(SELECT);
				setState(69);
				match(T__0);
				setState(70);
				match(FROM);
				setState(71);
				tableList();
				setState(72);
				match(WHERE);
				setState(73);
				tableReferencedConditionList();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InsertStatementContext extends ParserRuleContext {
		public TerminalNode INSERT() { return getToken(SQLParser.INSERT, 0); }
		public TerminalNode INTO() { return getToken(SQLParser.INTO, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public ColumnListContext columnList() {
			return getRuleContext(ColumnListContext.class,0);
		}
		public TerminalNode VALUES() { return getToken(SQLParser.VALUES, 0); }
		public ValueListContext valueList() {
			return getRuleContext(ValueListContext.class,0);
		}
		public InsertStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterInsertStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitInsertStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitInsertStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertStatementContext insertStatement() throws RecognitionException {
		InsertStatementContext _localctx = new InsertStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_insertStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(INSERT);
			setState(78);
			match(INTO);
			setState(79);
			tableName();
			setState(80);
			match(T__1);
			setState(81);
			columnList();
			setState(82);
			match(T__2);
			setState(83);
			match(VALUES);
			setState(84);
			match(T__1);
			setState(85);
			valueList();
			setState(86);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UpdateStatementContext extends ParserRuleContext {
		public TerminalNode UPDATE() { return getToken(SQLParser.UPDATE, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode SET() { return getToken(SQLParser.SET, 0); }
		public UpdateListContext updateList() {
			return getRuleContext(UpdateListContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(SQLParser.WHERE, 0); }
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public UpdateStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_updateStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterUpdateStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitUpdateStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitUpdateStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpdateStatementContext updateStatement() throws RecognitionException {
		UpdateStatementContext _localctx = new UpdateStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_updateStatement);
		try {
			setState(102);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(88);
				match(UPDATE);
				setState(89);
				tableName();
				setState(90);
				match(SET);
				setState(91);
				updateList();
				setState(92);
				match(WHERE);
				setState(93);
				columnName();
				setState(94);
				match(T__3);
				setState(95);
				value();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(97);
				match(UPDATE);
				setState(98);
				tableName();
				setState(99);
				match(SET);
				setState(100);
				updateList();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeleteStatementContext extends ParserRuleContext {
		public TerminalNode DELETE() { return getToken(SQLParser.DELETE, 0); }
		public TerminalNode FROM() { return getToken(SQLParser.FROM, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(SQLParser.WHERE, 0); }
		public DeleteConditionListContext deleteConditionList() {
			return getRuleContext(DeleteConditionListContext.class,0);
		}
		public DeleteStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDeleteStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDeleteStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitDeleteStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeleteStatementContext deleteStatement() throws RecognitionException {
		DeleteStatementContext _localctx = new DeleteStatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_deleteStatement);
		try {
			setState(113);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(104);
				match(DELETE);
				setState(105);
				match(FROM);
				setState(106);
				tableName();
				setState(107);
				match(WHERE);
				setState(108);
				deleteConditionList();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(110);
				match(DELETE);
				setState(111);
				match(FROM);
				setState(112);
				tableName();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateTableStatementContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(SQLParser.CREATE, 0); }
		public TerminalNode TABLE() { return getToken(SQLParser.TABLE, 0); }
		public TableNameContext tableName() {
			return getRuleContext(TableNameContext.class,0);
		}
		public List<ColumnDefinitionContext> columnDefinition() {
			return getRuleContexts(ColumnDefinitionContext.class);
		}
		public ColumnDefinitionContext columnDefinition(int i) {
			return getRuleContext(ColumnDefinitionContext.class,i);
		}
		public TerminalNode PRIMARYKEY() { return getToken(SQLParser.PRIMARYKEY, 0); }
		public CreateTableStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTableStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterCreateTableStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitCreateTableStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitCreateTableStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateTableStatementContext createTableStatement() throws RecognitionException {
		CreateTableStatementContext _localctx = new CreateTableStatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_createTableStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(CREATE);
			setState(116);
			match(TABLE);
			setState(117);
			tableName();
			setState(118);
			match(T__1);
			setState(119);
			columnDefinition();
			setState(120);
			match(PRIMARYKEY);
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(121);
				match(T__4);
				setState(122);
				columnDefinition();
				}
				}
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(128);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnListContext extends ParserRuleContext {
		public List<ColumnNameContext> columnName() {
			return getRuleContexts(ColumnNameContext.class);
		}
		public ColumnNameContext columnName(int i) {
			return getRuleContext(ColumnNameContext.class,i);
		}
		public ColumnListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterColumnList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitColumnList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitColumnList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnListContext columnList() throws RecognitionException {
		ColumnListContext _localctx = new ColumnListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_columnList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			columnName();
			setState(135);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(131);
				match(T__4);
				setState(132);
				columnName();
				}
				}
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableListContext extends ParserRuleContext {
		public List<TableNameContext> tableName() {
			return getRuleContexts(TableNameContext.class);
		}
		public TableNameContext tableName(int i) {
			return getRuleContext(TableNameContext.class,i);
		}
		public List<TableReferenceContext> tableReference() {
			return getRuleContexts(TableReferenceContext.class);
		}
		public TableReferenceContext tableReference(int i) {
			return getRuleContext(TableReferenceContext.class,i);
		}
		public TableListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterTableList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitTableList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitTableList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableListContext tableList() throws RecognitionException {
		TableListContext _localctx = new TableListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_tableList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			tableName();
			setState(139);
			tableReference();
			setState(144); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(140);
				match(T__4);
				setState(141);
				tableName();
				setState(142);
				tableReference();
				}
				}
				setState(146); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__4 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueListContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public ValueListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterValueList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitValueList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitValueList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueListContext valueList() throws RecognitionException {
		ValueListContext _localctx = new ValueListContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_valueList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			value();
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(149);
				match(T__4);
				setState(150);
				value();
				}
				}
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UpdateListContext extends ParserRuleContext {
		public List<ColumnNameContext> columnName() {
			return getRuleContexts(ColumnNameContext.class);
		}
		public ColumnNameContext columnName(int i) {
			return getRuleContext(ColumnNameContext.class,i);
		}
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public UpdateListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_updateList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterUpdateList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitUpdateList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitUpdateList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpdateListContext updateList() throws RecognitionException {
		UpdateListContext _localctx = new UpdateListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_updateList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			columnName();
			setState(157);
			match(T__3);
			setState(158);
			value();
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(159);
				match(T__4);
				setState(160);
				columnName();
				setState(161);
				match(T__3);
				setState(162);
				value();
				}
				}
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConditionListContext extends ParserRuleContext {
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public OperatorContext operator() {
			return getRuleContext(OperatorContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public List<LogicalOperatorContext> logicalOperator() {
			return getRuleContexts(LogicalOperatorContext.class);
		}
		public LogicalOperatorContext logicalOperator(int i) {
			return getRuleContext(LogicalOperatorContext.class,i);
		}
		public List<ConditionListContext> conditionList() {
			return getRuleContexts(ConditionListContext.class);
		}
		public ConditionListContext conditionList(int i) {
			return getRuleContext(ConditionListContext.class,i);
		}
		public ConditionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterConditionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitConditionList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitConditionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionListContext conditionList() throws RecognitionException {
		ConditionListContext _localctx = new ConditionListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_conditionList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			columnName();
			setState(170);
			operator();
			setState(171);
			value();
			setState(177);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(172);
					logicalOperator();
					setState(173);
					conditionList();
					}
					} 
				}
				setState(179);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeleteConditionListContext extends ParserRuleContext {
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public List<LogicalOperatorContext> logicalOperator() {
			return getRuleContexts(LogicalOperatorContext.class);
		}
		public LogicalOperatorContext logicalOperator(int i) {
			return getRuleContext(LogicalOperatorContext.class,i);
		}
		public List<DeleteConditionListContext> deleteConditionList() {
			return getRuleContexts(DeleteConditionListContext.class);
		}
		public DeleteConditionListContext deleteConditionList(int i) {
			return getRuleContext(DeleteConditionListContext.class,i);
		}
		public DeleteConditionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteConditionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDeleteConditionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDeleteConditionList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitDeleteConditionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeleteConditionListContext deleteConditionList() throws RecognitionException {
		DeleteConditionListContext _localctx = new DeleteConditionListContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_deleteConditionList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			columnName();
			setState(181);
			match(T__3);
			setState(182);
			value();
			setState(188);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(183);
					logicalOperator();
					setState(184);
					deleteConditionList();
					}
					} 
				}
				setState(190);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableReferenceContext extends ParserRuleContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TableReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableReference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterTableReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitTableReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitTableReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableReferenceContext tableReference() throws RecognitionException {
		TableReferenceContext _localctx = new TableReferenceContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_tableReference);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			string();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableReferencedConditionListContext extends ParserRuleContext {
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public OperatorContext operator() {
			return getRuleContext(OperatorContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public List<TableReferenceContext> tableReference() {
			return getRuleContexts(TableReferenceContext.class);
		}
		public TableReferenceContext tableReference(int i) {
			return getRuleContext(TableReferenceContext.class,i);
		}
		public List<LogicalOperatorContext> logicalOperator() {
			return getRuleContexts(LogicalOperatorContext.class);
		}
		public LogicalOperatorContext logicalOperator(int i) {
			return getRuleContext(LogicalOperatorContext.class,i);
		}
		public List<TableReferencedConditionListContext> tableReferencedConditionList() {
			return getRuleContexts(TableReferencedConditionListContext.class);
		}
		public TableReferencedConditionListContext tableReferencedConditionList(int i) {
			return getRuleContext(TableReferencedConditionListContext.class,i);
		}
		public TableReferencedConditionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableReferencedConditionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterTableReferencedConditionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitTableReferencedConditionList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitTableReferencedConditionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableReferencedConditionListContext tableReferencedConditionList() throws RecognitionException {
		TableReferencedConditionListContext _localctx = new TableReferencedConditionListContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_tableReferencedConditionList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(194); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(193);
				tableReference();
				}
				}
				setState(196); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==LETTER );
			setState(199); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(198);
				match(T__5);
				}
				}
				setState(201); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__5 );
			setState(203);
			columnName();
			setState(204);
			operator();
			setState(205);
			value();
			setState(211);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(206);
					logicalOperator();
					setState(207);
					tableReferencedConditionList();
					}
					} 
				}
				setState(213);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnDefinitionContext extends ParserRuleContext {
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public ColumnDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterColumnDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitColumnDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitColumnDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnDefinitionContext columnDefinition() throws RecognitionException {
		ColumnDefinitionContext _localctx = new ColumnDefinitionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_columnDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			columnName();
			setState(215);
			dataType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DataTypeContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(SQLParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(SQLParser.FLOAT, 0); }
		public TerminalNode VARCHAR() { return getToken(SQLParser.VARCHAR, 0); }
		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class,0);
		}
		public TerminalNode DATE() { return getToken(SQLParser.DATE, 0); }
		public DataTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDataType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDataType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitDataType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataTypeContext dataType() throws RecognitionException {
		DataTypeContext _localctx = new DataTypeContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_dataType);
		try {
			setState(225);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(217);
				match(INT);
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(218);
				match(FLOAT);
				}
				break;
			case VARCHAR:
				enterOuterAlt(_localctx, 3);
				{
				setState(219);
				match(VARCHAR);
				setState(220);
				match(T__1);
				setState(221);
				integer();
				setState(222);
				match(T__2);
				}
				break;
			case DATE:
				enterOuterAlt(_localctx, 4);
				{
				setState(224);
				match(DATE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnNameContext extends ParserRuleContext {
		public List<TerminalNode> LETTER() { return getTokens(SQLParser.LETTER); }
		public TerminalNode LETTER(int i) {
			return getToken(SQLParser.LETTER, i);
		}
		public List<TerminalNode> DIGIT() { return getTokens(SQLParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(SQLParser.DIGIT, i);
		}
		public ColumnNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterColumnName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitColumnName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitColumnName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnNameContext columnName() throws RecognitionException {
		ColumnNameContext _localctx = new ColumnNameContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_columnName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(227);
				match(LETTER);
				}
				}
				setState(230); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==LETTER );
			setState(235);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DIGIT) {
				{
				{
				setState(232);
				match(DIGIT);
				}
				}
				setState(237);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableNameContext extends ParserRuleContext {
		public List<TerminalNode> LETTER() { return getTokens(SQLParser.LETTER); }
		public TerminalNode LETTER(int i) {
			return getToken(SQLParser.LETTER, i);
		}
		public List<TerminalNode> DIGIT() { return getTokens(SQLParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(SQLParser.DIGIT, i);
		}
		public TableNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterTableName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitTableName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitTableName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableNameContext tableName() throws RecognitionException {
		TableNameContext _localctx = new TableNameContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_tableName);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(239); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(238);
					match(LETTER);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(241); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			} while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER );
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DIGIT) {
				{
				{
				setState(243);
				match(DIGIT);
				}
				}
				setState(248);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public IntegerContext integer() {
			return getRuleContext(IntegerContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public DateContext date() {
			return getRuleContext(DateContext.class,0);
		}
		public DoubleContext double_() {
			return getRuleContext(DoubleContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_value);
		try {
			setState(253);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(249);
				integer();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(250);
				string();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(251);
				date();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(252);
				double_();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OperatorContext extends ParserRuleContext {
		public OperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorContext operator() throws RecognitionException {
		OperatorContext _localctx = new OperatorContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(255);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1936L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicalOperatorContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(SQLParser.AND, 0); }
		public TerminalNode OR() { return getToken(SQLParser.OR, 0); }
		public TerminalNode XOR() { return getToken(SQLParser.XOR, 0); }
		public LogicalOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterLogicalOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitLogicalOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitLogicalOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOperatorContext logicalOperator() throws RecognitionException {
		LogicalOperatorContext _localctx = new LogicalOperatorContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_logicalOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 14680064L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IntegerContext extends ParserRuleContext {
		public List<TerminalNode> DIGIT() { return getTokens(SQLParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(SQLParser.DIGIT, i);
		}
		public IntegerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitInteger(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitInteger(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntegerContext integer() throws RecognitionException {
		IntegerContext _localctx = new IntegerContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_integer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(259);
				match(DIGIT);
				}
				}
				setState(262); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==DIGIT );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DoubleContext extends ParserRuleContext {
		public List<TerminalNode> DIGIT() { return getTokens(SQLParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(SQLParser.DIGIT, i);
		}
		public DoubleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_double; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDouble(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDouble(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitDouble(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoubleContext double_() throws RecognitionException {
		DoubleContext _localctx = new DoubleContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_double);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(264);
				match(DIGIT);
				}
				}
				setState(267); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==DIGIT );
			setState(269);
			match(T__5);
			setState(271); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(270);
				match(DIGIT);
				}
				}
				setState(273); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==DIGIT );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringContext extends ParserRuleContext {
		public List<TerminalNode> LETTER() { return getTokens(SQLParser.LETTER); }
		public TerminalNode LETTER(int i) {
			return getToken(SQLParser.LETTER, i);
		}
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_string);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(276); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(275);
					match(LETTER);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(278); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			} while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DateContext extends ParserRuleContext {
		public List<TerminalNode> DIGIT() { return getTokens(SQLParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(SQLParser.DIGIT, i);
		}
		public DateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).enterDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLListener ) ((SQLListener)listener).exitDate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor) return ((SQLVisitor<? extends T>)visitor).visitDate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateContext date() throws RecognitionException {
		DateContext _localctx = new DateContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_date);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			match(DIGIT);
			setState(281);
			match(DIGIT);
			setState(282);
			match(DIGIT);
			setState(283);
			match(DIGIT);
			setState(284);
			match(T__10);
			setState(285);
			match(DIGIT);
			setState(286);
			match(DIGIT);
			setState(287);
			match(T__10);
			setState(288);
			match(DIGIT);
			setState(289);
			match(DIGIT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001!\u0124\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000"+
		"8\b\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0003\u0001L\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003g\b\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004r\b\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0005\u0005|\b\u0005\n\u0005\f\u0005\u007f\t\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006\u0086"+
		"\b\u0006\n\u0006\f\u0006\u0089\t\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0004\u0007\u0091\b\u0007\u000b\u0007"+
		"\f\u0007\u0092\u0001\b\u0001\b\u0001\b\u0005\b\u0098\b\b\n\b\f\b\u009b"+
		"\t\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0005"+
		"\t\u00a5\b\t\n\t\f\t\u00a8\t\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n"+
		"\u0001\n\u0005\n\u00b0\b\n\n\n\f\n\u00b3\t\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u00bb\b\u000b\n"+
		"\u000b\f\u000b\u00be\t\u000b\u0001\f\u0001\f\u0001\r\u0004\r\u00c3\b\r"+
		"\u000b\r\f\r\u00c4\u0001\r\u0004\r\u00c8\b\r\u000b\r\f\r\u00c9\u0001\r"+
		"\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u00d2\b\r\n\r\f\r\u00d5"+
		"\t\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003"+
		"\u000f\u00e2\b\u000f\u0001\u0010\u0004\u0010\u00e5\b\u0010\u000b\u0010"+
		"\f\u0010\u00e6\u0001\u0010\u0005\u0010\u00ea\b\u0010\n\u0010\f\u0010\u00ed"+
		"\t\u0010\u0001\u0011\u0004\u0011\u00f0\b\u0011\u000b\u0011\f\u0011\u00f1"+
		"\u0001\u0011\u0005\u0011\u00f5\b\u0011\n\u0011\f\u0011\u00f8\t\u0011\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u00fe\b\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0004\u0015\u0105"+
		"\b\u0015\u000b\u0015\f\u0015\u0106\u0001\u0016\u0004\u0016\u010a\b\u0016"+
		"\u000b\u0016\f\u0016\u010b\u0001\u0016\u0001\u0016\u0004\u0016\u0110\b"+
		"\u0016\u000b\u0016\f\u0016\u0111\u0001\u0017\u0004\u0017\u0115\b\u0017"+
		"\u000b\u0017\f\u0017\u0116\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0000\u0000\u0019\u0000\u0002\u0004\u0006\b\n"+
		"\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.0\u0000"+
		"\u0002\u0002\u0000\u0004\u0004\u0007\n\u0001\u0000\u0015\u0017\u012a\u0000"+
		"7\u0001\u0000\u0000\u0000\u0002K\u0001\u0000\u0000\u0000\u0004M\u0001"+
		"\u0000\u0000\u0000\u0006f\u0001\u0000\u0000\u0000\bq\u0001\u0000\u0000"+
		"\u0000\ns\u0001\u0000\u0000\u0000\f\u0082\u0001\u0000\u0000\u0000\u000e"+
		"\u008a\u0001\u0000\u0000\u0000\u0010\u0094\u0001\u0000\u0000\u0000\u0012"+
		"\u009c\u0001\u0000\u0000\u0000\u0014\u00a9\u0001\u0000\u0000\u0000\u0016"+
		"\u00b4\u0001\u0000\u0000\u0000\u0018\u00bf\u0001\u0000\u0000\u0000\u001a"+
		"\u00c2\u0001\u0000\u0000\u0000\u001c\u00d6\u0001\u0000\u0000\u0000\u001e"+
		"\u00e1\u0001\u0000\u0000\u0000 \u00e4\u0001\u0000\u0000\u0000\"\u00ef"+
		"\u0001\u0000\u0000\u0000$\u00fd\u0001\u0000\u0000\u0000&\u00ff\u0001\u0000"+
		"\u0000\u0000(\u0101\u0001\u0000\u0000\u0000*\u0104\u0001\u0000\u0000\u0000"+
		",\u0109\u0001\u0000\u0000\u0000.\u0114\u0001\u0000\u0000\u00000\u0118"+
		"\u0001\u0000\u0000\u000028\u0003\u0002\u0001\u000038\u0003\u0004\u0002"+
		"\u000048\u0003\u0006\u0003\u000058\u0003\b\u0004\u000068\u0003\n\u0005"+
		"\u000072\u0001\u0000\u0000\u000073\u0001\u0000\u0000\u000074\u0001\u0000"+
		"\u0000\u000075\u0001\u0000\u0000\u000076\u0001\u0000\u0000\u00008\u0001"+
		"\u0001\u0000\u0000\u00009:\u0005\r\u0000\u0000:;\u0005\u0001\u0000\u0000"+
		";<\u0005\u0013\u0000\u0000<=\u0003\"\u0011\u0000=>\u0005\u001a\u0000\u0000"+
		">?\u0003\u0014\n\u0000?L\u0001\u0000\u0000\u0000@A\u0005\r\u0000\u0000"+
		"AB\u0005\u0001\u0000\u0000BC\u0005\u0013\u0000\u0000CL\u0003\u000e\u0007"+
		"\u0000DE\u0005\r\u0000\u0000EF\u0005\u0001\u0000\u0000FG\u0005\u0013\u0000"+
		"\u0000GH\u0003\u000e\u0007\u0000HI\u0005\u001a\u0000\u0000IJ\u0003\u001a"+
		"\r\u0000JL\u0001\u0000\u0000\u0000K9\u0001\u0000\u0000\u0000K@\u0001\u0000"+
		"\u0000\u0000KD\u0001\u0000\u0000\u0000L\u0003\u0001\u0000\u0000\u0000"+
		"MN\u0005\u000e\u0000\u0000NO\u0005\u000f\u0000\u0000OP\u0003\"\u0011\u0000"+
		"PQ\u0005\u0002\u0000\u0000QR\u0003\f\u0006\u0000RS\u0005\u0003\u0000\u0000"+
		"ST\u0005\u0014\u0000\u0000TU\u0005\u0002\u0000\u0000UV\u0003\u0010\b\u0000"+
		"VW\u0005\u0003\u0000\u0000W\u0005\u0001\u0000\u0000\u0000XY\u0005\u0010"+
		"\u0000\u0000YZ\u0003\"\u0011\u0000Z[\u0005\u0011\u0000\u0000[\\\u0003"+
		"\u0012\t\u0000\\]\u0005\u001a\u0000\u0000]^\u0003 \u0010\u0000^_\u0005"+
		"\u0004\u0000\u0000_`\u0003$\u0012\u0000`g\u0001\u0000\u0000\u0000ab\u0005"+
		"\u0010\u0000\u0000bc\u0003\"\u0011\u0000cd\u0005\u0011\u0000\u0000de\u0003"+
		"\u0012\t\u0000eg\u0001\u0000\u0000\u0000fX\u0001\u0000\u0000\u0000fa\u0001"+
		"\u0000\u0000\u0000g\u0007\u0001\u0000\u0000\u0000hi\u0005\u0012\u0000"+
		"\u0000ij\u0005\u0013\u0000\u0000jk\u0003\"\u0011\u0000kl\u0005\u001a\u0000"+
		"\u0000lm\u0003\u0016\u000b\u0000mr\u0001\u0000\u0000\u0000no\u0005\u0012"+
		"\u0000\u0000op\u0005\u0013\u0000\u0000pr\u0003\"\u0011\u0000qh\u0001\u0000"+
		"\u0000\u0000qn\u0001\u0000\u0000\u0000r\t\u0001\u0000\u0000\u0000st\u0005"+
		"\u0018\u0000\u0000tu\u0005\u0019\u0000\u0000uv\u0003\"\u0011\u0000vw\u0005"+
		"\u0002\u0000\u0000wx\u0003\u001c\u000e\u0000x}\u0005\f\u0000\u0000yz\u0005"+
		"\u0005\u0000\u0000z|\u0003\u001c\u000e\u0000{y\u0001\u0000\u0000\u0000"+
		"|\u007f\u0001\u0000\u0000\u0000}{\u0001\u0000\u0000\u0000}~\u0001\u0000"+
		"\u0000\u0000~\u0080\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000"+
		"\u0080\u0081\u0005\u0003\u0000\u0000\u0081\u000b\u0001\u0000\u0000\u0000"+
		"\u0082\u0087\u0003 \u0010\u0000\u0083\u0084\u0005\u0005\u0000\u0000\u0084"+
		"\u0086\u0003 \u0010\u0000\u0085\u0083\u0001\u0000\u0000\u0000\u0086\u0089"+
		"\u0001\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0087\u0088"+
		"\u0001\u0000\u0000\u0000\u0088\r\u0001\u0000\u0000\u0000\u0089\u0087\u0001"+
		"\u0000\u0000\u0000\u008a\u008b\u0003\"\u0011\u0000\u008b\u0090\u0003\u0018"+
		"\f\u0000\u008c\u008d\u0005\u0005\u0000\u0000\u008d\u008e\u0003\"\u0011"+
		"\u0000\u008e\u008f\u0003\u0018\f\u0000\u008f\u0091\u0001\u0000\u0000\u0000"+
		"\u0090\u008c\u0001\u0000\u0000\u0000\u0091\u0092\u0001\u0000\u0000\u0000"+
		"\u0092\u0090\u0001\u0000\u0000\u0000\u0092\u0093\u0001\u0000\u0000\u0000"+
		"\u0093\u000f\u0001\u0000\u0000\u0000\u0094\u0099\u0003$\u0012\u0000\u0095"+
		"\u0096\u0005\u0005\u0000\u0000\u0096\u0098\u0003$\u0012\u0000\u0097\u0095"+
		"\u0001\u0000\u0000\u0000\u0098\u009b\u0001\u0000\u0000\u0000\u0099\u0097"+
		"\u0001\u0000\u0000\u0000\u0099\u009a\u0001\u0000\u0000\u0000\u009a\u0011"+
		"\u0001\u0000\u0000\u0000\u009b\u0099\u0001\u0000\u0000\u0000\u009c\u009d"+
		"\u0003 \u0010\u0000\u009d\u009e\u0005\u0004\u0000\u0000\u009e\u00a6\u0003"+
		"$\u0012\u0000\u009f\u00a0\u0005\u0005\u0000\u0000\u00a0\u00a1\u0003 \u0010"+
		"\u0000\u00a1\u00a2\u0005\u0004\u0000\u0000\u00a2\u00a3\u0003$\u0012\u0000"+
		"\u00a3\u00a5\u0001\u0000\u0000\u0000\u00a4\u009f\u0001\u0000\u0000\u0000"+
		"\u00a5\u00a8\u0001\u0000\u0000\u0000\u00a6\u00a4\u0001\u0000\u0000\u0000"+
		"\u00a6\u00a7\u0001\u0000\u0000\u0000\u00a7\u0013\u0001\u0000\u0000\u0000"+
		"\u00a8\u00a6\u0001\u0000\u0000\u0000\u00a9\u00aa\u0003 \u0010\u0000\u00aa"+
		"\u00ab\u0003&\u0013\u0000\u00ab\u00b1\u0003$\u0012\u0000\u00ac\u00ad\u0003"+
		"(\u0014\u0000\u00ad\u00ae\u0003\u0014\n\u0000\u00ae\u00b0\u0001\u0000"+
		"\u0000\u0000\u00af\u00ac\u0001\u0000\u0000\u0000\u00b0\u00b3\u0001\u0000"+
		"\u0000\u0000\u00b1\u00af\u0001\u0000\u0000\u0000\u00b1\u00b2\u0001\u0000"+
		"\u0000\u0000\u00b2\u0015\u0001\u0000\u0000\u0000\u00b3\u00b1\u0001\u0000"+
		"\u0000\u0000\u00b4\u00b5\u0003 \u0010\u0000\u00b5\u00b6\u0005\u0004\u0000"+
		"\u0000\u00b6\u00bc\u0003$\u0012\u0000\u00b7\u00b8\u0003(\u0014\u0000\u00b8"+
		"\u00b9\u0003\u0016\u000b\u0000\u00b9\u00bb\u0001\u0000\u0000\u0000\u00ba"+
		"\u00b7\u0001\u0000\u0000\u0000\u00bb\u00be\u0001\u0000\u0000\u0000\u00bc"+
		"\u00ba\u0001\u0000\u0000\u0000\u00bc\u00bd\u0001\u0000\u0000\u0000\u00bd"+
		"\u0017\u0001\u0000\u0000\u0000\u00be\u00bc\u0001\u0000\u0000\u0000\u00bf"+
		"\u00c0\u0003.\u0017\u0000\u00c0\u0019\u0001\u0000\u0000\u0000\u00c1\u00c3"+
		"\u0003\u0018\f\u0000\u00c2\u00c1\u0001\u0000\u0000\u0000\u00c3\u00c4\u0001"+
		"\u0000\u0000\u0000\u00c4\u00c2\u0001\u0000\u0000\u0000\u00c4\u00c5\u0001"+
		"\u0000\u0000\u0000\u00c5\u00c7\u0001\u0000\u0000\u0000\u00c6\u00c8\u0005"+
		"\u0006\u0000\u0000\u00c7\u00c6\u0001\u0000\u0000\u0000\u00c8\u00c9\u0001"+
		"\u0000\u0000\u0000\u00c9\u00c7\u0001\u0000\u0000\u0000\u00c9\u00ca\u0001"+
		"\u0000\u0000\u0000\u00ca\u00cb\u0001\u0000\u0000\u0000\u00cb\u00cc\u0003"+
		" \u0010\u0000\u00cc\u00cd\u0003&\u0013\u0000\u00cd\u00d3\u0003$\u0012"+
		"\u0000\u00ce\u00cf\u0003(\u0014\u0000\u00cf\u00d0\u0003\u001a\r\u0000"+
		"\u00d0\u00d2\u0001\u0000\u0000\u0000\u00d1\u00ce\u0001\u0000\u0000\u0000"+
		"\u00d2\u00d5\u0001\u0000\u0000\u0000\u00d3\u00d1\u0001\u0000\u0000\u0000"+
		"\u00d3\u00d4\u0001\u0000\u0000\u0000\u00d4\u001b\u0001\u0000\u0000\u0000"+
		"\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d6\u00d7\u0003 \u0010\u0000\u00d7"+
		"\u00d8\u0003\u001e\u000f\u0000\u00d8\u001d\u0001\u0000\u0000\u0000\u00d9"+
		"\u00e2\u0005\u001b\u0000\u0000\u00da\u00e2\u0005\u001c\u0000\u0000\u00db"+
		"\u00dc\u0005\u001d\u0000\u0000\u00dc\u00dd\u0005\u0002\u0000\u0000\u00dd"+
		"\u00de\u0003*\u0015\u0000\u00de\u00df\u0005\u0003\u0000\u0000\u00df\u00e2"+
		"\u0001\u0000\u0000\u0000\u00e0\u00e2\u0005\u001e\u0000\u0000\u00e1\u00d9"+
		"\u0001\u0000\u0000\u0000\u00e1\u00da\u0001\u0000\u0000\u0000\u00e1\u00db"+
		"\u0001\u0000\u0000\u0000\u00e1\u00e0\u0001\u0000\u0000\u0000\u00e2\u001f"+
		"\u0001\u0000\u0000\u0000\u00e3\u00e5\u0005\u001f\u0000\u0000\u00e4\u00e3"+
		"\u0001\u0000\u0000\u0000\u00e5\u00e6\u0001\u0000\u0000\u0000\u00e6\u00e4"+
		"\u0001\u0000\u0000\u0000\u00e6\u00e7\u0001\u0000\u0000\u0000\u00e7\u00eb"+
		"\u0001\u0000\u0000\u0000\u00e8\u00ea\u0005 \u0000\u0000\u00e9\u00e8\u0001"+
		"\u0000\u0000\u0000\u00ea\u00ed\u0001\u0000\u0000\u0000\u00eb\u00e9\u0001"+
		"\u0000\u0000\u0000\u00eb\u00ec\u0001\u0000\u0000\u0000\u00ec!\u0001\u0000"+
		"\u0000\u0000\u00ed\u00eb\u0001\u0000\u0000\u0000\u00ee\u00f0\u0005\u001f"+
		"\u0000\u0000\u00ef\u00ee\u0001\u0000\u0000\u0000\u00f0\u00f1\u0001\u0000"+
		"\u0000\u0000\u00f1\u00ef\u0001\u0000\u0000\u0000\u00f1\u00f2\u0001\u0000"+
		"\u0000\u0000\u00f2\u00f6\u0001\u0000\u0000\u0000\u00f3\u00f5\u0005 \u0000"+
		"\u0000\u00f4\u00f3\u0001\u0000\u0000\u0000\u00f5\u00f8\u0001\u0000\u0000"+
		"\u0000\u00f6\u00f4\u0001\u0000\u0000\u0000\u00f6\u00f7\u0001\u0000\u0000"+
		"\u0000\u00f7#\u0001\u0000\u0000\u0000\u00f8\u00f6\u0001\u0000\u0000\u0000"+
		"\u00f9\u00fe\u0003*\u0015\u0000\u00fa\u00fe\u0003.\u0017\u0000\u00fb\u00fe"+
		"\u00030\u0018\u0000\u00fc\u00fe\u0003,\u0016\u0000\u00fd\u00f9\u0001\u0000"+
		"\u0000\u0000\u00fd\u00fa\u0001\u0000\u0000\u0000\u00fd\u00fb\u0001\u0000"+
		"\u0000\u0000\u00fd\u00fc\u0001\u0000\u0000\u0000\u00fe%\u0001\u0000\u0000"+
		"\u0000\u00ff\u0100\u0007\u0000\u0000\u0000\u0100\'\u0001\u0000\u0000\u0000"+
		"\u0101\u0102\u0007\u0001\u0000\u0000\u0102)\u0001\u0000\u0000\u0000\u0103"+
		"\u0105\u0005 \u0000\u0000\u0104\u0103\u0001\u0000\u0000\u0000\u0105\u0106"+
		"\u0001\u0000\u0000\u0000\u0106\u0104\u0001\u0000\u0000\u0000\u0106\u0107"+
		"\u0001\u0000\u0000\u0000\u0107+\u0001\u0000\u0000\u0000\u0108\u010a\u0005"+
		" \u0000\u0000\u0109\u0108\u0001\u0000\u0000\u0000\u010a\u010b\u0001\u0000"+
		"\u0000\u0000\u010b\u0109\u0001\u0000\u0000\u0000\u010b\u010c\u0001\u0000"+
		"\u0000\u0000\u010c\u010d\u0001\u0000\u0000\u0000\u010d\u010f\u0005\u0006"+
		"\u0000\u0000\u010e\u0110\u0005 \u0000\u0000\u010f\u010e\u0001\u0000\u0000"+
		"\u0000\u0110\u0111\u0001\u0000\u0000\u0000\u0111\u010f\u0001\u0000\u0000"+
		"\u0000\u0111\u0112\u0001\u0000\u0000\u0000\u0112-\u0001\u0000\u0000\u0000"+
		"\u0113\u0115\u0005\u001f\u0000\u0000\u0114\u0113\u0001\u0000\u0000\u0000"+
		"\u0115\u0116\u0001\u0000\u0000\u0000\u0116\u0114\u0001\u0000\u0000\u0000"+
		"\u0116\u0117\u0001\u0000\u0000\u0000\u0117/\u0001\u0000\u0000\u0000\u0118"+
		"\u0119\u0005 \u0000\u0000\u0119\u011a\u0005 \u0000\u0000\u011a\u011b\u0005"+
		" \u0000\u0000\u011b\u011c\u0005 \u0000\u0000\u011c\u011d\u0005\u000b\u0000"+
		"\u0000\u011d\u011e\u0005 \u0000\u0000\u011e\u011f\u0005 \u0000\u0000\u011f"+
		"\u0120\u0005\u000b\u0000\u0000\u0120\u0121\u0005 \u0000\u0000\u0121\u0122"+
		"\u0005 \u0000\u0000\u01221\u0001\u0000\u0000\u0000\u00187Kfq}\u0087\u0092"+
		"\u0099\u00a6\u00b1\u00bc\u00c4\u00c9\u00d3\u00e1\u00e6\u00eb\u00f1\u00f6"+
		"\u00fd\u0106\u010b\u0111\u0116";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
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
		T__9=10, T__10=11, SELECT=12, INSERT=13, INTO=14, UPDATE=15, SET=16, DELETE=17, 
		FROM=18, VALUES=19, AND=20, OR=21, XOR=22, CREATE=23, TABLE=24, WHERE=25, 
		INT=26, FLOAT=27, VARCHAR=28, DATE=29, LETTER=30, DIGIT=31, WS=32;
	public static final int
		RULE_query = 0, RULE_selectStatement = 1, RULE_insertStatement = 2, RULE_updateStatement = 3, 
		RULE_deleteStatement = 4, RULE_createTableStatement = 5, RULE_columnList = 6, 
		RULE_tableList = 7, RULE_valueList = 8, RULE_updateList = 9, RULE_conditionList = 10, 
		RULE_columnDefinition = 11, RULE_dataType = 12, RULE_columnName = 13, 
		RULE_tableName = 14, RULE_value = 15, RULE_operator = 16, RULE_logicalOperator = 17;
	private static String[] makeRuleNames() {
		return new String[] {
			"query", "selectStatement", "insertStatement", "updateStatement", "deleteStatement", 
			"createTableStatement", "columnList", "tableList", "valueList", "updateList", 
			"conditionList", "columnDefinition", "dataType", "columnName", "tableName", 
			"value", "operator", "logicalOperator"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'*'", "'('", "','", "')'", "'='", "'-'", "'.'", "'<'", "'>'", 
			"'<='", "'>='", "'SELECT'", "'INSERT'", "'INTO'", "'UPDATE'", "'SET'", 
			"'DELETE'", "'FROM'", "'VALUES'", "'AND'", "'OR'", "'XOR'", "'CREATE'", 
			"'TABLE'", "'WHERE'", "'INT'", "'FLOAT'", "'VARCHAR'", "'Date'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"SELECT", "INSERT", "INTO", "UPDATE", "SET", "DELETE", "FROM", "VALUES", 
			"AND", "OR", "XOR", "CREATE", "TABLE", "WHERE", "INT", "FLOAT", "VARCHAR", 
			"DATE", "LETTER", "DIGIT", "WS"
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
			setState(41);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SELECT:
				enterOuterAlt(_localctx, 1);
				{
				setState(36);
				selectStatement();
				}
				break;
			case INSERT:
				enterOuterAlt(_localctx, 2);
				{
				setState(37);
				insertStatement();
				}
				break;
			case UPDATE:
				enterOuterAlt(_localctx, 3);
				{
				setState(38);
				updateStatement();
				}
				break;
			case DELETE:
				enterOuterAlt(_localctx, 4);
				{
				setState(39);
				deleteStatement();
				}
				break;
			case CREATE:
				enterOuterAlt(_localctx, 5);
				{
				setState(40);
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
		public ColumnListContext columnList() {
			return getRuleContext(ColumnListContext.class,0);
		}
		public TerminalNode FROM() { return getToken(SQLParser.FROM, 0); }
		public TableListContext tableList() {
			return getRuleContext(TableListContext.class,0);
		}
		public TerminalNode WHERE() { return getToken(SQLParser.WHERE, 0); }
		public ConditionListContext conditionList() {
			return getRuleContext(ConditionListContext.class,0);
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
			setState(66);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				match(SELECT);
				setState(44);
				columnList();
				setState(45);
				match(FROM);
				setState(46);
				tableList();
				setState(47);
				match(WHERE);
				setState(48);
				conditionList();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(50);
				match(SELECT);
				setState(51);
				columnList();
				setState(52);
				match(FROM);
				setState(53);
				tableList();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(55);
				match(SELECT);
				setState(56);
				match(T__0);
				setState(57);
				match(FROM);
				setState(58);
				tableList();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(59);
				match(SELECT);
				setState(60);
				match(T__0);
				setState(61);
				match(FROM);
				setState(62);
				tableList();
				setState(63);
				match(WHERE);
				setState(64);
				conditionList();
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
			setState(68);
			match(INSERT);
			setState(69);
			match(INTO);
			setState(70);
			tableName();
			setState(71);
			columnList();
			setState(72);
			match(VALUES);
			setState(73);
			valueList();
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
		public ConditionListContext conditionList() {
			return getRuleContext(ConditionListContext.class,0);
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
			setState(87);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(75);
				match(UPDATE);
				setState(76);
				tableName();
				setState(77);
				match(SET);
				setState(78);
				updateList();
				setState(79);
				match(WHERE);
				setState(80);
				conditionList();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(82);
				match(UPDATE);
				setState(83);
				tableName();
				setState(84);
				match(SET);
				setState(85);
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
		public ConditionListContext conditionList() {
			return getRuleContext(ConditionListContext.class,0);
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
			setState(98);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(89);
				match(DELETE);
				setState(90);
				match(FROM);
				setState(91);
				tableName();
				setState(92);
				match(WHERE);
				setState(93);
				conditionList();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(95);
				match(DELETE);
				setState(96);
				match(FROM);
				setState(97);
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
			setState(100);
			match(CREATE);
			setState(101);
			match(TABLE);
			setState(102);
			tableName();
			setState(103);
			match(T__1);
			setState(104);
			columnDefinition();
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(105);
				match(T__2);
				setState(106);
				columnDefinition();
				}
				}
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(112);
			match(T__3);
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
			setState(114);
			columnName();
			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(115);
				match(T__2);
				setState(116);
				columnName();
				}
				}
				setState(121);
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
			setState(122);
			tableName();
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(123);
				match(T__2);
				setState(124);
				tableName();
				}
				}
				setState(129);
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
			setState(130);
			value();
			setState(135);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(131);
				match(T__2);
				setState(132);
				value();
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
			setState(138);
			columnName();
			setState(139);
			match(T__4);
			setState(140);
			value();
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(141);
				match(T__2);
				setState(142);
				columnName();
				setState(143);
				match(T__4);
				setState(144);
				value();
				}
				}
				setState(150);
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
			setState(151);
			columnName();
			setState(152);
			operator();
			setState(153);
			value();
			setState(159);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(154);
					logicalOperator();
					setState(155);
					conditionList();
					}
					} 
				}
				setState(161);
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
		enterRule(_localctx, 22, RULE_columnDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			columnName();
			setState(163);
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
		public List<TerminalNode> DIGIT() { return getTokens(SQLParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(SQLParser.DIGIT, i);
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
		enterRule(_localctx, 24, RULE_dataType);
		int _la;
		try {
			setState(176);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(165);
				match(INT);
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(166);
				match(FLOAT);
				}
				break;
			case VARCHAR:
				enterOuterAlt(_localctx, 3);
				{
				setState(167);
				match(VARCHAR);
				setState(168);
				match(T__1);
				setState(170); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(169);
					match(DIGIT);
					}
					}
					setState(172); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==DIGIT );
				setState(174);
				match(T__3);
				}
				break;
			case DATE:
				enterOuterAlt(_localctx, 4);
				{
				setState(175);
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
		enterRule(_localctx, 26, RULE_columnName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(178);
				match(LETTER);
				}
				}
				setState(181); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==LETTER );
			setState(186);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DIGIT) {
				{
				{
				setState(183);
				match(DIGIT);
				}
				}
				setState(188);
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
		enterRule(_localctx, 28, RULE_tableName);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(190); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(189);
					match(LETTER);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(192); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			} while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER );
			setState(197);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DIGIT) {
				{
				{
				setState(194);
				match(DIGIT);
				}
				}
				setState(199);
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
		public List<TerminalNode> DIGIT() { return getTokens(SQLParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(SQLParser.DIGIT, i);
		}
		public List<TerminalNode> LETTER() { return getTokens(SQLParser.LETTER); }
		public TerminalNode LETTER(int i) {
			return getToken(SQLParser.LETTER, i);
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
		enterRule(_localctx, 30, RULE_value);
		int _la;
		try {
			setState(231);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(201); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(200);
					match(DIGIT);
					}
					}
					setState(203); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==DIGIT );
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(206); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(205);
					match(LETTER);
					}
					}
					setState(208); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==LETTER );
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(210);
				match(DIGIT);
				setState(211);
				match(DIGIT);
				setState(212);
				match(DIGIT);
				setState(213);
				match(DIGIT);
				setState(214);
				match(T__5);
				setState(215);
				match(DIGIT);
				setState(216);
				match(DIGIT);
				setState(217);
				match(T__5);
				setState(218);
				match(DIGIT);
				setState(219);
				match(DIGIT);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(221); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(220);
					match(DIGIT);
					}
					}
					setState(223); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==DIGIT );
				setState(225);
				match(T__6);
				setState(227); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(226);
					match(DIGIT);
					}
					}
					setState(229); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==DIGIT );
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
		enterRule(_localctx, 32, RULE_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 3872L) != 0)) ) {
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
		enterRule(_localctx, 34, RULE_logicalOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 7340032L) != 0)) ) {
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

	public static final String _serializedATN =
		"\u0004\u0001 \u00ee\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000*\b\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001C\b\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003X\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0003\u0004c\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005l\b\u0005\n\u0005\f\u0005"+
		"o\t\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0005\u0006v\b\u0006\n\u0006\f\u0006y\t\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0005\u0007~\b\u0007\n\u0007\f\u0007\u0081\t\u0007\u0001"+
		"\b\u0001\b\u0001\b\u0005\b\u0086\b\b\n\b\f\b\u0089\t\b\u0001\t\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0005\t\u0093\b\t\n\t"+
		"\f\t\u0096\t\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0005\n"+
		"\u009e\b\n\n\n\f\n\u00a1\t\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0004\f\u00ab\b\f\u000b\f\f\f\u00ac"+
		"\u0001\f\u0001\f\u0003\f\u00b1\b\f\u0001\r\u0004\r\u00b4\b\r\u000b\r\f"+
		"\r\u00b5\u0001\r\u0005\r\u00b9\b\r\n\r\f\r\u00bc\t\r\u0001\u000e\u0004"+
		"\u000e\u00bf\b\u000e\u000b\u000e\f\u000e\u00c0\u0001\u000e\u0005\u000e"+
		"\u00c4\b\u000e\n\u000e\f\u000e\u00c7\t\u000e\u0001\u000f\u0004\u000f\u00ca"+
		"\b\u000f\u000b\u000f\f\u000f\u00cb\u0001\u000f\u0004\u000f\u00cf\b\u000f"+
		"\u000b\u000f\f\u000f\u00d0\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0004\u000f\u00de\b\u000f\u000b\u000f\f\u000f\u00df\u0001"+
		"\u000f\u0001\u000f\u0004\u000f\u00e4\b\u000f\u000b\u000f\f\u000f\u00e5"+
		"\u0003\u000f\u00e8\b\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0000\u0000\u0012\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"\u0000\u0002\u0002\u0000"+
		"\u0005\u0005\b\u000b\u0001\u0000\u0014\u0016\u00f9\u0000)\u0001\u0000"+
		"\u0000\u0000\u0002B\u0001\u0000\u0000\u0000\u0004D\u0001\u0000\u0000\u0000"+
		"\u0006W\u0001\u0000\u0000\u0000\bb\u0001\u0000\u0000\u0000\nd\u0001\u0000"+
		"\u0000\u0000\fr\u0001\u0000\u0000\u0000\u000ez\u0001\u0000\u0000\u0000"+
		"\u0010\u0082\u0001\u0000\u0000\u0000\u0012\u008a\u0001\u0000\u0000\u0000"+
		"\u0014\u0097\u0001\u0000\u0000\u0000\u0016\u00a2\u0001\u0000\u0000\u0000"+
		"\u0018\u00b0\u0001\u0000\u0000\u0000\u001a\u00b3\u0001\u0000\u0000\u0000"+
		"\u001c\u00be\u0001\u0000\u0000\u0000\u001e\u00e7\u0001\u0000\u0000\u0000"+
		" \u00e9\u0001\u0000\u0000\u0000\"\u00eb\u0001\u0000\u0000\u0000$*\u0003"+
		"\u0002\u0001\u0000%*\u0003\u0004\u0002\u0000&*\u0003\u0006\u0003\u0000"+
		"\'*\u0003\b\u0004\u0000(*\u0003\n\u0005\u0000)$\u0001\u0000\u0000\u0000"+
		")%\u0001\u0000\u0000\u0000)&\u0001\u0000\u0000\u0000)\'\u0001\u0000\u0000"+
		"\u0000)(\u0001\u0000\u0000\u0000*\u0001\u0001\u0000\u0000\u0000+,\u0005"+
		"\f\u0000\u0000,-\u0003\f\u0006\u0000-.\u0005\u0012\u0000\u0000./\u0003"+
		"\u000e\u0007\u0000/0\u0005\u0019\u0000\u000001\u0003\u0014\n\u00001C\u0001"+
		"\u0000\u0000\u000023\u0005\f\u0000\u000034\u0003\f\u0006\u000045\u0005"+
		"\u0012\u0000\u000056\u0003\u000e\u0007\u00006C\u0001\u0000\u0000\u0000"+
		"78\u0005\f\u0000\u000089\u0005\u0001\u0000\u00009:\u0005\u0012\u0000\u0000"+
		":C\u0003\u000e\u0007\u0000;<\u0005\f\u0000\u0000<=\u0005\u0001\u0000\u0000"+
		"=>\u0005\u0012\u0000\u0000>?\u0003\u000e\u0007\u0000?@\u0005\u0019\u0000"+
		"\u0000@A\u0003\u0014\n\u0000AC\u0001\u0000\u0000\u0000B+\u0001\u0000\u0000"+
		"\u0000B2\u0001\u0000\u0000\u0000B7\u0001\u0000\u0000\u0000B;\u0001\u0000"+
		"\u0000\u0000C\u0003\u0001\u0000\u0000\u0000DE\u0005\r\u0000\u0000EF\u0005"+
		"\u000e\u0000\u0000FG\u0003\u001c\u000e\u0000GH\u0003\f\u0006\u0000HI\u0005"+
		"\u0013\u0000\u0000IJ\u0003\u0010\b\u0000J\u0005\u0001\u0000\u0000\u0000"+
		"KL\u0005\u000f\u0000\u0000LM\u0003\u001c\u000e\u0000MN\u0005\u0010\u0000"+
		"\u0000NO\u0003\u0012\t\u0000OP\u0005\u0019\u0000\u0000PQ\u0003\u0014\n"+
		"\u0000QX\u0001\u0000\u0000\u0000RS\u0005\u000f\u0000\u0000ST\u0003\u001c"+
		"\u000e\u0000TU\u0005\u0010\u0000\u0000UV\u0003\u0012\t\u0000VX\u0001\u0000"+
		"\u0000\u0000WK\u0001\u0000\u0000\u0000WR\u0001\u0000\u0000\u0000X\u0007"+
		"\u0001\u0000\u0000\u0000YZ\u0005\u0011\u0000\u0000Z[\u0005\u0012\u0000"+
		"\u0000[\\\u0003\u001c\u000e\u0000\\]\u0005\u0019\u0000\u0000]^\u0003\u0014"+
		"\n\u0000^c\u0001\u0000\u0000\u0000_`\u0005\u0011\u0000\u0000`a\u0005\u0012"+
		"\u0000\u0000ac\u0003\u001c\u000e\u0000bY\u0001\u0000\u0000\u0000b_\u0001"+
		"\u0000\u0000\u0000c\t\u0001\u0000\u0000\u0000de\u0005\u0017\u0000\u0000"+
		"ef\u0005\u0018\u0000\u0000fg\u0003\u001c\u000e\u0000gh\u0005\u0002\u0000"+
		"\u0000hm\u0003\u0016\u000b\u0000ij\u0005\u0003\u0000\u0000jl\u0003\u0016"+
		"\u000b\u0000ki\u0001\u0000\u0000\u0000lo\u0001\u0000\u0000\u0000mk\u0001"+
		"\u0000\u0000\u0000mn\u0001\u0000\u0000\u0000np\u0001\u0000\u0000\u0000"+
		"om\u0001\u0000\u0000\u0000pq\u0005\u0004\u0000\u0000q\u000b\u0001\u0000"+
		"\u0000\u0000rw\u0003\u001a\r\u0000st\u0005\u0003\u0000\u0000tv\u0003\u001a"+
		"\r\u0000us\u0001\u0000\u0000\u0000vy\u0001\u0000\u0000\u0000wu\u0001\u0000"+
		"\u0000\u0000wx\u0001\u0000\u0000\u0000x\r\u0001\u0000\u0000\u0000yw\u0001"+
		"\u0000\u0000\u0000z\u007f\u0003\u001c\u000e\u0000{|\u0005\u0003\u0000"+
		"\u0000|~\u0003\u001c\u000e\u0000}{\u0001\u0000\u0000\u0000~\u0081\u0001"+
		"\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000\u007f\u0080\u0001\u0000"+
		"\u0000\u0000\u0080\u000f\u0001\u0000\u0000\u0000\u0081\u007f\u0001\u0000"+
		"\u0000\u0000\u0082\u0087\u0003\u001e\u000f\u0000\u0083\u0084\u0005\u0003"+
		"\u0000\u0000\u0084\u0086\u0003\u001e\u000f\u0000\u0085\u0083\u0001\u0000"+
		"\u0000\u0000\u0086\u0089\u0001\u0000\u0000\u0000\u0087\u0085\u0001\u0000"+
		"\u0000\u0000\u0087\u0088\u0001\u0000\u0000\u0000\u0088\u0011\u0001\u0000"+
		"\u0000\u0000\u0089\u0087\u0001\u0000\u0000\u0000\u008a\u008b\u0003\u001a"+
		"\r\u0000\u008b\u008c\u0005\u0005\u0000\u0000\u008c\u0094\u0003\u001e\u000f"+
		"\u0000\u008d\u008e\u0005\u0003\u0000\u0000\u008e\u008f\u0003\u001a\r\u0000"+
		"\u008f\u0090\u0005\u0005\u0000\u0000\u0090\u0091\u0003\u001e\u000f\u0000"+
		"\u0091\u0093\u0001\u0000\u0000\u0000\u0092\u008d\u0001\u0000\u0000\u0000"+
		"\u0093\u0096\u0001\u0000\u0000\u0000\u0094\u0092\u0001\u0000\u0000\u0000"+
		"\u0094\u0095\u0001\u0000\u0000\u0000\u0095\u0013\u0001\u0000\u0000\u0000"+
		"\u0096\u0094\u0001\u0000\u0000\u0000\u0097\u0098\u0003\u001a\r\u0000\u0098"+
		"\u0099\u0003 \u0010\u0000\u0099\u009f\u0003\u001e\u000f\u0000\u009a\u009b"+
		"\u0003\"\u0011\u0000\u009b\u009c\u0003\u0014\n\u0000\u009c\u009e\u0001"+
		"\u0000\u0000\u0000\u009d\u009a\u0001\u0000\u0000\u0000\u009e\u00a1\u0001"+
		"\u0000\u0000\u0000\u009f\u009d\u0001\u0000\u0000\u0000\u009f\u00a0\u0001"+
		"\u0000\u0000\u0000\u00a0\u0015\u0001\u0000\u0000\u0000\u00a1\u009f\u0001"+
		"\u0000\u0000\u0000\u00a2\u00a3\u0003\u001a\r\u0000\u00a3\u00a4\u0003\u0018"+
		"\f\u0000\u00a4\u0017\u0001\u0000\u0000\u0000\u00a5\u00b1\u0005\u001a\u0000"+
		"\u0000\u00a6\u00b1\u0005\u001b\u0000\u0000\u00a7\u00a8\u0005\u001c\u0000"+
		"\u0000\u00a8\u00aa\u0005\u0002\u0000\u0000\u00a9\u00ab\u0005\u001f\u0000"+
		"\u0000\u00aa\u00a9\u0001\u0000\u0000\u0000\u00ab\u00ac\u0001\u0000\u0000"+
		"\u0000\u00ac\u00aa\u0001\u0000\u0000\u0000\u00ac\u00ad\u0001\u0000\u0000"+
		"\u0000\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae\u00b1\u0005\u0004\u0000"+
		"\u0000\u00af\u00b1\u0005\u001d\u0000\u0000\u00b0\u00a5\u0001\u0000\u0000"+
		"\u0000\u00b0\u00a6\u0001\u0000\u0000\u0000\u00b0\u00a7\u0001\u0000\u0000"+
		"\u0000\u00b0\u00af\u0001\u0000\u0000\u0000\u00b1\u0019\u0001\u0000\u0000"+
		"\u0000\u00b2\u00b4\u0005\u001e\u0000\u0000\u00b3\u00b2\u0001\u0000\u0000"+
		"\u0000\u00b4\u00b5\u0001\u0000\u0000\u0000\u00b5\u00b3\u0001\u0000\u0000"+
		"\u0000\u00b5\u00b6\u0001\u0000\u0000\u0000\u00b6\u00ba\u0001\u0000\u0000"+
		"\u0000\u00b7\u00b9\u0005\u001f\u0000\u0000\u00b8\u00b7\u0001\u0000\u0000"+
		"\u0000\u00b9\u00bc\u0001\u0000\u0000\u0000\u00ba\u00b8\u0001\u0000\u0000"+
		"\u0000\u00ba\u00bb\u0001\u0000\u0000\u0000\u00bb\u001b\u0001\u0000\u0000"+
		"\u0000\u00bc\u00ba\u0001\u0000\u0000\u0000\u00bd\u00bf\u0005\u001e\u0000"+
		"\u0000\u00be\u00bd\u0001\u0000\u0000\u0000\u00bf\u00c0\u0001\u0000\u0000"+
		"\u0000\u00c0\u00be\u0001\u0000\u0000\u0000\u00c0\u00c1\u0001\u0000\u0000"+
		"\u0000\u00c1\u00c5\u0001\u0000\u0000\u0000\u00c2\u00c4\u0005\u001f\u0000"+
		"\u0000\u00c3\u00c2\u0001\u0000\u0000\u0000\u00c4\u00c7\u0001\u0000\u0000"+
		"\u0000\u00c5\u00c3\u0001\u0000\u0000\u0000\u00c5\u00c6\u0001\u0000\u0000"+
		"\u0000\u00c6\u001d\u0001\u0000\u0000\u0000\u00c7\u00c5\u0001\u0000\u0000"+
		"\u0000\u00c8\u00ca\u0005\u001f\u0000\u0000\u00c9\u00c8\u0001\u0000\u0000"+
		"\u0000\u00ca\u00cb\u0001\u0000\u0000\u0000\u00cb\u00c9\u0001\u0000\u0000"+
		"\u0000\u00cb\u00cc\u0001\u0000\u0000\u0000\u00cc\u00e8\u0001\u0000\u0000"+
		"\u0000\u00cd\u00cf\u0005\u001e\u0000\u0000\u00ce\u00cd\u0001\u0000\u0000"+
		"\u0000\u00cf\u00d0\u0001\u0000\u0000\u0000\u00d0\u00ce\u0001\u0000\u0000"+
		"\u0000\u00d0\u00d1\u0001\u0000\u0000\u0000\u00d1\u00e8\u0001\u0000\u0000"+
		"\u0000\u00d2\u00d3\u0005\u001f\u0000\u0000\u00d3\u00d4\u0005\u001f\u0000"+
		"\u0000\u00d4\u00d5\u0005\u001f\u0000\u0000\u00d5\u00d6\u0005\u001f\u0000"+
		"\u0000\u00d6\u00d7\u0005\u0006\u0000\u0000\u00d7\u00d8\u0005\u001f\u0000"+
		"\u0000\u00d8\u00d9\u0005\u001f\u0000\u0000\u00d9\u00da\u0005\u0006\u0000"+
		"\u0000\u00da\u00db\u0005\u001f\u0000\u0000\u00db\u00e8\u0005\u001f\u0000"+
		"\u0000\u00dc\u00de\u0005\u001f\u0000\u0000\u00dd\u00dc\u0001\u0000\u0000"+
		"\u0000\u00de\u00df\u0001\u0000\u0000\u0000\u00df\u00dd\u0001\u0000\u0000"+
		"\u0000\u00df\u00e0\u0001\u0000\u0000\u0000\u00e0\u00e1\u0001\u0000\u0000"+
		"\u0000\u00e1\u00e3\u0005\u0007\u0000\u0000\u00e2\u00e4\u0005\u001f\u0000"+
		"\u0000\u00e3\u00e2\u0001\u0000\u0000\u0000\u00e4\u00e5\u0001\u0000\u0000"+
		"\u0000\u00e5\u00e3\u0001\u0000\u0000\u0000\u00e5\u00e6\u0001\u0000\u0000"+
		"\u0000\u00e6\u00e8\u0001\u0000\u0000\u0000\u00e7\u00c9\u0001\u0000\u0000"+
		"\u0000\u00e7\u00ce\u0001\u0000\u0000\u0000\u00e7\u00d2\u0001\u0000\u0000"+
		"\u0000\u00e7\u00dd\u0001\u0000\u0000\u0000\u00e8\u001f\u0001\u0000\u0000"+
		"\u0000\u00e9\u00ea\u0007\u0000\u0000\u0000\u00ea!\u0001\u0000\u0000\u0000"+
		"\u00eb\u00ec\u0007\u0001\u0000\u0000\u00ec#\u0001\u0000\u0000\u0000\u0015"+
		")BWbmw\u007f\u0087\u0094\u009f\u00ac\u00b0\u00b5\u00ba\u00c0\u00c5\u00cb"+
		"\u00d0\u00df\u00e5\u00e7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
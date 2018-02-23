package cn.zh.blueshit.sql;

import com.alibaba.druid.sql.ast.SQLStatement;

public class SQLParsedResult {

	private SQLStatement stmt;

	private SqlType type;

	private RouterContext routerContext;

	private MergeContext mergeContext;

	public SQLParsedResult(SqlType type, SQLStatement stmt) throws ShardParseException {
		this.stmt = stmt;
		this.type = type;
		this.routerContext = new RouterContext();
		this.mergeContext = new MergeContext();
	}

	public SqlType getType() {
		return type;
	}

	public SQLStatement getStmt() {
		return stmt;
	}

	public RouterContext getRouterContext() {
		return routerContext;
	}

	public MergeContext getMergeContext() {
		return mergeContext;
	}
}

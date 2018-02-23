package cn.zh.blueshit.sql;

public interface SQLRewrite{
	
	public String rewrite(SQLParsedResult pr,String logicalTable, String physicalTable);
	
}

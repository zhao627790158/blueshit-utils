package cn.zh.blueshit.sql;

import java.util.HashSet;
import java.util.Set;

public class RouterContext {

	private Set<String> tableSet = new HashSet<String>();

	private SQLHint sqlhint;
	
	public Set<String> getTableSet() {
		return tableSet;
	}

	public void setTableSets(Set<String> tableSet) {
		this.tableSet = tableSet;
	}

	public SQLHint getSqlhint() {
		return sqlhint;
	}

	public void setSqlhint(SQLHint sqlhint) {
		this.sqlhint = sqlhint;
	}

	public void setTableSet(Set<String> tableSet) {
		this.tableSet = tableSet;
	}
}

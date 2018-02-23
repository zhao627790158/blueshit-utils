package cn.blueshit.cn.test.sql;


import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by wangjinsi on 2015-08-14.
 */
public class Sql implements Cloneable {

    private String text;

    private boolean prepared;

    private SortedMap<Integer, Object> parameters;

    private SqlType sqlType;

    public Sql(String text, boolean prepared, SortedMap<Integer, Object> parameters) {
        this.text = text;
        this.parameters = parameters;
        this.prepared = prepared;
        this.sqlType = SqlType.getSqlType(text);
    }

    @Override
    public Sql clone() {
        return new Sql(
                text,
                prepared,
                parameters == null ? null : new TreeMap<Integer, Object>(this.parameters));
    }

    public String getText() {
        return text;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public SortedMap<Integer, Object> getParameters() {
        return parameters == null ? null : new TreeMap<Integer, Object>(parameters);
    }


    public SqlType getSqlType() {
        return sqlType;
    }

    @Override
    public String toString() {
        return "Sql{" +
                "text='" + text + '\'' +
                ", prepared=" + prepared +
                ", parameters=" + parameters +
                '}';
    }
}

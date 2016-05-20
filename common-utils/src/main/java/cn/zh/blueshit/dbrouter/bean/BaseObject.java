package cn.zh.blueshit.dbrouter.bean;

import cn.zh.blueshit.dbrouter.DbContextHolder;

import java.io.Serializable;

/**
 * Created by zhaoheng on 2016/5/20.
 */
public class BaseObject implements Serializable {

    private static final long serialVersionUID = 5537432372984608288L;


    private String tableIndex;

    public String getTableIndex() {
        return DbContextHolder.getTableIndex();
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex;
    }
}

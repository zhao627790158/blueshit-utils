package cn.blueshit.cn.test.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaoheng on 2016/6/14.
 */
public class DescribeLVBResponse {

    private int code;
    private String message;
    private String all_count;
    private List<Map<String, String>> channelSet;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAll_count() {
        return all_count;
    }

    public void setAll_count(String all_count) {
        this.all_count = all_count;
    }

    public List<Map<String, String>> getChannelSet() {
        return channelSet;
    }

    public void setChannelSet(List<Map<String, String>> channelSet) {
        this.channelSet = channelSet;
    }
}

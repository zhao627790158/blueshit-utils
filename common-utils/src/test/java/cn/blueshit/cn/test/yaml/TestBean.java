package cn.blueshit.cn.test.yaml;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * Created by zhaoheng on 18/2/6.
 */
@Data
public class TestBean {

    private Map<String, String> dateTime = Maps.newHashMap();

    private String zkConifg;

    private TestBean children;


}

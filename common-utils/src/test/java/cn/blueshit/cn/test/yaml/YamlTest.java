package cn.blueshit.cn.test.yaml;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by zhaoheng on 18/2/6.
 */
public class YamlTest {


    @Test
    public void testYaml1() throws URISyntaxException, IOException {
        URI uri = YamlTest.class.getResource("/test.yaml").toURI();
        TestBean unmarshal = unmarshal(new File(uri));
        System.out.println(JSON.toJSONString(unmarshal));

    }


    private static TestBean unmarshal(final File yamlFile) throws IOException {
        try (
                FileInputStream fileInputStream = new FileInputStream(yamlFile);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8")
        ) {
            //return new Yaml(new Constructor(TestBean.class)).loadAs(inputStreamReader, TestBean.class);
            return new Yaml().loadAs(inputStreamReader, TestBean.class);
        }
    }

}

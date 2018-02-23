package cn.blueshit.cn.test.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaoheng on 17/4/28.
 */
public class HelloVelocity {

    public static void main(String[] args) {
//初始化引擎
        VelocityEngine vEngine = new VelocityEngine();
        vEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        vEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        vEngine.init();
        //读取Hellovelocity.vm这个模板生成的Template这个类
        Template template = vEngine.getTemplate("vm/test.vm");
        VelocityContext context = new VelocityContext();

        context.put("name", "zifangsky");
        context.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add("velocity");
        context.put("list", list);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        System.out.println(writer.toString());

    }
}

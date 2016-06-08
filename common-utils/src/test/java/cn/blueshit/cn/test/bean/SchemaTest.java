package cn.blueshit.cn.test.bean;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaoheng on 2016/6/8.
 */
public class SchemaTest {



    @Test
    public void schemaTest() {

        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream validateConfigInput = null;
        InputStream xsdInputStream = null;
        try {
            validateConfigInput = classLoader.getResourceAsStream("mybatis-sharding-config.xml");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = validateConfigInput.read(buffer)) > -1 ) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            xsdInputStream = classLoader.getResourceAsStream("mybatis-sharding-config.xsd");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            //创建schema
            Schema schema = schemaFactory.newSchema(new StreamSource(xsdInputStream));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new ByteArrayInputStream(baos.toByteArray())));

            SAXParser parser = factory.newSAXParser();
            SAXReader reader = new SAXReader(parser.getXMLReader());

            Document document = reader.read(new ByteArrayInputStream(baos.toByteArray()));
            Element root = document.getRootElement();
            parseStrategies(root);


        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void parseStrategies(Element root) throws Exception {
        List<?> strategiesList = root.elements("strategy");
        if (strategiesList != null) {
            for (Object o : strategiesList) {
                Element strategy = (Element) o;
                String logicTable = strategy.attribute("logicTable").getStringValue();
                String strategyClass = strategy.attribute("class").getStringValue();
                Class<?> clazz = Class.forName(strategyClass);
                Date shardStrategy = (Date) clazz.newInstance();
            }
        }
    }
}

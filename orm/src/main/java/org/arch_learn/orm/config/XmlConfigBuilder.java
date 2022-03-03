package org.arch_learn.orm.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.arch_learn.orm.io.Resources;
import org.arch_learn.orm.po.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XmlConfigBuilder {

    private Configuration configuration;

    public XmlConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 使用dom4j将配置文件解析，封装Configuration对象
     *
     * @param inputStream
     * @return
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        //1.解析sqlMapConfig文件
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        list.forEach(v -> {
            String name = v.attributeValue("name");
            String value = v.attributeValue("value");
            properties.setProperty(name, value);
        });
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(comboPooledDataSource);

        //解析mapper.xml文件。
        List<Element> mapperElements = rootElement.selectNodes("//mapper");
        for (Element v : mapperElements) {
            String path = v.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(path);
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsStream);
        }
        return configuration;
    }
}

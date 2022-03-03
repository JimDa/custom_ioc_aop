package org.arch_learn.orm.config;

import org.arch_learn.orm.po.Configuration;
import org.arch_learn.orm.po.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XmlMapperBuilder {
    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");

        loadMappedStatements(rootElement, namespace, "//select");
        loadMappedStatements(rootElement, namespace, "//insert");
        loadMappedStatements(rootElement, namespace, "//delete");
        loadMappedStatements(rootElement, namespace, "//update");
    }

    private void loadMappedStatements(Element rootElement, String namespace, String node) {
        List<Element> selectElementList = rootElement.selectNodes(node);
        selectElementList.forEach(v -> {
            String id = v.attributeValue("id");
            String returnType = v.attributeValue("resultType");
            String parameterType = v.attributeValue("parameterType");
            String sqlText = v.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(returnType);
            mappedStatement.setSql(sqlText);
            configuration.getMappedStatementMap().put(namespace + "." + id, mappedStatement);
        });
    }
}

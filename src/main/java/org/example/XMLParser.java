package org.example;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {

    final static Logger logger = Logger.getLogger(XMLParser.class);

    public static Map<String, String> parseXML (File xmlFile) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.info("Failed builder creating", e);
            return null;
        }
        builder.setErrorHandler(
                new ErrorHandler() {
                    public void warning(SAXParseException e) {
                        logger.info("WARNING : " + e.getMessage());
                    }

                    public void error(SAXParseException e) throws SAXException {
                        logger.info("ERROR : " + e.getMessage());
                        throw e;
                    }

                    public void fatalError(SAXParseException e) throws SAXException {
                        logger.info("FATAL : " + e.getMessage());
                        throw e;
                    }
                }
        );
        Document document;
        try {
            document = builder.parse(xmlFile);
        } catch (SAXException | IOException e) {
            logger.info("Failed file parsing!", e);
            return null;
        }
        Map<String, String> mapDataSource = new HashMap<>();
        String sourceName = document.getElementsByTagName("source-name").item(0).getFirstChild().getNodeValue();
        String connectionUrl = document.getElementsByTagName("connection-url").item(0).getFirstChild().getNodeValue();
        String driverClass = document.getElementsByTagName("driver-class").item(0).getFirstChild().getNodeValue();
        String password = document.getElementsByTagName("password").item(0).getFirstChild().getNodeValue();
        String userName = document.getElementsByTagName("user-name").item(0).getFirstChild().getNodeValue();
        mapDataSource.put("sourceName", sourceName);
        mapDataSource.put("driverClass", driverClass);
        mapDataSource.put("password", password);
        mapDataSource.put("userName", userName);
        mapDataSource.put("connectionUrl", connectionUrl);
        return mapDataSource;
    }
}

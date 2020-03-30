package com.macrovision.test.util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Properties;

public class Settings {
    private Properties prop;

    public Settings() {
        prop = new Properties();
        read();
    }

    public String get(String key) {
        return (String) prop.getProperty(key);
    }

    private void read() {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = docBuilder.parse(new File("settings.xml"));

            // normalize text representation
            doc.getDocumentElement().normalize();
            System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());

            NodeList listOfSettings = doc.getElementsByTagName("setting");
            int totalSettings = listOfSettings.getLength();
            System.out.println("total settings: " + totalSettings);

            for (int s = 0; s < listOfSettings.getLength(); s++) {
                Node firstNode = listOfSettings.item(s);

                if (firstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element firstElement = (Element) firstNode;

                    NodeList firstNameList = firstElement.getElementsByTagName("param");
                    Element firstNameElement = (Element) firstNameList.item(0);

                    NodeList paramList = firstNameElement.getChildNodes();
                    System.out.println("Param : " + ((Node) paramList.item(0)).getNodeValue().trim());
                    String parameterName = ((Node) paramList.item(0)).getNodeValue().trim();

                    NodeList lastNameList = firstElement.getElementsByTagName("value");
                    Element lastNameElement = (Element) lastNameList.item(0);

                    NodeList valueList = lastNameElement.getChildNodes();
                    System.out.println("Value : " + ((Node) valueList.item(0)).getNodeValue().trim());
                    String value = ((Node) valueList.item(0)).getNodeValue().trim();

                    prop.setProperty(parameterName, value);
                }
            }
        } catch (SAXParseException err) {
            System.out.println("** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
            System.out.println(" " + err.getMessage());
        } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

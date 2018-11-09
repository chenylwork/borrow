package com.work.borrow.util;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * xml文件操作类
 */
public class XMlUtils {
    private static String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
            "<returnsms name='lisi'>" +
            "<returnstatus>status</returnstatus>" +
            "<message>message</message>" +
            "<remainpoint> remainpoint</remainpoint>" +
            "<taskID>taskID</taskID>" +
            "<successCounts>successCounts</successCounts>" +
            "</returnsms>";

    /**
     * 获取节点属性
     * @param string
     * @param attributeName
     * @return
     */
    public static String getAttributeValue(String string, String attributeName) {
        Element rootElement = getRootElement(string);
        return rootElement.attributeValue(attributeName);
    }

    /**
     * 获取子节点内容
     * @param string
     * @param childNodeName
     * @return
     */
    public static String getChildNodeValue(String string,String childNodeName) {
        Element rootElement = getRootElement(string);
        return rootElement.elementText(childNodeName);
    }

    /**
     * 获取文档结构
     *
     * @param string
     * @return
     */
    public static Element getRootElement(String string) {
        Element element = null;
        try {
            Document document = DocumentHelper.parseText(string);
            element = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return element;
    }

    public static void main(String[] a) {
        System.err.println(getAttributeValue(xml,"name"));
        System.err.println(getChildNodeValue(xml,"taskID"));
    }


}

package com.example.ray.myapplication.Parsing;


import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class SaxHelper extends DefaultHandler {
    private Student student;
    private ArrayList<Student> students;
    private String tagName = null;
    /**
     * 当读取到文档开始标志是触发，通常在这里完成一些初始化操作
     */
    @Override
    public void startDocument() throws SAXException {
        this.students = new ArrayList<>();
        Log.i("SAX", "读取到文档头,开始解析xml");
    }
    /**
     * 读到一个开始标签时调用,第二个参数为标签名,最后一个参数为属性数组
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equals("student"))
        {
            student = new Student();
            //Integer.parseInt(attributes.getValue("id"))转化为int
            student.setId(attributes.getValue("id"));
            Log.i("SAX", "开始处理person元素~");
        }
        this.tagName = localName;
    }
    /**
     * 读到到内容,第一个参数为字符串内容,后面依次为起始位置与长度
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //判断当前标签是否有效
        if (this.tagName!=null){
            String data = new String(ch, start, length);
            //读取标签中的内容
            if (this.tagName.equals("name")) {
                this.student.setName(data);
                Log.i("SAX", "处理name元素内容"+data);
            } else if (this.tagName.equals("age")) {
                this.student.setAge(data);
                Log.i("SAX", "处理age元素内容"+data);
            }

        }
    }
    /**
     * 处理元素结束时触发,这里将对象添加到结合中
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (localName.equals("student")) {
            this.students.add(student);
            student = null;
            Log.i("SAX", "处理person元素结束~");
        }
        this.tagName = null;
    }

    /**
     * 读取到文档结尾时触发，
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.i("SAX", "读取到文档尾,xml解析结束");
    }

    //获取Students集合
    public ArrayList<Student> getStudents() {
        Log.i("SAX", " "+students.size());
        return students;
    }
}

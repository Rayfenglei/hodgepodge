package com.example.ray.myapplication.Parsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.ray.myapplication.R;
import com.example.ray.myapplication.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ParseActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<Student> mXMLdata = new ArrayList<>();
    private Button mSaxparseBtn,mJsonBtn;
    private TextView textView;
    private Student mSaxDatas;
    private List<Student> mJsonDatas;
    private StringBuilder data =new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse);
        init();
    }
    private void init(){
        mSaxparseBtn = findViewById(R.id.sax_parse_btn);
        mJsonBtn = findViewById(R.id.json_parse_btn);
        textView = findViewById(R.id.parse_textview);

        mSaxparseBtn.setOnClickListener(this);
        mJsonBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sax_parse_btn:
                try {
                    mXMLdata = readxmlForSAX();
                }catch (Exception e){
                    e.printStackTrace();
                }
                showSAXDatas();
                break;
            case R.id.json_parse_btn:
                parseJson();
                showJsonDatas();
                break;
            default:
                break;
        }
    }

    /*
    使用SAX方法解析本地XML文件
    */
    private ArrayList<Student> readxmlForSAX() throws Exception {
        Log.i("dataXML","full1");
        //获取文件资源建立输入流对象
        InputStream is = getAssets().open("students.xml");
        //①创建XML解析处理器
        SaxHelper ss = new SaxHelper();
        //②得到SAX解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //③创建SAX解析器
        SAXParser parser = factory.newSAXParser();
        //④将xml解析处理器分配给解析器,对文档进行解析,将事件发送给处理器
        parser.parse(is, ss);
        is.close();
        return ss.getStudents();
    }

    private void showSAXDatas(){
        mSaxDatas = null;
        for (int i =0;i<mXMLdata.size();i++){
            mSaxDatas = mXMLdata.get(i);
            data.append(mSaxDatas.getName()).append(" ").append(mSaxDatas.getId()).append(" ").append(mSaxDatas.getAge()).append(" ");
        }
        textView.setText(data);
    }

    /*
    解析JSON数据
    */

    private void parseJson(){
        /*
         * 使用工具类Utils解析本地assets中的Json数据,得到String类型的数据
         */
        String json = Utils.getJson(this,"student.json");

        mJsonDatas = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0;i < jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Student student = new Student();
                student.setId(i+"");
                student.setName(jsonObject.getString("name"));
                student.setAge(jsonObject.getString("age"));
                mJsonDatas.add(student);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void showJsonDatas (){
        mSaxDatas = null;
        for (int i =0;i<mJsonDatas.size();i++){
            mSaxDatas = mJsonDatas.get(i);
            data.append(mSaxDatas.getName()).append(" ").append(mSaxDatas.getId()).append(" ").append(mSaxDatas.getAge()).append(" ");
        }
        textView.setText(data);
    }
}

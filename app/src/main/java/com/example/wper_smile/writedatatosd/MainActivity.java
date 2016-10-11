package com.example.wper_smile.writedatatosd;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private final static String MyFileName="myfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_Write=(Button)findViewById(R.id.ButtonWrite);
        Button btn_Read=(Button)findViewById(R.id.ButtonRead);

        btn_Write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutputStream out=null;//声明一个输出流
                try {
                    //通过静态方法getExternalStorageState()来获取外部存储的状态
                    /**
                     * MEDIA_MOUNTED
                     *  存储媒体已经挂载，并且挂载点可读/写
                     */

                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                        //获取文件路径
                        File file = Environment.getExternalStorageDirectory();
                        File myfile = new File(file.getCanonicalPath() + "/"+MyFileName);

                        FileOutputStream fileOutputStream = new FileOutputStream(myfile);
                        out = new BufferedOutputStream(fileOutputStream);
                        EditText edt_name=(EditText)findViewById(R.id.edt_name);
                        EditText edt_num=(EditText)findViewById(R.id.edt_num);
                        String Name="姓名："+edt_name.getText().toString();
                        String Num=" 学号："+edt_num.getText().toString();
                        try {
                            out.write(Name.getBytes(StandardCharsets.UTF_8));
                            out.write(Num.getBytes(StandardCharsets.UTF_8));
                        } finally {
                            if (out != null)
                                out.close();
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btn_Read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                        File file = Environment.getExternalStorageDirectory();
                        File myfile = new File(file.getCanonicalPath() + "/"+ MyFileName);
                        FileInputStream fileInputStream = new FileInputStream(myfile);
                        BufferedInputStream bis = new BufferedInputStream(fileInputStream);
                        //采用BufferedReader避免中文乱码
                        BufferedReader reader = new BufferedReader (new InputStreamReader(bis));
                        StringBuilder stringBuilder = new StringBuilder("");
                        try {

                            while (reader.ready()) {
                                stringBuilder.append((char) reader.read());
                            }
                            Toast.makeText(MainActivity.this,
                                    stringBuilder.toString(), Toast.LENGTH_LONG).show();
                        } finally {
                            if (reader != null)
                                reader.close();
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}


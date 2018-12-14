package com.example.lixiaobin.studentapp.Activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.lixiaobin.studentapp.Obj.AppInfo;
import com.example.lixiaobin.studentapp.Obj.Student;
import com.example.lixiaobin.studentapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView studentName;
    private TextView studentSex;
    private TextView studentMajor;
    private TextView studentClass;
    private TextView joinyearText;
    private TextView stuyearText;
    private TextView phoneText;
    private TextView emileText;
    private TextView lookMore;
    private ConstraintLayout deliInfo;
    private Button numSignButton;
    private Button imgSignButton;
    private Button changeInfo;
    private Button returnSign;
    AppInfo student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentName = (TextView) findViewById(R.id.studentName);
        studentSex = (TextView) findViewById(R.id.studentSex);
        studentMajor = (TextView) findViewById(R.id.studentMajor);
        studentClass = (TextView) findViewById(R.id.studentClass);
        joinyearText = (TextView) findViewById(R.id.joinyearText);
        stuyearText = (TextView) findViewById(R.id.stuyearText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        emileText = (TextView) findViewById(R.id.emileText);
        lookMore = (TextView) findViewById(R.id.lookMore);
        deliInfo = (ConstraintLayout) findViewById(R.id.deliInfo);
        numSignButton = (Button) findViewById(R.id.numSignButton);
        imgSignButton = (Button) findViewById(R.id.imgSignButton);
        changeInfo = (Button) findViewById(R.id.changeInfo);
        returnSign = (Button) findViewById(R.id.returnSign);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        student = (AppInfo)getApplicationContext();
        studentName.setText(student.getStudent_name());
        studentSex.setText(student.getStudent_sex());
        studentMajor.setText(student.getStudent_major());
        studentClass.setText(student.getStudent_class());
        joinyearText.setText(student.getStudent_joinyear());
        stuyearText.setText(String.valueOf(student.getStudent_stuyear()));
        phoneText.setText(student.getStudent_password());
        emileText.setText(student.getStudent_emile());

        lookMore.setOnClickListener(this);
        returnSign.setOnClickListener(this);
        numSignButton.setOnClickListener(this);
        imgSignButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lookMore:
                lookMore.setVisibility(View.GONE);
                numSignButton.setVisibility(View.GONE);
                imgSignButton.setVisibility(View.GONE);
                deliInfo.setVisibility(View.VISIBLE);
                break;
            case R.id.returnSign:
                lookMore.setVisibility(View.VISIBLE);
                numSignButton.setVisibility(View.VISIBLE);
                imgSignButton.setVisibility(View.VISIBLE);
                deliInfo.setVisibility(View.GONE);
                break;
            case R.id.numSignButton:
                Bundle bundle = new Bundle();
                bundle.putString("studentAccount", String.valueOf(student.getStudent_account()));
                Intent intent = new Intent(MainActivity.this, NumSignActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.imgSignButton:
                Bundle bundle1 = new Bundle();
                bundle1.putString("studentAccount", String.valueOf(student.getStudent_account()));
                Intent intent1 = new Intent(MainActivity.this, ImgSignActivity.class);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
        }
    }
}

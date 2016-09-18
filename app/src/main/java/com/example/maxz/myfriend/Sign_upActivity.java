package com.example.maxz.myfriend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Sign_upActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, userEditText,
            passwordEditText, rePasswordEditText;
    private RadioGroup radioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private ImageView imageView;
    private String nameString, userString, passwordString,
            repasswordString, sexString, imageString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget ประกาศค่า ตัวแปร private
        nameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText3);
        rePasswordEditText = (EditText) findViewById(R.id.editText4);
        radioGroup = (RadioGroup) findViewById(R.id.ragsex);
        maleRadioButton = (RadioButton) findViewById(R.id.radioButton);
        femaleRadioButton = (RadioButton) findViewById(R.id.radioButton2);
        imageView = (ImageView) findViewById(R.id.imageView);

        //image controller
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "โปรดเลือกรูปภาพ"), 1);



            } // onclick
        });


    }//main method 2

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            //Result Complete
            Log.d("MyFriendsV1", "Result ==> OK");
        } // if

    }// onActivityResult

    public void clickSignupSign(View view) {

        //get Value From Edit Text แปลค่าจาก Edit Text > String
        nameString = nameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();
        repasswordString = rePasswordEditText.getText().toString().trim();

        //Chack Space ต้องกรอกข้อความให้ครบ
        if (nameString.equals("") || userString.equals("") ||
                passwordString.equals("") || repasswordString.equals("")) {
            //Have Space สภาวะที่มีช่องว่าง
            myAlert myAlert = new myAlert(this,
                    R.drawable.doremon48, "มีช่องว่าง", "กรุณากรอกทุกช่องค่ะ");
            myAlert.myDialog();
        } else if (!passwordString.equals(repasswordString)) {
            // Password not match
            myAlert myAlert = new myAlert(this, R.drawable.doremon48, "Password error", "กรุณาพิมพ์ password ให้เหมือนกัน");
            myAlert.myDialog();
        } else if (!(maleRadioButton.isChecked() || femaleRadioButton.isChecked())) {
            //non check sex
            myAlert sexmyAlert = new myAlert(this, R.drawable.rat48, "no choose sex", "please choose sex");
            sexmyAlert.myDialog();

        }

    } // clickSignup

}//main class 2

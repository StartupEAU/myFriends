package com.example.maxz.myfriend;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;


public class Sign_upActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, userEditText,
            passwordEditText, rePasswordEditText;
    private RadioGroup radioGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private ImageView imageView;
    private String nameString, userString, passwordString,
            repasswordString, sexString, imageString, imasgePathString, imageNameString;
    private Boolean statusABoolean = true;


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

        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radioButton:
                        sexString = "Male";
                        break;
                    case R.id.radioButton2:
                        sexString = "Female";
                        break;
                } // switch
            } // checked
        });

    }//main method 2

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            //Result Complete
            Log.d("MyFriendsV1", "Result ==> OK");

            //Find Path of Image
            Uri uri = data.getData();
            imasgePathString = myFindPathImage(uri);
            Log.d("MyFriendsV1", "imagePathString==>" + imasgePathString);

            // Setup image to imageview
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);


            } catch (Exception e) {
                e.printStackTrace();
            } // try

            statusABoolean = false;


        } // if

    }// onActivityResult


    private String myFindPathImage(Uri uri) {
        String strResult = null;

        String[] strings = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int intIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            strResult = cursor.getString(intIndex);

        } else {
            strResult = uri.getPath();
        }
        return strResult;
    }

    public void clickSignupSign(View view) {

        //get Value From Edit Text แปลค่าจาก Edit Text > String
        nameString = nameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();
        repasswordString = rePasswordEditText.getText().toString().trim();

        //Check Space ต้องกรอกข้อความให้ครบ
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
        } else if (statusABoolean) {
            myAlert myAlert = new myAlert(this, R.drawable.bird48, "ยังไม่เลือกรูป", "กรุณาเลือกรูปด้วยครับ");
            myAlert.myDialog();
        } else {
            //upload image to server
            uploadImageToServer();
            insertDataToServer();
        }

    } // clickSignup

    private void insertDataToServer() {

        imageNameString = imasgePathString.substring(imasgePathString.lastIndexOf("/"));
        imageNameString = "http://swiftcodingthai.com/18Sep/Image" + imageNameString;

        Log.d("MyFriendV1", "imageNameString ==>" + imageNameString);

        Log.d("MYFriendV2", "Name =" + nameString);
        Log.d("MYFriendV2", "Sex" + sexString);
        Log.d("MYFriendV2", "User" + userString);
        Log.d("MYFriendV2", "Password" + passwordString);
        Log.d("MYFriendV2", "Image" + imageNameString);


        MyUpdateUser myUpdateUser = new MyUpdateUser(this);
        myUpdateUser.execute();


    } // insertData

    private class MyUpdateUser extends AsyncTask<Void, Void, String> {

        private Context context;
        private static final String urlPHP = "http://swiftcodingthai.com/18Sep/add_film.php";

        public MyUpdateUser(Context context) {
            this.context = context;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("MyFriendV2", "Result ==>" + s);

            if (Boolean.parseBoolean(s)) {
                Toast.makeText(context, "อัพข้อมูลเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected String doInBackground(Void... params) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()// การโยนภายในครั้งเดียว
                        .add("isAdd", "true")
                        .add("Name", nameString)
                        .add("Sex", sexString)
                        .add("User", userString)
                        .add("Password", passwordString)
                        .add("Image", imageNameString)
                        .build();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlPHP).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                return null;
            }

        } // do in back
    } // Myupdateuserclass


    private void uploadImageToServer() {

        //New Policy
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);


        try {
            // connect
            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21,
                    "18Sep@swiftcodingthai.com", "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("Image"); // path server folder
            simpleFTP.stor(new File(imasgePathString)); // ตำแหน่ง path ที่โยนขึ้นไป
            simpleFTP.disconnect();

            Log.d("MyFriendV1", "upload Finish");

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("MyFriendV1", "e ==>" + e.toString());
        }
    }

}//main class 2

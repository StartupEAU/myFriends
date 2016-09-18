package com.example.maxz.myfriend;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


public class MainActivity extends AppCompatActivity {

    //ประกาศตัวแปร
    private EditText userEditText, passwordEditText;
    private String userString , passwordString;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind Wiget
        userEditText = (EditText) findViewById(R.id.editText5);
        passwordEditText = (EditText) findViewById(R.id.editText6);


    }//main method

    public  void clickSignIn(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //checkspace
        if (userString.equals("") || passwordString.equals("")) {
            myAlert myAlert = new myAlert(this, R.drawable.rat48, "มีช่องว่าง", " thank you");
            myAlert.myDialog();
        } else {
            SynUser synUser = new SynUser();
            synUser.execute();
        }



    } // clicksignin

    private class SynUser extends AsyncTask<Void, Void, String> {

        private static final String urlJSON = "http://swiftcodingthai.com/18Sep/get_user.php";

        @Override
        protected String doInBackground(Void... params) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("MyFriendV3", "JSON ==>" + s);
        }
    }// main class

    public void click_signUPMain(View view) {

        startActivity(new Intent(MainActivity.this,Sign_upActivity.class));

    }


}//main class
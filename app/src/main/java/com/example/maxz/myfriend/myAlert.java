package com.example.maxz.myfriend;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by onasume on 17/9/2559.
 */
public class myAlert  {

    //Explicit
    private Context context;
    private int anInt;
    private String titleString, messageString;

    public myAlert(Context context, int anInt, String titleString, String massageString) {
        this.context = context;
        this.anInt = anInt;
        this.titleString = titleString;
        this.messageString = massageString;
    } //Construtor  Method ที่ชื่อเดียวกับคาส

    public void myDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context); //การสร้าง Instance
        builder.setCancelable(false); // ให้ปุ่ม ย้อนกลับ ไม่ทำงาน
        builder.setIcon(anInt);
        builder.setTitle(titleString);
        builder.setMessage(messageString);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    } // my dialog



} //main class


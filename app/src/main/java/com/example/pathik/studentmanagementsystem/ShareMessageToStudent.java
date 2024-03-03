package com.example.pathik.studentmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ShareMessageToStudent extends AppCompatActivity {

    private EditText message_title,message_content;
    private DataBaseHelperAppClass mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_message_to_student);

        mydb =new DataBaseHelperAppClass(this);


        LayoutInflater li = LayoutInflater.from(ShareMessageToStudent.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShareMessageToStudent.this);
        View promptsView = getLayoutInflater().inflate(R.layout.share_message_to_student_prompt, null);
        message_title =(EditText) promptsView.findViewById(R.id.tb_share_message_title);
        message_content =(EditText) promptsView.findViewById(R.id.tb_share_message_content);

        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle(Html.fromHtml("<font color='#2e91d3'>Share a Message</font>"));
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Send",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                    Cursor temp_cursor=mydb.getShareMessage();
                                    int count=temp_cursor.getCount();
                                    if(count <= 0)
                                    {
                                        count=0;
                                    }
                                    count=count+1;
                                    String temp=Integer.toString(count);
                                    boolean result=mydb.insert_message(message_title.getText().toString(),message_content.getText().toString(),temp);
                                    if(result)
                                    {
                                        Toast.makeText(ShareMessageToStudent.this,"Message Send!!",Toast.LENGTH_LONG).show();
                                        dialog.cancel();
                                        finish();
                                    }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                                finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();


    }
}

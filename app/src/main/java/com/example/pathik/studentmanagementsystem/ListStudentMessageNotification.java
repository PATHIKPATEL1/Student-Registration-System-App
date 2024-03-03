package com.example.pathik.studentmanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ListStudentMessageNotification extends AppCompatActivity {

    private DataBaseHelperAppClass mydb;
    private ListView message_notification_list_view;
    private ArrayList<String> message_list;
    private ArrayAdapter<String> adapter;
    private Cursor notification;
    private String username_signin,password_signin,isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_student_message_notification);


        Toolbar toolbar = findViewById(R.id.list_student_message_notification_toolbar);
        toolbar.setTitle("Notification");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();
        username_signin = intent.getStringExtra("username_signin");
        password_signin = intent.getStringExtra("password_signin");
        isAdmin=intent.getStringExtra("isAdmin");

        mydb=new DataBaseHelperAppClass(ListStudentMessageNotification.this);

        message_list=new ArrayList<>();

        message_notification_list_view=findViewById(R.id.list_student_message_notification);

        showNotificaion();

    }

    public void showNotificaion()
    {
        notification=mydb.getShareMessage();

        if(notification.getCount()<=0)
        {
            Toast.makeText(ListStudentMessageNotification.this,"Notification doesn't exist",Toast.LENGTH_LONG).show();
        }
        else {
            while (notification.moveToNext())
            {
                message_list.add(notification.getString(notification.getColumnIndex("MESSAGE_TITLE")));
            }

            Collections.reverse(message_list);
            adapter=new ArrayAdapter<>(ListStudentMessageNotification.this,android.R.layout.simple_list_item_1,message_list);
            message_notification_list_view.setAdapter(adapter);

            message_notification_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    int temp_message_row = (notification.getCount()) - (i);
                    Cursor temp_cursor=mydb.getMessage((Integer.toString(temp_message_row)));
                    temp_cursor.moveToNext();
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListStudentMessageNotification.this);
                    alertDialogBuilder.setTitle(temp_cursor.getString(temp_cursor.getColumnIndex("MESSAGE_TITLE")))
                            .setMessage(temp_cursor.getString(temp_cursor.getColumnIndex("MESSAGE_CONTENT")));
                            alertDialogBuilder.setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int arg1) {
                                        dialogInterface.cancel();
                                        }
                                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1=new Intent(ListStudentMessageNotification.this,UpdateStudentDetails.class);
        intent1.putExtra("username_signin",username_signin);
        intent1.putExtra("password_signin",password_signin);
        intent1.putExtra("isAdmin",isAdmin);
        startActivity(intent1);
        finish();
    }
}

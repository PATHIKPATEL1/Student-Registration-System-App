package com.example.pathik.studentmanagementsystem;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateStudentDetails extends AppCompatActivity {


    private TextView tb_update_details_addstudent_date_of_birth,tb_update_details_addstudent_firstname,tb_update_details_addstudent_middlename,tb_update_details_addstudent_lastname,
            tb_update_details_addstudent_email,tb_update_details_addstudent_mobileno,tb_update_details_addstudent_address,tb_update_details_addstudent_state,tb_update_details_addstudent_enrollmentno,
            tb_update_details_addstudent_institutename,tb_update_details_addstudent_gender,tb_update_details_addstudent_branch,tb_update_details_addstudent_semester,tb_update_details_addstudent_password;
    private DataBaseHelperAppClass mydb;
    private int TotalMessageId,temp=0;
    private Cursor studentDetails,message_notification;
    private  String firstname,middlename,lastname,date_of_birth,gender,email,mobileno,address,state,enrollmentno,branch,semester,institutename,
            password,isAdmin,username_signin,password_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student_details);
        mydb = new DataBaseHelperAppClass(this);


        Intent intent = getIntent();
        username_signin = intent.getStringExtra("username_signin");
        password_signin = intent.getStringExtra("password_signin");
        isAdmin=intent.getStringExtra("isAdmin");

        if(isAdmin==null)
        {
            isAdmin="no";
        }

        Toolbar toolbar =  findViewById(R.id.update_details_student_toolbar);
        toolbar.setTitle(username_signin+"'s Information");
        setSupportActionBar(toolbar);

        if(isAdmin.equals("no")) {
            Cursor temp_id = mydb.getShareMessageId(username_signin);
            temp_id.moveToNext();
            message_notification = mydb.getShareMessage();
            TotalMessageId=message_notification.getCount();

            if (Integer.parseInt(temp_id.getString(0)) != TotalMessageId) {
                if (message_notification.getCount() > 0) {
                    message_notification.moveToLast();
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.ic_app_logo)
                                    .setContentTitle(message_notification.getString(message_notification.getColumnIndex("MESSAGE_TITLE")))
                                    .setContentText(message_notification.getString(message_notification.getColumnIndex("MESSAGE_CONTENT")))
                                    .setAutoCancel(true)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                    Intent notificationIntent = new Intent(UpdateStudentDetails.this, ApplicationAnimationActivity.class);
                    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);

                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(0, builder.build());

                    temp=1;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        String channelId = "channel_id";
                        NotificationChannel channel = new NotificationChannel(channelId,
                                "Student Management System Channel", NotificationManager.IMPORTANCE_DEFAULT);
                        manager.createNotificationChannel(channel);
                        builder.setChannelId(channelId);
                    }

                    manager.notify(0, builder.build());



                }
            }

        }

        if(isAdmin.equals("yes"))
        {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                onBackPressed();
                }
            });
        }

        try {


            tb_update_details_addstudent_firstname = (TextView) findViewById(R.id.update_details_addstudent_firstname);
            tb_update_details_addstudent_middlename = (TextView) findViewById(R.id.update_details_addstudent_middlename);
            tb_update_details_addstudent_lastname = (TextView) findViewById(R.id.update_details_addstudent_lastname);
            tb_update_details_addstudent_date_of_birth = (TextView) findViewById(R.id.update_details_addstudent_date_of_birth);
            tb_update_details_addstudent_gender = (TextView) findViewById(R.id.update_details_addstudent_gender);
            tb_update_details_addstudent_email = (TextView) findViewById(R.id.update_details_addstudent_email);
            tb_update_details_addstudent_mobileno = (TextView) findViewById(R.id.update_details_addstudent_mobileno);
            tb_update_details_addstudent_address = (TextView) findViewById(R.id.update_details_addstudent_address);
            tb_update_details_addstudent_state = (TextView) findViewById(R.id.update_details_addstudent_state);
            tb_update_details_addstudent_enrollmentno = (TextView) findViewById(R.id.update_details_addstudent_enrollmentno);
            tb_update_details_addstudent_branch = (TextView) findViewById(R.id.update_details_spinner_addstudent_branch);
            tb_update_details_addstudent_semester = (TextView) findViewById(R.id.update_details_spinner_addstudent_semester);
            tb_update_details_addstudent_institutename = (TextView) findViewById(R.id.update_details_addstudent_institutename);


            studentDetails = mydb.getStudentDetails(username_signin, password_signin);

            if(studentDetails.getCount()<=0)
            {
                Intent intent1=new Intent(UpdateStudentDetails.this,SignInActivity.class);
                startActivity(intent1);
                finish();
            }
            else {
                studentDetails.moveToNext();
                firstname = studentDetails.getString(0);
                middlename = studentDetails.getString(1);
                lastname = studentDetails.getString(2);
                date_of_birth = studentDetails.getString(3);
                gender = studentDetails.getString(4);
                email = studentDetails.getString(5);
                mobileno = studentDetails.getString(6);
                address = studentDetails.getString(7);
                state = studentDetails.getString(8);
                enrollmentno = studentDetails.getString(9);
                branch = studentDetails.getString(10);
                semester = studentDetails.getString(11);
                institutename = studentDetails.getString(12);
                password = studentDetails.getString(13);

                studentDetails.close();

                tb_update_details_addstudent_firstname.setText(firstname);
                tb_update_details_addstudent_middlename.setText(middlename);
                tb_update_details_addstudent_lastname.setText(lastname);
                tb_update_details_addstudent_date_of_birth.setText(date_of_birth);
                tb_update_details_addstudent_gender.setText(gender);
                tb_update_details_addstudent_email.setText(email);
                tb_update_details_addstudent_mobileno.setText(mobileno);
                tb_update_details_addstudent_address.setText(address);
                tb_update_details_addstudent_state.setText(state);
                tb_update_details_addstudent_enrollmentno.setText(enrollmentno);
                tb_update_details_addstudent_branch.setText(branch);
                tb_update_details_addstudent_semester.setText(semester);
                tb_update_details_addstudent_institutename.setText(institutename);
            }
        }catch (Exception e){}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isAdmin.equals("yes"))
        {
            getMenuInflater().inflate(R.menu.delete_student_details_menu, menu);

        }
        else {
            getMenuInflater().inflate(R.menu.notification_menu, menu);
            if(temp==1)
            {
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_notifications_active));
            }

        }
        getMenuInflater().inflate(R.menu.update_logout_option_student_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.studentDetailsUpdateMenu:

                Intent intent=new Intent(UpdateStudentDetails.this,StudentRegistrationActivity.class);
                intent.putExtra("update","yes");
                intent.putExtra("isAdmin",isAdmin);
                intent.putExtra("confirm_addstudent_firstname",firstname);
                intent.putExtra("confirm_addstudent_lastname",lastname);
                intent.putExtra("confirm_addstudent_middlename",middlename);
                intent.putExtra("confirm_addstudent_date_of_birth",date_of_birth);
                intent.putExtra("confirm_addstudent_gender_value",gender);
                intent.putExtra("confirm_addstudent_email",email);
                intent.putExtra("confirm_addstudent_mobileno",mobileno);
                intent.putExtra("confirm_addstudent_address",address);
                intent.putExtra("confirm_addstudent_state",state);
                intent.putExtra("confirm_addstudent_enrollmentno",enrollmentno);
                intent.putExtra("confirm_addstudent_institutename",institutename);
                intent.putExtra("confirm_addstudent_branch",branch);
                intent.putExtra("confirm_addstudent_semester",semester);
                intent.putExtra("confirm_addstudent_password",password);
                startActivity(intent);
                return true;


            case R.id.studentDetailsSignOutMenu:

                final AlertDialog.Builder signout_message = new AlertDialog.Builder(UpdateStudentDetails.this);
                signout_message.setCancelable(false);
                signout_message.setMessage(Html.fromHtml("Are you sure you want to sign out?"));
                signout_message.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new UserSignInData(UpdateStudentDetails.this).removeSignInData();
                        Toast.makeText(UpdateStudentDetails.this,"SignOut Successfully...",Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(UpdateStudentDetails.this,SignInActivity.class);
                        startActivity(intent2);
                        finishAffinity();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel();
                    }
                });
                signout_message.create().show();
                return true;

            case R.id.ic_delete_student:

                final AlertDialog.Builder delete_message = new AlertDialog.Builder(UpdateStudentDetails.this);
                delete_message.setCancelable(false);
                delete_message.setMessage(Html.fromHtml("Are you sure you want to Delete <font color='#FF0000'>"+username_signin+" </font>?"));
                delete_message.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int row=mydb.deleteStudentDetails(username_signin);
                        if(row==1) {
                            Toast.makeText(UpdateStudentDetails.this, username_signin + " is Removed", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(UpdateStudentDetails.this, username_signin + " is not Removed", Toast.LENGTH_LONG).show();
                        }
                        onBackPressed();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                delete_message.create().show();
                return true;

            case R.id.studentNotification:
                mydb.update_message_id(username_signin,Integer.toString(TotalMessageId));
                Intent intent1=new Intent(UpdateStudentDetails.this,ListStudentMessageNotification.class);
                intent1.putExtra("username_signin",username_signin);
                intent1.putExtra("password_signin",password_signin);
                intent1.putExtra("isAdmin",isAdmin);
                startActivity(intent1);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(isAdmin.equals("yes"))
        {
            UserSignInData userSignInData=new UserSignInData(UpdateStudentDetails.this);
            Intent intent=new Intent(UpdateStudentDetails.this,Admin_StudentDetailsActivity.class);
            intent.putExtra("isAdmin",isAdmin);
            intent.putExtra("username_signin",userSignInData.getUsername_signin());
            intent.putExtra("password_signin",userSignInData.getPassword_signin());
            startActivity(intent);
            finishAffinity();
        }
        else {

        }


    }
}

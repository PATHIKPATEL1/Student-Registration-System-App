package com.example.pathik.studentmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmAdminRegistrationActivity extends AppCompatActivity {


    private TextView tb_confirm_add_admin_name,tb_confirm_add_admin_id,tb_confirm_add_admin_email_id,tb_confirm_add_admin_branch;

    private static String confirm_add_admin_name,confirm_add_admin_id,confirm_add_admin_email_id,confirm_add_admin_branch,confirm_add_admin_password,update_admin,old_admin_password;
    private DataBaseHelperAppClass mydb;
    private boolean addadmin_result;
    private UserSignInData userSignInData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_admin_registration);

        mydb =new DataBaseHelperAppClass(this);
        Intent intent=getIntent();

        update_admin = intent.getStringExtra("update_admin");
        confirm_add_admin_name=intent.getStringExtra("confirm_add_admin_name");
        confirm_add_admin_id=intent.getStringExtra("confirm_add_admin_id");
        confirm_add_admin_email_id=intent.getStringExtra("confirm_add_admin_email_id");
        confirm_add_admin_branch=intent.getStringExtra("confirm_add_admin_branch");
        confirm_add_admin_password=intent.getStringExtra("confirm_add_admin_password");
        old_admin_password=intent.getStringExtra("old_admin_password");

        LayoutInflater li = LayoutInflater.from(ConfirmAdminRegistrationActivity.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ConfirmAdminRegistrationActivity.this);
        View promptsView = getLayoutInflater().inflate(R.layout.activity_confirm_admin_registration_prompt, null);

        tb_confirm_add_admin_name=(TextView) promptsView.findViewById(R.id.confirm_tb_add_admin_name);
        tb_confirm_add_admin_id=(TextView) promptsView.findViewById(R.id.confirm_tb_add_admin_id);
        tb_confirm_add_admin_email_id=(TextView) promptsView.findViewById(R.id.confirm_tb_add_admin_email_id);
        tb_confirm_add_admin_branch=(TextView) promptsView.findViewById(R.id.confirm_tb_add_admin_branch);

        tb_confirm_add_admin_name.setText(confirm_add_admin_name);
        tb_confirm_add_admin_id.setText(confirm_add_admin_id);
        tb_confirm_add_admin_email_id.setText(confirm_add_admin_email_id);
        tb_confirm_add_admin_branch.setText(confirm_add_admin_branch);

        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle("Please Confirm the Details");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                addadmin_result = mydb.insert_update_Data_Admin(confirm_add_admin_name, confirm_add_admin_id, confirm_add_admin_email_id, confirm_add_admin_branch, confirm_add_admin_password,update_admin);

                                if(update_admin.equals("no")) {
                                    if (addadmin_result == true) {
                                        Toast.makeText(ConfirmAdminRegistrationActivity.this, "Registration Successful...", Toast.LENGTH_LONG).show();
                                        dialog.cancel();
                                        AlertDialog.Builder admin_id_message = new AlertDialog.Builder(ConfirmAdminRegistrationActivity.this);
                                        admin_id_message.setCancelable(false);
                                        admin_id_message.setMessage(Html.fromHtml("Admin Id <font color = '#2e91d3' > " + confirm_add_admin_id + " </font> is Considered as a Username "));
                                        admin_id_message.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                userSignInData=new UserSignInData(ConfirmAdminRegistrationActivity.this);
                                                Intent intent=new Intent(ConfirmAdminRegistrationActivity.this, UpdateAdminDetails.class);
                                                intent.putExtra("username_signin",userSignInData.getUsername_signin());
                                                intent.putExtra("password_signin",userSignInData.getPassword_signin());
                                                startActivity(intent);
                                                finishAffinity();
                                            }
                                        });
                                        admin_id_message.create().show();
                                    } else {

                                        Toast.makeText(ConfirmAdminRegistrationActivity.this, "Admin is already Ragistered", Toast.LENGTH_LONG).show();
                                        dialog.cancel();
                                        finish();
                                    }
                                }else{
                                    Toast.makeText(ConfirmAdminRegistrationActivity.this, "Updated Successfully...", Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                    if(old_admin_password!=confirm_add_admin_password){
                                        UserSignInData userSignInData=new UserSignInData(ConfirmAdminRegistrationActivity.this);
                                        userSignInData.setPassword_signin(confirm_add_admin_password);
                                    }
                                    Intent intent1=new Intent(ConfirmAdminRegistrationActivity.this,UpdateAdminDetails.class);
                                    intent1.putExtra("username_signin",confirm_add_admin_id);
                                    intent1.putExtra("password_signin",confirm_add_admin_password);
                                    startActivity(intent1);
                                    finishAffinity();
                                }
                            }
                        })
                .setNegativeButton("Back",
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

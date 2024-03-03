package com.example.pathik.studentmanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmStudentRegistrationActivity extends AppCompatActivity {


    private TextView tb_confirm_addstudent_date_of_birth,tb_confirm_addstudent_firstname,tb_confirm_addstudent_middlename,tb_confirm_addstudent_lastname,
            tb_confirm_addstudent_email,tb_confirm_addstudent_mobileno,tb_confirm_addstudent_address,tb_confirm_addstudent_state,tb_confirm_addstudent_enrollmentno,
            tb_confirm_addstudent_institutename,tb_confirm_addstudent_gender,tb_confirm_addstudent_branch,tb_confirm_addstudent_semester;

    private static String confirm_addstudent_date_of_birth,confirm_addstudent_firstname,confirm_addstudent_middlename,confirm_addstudent_lastname
            ,confirm_addstudent_gender,confirm_addstudent_email,confirm_addstudent_mobileno,confirm_addstudent_address,confirm_addstudent_state
            ,confirm_addstudent_enrollmentno,confirm_addstudent_branch,confirm_addstudent_semester,confirm_addstudent_institutename,
            confirm_addstudent_password,update,old_student_password,isAdmin;
    private DataBaseHelperAppClass mydb;
    private boolean addstudent_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_student_registration);

        mydb =new DataBaseHelperAppClass(this);
        Intent intent=getIntent();

        update = intent.getStringExtra("update");
        isAdmin=intent.getStringExtra("isAdmin");

        confirm_addstudent_firstname=intent.getStringExtra("confirm_addstudent_firstname");
        confirm_addstudent_middlename=intent.getStringExtra("confirm_addstudent_middlename");
        confirm_addstudent_lastname=intent.getStringExtra("confirm_addstudent_lastname");
        confirm_addstudent_date_of_birth=intent.getStringExtra("confirm_addstudent_date_of_birth");
        confirm_addstudent_gender=intent.getStringExtra("confirm_addstudent_gender_value");
        confirm_addstudent_email=intent.getStringExtra("confirm_addstudent_email");
        confirm_addstudent_mobileno=intent.getStringExtra("confirm_addstudent_mobileno");
        confirm_addstudent_address=intent.getStringExtra("confirm_addstudent_address");
        confirm_addstudent_state=intent.getStringExtra("confirm_addstudent_state");
        confirm_addstudent_enrollmentno=intent.getStringExtra("confirm_addstudent_enrollmentno");
        confirm_addstudent_branch=intent.getStringExtra("confirm_addstudent_branch");
        confirm_addstudent_semester=intent.getStringExtra("confirm_addstudent_semester");
        confirm_addstudent_institutename=intent.getStringExtra("confirm_addstudent_institutename");
        confirm_addstudent_password=intent.getStringExtra("confirm_addstudent_password");
        old_student_password=intent.getStringExtra("old_student_password");


        LayoutInflater li = LayoutInflater.from(ConfirmStudentRegistrationActivity.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ConfirmStudentRegistrationActivity.this);
        View promptsView = getLayoutInflater().inflate(R.layout.activity_confirm_student_registration_prompt, null);

        tb_confirm_addstudent_firstname=(TextView) promptsView.findViewById(R.id.confirm_tb_addstudent_firstname);
        tb_confirm_addstudent_middlename=(TextView) promptsView.findViewById(R.id.confirm_tb_addstudent_middlename);
        tb_confirm_addstudent_lastname=(TextView) promptsView.findViewById(R.id.confirm_tb_addstudent_lastname);
        tb_confirm_addstudent_date_of_birth=(TextView) promptsView.findViewById(R.id.confirm_tb_addstudent_date_of_birth);
        tb_confirm_addstudent_gender=(TextView) promptsView.findViewById(R.id.confirm_tb_addstudent_gender);
        tb_confirm_addstudent_email=(TextView) promptsView.findViewById(R.id.confirm_tb_addstudent_email);
        tb_confirm_addstudent_mobileno=(TextView) promptsView.findViewById(R.id.confirm_tb_addstudent_mobileno);
        tb_confirm_addstudent_address=(TextView) promptsView.findViewById(R.id.confirm_tb_addstudent_address);
        tb_confirm_addstudent_state=(TextView) promptsView.findViewById(R.id.confirm_tb_addstudent_state);
        tb_confirm_addstudent_enrollmentno=(TextView) promptsView.findViewById(R.id.confirm_tb_addstudent_enrollmentno);
        tb_confirm_addstudent_branch=(TextView) promptsView.findViewById(R.id.confirm_spinner_addstudent_branch);
        tb_confirm_addstudent_semester=(TextView) promptsView.findViewById(R.id.confirm_spinner_addstudent_semester);
        tb_confirm_addstudent_institutename=(TextView) promptsView.findViewById(R.id.confirm_tb_addstudent_institutename);

        tb_confirm_addstudent_firstname.setText(confirm_addstudent_firstname);
        tb_confirm_addstudent_middlename.setText(confirm_addstudent_middlename);
        tb_confirm_addstudent_lastname.setText(confirm_addstudent_lastname);
        tb_confirm_addstudent_date_of_birth.setText(confirm_addstudent_date_of_birth);
        tb_confirm_addstudent_gender.setText(confirm_addstudent_gender);
        tb_confirm_addstudent_email.setText(confirm_addstudent_email);
        tb_confirm_addstudent_mobileno.setText(confirm_addstudent_mobileno);
        tb_confirm_addstudent_address.setText(confirm_addstudent_address);
        tb_confirm_addstudent_state.setText(confirm_addstudent_state);
        tb_confirm_addstudent_enrollmentno.setText(confirm_addstudent_enrollmentno);
        tb_confirm_addstudent_branch.setText(confirm_addstudent_branch);
        tb_confirm_addstudent_semester.setText(confirm_addstudent_semester);
        tb_confirm_addstudent_institutename.setText(confirm_addstudent_institutename);

        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle("Please Confirm the Details");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {



                                    addstudent_result = mydb.insert_update_Data_Student(confirm_addstudent_firstname, confirm_addstudent_middlename, confirm_addstudent_lastname, confirm_addstudent_date_of_birth
                                            , confirm_addstudent_gender, confirm_addstudent_email, confirm_addstudent_mobileno, confirm_addstudent_address, confirm_addstudent_state, confirm_addstudent_enrollmentno,
                                            confirm_addstudent_branch, confirm_addstudent_semester, confirm_addstudent_institutename, confirm_addstudent_password,update,"0");

                                    if(update.equals("no")) {
                                        if (addstudent_result == true) {
                                            Toast.makeText(ConfirmStudentRegistrationActivity.this, "Registration Successful...", Toast.LENGTH_LONG).show();
                                            dialog.cancel();
                                            AlertDialog.Builder enrollmentno_message = new AlertDialog.Builder(ConfirmStudentRegistrationActivity.this);
                                            enrollmentno_message.setCancelable(false);
                                            enrollmentno_message.setMessage(Html.fromHtml("Your Enrollment Number <font color = '#2e91d3' > " + confirm_addstudent_enrollmentno + " </font> is Considered as a Username "));
                                            enrollmentno_message.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                    startActivity(new Intent(ConfirmStudentRegistrationActivity.this, SignInActivity.class));
                                                    finishAffinity();
                                                }
                                            });
                                            enrollmentno_message.create().show();
                                        } else {

                                            Toast.makeText(ConfirmStudentRegistrationActivity.this, "Student is already Ragistered", Toast.LENGTH_LONG).show();
                                            dialog.cancel();
                                            finish();
                                        }
                                    }else{
                                        Toast.makeText(ConfirmStudentRegistrationActivity.this, "Updated Successfully...", Toast.LENGTH_LONG).show();
                                        dialog.cancel();
                                        if(isAdmin.equals("no")) {
                                            if (old_student_password != confirm_addstudent_password) {
                                                UserSignInData userSignInData = new UserSignInData(ConfirmStudentRegistrationActivity.this);
                                                userSignInData.setPassword_signin(confirm_addstudent_password);
                                            }
                                        }
                                        Intent intent=new Intent(ConfirmStudentRegistrationActivity.this,UpdateStudentDetails.class);
                                        intent.putExtra("username_signin",confirm_addstudent_enrollmentno);
                                        intent.putExtra("isAdmin",isAdmin);
                                        intent.putExtra("password_signin",confirm_addstudent_password);
                                        startActivity(intent);
                                        if(isAdmin.equals("yes")) {
                                            finish();
                                        }
                                        else {
                                            finishAffinity();
                                        }
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

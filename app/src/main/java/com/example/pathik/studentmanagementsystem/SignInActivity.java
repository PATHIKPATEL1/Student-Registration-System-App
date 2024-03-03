package com.example.pathik.studentmanagementsystem;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    private String signin="student";
    private EditText tb_username_signin,tb_password_signin;
    private Button btn_signin;
    private ConstraintLayout constraintLayout_signup;
    private DataBaseHelperAppClass mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mydb =new DataBaseHelperAppClass(this);
        signin="student";

        btn_signin=findViewById(R.id.btn_signin);
        constraintLayout_signup=findViewById(R.id.constraintLayout_signup);
        tb_username_signin=findViewById(R.id.tb_username_signin);
        tb_password_signin=findViewById(R.id.tb_password_signin);
        tb_username_signin.addTextChangedListener(signinTextWatcher);
        tb_password_signin.addTextChangedListener(signinTextWatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tb_password_signin.setText("");
    }

    private TextWatcher signinTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           String username_signin=tb_username_signin.getText().toString().trim();
           String password_signin=tb_password_signin.getText().toString().trim();
           btn_signin.setEnabled(!username_signin.isEmpty() && !password_signin.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    };





    public void signIn(View v) {
        String username_signin = tb_username_signin.getText().toString();
        String password_signin = tb_password_signin.getText().toString();

        if (signin.equals("student")) {
            if (mydb.signinCheck_Student(username_signin,password_signin)) {

                UserSignInData userSignInData=new UserSignInData(SignInActivity.this);
                userSignInData.setSignin(signin);
                userSignInData.setUsername_signin(username_signin);
                userSignInData.setPassword_signin(password_signin);
                Intent intent=new Intent(SignInActivity.this,UpdateStudentDetails.class);
                intent.putExtra("username_signin",username_signin);
                intent.putExtra("password_signin",password_signin);
                Toast.makeText(SignInActivity.this, "SignIn Successfully...", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            } else {

                Toast.makeText(SignInActivity.this,  Html.fromHtml("<font color='#FF0000'>Invalid username or password</font>"), Toast.LENGTH_LONG).show();
            }
        }
        else {
            if (mydb.signinCheck_Admin(username_signin,password_signin)) {


                UserSignInData userSignInData=new UserSignInData(SignInActivity.this);
                userSignInData.setSignin(signin);
                userSignInData.setUsername_signin(username_signin);
                userSignInData.setPassword_signin(password_signin);
                Intent intent=new Intent(SignInActivity.this,UpdateAdminDetails.class);
                intent.putExtra("username_signin",username_signin);
                intent.putExtra("password_signin",password_signin);
                Toast.makeText(SignInActivity.this, "SignIn Successfully...", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(SignInActivity.this, Html.fromHtml("<font color='#FF0000'>Invalid username or password</font>"), Toast.LENGTH_LONG).show();
            }
        }

    }






    public void changesigninlogo(View view) {

        ImageView ic_signin = findViewById(R.id.ic_signin);
        TextView txt_signin=findViewById(R.id.txt_signin);

        tb_username_signin.setText("");
        tb_password_signin.setText("");
        tb_username_signin.clearFocus();
        tb_password_signin.clearFocus();

        if (signin.equals("student")) {
            signin = "admin";
            ic_signin.setImageResource(R.drawable.ic_admin);
            ic_signin.setContentDescription(getResources().getString(R.string.ic_admin_signin));
            txt_signin.setText(getResources().getString(R.string.txt_admin_signin));
            constraintLayout_signup.setVisibility(View.INVISIBLE);
            Toast.makeText(SignInActivity.this,"Admin Signin",Toast.LENGTH_LONG).show();

        }
        else if (signin.equals("admin"))
        {
            signin = "student";
            ic_signin.setImageResource(R.drawable.ic_student);
            ic_signin.setContentDescription(getResources().getString(R.string.ic_student_signin));
            txt_signin.setText(getResources().getString(R.string.txt_student_signin));
            constraintLayout_signup .setVisibility(View.VISIBLE);
            Toast.makeText(SignInActivity.this,"Student Signin",Toast.LENGTH_LONG).show();

        }
    }

    public void signUp(View view)
    {
        Intent intent=new Intent(SignInActivity.this,StudentRegistrationActivity.class);
        intent.putExtra("update","no");
        startActivity(intent);
    }
}

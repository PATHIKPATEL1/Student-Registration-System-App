package com.example.pathik.studentmanagementsystem;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSignInData {
    SharedPreferences sharedPreferences;
    Context context;
    private String username_signin;
    private String password_signin;
    private String signin;


    public void removeSignInData()
    {
        sharedPreferences.edit().clear().commit();
    }

    public String getSignin() {
        signin=sharedPreferences.getString("SignIn_user","");
        return signin;
    }

    public void setSignin(String signin) {
        this.signin = signin;
        sharedPreferences.edit().putString("SignIn_user",signin).commit();
    }


    public String getUsername_signin() {
        username_signin=sharedPreferences.getString("SignIn_username_data","");
        return username_signin;
    }

    public void setUsername_signin(String username_signin) {
        this.username_signin = username_signin;
        sharedPreferences.edit().putString("SignIn_username_data",username_signin).commit();
    }

    public String getPassword_signin() {
        password_signin=sharedPreferences.getString("SignIn_password_data","");
        return password_signin;
    }

    public void setPassword_signin(String password_signin) {
        this.password_signin = password_signin;
        sharedPreferences.edit().putString("SignIn_password_data",password_signin).commit();

    }

    public UserSignInData(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("UserSignInInfo",context.MODE_PRIVATE);

    }
}

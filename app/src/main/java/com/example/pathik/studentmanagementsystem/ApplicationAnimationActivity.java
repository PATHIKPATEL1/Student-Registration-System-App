package com.example.pathik.studentmanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ApplicationAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_animation);

        final UserSignInData userSignInData=new UserSignInData(ApplicationAnimationActivity.this);
        final ImageView animation_app_logo=findViewById(R.id.img_app_logo_animation);

        final Animation AppLogoAnimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.app_logo_animation);
        AppLogoAnimation.setFillAfter(false);
        animation_app_logo.setAnimation(AppLogoAnimation);
        AppLogoAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(userSignInData.getSignin()!="" && userSignInData.getUsername_signin()!="" && userSignInData.getPassword_signin()!="")
                {
                    if((userSignInData.getSignin()).equals("admin")){

                        Intent intent = new Intent(ApplicationAnimationActivity.this, UpdateAdminDetails.class);
                        intent.putExtra("username_signin", userSignInData.getUsername_signin());
                        intent.putExtra("password_signin", userSignInData.getPassword_signin());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        ApplicationAnimationActivity.this.finish();

                    }
                    else {
                        Intent intent = new Intent(ApplicationAnimationActivity.this, UpdateStudentDetails.class);
                        intent.putExtra("username_signin", userSignInData.getUsername_signin());
                        intent.putExtra("password_signin", userSignInData.getPassword_signin());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        ApplicationAnimationActivity.this.finish();
                    }
                }
                else{
                    Intent intent1=new Intent(ApplicationAnimationActivity.this,SignInActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent1);
                    ApplicationAnimationActivity.this.finish();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    }


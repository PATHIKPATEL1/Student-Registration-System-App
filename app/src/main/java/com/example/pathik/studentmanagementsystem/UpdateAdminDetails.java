package com.example.pathik.studentmanagementsystem;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.Cursor;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.text.Html;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.TextView;
        import android.widget.Toast;

public class UpdateAdminDetails extends AppCompatActivity {


    private TextView tb_update_details_add_admin_name,tb_update_details_add_admin_id,tb_update_details_add_admin_email_id,tb_update_details_add_admin_branch;
    private DataBaseHelperAppClass mydb;
    private Cursor adminDetails;
    private  String admin_name,admin_id,admin_email_id,admin_branch,admin_password,username_signin,password_signin;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_admin_details);


            Intent intent = getIntent();
         username_signin = intent.getStringExtra("username_signin");
         password_signin = intent.getStringExtra("password_signin");


        Toolbar toolbar =  findViewById(R.id.update_details_admin_toolbar);
        toolbar.setTitle("Admin "+username_signin+"'s Information");
        setSupportActionBar(toolbar);

        try {
            mydb = new DataBaseHelperAppClass(this);


            tb_update_details_add_admin_name = (TextView) findViewById(R.id.update_details_add_admin_name);
            tb_update_details_add_admin_id = (TextView) findViewById(R.id.update_details_add_admin_id);
            tb_update_details_add_admin_email_id = (TextView) findViewById(R.id.update_details_add_admin_email_id);
            tb_update_details_add_admin_branch = (TextView) findViewById(R.id.update_details_add_admin_branch);


            adminDetails = mydb.getAdminDetails(username_signin, password_signin);

            if(adminDetails.getCount()<=0)
            {
                Intent intent1=new Intent(UpdateAdminDetails.this,SignInActivity.class);
                startActivity(intent1);
                finish();
            }
            else {
                adminDetails.moveToNext();
                admin_name = adminDetails.getString(0);
                admin_id = adminDetails.getString(1);
                admin_email_id = adminDetails.getString(2);
                admin_branch = adminDetails.getString(3);
                admin_password = adminDetails.getString(4);
                adminDetails.close();

                tb_update_details_add_admin_name.setText(admin_name);
                tb_update_details_add_admin_id.setText(admin_id);
                tb_update_details_add_admin_email_id.setText(admin_email_id);
                tb_update_details_add_admin_branch.setText(admin_branch);
            }
        }catch (Exception e){}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_option_admin_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.adminDetailsUpdateMenu:

                Intent intent=new Intent(UpdateAdminDetails.this,AdminRegistrationActivity.class);
                intent.putExtra("update_admin","yes");
                intent.putExtra("confirm_add_admin_name",admin_name);
                intent.putExtra("confirm_add_admin_id",admin_id);
                intent.putExtra("confirm_add_admin_email_id",admin_email_id);
                intent.putExtra("confirm_add_admin_branch",admin_branch);
                intent.putExtra("confirm_add_admin_password",admin_password);
                startActivity(intent);
                return true;


            case R.id.adminDetailsSignOutMenu:


                final AlertDialog.Builder signout_message = new AlertDialog.Builder(UpdateAdminDetails.this);
                signout_message.setCancelable(false);
                signout_message.setMessage(Html.fromHtml("Are you sure you want to sign out?"));
                signout_message.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new UserSignInData(UpdateAdminDetails.this).removeSignInData();
                        Toast.makeText(UpdateAdminDetails.this,"SignOut Successfully...",Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(UpdateAdminDetails.this,SignInActivity.class);
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

            case R.id.adminAddAdminMenu:
                Intent intent2=new Intent(UpdateAdminDetails.this,AdminRegistrationActivity.class);
                intent2.putExtra("update_admin","no");
                startActivity(intent2);
                return true;

            case R.id.admin_studentDetailsMenu:
                Intent intent1=new Intent(UpdateAdminDetails.this,Admin_StudentDetailsActivity.class);
                intent1.putExtra("isAdmin","yes");
                intent1.putExtra("username_signin",username_signin);
                intent1.putExtra("password_signin",password_signin);
                startActivity(intent1);
                return true;

            case R.id.admin_share_message_to_student:
                startActivity(new Intent(UpdateAdminDetails.this,ShareMessageToStudent.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}

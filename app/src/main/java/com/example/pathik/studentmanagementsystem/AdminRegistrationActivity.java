package com.example.pathik.studentmanagementsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AdminRegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

private static String add_admin_name,add_admin_id,add_admin_email_id,add_admin_branch,add_admin_password,emailPattern = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$",update_admin="no",old_admin_password;

private EditText tb_add_admin_name,tb_add_admin_id,tb_add_admin_email,tb_add_admin_password;
private boolean emailValidation;
private Spinner spinner_add_admin_branch;
private Button btn_add_admin_done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        Toolbar toolbar = findViewById(R.id.admin_registration_toolbar);
        toolbar.setTitle("Create new admin");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btn_add_admin_done = findViewById(R.id.btn_add_admin_done);
        tb_add_admin_name = findViewById(R.id.tb_add_admin_name);
        tb_add_admin_id = findViewById(R.id.tb_add_admin_id);
        tb_add_admin_email = findViewById(R.id.tb_add_admin_email);
        spinner_add_admin_branch = findViewById(R.id.spinner_add_admin_branch);
        tb_add_admin_password = findViewById(R.id.tb_add_admin_password);

        Intent intent = getIntent();
        update_admin = intent.getStringExtra("update_admin");

        if (update_admin.equals("yes")) {

            tb_add_admin_name.setText(intent.getStringExtra("confirm_add_admin_name"));
            tb_add_admin_id.setText(intent.getStringExtra("confirm_add_admin_id"));
            tb_add_admin_id.setEnabled(false);
            tb_add_admin_email.setText(intent.getStringExtra("confirm_add_admin_email_id"));
            spinner_add_admin_branch.setSelection(getIndex(spinner_add_admin_branch, intent.getStringExtra("confirm_add_admin_branch")));
            tb_add_admin_password.setText(intent.getStringExtra("confirm_add_admin_password"));
            old_admin_password=intent.getStringExtra("confirm_add_admin_password");
            Button changeName = findViewById(R.id.btn_add_admin_done);
            changeName.setText("UPDATE");
        }

        tb_add_admin_name.addTextChangedListener(doneTextWatcher);
        tb_add_admin_id.addTextChangedListener(doneTextWatcher);
        tb_add_admin_email.addTextChangedListener(doneTextWatcher);
        tb_add_admin_password.addTextChangedListener(doneTextWatcher);

        spinner_add_admin_branch.setOnItemSelectedListener(this);
    }
@Override
public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    stringValue();
    emailValidation=add_admin_email_id.matches(emailPattern);
    btn_add_admin_done.setEnabled(!add_admin_name.isEmpty() && !add_admin_id.isEmpty() && emailValidation && !add_admin_password.isEmpty() && !add_admin_branch.equals("--Select Branch--"));
}

@Override
public void onNothingSelected(AdapterView<?> adapterView) {}

private TextWatcher doneTextWatcher=new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
@Override
public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        stringValue();
        emailValidation=add_admin_email_id.matches(emailPattern);
        btn_add_admin_done.setEnabled(!add_admin_name.isEmpty() && !add_admin_id.isEmpty() && emailValidation && !add_admin_password.isEmpty() && !add_admin_branch.equals("--Select Branch--"));
        }
@Override
public void afterTextChanged(Editable editable) {}
        };

private void stringValue()
        {

            add_admin_name=tb_add_admin_name.getText().toString().trim();
            add_admin_id=tb_add_admin_id.getText().toString().trim();
            add_admin_email_id= tb_add_admin_email.getText().toString().trim();
            add_admin_password =tb_add_admin_password.getText().toString().trim();

            add_admin_branch= String.valueOf(spinner_add_admin_branch.getSelectedItem()).trim();

        }

public void Done(View view) {

        View v=this.getCurrentFocus();
        if(v != null) {
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
        stringValue();
        Intent intent=new Intent(AdminRegistrationActivity.this,ConfirmAdminRegistrationActivity.class);
        intent.putExtra("update_admin",update_admin);
        intent.putExtra("confirm_add_admin_name",add_admin_name);
        intent.putExtra("confirm_add_admin_id",add_admin_id);
        intent.putExtra("confirm_add_admin_email_id",add_admin_email_id);
        intent.putExtra("confirm_add_admin_branch",add_admin_branch);
        intent.putExtra("confirm_add_admin_password",add_admin_password);
        intent.putExtra("old_admin_password",old_admin_password);
        startActivity(intent);

        }

private int getIndex(Spinner spinner,String myString)
        {
        for (int i=0;i<spinner.getCount();i++){
        if(spinner.getItemAtPosition(i).toString().equals(myString)){
        return i;
        }
        }
        return 0;
        }


@Override
public boolean onCreateOptionsMenu(Menu menu) {
        if(update_admin.equals("yes")) {
        getMenuInflater().inflate(R.menu.sign_out_menu, menu);
        return true;
        }
        return true;
        }

@Override
public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
        case R.id.ic_signout:


final AlertDialog.Builder signout_message = new AlertDialog.Builder(AdminRegistrationActivity.this);
        signout_message.setCancelable(false);
        signout_message.setMessage(Html.fromHtml("Are you sure you want to sign out?"));
        signout_message.setPositiveButton("YES", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialogInterface, int i) {

        new UserSignInData(AdminRegistrationActivity.this).removeSignInData();
        Toast.makeText(AdminRegistrationActivity.this,"SignOut Successfully...",Toast.LENGTH_LONG).show();
        Intent intent2=new Intent(AdminRegistrationActivity.this,SignInActivity.class);
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


default:
        return super.onOptionsItemSelected(item);
        }

        }
        }

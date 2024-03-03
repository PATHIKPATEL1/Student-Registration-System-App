package com.example.pathik.studentmanagementsystem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class StudentRegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static String addstudent_date_of_birth,addstudent_firstname,addstudent_middlename,addstudent_lastname
            ,addstudent_gender_value,addstudent_email,addstudent_mobileno,addstudent_address,addstudent_state
            ,addstudent_enrollmentno,addstudent_branch,addstudent_semester,addstudent_institutename,addstudent_password,
            emailPattern = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$",data_of_birth_pattern="([0-9]{2})/([0-9]{2})/([0-9]{4})",update="no",
            old_student_password,isAdmin;

    private EditText tb_addstudent_date_of_birth,tb_addstudent_firstname,tb_addstudent_middlename,tb_addstudent_lastname,
    tb_addstudent_email,tb_addstudent_mobileno,tb_addstudent_address,tb_addstudent_state,tb_addstudent_enrollmentno,
            tb_addstudent_institutename,tb_addstudent_password;
    private boolean emailValidation,mobilenoValidation,date_of_birth_validation;
    private RadioGroup radiobtn_addstudent_gender;
    private RadioButton radiobtn_addstudent_gender_value;
    private Spinner spinner_addstudent_branch,spinner_addstudent_semester;
    private ImageView ic_addstudent_date_of_birth;
    private Button btn_addstudent_done;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static int radiobtn_addstudent_gender_selectedid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        Toolbar toolbar =  findViewById(R.id.student_registration_toolbar);
        toolbar.setTitle("Registration");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btn_addstudent_done=findViewById(R.id.btn_addstudent_done);
        tb_addstudent_date_of_birth=findViewById(R.id.tb_addstudent_date_of_birth);
        ic_addstudent_date_of_birth=findViewById(R.id.ic_addstudent_date_of_birth);
        tb_addstudent_firstname=findViewById(R.id.tb_addstudent_firstname);
        tb_addstudent_middlename=findViewById(R.id.tb_addstudent_middlename);
        tb_addstudent_lastname=findViewById(R.id.tb_addstudent_lastname);
        radiobtn_addstudent_gender=findViewById(R.id.radiobtn_addstudent_gender);
        tb_addstudent_email=findViewById(R.id.tb_addstudent_email);
        tb_addstudent_mobileno=findViewById(R.id.tb_addstudent_mobileno);
        tb_addstudent_address=findViewById(R.id.tb_addstudent_address);
        tb_addstudent_state=findViewById(R.id.tb_addstudent_state);
        tb_addstudent_enrollmentno=findViewById(R.id.tb_addstudent_enrollmentno);
        spinner_addstudent_branch=findViewById(R.id.spinner_addstudent_branch);
        spinner_addstudent_semester=findViewById(R.id.spinner_addstudent_semester);
        tb_addstudent_institutename=findViewById(R.id.tb_addstudent_institutename);
        tb_addstudent_password=findViewById(R.id.tb_addstudent_password);

        Intent intent=getIntent();
        update=intent.getStringExtra("update");
        isAdmin=intent.getStringExtra("isAdmin");

        if(update.equals("yes"))
        {

            tb_addstudent_firstname.setText(intent.getStringExtra("confirm_addstudent_firstname"));
            tb_addstudent_middlename.setText(intent.getStringExtra("confirm_addstudent_middlename"));
            tb_addstudent_lastname.setText(intent.getStringExtra("confirm_addstudent_lastname"));
            tb_addstudent_date_of_birth.setText(intent.getStringExtra("confirm_addstudent_date_of_birth"));
            if( (intent.getStringExtra("confirm_addstudent_gender_value")).equals("Female")){
               radiobtn_addstudent_gender.check(R.id.radiobtn_addstudent_female);
             }
            tb_addstudent_email.setText(intent.getStringExtra("confirm_addstudent_email"));
            tb_addstudent_mobileno.setText(intent.getStringExtra("confirm_addstudent_mobileno"));
            tb_addstudent_address.setText(intent.getStringExtra("confirm_addstudent_address"));
            tb_addstudent_state.setText(intent.getStringExtra("confirm_addstudent_state"));
            tb_addstudent_enrollmentno.setText(intent.getStringExtra("confirm_addstudent_enrollmentno"));
            tb_addstudent_enrollmentno.setEnabled(false);
            spinner_addstudent_branch.setSelection(getIndex(spinner_addstudent_branch,intent.getStringExtra("confirm_addstudent_branch")));
            spinner_addstudent_semester.setSelection(getIndex(spinner_addstudent_semester,intent.getStringExtra("confirm_addstudent_semester")));
            tb_addstudent_institutename.setText(intent.getStringExtra("confirm_addstudent_institutename"));
            tb_addstudent_password.setText(intent.getStringExtra("confirm_addstudent_password"));
            old_student_password=intent.getStringExtra("confirm_addstudent_password");

            Button changeName=findViewById(R.id.btn_addstudent_done);
            changeName.setText("UPDATE");
        }

        tb_addstudent_date_of_birth.addTextChangedListener(doneTextWatcher);
        tb_addstudent_firstname.addTextChangedListener(doneTextWatcher);
        tb_addstudent_middlename.addTextChangedListener(doneTextWatcher);
        tb_addstudent_lastname.addTextChangedListener(doneTextWatcher);
        tb_addstudent_email.addTextChangedListener(doneTextWatcher);
        tb_addstudent_mobileno.addTextChangedListener(doneTextWatcher);
        tb_addstudent_address.addTextChangedListener(doneTextWatcher);
        tb_addstudent_state.addTextChangedListener(doneTextWatcher);
        tb_addstudent_enrollmentno.addTextChangedListener(doneTextWatcher);
        tb_addstudent_institutename.addTextChangedListener(doneTextWatcher);
        tb_addstudent_password.addTextChangedListener(doneTextWatcher);

        spinner_addstudent_branch.setOnItemSelectedListener(this);
        spinner_addstudent_semester.setOnItemSelectedListener(this);

        ic_addstudent_date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day =cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(StudentRegistrationActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        mDateSetListener= new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String temp_month= Integer.toString(month);
                String temp_day= Integer.toString(day);
                if(temp_day.length()==1){
                    temp_day="0"+temp_day;
                }

                if(temp_month.length()==1){
                temp_month="0"+temp_month;
                }
                addstudent_date_of_birth = temp_day + "/" + temp_month + "/" + year;
                tb_addstudent_date_of_birth.setText(addstudent_date_of_birth);
            }
        };
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        stringValue();
        emailValidation=addstudent_email.matches(emailPattern);
        mobilenoValidation= (addstudent_mobileno.length()==10);
        date_of_birth_validation=(addstudent_date_of_birth.matches(data_of_birth_pattern));
        btn_addstudent_done.setEnabled(!addstudent_firstname.isEmpty() && !addstudent_middlename.isEmpty() && !addstudent_lastname.isEmpty() && emailValidation && mobilenoValidation && !addstudent_address.isEmpty() && !addstudent_state.isEmpty() && !addstudent_enrollmentno.isEmpty()
                && date_of_birth_validation && !addstudent_institutename.isEmpty() && !addstudent_password.isEmpty() && !addstudent_branch.equals("--Select Branch--") && !addstudent_semester.equals("--Select Semester--"));
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
            emailValidation=addstudent_email.matches(emailPattern);
            mobilenoValidation= (addstudent_mobileno.length()==10);
            date_of_birth_validation=(addstudent_date_of_birth.matches(data_of_birth_pattern));
            btn_addstudent_done.setEnabled(!addstudent_firstname.isEmpty() && !addstudent_middlename.isEmpty() && !addstudent_lastname.isEmpty() && emailValidation && mobilenoValidation && !addstudent_address.isEmpty() && !addstudent_state.isEmpty() && !addstudent_enrollmentno.isEmpty()
                    && date_of_birth_validation && !addstudent_institutename.isEmpty() && !addstudent_password.isEmpty() && !addstudent_branch.equals("--Select Branch--") && !addstudent_semester.equals("--Select Semester--"));
        }
        @Override
        public void afterTextChanged(Editable editable) {}
    };

    private void stringValue()
    {
        radiobtn_addstudent_gender_selectedid=radiobtn_addstudent_gender.getCheckedRadioButtonId();
        radiobtn_addstudent_gender_value=findViewById(radiobtn_addstudent_gender_selectedid);

        addstudent_firstname=tb_addstudent_firstname.getText().toString().trim();
        addstudent_middlename=tb_addstudent_middlename.getText().toString().trim();
        addstudent_lastname= tb_addstudent_lastname.getText().toString().trim();
        addstudent_date_of_birth =tb_addstudent_date_of_birth.getText().toString().trim();
        addstudent_gender_value=radiobtn_addstudent_gender_value.getText().toString().trim();
        addstudent_email=tb_addstudent_email.getText().toString().trim();
        addstudent_mobileno=tb_addstudent_mobileno.getText().toString().trim();
        addstudent_address=tb_addstudent_address.getText().toString().trim();
        addstudent_state=tb_addstudent_state.getText().toString().trim();
        addstudent_enrollmentno=tb_addstudent_enrollmentno.getText().toString().trim();
        addstudent_institutename=tb_addstudent_institutename.getText().toString().trim();
        addstudent_password=tb_addstudent_password.getText().toString().trim();

        addstudent_branch= String.valueOf(spinner_addstudent_branch.getSelectedItem()).trim();
        addstudent_semester=String.valueOf(spinner_addstudent_semester.getSelectedItem()).trim();

    }

    public void Done(View view) {

        View v=this.getCurrentFocus();
        if(v != null) {
            InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
        stringValue();
        Intent intent=new Intent(StudentRegistrationActivity.this,ConfirmStudentRegistrationActivity.class);
        intent.putExtra("update",update);
        intent.putExtra("isAdmin",isAdmin);
        intent.putExtra("confirm_addstudent_firstname",addstudent_firstname);
        intent.putExtra("confirm_addstudent_lastname",addstudent_lastname);
        intent.putExtra("confirm_addstudent_middlename",addstudent_middlename);
        intent.putExtra("confirm_addstudent_date_of_birth",addstudent_date_of_birth);
        intent.putExtra("confirm_addstudent_gender_value",addstudent_gender_value);
        intent.putExtra("confirm_addstudent_email",addstudent_email);
        intent.putExtra("confirm_addstudent_mobileno",addstudent_mobileno);
        intent.putExtra("confirm_addstudent_address",addstudent_address);
        intent.putExtra("confirm_addstudent_state",addstudent_state);
        intent.putExtra("confirm_addstudent_enrollmentno",addstudent_enrollmentno);
        intent.putExtra("confirm_addstudent_institutename",addstudent_institutename);
        intent.putExtra("confirm_addstudent_branch",addstudent_branch);
        intent.putExtra("confirm_addstudent_semester",addstudent_semester);
        intent.putExtra("confirm_addstudent_password",addstudent_password);
        intent.putExtra("old_student_password",old_student_password);

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
        if(update.equals("yes")) {
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


                final AlertDialog.Builder signout_message = new AlertDialog.Builder(StudentRegistrationActivity.this);
                signout_message.setCancelable(false);
                signout_message.setMessage(Html.fromHtml("Are you sure you want to sign out?"));
                signout_message.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new UserSignInData(StudentRegistrationActivity.this).removeSignInData();
                        Toast.makeText(StudentRegistrationActivity.this,"SignOut Successfully...",Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(StudentRegistrationActivity.this,SignInActivity.class);
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

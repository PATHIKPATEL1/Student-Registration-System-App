package com.example.pathik.studentmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_StudentDetailsActivity extends AppCompatActivity {

    private DataBaseHelperAppClass mydb;
    private ListView student_details_listview;
    private ArrayList<String> list_student;
    private ArrayAdapter<String> adapter;
    private String isAdmin,username_signin,password_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__student_details);

        Toolbar toolbar = findViewById(R.id.admin_studentDetails_toolbar);
        toolbar.setTitle("Student Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent1=getIntent();
        isAdmin=intent1.getStringExtra("isAdmin");
         username_signin = intent1.getStringExtra("username_signin");
         password_signin = intent1.getStringExtra("password_signin");

        mydb=new DataBaseHelperAppClass(Admin_StudentDetailsActivity.this);

        list_student=new ArrayList<>();

        student_details_listview=findViewById(R.id.student_details_listview);

        showStudentDetails();

    }


    private void showStudentDetails()
    {
        Cursor cursor=mydb.showAllStudentDetails();

        if(cursor.getCount()<=0)
        {
            Toast.makeText(Admin_StudentDetailsActivity.this,"Student data doesn't exist",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext())
            {
                list_student.add(cursor.getString(cursor.getColumnIndex("_ENROLLMENT_NO")));
            }

            adapter=new ArrayAdapter<>(Admin_StudentDetailsActivity.this,android.R.layout.simple_list_item_1,list_student);
            student_details_listview.setAdapter(adapter);

            registerForContextMenu(student_details_listview);
            student_details_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Cursor cursor=mydb.studentDetailsPassword(student_details_listview.getItemAtPosition(i).toString());
                    cursor.moveToNext();
                    Intent intent=new Intent(Admin_StudentDetailsActivity.this,UpdateStudentDetails.class);
                    intent.putExtra("username_signin",student_details_listview.getItemAtPosition(i).toString());
                    intent.putExtra("password_signin",cursor.getString(0));
                    intent.putExtra("isAdmin",isAdmin);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select the Action");
        menu.add(0,v.getId(),0,"Delete");

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(item.getTitle().equals("Delete")) {


            final AlertDialog.Builder delete_message = new AlertDialog.Builder(Admin_StudentDetailsActivity.this);
            delete_message.setCancelable(false);
            delete_message.setMessage(Html.fromHtml("Are you sure you want to Delete <font color='#FF0000'>"+(String) adapter.getItem(info.position)+" </font>?"));
            delete_message.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    int row=mydb.deleteStudentDetails((String) adapter.getItem(info.position));
                    String tempValue=adapter.getItem(info.position);
                    if(row==1) {
                        list_student.remove(info.position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(Admin_StudentDetailsActivity.this, tempValue + " is Removed", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(Admin_StudentDetailsActivity.this, tempValue + " is not Removed", Toast.LENGTH_LONG).show();

                    }
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            delete_message.create().show();

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.search_student_menu, menu);
            MenuItem item=menu.findItem(R.id.ic_search_student_searchview);
            SearchView searchView=(SearchView)item.getActionView();
            searchView.setQueryHint("Search Student");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.getFilter().filter(s);
                    return false;
                }
            });
            return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ic_search_student_signout:


                final AlertDialog.Builder signout_message = new AlertDialog.Builder(Admin_StudentDetailsActivity.this);
                signout_message.setCancelable(false);
                signout_message.setMessage(Html.fromHtml("Are you sure you want to sign out?"));
                signout_message.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new UserSignInData(Admin_StudentDetailsActivity.this).removeSignInData();
                        Toast.makeText(Admin_StudentDetailsActivity.this, "SignOut Successfully...", Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(Admin_StudentDetailsActivity.this, SignInActivity.class);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Admin_StudentDetailsActivity.this,UpdateAdminDetails.class);
        intent.putExtra("username_signin",username_signin);
        intent.putExtra("password_signin",password_signin);
        startActivity(intent);
        finishAffinity();

    }
}

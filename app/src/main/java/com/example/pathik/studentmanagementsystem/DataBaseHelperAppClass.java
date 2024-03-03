package com.example.pathik.studentmanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelperAppClass extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="MyAppDataBase";
    public static final String STUDENT_TABLE_NAME="StudentTable";
    public static final String ADMIN_TABLE_NAME="AdminTable";
    public static final String NOTIFICATION_TABLE="NotificationTable";

    public static final String FIRSTNAME="FIRSTNAME";
    public static final String MIDDLENAME="MIDDLENAME";
    public static final String LASTNAME="LASTNAME";
    public static final String DATE_OF_BIRTH="DATE_OF_BIRTH";
    public static final String GENDER="GENDER";
    public static final String EMAIL_ID="EMAIL_ID";
    public static final String MOBILE_NO="MOBILE_NO";
    public static final String ADDRESS="ADDRESS";
    public static final String STATE="STATE";
    public static final String _ENROLLMENT_NO="_ENROLLMENT_NO";
    public static final String BRANCH="BRANCH";
    public static final String SEMESTER="SEMESTER";
    public static final String INSTITUTE="INSTITUTE";
    public static final String PASSWORD="PASSWORD";
    public static final String LAST_NOTIFICATION_ID="LAST_NOTIFICATION_ID";

    public static final String ADMIN_NAME="ADMIN_NAME";
    public static final String _ADMIN_ID="_ADMIN_ID";
    public static final String ADMIN_EMAIL_ID="ADMIN_EMAIL_ID";
    public static final String ADMIN_BRANCH="ADMIN_BRANCH";
    public static final String ADMIN_PASSWORD="ADMIN_PASSWORD";

    public static final String MESSAGE_TITLE ="MESSAGE_TITLE";
    public static final String MESSAGE_CONTENT="MESSAGE_CONTENT";
    public static final String _MESSAGE_ID ="_MESSAGE_ID";

    public DataBaseHelperAppClass(Context context) {
        super(context, DATABASE_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+ STUDENT_TABLE_NAME + "( FIRSTNAME TEXT,MIDDLENAME TEXT,LASTNAME TEXT,DATE_OF_BIRTH DATE,GENDER TEXT," +
                "EMAIL_ID TEXT,MOBILE_NO INTEGER,ADDRESS TEXT,STATE TEXT,_ENROLLMENT_NO TEXT PRIMARY KEY,BRANCH TEXT,SEMESTER INTEGER,INSTITUTE TEXT,PASSWORD TEXT,LAST_NOTIFICATION_ID TEXT)");


        sqLiteDatabase.execSQL("CREATE TABLE "+ ADMIN_TABLE_NAME + "( ADMIN_NAME TEXT, _ADMIN_ID TEXT PRIMARY KEY,ADMIN_EMAIL_ID TEXT,ADMIN_BRANCH TEXT,ADMIN_PASSWORD TEXT )");

        ContentValues contentValues=new ContentValues();
        contentValues.put(_ADMIN_ID,"1");
        contentValues.put(ADMIN_PASSWORD,"admin");
        sqLiteDatabase.insert(ADMIN_TABLE_NAME, null, contentValues);


        sqLiteDatabase.execSQL("CREATE TABLE "+ NOTIFICATION_TABLE + "( MESSAGE_TITLE TEXT,MESSAGE_CONTENT TEXT,_MESSAGE_ID TEXT PRIMARY KEY)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ STUDENT_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ ADMIN_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insert_update_Data_Student(String FIRSTNAME, String MIDDLENAME, String LASTNAME, String DATE_OF_BIRTH, String GENDER, String EMAIL_ID,
                                              String MOBILE_NO, String ADDRESS, String STATE, String _ENROLLMENT_NO, String BRANCH, String SEMESTER, String INSTITUTE, String PASSWORD, String update,String LAST_NOTIFICATION_ID)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(this.FIRSTNAME,FIRSTNAME);
        contentValues.put(this.MIDDLENAME,MIDDLENAME);
        contentValues.put(this.LASTNAME,LASTNAME);
        contentValues.put(this.DATE_OF_BIRTH,DATE_OF_BIRTH);
        contentValues.put(this.GENDER,GENDER);
        contentValues.put(this.EMAIL_ID,EMAIL_ID);
        contentValues.put(this.MOBILE_NO,MOBILE_NO);
        contentValues.put(this.ADDRESS,ADDRESS);
        contentValues.put(this.STATE,STATE);
        contentValues.put(this._ENROLLMENT_NO,_ENROLLMENT_NO);
        contentValues.put(this.BRANCH,BRANCH);
        contentValues.put(this.SEMESTER,SEMESTER);
        contentValues.put(this.INSTITUTE,INSTITUTE);
        contentValues.put(this.PASSWORD,PASSWORD);
        long result=-1;
        if (update.equals("no")) {
            contentValues.put(this.LAST_NOTIFICATION_ID,"0");
            result = sqLiteDatabase.insert(STUDENT_TABLE_NAME, null, contentValues);
            sqLiteDatabase.close();
        }
        else{

            Cursor temp_cursor=getShareMessageId(_ENROLLMENT_NO);
            temp_cursor.moveToNext();
            String temp_id=temp_cursor.getString(temp_cursor.getColumnIndex("LAST_NOTIFICATION_ID"));
            temp_cursor.close();
            contentValues.put(this.LAST_NOTIFICATION_ID,temp_id);
            result = sqLiteDatabase.update(STUDENT_TABLE_NAME,contentValues,"_ENROLLMENT_NO = '"+_ENROLLMENT_NO+"'",null);
            sqLiteDatabase.close();
        }
        if(result==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }
    public boolean signinCheck_Student(String username, String password)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        try {
            Cursor checkExist = sqLiteDatabase.rawQuery(" SELECT "+PASSWORD+" FROM " + STUDENT_TABLE_NAME + " WHERE " + _ENROLLMENT_NO + " = '" + username+"' COLLATE NOCASE" , null);
            if (!checkExist.moveToFirst()) {
                checkExist.close();
                return false;
            }
            else {
             if((checkExist.getString(0)).equals(password)) {
                 checkExist.close();
                 return true;
             }
             else {
                 checkExist.close();
                 return false;
             }
            }
        }catch (Exception e)
        {
        }
        return false;
    }

    public Cursor getStudentDetails(String username,String password)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor studentDetails = sqLiteDatabase.rawQuery(" SELECT * FROM " + STUDENT_TABLE_NAME + " WHERE " + _ENROLLMENT_NO + " = '" + username +"'COLLATE NOCASE AND "+PASSWORD+" = '"+password+"'", null);
        return studentDetails;
    }


    public boolean signinCheck_Admin(String username, String password)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        try {
            Cursor checkExist = sqLiteDatabase.rawQuery(" SELECT "+ADMIN_PASSWORD+" FROM " + ADMIN_TABLE_NAME + " WHERE " + _ADMIN_ID + " = '" + username +"' COLLATE NOCASE", null);
            if (!checkExist.moveToFirst()) {
                checkExist.close();
                return false;
            }
            else {
                if((checkExist.getString(0)).equals(password)) {
                    checkExist.close();
                    return true;
                }
                else {
                    checkExist.close();
                    return false;
                }
            }
        }catch (Exception e)
        {
        }
        return false;
    }


    public boolean insert_update_Data_Admin(String ADMIN_NAME, String _ADMIN_ID, String ADMIN_EMAIL_ID, String ADMIN_BRANCH, String ADMIN_PASSWORD,String update_admin)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(this.ADMIN_NAME,ADMIN_NAME);
        contentValues.put(this._ADMIN_ID,_ADMIN_ID);
        contentValues.put(this.ADMIN_EMAIL_ID,ADMIN_EMAIL_ID);
        contentValues.put(this.ADMIN_BRANCH,ADMIN_BRANCH);
        contentValues.put(this.ADMIN_PASSWORD,ADMIN_PASSWORD);
        long result=-1;
        if (update_admin.equals("no")) {
            result = sqLiteDatabase.insert(ADMIN_TABLE_NAME, null, contentValues);
            sqLiteDatabase.close();
        }
        else{

            result = sqLiteDatabase.update(ADMIN_TABLE_NAME,contentValues,"_ADMIN_ID="+_ADMIN_ID,null);
            sqLiteDatabase.close();
        }
        if(result==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAdminDetails(String username,String password)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor adminDetails = sqLiteDatabase.rawQuery(" SELECT * FROM " + ADMIN_TABLE_NAME + " WHERE " + _ADMIN_ID + " = '" + username +"' COLLATE NOCASE AND "+ADMIN_PASSWORD+" = '"+password+"'", null);
        return adminDetails;
    }

    public Cursor showAllStudentDetails()
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+ STUDENT_TABLE_NAME ,null);
        return cursor;
    }

    public Cursor studentDetailsPassword(String _ENROLLMENT_NO)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT PASSWORD FROM "+ STUDENT_TABLE_NAME +" WHERE _ENROLLMENT_NO='"+_ENROLLMENT_NO+"'" ,null);
        return  cursor;
    }

    public int deleteStudentDetails(String _ENROLLMENT_NO)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        int i=sqLiteDatabase.delete(STUDENT_TABLE_NAME,"_ENROLLMENT_NO=?",new String[]{_ENROLLMENT_NO});
        return i;
    }

    public boolean insert_message(String MESSAGE_TITLE,String MESSAGE_CONTENT,String _MESSAGE_ID)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(this.MESSAGE_TITLE,MESSAGE_TITLE);
        contentValues.put(this.MESSAGE_CONTENT,MESSAGE_CONTENT);
        contentValues.put(this._MESSAGE_ID,_MESSAGE_ID);
        long result=-1;
        result = sqLiteDatabase.insert(NOTIFICATION_TABLE, null, contentValues);
        sqLiteDatabase.close();
        if(result==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getShareMessage()
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor share_message = sqLiteDatabase.rawQuery(" SELECT * FROM " + NOTIFICATION_TABLE, null);
        return share_message;
    }
    public Cursor getShareMessageId(String _ENROLLMENT_NO)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor share_message = sqLiteDatabase.rawQuery("SELECT LAST_NOTIFICATION_ID FROM " +STUDENT_TABLE_NAME+" WHERE _ENROLLMENT_NO='"+_ENROLLMENT_NO+"'", null);
        return share_message;
    }

    public void update_message_id(String _ENROLLMENT_NO, String LAST_NOTIFICATION_ID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(this._ENROLLMENT_NO, _ENROLLMENT_NO);
        contentValues.put(this.LAST_NOTIFICATION_ID, LAST_NOTIFICATION_ID);

            sqLiteDatabase.execSQL("UPDATE " + STUDENT_TABLE_NAME + " SET LAST_NOTIFICATION_ID='" + LAST_NOTIFICATION_ID + "' WHERE _ENROLLMENT_NO='" + _ENROLLMENT_NO + "'");
            sqLiteDatabase.close();

    }

    public  Cursor getMessage(String _MESSAGE_ID){

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor message = sqLiteDatabase.rawQuery("SELECT * FROM " +NOTIFICATION_TABLE+" WHERE _MESSAGE_ID='"+_MESSAGE_ID+"'", null);
        return message;

    }

}

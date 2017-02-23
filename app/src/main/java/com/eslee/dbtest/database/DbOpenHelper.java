package com.eslee.dbtest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by Administrator on 2017-02-20.
 */

public class DbOpenHelper {
    private static final String DATABASE_NAME = "healthfree.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        //생성자
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context,name,factory,version);
        }

        // 최초 DB를 만들때 한번만 호출
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.UserTable._CREATE);
            db.execSQL(DataBases.PointTable._CREATE);
            db.execSQL(DataBases.HealthTable._CREATE);
            db.execSQL(DataBases.LocationTable._CREATE);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.UserTable._TABLENAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.PointTable._TABLENAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.HealthTable._TABLENAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.LocationTable._TABLENAME);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx,DATABASE_NAME,null,DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDB.close();
    }

    // Insert DB
    public long insertUserInfo(String name, String email, String user_type){
        int userid = 1;

        ContentValues values = new ContentValues();
        values.put(DataBases.UserTable.USER_ID, userid);
        values.put(DataBases.UserTable.USER_NAME, name);
        values.put(DataBases.UserTable.EMAIL, email);
        values.put(DataBases.UserTable.USER_TYPE, user_type);
        return mDB.insert(DataBases.UserTable._TABLENAME, null, values);
    }

    // Update DB
    public boolean updateUserInfo(long id , String name, String email){
        ContentValues values = new ContentValues();
        values.put(DataBases.UserTable.USER_NAME, name);
        values.put(DataBases.UserTable.EMAIL, email);
        return mDB.update(DataBases.UserTable._TABLENAME, values, "_id="+id, null) > 0;
    }

    // Delete ID
    public boolean deleteUserInfo(long id){
        return mDB.delete(DataBases.UserTable._TABLENAME, "_id="+id, null) > 0;
    }

    // Select All
    public Cursor getAllUserInfo(){
        return mDB.query(DataBases.UserTable._TABLENAME, null, null, null, null, null, null);
    }

    // ID 컬럼 얻어 오기
    public Cursor getUserInfo(long id){
        Cursor c = mDB.query(DataBases.UserTable._TABLENAME, null,
                "_id="+id, null, null, null, null);
        if(c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

    // 이름 검색 하기 (rawQuery)
    public Cursor getMatchNameFromUserInfo(String name){
        Cursor c = mDB.rawQuery( "select * from " + DataBases.UserTable._TABLENAME + " where name='" + name + "'" , null);
        return c;
    }

    // Insert DB
    public long insertPoint(int user_id, String use_type, int use_point, String use_where, String location){
        ContentValues values = new ContentValues();
        values.put(DataBases.PointTable.USER_ID, user_id);
        values.put(DataBases.PointTable.USE_TYPE, use_type);
        values.put(DataBases.PointTable.USE_POINT, use_point);
        values.put(DataBases.PointTable.USE_WHERE, use_where);
        values.put(DataBases.PointTable.LOCATION, location);

        return mDB.insert(DataBases.PointTable._TABLENAME, null, values);
    }

    // Update DB
    public boolean updatePoint(long id , String use_where, String use_type, int point){
        ContentValues values = new ContentValues();
        values.put(DataBases.PointTable.USE_WHERE, use_where);
        values.put(DataBases.PointTable.USE_TYPE, use_type);
        values.put(DataBases.PointTable.USE_POINT, point);
        return mDB.update(DataBases.PointTable._TABLENAME, values, "_id="+id, null) > 0;
    }

    // Delete ID
    public boolean deletePoint(long id){
        return mDB.delete(DataBases.PointTable._TABLENAME, "_id="+id, null) > 0;
    }

    // Select All
    public Cursor getAllPoint(){
        return mDB.query(DataBases.PointTable._TABLENAME, null, null, null, null, null, null);
    }

    // ID 컬럼 얻어 오기
    public Cursor getPoint(long id){
        Cursor c = mDB.query(DataBases.PointTable._TABLENAME, null,
                "_id="+id, null, null, null, null);
        if(c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

    public String get_datetime(){
        String result = "";

        Date date = new Date();

        return result;
    }


}

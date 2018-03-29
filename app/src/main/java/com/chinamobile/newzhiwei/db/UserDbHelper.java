package com.chinamobile.newzhiwei.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/3/14.
 */

public class UserDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="zhiwei_db";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="users";
    public UserDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table if not exists "+TABLE_NAME+" (Id integer primary key,UserName text,Password text,SessionId text,Gesture text,Phone text,NickName text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql="drop table if exists "+TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}

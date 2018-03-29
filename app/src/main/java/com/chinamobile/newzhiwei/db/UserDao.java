package com.chinamobile.newzhiwei.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chinamobile.newzhiwei.model.bean.login.UserBean;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2018/3/14.
 */

public class UserDao {
    private Context context;
    private UserDbHelper userDbHelper;
    private SQLiteDatabase db;
    public UserDao(Context context) {
        this.context=context;
        userDbHelper=new UserDbHelper(context);
    }
    public void insertUser(UserBean userBean){
        if(userDbHelper==null){
            return;
        }
        db = userDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",userBean.getUserName());
        contentValues.put("password",userBean.getPassword());
        contentValues.put("sessionid",userBean.getSessionId());
        contentValues.put("phone",userBean.getPhone());
        contentValues.put("nickname",userBean.getNickName());
        db.insert("users",null,contentValues);
        db.close();
    }

    public UserBean queryUserByUserName(String userName){
        if(userDbHelper==null){
            return null;
        }
        db=userDbHelper.getReadableDatabase();
        UserBean userBean = new UserBean();
        Cursor cursor=null;
        String sql="select * from users where UserName=?";
        cursor = db.rawQuery(sql, new String[]{userName});
        while(cursor.moveToNext()){
            userBean.setUserName(cursor.getString(0));
            userBean.setPassword(cursor.getString(1));
            userBean.setSessionId(cursor.getString(2));
            userBean.setPhone(cursor.getString(3));
            userBean.setNickName(cursor.getString(4));
        }
        cursor.close();
        db.close();
        return userBean;
    }
    public Flowable<UserBean> readLastUser(){
        return Flowable.create(new FlowableOnSubscribe<UserBean>() {
            @Override
            public void subscribe(FlowableEmitter<UserBean> emitter) throws Exception {
                if (userDbHelper == null) {
                    return ;
                }else{
                    db = userDbHelper.getReadableDatabase();
                    Cursor cursor = null;
                    try{
                        UserBean bean = new UserBean();
                        String sql = "select * from users" ;
                        cursor = db.rawQuery(sql, null);
                        while (cursor.moveToNext()) {
                            bean.setUserName(cursor.getString(0));
                            bean.setPassword(cursor.getString(1));
                            bean.setSessionId(cursor.getString(2));
                            bean.setGesture(cursor.getString(3));
                            bean.setPhone(cursor.getString(4));
                            bean.setNickName(cursor.getString(5));
                        }
                        emitter.onNext(bean);
                        emitter.onComplete();
                    }finally {
                         if(cursor!=null){
                             cursor.close();
                         }
                         db.close();
                    }

                }
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public UserBean getLastUser() {
        if (userDbHelper == null) {
            return null;
        }
        db = userDbHelper.getReadableDatabase();
        UserBean bean = new UserBean();
        Cursor cursor = null;
        String sql = "select * from users" ;
        cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            bean.setUserName(cursor.getString(1));
            bean.setPassword(cursor.getString(2));
            bean.setSessionId(cursor.getString(3));
            bean.setGesture(cursor.getString(4));
            bean.setPhone(cursor.getString(5));
            bean.setNickName(cursor.getString(6));

        }
        cursor.close();
        db.close();
        return bean;
    }

    public UserBean getUserByUserName(String userName) {
        if (userDbHelper == null) {
            return null;
        }
        db = userDbHelper.getReadableDatabase();
        UserBean bean = new UserBean();
        Cursor cursor = null;
        String sql = "select * from users where username=?";
        cursor = db.rawQuery(sql, new String[] { userName });
        while (cursor.moveToNext()) {
            bean.setUserName(cursor.getString(0));
            bean.setPassword(cursor.getString(1));
            bean.setSessionId(cursor.getString(2));
            bean.setGesture(cursor.getString(3));
            bean.setPhone(cursor.getString(4));
            bean.setNickName(cursor.getString(5));
        }
        cursor.close();
        db.close();
        return bean;
    }

    public void updateSessionId(String sessionId,String phone){
        if(userDbHelper==null){
            return;
        }
        db=userDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sessionId",sessionId);
        db.update("users",contentValues,"phone=?",new String[]{phone});
        db.close();
    }
   /* public Flowable<UserBean> insertUser(){
        return Flowable.create(new FlowableOnSubscribe<UserBean>() {
            @Override
            public void subscribe(FlowableEmitter<UserBean> emitter) throws Exception {
                if(userDbHelper==null){
                    return;
                }
                db = userDbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("username",userBean.getUserName());
                contentValues.put("password",userBean.getPassword());
                contentValues.put("sessionid",userBean.getSessionId());
                contentValues.put("phone",userBean.getPhone());
                contentValues.put("nickname",userBean.getNickName());
                db.insert("users", null, contentValues);
                db.close();
                        emitter.onNext(bean);
                        emitter.onComplete();
                    }finally {
                        if(cursor!=null){
                            cursor.close();
                        }
                        db.close();
                    }

                }
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }*/

}

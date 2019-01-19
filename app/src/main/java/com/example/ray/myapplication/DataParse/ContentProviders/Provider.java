package com.example.ray.myapplication.DataParse.ContentProviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Provider extends ContentProvider{
    private Context mContext;
    private ProviderDBHelper mDBhelper = null;
    private SQLiteDatabase db = null;

    //contentprovider唯一标识
    public static final String AUTOHORITY = "cn.scu.provider";
    public static final int USER_CODE = 1;
    public static final int JOB_CODE = 2;

    //在ContentProcider中注册uri
    private static final UriMatcher mMatcher;
    static {
        //初始化
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // 若URI资源路径 = content://cn.scu.myprovider/user ，则返回注册码USER_CODE
        // 若URI资源路径 = content://cn.scu.myprovider/job ，则返回注册码JOB_CODE
        mMatcher.addURI(AUTOHORITY,"user",USER_CODE);
        mMatcher.addURI(AUTOHORITY,"JOB",JOB_CODE);
    }

    /*
    * 初始化ContentProvider
    * */
    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDBhelper = new ProviderDBHelper(mContext);
        db = mDBhelper.getWritableDatabase();
        db.execSQL("delete from user");
        db.execSQL("insert into user values(1,'hehe')");
        db.execSQL("insert into user values(2,'haha')");

        db.execSQL("delete from job");
        db.execSQL("insert into job values(1,'doing')");
        db.execSQL("insert into job values(2,'done')");

        return true;
    }

    /*
    * 添加数据
    * */

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        String table = getTableName(uri);
        db.insert(table,null,contentValues);
        // 当该URI的ContentProvider数据发生变化时，通知外界（即访问该ContentProvider数据的访问者）
        mContext.getContentResolver().notifyChange(uri,null);
        return uri;
    }
    /*
    * 查询数据
    * */

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        String table = getTableName(uri);

        return db.query(table, strings, s, strings1, null,null, s1);

    }
    /*
    * 更新数据
    * */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
    /*
    * 删除数据
    * */

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        String table = getTableName(uri);
        db.delete(table,s,strings);
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
     */
    private String getTableName(Uri uri){
        String tableName = null;
        switch (mMatcher.match(uri)) {
            case USER_CODE:
                tableName = ProviderDBHelper.USER_TABLE_NAME;
                break;
            case JOB_CODE:
                tableName = ProviderDBHelper.JOB_TABLE_NAME;
                break;
        }
        return tableName;
    }


}




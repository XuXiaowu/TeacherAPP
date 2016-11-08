package com.lichen.teacher.application;

import android.app.Application;

import com.lichen.teacher.global.Constant;
import com.lichen.teacher.util.PreferenceUtils;

import org.xutils.BuildConfig;
import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by xiaowu on 2016/9/20.
 */
public class MyApplication extends Application {

    public static DbManager.DaoConfig DAO_CONFIG;
    private static MyApplication application;
    public static String USER_ID;

    @Override
    public void onCreate() {
        super.onCreate();

        initXutils();
        initDbManager();
        PreferenceUtils.setApplication(this);
    }

    public static MyApplication getInstance() {
        if (null == application) {
            application = new MyApplication();
        }
        return application;
    }

    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); //开启debug会影响性能
    }

    private void initDbManager() {
        DAO_CONFIG = new DbManager.DaoConfig()
                .setDbName(Constant.DATABASE_NAME)
//                .setDbDir(new File("/sdcard")) //不设置dbDir时, 默认存储在app的私有目录.
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                });
        DbManager dbManager = x.getDb(DAO_CONFIG);
        dbManager.getDatabase().enableWriteAheadLogging(); // 开启WAL, 对写入加速提升巨大
    }
}

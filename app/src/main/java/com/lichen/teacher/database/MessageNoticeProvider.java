package com.lichen.teacher.database;

import android.util.Log;

import com.lichen.teacher.application.MyApplication;
import com.lichen.teacher.models.MessageNoticeInfo;
import com.umeng.analytics.MobclickAgent;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016/10/1.
 */
public class MessageNoticeProvider {

    private static final String TAG = "MessageNoticeProvider";

    public static void saveOrUpdateMessage(List<MessageNoticeInfo> list) {
        DbManager dbManager = x.getDb(MyApplication.DAO_CONFIG);
        try {
            dbManager.save(list);
            List ls = dbManager.findAll(MessageNoticeInfo.class);
            Log.e(TAG, ls.toString());
        } catch (DbException e) {
            e.printStackTrace();
            MobclickAgent.reportError(MyApplication.getInstance(), e.getCause());
        }
    }

    public static boolean checkHasNewMessage() {
        DbManager dbManager = x.getDb(MyApplication.DAO_CONFIG);
        List<MessageNoticeInfo> list = null;
        try {
            list = dbManager.findAll(MessageNoticeInfo.class);
        } catch (DbException e) {
            e.printStackTrace();
            MobclickAgent.reportError(MyApplication.getInstance(), e.getCause());
        }

        if (list == null) return false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isNew()) return true;
        }
        return false;
    }

    public static List<MessageNoticeInfo> findAllMessage() {
        DbManager dbManager = x.getDb(MyApplication.DAO_CONFIG);
        List<MessageNoticeInfo> list = null;
        try {
            list = dbManager.findAll(MessageNoticeInfo.class);
        } catch (DbException e) {
            e.printStackTrace();
            MobclickAgent.reportError(MyApplication.getInstance(), e.getCause());
        }
        return list;
    }

    public static void setMessageAlreadyRead() {
        DbManager dbManager = x.getDb(MyApplication.DAO_CONFIG);
        List<MessageNoticeInfo> list = findAllMessage();
        List<MessageNoticeInfo> updateList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MessageNoticeInfo mni = list.get(i);
            if (mni.isNew()) {
                mni.setNew(false);
                updateList.add(mni);
            }
        }
        try {
            dbManager.update(updateList, "isNew");
            List ls = dbManager.findAll(MessageNoticeInfo.class);
            Log.e(TAG, ls.toString());
        } catch (DbException e) {
            e.printStackTrace();
            MobclickAgent.reportError(MyApplication.getInstance(), e.getCause());
        }
    }
}

package com.lichen.teacher.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.lichen.teacher.R;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.util.FileUtils;
import com.lichen.teacher.util.Utils;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.io.File;


/**
 * Created by xiaowu on 2016/10/12.
 */
public class DownloadService extends Service {

    private static final String TAG = "DownloadService";

    private static final String APK_NAME = "LCWX_TEACHER.apk";
    private static final int NOTIFICATION_FLAG = 1;
    private String mDownloadUrl;

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private String mDownloadPath;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        mDownloadUrl = intent.getStringExtra(Constant.EXTRA_DOWNLOAD_URL);
        mDownloadPath = Utils.getDirPath("download", getApplication())+File.separator + APK_NAME;
        File apkFile = new File(mDownloadPath);
        if (apkFile.exists()) apkFile.delete();

        RequestParams requestParams = new RequestParams(mDownloadUrl);
        requestParams.setSaveFilePath(mDownloadPath);
        requestParams.setAutoResume(true);
        x.http().get(requestParams, mProgressCallback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    private Callback.ProgressCallback<File> mProgressCallback = new Callback.ProgressCallback<File>() {
        @Override
        public void onWaiting() {
//            Log.e(TAG, "onWaiting");
        }

        @Override
        public void onStarted() {
            createNotification();
        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {
            float fTotal = total;
            float fCurrent = current;
            float progress = fCurrent / fTotal * 100;
            RemoteViews contentView = mNotification.contentView;
            contentView.setTextViewText(R.id.progress_view, (int) progress + "%");
            contentView.setProgressBar(R.id.progress_bar_view, 100, (int) progress, false);
            mNotificationManager.notify(NOTIFICATION_FLAG, mNotification);
        }

        @Override
        public void onSuccess(File result) {
            RemoteViews contentView = mNotification.contentView;
            contentView.setTextViewText(R.id.title_view, getString(R.string.downloading_new_version_finish));
            contentView.setTextViewText(R.id.progress_view, "100%");
            contentView.setProgressBar(R.id.progress_bar_view, 100, 100, false);
            mNotificationManager.notify(NOTIFICATION_FLAG, mNotification);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            Toast.makeText(getApplicationContext(), R.string.downloading_new_version_error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(CancelledException cex) {
//            Log.e(TAG, "onCancelled");
        }

        @Override
        public void onFinished() {
            File file = new File(mDownloadPath);
            openFile(file);
        }
    };

    private void createNotification() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        File file = new File(mDownloadPath);
        String type = FileUtils.getMIMEType(file);
        intent.setDataAndType(Uri.fromFile(file), type);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.download_notification_view);
        mNotificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        long when = System.currentTimeMillis();
        mNotification = new  Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("正在下载新版本")
                .setContentTitle(getString(R.string.app_name))
                .setContentText("正在下载新版本")
                .setWhen(when)
                .setContentIntent(pendingIntent)
                .setContent(contentView)
                .getNotification();
        mNotificationManager.notify(NOTIFICATION_FLAG, mNotification);
    }

    private void openFile(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = FileUtils.getMIMEType(file);
        intent.setDataAndType(Uri.fromFile(file), type);
        startActivity(intent);
    }

}

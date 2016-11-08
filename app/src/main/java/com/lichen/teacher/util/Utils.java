package com.lichen.teacher.util;

import android.content.Context;
import android.os.Environment;

import com.lichen.teacher.R;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.http.Address;
import com.lichen.teacher.sharesdk.onekeyshare.OnekeyShare;
import com.lichen.teacher.sharesdk.onekeyshare.OnekeyShareTheme;

import java.io.File;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by xiaowu on 2016/10/6.
 */
public class Utils {

    public static boolean isLogin(){
        String userId = PreferenceUtils.getStringPref(Constant.USER_ID, "");
        if (userId.equals("")) return false;
        else return true;
    }

    /**
     * 调用ShareSDK执行分享
     *
     * @param context
     * @param platformToShare  指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
     * @param showContentEdit  是否显示编辑页
     */
    public static void showShare(Context context, String platformToShare, boolean showContentEdit, String url, String title, String text) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        //oks.setAddress("12345678901"); //分享短信的号码和邮件的地址
        oks.setTitle(title);
        if (url == null) oks.setTitleUrl(Address.HOST);
        else oks.setTitleUrl(url);
        oks.setText(text);
        //oks.setImagePath("/sdcard/test-pic.jpg");  //分享sdcard目录下的图片
//        oks.setImageUrl(randomPic()[0]);
        if (url == null)oks.setUrl(Address.HOST); //微信不绕过审核分享链接
        else oks.setUrl(url);
        //oks.setFilePath("/sdcard/test-pic.jpg");  //filePath是待分享应用程序的本地路劲，仅在微信（易信）好友和Dropbox中使用，否则可以不提供
        oks.setComment("分享"); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
        oks.setSite(context.getResources().getString(R.string.app_name));  //QZone分享完之后返回应用时提示框上显示的名称
        if (url == null) oks.setSiteUrl(Address.HOST);//QZone分享参数
        else oks.setSiteUrl(url);
//        oks.setVenueName("ShareSDK");
//        oks.setVenueDescription("This is a beautiful place!");
        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        oks.setCallback(platformActionListener);
        // 去自定义不同平台的字段内容
        //oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        // 在九宫格设置自定义的图标
//        Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
//        String label = "ShareSDK";
//        View.OnClickListener listener = new View.OnClickListener() {
//            public void onClick(View v) {
//
//            }
//        };
//        oks.setCustomerLogo(logo, label, listener);

        // 为EditPage设置一个背景的View
        //oks.setEditPageBackground(getPage());
        // 隐藏九宫格中的新浪微博
        // oks.addHiddenPlatform(SinaWeibo.NAME);

        // String[] AVATARS = {
        // 		"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
        // 		"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
        // 		"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
        // 		"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };
        // oks.setImageArray(AVATARS);              //腾讯微博和twitter用此方法分享多张图片，其他平台不可以

        // 启动分享
        oks.show(context);
    }

    private static PlatformActionListener platformActionListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//            Log.e(TAG, "onComplete");
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
//            Log.e(TAG, "onError");
        }

        @Override
        public void onCancel(Platform platform, int i) {
//            Log.e(TAG, "onCancel");
        }
    };

    public static String getDirPath(String name, Context context) {
        String path = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment
                    .getExternalStorageDirectory().getPath()
                    + "/LichenTeacher/");
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return path;
                }
            }
            file = new File(Environment.getExternalStorageDirectory().getPath()
                    + "/LichenTeacher/" + name);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    path = file.getAbsolutePath();
                }
            } else {
                path = file.getAbsolutePath();
            }
        } else {
            File dir = context.getDir(name, Context.MODE_PRIVATE
                    | Context.MODE_WORLD_READABLE
                    | Context.MODE_WORLD_WRITEABLE);
            path = dir.getAbsolutePath();
        }
        return path;
    }
}

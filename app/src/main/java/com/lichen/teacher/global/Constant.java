package com.lichen.teacher.global;

/**
 * Created by xiaowu on 2016/7/20.
 */
public class Constant {

    /****************************** SharedPreferences **************************/
    public static final String USER_ID = "user_id";
    public static final String FIRST_LAUNCH_TAG = "first_launch_tag";


    /****************************** http **************************/
    public static final String HTTP_PARAM_TYPE = "type";
    public static final String HTTP_PARAM_CURRENT_PAGE = "currentPage";
    public static final String STATUS_SUCCESS = "success";

    /****************************** request code **************************/
    public static final int  REQUEST_CODE_FRAGMENT_VIDEO = 10001;
    public static final int  REQUEST_CODE_FRAGMENT_CLASS = 10002;

    /****************************** extra **************************/
    public static final String EXTRA_LIVE_INFO = "live_info";
//    public static final String EXTRA_FRAGMENT_LIVE_COUNT_DOWN_TAG = "fragment_live_action";
    public static final String EXTRA_DOWNLOAD_URL = "download_url";
    public static final String EXTRA_PASSWORD_EDIT_TYPE = "password_edit_type";


    /****************************** database **************************/
    public static final String DATABASE_NAME = "LichenApp_DB";

    /****************************** broadcast action**************************/
    public static final String UPDATE_MESSAGE_NOTICE_ACTION = "com.lichen.teacher.update_message_notice_action";
    public static final String NOTIFY_LIST_ACTION = "com.lichen.teacher.notify_list_action";
    public static final String SELECT_CHAT_PAGE_ACTION = "com.lichen.teacher.select_chat_page_action";
    public static final String SELECT_HOME_PAGE_ACTION = "com.lichen.teacher.select_home_page_action";


}

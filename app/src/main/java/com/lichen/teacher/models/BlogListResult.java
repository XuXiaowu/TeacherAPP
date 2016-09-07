package com.lichen.teacher.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import java.util.List;

import truecolor.webdataloader.annotations.MemoryCache;

/**
 * Created by xiaowu on 2016/7/20.
 */
@JSONType
@MemoryCache
public class BlogListResult {

    @JSONField(name = "queryBlogAndReplyList")
    public List<Blog> queryBlogAndReplyList;
    @JSONField(name = "page")
    public Page page;


    @JSONType
    public static class Blog {
        @JSONField(name = "blogId")
        public long blogId;
        @JSONField(name = "cusId")
        public long cusId;
        @JSONField(name = "showName")
        public String showName;
        @JSONField(name = "title")
        public String title;
        @JSONField(name = "viewCount")
        public int viewCount;
        @JSONField(name = "replyCount")
        public int replyCount;
        @JSONField(name = "addTime")
        public String addTime;
        @JSONField(name = "updateTime")
        public String updateTime;
        @JSONField(name = "blognum")
        public int blognum;
        @JSONField(name = "id")
        public long id;
        @JSONField(name = "type")
        public int type;
        @JSONField(name = "status")
        public int status;
        @JSONField(name = "isBest")
        public int isBest;
        @JSONField(name = "activity")
        public int activity;
        @JSONField(name = "top")
        public int top;
        @JSONField(name = "lineNum")
        public int lineNum;
        @JSONField(name = "maxId")
        public int maxId;
        @JSONField(name = "flag")
        public int flag;
        @JSONField(name = "summary")
        public String summary;
        @JSONField(name = "modelStr")
        public String modelStr;
        @JSONField(name = "upmodelStr")
        public String upmodelStr;
        @JSONField(name = "avatar")
        public String avatar;



    }
}

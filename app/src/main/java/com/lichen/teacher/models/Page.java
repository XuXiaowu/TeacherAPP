package com.lichen.teacher.models;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

/**
 * Created by xiaowu on 2016/7/21.
 */

@JSONType
public class Page {

    @JSONField(name = "totalResultSize")
    public int totalResultSize;
    @JSONField(name = "totalPageSize")
    public int totalPageSize;
    @JSONField(name = "pageSize")
    public int pageSize;
    @JSONField(name = "currentPage")
    public int currentPage;

}

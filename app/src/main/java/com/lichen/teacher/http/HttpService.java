package com.lichen.teacher.http;

import com.lichen.teacher.global.Constant;
import com.lichen.teacher.global.WebServiceConfigure;
import com.lichen.teacher.models.BlogListResult;
import truecolor.webdataloader.HttpRequest;
import truecolor.webdataloader.WebDataLoader;
import truecolor.webdataloader.WebListener;

/**
 * Created by xiaowu on 16-7-13
 */
public class HttpService {

	/**
	 * 获取社区文章
	 * currentPae 页数
	 */
	public static void getBlogList(int type, int currentPage, WebListener webListener) {
		HttpRequest httpRequest = HttpRequest.createDefaultRequest(Address.BLOG_LIST_URL)
				.addQuery(Constant.HTTP_PARAM_TYPE, type)
				.addQuery(Constant.HTTP_PARAM_CURRENT_PAGE, currentPage);
		WebDataLoader.loadWebData(httpRequest, BlogListResult.class, webListener, WebServiceConfigure.GET_BLOG_LIST, null);
	}

}

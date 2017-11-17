package com.ly.supermvp.server;

import com.ly.supermvp.common.BizInterface;
import com.ly.supermvp.model.entity.ShowApiResponse;
import com.ly.supermvp.model.news.ShowApiNews;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * <Pre>
 * 易源api
 * </Pre>
 *
 * @author 刘阳
 * @version 1.0
 *          <p>
 *          Create by 2017/1/18 15:41
 */

public interface ShowAPI {
    /**
     * 新闻列表
     * @param cacheControl 缓存控制
     * @param appId 易源appid
     * @param key 易源密钥
     * @param page 页数
     * @param channelId 频道id
     * @param channelName 名称
     * @return
     */
    @GET(BizInterface.NEWS_URL)
    @Headers("apikey: " + BizInterface.API_KEY)
    Call<ShowApiResponse<ShowApiNews>> getNewsList(@Header("Cache-Control") String cacheControl,
                                                   @Query("showapi_appid") String appId,
                                                   @Query("showapi_sign") String key,
                                                   @Query("page") int page,
                                                   @Query("channelId") String channelId,//新闻频道id，必须精确匹配
                                                   @Query("channelName") String channelName);//新闻频道名称，可模糊匹配
}

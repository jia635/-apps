package com.ly.supermvp.server;

import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * <Pre>
 * 打印网络请求与响应
 * </Pre>
 *
 * @author 刘阳
 * @version 1.0
 *          <p>
 *          Create by 2017/1/18 16:09
 */
@Deprecated
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Logger.d(String.format("网络%s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        return chain.proceed(request);
    }
}

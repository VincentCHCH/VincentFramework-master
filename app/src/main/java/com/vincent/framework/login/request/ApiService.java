package com.vincent.framework.login.request;


import com.vincent.framework.login.domain.UserInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Vincent on 25/1/2018.
 */
public interface ApiService {
    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("UserInfo/loginin")
    Observable<ResponseResult<UserInfo>> login(@Field("username") String username, @Field("password") String password);
}

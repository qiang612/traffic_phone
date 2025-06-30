package com.rainkaze.traffic.api;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rainkaze.traffic.api.AuthInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://10.151.180.244:8080";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 1. 创建一个 GsonBuilder
            GsonBuilder gsonBuilder = new GsonBuilder();

            // 2. 设置日期时间解析格式，以匹配服务器返回的 "yyyy-MM-dd'T'HH:mm:ss"
            gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            // 3. 创建带有自定义日期格式的 Gson 实例
            Gson gson = gsonBuilder.create();

            // Add the AuthInterceptor
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(context))
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    // 4. 使用我们自定义的 Gson 实例来创建转换器工厂
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
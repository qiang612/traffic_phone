package com.rainkaze.traffic.api;

import com.rainkaze.traffic.model.DashboardDataDto;
import com.rainkaze.traffic.model.Device;
import com.rainkaze.traffic.model.JwtAuthResponseDto;
import com.rainkaze.traffic.model.LoginDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.rainkaze.traffic.model.User;
import com.rainkaze.traffic.model.PageResult; // 假设您有分页结果的通用DTO
import com.rainkaze.traffic.model.Violation;

import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;
public interface ApiService {
    @POST("/api/auth/login")
    Call<JwtAuthResponseDto> login(@Body LoginDto loginDto);

    @GET("/api/dashboard/data")
    Call<DashboardDataDto> getDashboardData(@Query("timeRange") String timeRange);
    @GET("/api/users")
    Call<PageResult<User>> getUsers(@Query("page") int page, @Query("size") int size);

    @GET("/api/violations")
    Call<PageResult<Violation>> getViolations(@Query("page") int page, @Query("size") int size);
    // ... 在这里根据您的后端Controller添加所有其他接口 ...
    // 例如:
    // @GET("/api/violations")
    // Call<PageResultDto<ViolationDetailDto>> getViolations(@QueryMap Map<String, String> options);
    @GET("/api/devices")
    Call<List<Device>> getDevices(@Query("page") int page, @Query("size") int size);

}
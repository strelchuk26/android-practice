package com.example.shop.services;

import com.example.shop.auth.LoginRequest;
import com.example.shop.auth.LoginResponse;
import com.example.shop.auth.RegisterRequest;
import com.example.shop.auth.RegisterResponse;
import com.example.shop.auth.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    // Отримання профілю користувача
    @GET("user/profile")
    Call<User> getUserProfile();
}


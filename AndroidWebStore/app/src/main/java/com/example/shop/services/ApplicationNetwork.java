package com.example.shop.services;

import com.example.shop.auth.User;
import com.example.shop.constants.Urls;
import com.example.shop.network.CategoriesApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationNetwork {
    private static ApplicationNetwork instance;
    private Retrofit retrofit;

    private ApplicationNetwork() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(Urls.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static ApplicationNetwork getInstance() {
        if (instance == null)
            instance = new ApplicationNetwork();
        return instance;
    }

    public CategoriesApi getCategoriesApi() {
        return retrofit.create(CategoriesApi.class);
    }

    // Доступ до AuthService
    public AuthService getAuthService() {
        return retrofit.create(AuthService.class);
    }

    // Метод на створення
    public Call<User> getUserProfile() {
        return getAuthService().getUserProfile();
    }
}


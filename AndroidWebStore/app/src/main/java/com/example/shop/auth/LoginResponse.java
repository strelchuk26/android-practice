package com.example.shop.auth;

import com.google.gson.annotations.SerializedName;

// Відповідь авторизації
public class LoginResponse {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


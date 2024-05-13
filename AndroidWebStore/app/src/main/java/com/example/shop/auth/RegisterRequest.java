package com.example.shop.auth;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class RegisterRequest {
    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("image")
    private Uri imageUri;

    public RegisterRequest(String username, String email, String password, Uri imageUri) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.imageUri = imageUri;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}


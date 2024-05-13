package com.example.shop.profile;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shop.R;
import com.example.shop.auth.User;
import com.example.shop.services.ApplicationNetwork;

import java.io.File;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

// Активність профілю
public class ProfileActivity extends AppCompatActivity {

    private TextView tvUsername, tvEmail, tvFirstName, tvLastName;
    private ImageView ivProfileImage;

    // Створення
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        ivProfileImage= findViewById(R.id.ivProfileImage);

        loadUserProfile();
    }

    // Завантаження профілю
    private void loadUserProfile() {
        ApplicationNetwork
                .getInstance()
                .getUserProfile()
                .enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    updateUI(user);
                } else {
                    Log.e("ProfileActivity", "Failed to fetch user profile");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ProfileActivity", "Failed to fetch user profile: " + t.getMessage());
            }
        });
    }

    // Оновлення UI
    private void updateUI(User user) {
        tvUsername.setText(user.getUsername());
        tvEmail.setText(user.getEmail());
        tvFirstName.setText(user.getFirstName());
        tvLastName.setText(user.getLastName());

        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.error_image);

        // Завантаження локального файлу зображення
        Glide.with(this)
                .load(new File(getFilesDir(), "gingercat.jpg"))
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivProfileImage);
    }
}

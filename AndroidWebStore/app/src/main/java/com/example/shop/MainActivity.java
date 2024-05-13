package com.example.shop;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.shop.auth.LoginActivity;
import com.example.shop.category.CategoriesAdapter;
import com.example.shop.category.CategoryCreateActivity;
import com.example.shop.dto.category.CategoryItemDTO;
import com.example.shop.services.ApplicationNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    RecyclerView rcCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcCategories = findViewById(R.id.rcCategories);
        rcCategories.setHasFixedSize(true);
        rcCategories.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));

        // Перевірити чи користувач увійшов
        if (!isLoggedIn()) {
            // Повертаємо в LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            ApplicationNetwork
                    .getInstance()
                    .getCategoriesApi()
                    .list()
                    .enqueue(new Callback<List<CategoryItemDTO>>() {
                        @Override
                        public void onResponse(Call<List<CategoryItemDTO>> call, Response<List<CategoryItemDTO>> response) {
                            if(response.isSuccessful()) {
                                List<CategoryItemDTO> items = response.body();
                                CategoriesAdapter ca = new CategoriesAdapter(items);
                                rcCategories.setAdapter(ca);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<CategoryItemDTO>> call, Throwable throwable) {
                            Log.e("--problem--", "error server");
                        }
                    });

        }
    }
    private boolean isLoggedIn() {
        // Перевірити чи токен зберігається
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        return preferences.contains("token");
    }

    // Створення категорії
    public void onClickCreateCategory(View view) {
        Intent intent = new Intent(MainActivity.this, CategoryCreateActivity.class);
        startActivity(intent);
    }
}

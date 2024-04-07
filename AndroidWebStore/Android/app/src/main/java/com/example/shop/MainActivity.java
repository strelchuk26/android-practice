package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shop.application.HomeApplication;
import com.example.shop.category.CategoriesAdapter;
import com.example.shop.dto.category.CategoryItemDTO;
import com.example.shop.services.ApplicationNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcCategories = findViewById(R.id.rcCategories);
        rcCategories.setHasFixedSize(true);
        rcCategories.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        //знаходимо елемент на сторінці
        //ImageView ivAvatar = findViewById(R.id.ivAvatar);
        //Server ip
        //http://10.0.2.2:5297/images/1.jpg
//        String url = "https://content1.rozetka.com.ua/goods/images/big/415679366.jpg";
//        String url = "http://10.0.2.2:5297/images/1.jpg";
//        String url = "https://spu111.itstep.click/images/1.jpg";
//        Glide.with(HomeApplication.getAppContext())
//                .load(url)
//                .apply(new RequestOptions().override(400))
//                .into(ivAvatar);

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
                            //int count = items.size();
                            //Log.d("---count---", String.valueOf(count));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable throwable) {
                        Log.e("--problem--", "error server");
                    }
                });
    }
}
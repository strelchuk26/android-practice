package com.example.shop.network;

import com.example.shop.auth.RegisterResponse;
import com.example.shop.dto.category.CategoryCreateDTO;
import com.example.shop.dto.category.CategoryItemDTO;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface CategoriesApi {
    @GET("/api/categories")
    public Call<List<CategoryItemDTO>> list();

    @Multipart
    @POST("/api/categories")
    public Call<CategoryItemDTO> create(@PartMap Map<String, RequestBody> params,
                                        @Part MultipartBody.Part image);

    @Multipart
    @PUT("/api/categories")
    public Call<CategoryItemDTO> update(@Path("id") int categoryId,
                                        @PartMap Map<String, RequestBody> params,
                                        @Part MultipartBody.Part image);

}
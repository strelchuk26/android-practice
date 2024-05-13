package com.example.shop.category;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shop.BaseActivity;
import com.example.shop.MainActivity;
import com.example.shop.R;
import com.example.shop.constants.Urls;
import com.example.shop.dto.category.CategoryCreateDTO;
import com.example.shop.dto.category.CategoryItemDTO;
import com.example.shop.services.ApplicationNetwork;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCreateActivity extends BaseActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    TextInputLayout tlCategoryName;
    TextInputLayout tlCategoryDescription;
    private final String TAG="CategoryCreateActivity";

    private ImageView ivSelectImage;

    private String filePath;

    // Надання дозволу
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    // Відкриття галереї для вибору зображення
    public void openGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);

        isStoragePermissionGranted();

        tlCategoryName = findViewById(R.id.tlCategoryName);
        tlCategoryDescription = findViewById(R.id.tlCategoryDescription);

        // Перевірка чи активність в режимі редагування
        CategoryItemDTO category = getIntent().getParcelableExtra("category");
        if (category != null) {
            populateForm(category);
            Button btnEdit = findViewById(R.id.btnEditCategory);
            // Відобразити кнопку редагування
            btnEdit.setVisibility(View.VISIBLE);
        }

        ivSelectImage = findViewById(R.id.ivSelectImage);

        String url = "https://cdn.pixabay.com/photo/2016/01/03/00/43/upload-1118929_1280.png";
        Glide
                .with(this)
                .load(url)
                .apply(new RequestOptions().override(300))
                .into(ivSelectImage);

    }

    public void onClickCreateCategory(View view) {
        try {
            String name = tlCategoryName.getEditText().getText().toString().trim();
            String description = tlCategoryDescription.getEditText().getText().toString().trim();
            Map<String, RequestBody> params = new HashMap<>();
            params.put("name", RequestBody.create(MediaType.parse("text/plain"), name));
            params.put("description", RequestBody.create(MediaType.parse("text/plain"), description));

            MultipartBody.Part imagePart=null;
            if (filePath != null) {
                File imageFile = new File(filePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
                imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
            }

            // Перевірити на редагування чи створення
            CategoryItemDTO category = getIntent().getParcelableExtra("category");
            if (category != null) {
                // Редагування
                ApplicationNetwork.getInstance()
                        .getCategoriesApi()
                        .update(category.getId(), params, imagePart)
                        .enqueue(new Callback<CategoryItemDTO>() {
                            @Override
                            public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                                if (response.isSuccessful()) {
                                    Intent intent = new Intent(CategoryCreateActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            @Override
                            public void onFailure(Call<CategoryItemDTO> call, Throwable throwable) {

                                Log.e("--CategoryCreateActivity--", "Failed to update category: " + throwable.getMessage());
                            }
                        });
            }
            else {
                // Створення
                ApplicationNetwork.getInstance()
                        .getCategoriesApi()
                        .create(params, imagePart)
                        .enqueue(new Callback<CategoryItemDTO>() {
                            @Override
                            public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                                if(response.isSuccessful())
                                {
                                    Intent intent = new Intent(CategoryCreateActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            @Override
                            public void onFailure(Call<CategoryItemDTO> call, Throwable throwable) {

                                Log.e("--CategoryCreateActivity--", "Failed to create category: " + throwable.getMessage());
                            }
                        });
            }
        }
        catch(Exception ex) {
            Log.e("--CategoryCreateActivity--", "Problem create "+ ex.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Отримати URI з обраного зображення
            Uri uri = data.getData();

            Glide
                    .with(this)
                    .load(uri)
                    .apply(new RequestOptions().override(300))
                    .into(ivSelectImage);

            filePath = getPathFromURI(uri);
        }
    }

    // Конвертація URI
    private String getPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }

    // TODO
    private void populateForm(CategoryItemDTO category) {
        tlCategoryName.getEditText().setText(category.getName());
        tlCategoryDescription.getEditText().setText(category.getDescription());

        // Завантаження існуючого зображення за допомогою Glide
        String url = Urls.BASE + "/images/" + category.getImage();
        Glide
                .with(this)
                .load(url)
                .apply(new RequestOptions().override(300))
                .into(ivSelectImage);

        filePath = category.getImage();
    }


}

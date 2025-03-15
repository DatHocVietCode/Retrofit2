package com.example.ritrofit2;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ritrofit2.client.RetrofitClient;
import com.example.ritrofit2.model.Category;
import com.example.ritrofit2.model.Respone;
import com.example.ritrofit2.modelAdapter.CategoryAdapter;
import com.example.ritrofit2.service.APIService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {
    RecyclerView rcCate;
    CategoryAdapter categoryAdapter;
    APIService apiService;
    List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Anhxa();
        GetCategory();
    }

    private void Anhxa() {
        rcCate = (RecyclerView) findViewById(R.id.rc_category);
    }

    private void GetCategory() {
        Log.d("DEBUG", "Hello");
        //Goi Interface trong APIService
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCategory().enqueue(new Callback<Respone<List<Category>>>() {
            @Override
            public void onResponse(Call<Respone<List<Category>>> call, Response<Respone<List<Category>>> response) {
                Log.d("DEBUG", "Hello, im here");
                if (response.isSuccessful()) {
                    Log.d("DEBUG", "onResponse: Success");
                    categoryList = (List<Category>) response.body().body; //nhân mảng
                    if (categoryList != null)
                    {
                        Log.d("DEBUG", "onResponse: " + categoryList.size());
                    }
                    else {
                        Log.d("DEBUG", "onResponse: categoryList is null!");

                    }
                    Log.d("DEBUG", "Raw Response: " + new Gson().toJson(response.body().body));
                    //khởi tạo Adapter
                    categoryAdapter = new CategoryAdapter(RetrofitActivity.this, categoryList);
                    rcCate.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcCate.setLayoutManager(layoutManager);
                    rcCate.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                    Log.d("DEBUG", "onResponse: Error" + statusCode);
                }
            }

            @Override
            public void onFailure(Call<Respone<List<Category>>> call, Throwable t) {
                Log.d("DEBUG", t.getMessage());
            }
        });
    }
}
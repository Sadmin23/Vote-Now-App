package com.example.votenow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridLayout;

import com.example.votenow.R;
import com.example.votenow.adapters.CategoryAdapter;
import com.example.votenow.adapters.ProductAdapter;
import com.example.votenow.databinding.ActivityHomeBinding;
import com.example.votenow.model.Category;
import com.example.votenow.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Objects.requireNonNull(getSupportActionBar()).hide();


        initCategorise();

        initProducts();

        initSlider();











































//////////////// ..................Bottom Navigation.............../////////////////////////////////////


        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });





    }

    private void initSlider() {
        binding.carousel.addData(new CarouselItem("data:image/jpeg;base64,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"));
        binding.carousel.addData(new CarouselItem("data:image/png;base64,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"));
    }

    void initCategorise(){
        categories = new ArrayList<>();
        categories.add(new Category("Sport","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","#C8290A","Some text",1));
        categories.add(new Category("Sport","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","#C8290A","Some text",1));
        categories.add(new Category("Sport","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","#C8290A","Some text",1));
        categories.add(new Category("Sport","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","#C8290A","Some text",1));
        categories.add(new Category("Sport","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","#C8290A","Some text",1));
        categories.add(new Category("Sport","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","#C8290A","Some text",1));
        categories.add(new Category("Sport","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","#C8290A","Some text",1));
        categories.add(new Category("Sport","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","#C8290A","Some text",1));
        categoryAdapter = new CategoryAdapter(this, categories);

        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager((layoutManager));
        binding.categoriesList.setAdapter(categoryAdapter);
    }

    void  initProducts(){
        products = new ArrayList<>();
        products.add(new Product("Iphone","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","Nothing",300,10,10,1));
        products.add(new Product("Iphone","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","Nothing",300,10,10,1));
        products.add(new Product("Iphone","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","Nothing",300,10,10,1));
        products.add(new Product("Iphone","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","Nothing",300,10,10,1));
        products.add(new Product("Iphone","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","Nothing",300,10,10,1));
        products.add(new Product("Iphone","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","Nothing",300,10,10,1));
        products.add(new Product("Iphone","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS13T9qaAEeZTq4xPmG4UR8FGMBhiYsYnThYA&usqp=CAU","Nothing",300,10,10,1));
        productAdapter = new ProductAdapter(this,products);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.productList.setLayoutManager((layoutManager));
        binding.productList.setAdapter(productAdapter);
    }
}
package com.example.foodfortheweek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private RequestQueue queue;
    private String foodName;
    private ArrayList<String> ingredients;
    private  String pictureUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        queue = Volley.newRequestQueue(this);

        if(savedInstanceState != null) {
            foodName = savedInstanceState.getString("FOOD_NAME", "");
            ingredients = savedInstanceState.getStringArrayList("INGREDIENTS");
            pictureUrl = savedInstanceState.getString("PICTURE_URL", "");
        } else {
            foodName = getIntent().getStringExtra("label");
            ingredients = getIntent().getStringArrayListExtra("Ingredients");
            pictureUrl = getIntent().getStringExtra("pictureUrl");
        }

        setThumbnail();
        setTexts();
    }

    private void setTexts() {
        TextView textViewLabel = findViewById(R.id.textViewLabel);
        textViewLabel.setText(foodName);

        String ingredientsStr = "";

        for(String item : ingredients) {
            ingredientsStr += item + "\n\n";
        }

        TextView textViewIngredients = findViewById(R.id.textViewIngredients);
        textViewIngredients.setText(ingredientsStr);
    }

    private void setThumbnail() {
        ImageRequest imageRequest = new ImageRequest(pictureUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                // callback
                ImageView imageView = findViewById(R.id.imageViewDetails);
                imageView.setImageBitmap(response);

            }
        }, 600, 600, null, null);
        queue.add(imageRequest);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("FOOD_NAME", foodName);
        outState.putStringArrayList("INGREDIENTS", ingredients);
        outState.putString("PICTURE_URL", pictureUrl);
    }
}
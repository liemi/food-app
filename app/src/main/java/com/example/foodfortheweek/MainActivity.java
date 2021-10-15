package com.example.foodfortheweek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity implements recyclerAdapter.OnItemListener {
    private ArrayList<OneDayFood> foodList;
    private ArrayList<String> keywordList;
    private RecyclerView recyclerView;
    private recyclerAdapter adapter;
    private RequestQueue queue;
    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        recyclerView = findViewById(R.id.recyclerView);

        if(savedInstanceState == null || !savedInstanceState.containsKey("FOOD_DATA")) {
            ArrayList<String> tempList = getIntent().getStringArrayListExtra("keywords");
            if (tempList == null) {
                keywordList = new ArrayList<>(Arrays.asList("Chicken", "Fish", "Meat", "Pork", "Bacon"));
            }
            else {
                keywordList = tempList;
            }
//            System.out.println(keywordListIntent);

            generateWeek();
        } else {
            foodList = savedInstanceState.getParcelableArrayList("FOOD_DATA");
            // Kuvan tallennus bundleen ei onnistunut, joten ladataan kuvat uudelleen
            for(OneDayFood item : foodList) {
                setThumbnail(item.getPictureUrl(), item.getId());
            }
        }
        setAdapter();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("FOOD_DATA", foodList);
        super.onSaveInstanceState(outState);
    }

    private void setAdapter() {
        adapter = new recyclerAdapter(foodList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    OneDayFood findOneDayFood(Integer id) {
        for(OneDayFood item : foodList) {
            if(item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    private ArrayList<String> jsonArrayToArrayList(JSONArray list) {
        ArrayList<String> ingredientsList = new ArrayList<>();
        for (int i = 0; i < list.length(); i++) {
            try {
                ingredientsList.add(list.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ingredientsList;
    }

    private void generateWeek() {
        foodList = new ArrayList<>();
        foodList.add(new OneDayFood("", getResources().getString(R.string.monday), 1, null, null, null));
        foodList.add(new OneDayFood("", getResources().getString(R.string.tuesday), 2, null, null, null));
        foodList.add(new OneDayFood("", getResources().getString(R.string.wensday), 3, null, null, null));
        foodList.add(new OneDayFood("", getResources().getString(R.string.thursday), 4, null, null, null));
        foodList.add(new OneDayFood("", getResources().getString(R.string.friday), 5, null, null, null));

        int bound = keywordList.size();
        for (int i = 0; i < bound; i++) {
            getFood(keywordList.get(i), i + 1);
        }

//        getFood("Chicken", 1);
//        getFood("Fish", 2);
//        getFood("Meat", 3);
//        getFood("Pork", 4);
//        getFood("Fish", 5);
    }


    private void getFood(String queryWord, Integer id) {
        String url ="https://api.edamam.com/api/recipes/v2?type=public&q=" + queryWord + "&app_id=81b53448&app_key=b9c799f1d3ff5ba54602fa9adf986eeb&mealType=Lunch&time=30-45&imageSize=THUMBNAIL";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    parseFood(response, id);
                }, error -> {
                    System.out.println("Error!");
                }
        );

        queue.add(stringRequest);
    }

    private void parseFood(String stringObj, Integer id) {
        // Random number to get random food
        Integer randomNum = rand.nextInt(19 - 0 + 1) + 0;
        JSONObject rootObj = null;
        try {
            rootObj = new JSONObject(stringObj);
            String label = rootObj.getJSONArray("hits").getJSONObject(randomNum).getJSONObject("recipe").getString("label");
            String pictureUrl = rootObj.getJSONArray("hits").getJSONObject(randomNum).getJSONObject("recipe").getString("image");
            JSONArray ingredients = rootObj.getJSONArray("hits").getJSONObject(randomNum).getJSONObject("recipe").getJSONArray("ingredientLines");
            ArrayList<String> ingredientsList = jsonArrayToArrayList(ingredients);

            OneDayFood item = findOneDayFood(id);

            item.setFood(label);
            item.setPictureUrl(pictureUrl);
            item.setIngredients(ingredientsList);

            setThumbnail(pictureUrl, id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setThumbnail(String url, Integer id) {
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                // callback
                findOneDayFood(id).setPictureBitmap(response);
                adapter.notifyDataSetChanged();
            }
        }, 400, 400, null, null);
        queue.add(imageRequest);
    }

    @Override
    public void onItemClick(int position) {
//        System.out.println(foodList.get(position).getId());
        Intent openDetails = new Intent(this,DetailsActivity.class);
        openDetails.putExtra("label", foodList.get(position).getFood());
        openDetails.putExtra("Ingredients", foodList.get(position).getIngredients());
        openDetails.putExtra("pictureUrl", foodList.get(position).getPictureUrl());
        startActivity(openDetails);
    }

    public void openEditActivity(View view) {
        Intent openEdit = new Intent(this,EditActivity.class);
        startActivity(openEdit);
    }
}
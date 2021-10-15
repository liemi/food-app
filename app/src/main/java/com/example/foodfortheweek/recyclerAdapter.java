package com.example.foodfortheweek;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<OneDayFood> foodList;
    private OnItemListener mOnItemListener;

    public recyclerAdapter(ArrayList<OneDayFood> foodList, OnItemListener onItemListener) {
        this.foodList = foodList;
        this.mOnItemListener = onItemListener;
    }
    private RequestQueue queue;

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView foodText;
        private TextView weekDayText;
        private ImageView imageView;
        private OnItemListener onItemListener;

        public MyViewHolder(final View view, OnItemListener onItemListener) {
            super(view);
            foodText = view.findViewById(R.id.textViewFood);
            weekDayText = view.findViewById(R.id.textViewWeekDay);
            imageView = view.findViewById(R.id.imageView);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String food = foodList.get(position).getFood();
        String weekDay = foodList.get(position).getWeekDay();
        Bitmap pictureBitmap = foodList.get(position).getPictureBitmap();

        holder.foodText.setText(food);
        holder.weekDayText.setText(weekDay);
        holder.imageView.setImageBitmap(pictureBitmap);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }
}

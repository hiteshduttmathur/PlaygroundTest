package com.test.playground.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.playground.models.Results;
import com.test.playground.view.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<Results> mFilteredList;

    private Context context;
    public DataAdapter(ArrayList<Results> arrayList, Context context) {
        mFilteredList = arrayList;
        this.context = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.textViewCollectionCensoredName.setText(mFilteredList.get(i).getCollectionCensoredName());
        viewHolder.textViewCollectionPrice.setText(mFilteredList.get(i).getCollectionPrice());

        // loading album cover using Glide library
        Glide.with(context).load(mFilteredList.get(i).getArtworkUrl100()).into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCollectionCensoredName, textViewCollectionPrice;
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            textViewCollectionCensoredName = (TextView) view.findViewById(R.id.textViewCollectionCensoredName);
            textViewCollectionPrice = (TextView) view.findViewById(R.id.textViewCollectionPrice);
            imageView = (ImageView) view.findViewById(R.id.imageView);

        }
    }

}
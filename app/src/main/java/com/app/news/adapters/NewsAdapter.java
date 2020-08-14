package com.app.news.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.news.R;
import com.app.news.utils.News;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    Context context;
    List<News> list;

    private OnItemClicked onClick;

    public interface OnItemClicked{
        void onItemClick(String webUrl);
    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }

    public NewsAdapter(Context context, List<News> list) {
        this.context = context;
        this.list = list;
    }

    public void addMoreData(List<News> list){
        if(this.list == null){
            this.list = list;
        }else{
            this.list.addAll(list);
        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        NewsViewHolder v = new NewsViewHolder(mView);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        final News news = list.get(position);
        holder.title.setText(news.getTitle());
        holder.section.setText(news.getSectionName());
        holder.desc.setText(news.getDescription());
        Glide.with(context).load(news.getImageUrl()).into(holder.thumbnail);
        holder.date.setText(news.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(news.getWebUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<News> list) {
        if(this.list != null) {
            this.list.addAll(list);
        }else{
            this.list = new ArrayList<>(list);
        }
        this.notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView thumbnail;
        TextView title;
        TextView desc;
        TextView date;
        TextView section;


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            thumbnail = itemView.findViewById(R.id.news_thumbnail);
            title = itemView.findViewById(R.id.news_title);
            desc = itemView.findViewById(R.id.news_description);
            date = itemView.findViewById(R.id.news_time_date);
            section = itemView.findViewById(R.id.news_section);
        }
    }
}

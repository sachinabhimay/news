package com.app.news.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.app.news.R;
import com.app.news.adapters.NewsAdapter;
import com.app.news.models.NewsViewModel;
import com.app.news.utils.FetchNews;
import com.app.news.utils.News;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class PageFragment extends Fragment implements NewsAdapter.OnItemClicked{

    private final String TAG = PageFragment.class.getSimpleName();

    private NewsViewModel newsViewModel;
    private Context context;
    private RecyclerView recyclerView;
    private int position;
    private NewsAdapter adapter;
    private static int page;
    private String section_tag;

    public PageFragment(int position) {
        this.position = position;

    }

    public PageFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        recyclerView = view.findViewById(R.id.news_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        page = 1;

        if(position == 0){
            section_tag = "";
        }else{
            section_tag = view.getResources().getStringArray(R.array.tabs)[position].toLowerCase();
            Log.d(TAG, section_tag);
        }
        context = getContext();



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        try {
            newsViewModel.getNews(section_tag,String.valueOf(page) ).observe(getViewLifecycleOwner(), new Observer<List<News>>() {
                @Override
                public void onChanged(List<News> news) {
                    adapter = new NewsAdapter(context, news);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnClick(PageFragment.this);
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    page++;
                    try {
//                        newsViewModel.loadNews(section_tag,String.valueOf(page));
                        adapter.addMoreData(new FetchNews().execute(section_tag,String.valueOf(page)).get());
                        adapter.notifyDataSetChanged();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(String webUrl) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(webUrl));
    }
}
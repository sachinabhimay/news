package com.app.news.models;

import com.app.news.utils.FetchNews;
import com.app.news.utils.News;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends ViewModel {
    private MutableLiveData<List<News>> news;

    public LiveData<List<News>> getNews(String section_name, String page) throws ExecutionException, InterruptedException {
        if(news == null){
            news = new MutableLiveData<List<News>>();
            loadNews(section_name, page);
        }
        return news;
    }
    public void loadNews(String section_name, String page) throws ExecutionException, InterruptedException {
        news.setValue(new FetchNews().execute(section_name, page).get());
    }

}

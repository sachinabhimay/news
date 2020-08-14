package com.app.news.utils;

import android.os.AsyncTask;
import java.util.List;

public class FetchNews extends AsyncTask<String, Void, List<News>> {
    private final String TAG = "FetchNews";

    @Override
    protected List<News> doInBackground(String... strings) {
        return NetworkUtils.getNewsJson(strings[0], strings[1]);
    }
}

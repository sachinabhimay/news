package com.app.news.utils;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    // Base URL for Books API.
    private static final String NEWS_BASE_URL =  "https://content.guardianapis.com/search";

    public static int PAGE_NUMBER = 1;

    public static final String SEARCH  = "q";

    public static final String SECTION = "section";

    public static final String PAGE = "page";

    public static final String PAGE_SIZE = "page-size";

    public static final String SHOW_FIELDS = "show-fields";

    public static final String HEADLINE = "headline";

    public static final String TRAIL_TEXT = "trailText";

    public static final String FIELDS = "thumbnail,trailText";

    public static final String USE_DATE = "use-date";

    public static final String API_KEY = "api-key";

    public static final String API = "test";


    public static List<News> getNewsJson(String queryString, String page){

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String newsJSONString = null;

        Uri builtURI = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(SEARCH, queryString)
                .appendQueryParameter(PAGE, page)
//                .appendQueryParameter(SECTION, queryString)
                .appendQueryParameter(SHOW_FIELDS, FIELDS)
                .appendQueryParameter(API_KEY, API)
                .build();

        Log.d(LOG_TAG, builtURI.toString());


        try {
            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            newsJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        List<News> list = new ArrayList<>();
        JSONObject jsonObject = null;
        JSONArray itemsArray = null;
        JSONObject jsonObjectResponse = null;
        try {
            jsonObject = new JSONObject(newsJSONString);
            jsonObjectResponse = jsonObject.getJSONObject("response");
            itemsArray = jsonObjectResponse.getJSONArray("results");

            int i = 0;

            while (i < itemsArray.length()) {

                JSONObject news = itemsArray.getJSONObject(i);

                try {
                    String sectionName = newsParse(news, null, "sectionName");
                    String webTitle = newsParse(news, null, "webTitle");
                    String webUrl = newsParse(news, null, "webUrl");
                    String webPublicationDate = newsParse(news, null, "webPublicationDate");
                    String thumbnailImage = newsParse(news, "fields", "thumbnail");
                    String trailText = newsParse(news, "fields", "trailText");

                    list.add(new News(webTitle,trailText, webPublicationDate, thumbnailImage, webUrl, sectionName));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String newsParse(JSONObject objectOne, String objectTwo, String key) throws JSONException {
        JSONObject jsonObject;
        String value;

        if (objectOne != null && objectOne.has(key)) {
            value = objectOne.getString(key);
            return value;
        } else if (objectOne != null && objectOne.has(objectTwo)) {
            jsonObject = objectOne.getJSONObject(objectTwo);
            value = jsonObject.getString(key);
            return value;
        }
        return null;
    }
}

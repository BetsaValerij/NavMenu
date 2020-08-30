package com.y.test.mvp.repository;


import android.content.Context;
import android.os.AsyncTask;

import com.y.test.R;
import com.y.test.mvp.model.CustomMenuItem;
import com.y.test.mvp.controller.MainController;
import com.y.test.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainRepository implements MainController.MainRepository {
    private Context ctx;

    public MainRepository(Context context){
        this.ctx = context;
    }

    public void getMenuData(LoadMenuCallback loadMenuCallback) {
        if (!Util.isNetworkConnected(ctx)){
            loadMenuCallback.onError(ctx.getString(R.string.error_network_connection));
            return;
        }
        GetJsonAsync getJsonAsync = new GetJsonAsync(loadMenuCallback);
        try {
            getJsonAsync.execute(ctx.getString(R.string.SERVER_URL)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public interface LoadMenuCallback{
        void onSuccess(List<CustomMenuItem> list);
        void onError(String message);
    }
    class GetJsonAsync extends AsyncTask<String, Void, List<CustomMenuItem>> {
        private LoadMenuCallback listener;
        public GetJsonAsync(LoadMenuCallback listener){
            this.listener = listener;
        }
        @Override
        protected List<CustomMenuItem> doInBackground(String... strings) {
            List<CustomMenuItem> list = new ArrayList<>();
            try {
                String urlResult = HTTPGetCall(strings[0]);
                if (urlResult != null){
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(urlResult);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (obj != null){
                       list = createMenu(obj);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return list;
        }
        private List<CustomMenuItem> createMenu(JSONObject obj){
            List<CustomMenuItem> customMenuItemList = new ArrayList<>();
            try {
                JSONArray jsonArray = obj.getJSONArray("menu");
                for (int i=0; i<jsonArray.length();i++){
                    try {
                        JSONObject o = jsonArray.getJSONObject(i);
                        CustomMenuItem item = new CustomMenuItem();
                        item.setName(o.getString("name"));
                        item.setFunction(o.getString("function"));
                        item.setParam(o.getString("param"));
                        customMenuItemList.add(item);
                        System.out.println(item.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return customMenuItemList;
        }
        public void onPostExecute(List<CustomMenuItem> result){
            if (listener != null) {
                listener.onSuccess(result);
            }
        }
    }
    public String HTTPGetCall(String WebMethodURL) throws IOException, MalformedURLException
    {
        StringBuilder response = new StringBuilder();
        URL u = new URL(WebMethodURL);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()),8192);
            String line = null;
            while ((line = input.readLine()) != null)
            {
                response.append(line);
            }
            input.close();
        }
        return response.toString();
    }
}

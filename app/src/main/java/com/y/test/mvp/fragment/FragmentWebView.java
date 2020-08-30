package com.y.test.mvp.fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.y.test.R;

import androidx.fragment.app.Fragment;

public class FragmentWebView extends Fragment {
    private static String ARG_URL_VALUE = "value";
    private static String mUrl;
    private WebView webView;
    private View v;

    public static Fragment newInstance(String value){
        FragmentWebView fragment = new FragmentWebView();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_URL_VALUE, value);
        fragment.setArguments(arguments);
        fragment.mUrl = value;
        return fragment;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_URL_VALUE, mUrl);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mUrl = savedInstanceState.getString(ARG_URL_VALUE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState){
        v = inflater.inflate(R.layout.fragment_web, parent, false);
        webView = v.findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        if (savedInstanseState != null) {
            mUrl = savedInstanseState.getString(ARG_URL_VALUE);

        }
        webView.loadUrl(mUrl);
        return v;
    }
    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            v.findViewById(R.id.progress).setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            v.findViewById(R.id.progress).setVisibility(View.GONE);
        }
    }
}

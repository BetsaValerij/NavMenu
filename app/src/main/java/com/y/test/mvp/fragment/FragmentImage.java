package com.y.test.mvp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.y.test.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentImage extends Fragment {
    private static final String ARG_IMAGE_VALUE = "value";
    private static String imageUrl;
    private ImageView imgView;
    private ProgressBar progressBar;
    private View view;

    public static Fragment newInstance(String value){
        FragmentImage fragment = new FragmentImage();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_IMAGE_VALUE, value);
        fragment.setArguments(arguments);
        fragment.imageUrl = value;
        return fragment;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_IMAGE_VALUE, imageUrl);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            imageUrl = savedInstanceState.getString(ARG_IMAGE_VALUE);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image, container, false);
        imgView = view.findViewById(R.id.itemImage);
        progressBar = view.findViewById(R.id.pb_load);
        if (savedInstanceState != null){
            imageUrl = savedInstanceState.getString(ARG_IMAGE_VALUE);

        }
        loadImage(imageUrl);
        return view;
    }
    private void loadImage(String url){
        if (!url.equalsIgnoreCase("")){
            progressBar.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(url)
                    .placeholder(android.R.drawable.ic_menu_report_image)// Place holder image from drawable folder
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(imgView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onError(Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Snackbar snackBar = Snackbar.make(view,
                                    getString(R.string.error_load_image), Snackbar.LENGTH_LONG);
                            snackBar.show();
                        }
                    });
        }
    }

}

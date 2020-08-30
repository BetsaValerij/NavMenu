package com.y.test.mvp.presenter;

import android.util.Log;

import com.y.test.mvp.model.CustomMenuItem;
import com.y.test.mvp.controller.MainController;
import com.y.test.mvp.repository.MainRepository;

import java.util.List;

public class MainPresenter implements MainController.MainPresenter {
    private MainRepository mainRepository;
    private MainController.MainView mView;

    public MainPresenter(MainRepository model) {
        this.mainRepository = model;
    }

    public void attachView(MainController.MainView view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }

    @Override
    public void loadMenuData() {
        mainRepository.getMenuData(new MainRepository.LoadMenuCallback() {
            @Override
            public void onSuccess(List<CustomMenuItem> list) {
                Log.d("Menu Loaded:", "Ok");
                mView.initNavigationDrawer(list);
            }

            @Override
            public void onError(String message) {
                Log.d("Menu Loaded:", "Error");
                mView.showError(message);
            }
        });
    }


}

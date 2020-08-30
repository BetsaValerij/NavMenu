package com.y.test.mvp.controller;

import com.y.test.mvp.model.CustomMenuItem;

import java.util.List;

public interface MainController {
    interface MainView{
        void showError(String error);
        void initNavigationDrawer(List<CustomMenuItem> list);

    }
    interface MainPresenter {
        void loadMenuData();
    }
    interface MainRepository {

    }
}

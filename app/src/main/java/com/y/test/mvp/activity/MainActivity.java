package com.y.test.mvp.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.y.test.R;
import com.y.test.mvp.model.CustomMenuItem;
import com.y.test.mvp.fragment.FragmentImage;
import com.y.test.mvp.fragment.FragmentText;
import com.y.test.mvp.fragment.FragmentWebView;
import com.y.test.mvp.controller.MainController;
import com.y.test.mvp.presenter.MainPresenter;
import com.y.test.mvp.repository.MainRepository;
import com.y.test.util.FRAGMENT_STATE;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements MainController.MainView {
    protected ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    MainPresenter mainPresenter;
    private static String ARG_ITEM = "item";
    private int selectedItem;
    private List<CustomMenuItem> menuItemList = new ArrayList<>();
    private List<MenuItem> menuItems;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        MainRepository mainRepository = new MainRepository(this);
        mainPresenter = new MainPresenter(mainRepository);
        mainPresenter.attachView(this);
        if (savedInstanceState == null) {
            mainPresenter.loadMenuData();
        } else {
            openFragmentBySelectedItem(selectedItem);
        }
    }
    protected void onSaveInstanceState(Bundle savedInstance) {
        super.onSaveInstanceState(savedInstance);
        savedInstance.putInt(ARG_ITEM, selectedItem);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedItem = savedInstanceState.getInt(ARG_ITEM);
    }
    @Override
    public void onResume(){
        super.onResume();
    }
    private void setupUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar mActionBar = getSupportActionBar();
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupActionBarDrawerToogle();
    }
    @Override
    public void initNavigationDrawer(List<CustomMenuItem> listItems) {
        if(mNavigationView != null) {
            if (listItems != null && listItems.size() > 0) {
                setupDrawerContent(listItems);
            }
        }
    }

    private void setupActionBarDrawerToogle() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                Snackbar.make(view, R.string.drawer_close, Snackbar.LENGTH_SHORT).show();
            }

            public void onDrawerOpened(View drawerView) {
                Snackbar.make(drawerView, R.string.drawer_open, Snackbar.LENGTH_SHORT).show();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void setupDrawerContent(List<CustomMenuItem> list) {
        menuItemList = list;
        menu = mNavigationView.getMenu();
        for (int i = 0; i < menuItemList.size(); i++) {
            menu.add(menuItemList.get(i).getName());
        }
        createMenuList(menu);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        selectedItem = menuItems.indexOf(menuItem);
                        openFragmentBySelectedItem(selectedItem);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        openFragmentBySelectedItem(selectedItem);
        mDrawerLayout.openDrawer(GravityCompat.START);
    }
    private void createMenuList(Menu menu){
        menuItems = new ArrayList<>();
        for (int i=0; i<menu.size(); i++){
            menuItems.add(menu.getItem(i));
        }
    }
    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openFragmentBySelectedItem(int position) {
        CustomMenuItem item = menuItemList.get(position);
        Fragment fragment = null;
        if (item != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FRAGMENT_STATE state = FRAGMENT_STATE.get(item.getFunction());

            switch (state) {
                case TEXT:
                    fragment = FragmentText.newInstance(item.getParam());
                    break;
                case IMAGE:
                    fragment = FragmentImage.newInstance(item.getParam());
                    break;
                case URL:
                    fragment = FragmentWebView.newInstance(item.getParam());
                    break;
            }
            ft.replace(R.id.container_fragment, fragment).commit();
        }
    }

    @Override
    public void showError(String error) {
        Snackbar.make(getWindow().getDecorView().getRootView(), error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }
}
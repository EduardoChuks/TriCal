package com.chukslabs.trical.ui.common;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.chukslabs.trical.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by echuquilin on 9/06/17.
 */
public class BaseActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) protected Toolbar mToolbar;
    @BindView(R.id.full_size_image_view) @Nullable protected ImageView mFullImageView;

    protected ActionBar mActionBar;

    protected void replaceFragment(Fragment fragment, boolean addToBackStack) {
        replaceFragment(R.id.main_content, fragment, addToBackStack);
    }

    protected void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        String tag = fragment.getClass().getSimpleName();
        transaction.replace(containerId, fragment, tag);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        configureToolbar();
    }

    private void configureToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(getString(R.string.app_name));
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            showBackButton(true);
        }
    }

    public void showBackButton(boolean show) {
        mActionBar.setDisplayHomeAsUpEnabled(show);
    }

    public void setToolbarColor(ColorDrawable colorDrawable) {
        if (mToolbar != null) mToolbar.setBackground(colorDrawable);
    }

    public void setActionBarTitle(String title) {
        mActionBar.setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void removeActionBarElevation() {
        mActionBar.setElevation(0);
    }

    public static boolean supportsLollipop() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }

}

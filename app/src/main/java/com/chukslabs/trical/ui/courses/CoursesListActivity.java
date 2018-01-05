package com.chukslabs.trical.ui.courses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chukslabs.trical.data.MyCoursesLang;
import com.chukslabs.trical.ui.common.BaseActivity;


/**
 * Created by echuquilin on 9/06/17.
 */
public class CoursesListActivity extends BaseActivity {

    private CoursesListFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            initViews();
            mFragment = CoursesListFragment.newInstance();
            replaceFragment(mFragment, false);
        }
    }

    private void initViews() {
        showBackButton(false);
        setActionBarTitle(MyCoursesLang.MY_COURSES_TITLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, CoursesListActivity.class);
    }

}

package com.chukslabs.trical.ui.courses.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chukslabs.trical.data.MyCoursesLang;
import com.chukslabs.trical.ui.common.BaseActivity;

/**
 * Created by echuquilin on 27/06/17.
 */
public class AddCourseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setActionBarTitle(MyCoursesLang.ADD_COURSE_TITLE);
            replaceFragment(AddCourseFragment.newInstance(), false);
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCourseActivity.class);
    }

}

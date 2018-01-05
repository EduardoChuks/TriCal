package com.chukslabs.trical.ui.courses.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.chukslabs.trical.R;
import com.chukslabs.trical.data.MyCoursesLang;
import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.ui.common.BaseActivity;

/**
 * Created by echuquilin on 4/08/17.
 */
public class CourseDetailActivity extends BaseActivity {

    private CourseDetailFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Course course = (Course) getIntent().getSerializableExtra(MyCoursesLang.EXTRA_OBJECT_COURSE);
            setActionBarTitle(course.getName());
            mFragment = CourseDetailFragment.newInstance(course);
            replaceFragment(mFragment, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, CourseDetailActivity.class);
    }

}

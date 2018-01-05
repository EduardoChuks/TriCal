package com.chukslabs.trical.ui.exams;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.chukslabs.trical.R;
import com.chukslabs.trical.data.MyCoursesLang;
import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.ui.common.BaseActivity;

import static com.chukslabs.trical.data.MyCoursesLang.EXTRA_OBJECT_COURSE;

/**
 * Created by educhuks on 10/9/17.
 */

public class AddExamsActivity extends BaseActivity {

    private AddExamsFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Course course = (Course) getIntent().getExtras().getSerializable(EXTRA_OBJECT_COURSE);
            if (course.getExams().size() == 0) {
                setActionBarTitle(MyCoursesLang.ADD_EXAMS_TITLE);
            } else {
                setActionBarTitle(MyCoursesLang.UPDATE_EXAMS_TITLE);
            }
            mFragment = AddExamsFragment.newInstance(course);
            replaceFragment(mFragment, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_exams, menu);
        enableSaveButton(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                mFragment.prepareGrades();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void enableSaveButton(boolean enable) {
        MenuItem item = mToolbar.getMenu().findItem(R.id.action_save);
        Drawable resIcon = getResources().getDrawable(R.drawable.ic_save);
        int colorIcon = enable ? getResources().getColor(R.color.white) : getResources().getColor(R.color.menuItemDisabled);
        resIcon.mutate().setColorFilter(colorIcon, PorterDuff.Mode.SRC_IN);
        if (item != null) {
            item.setEnabled(enable);
            item.setIcon(resIcon);
        }
    }

    @Override
    public void onBackPressed() {
        mFragment.onBackPressed();
    }

    public static Intent makeIntent(Context context, Course course) {
        Intent intent = new Intent(context, AddExamsActivity.class);
        intent.putExtra(EXTRA_OBJECT_COURSE, course);
        return intent;
    }

}

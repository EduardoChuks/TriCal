package com.chukslabs.trical.ui.courses;

import android.support.v4.app.FragmentActivity;

import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.ui.common.BaseView;

/**
 * Created by echuquilin on 9/06/17.
 */
public interface CoursesListView extends BaseView<CoursesListPresenter> {

    void startAddCourse();
    void updateCoursesList();
    void hideNoDataView();
    void showNoDataView();
    void showRemoveDialog(Course course);
    void showSnackCourseAdded(String courseName, String message);
    void showSnackCourseRemoved(Course course);
    void goToDetailScreen(Course course);
    FragmentActivity getContext();

}

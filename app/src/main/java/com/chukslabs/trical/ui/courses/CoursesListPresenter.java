package com.chukslabs.trical.ui.courses;

import com.chukslabs.trical.data.MyCoursesLang;
import com.chukslabs.trical.managers.PreferencesManager;
import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.ui.common.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by echuquilin on 9/06/17.
 */
public class CoursesListPresenter extends BasePresenter<CoursesListView> {

    private List<Course> mCourses;

    public CoursesListPresenter(CoursesListView view) {
        super(view);
    }

    public void onAddCourseButtonClicked() {
        mView.startAddCourse();
    }

    public List<Course> getCourses() {
        return mCourses;
    }

    public void addCourse(Course newCourse, boolean isNewCourse) {
        PreferencesManager.get(mView.getContext()).addCourse(newCourse);
        loadData();
        if (isNewCourse) {
            mView.showSnackCourseAdded(newCourse.getName(), MyCoursesLang.SNACKBAR_COURSE_ADDED_MSG);
        }
    }

    public void removeCourse(Course course) {
        PreferencesManager.get(mView.getContext()).removeCourse(course);
        loadData();
        mView.showSnackCourseRemoved(course);
    }

    public void loadData() {
        mCourses = new ArrayList<>();
        mCourses = PreferencesManager.get(mView.getContext()).loadCourses();
        if (mCourses.size() > 0) {
            mView.hideNoDataView();
            mView.updateCoursesList();
        } else {
            mView.showNoDataView();
        }
    }

}

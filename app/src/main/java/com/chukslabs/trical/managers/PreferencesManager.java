package com.chukslabs.trical.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.model.Exam;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by echuquilin on 28/06/17.
 */
public class PreferencesManager {

    private static PreferencesManager mInstance;

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public static final String COURSES_PREF_NAME = "my_courses_pref";
    public static final String COURSES_LIST_ID = "all_courses_id";
    public static final String INSTITUTIONS_LIST_ID = "all_inst_id";

    private PreferencesManager(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(COURSES_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesManager get(Context context) {
        if (mInstance == null) {
            mInstance = new PreferencesManager(context);
        }
        return mInstance;
    }

    public List<Course> loadCourses() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString(COURSES_LIST_ID, "");
        Courses courses = new Courses();
        if (!json.isEmpty()) {
            courses = gson.fromJson(json, Courses.class);
        }
        return courses.getList();
    }

    public void addCourse(Course course) {
        List<Course> list = loadCourses();
        list.add(course);
        updateCourses(list);
    }

    public void updateExams(List<Exam> exams, Course course) {
        List<Course> list = loadCourses();
        int index = getCourseIndex(course, list);
        list.get(index).setExams(exams);
        updateCourses(list);
    }

    public void updateGrades(int examIndex, List<Integer> grades, Course course) {
        List<Course> list = loadCourses();
        int index = getCourseIndex(course, list);
        list.get(index).getExams().get(examIndex).setGrades(grades);
        updateCourses(list);
    }

    public void updateFinalGrade(int finalGrade, Course course) {
        List<Course> list = loadCourses();
        int index = getCourseIndex(course, list);
        list.get(index).setFinalGrade(finalGrade);
        updateCourses(list);
    }

    public void removeCourse(Course course) {
        List<Course> list = loadCourses();
        int index = getCourseIndex(course, list);
        list.remove(index);
        updateCourses(list);
    }

    public int getCourseIndex(Course course, List<Course> list) {
        int index = 0;
        while (!list.get(index).getName().equalsIgnoreCase(course.getName())) {
            index++;
        }
        return index;
    }

    private void updateCourses(List<Course> list) {
        SharedPreferences.Editor prefsEditor = mSharedPreferences.edit();
        Gson gson = new Gson();
        Courses courses = new Courses(list);
        String json = gson.toJson(courses);
        prefsEditor.putString(COURSES_LIST_ID, json);
        prefsEditor.commit();
    }

    public Course getCourseByCode(String courseCode) {
        List<Course> courses = PreferencesManager.get(mContext).loadCourses();
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCode().equalsIgnoreCase(courseCode)) {
                return courses.get(i);
            }
        }
        return null;
    }

    private class Courses {

        private List<Course> mList;

        public Courses() {
            mList = new ArrayList<>();
        }

        public Courses(List<Course> list) {
            mList = list;
        }

        public List<Course> getList() {
            return mList;
        }

    }

}

package com.chukslabs.trical.ui.courses.add;

import android.support.v4.app.FragmentActivity;

import com.chukslabs.trical.ui.common.BaseView;

/**
 * Created by echuquilin on 27/06/17.
 */
public interface AddCourseView extends BaseView<AddCoursePresenter> {

    void enableButton(boolean enable);
    void finishActivity();
    void showErrorDialog(String title, String msg);
    boolean getTruncTextViewVisibility();
    String getCode();
    String getName();
    String getMinGrade();
    String getMaxGrade();
    String getTruncDecimals();
    FragmentActivity getContext();

}

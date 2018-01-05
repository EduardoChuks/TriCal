package com.chukslabs.trical.ui.courses.add;

import com.chukslabs.trical.data.MyCoursesLang;
import com.chukslabs.trical.managers.PreferencesManager;
import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.ui.common.BasePresenter;

import java.util.List;

/**
 * Created by echuquilin on 27/06/17.
 */
public class AddCoursePresenter extends BasePresenter<AddCourseView> {

    public AddCoursePresenter(AddCourseView view) {
        super(view);
    }

    public void onAddCourseButtonClicked() {
        ExistHelper existHelper = exists(mView.getCode(), mView.getName());
        if (!existHelper.exists) {
            if (validateGradesValues()) {
                mView.finishActivity();
            } else {
                mView.showErrorDialog(MyCoursesLang.DIALOG_ERROR, MyCoursesLang.DIALOG_COURSE_GRADES_ERROR);
            }
        } else {
            mView.showErrorDialog(MyCoursesLang.DIALOG_ERROR, existHelper.msg);
        }
    }

    private boolean validateGradesValues() {
        return (Integer.valueOf(mView.getMaxGrade()) > Integer.valueOf(mView.getMinGrade()));
    }

    private ExistHelper exists(String code, String name) {
        List<Course> list = PreferencesManager.get(mView.getContext()).loadCourses();
        ExistHelper existHelper = new ExistHelper();
        for (Course course : list) {
            if (course.getName().equalsIgnoreCase(name)) {
                existHelper.setExists();
                if (course.getCode() != null && course.getCode().equalsIgnoreCase(code)) {
                    existHelper.msg = MyCoursesLang.DIALOG_COURSE_DUPLICATED_CODE;
                }
                return existHelper;
            }
        }
        return existHelper;
    }

    public void validateFields() {
        if (!mView.getName().isEmpty() && !mView.getMinGrade().isEmpty() &&
                !mView.getMaxGrade().isEmpty() && checkForRadioButtons()) {
            mView.enableButton(true);
        } else {
            mView.enableButton(false);
        }
    }

    private boolean checkForRadioButtons() {
        return !(mView.getTruncTextViewVisibility() && mView.getTruncDecimals().isEmpty());
    }

    public class ExistHelper {

        public boolean exists;
        public String msg;

        public ExistHelper() {
            this.exists = false;
            this.msg = "";
        }

        public void setExists() {
            this.exists = true;
            this.msg = MyCoursesLang.DIALOG_COURSE_DUPLICATED;
        }

    }

}

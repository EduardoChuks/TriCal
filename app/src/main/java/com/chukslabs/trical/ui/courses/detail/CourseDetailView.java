package com.chukslabs.trical.ui.courses.detail;

import android.content.Context;

import com.chukslabs.trical.model.Exam;
import com.chukslabs.trical.ui.common.BaseView;

import java.util.List;

/**
 * Created by echuquilin on 4/08/17.
 */
public interface CourseDetailView extends BaseView<CourseDetailPresenter> {

    void setFinalGrade(String finalGrade);
    void setFormula(String top, String bottom);
    void setExams(List<Exam> exams);
    void beginDelayedTransition();
    void updateGrade(String examName, int index, int grade);
    void setExamAverage(int examIndex);
    void goToConfigFormulaScreen();
    void finishActivity();
    void hideKeyboard();

    Context getContext();

}

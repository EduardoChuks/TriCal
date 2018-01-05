package com.chukslabs.trical.ui.exams;

import com.chukslabs.trical.model.Exam;
import com.chukslabs.trical.ui.common.BaseView;

import java.util.List;

/**
 * Created by educhuks on 10/9/17.
 */

public interface AddExamsView extends BaseView<AddExamsPresenter> {

    void addExam();
    void removeExam(int index);
    void setExams(List<Exam> exams);
    void hideKeyboard();
    void validateExams();
    void finishActivity(List<Exam> exams);

}

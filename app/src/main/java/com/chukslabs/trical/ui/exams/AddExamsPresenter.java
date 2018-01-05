package com.chukslabs.trical.ui.exams;

import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.model.Exam;
import com.chukslabs.trical.ui.common.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by educhuks on 10/9/17.
 */

public class AddExamsPresenter extends BasePresenter<AddExamsView> {

    private List<Exam> mExams;
    private boolean hasDataLoaded;

    public AddExamsPresenter(AddExamsView view) {
        super(view);
        mExams = new ArrayList<>();
        hasDataLoaded = false;
    }

    public void setDataLoaded() {
        hasDataLoaded = true;
    }

    public void loadExams(Course course) {
        mExams = course.getExams();
    }

    public void loadData() {
        if (mExams.size() > 0) {
            mView.setExams(mExams);
        }
    }

    public List<Exam> getExams() {
        return mExams;
    }

    public List<Exam> prepareGrades(List<Exam> exams) {
        for (int i = 0; i < exams.size(); i++) {
            Exam exam = exams.get(i);
            int quantity = exam.getQuantity();
            int gradesSize = exam.getGrades().size();
            List<Integer> grades = new ArrayList<>();
            if (gradesSize == 0) {
                for (int j = 0; j < quantity; j++) {
                    grades.add(-1);
                }
            } else if (quantity <= gradesSize) {
                for (int j = 0; j < quantity; j++) {
                    grades.add(exam.getGrades().get(j));
                }
            } else {
                for (int j = 0; j < gradesSize; j++) {
                    grades.add(exam.getGrades().get(j));
                }
                for (int j = 0; j < quantity - gradesSize; j++) {
                    grades.add(-1);
                }
            }
            exams.get(i).setGrades(grades);
        }
        return exams;
    }

}

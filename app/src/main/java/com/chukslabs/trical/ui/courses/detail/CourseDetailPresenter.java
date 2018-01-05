package com.chukslabs.trical.ui.courses.detail;

import com.chukslabs.trical.data.MyCoursesLang;
import com.chukslabs.trical.managers.PreferencesManager;
import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.model.Exam;
import com.chukslabs.trical.ui.common.BasePresenter;

import java.util.List;

import static com.chukslabs.trical.util.GradeUtils.getFinalGrade;

/**
 * Created by echuquilin on 4/08/17.
 */
public class CourseDetailPresenter extends BasePresenter<CourseDetailView> {

    private Course mCourse;

    public CourseDetailPresenter(CourseDetailView view) {
        super(view);
    }

    public void loadCourse(Course course) {
        mCourse = course;
    }

    public void loadData() {
        if (mCourse.getExams().size() > 0) {
            int finalGrade = mCourse.getFinalGrade();
            mView.setFormula(getFormulaTop(), getFormulaBottom());
            mView.setFinalGrade(finalGrade != -1 ? String.valueOf(finalGrade) : MyCoursesLang.DETAIL_FINAL_GRADE_LEFT);
            mView.setExams(mCourse.getExams());
        } else {
            mView.goToConfigFormulaScreen();
        }
    }

    public void updateGrade(String examName, int index, int grade) {
        int examIndex = mCourse.updateGrade(examName, index, grade);
        PreferencesManager.get(mView.getContext()).updateGrades(examIndex, mCourse.getExams().get(examIndex).getGrades(), mCourse);
        int finalGrade = getFinalGrade(mCourse);
        PreferencesManager.get(mView.getContext()).updateFinalGrade(finalGrade, mCourse);
        mView.setFinalGrade(finalGrade != -1 ? String.valueOf(finalGrade) : MyCoursesLang.DETAIL_FINAL_GRADE_LEFT);
        mView.setExamAverage(examIndex);
    }

    public List<Exam> getExams() {
        return mCourse.getExams();
    }

    public Course getCourse() {
        return mCourse;
    }

    public void setExamsData(List<Exam> exams) {
        mCourse.setExams(exams);
        PreferencesManager.get(mView.getContext()).updateExams(exams, mCourse);
        if (exams.size() == 0) {
            mView.finishActivity();
        } else {
            loadData();
        }
    }

    private String getFormulaTop() {
        String finalFormat = "";
        for (int i = 0; i < mCourse.getExams().size(); i++) {
            Exam exam = mCourse.getExams().get(i);
            if (i == 0) {
                finalFormat += String.format("%d%s", exam.getWeight(), exam.getName());
            } else {
                finalFormat += String.format(" + %d%s", exam.getWeight(), exam.getName());
            }
        }
        return finalFormat;
    }

    private String getFormulaBottom() {
        int totalWeight = 0;
        for (Exam exam : mCourse.getExams()) {
            totalWeight += exam.getWeight();
        }
        return String.valueOf(totalWeight);
    }

}

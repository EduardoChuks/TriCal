package com.chukslabs.trical.util.views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chukslabs.trical.R;
import com.chukslabs.trical.model.CalcMethod;
import com.chukslabs.trical.model.Exam;
import com.chukslabs.trical.ui.courses.detail.CourseDetailView;
import com.chukslabs.trical.util.GradeUtils;

import java.util.List;

/**
 * Created by educhuks on 10/9/17.
 */

public class ExamCustomView extends LinearLayout {

    private Exam exam;
    private CalcMethod calcMethod;
    private Context context;
    private CourseDetailView courseDetailView;
    private TextView examAvg;

    public ExamCustomView(Context context, Exam exam, CalcMethod calcMethod, CourseDetailView view) {
        super(context);
        this.context = context;
        this.exam = exam;
        this.calcMethod = calcMethod;
        this.courseDetailView = view;
        inflate();
    }

    private void inflate(){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_exam, this);

        View examContainer = findViewById(R.id.exam_container);
        examAvg = findViewById(R.id.item_exam_average);
        final TextView examName = findViewById(R.id.item_exam_name);
        final LinearLayout gradesLayout = findViewById(R.id.grades_linear_layout);
        final View examsDivider = findViewById(R.id.exams_divider);
        final View examBottomShadow = findViewById(R.id.exam_name_bottom_shadow);

        examName.setText(exam.getName());
        examAvg.setText(GradeUtils.getExamAvgTitle(exam, calcMethod));

        examContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                courseDetailView.beginDelayedTransition();
                if (gradesLayout.getVisibility() == GONE) {
                    examsDivider.setVisibility(GONE);
                    examBottomShadow.setVisibility(VISIBLE);
                    gradesLayout.setVisibility(VISIBLE);
                    examName.setTypeface(Typeface.DEFAULT_BOLD);
                    examAvg.setTypeface(Typeface.DEFAULT_BOLD);
                }
                else {
                    courseDetailView.hideKeyboard();
                    gradesLayout.setVisibility(GONE);
                    examBottomShadow.setVisibility(GONE);
                    examsDivider.setVisibility(VISIBLE);
                    examName.setTypeface(Typeface.DEFAULT);
                    examAvg.setTypeface(Typeface.DEFAULT);
                }
            }
        });

        List<Integer> grades = exam.getGrades();
        for (int i = 1; i <= grades.size(); i++) {
            gradesLayout.addView(new GradeCustomView(context, courseDetailView, grades.get(i - 1), exam, i - 1));
        }
    }

    public void updateExamAverage() {
        examAvg.setText(GradeUtils.getExamAvgTitle(exam, calcMethod));
    }

}

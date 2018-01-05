package com.chukslabs.trical.util.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chukslabs.trical.R;
import com.chukslabs.trical.data.MyCoursesLang;
import com.chukslabs.trical.model.Exam;
import com.chukslabs.trical.ui.courses.detail.CourseDetailView;

/**
 * Created by educhuks on 10/9/17.
 */

public class GradeCustomView extends LinearLayout {

    private int grade;
    private Context context;
    private Exam exam;
    private CourseDetailView view;
    private int index;

    public GradeCustomView(Context context, CourseDetailView courseDetailView, int grade, Exam exam, int index) {
        super(context);
        this.context = context;
        this.grade = grade;
        this.exam = exam;
        this.index = index;
        this.view = courseDetailView;
        inflate();
    }

    private void inflate(){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_grade, this);

        String gradeName = String.format("%s %d", exam.getName(), index + 1);
        if (exam.getQuantity() == 1) {
            gradeName = MyCoursesLang.DETAIL_GRADE_NAME;
        }

        ((TextView) findViewById(R.id.item_grade_name)).setText(gradeName);
        EditText gradeValue = findViewById(R.id.item_grade_value);

        gradeValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()) {
                    view.updateGrade(exam.getName(), index, Integer.valueOf(charSequence.toString()));
                } else {
                    view.updateGrade(exam.getName(), index, -1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        if (grade == -1) {
            gradeValue.setText("");
        } else {
            gradeValue.setText(String.valueOf(grade));
        }

        if (index > 0) {
            findViewById(R.id.general_divider).setVisibility(VISIBLE);
        }
    }

}

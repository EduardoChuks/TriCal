package com.chukslabs.trical.util.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chukslabs.trical.R;
import com.chukslabs.trical.model.Exam;
import com.chukslabs.trical.ui.exams.AddExamsView;

/**
 * Created by educhuks on 10/15/17.
 */

public class CardCustomView extends LinearLayout {

    private Exam exam;
    private Context context;
    private AddExamsView addExamsView;
    private int index;

    private EditText examName;
    private EditText examWeight;
    private EditText examQuant;
    private CheckBox examCheckMin;
    private View removeExam;

    public CardCustomView(Context context, Exam exam, AddExamsView view, int index) {
        super(context);
        this.context = context;
        this.exam = exam;
        this.addExamsView = view;
        this.index = index;
        inflate();
        setViews();
        setListeners();
    }

    private void inflate(){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_card_exam, this);

        examName = findViewById(R.id.element_exam_name);
        examWeight = findViewById(R.id.element_exam_weight);
        examQuant = findViewById(R.id.element_exam_quantity);
        examCheckMin = findViewById(R.id.element_exam_checkbox);
        removeExam = findViewById(R.id.element_exam_remove);
    }

    private void setViews() {
        if (exam != null) {
            examName.setText(exam.getName());
            examWeight.setText(String.valueOf(exam.getWeight()));
            examQuant.setText(String.valueOf(exam.getQuantity()));
            if (exam.getQuantity() > 1) {
                examCheckMin.setEnabled(true);
                if (exam.isRemoveMin()) {
                    examCheckMin.setChecked(true);
                }
            }
        }
    }

    private void setListeners() {
        removeExam.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addExamsView.hideKeyboard();
                addExamsView.removeExam(index);
            }
        });
        examCheckMin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addExamsView.validateExams();
            }
        });
        examName.addTextChangedListener(saveTextWatcher);
        examWeight.addTextChangedListener(saveTextWatcher);
        examQuant.addTextChangedListener(saveTextWatcher);
        examQuant.addTextChangedListener(checkButtonWatcher);
    }

    public void reduceIndex() {
        index-=1;
    }

    public String getExamName() {
        return examName.getText().toString().trim();
    }

    public String getExamWeight() {
        return examWeight.getText().toString().trim();
    }

    public String getExamQuant() {
        return examQuant.getText().toString().trim();
    }

    public boolean getRemoveMin() {
        return (examCheckMin.isEnabled() && examCheckMin.isChecked());
    }

    public Exam getUpdatedExam() {
        if (exam == null) {
            return new Exam(getExamName(), Integer.valueOf(getExamWeight()), Integer.valueOf(getExamQuant()), getRemoveMin());
        } else {
            exam.setName(getExamName());
            exam.setWeight(Integer.valueOf(getExamWeight()));
            exam.setQuantity(Integer.valueOf(getExamQuant()));
            exam.setRemoveMin(getRemoveMin());
            return exam;
        }
    }

    private TextWatcher saveTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            addExamsView.validateExams();
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    private TextWatcher checkButtonWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!charSequence.toString().isEmpty() && Integer.valueOf(charSequence.toString()) > 1) {
                examCheckMin.setEnabled(true);
            } else {
                examCheckMin.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };
    
}

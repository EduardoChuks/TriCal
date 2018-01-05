package com.chukslabs.trical.ui.courses.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chukslabs.trical.R;
import com.chukslabs.trical.data.MyCoursesLang;
import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.model.Exam;
import com.chukslabs.trical.model.Exams;
import com.chukslabs.trical.ui.exams.AddExamsActivity;
import com.chukslabs.trical.util.KeyboardUtils;
import com.chukslabs.trical.util.views.ExamCustomView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by echuquilin on 4/08/17.
 */
public class CourseDetailFragment extends Fragment implements CourseDetailView {

    /** Views managing with Butterknife **/

    @BindView(R.id.course_final_grade) TextView mCourseFinalGrade;
    @BindView(R.id.modify_formula_button) ImageView mUpdateFormulaButton;
    @BindView(R.id.exams_linear_layout) LinearLayout mExamsLinearLayout;
    @BindView(R.id.final_grade_container) View mFinalGradeContainer;
    @BindView(R.id.exams_scroll_view) ScrollView mExamsScrollView;
    @BindView(R.id.formula_top_text) TextView mFormulaTopText;
    @BindView(R.id.formula_bottom_text) TextView mFormulaBottomText;
    @BindView(R.id.formula_divider) View mFormulaDivider;
    @BindView(R.id.transition_container) ViewGroup mTransitionContainer;

    @OnClick(R.id.modify_formula_button)
    public void modifyButtonClicked() {
        Intent intent = AddExamsActivity.makeIntent(getActivity(), mPresenter.getCourse());
        startActivityForResult(intent, ADD_EXAM_CODE);
    }

    /** Fragment **/

    public static final int ADD_EXAM_CODE = 1002;

    private CourseDetailPresenter mPresenter;
    private CourseDetailActivity mActivity;

    public static CourseDetailFragment newInstance(Course course) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MyCoursesLang.EXTRA_OBJECT_COURSE, course);
        CourseDetailFragment fragment = new CourseDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = context instanceof CourseDetailActivity ? (CourseDetailActivity) context : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_course_detail, container, false);
        ButterKnife.bind(this, rootView);
        if (savedInstanceState == null) {
            setPresenter(new CourseDetailPresenter(this));
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            setupScrollView();
            mPresenter.loadCourse((Course) getArguments().getSerializable(MyCoursesLang.EXTRA_OBJECT_COURSE));
            mPresenter.loadData();
        }
    }

    private void setupScrollView() {
        mExamsScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        mExamsScrollView.setFocusable(true);
        mExamsScrollView.setFocusableInTouchMode(true);
        mExamsScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                KeyboardUtils.hideKeyboard(getActivity(), v);
                return false;
            }
        });
    }

    @Override
    public void goToConfigFormulaScreen() {
        Intent intent = AddExamsActivity.makeIntent(getActivity(), mPresenter.getCourse());
        startActivityForResult(intent, ADD_EXAM_CODE);
    }

    @Override
    public void setFinalGrade(String finalGrade) {
        mCourseFinalGrade.setText(finalGrade);
    }

    @Override
    public void setFormula(String top, String bottom) {
        mFormulaTopText.setText(top);
        if (Integer.valueOf(bottom) > 1) {
            mFormulaBottomText.setVisibility(View.VISIBLE);
            mFormulaDivider.setVisibility(View.VISIBLE);
            mFormulaBottomText.setText(bottom);
        } else {
            mFormulaBottomText.setVisibility(View.GONE);
            mFormulaDivider.setVisibility(View.GONE);
        }
    }

    @Override
    public void setExams(List<Exam> exams) {
        mExamsLinearLayout.removeAllViews();
        for (int i = 0; i < exams.size(); i++) {
            mExamsLinearLayout.addView(new ExamCustomView(getActivity(), exams.get(i), mPresenter.getCourse().getCalcMethod(), this));
        }
    }

    @Override
    public void beginDelayedTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionManager.beginDelayedTransition(mTransitionContainer);
        }
    }

    @Override
    public void updateGrade(String examName, int index, int grade) {
        mPresenter.updateGrade(examName, index, grade);
    }

    @Override
    public void setExamAverage(int examIndex) {
        ExamCustomView customView = (ExamCustomView) mExamsLinearLayout.getChildAt(examIndex);
        if (customView != null) {
            customView.updateExamAverage();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_EXAM_CODE && data != null) {
            if (resultCode == Activity.RESULT_OK) {
                Exams exams = (Exams) data.getSerializableExtra(MyCoursesLang.EXTRA_OBJECT_EXAMS);
                mPresenter.setExamsData(exams.getExams());
            }
            if (resultCode == Activity.RESULT_CANCELED && mPresenter.getCourse().getExams().size() == 0) {
                finishActivity();
            }
        }
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideKeyboard(getActivity(), getActivity().getCurrentFocus());
    }

    @Override
    public void finishActivity() {
        mActivity.onBackPressed();
    }

    @Override
    public void setPresenter(CourseDetailPresenter presenter) {
        mPresenter = presenter;
    }

}

package com.chukslabs.trical.ui.exams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chukslabs.trical.R;
import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.model.Exam;
import com.chukslabs.trical.model.Exams;
import com.chukslabs.trical.util.KeyboardUtils;
import com.chukslabs.trical.util.views.CardCustomView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chukslabs.trical.data.MyCoursesLang.EXTRA_OBJECT_COURSE;
import static com.chukslabs.trical.data.MyCoursesLang.EXTRA_OBJECT_EXAMS;

/**
 * Created by educhuks on 10/9/17.
 */

public class AddExamsFragment extends Fragment implements AddExamsView {

    /** Views managing with Butterknife **/

    @BindView(R.id.exams_linear_layout) LinearLayout mExamsLinearLayout;
    @BindView(R.id.no_data_text_view) TextView mNoDataTextView;
    @BindView(R.id.exams_scroll_view) ScrollView mExamsScrollView;

    @OnClick(R.id.add_exam_button)
    public void onAddExamClicked(){
        addExam();
    }

    /** Fragment **/

    private AddExamsPresenter mPresenter;
    private AddExamsActivity mActivity;
    private List<CardCustomView> mCustomViews;

    public static AddExamsFragment newInstance(Course course) {
        AddExamsFragment fragment = new AddExamsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_OBJECT_COURSE, course);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = context instanceof AddExamsActivity ? (AddExamsActivity) context : null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_add_exams, container, false);
        ButterKnife.bind(this, rootView);
        if (savedInstanceState == null) {
            setPresenter(new AddExamsPresenter(this));
            mCustomViews = new ArrayList<>();
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            setupScrollView();
            Course course = (Course) getArguments().getSerializable(EXTRA_OBJECT_COURSE);
            mPresenter.loadExams(course);
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
    public void setExams(List<Exam> exams) {
        mExamsLinearLayout.removeAllViews();
        mCustomViews.clear();
        for (int i = 0; i < exams.size(); i++) {
            CardCustomView cardCustomView = new CardCustomView(getContext(), exams.get(i), this, i);
            mCustomViews.add(cardCustomView);
            mExamsLinearLayout.addView(cardCustomView);
        }
    }

    @Override
    public void addExam() {
        CardCustomView cardCustomView = new CardCustomView(getContext(), null, this, mCustomViews.size());
        mCustomViews.add(cardCustomView);
        mExamsLinearLayout.addView(cardCustomView);
    }

    @Override
    public void removeExam(int index) {
        for (int i = index + 1; i < mCustomViews.size(); i++) {
            mCustomViews.get(i).reduceIndex();
        }
        mCustomViews.remove(index);
        mExamsLinearLayout.removeViewAt(index);
    }

    public void prepareGrades() {
        finishActivity(mPresenter.prepareGrades(getExams()));
    }

    private List<Exam> getExams() {
        List<Exam> exams = new ArrayList<>();
        for (int i = 0; i < mExamsLinearLayout.getChildCount(); i++) {
            CardCustomView child = (CardCustomView) mExamsLinearLayout.getChildAt(i);
            exams.add(child.getUpdatedExam());
        }
        return exams;
    }

    @Override
    public void validateExams() {
        mActivity.enableSaveButton(areExamsFilled());
    }

    private boolean areExamsFilled() {
        for (int i = 0; i < mExamsLinearLayout.getChildCount(); i++) {
            CardCustomView child = (CardCustomView) mExamsLinearLayout.getChildAt(i);
            if (child.getExamName().isEmpty() || child.getExamWeight().isEmpty() || child.getExamQuant().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void hideKeyboard() {
        KeyboardUtils.hideKeyboard(getActivity(), getActivity().getCurrentFocus());
    }

    @Override
    public void finishActivity(List<Exam> exams) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_OBJECT_EXAMS, new Exams(exams));
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        getActivity().finish();
    }

    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_OBJECT_EXAMS, new Exams());
        getActivity().setResult(Activity.RESULT_CANCELED, returnIntent);
        getActivity().finish();
    }

    @Override
    public void setPresenter(AddExamsPresenter presenter) {
        mPresenter = presenter;
    }

}

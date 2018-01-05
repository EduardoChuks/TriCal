package com.chukslabs.trical.ui.courses.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;

import com.chukslabs.trical.R;
import com.chukslabs.trical.data.MyCoursesLang;
import com.chukslabs.trical.model.CalcMethod;
import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.util.DialogUtils;
import com.chukslabs.trical.util.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

import static android.view.View.GONE;

/**
 * Created by echuquilin on 7/07/17.
 */
public class AddCourseFragment extends Fragment implements AddCourseView {

    /** Views managing with Butterknife **/

    @BindView(R.id.course_code) EditText mCourseCode;
    @BindView(R.id.course_name) EditText mCourseName;
    @BindView(R.id.course_min_grade) EditText mCourseMinGrade;
    @BindView(R.id.course_max_grade) EditText mCourseMaxGrade;
    @BindView(R.id.course_trunc_decimals) EditText mCourseTruncDecimals;
    @BindView(R.id.add_course_button) Button mAddCourseButton;
    @BindView(R.id.course_radio_round) RadioButton mRadioRound;
    @BindView(R.id.course_radio_trunc) RadioButton mRadioTrunc;
    @BindView(R.id.course_radio_round_up) RadioButton mRadioRoundUp;
    @BindView(R.id.course_radio_round_down) RadioButton mRadioRoundDown;
    @BindView(R.id.option_round_container) View mOptionRoundContainer;
    @BindView(R.id.form_scroll_view) ScrollView mFormScrollView;

    @OnClick(R.id.add_course_button)
    public void onAddCourseButtonClicked() {
        mPresenter.onAddCourseButtonClicked();
    }

    @OnClick(R.id.course_radio_round)
    public void onRadioRoundClicked() {
        if (mRadioRound.isChecked()) {
            mCourseTruncDecimals.setVisibility(GONE);
            mOptionRoundContainer.setVisibility(View.VISIBLE);
            mCourseMaxGrade.setImeOptions(EditorInfo.IME_ACTION_DONE);
            mPresenter.validateFields();
        }
    }

    @OnClick(R.id.course_radio_trunc)
    public void onRadioTruncClicked() {
        if (mRadioTrunc.isChecked()) {
            mOptionRoundContainer.setVisibility(GONE);
            mCourseTruncDecimals.setVisibility(View.VISIBLE);
            mCourseMaxGrade.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            mPresenter.validateFields();
        }
    }

    @OnEditorAction(R.id.course_code)
    protected boolean onEditorCourseCode(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            mCourseName.requestFocus();
            return true;
        }
        return false;
    }

    @OnEditorAction(R.id.course_name)
    protected boolean onEditorCourseName(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            mCourseMinGrade.requestFocus();
            return true;
        }
        return false;
    }

    @OnEditorAction(R.id.course_min_grade)
    protected boolean onEditorCourseMinGrade(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            mCourseMaxGrade.requestFocus();
            return true;
        }
        return false;
    }

    @OnEditorAction(R.id.course_max_grade)
    protected boolean onEditorCourseMaxGrade(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            mCourseTruncDecimals.requestFocus();
            return true;
        }
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            KeyboardUtils.hideKeyboard(getActivity(), mCourseMaxGrade);
            mCourseMaxGrade.clearFocus();
            return true;
        }
        return false;
    }

    @OnEditorAction(R.id.course_trunc_decimals)
    protected boolean onEditorTruncDecimals(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            KeyboardUtils.hideKeyboard(getActivity(), mCourseTruncDecimals);
            mCourseTruncDecimals.clearFocus();
            return true;
        }
        return false;
    }

    @OnTextChanged(value = {R.id.course_code, R.id.course_name, R.id.course_min_grade,
                            R.id.course_max_grade, R.id.course_trunc_decimals},
                   callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onTextChanged(CharSequence text) {
        mPresenter.validateFields();
    }

    /** Fragment **/

    private AddCoursePresenter mPresenter;

    public static AddCourseFragment newInstance() {
        return new AddCourseFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_add_course, container, false);
        ButterKnife.bind(this, rootView);
        setPresenter(new AddCoursePresenter(this));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        setupScrollView();
        enableButton(false);
        enableRadio();
    }

    private void setupScrollView() {
        mFormScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        mFormScrollView.setFocusable(true);
        mFormScrollView.setFocusableInTouchMode(true);
        mFormScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                KeyboardUtils.hideKeyboard(getActivity(), v);
                return false;
            }
        });
    }

    private void enableRadio() {
        if (mRadioRound != null) {
            mRadioRound.setChecked(true);
            mRadioRoundUp.setChecked(true);
        }
    }

    @Override
    public void enableButton(boolean enable) {
        if (mAddCourseButton != null) {
            mAddCourseButton.setEnabled(enable);
        }
    }

    @Override
    public void finishActivity() {
        Course newCourse = new Course(getCode().trim(), getName().trim(), Integer.valueOf(getMinGrade().trim()),
                                        Integer.valueOf(getMaxGrade().trim()), getCalcMethod());
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MyCoursesLang.EXTRA_OBJECT_COURSE, newCourse);
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        getActivity().finish();
    }

    private CalcMethod getCalcMethod() {
        if (mCourseTruncDecimals.getVisibility() == View.VISIBLE) {
            return new CalcMethod(Integer.valueOf(mCourseTruncDecimals.getText().toString()));
        } else if (mRadioRoundUp.isChecked()) {
            return new CalcMethod(true);
        } else {
            return new CalcMethod(false);
        }
    }

    @Override
    public void showErrorDialog(String title, String msg) {
        DialogUtils.showDialog(getActivity(), title, msg);
    }

    @Override
    public boolean getTruncTextViewVisibility() {
        return (mCourseTruncDecimals.getVisibility() == View.VISIBLE);
    }

    @Override
    public String getCode() {
        return (mCourseCode != null) ? mCourseCode.getText().toString().trim() : "";
    }

    @Override
    public String getName() {
        return (mCourseName != null) ? mCourseName.getText().toString().trim() : "";
    }

    @Override
    public String getMinGrade() {
        return (mCourseMinGrade != null) ? mCourseMinGrade.getText().toString().trim() : "";
    }

    @Override
    public String getMaxGrade() {
        return (mCourseMaxGrade != null) ? mCourseMaxGrade.getText().toString().trim() : "";
    }

    @Override
    public String getTruncDecimals() {
        return (mCourseTruncDecimals != null) ? mCourseTruncDecimals.getText().toString().trim() : "";
    }

    @Override
    public FragmentActivity getContext() {
        return getActivity();
    }

    public void setPresenter(AddCoursePresenter presenter) {
        this.mPresenter = presenter;
    }

}

package com.chukslabs.trical.ui.courses;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chukslabs.trical.R;
import com.chukslabs.trical.adapters.CoursesListAdapter;
import com.chukslabs.trical.data.MyCoursesLang;
import com.chukslabs.trical.model.Course;
import com.chukslabs.trical.ui.courses.add.AddCourseActivity;
import com.chukslabs.trical.ui.courses.detail.CourseDetailActivity;
import com.chukslabs.trical.util.DialogUtils;
import com.chukslabs.trical.util.SnackbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by echuquilin on 7/07/17.
 */
public class CoursesListFragment extends Fragment implements CoursesListView {

    /** Views managing with Butterknife **/

    @BindView(R.id.addCourseFloatingButton) FloatingActionButton mAddCourseButton;
    @BindView(R.id.coursesListRecycler) RecyclerView mCoursesListRecycler;
    @BindView(R.id.noDataTextView) TextView mNoDataTextView;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout mCoordinatorLayout;

    @OnClick(R.id.addCourseFloatingButton)
    public void addCourseClicked() {
        mPresenter.onAddCourseButtonClicked();
    }

    /** Fragment **/

    public static final int ADD_COURSE_CODE = 1001;
    public static final int BACKGROUND_DURATION = 500;
    public static final int REVEAL_DURATION = 400;

    private CoursesListPresenter mPresenter;
    private CoursesListAdapter mCoursesAdapter;
    private CoursesListActivity mActivity;
    private Intent nextActivity;
    private long mLastClickTime = 0;

    private AnimatorListener mAnimatorListener = new AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {}

        @Override
        public void onAnimationEnd(Animator animator) {
            animateFullSizeBackground(nextActivity);
        }

        @Override
        public void onAnimationCancel(Animator animator) {}

        @Override
        public void onAnimationRepeat(Animator animator) {}
    };

    public static CoursesListFragment newInstance() {
        return new CoursesListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = context instanceof CoursesListActivity ? (CoursesListActivity) context : null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_courses, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setPresenter(new CoursesListPresenter(this));
    }

    @Override
    public void updateCoursesList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mCoursesAdapter = new CoursesListAdapter(this, mPresenter.getCourses());
        mCoursesListRecycler.setLayoutManager(layoutManager);
        mCoursesListRecycler.setAdapter(mCoursesAdapter);
    }

    @Override
    public void startAddCourse() {
        nextActivity = AddCourseActivity.makeIntent(getActivity());
        startActivityForResult(nextActivity, ADD_COURSE_CODE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateFullSizeBackground(final Intent intent) {
        //mActivity.animateFullSizeBackground(BACKGROUND_DURATION);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent, ADD_COURSE_CODE, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                getActivity().overridePendingTransition(0, 0);
            }
        }, BACKGROUND_DURATION);
    }

    @Override
    public void showRemoveDialog(final Course course) {
        DialogUtils.showDialog(getActivity(), MyCoursesLang.DIALOG_REMOVE_TITLE,
                String.format(MyCoursesLang.DIALOG_REMOVE_MESSAGE, course.getName()),
                MyCoursesLang.DIALOG_REMOVE_POSITIVE,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.removeCourse(course);
                    }
                },
                MyCoursesLang.DIALOG_CANCEL, null);
    }

    @Override
    public void showSnackCourseAdded(String courseName, String message) {
        SnackbarUtils.showSnackBar(mCoordinatorLayout, String.format(message, courseName)).show();
    }

    @Override
    public void showSnackCourseRemoved(final Course course) {
        SnackbarUtils.showSnackBar(mCoordinatorLayout,
                String.format(MyCoursesLang.SNACKBAR_COURSE_REMOVED_MSG, course.getName()),
                MyCoursesLang.SNACKBAR_UNDO,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                            return;
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();
                        mPresenter.addCourse(course, false);
                    }
                }).show();
    }

    @Override
    public void showNoDataView() {
        mCoursesListRecycler.setVisibility(View.GONE);
        mNoDataTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoDataView() {
        mCoursesListRecycler.setVisibility(View.VISIBLE);
        mNoDataTextView.setVisibility(View.GONE);
    }

    @Override
    public void goToDetailScreen(Course course) {
        Intent intent = CourseDetailActivity.makeIntent(getActivity());
        intent.putExtra(MyCoursesLang.EXTRA_OBJECT_COURSE, course);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public FragmentActivity getContext() {
        return getActivity();
    }

    public void setPresenter(CoursesListPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_COURSE_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Course newCourse = (Course) data.getSerializableExtra(MyCoursesLang.EXTRA_OBJECT_COURSE);
            mPresenter.addCourse(newCourse, true);
        }
    }

}

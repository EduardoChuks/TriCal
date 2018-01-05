package com.chukslabs.trical.ui.common;

/**
 * Created by echuquilin on 9/06/17.
 */
public class BasePresenter<V extends BaseView> {

    protected V mView;

    public BasePresenter(V view) {
        this.mView = view;
    }

}

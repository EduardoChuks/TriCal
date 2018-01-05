package com.chukslabs.trical.util;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.chukslabs.trical.data.MyCoursesLang;

/**
 * Created by echuquilin on 5/07/17.
 */
public class SnackbarUtils extends AppCompatActivity {

    public static Snackbar showSnackBar(View coordinator, String message) {
        return showSnackBar(coordinator, message, null, null);
    }

    public static Snackbar showSnackBar(View coordinator, String message, String onClickOption) {
        return showSnackBar(coordinator, message, onClickOption, null);
    }

    public static Snackbar showSnackBar(View coordinator, String message, String onClickOption, View.OnClickListener onClick) {
        final Snackbar snackbar = Snackbar.make(coordinator, message, Snackbar.LENGTH_LONG);
        if (onClickOption == null) {
            onClickOption = MyCoursesLang.SNACKBAR_OK;
        }
        if (onClick == null) {
            onClick = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            };
        }
        snackbar.setAction(onClickOption, onClick);

        // Customizing Snackbar

        // Changing Action Button text color
        snackbar.setActionTextColor(Color.CYAN);

        // Changing Message text color
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        return snackbar;
    }

}

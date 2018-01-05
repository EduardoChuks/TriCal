package com.chukslabs.trical.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.chukslabs.trical.data.MyCoursesLang;

/**
 * Created by echuquilin on 28/06/17.
 */
public class DialogUtils {

    public static void showDialog(Context context, String title, String message) {
        showDialog(context, title, message, null, null, null, null);
    }

    public static void showDialog(Context context, String title, String message, String positiveText, DialogInterface.OnClickListener positiveListener,
                              String negativeText, DialogInterface.OnClickListener negativeListener) {
        showDialog(context, title, message, positiveText, positiveListener, negativeText, negativeListener, true);
    }

    public static void showDialog(Context context, String title, String message, String positiveText, DialogInterface.OnClickListener positiveListener,
                              String negativeText, DialogInterface.OnClickListener negativeListener, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(message);

        if (positiveListener == null) {
            positiveListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            };
        }
        if (positiveText == null) {
            positiveText = MyCoursesLang.DIALOG_OK;
        }
        builder.setPositiveButton(positiveText, positiveListener);

        if (negativeText != null) {
            if (negativeListener == null) {
                negativeListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                };
            }
            builder.setNegativeButton(negativeText, negativeListener);
        }

        builder.setCancelable(cancelable);
        builder.show();
    }

}

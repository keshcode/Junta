package com.skeleton.util;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Developer: Saurabh Verma
 * Dated: 03-03-2017.
 */

public final class EditTextUtil {


    /**
     * Filter on editText to block space character
     */
    private static InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(final CharSequence source,
                                   final int start,
                                   final int end,
                                   final Spanned dest,
                                   final int dstart,
                                   final int dend) {
            String blockCharacterSet = " ";
            if (source != null && blockCharacterSet.contains("" + source)) {
                return "";
            }
            return null;
        }
    };

    /**
     * Empty Constructor
     * not called
     */
    private EditTextUtil() {
    }

    /**
     * @param editText instance of that edittext on which no space functionality want
     */
    public static void blockSpace(final EditText editText) {
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * Adds automation to the editText moving to next or previous
     *
     * @param mSize    size after which you want to move to next editText
     * @param editText reference to all the edit to move next or previous location
     */

    public static void moveFrontAndBackAutomatic(final int mSize, final EditText... editText) {
        for (int pos = 0; pos < (editText.length - 1); pos++) {
            moveToNextEtAutomatic(editText[pos], editText[pos + 1], mSize);
        }
        for (int pos = 1; pos < editText.length; pos++) {
            moveToPreviousEtAutomatic(editText[pos], editText[pos - 1]);
        }
    }

    /**
     * adds Functionality to move automaticaly to next field or edit text
     *
     * @param mCurrentEt current reference to EditText
     * @param mNextEt    refernce to next editText shifted to
     * @param mSize      size after which to move to the next position
     */
    public static void moveToNextEtAutomatic(final EditText mCurrentEt, final EditText mNextEt, final int mSize) {
        mCurrentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                if (mCurrentEt.getText().toString().length() == mSize) {
                    mNextEt.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });
    }


    /**
     * adds Functionality to move automaticaly to previous field or edit text
     *
     * @param mCurrentEt current reference to EditText
     * @param mPrevEt    refernce to next edittext shifted to
     */
    public static void moveToPreviousEtAutomatic(final EditText mCurrentEt, final EditText mPrevEt) {
        mCurrentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                if (mCurrentEt.getText().toString().length() == 0) {
                    mPrevEt.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });
    }
}

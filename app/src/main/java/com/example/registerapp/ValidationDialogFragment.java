package com.example.registerapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.Random;

/**
 * The dialog fragment - validation dialog.
 * Displayed from the first fragment.
 * After dismissed, the second fragment is displayed.
 */

public class ValidationDialogFragment extends DialogFragment {

    /* Tag for the dialog show, to identify the dialog */
    public static final String TAG = "Validation Dialog";

    /* RANDOM variable to generate numbers between LOW and HIGH */
    private static final Random RANDOM = new Random();
    private static final int LOW = 10;
    private static final int HIGH = 100;

    private TextInputLayout mInputValidateCodeLayout;
    private String mRandomCodeString;

    static ValidationDialogFragment newInstance() {
        return new ValidationDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_validation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRandomCodeString = generateRandomCode();
        TextView mValidateCodeTextView = (TextView) view.findViewById(R.id.tv_validate_code);
        mValidateCodeTextView.setText(mRandomCodeString);

        mInputValidateCodeLayout = (TextInputLayout) view.findViewById(R.id.til_validate_code);

        Button mValidateButton = (Button) view.findViewById(R.id.b_continue_validate);
        mValidateButton.setOnClickListener(v -> validateCode());
    }

    /**
     * Generate a random code with 2 digits.
     *
     * @Return The code as a string.
     */
    private String generateRandomCode() {
        int randomCode = RANDOM.nextInt(HIGH - LOW) + LOW;

        return String.valueOf(randomCode);
    }

    /**
     * Validate the code input, check if it's the same as the displaying code.
     * If it is, dismiss the dialog.
     * If it is not, set error message to the user.
     */
    private void validateCode() {
        String inputCode = Objects.requireNonNull(mInputValidateCodeLayout.getEditText()).getText().toString();

        if (inputCode.equals(mRandomCodeString)) {
            Objects.requireNonNull(getDialog()).dismiss();
        } else {
            mInputValidateCodeLayout.setError(getString(R.string.error_validate_code));
        }
    }

    /**
     * When dialog is dismissed, check if the parent fragment is onDismisslistener.
     * If it is, invoke the onDismiss method.
     *
     * @param dialog The dialog which is dismissed.
     */
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) parentFragment).onDismiss(dialog);
        }
    }

    /**
     * When the dialog on display, set the size of the layout to show.
     */
    public void onResume() {
        super.onResume();

        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if (window == null) {
            return;
        }

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }
}

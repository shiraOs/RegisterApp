package com.example.registerapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

/**
 * The first fragment to be displayed - the register form.
 * After the input validation, display the dialog fragment.
 * After the dialog is dismissed, the fragment is replaced by the second fragment - the confirmation.
 */

public class RegisterFragment extends Fragment
        implements DialogInterface.OnDismissListener {

    /* Error message if getting null pointers for edit text element */
    public static final String EDIT_TEXT_NULL = "Edit Text Is Null";

    private TextInputLayout mNameInputLayout;
    private TextInputLayout mMailInputLayout;
    private TextInputLayout mPasswordInputLayout;
    private TextInputLayout mPasswordConfirmInputLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNameInputLayout = (TextInputLayout) view.findViewById(R.id.til_user_name);
        mMailInputLayout = (TextInputLayout) view.findViewById(R.id.til_email_address);
        mPasswordInputLayout = (TextInputLayout) view.findViewById(R.id.til_password);
        mPasswordConfirmInputLayout = (TextInputLayout) view.findViewById(R.id.til_password_confirm);

        Button mButtonContinue = (Button) view.findViewById(R.id.b_continue_register);
        mButtonContinue.setOnClickListener(v -> validateInput());
    }

    /**
     * Validate the input, check if all the validation checks are good.
     * If they are, it shows the dialog.
     */
    private void validateInput() {
        boolean nameValid = validateUserName();
        boolean mailValid = validateMail();
        boolean passwordValid = validatePassword();

        if (nameValid & mailValid & passwordValid) {
            showDialog();
        }
    }

    /**
     * Validate the user name input, check if it has at least one character.
     * Set error message in case it is empty.
     *
     * @return True if it has or False if it is empty.
     */
    private boolean validateUserName() {
        String errorMassage = null;
        boolean valid = true;

        try {
            String name = Objects.requireNonNull(mNameInputLayout.getEditText(), EDIT_TEXT_NULL).getText().toString();

            if (name.isEmpty()) {
                errorMassage = getString(R.string.error_empty_user_name);
                valid = false;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            errorMassage = getString(R.string.error_system);
            valid = false;
        }

        mNameInputLayout.setError(errorMassage);
        return valid;
    }

    /**
     * Validate the mail input according to android Patterns validation library.
     * Set error message in case it is invalid.
     *
     * @return True if it is valid or False if it is not.
     */
    private boolean validateMail() {
        String errorMassage = null;
        boolean valid = true;

        try {
            String email = Objects.requireNonNull(mMailInputLayout.getEditText(), EDIT_TEXT_NULL).getText().toString();

            if (email.isEmpty()) {
                errorMassage = getString(R.string.error_empty_mail);
                valid = false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                errorMassage = getString(R.string.error_validate_mail);
                valid = false;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            errorMassage = getString(R.string.error_system);
            valid = false;
        }

        mMailInputLayout.setError(errorMassage);
        return valid;
    }

    /**
     * Validate the password input, check if is has at least one character.
     * Check if the password confirm input is the same.
     * Set error message in case it is empty or it is not match.
     *
     * @return True if it is valid or False if it is not.
     */
    private boolean validatePassword() {
        String errorMassagePassword = null;
        String errorMassagePasswordConfirm = null;
        boolean valid = true;

        try {
            String password = Objects.requireNonNull(mPasswordInputLayout.getEditText(), EDIT_TEXT_NULL).getText().toString();
            String passwordConfirm = Objects.requireNonNull(mPasswordConfirmInputLayout.getEditText(), EDIT_TEXT_NULL).getText().toString();

            if (password.isEmpty()) {
                errorMassagePassword = getString(R.string.error_empty_password);
                valid = false;
            }

            if (!password.equals(passwordConfirm)) {
                errorMassagePasswordConfirm = getString(R.string.error_validate_password);
                valid = false;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            errorMassagePassword = getString(R.string.error_system);
            errorMassagePasswordConfirm = getString(R.string.error_system);
            valid = false;
        }

        mPasswordInputLayout.setError(errorMassagePassword);
        mPasswordConfirmInputLayout.setError(errorMassagePasswordConfirm);
        return valid;
    }

    /**
     * Create the dialog fragment and show it as a dialog.
     */
    private void showDialog() {
        DialogFragment dialogFragment = ValidationDialogFragment.newInstance();
        dialogFragment.setCancelable(false);
        dialogFragment.show(getChildFragmentManager(), ValidationDialogFragment.TAG);
    }

    /**
     * Implementation of the dialog interface onDismiss.
     * Replace the current fragment in the layout with a new fragment when the dialog is dismissed.
     *
     * @param dialog The dialog which is dismissed.
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        String userName = Objects.requireNonNull(mNameInputLayout.getEditText()).getText().toString();
        Bundle args = new Bundle();
        args.putString(getString(R.string.user_name_data_key), userName);

        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, ConfirmFragment.class, args)
                .commit();
    }
}
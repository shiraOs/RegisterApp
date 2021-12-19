package com.example.registerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * The second fragment to be displayed - the confirmation.
 */

public class ConfirmFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView mConfirmMessageTextView = (TextView) view.findViewById(R.id.tv_confirm_register);
        mConfirmMessageTextView.setText(getConfirmMessage());
    }

    /**
     * Build the confirmation message with the correct user name.
     *
     * @return The message as a string.
     */
    private String getConfirmMessage() {
        String mUserName = requireArguments().getString(getString(R.string.user_name_data_key));

        return String.format(getString(R.string.title_template_confirmation), mUserName, System.lineSeparator());
    }
}
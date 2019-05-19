package com.main.s_app.com.main.s_app.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.main.s_app.Login;
import com.main.s_app.R;

public class LogoutDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.logout_question)
            .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getActivity(), Login.class));
                }
            })
            .setNegativeButton(R.string.discard_cancel, null);

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Set Color of both buttons
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
    }
}

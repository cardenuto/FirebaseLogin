//TODO: (Required) Add your package name
package info.anth.firebaselogin.login;

//TODO: (Required) Add your package name to the resource import
import info.anth.firebaselogin.R;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Primary on 4/20/2016.
 *
 */
public class LoginRegisterDialog extends DialogFragment {

    private static View mView;
    private static AlertDialog mDialog;
    private Firebase mRef;
    private Context context;

    private String email;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        mView = inflater.inflate(R.layout.dialog_login_register, null);
        mRef = new Firebase(getResources().getString(R.string.FIREBASE_BASE_REF));
        context = getActivity().getApplicationContext();

        builder.setView(mView)
                // Add action buttons
                .setPositiveButton(R.string.login_login, null)
                .setNegativeButton(R.string.login_verify_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginRegisterDialog.this.getDialog().cancel();
                        ((LoginActivity)getActivity()).clickLogin();
                    }
                });

        this.setRetainInstance(true);

        mDialog = builder.create();
        return mDialog;
    }

    private void verifyInput() {
        Boolean hasErrors = false;

        EditText emailView = (EditText) mView.findViewById(R.id.register_email);
        email = emailView.getText().toString();

        EditText passwordView = (EditText) mView.findViewById(R.id.register_password);
        String password = passwordView.getText().toString();

        EditText passwordRetypeView = (EditText) mView.findViewById(R.id.register_retype_password);
        String passwordRetype = passwordRetypeView.getText().toString();

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            hasErrors = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            hasErrors = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            passwordView.setError(getString(R.string.error_field_required));
            hasErrors = true;
        } else if (!isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            hasErrors = true;
        } else {
            // Check for a valid retyped password, if the user entered one.
            if (TextUtils.isEmpty(passwordRetype)) {
                passwordRetypeView.setError(getString(R.string.error_field_required));
                hasErrors = true;
            } else if (!password.equals(passwordRetype)) {
                passwordView.setError(getString(R.string.error_password_retype));
                passwordRetypeView.setError(getString(R.string.error_password_retype));
                hasErrors = true;
            }
        }

        //TODO: (Optional) Add additional logic for any new field verification

        if (!hasErrors) {
            createUser(email, password);
        }
    }

    private void createUser(final String email, final String password) {
        mRef.createUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                // user was created
                // Login the user
                mRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // add data to UserInfo
                        String auid = addUserInfoPassword();
                        mDialog.dismiss();
                        // Populate local data once the user is logged in and account is created
                        // The login process needs to be completed in populateDataLocally
                        ((LoginActivity)getActivity()).populateDataLocally(mRef, authData.getUid(), auid, getActivity());
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // there was a login error
                        CharSequence text = firebaseError.getMessage();
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                });
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                // there was a creation error
                CharSequence text = firebaseError.getMessage();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });
    }

    private boolean isEmailValid(String email) {
        //TODO: (Optional) Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: (Optional) Replace this with your own logic
        return password.length() > 2;
    }

    @Override
    public void onStart(){
        super.onStart();
        mDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verify the input, create user and login
                verifyInput();
            }
        });
    }

    public String addUserInfoPassword() {
        //TODO: (Optional) Update for any new fields added to DbUserInfo class
        // set user id
        String uid = mRef.getAuth().getUid();

        // create record
        String profileImageUrl = "Not provided by authData";
        String provider = mRef.getAuth().getProvider();

        EditText nameView = (EditText) mView.findViewById(R.id.register_name);
        String displayName = nameView.getText().toString();

        if(mRef.getAuth().getProviderData().containsKey("profileImageURL")) profileImageUrl = mRef.getAuth().getProviderData().get("profileImageURL").toString();

        // define users
        DbUserInfo newUserInfo = new DbUserInfo(provider, email, profileImageUrl, displayName);
        Firebase pushUser = mRef.child("userInfo/users").push();
        pushUser.setValue(newUserInfo);

        // define userMap
        LoginActivity.populateUserMap(mRef, uid, pushUser.getKey());

        return pushUser.getKey();
    }
}

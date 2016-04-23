# FirebaseLogin
This repository provides reusable code that can be included in your Android project to add Firebase login functionality. It utilizes the FirebaseUI library (version 0.3.1), encapsulating it into its own activity, to be started when needed. It expands the login functionality by overriding the delivered login layout (fragment_firebase_login.xml) adding multi-language support by implementing resource strings and the ability to create/register a new user.  

It is delivered in the form of examples with setup instructions included. 

Why not delivered as a library? For my own needs I didn’t want these classes and layouts “fixed.” Some of my apps I may want to add different branding, some additional information needs to be collected from the user. Adding this to a project, though not as quick as adding a dependency, is still relatively quick. I am new to Android so if there is a better process please share. 

##Content
[Branches](https://github.com/cardenuto/FirebaseLogin#branches)
<br>[Setup](https://github.com/cardenuto/FirebaseLogin#setup)
<br>[Using the code](https://github.com/cardenuto/FirebaseLogin#using-the-code)
<br>[Structure/UI](https://github.com/cardenuto/FirebaseLogin#structureui)
<br>[Dependencies](https://github.com/cardenuto/FirebaseLogin#dependencies)

##Branches
[Example-Basic](https://github.com/cardenuto/FirebaseLogin/tree/Example-Basic) - base example of the code with a small surrounding app. Contains login (startActivity)  and logout buttons with logging to see success.
<br>[Example-NewActivity](https://github.com/cardenuto/FirebaseLogin/tree/Example-NewActivity) - base example plus an auth listener to show login (auth) data, additional activity that requires valid login (startActivityForResult) and use of an application class
<br>[master](https://github.com/cardenuto/FirebaseLogin) - most recent example currently Example-NewActivity
<br>[dev](https://github.com/cardenuto/FirebaseLogin/tree/dev) - working copy Take a look at [DEV.md](https://github.com/cardenuto/FirebaseLogin/blob/dev/DEV.md) for current and future potential development

[Top](https://github.com/cardenuto/FirebaseLogin#content)

##Setup
Setup instructions include a list of pre-steps and the steps I take to add this Firebase Login code to my projects. https://github.com/cardenuto/FirebaseLogin/blob/master/SETUP.md

For FirebaseUI setup including instructions on Facebook and Twitter Auth see: 
<br>The version I am using: https://github.com/firebase/FirebaseUI-Android/tree/0.3.1
<br>The latest repository: https://github.com/firebase/FirebaseUI-Android

[Top](https://github.com/cardenuto/FirebaseLogin#content)

##Using the code
Once the setup is complete, using the code is starting the LoginActivity. The LoginActivity is designed to be started with either startActivity or startActivityForResult. 

<b>startActivity</b> is used when the program doesn’t need to take any action should the user cancel out of the login process. In the example in the master branch, this is done for the login button. Should the user not login, no special action is taken.  

Implementation – MainActivity.java: 

    public void callLogin (View view) {
        // Logout is logged in
        if (mRef.getAuth() != null) callLogout(view);
        // call the login intent
         startActivity(new Intent(this, LoginActivity.class));
    }


With <b>startActivityForResult</b>, the program can close the current activity should the user cancel out of the login process. In the example in the master branch, this is done for the “open activity requiring login” button.  The user is not allowed to interact with the new activity (NewActivity) if they are not logged in. If the user cancels out of the login process, the new activity also closes returning them to the main activity. 

Implementation – NewActivity.java: 

     @Override
     public void onResume() {
         super.onResume();
         if (mRef.getAuth()==null) startActivityForResult(new Intent(this, LoginActivity.class)
             , LoginActivity.RESULT_REQUEST_CODE);
     }
     
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     
         if (requestCode == LoginActivity.RESULT_REQUEST_CODE) {
             /*
             if(resultCode == Activity.RESULT_OK){
                 String result=data.getStringExtra("result");
             }
             */
             if (resultCode == Activity.RESULT_CANCELED) {
                 // Login was cancelled therefore cancel this activity
                 finish();
             }
         }
     }

[Top](https://github.com/cardenuto/FirebaseLogin#content)

##Structure/UI
This solution adds 5 new files: a login activity (LoginActivity.java), its layout file (activity_login.xml), a dialog to register new users (LoginRegisterDialog.java), its layout file (dialog_login_register.xml), and the overridden dialog used by the FirebaseUI library as the login UI (fragment_firebase_login.xml). Strings are stored in the strings.xml resource file. LoginActivity is a dialog style stored in the style.xml resource file.

When the login intent is started the adjusted login UI is displayed. If the user successfully logs in, the activity will close and the user will return to the activity it was called from. 

<img src="https://github.com/cardenuto/FirebaseLogin/blob/master/device-2016-04-22-211208.png" alt="Step Screen" width="40%">

Should the user dismiss the login screen without logging in, the login activity’s UI will display. 

<img src="https://github.com/cardenuto/FirebaseLogin/blob/master/device-2016-04-22-211205.png" alt="Step Screen" width="40%">

The cancel button closes the activity, the login button return to the adjusted login UI where the user can create a new account should they wish.

<img src="https://github.com/cardenuto/FirebaseLogin/blob/master/device-2016-04-22-211202.png" alt="Step Screen" width="40%">
<img src="https://github.com/cardenuto/FirebaseLogin/blob/master/device-2016-04-22-211151.png" alt="Step Screen" width="40%">

Error checking is performed on the form before the user is able to login. The login button closes the activity returning the user to the prior activity. The cancel button brings the user back to the adjusted login UI. 

[Top](https://github.com/cardenuto/FirebaseLogin#content)

##Dependencies

Android Support
<br>com.android.support:appcompat-v7:23.2.0

Firebase
<br>com.firebase:firebase-client-android:2.5.1
<br>com.firebaseui:firebase-ui:0.3.1

Google Authentication
<br>com.google.android.gms:play-services-auth:8.4.0

[Top](https://github.com/cardenuto/FirebaseLogin#content)

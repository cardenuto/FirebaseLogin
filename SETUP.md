#Setup Firebase LoginActivity for Pasword and Google Auth
Here are the steps to implement this code into your project. You will need to copy (or recreate) two java files stored in a package, and three layout files. Add data to your AndroidManifest.xml, strings.xml, styles.xml, and build.gradle (app) files. Required and Optional TODOs are placed throughout the code to complete the setup and add your own customizations. The final step, setting the Firebase context, you will have a choice to add it to your main activity or create an application class.

The steps are listed as if you were starting an empty project, they will be similar if they are being added to an existing project. 
However, you may need to adjust this code based on existing library versions, or classes/strings/styles you created with the same names.

<hr>

##External Setup
There are a number of steps that need to be taken external of Android Studio in order to use Firebase Login for Password and Google authentication. [I've listed these step at the bottom of this document.](https://github.com/cardenuto/FirebaseLogin/blob/master/SETUP.md#external-setup-details)

<hr>

##Step 1
Create a new Empty Activity project (or any type of project you are preparing) – minimum SDK version for Firebase is 16. 

##Step 2
Update ***build.gradle*** with the required firebase dependencies (add to dependencies section)

    dependencies {
        ... 
        compile 'com.firebase:firebase-client-android:2.5.1'
        compile 'com.firebaseui:firebase-ui:0.3.1'
        ... 
    }


##Step 3
(Optional if you are using Google Login) Update ***build.gradle*** with the required Google Auth dependencies (add to dependencies section)

    dependencies {
        ... 
        compile 'com.google.android.gms:play-services-auth:8.4.0'
        ... 
    }

##Step 4
Update ***build.gradle*** with package options for duplicates (add to android section)

    android {
    ... 
        /* added packaging options to fix error: Duplicate files copied in APK META-INF/NOTICE */
        packagingOptions {
            exclude 'META-INF/LICENSE'
            exclude 'META-INF/LICENSE-FIREBASE.txt'
            exclude 'META-INF/NOTICE'
        }
    ... 
    }

##Step 5
Copy in (or create) [login package](https://github.com/cardenuto/FirebaseLogin/tree/master/app/src/main/java/info/anth/firebaselogin/login) 

<ol>Files:
<li>LoginActivity.java</li>
<li>LoginRegisterDialog.java</li>
</ol>

##Step 6
Copy in (or create) needed [xml layout files](https://github.com/cardenuto/FirebaseLogin/tree/master/app/src/main/res/layout) (into the standard layout location)

<ol>Files:
<li><a href="https://github.com/cardenuto/FirebaseLogin/blob/master/app/src/main/res/layout/activity_login.xml">activity_login.xml</a></li>
<li><a href="https://github.com/cardenuto/FirebaseLogin/blob/master/app/src/main/res/layout/dialog_login_register.xml">dialog_login_register.xml</a></li>
<li><a href="https://github.com/cardenuto/FirebaseLogin/blob/master/app/src/main/res/layout/fragment_firebase_login.xml">fragment_firebase_login.xml</a></li>
</ol>

##Step 7
Update the ***strings.xml*** resource file for Firebase Login strings

    <!-- TODO: (Required) Add your Firebase database reference replace YOUR_DATABASE_NAME with the name of your database -->
    <string name="FIREBASE_BASE_REF">https://YOUR_DATABASE_NAME.firebaseio.com</string>

    <!-- **** Start Firebase login text **** -->

    <!-- fragment_firebase_login.xml -->
    <string name="login_email">Email</string>
    <string name="login_password">Password</string>
    <string name="login_login">Login</string>
    <string name="login_or">or</string>
    <string name="login_facebook">Login with Facebook</string>
    <string name="login_google">Login with Google</string>
    <string name="login_twitter">Login with Twitter</string>
    <string name="login_create">Create Account</string>

    <!-- activity_login.xml -->
    <string name="login_verify_text">This activity requires you to be logged in.</string>
    <string name="login_verify_cancel">Cancel</string>
    <string name="login_verify_login">Login</string>

    <!-- dialog_login_register.xml -->
    <string name="login_retype_password">Retype Password</string>
    <string name="register_name">Name (optional)</string>
    <string name="error_invalid_email">This email address is invalid</string>
    <string name="error_invalid_password">Password must be at least 3 characters long</string>
    <string name="error_incorrect_password">This password is incorrect</string>
    <string name="error_field_required">This field is required</string>
    <string name="error_password_retype">Password and password retype are not the same</string>

    <!-- **** End Firebase login text **** -->

##Step 8
Update the ***styles.xml*** resource file. This style makes the login activity look like a dialog. You may need to adjust for your application’s style.

    <!-- TODO: (Optional) Adjust for your theme -->
    <!-- Dialog Theme. -->
    <style name="DialogTheme" parent="Theme.AppCompat.Light.Dialog">
        <!-- Remove title from window. -->
        <item name="windowNoTitle">true</item>
    </style>

##Step 9
Update the ***AndroidManifest.xml*** file for internet permissions

    <!-- database access needs internet services -->
    <uses-permission android:name="android.permission.INTERNET" />

##Step 10
Update the ***AndroidManifest.xml*** file for the new login activity (Application Section)
    
    <activity
        android:name=".login.LoginActivity"
        android:theme="@style/DialogTheme" />


##Step 11
Update required **TODO** items. We have marked many areas of the code for both required changes, such as updating your Firebase database name and the activity for your package, as well as optional items like password verification rules or setting authentication providers. 

##Step 12
Set Firebase Context, required to use Firebase. This is a Firebase setup design question that impacts this login process.

    Firebase.setAndroidContext(getApplicationContext());

####Option A : Add code to an activity
The simplest solution is to add the code to your main activity. I did this in the [Example-Basic branch](https://github.com/cardenuto/FirebaseLogin/tree/Example-Basic) of this project. Though it may be simple it may not be the best. If you should enter your application from another activity and then try to access Firebase you will get an error. I am unsure if the main activity were to be destroyed if this setting will also be lost. 

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // need to set application context
        Firebase.setAndroidContext(getApplicationContext());
        // setup the Firebase database reference
        mRef = new Firebase(getResources().getString(R.string.FIREBASE_BASE_REF));
    }
```

####Option B : Create an Application Class
Though slightly more complicated, my preferred solution is to create an application class that extends standard application class. As my programs become more complex, I have had to add other libraries’ initialization code to the application level as well. Set it once and don’t worry about application entry points or activity lifecycles. This is how I have setup the master branch of this project. 

There are three steps to setting this up:

#####Option B : Step 1
Create a new Java class, I like to name the file <application name>Application so my file is called [FirebaseLoginApplication.java](https://github.com/cardenuto/FirebaseLogin/blob/master/app/src/main/java/info/anth/firebaselogin/MainActivity.java).
Take a look at the master version, it may contain additional code that may be useful.

#####Option B : Step 2
Update the class to extend android.app.Application, override onCreate and add our Firebase set context code.

```java
    public class FirebaseLoginApplication extends android.app.Application {
        @Override
        public void onCreate() {
            super.onCreate();

            Firebase.setAndroidContext(this);
        }
    }
```

#####Option B : Step 3
Update the ***AndroidManifest.xml*** file by setting the android:name property.

    <application
        android:name=".FirebaseLoginApplication"
        ...
         >


<hr>

##External Setup Details
I have marked the steps required for password authentication with a preceding (P) and for Google authentication (G)

**Step 1** (P)(G) Create [Firebase](https://www.firebase.com) account

**Step 2** (P) Enabled your Firebase database to Authenticate using Email and Password [Guide](https://www.firebase.com/docs/ios/guide/user-auth.html#section-enable-providers)

**Step 3** (G) Create Google developers account - [Google API Console](https://console.developers.google.com) - and setup a project. [Guide](https://developers.google.com/identity/sign-in/web/devconsole-project)

**Step 4** (G) Add **web application** OAuth credentials to the project. 
<br>Firebase uses this to authenticate even when using Android.

For "Authorized JavaScript origins" - use origin URI: `https://auth.firebase.com`
<br>For "Authorized redirect URIs" - use application path: `https://auth.firebase.com/v2/YOUR_DATABASE_NAME/auth/google/callback` - replacing YOUR_DATABASE_NAME with the name of your Firebase database.

**Step 5** (G) Add **Android Client** OAuth credentials to the project. 
<br>Google uses this to determine if your Android APP had API access. Without it your app will not access Google in order to use the Web Application credentials. It is not used in the Firebase setup.

For "Package name" use the package defined in your AndroidManifest.xml file.
<br>For "Signing-certificate fingerprint" follow this [guide](https://support.google.com/cloud/answer/6158849?hl=en#android). **DO NOT SHARE** this fingerprint with people.

**NOTE:** I am using play services auth version 8.4.0 and I found that this is required – other instructions do not mention that you need it.

**Step 6** (G) Enabled your Firebase database to Authenticate using Google [Guide](https://www.firebase.com/docs/ios/guide/user-auth.html#section-enable-providers).
<br>Using your **Web Application** credentials, enable Google authentication and enter your Google Client ID and your Google Client Secret. **DO NOT SHARE** these keys.





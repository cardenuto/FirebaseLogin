#Setup Firebase LoginActivity for Pasword and Google Auth
Here are the steps to implement this code into your project. You will need to copy (or recreate) two java files stored in a package, and three layout files. Add data to your AndroidManifest.xml, strings.xml, styles.xml, and build.gradle (app) files. Required and Optional TODOs are placed throughout the code to complete the setup and add your own customizations. The final step, setting the Firebase context, you will have a choice to add it to your main activity or create an application class.

The steps are listed as if you were starting an empty project, they will be similar if they are being added to an existing project. 
However, you may need to adjust this code based on existing library versions, or classes/strings/styles you created with the same names.

##Pre-Setup
There are a number of steps that need to be taken extermal of Android Studio in order to use Firebase Login for Password and Google authentication. 

##Step 1
Create a new Empty Activity project (or any type of project you are preparing) – minimum SDK version for Firebase is 16. 

##Step 2
Update build.gradle with the required firebase dependencies (add to dependencies section)
        dependencies {
            ... 
            compile 'com.firebase:firebase-client-android:2.5.1'
            compile 'com.firebaseui:firebase-ui:0.3.1'
            ... 
        }




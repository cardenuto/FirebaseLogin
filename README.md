# FirebaseLogin - Example-NewActivity Branch
This repository provides reusable code that can be included in your Android project to add Firebase login functionality. It utilizes the FirebaseUI library (version 0.3.1), encapsulating it into its own activity, to be started when needed. It expands the login functionality by overriding the delivered login layout (fragment_firebase_login.xml) adding multi-language support by implementing resource strings and the ability to create/register a new user.  

It is delivered in the form of examples with setup instructions included. 

Why not delivered as a library? For my own needs I didn’t want these classes and layouts “fixed.” Some of my apps I may want to add different branding, some additional information needs to be collected from the user. Adding this to a project, though not as quick as adding a dependency, is still relatively quick. I am new to Android so if there is a better process please share. 

This branch is the base example plus an auth listener to show login (auth) data, additional activity that requires valid login (startActivityForResult) and use of an application class


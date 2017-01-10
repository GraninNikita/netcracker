# About Notification system 
The system allows you to manage events and notificate users about upcoming events. 
Users can be notified in convenient way (Sms, email).
# How to build
Project uses maven as build manager, but should pay attention to the following:

1. You should download oracle driver for database connection from [Oracle jdbc driver](http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html)
2. Include this jar file to your web archive.
3. Build your project with ide.

Before deploying to application server need to configure security system:

1. Download and install keycloak, it's simply to do by using this tutorial: [How to install keycloak](https://keycloak.gitbooks.io/getting-started-tutorials/content/index.html).
2. Change you standalone.xml in your app server [Like this](https://keycloak.gitbooks.io/getting-started-tutorials/content/topics/secure-jboss-app/subsystem.html).

To create database run [this script](https://drive.google.com/file/d/0BxXRGrJX2XKWRjN2YXlRamNxVjg/view?usp=sharing).

If you have troubles with configuring your application server use this [Configuration file](https://drive.google.com/file/d/0BxXRGrJX2XKWZ0c3TmN2MWtGVms/view?usp=sharing) as example.

If you can't install our project, contact me!

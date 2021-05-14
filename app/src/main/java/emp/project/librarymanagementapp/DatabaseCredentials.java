package emp.project.librarymanagementapp;

import emp.project.librarymanagementapp.View.LoginActivityView;

public interface DatabaseCredentials {
    String DB_NAME = "jdbc:mysql://192.168.1.152:3306/librarydb";
    String USER = LoginActivityView.getUsername();
    String PASS = LoginActivityView.getPassword();
}

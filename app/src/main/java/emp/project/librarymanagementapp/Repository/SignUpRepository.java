package emp.project.librarymanagementapp.Repository;

import android.os.StrictMode;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.SQLException;

import emp.project.librarymanagementapp.DatabaseCredentials;
import emp.project.librarymanagementapp.Interfaces.ISignUp;
import emp.project.librarymanagementapp.Models.LoginModel;

public class SignUpRepository implements ISignUp.ISignUpRepository, DatabaseCredentials {
    private static SignUpRepository instance = null;

    private SignUpRepository() {

    }

    public static SignUpRepository getInstance() {
        if (instance == null) {
            instance = new SignUpRepository();
        }
        return instance;
    }
    @Override
    public void Connection() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override
    public void insertNewAccount(LoginModel model) throws ClassNotFoundException, SQLException {
        Connection();
        Connection connection = (Connection) DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlcmd = "INSERT INTO userlogin(user_username,user_password,user_status)VALUES(" + "'" + model.getUser_username() + "','" + model.getUser_password() + "','" + model.getUser_status() + "')";
        Statement statement = (Statement) connection.createStatement();
        statement.execute(sqlcmd);
        statement.close();
        connection.close();
    }
}

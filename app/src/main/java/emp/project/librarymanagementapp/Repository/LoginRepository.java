package emp.project.librarymanagementapp.Repository;

import android.os.StrictMode;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import emp.project.librarymanagementapp.DatabaseCredentials;
import emp.project.librarymanagementapp.Interfaces.ILogin;
import emp.project.librarymanagementapp.Models.LoginModel;

public class LoginRepository implements ILogin.ILoginRepository, DatabaseCredentials {
    private static LoginRepository instance = null;

    private LoginRepository() {

    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
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
    public boolean checkLoginCredentials(LoginModel model) throws ClassNotFoundException, SQLException {
        Connection();
        Connection connection = (Connection) DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlcmd = "SELECT * FROM userlogin WHERE user_username=" + "'" + model.getUser_username() + "' AND user_password=" + "'" + model.getUser_password() +
                "' AND user_status='Active'";
        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlcmd);
        if (resultSet.next()) {
            resultSet.close();
            statement.close();
            connection.close();
            return true;
        } else {
            resultSet.close();
            statement.close();
            connection.close();
            return false;
        }
    }
}

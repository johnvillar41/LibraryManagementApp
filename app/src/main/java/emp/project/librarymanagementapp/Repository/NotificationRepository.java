package emp.project.librarymanagementapp.Repository;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.librarymanagementapp.DatabaseCredentials;
import emp.project.librarymanagementapp.Interfaces.INotification;
import emp.project.librarymanagementapp.Models.NotificationModel;

public class NotificationRepository implements INotification.INotificationRepository, DatabaseCredentials {
    private static NotificationRepository instance = null;

    private NotificationRepository() {

    }

    public static NotificationRepository getInstance() {
        if (instance == null) {
            instance = new NotificationRepository();
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
    public void deleteNotification(String notif_id) throws ClassNotFoundException, SQLException {
        Connection();
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        String sqlcmd = "DELETE FROM notifications WHERE notif_id=" + "'" + notif_id + "'";
        Statement statement = connection.createStatement();
        statement.execute(sqlcmd);
        connection.close();
        statement.close();
    }

    @Override
    public List<NotificationModel> getAllNotifications() {
        List<NotificationModel> list = new ArrayList<>();
        try {
            Connection();
            String sqlcmd = "SELECT * FROM notifications";
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlcmd);
            while (resultSet.next()) {
                NotificationModel model = new NotificationModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4));
                list.add(model);
            }
            statement.close();
            resultSet.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteAllNotifications(String username) throws ClassNotFoundException, SQLException {
        Connection();
        String sqlcmd = "DELETE FROM notifications WHERE user_username=" + "'" + username + "'";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        Statement statement = connection.createStatement();
        statement.execute(sqlcmd);
        statement.close();
        connection.close();
    }
}

package emp.project.librarymanagementapp.Controller;

import android.os.StrictMode;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.librarymanagementapp.Interfaces.NotificationInterface;
import emp.project.librarymanagementapp.Models.NotificationModel;
import emp.project.librarymanagementapp.View.LoginActivityView;
import emp.project.librarymanagementapp.View.NotificationActivityView;

public class NotificationController implements NotificationInterface.NotificationControllerInterface {

    NotificationActivityView view;
    NotificationModel model;

    public NotificationController(NotificationActivityView view) {
        this.view = view;
        this.model = new NotificationModel();
    }

    @Override
    public void onRemoveButtonClicked() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                view.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.displayProgressBar();
                    }
                });
                Dbhelper dbhelper = new Dbhelper();
                try {
                    dbhelper.deleteAllNotifications(LoginActivityView.getUsername());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                view.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.hideProgressBar();
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    public void getAllNotifications() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Dbhelper dbhelper = new Dbhelper();
                try {
                    view.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.displayProgressBar();
                        }
                    });
                    view.displayNotifications(dbhelper.getAllNotifications());
                    view.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.hideProgressBar();
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class Dbhelper implements NotificationInterface.NotificationDBhelper {

        private String DB_NAME = "jdbc:mysql://192.168.1.152:3306/librarydb";
        private String USER = "admin";
        private String PASS = "admin";


        @Override
        public void Connection() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

        @Override
        public List<NotificationModel> getAllNotifications() throws ClassNotFoundException, SQLException {
            Connection();
            List<NotificationModel> list = new ArrayList<>();
            String sqlcmd = "SELECT * FROM notifications";
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlcmd);
            while (resultSet.next()) {
                model = new NotificationModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4));
                list.add(model);
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
        }
    }
}

package emp.project.librarymanagementapp.Interfaces;

import java.sql.SQLException;
import java.util.List;

import emp.project.librarymanagementapp.Models.NotificationModel;

public interface NotificationInterface {
    interface NotificationViewInterface{
        void InitViews();

        void displayProgressBar();

        void hideProgressBar();

        void refreshPage();

        void displayNotifications(List<NotificationModel>list);
    }
    interface NotificationControllerInterface{
        void onRemoveButtonClicked();

        void getAllNotifications();

        void onDeleteAllNotifClicked() throws SQLException, ClassNotFoundException;
    }
    interface NotificationDBhelper{
        void Connection() throws ClassNotFoundException;

        List<NotificationModel> getAllNotifications() throws ClassNotFoundException, SQLException;

        void deleteAllNotifications(String username) throws ClassNotFoundException, SQLException;
    }
}

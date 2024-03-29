package emp.project.librarymanagementapp.Interfaces;

import java.sql.SQLException;
import java.util.List;

import emp.project.librarymanagementapp.Models.NotificationModel;

public interface INotification {
    interface NotificationViewInterface{
        void InitViews();

        void displayProgressBar();

        void hideProgressBar();

        void refreshPage();

        void displayNotifications(List<NotificationModel>list);
    }
    interface NotificationPresenterInterface {
        void onRemoveButtonClicked();

        void getAllNotifications() throws SQLException, ClassNotFoundException;

        void onDeleteAllNotifClicked() throws SQLException, ClassNotFoundException;

        void deleteNotification(String notif_id) throws SQLException, ClassNotFoundException;
    }
    interface INotificationRepository {
        void Connection() throws ClassNotFoundException;

        void deleteNotification(String notif_id) throws ClassNotFoundException, SQLException;

        List<NotificationModel> getAllNotifications() throws ClassNotFoundException, SQLException;

        void deleteAllNotifications(String username) throws ClassNotFoundException, SQLException;
    }
}

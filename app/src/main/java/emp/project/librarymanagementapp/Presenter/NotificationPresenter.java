package emp.project.librarymanagementapp.Presenter;

import android.app.Activity;
import android.content.Context;

import java.sql.SQLException;

import emp.project.librarymanagementapp.Interfaces.INotification;
import emp.project.librarymanagementapp.Models.NotificationModel;
import emp.project.librarymanagementapp.View.LoginActivityView;

public class NotificationPresenter implements INotification.NotificationPresenterInterface {

    private final INotification.NotificationViewInterface view;
    private final INotification.INotificationRepository repository;


    public NotificationPresenter(INotification.NotificationViewInterface view, INotification.INotificationRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onRemoveButtonClicked() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            repository.deleteAllNotifications(LoginActivityView.getUsername());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        thread.start();
        view.refreshPage();
    }

    @Override
    public void getAllNotifications() {
        view.displayProgressBar();
        try {
            view.displayNotifications(repository.getAllNotifications());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        view.hideProgressBar();
    }

    @Override
    public void onDeleteAllNotifClicked()  {
        try {
            repository.deleteAllNotifications(LoginActivityView.getUsername());
            view.refreshPage();
            view.displayNotifications(repository.getAllNotifications());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void deleteNotification(String notif_id)  {
        try {
            repository.deleteNotification(notif_id);
            view.refreshPage();
            view.displayNotifications(repository.getAllNotifications());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}

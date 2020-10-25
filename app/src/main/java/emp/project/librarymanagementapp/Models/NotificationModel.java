package emp.project.librarymanagementapp.Models;

public class NotificationModel {
    String notif_id,notif_title,notif_msg,user_username;

    public NotificationModel() {
    }

    public NotificationModel(String notif_id, String notif_title, String notif_msg, String user_username) {
        this.notif_id = notif_id;
        this.notif_title = notif_title;
        this.notif_msg = notif_msg;
        this.user_username = user_username;
    }

    public String getNotif_id() {
        return notif_id;
    }

    public String getNotif_title() {
        return notif_title;
    }

    public String getNotif_msg() {
        return notif_msg;
    }

    public String getUser_username() {
        return user_username;
    }
}

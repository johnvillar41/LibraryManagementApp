package emp.project.librarymanagementapp.Models;

public class IssueBookModel {
    String user_username;
    String book_title;
    String cart_id;
    String book_image_url;
    String dateIssue;
    String dateDeadline;


    public IssueBookModel(String user_username, String book_title, String cart_id, String book_image_url, String dateIssue, String dateDeadline) {
        this.user_username = user_username;
        this.book_title = book_title;
        this.cart_id = cart_id;
        this.book_image_url = book_image_url;
        this.dateIssue = dateIssue;
        this.dateDeadline = dateDeadline;
    }

    public IssueBookModel() {
    }

    public String getDateIssue() {
        return dateIssue;
    }

    public String getDateDeadline() {
        return dateDeadline;
    }

    public String getUser_username() {
        return user_username;
    }

    public String getBook_title() {
        return book_title;
    }

    public String getCart_id() {
        return cart_id;
    }

    public String getBook_image_url() {
        return book_image_url;
    }
}

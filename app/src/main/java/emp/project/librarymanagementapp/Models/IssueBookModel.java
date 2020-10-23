package emp.project.librarymanagementapp.Models;

public class IssueBookModel {
    String user_username,book_title,cart_id,book_image_url;

    public IssueBookModel() {
    }

    public IssueBookModel(String user_username, String book_title, String cart_id, String book_image_url) {
        this.user_username = user_username;
        this.book_title = book_title;
        this.cart_id = cart_id;
        this.book_image_url = book_image_url;
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

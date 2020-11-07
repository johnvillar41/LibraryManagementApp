package emp.project.librarymanagementapp.Models;

import java.util.ArrayList;
import java.util.List;

public class BookModel {
    private static List<BookModel> list_cartBooks=new ArrayList<>();
    String book_id,book_title,book_quantity,
            book_description,book_category,book_url;

    public void addBooks(BookModel model){
        list_cartBooks.add(model);
    }

    public static List<BookModel> getList_cartBooks() {
        return list_cartBooks;
    }

    public BookModel() {
    }

    public BookModel(String book_id, String book_title,
                     String book_quantity, String book_description,
                     String book_category, String book_url) {
        this.book_url=book_url;
        this.book_id=book_id;
        this.book_title = book_title;
        this.book_quantity = book_quantity;
        this.book_description = book_description;
        this.book_category = book_category;
    }

    @Override
    public String toString() {
        return "BookModel{" +
                "book_id='" + book_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getBook_url() {
        return book_url;
    }

    public String getBook_title() {
        return book_title;
    }

    public String getBook_quantity() {
        return book_quantity;
    }

    public String getBook_description() {
        return book_description;
    }

    public String getBook_category() {
        return book_category;
    }
}

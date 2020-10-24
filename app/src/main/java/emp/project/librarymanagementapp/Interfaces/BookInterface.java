package emp.project.librarymanagementapp.Interfaces;

import java.sql.SQLException;
import java.util.List;

import emp.project.librarymanagementapp.Models.BookModel;

public interface BookInterface {
    interface BookActivityViewInterface {
        void InitViews() throws SQLException, ClassNotFoundException;

        void displayBookCategory(List<String> books);

        void displayRecyclerView(List<BookModel> list);

        void displayRecyclerViewBasedOnCategory(List<BookModel> books) throws SQLException, ClassNotFoundException;

        void displayAlertDialog();

        void onFailedMessage(String message);

        void displayRecyclerViewBasedOnSearch(List<BookModel> list);

        void refreshPage();
    }

    interface BookControllerInterface {
        void directRecyclerView() throws SQLException, ClassNotFoundException;

        void directBookCategory() throws SQLException, ClassNotFoundException;

        void directRecyclerViewBasedOnCategory(String category) throws SQLException, ClassNotFoundException;

        void directCartListToDB(List<BookModel> list_cartBooks) throws SQLException, ClassNotFoundException;

        void directAlertDialog();

        void directGetBookBasedOnSearch(String book_title) throws SQLException, ClassNotFoundException;
    }

    interface DbHelperBooks {
        void Connection() throws ClassNotFoundException;

        List<BookModel> getBook() throws ClassNotFoundException, SQLException;

        List<String> getBookCategory() throws ClassNotFoundException, SQLException;

        List<BookModel> getBookBasedOnCategory(String category) throws ClassNotFoundException, SQLException;

        void insertCartListToDB(List<BookModel> list_cartBooks) throws ClassNotFoundException, SQLException;

        List<BookModel> getBookBasedOnSearch(String book_title) throws ClassNotFoundException, SQLException;
    }
}

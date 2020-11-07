package emp.project.librarymanagementapp.Interfaces;

import java.sql.SQLException;
import java.util.List;

import emp.project.librarymanagementapp.Models.IssueBookModel;

public interface IssueHistoryInterface {
    interface IssueHistoryViewInterface{
        void InitViews() throws SQLException, ClassNotFoundException;

        void displayIssueBooks(List<IssueBookModel> books);

        void displayIfEmptyCart(boolean isEmpty);

        void displayNumberOfBooks(int total_books);
    }
    interface IssueHistoryPresenterInterface {
        void directIssueBookDisplay(String username) throws SQLException, ClassNotFoundException;

        void directReturnBook(IssueBookModel model) throws SQLException, ClassNotFoundException;

        void directCheckCart(String username) throws SQLException, ClassNotFoundException;

        void directNumberBooks() throws SQLException, ClassNotFoundException;
    }
    interface  IssueHistoryDbHelperInterface{
        void Connection() throws ClassNotFoundException;

        List<IssueBookModel> getIssueBooks(String username) throws ClassNotFoundException, SQLException;

        void returnBooks(IssueBookModel model) throws ClassNotFoundException, SQLException;

        boolean checkIfEmptyCart(String username) throws ClassNotFoundException, SQLException;

        int getNumberOfBooks(String username) throws ClassNotFoundException, SQLException;
    }
}

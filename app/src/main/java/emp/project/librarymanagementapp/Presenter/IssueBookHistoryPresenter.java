package emp.project.librarymanagementapp.Presenter;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.librarymanagementapp.Interfaces.IssueHistoryInterface;
import emp.project.librarymanagementapp.Models.IssueBookModel;
import emp.project.librarymanagementapp.View.IssueHistoryActivityView;
import emp.project.librarymanagementapp.View.LoginActivityView;

public class IssueBookHistoryPresenter implements IssueHistoryInterface.IssueHistoryPresenterInterface {
    IssueHistoryActivityView view;
    IssueBookModel model;

    public IssueBookHistoryPresenter(IssueHistoryActivityView view) {
        this.view = view;
        this.model=new IssueBookModel();
    }

    @Override
    public void directIssueBookDisplay(String username) throws SQLException, ClassNotFoundException {
        DbHelperIssueBook dbHelperIssueBook=new DbHelperIssueBook();
        view.displayIssueBooks(dbHelperIssueBook.getIssueBooks(username));
    }

    @Override
    public void directReturnBook(IssueBookModel model) throws SQLException, ClassNotFoundException {
        DbHelperIssueBook dbHelperIssueBook=new DbHelperIssueBook();
        dbHelperIssueBook.returnBooks(model);
    }

    @Override
    public void directCheckCart(String username) throws SQLException, ClassNotFoundException {
        DbHelperIssueBook dbHelperIssueBook=new DbHelperIssueBook();
        view.displayIfEmptyCart(dbHelperIssueBook.checkIfEmptyCart(username));
    }

    @Override
    public void directNumberBooks() throws SQLException, ClassNotFoundException {
        DbHelperIssueBook dbHelperIssueBook=new DbHelperIssueBook();
        view.displayNumberOfBooks(dbHelperIssueBook.getNumberOfBooks(LoginActivityView.getUsername()));
    }

    private class DbHelperIssueBook implements IssueHistoryInterface.IssueHistoryDbHelperInterface{

        private String DB_NAME = "jdbc:mysql://192.168.1.152:3306/librarydb";
        private String USER = LoginActivityView.getUsername();
        private String PASS = LoginActivityView.getPassword();

        @Override
        public void Connection() throws ClassNotFoundException {
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver");
        }

        @Override
        public List<IssueBookModel> getIssueBooks(String username) throws ClassNotFoundException, SQLException {
            List<IssueBookModel> list=new ArrayList<>();
            Connection();
            String sqlcmd="SELECT * FROM bookcart WHERE user_username="+"'"+username+"'";
            Connection connection= DriverManager.getConnection(DB_NAME,USER,PASS);
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sqlcmd);
            while(resultSet.next()){
                model=new IssueBookModel(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                        resultSet.getString(4));
                list.add(model);
            }
            statement.close();
            connection.close();
            resultSet.close();
            return list;
        }

        @Override
        public void returnBooks(IssueBookModel model) throws ClassNotFoundException, SQLException {
            Connection();
            //Deletion
            String sqlcmd="DELETE from bookcart WHERE cart_id="+"'"+model.getCart_id()+"'";
            Connection connection=DriverManager.getConnection(DB_NAME,USER,PASS);
            Statement statement=connection.createStatement();
            statement.execute(sqlcmd);
            //Update
            String sqlcmd2="UPDATE bookslist SET book_quantity=book_quantity+1 WHERE book_title="+"'"+model.getBook_title()+"'";
            Statement statement2=connection.createStatement();
            statement2.execute(sqlcmd2);

            statement.close();
            statement2.close();
            connection.close();
        }

        @Override
        public boolean checkIfEmptyCart(String username) throws ClassNotFoundException, SQLException {
            Connection();
            String sqlcmd="SELECT * FROM bookcart WHERE user_username="+"'"+username+"'";
            Connection connection=DriverManager.getConnection(DB_NAME,USER,PASS);
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sqlcmd);
            if(!resultSet.next()){
                statement.close();
                connection.close();
                return true;
            }else {
                statement.close();
                connection.close();
                return false;
            }
        }

        @Override
        public int getNumberOfBooks(String username) throws ClassNotFoundException, SQLException {
            Connection();
            int number_books=0;
            String sqlcmd="SELECT * FROM bookcart WHERE user_username="+"'"+username+"'";
            Connection connection=DriverManager.getConnection(DB_NAME,USER,PASS);
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sqlcmd);
            while(resultSet.next()){
                number_books++;
            }
            statement.close();
            connection.close();
            resultSet.close();
            return number_books;
        }
    }
}

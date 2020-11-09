package emp.project.librarymanagementapp.Presenter;

import android.content.Context;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import emp.project.librarymanagementapp.Interfaces.BookInterface;
import emp.project.librarymanagementapp.Models.BookModel;
import emp.project.librarymanagementapp.View.BookActivityView;
import emp.project.librarymanagementapp.View.LoginActivityView;

@SuppressWarnings("ALL")
public class BookPresenter implements BookInterface.BookPresenterInterface {

    Context context;
    BookActivityView view;
    BookModel model;

    public BookPresenter(Context context, BookActivityView view) {
        this.context = context;
        this.view = view;
        this.model = new BookModel();
    }

    @Override
    public void directRefreshPage() {
        view.refreshPage();
    }

    @Override
    public void directRecyclerView() throws SQLException, ClassNotFoundException {
        DbHelper dbHelper = new DbHelper();
        view.displayRecyclerView(dbHelper.getBook());
    }

    @Override
    public void directBookCategory() throws SQLException, ClassNotFoundException {
        DbHelper dbHelper = new DbHelper();
        view.displayBookCategory(dbHelper.getBookCategory());
    }

    @Override
    public void directRecyclerViewBasedOnCategory(String category) throws SQLException, ClassNotFoundException {
        DbHelper dbHelper = new DbHelper();
        view.displayRecyclerViewBasedOnCategory(dbHelper.getBookBasedOnCategory(category));
    }

    @Override
    public void directCartListToDB(List<BookModel> list_cartBooks) throws SQLException, ClassNotFoundException {
        DbHelper db = new DbHelper();
        db.insertCartListToDB(list_cartBooks);
        view.onFailedMessage("Successfull!");
        view.refreshPage();
    }


    @Override
    public void btnCheckOutClicked() {
        view.displayAlertDialog();
    }

    @Override
    public void directGetBookBasedOnSearch(String book_title) throws SQLException, ClassNotFoundException {
        DbHelper dbHelper = new DbHelper();
        view.displayRecyclerViewBasedOnSearch(dbHelper.getBookBasedOnSearch(book_title));
    }


    private class DbHelper implements BookInterface.DbHelperBooks {

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
        public List<BookModel> getBook() throws ClassNotFoundException, SQLException {
            List<BookModel> list = new ArrayList<>();
            Connection();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlcmd = "SELECT * FROM bookslist";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlcmd);
            while (resultSet.next()) {
                model = new BookModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
                list.add(model);
            }
            resultSet.close();
            statement.close();
            connection.close();
            return list;
        }

        @Override
        public List<String> getBookCategory() throws ClassNotFoundException, SQLException {
            List<String> list = new ArrayList<>();
            List<String> tempList = new ArrayList<>();
            Connection();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlcmd = "SELECT * FROM bookslist";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlcmd);
            while (resultSet.next()) {
                tempList.add(resultSet.getString(5));
            }
            for (int i = 0; i < tempList.size(); i++) {
                if (!list.contains(tempList.get(i))) {
                    list.add(tempList.get(i));
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
            return list;
        }

        @Override
        public List<BookModel> getBookBasedOnCategory(String category) throws ClassNotFoundException, SQLException {
            List<BookModel> list = new ArrayList<>();
            Connection();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlcmd = "SELECT * FROM bookslist WHERE book_category=" + "'" + category + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlcmd);
            while (resultSet.next()) {
                model = new BookModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
                list.add(model);
            }
            resultSet.close();
            statement.close();
            connection.close();
            return list;
        }

        @Override
        public void insertCartListToDB(List<BookModel> list_cartBooks) throws ClassNotFoundException, SQLException {
            //Dates---------------
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String dateIssue = sdf.format(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, 5);
            String dateDeadline = sdf.format(c.getTime());
            try{
                c.setTime(sdf.parse(dateIssue));
            }catch(ParseException e){
                e.printStackTrace();
            }

            //Dates----------------

            Connection();
            Statement statement = null;
            Statement statement2 = null;
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            for (int i = 0; i < list_cartBooks.size(); i++) {
                String sqlcmd = "INSERT INTO bookcart(user_username,book_title,book_imageurl,dateIssue,dateDeadline)VALUES(" + "'" + LoginActivityView.getUsername()
                        + "','" + list_cartBooks.get(i).getBook_title() + "','" + list_cartBooks.get(i).getBook_url() + "','"+dateIssue+"','"+dateDeadline+"')";
                String sqlcmdMinusValue = "UPDATE bookslist SET book_quantity = book_quantity - 1 WHERE book_title=" + "'" + list_cartBooks.get(i).getBook_title() + "'";
                statement = connection.createStatement();
                statement2 = connection.createStatement();
                statement.execute(sqlcmd);
                statement2.execute(sqlcmdMinusValue);
            }
            statement.close();
            statement2.close();
            connection.close();
        }

        @Override
        public List<BookModel> getBookBasedOnSearch(String book_title) throws ClassNotFoundException, SQLException {
            Connection();
            List<BookModel> list = new ArrayList<>();
            Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
            String sqlcmdSearch = "SELECT * FROM bookslist WHERE book_title LIKE " + "'" + book_title + "%'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlcmdSearch);
            while (resultSet.next()) {
                model = new BookModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
                list.add(model);
            }
            statement.close();
            connection.close();
            return list;
        }
    }
}




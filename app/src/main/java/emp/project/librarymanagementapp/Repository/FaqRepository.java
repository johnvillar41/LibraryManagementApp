package emp.project.librarymanagementapp.Repository;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.librarymanagementapp.DatabaseCredentials;
import emp.project.librarymanagementapp.Interfaces.IFAqs;
import emp.project.librarymanagementapp.Models.FAQModel;

public class FaqRepository implements IFAqs.IFaqRepository, DatabaseCredentials {

    private static FaqRepository instance = null;

    private FaqRepository() {

    }

    public static FaqRepository getInstance() {
        if (instance == null) {
            instance = new FaqRepository();
        }
        return instance;
    }

    @Override
    public void Connection() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override
    public List<FAQModel> displayFAQS() throws ClassNotFoundException, SQLException {
        Connection();
        List<FAQModel> list = new ArrayList<>();
        String sqlcmd = "SELECT * FROM faqtable";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlcmd);
        while (resultSet.next()) {
            FAQModel model = new FAQModel(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            list.add(model);
        }
        statement.close();
        connection.close();
        resultSet.close();
        return list;
    }

    @Override
    public void insertNewQuestion(String question) throws ClassNotFoundException, SQLException {
        Connection();
        String sqlcmd = "INSERT INTO faqtable(faq_question,faq_reply) VALUES(" + "'" + question + "','" + "N/A" + "')";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        Statement statement = connection.createStatement();
        statement.execute(sqlcmd);
        statement.close();
        connection.close();
    }

    @Override
    public void removeFAQ(String id) throws ClassNotFoundException, SQLException {
        Connection();
        String sqlcmd = "DELETE FROM faqtable WHERE faq_id=" + "'" + id + "'";
        Connection connection = DriverManager.getConnection(DB_NAME, USER, PASS);
        Statement statement = connection.createStatement();
        statement.execute(sqlcmd);
        statement.close();
        connection.close();
    }
}

package emp.project.librarymanagementapp.Presenter;

import android.os.StrictMode;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import emp.project.librarymanagementapp.Interfaces.FAQInterface;
import emp.project.librarymanagementapp.Models.FAQModel;
import emp.project.librarymanagementapp.View.FAQActivityView;

public class FAQPresenter implements FAQInterface.FAQPresenter {

    FAQActivityView view;
    FAQModel model;

    public FAQPresenter(FAQActivityView view) {
        this.view = view;
        this.model=new FAQModel();
    }

    @Override
    public void directToDisplayFAQ() throws SQLException, ClassNotFoundException {
        FAQDBhelper faqdBhelper=new FAQDBhelper();
        view.displayFaqs(faqdBhelper.displayFAQS());
    }

    @Override
    public void directToNewQuestion(String question) throws SQLException, ClassNotFoundException {
        FAQDBhelper faqdBhelper=new FAQDBhelper();
        faqdBhelper.insertNewQuestion(question);
    }

    @Override
    public void directToErrorMessage(EditText textErrorField) {
        view.displayErrorMessage("Please fill out empty fields!",textErrorField);
    }

    @Override
    public void directToRemoveFAQ(String id) throws SQLException, ClassNotFoundException {
        FAQDBhelper faqdBhelper=new FAQDBhelper();
        faqdBhelper.removeFAQ(id);
    }

    private class FAQDBhelper implements FAQInterface.FAQDbhelper{

        private String DB_NAME = "jdbc:mysql://192.168.1.152:3306/librarydb";
        private String USER = "admin";
        private String PASS = "admin";

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
            List<FAQModel>list=new ArrayList<>();
            String sqlcmd="SELECT * FROM faqtable";
            Connection connection= DriverManager.getConnection(DB_NAME,USER,PASS);
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sqlcmd);
            while(resultSet.next()){
                model=new FAQModel(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
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
            String sqlcmd="INSERT INTO faqtable(faq_question,faq_reply) VALUES("+"'"+question+"','"+"N/A"+"')";
            Connection connection=DriverManager.getConnection(DB_NAME,USER,PASS);
            Statement statement=connection.createStatement();
            statement.execute(sqlcmd);
            statement.close();
            connection.close();
        }

        @Override
        public void removeFAQ(String id) throws ClassNotFoundException, SQLException {
            Connection();
            String sqlcmd="DELETE FROM faqtable WHERE faq_id="+"'"+id+"'";
            Connection connection=DriverManager.getConnection(DB_NAME,USER,PASS);
            Statement statement=connection.createStatement();
            statement.execute(sqlcmd);
            statement.close();
            connection.close();
        }
    }
}

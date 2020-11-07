package emp.project.librarymanagementapp.Interfaces;

import android.widget.EditText;

import java.sql.SQLException;
import java.util.List;

import emp.project.librarymanagementapp.Models.FAQModel;

public interface FAQInterface {
    interface FAQView{
        void InitViews() throws SQLException, ClassNotFoundException;

        void displayFaqs(List<FAQModel> list);

        void displayErrorMessage(String message,EditText textErrorField);
    }
    interface FAQPresenter {
        void directToDisplayFAQ() throws SQLException, ClassNotFoundException;

        void directToNewQuestion(String question) throws SQLException, ClassNotFoundException;

        void directToErrorMessage(EditText textErrorField);

        void directToRemoveFAQ(String id) throws SQLException, ClassNotFoundException;
    }
    interface FAQDbhelper{
        void Connection() throws ClassNotFoundException;

        List<FAQModel>displayFAQS() throws ClassNotFoundException, SQLException;

        void insertNewQuestion(String question) throws ClassNotFoundException, SQLException;

        void removeFAQ(String id) throws ClassNotFoundException, SQLException;
    }

}

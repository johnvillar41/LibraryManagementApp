package emp.project.librarymanagementapp.Presenter;

import android.widget.EditText;

import java.sql.SQLException;

import emp.project.librarymanagementapp.Interfaces.IFAqs;
import emp.project.librarymanagementapp.Models.FAQModel;

public class FAQPresenter implements IFAqs.FAQPresenter {

    private final IFAqs.FAQView view;
    private final IFAqs.IFaqRepository repository;

    public FAQPresenter(IFAqs.FAQView view, IFAqs.IFaqRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void directToDisplayFAQ() throws SQLException, ClassNotFoundException {
        view.displayFaqs(repository.displayFAQS());
    }

    @Override
    public void directToNewQuestion(String question) throws SQLException, ClassNotFoundException {
        repository.insertNewQuestion(question);
    }

    @Override
    public void directToErrorMessage(EditText textErrorField) {
        view.displayErrorMessage("Please fill out empty fields!", textErrorField);
    }

    @Override
    public void directToRemoveFAQ(String id) throws SQLException, ClassNotFoundException {
        repository.removeFAQ(id);
    }
}

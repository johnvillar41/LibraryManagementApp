package emp.project.librarymanagementapp.Presenter;

import java.sql.SQLException;

import emp.project.librarymanagementapp.Interfaces.IIssueHistory;
import emp.project.librarymanagementapp.Models.IssueBookModel;
import emp.project.librarymanagementapp.Models.NotificationModel;
import emp.project.librarymanagementapp.View.LoginActivityView;

public class IssueBookHistoryPresenter implements IIssueHistory.IssueHistoryPresenterInterface {
    private final IIssueHistory.IssueHistoryViewInterface view;
    private final IIssueHistory.IIssueHistoryRepository repository;

    public IssueBookHistoryPresenter(IIssueHistory.IssueHistoryViewInterface view, IIssueHistory.IIssueHistoryRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void directIssueBookDisplay(String username) throws SQLException, ClassNotFoundException {
        view.displayIssueBooks(repository.getIssueBooks(username));
    }

    @Override
    public void directReturnBook(IssueBookModel model) throws SQLException, ClassNotFoundException {
        repository.returnBooks(model);
    }

    @Override
    public void directCheckCart(String username) throws SQLException, ClassNotFoundException {
        view.displayIfEmptyCart(repository.checkIfEmptyCart(username));
    }

    @Override
    public void directNumberBooks() throws SQLException, ClassNotFoundException {
        view.displayNumberOfBooks(repository.getNumberOfBooks(LoginActivityView.getUsername()));
    }

    @Override
    public void directNewNotification(String notif_title, String notif_msg, String user_username) throws SQLException, ClassNotFoundException {
        NotificationModel model = new NotificationModel(null, notif_title, notif_msg, user_username);
        repository.createNewNotification(model);
    }
}

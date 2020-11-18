package emp.project.librarymanagementapp.Presenter;

import emp.project.librarymanagementapp.Interfaces.MainMenuInterface;
import emp.project.librarymanagementapp.View.MainMenuActivityView;

public class MainMenuPresenter implements MainMenuInterface.MainMenuPresenter {

    private MainMenuActivityView view;

    public MainMenuPresenter(MainMenuActivityView view) {
        this.view = view;
    }

    @Override
    public void directToBooksList(){
        view.goToBooksList();
    }

    @Override
    public void directToIssuelist() {
        view.goToIssueBooks();
    }

    @Override
    public void directToFaqs() {
        view.goToFaqs();
    }

    @Override
    public void directToNotifs() {
        view.goToNotifs();
    }

    @Override
    public void directToLogout() {
        view.logOut();
    }
}

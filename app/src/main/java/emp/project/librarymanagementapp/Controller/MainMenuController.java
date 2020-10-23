package emp.project.librarymanagementapp.Controller;

import emp.project.librarymanagementapp.Interfaces.MainMenuInterface;
import emp.project.librarymanagementapp.View.MainMenuActivityView;

public class MainMenuController implements MainMenuInterface.MainMenuController {

    MainMenuActivityView view;

    public MainMenuController(MainMenuActivityView view) {
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

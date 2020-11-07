package emp.project.librarymanagementapp.Interfaces;

public interface MainMenuInterface {
    interface MainMenuViewInterface{
        void InitViews();

        void goToBooksList();

        void goToIssueBooks();

        void goToFaqs();

        void goToNotifs();

        void logOut();

        void setUsername();
    }
    interface MainMenuPresenter {
        void directToBooksList();

        void directToIssuelist();

        void directToFaqs();

        void directToNotifs();

        void directToLogout();
    }
}

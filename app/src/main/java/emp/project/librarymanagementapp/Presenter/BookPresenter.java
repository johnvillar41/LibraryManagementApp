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

import emp.project.librarymanagementapp.Interfaces.IBook;
import emp.project.librarymanagementapp.Models.BookModel;
import emp.project.librarymanagementapp.View.LoginActivityView;

public class BookPresenter implements IBook.BookPresenterInterface {
    
    private final IBook.BookActivityViewInterface view;
    private final IBook.IBookRepository repository;

    public BookPresenter(IBook.IBookRepository repository, IBook.BookActivityViewInterface view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void directRefreshPage() {
        view.refreshPage();
    }

    @Override
    public void directRecyclerView() throws SQLException, ClassNotFoundException {
        view.displayRecyclerView(repository.getBook());
    }

    @Override
    public void directBookCategory() throws SQLException, ClassNotFoundException {
        view.displayBookCategory(repository.getBookCategory());
    }

    @Override
    public void directRecyclerViewBasedOnCategory(String category) throws SQLException, ClassNotFoundException {
        view.displayRecyclerViewBasedOnCategory(repository.getBookBasedOnCategory(category));
    }

    @Override
    public void directCartListToDB(List<BookModel> list_cartBooks) throws SQLException, ClassNotFoundException {
        repository.insertCartListToDB(list_cartBooks);
        view.onFailedMessage("Successfull!");
        view.refreshPage();
    }

    @Override
    public void btnCheckOutClicked() {
        view.displayAlertDialog();
    }

    @Override
    public void directGetBookBasedOnSearch(String book_title) throws SQLException, ClassNotFoundException {
        view.displayRecyclerViewBasedOnSearch(repository.getBookBasedOnSearch(book_title));
    }
}




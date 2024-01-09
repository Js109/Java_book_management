package de.julian.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookList {

    static List<Book> bookList = new ArrayList<>();

    public void addBook(String title, String author, int isbn) {
        if (bookList.isEmpty())
            bookList.add(new Book(1, title, author, isbn));
        else {
            int newBookID = bookList.getLast().getBookID() + 1;
            bookList.add(new Book(newBookID, title, author, isbn));
        }
    }

    public void removeBook(String title) {
        if (bookList.isEmpty())
            System.out.println("Book was not found and thus could not be removed.");
        else {
            bookList.removeIf(book -> book.getTitle().equals(title));
            System.out.println("Book with title " + title + " has been removed if present");
        }
    }

    public void printBookList() {
        bookList.forEach(System.out::println);
    }

    public void updateBook(int IDtoUpdate, String title, String author, int isbn) {
        Book bookToUpdate;
        List<Book> resultList = predicateBookFilter(bookList, (Book book) -> book.getBookID() == IDtoUpdate);
        if (resultList.isEmpty())
            System.out.println("There is no book with this id to update.");
        else {
            bookToUpdate = resultList.getFirst();
            bookList.set(bookList.indexOf(bookToUpdate), new Book(bookToUpdate.getBookID(), title, author, isbn));
        }
    }

    public void printSortedBookList() {
        Comparator<Book> TitleSorter = (b1, b2) -> b1.getTitle().compareTo(b2.getTitle());
        Stream<Book> bookStream = bookList.stream().sorted(TitleSorter);
        bookStream.forEach(System.out::println);
    }

    public void printFilteredBookList(String filterString) {
        System.out.println("Booktitle contains input: " + predicateBookFilter(bookList,  (Book book) -> book.getTitle().contains(filterString)));
    }

    private List<Book> predicateBookFilter(List<Book> books, Predicate<Book> p) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (p.test(book)) {                       // Condition overwrites test from Predicate --> returns boolean from Predicate
                result.add(book);
            }
        }
        return result;
    }
}

/*class TitleSorter implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return b1.getTitle().compareTo(b2.getTitle());
    }
}*/

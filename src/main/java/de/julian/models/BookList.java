package de.julian.models;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BookList {

    static List<Book> bookList = new ArrayList<>();

    public static List<Book> getBookList() {
        return bookList;
    }

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

    public void inputBooksFromFile(String filename) {
        Path path = Paths.get(filename);
        File file = new File(String.valueOf(path.toFile()));
        //File file = new File("booksinput.txt");

        try (
                BufferedReader br = Files.newBufferedReader(file.toPath())
        ) {

            int count = 0;
            String line;
            String[] booksInput;
            while ((line = br.readLine()) != null) {        // readLine() reads only line
                System.out.println(line);
                booksInput = line.split(",");

                // Trim space in every subinput
                for (int i = 0; i < booksInput.length; i++) {
                    booksInput[i] = booksInput[i].trim();
                }

                for (int j = 0; j < booksInput.length; j = j + 3) {
                    int newBookID = (bookList.isEmpty()) ? 1 : (bookList.getLast().getBookID() + 1);
                    bookList.add(new Book(newBookID, booksInput[0], booksInput[1], Integer.parseInt(booksInput[2])));
                }

                count++;

            }
            System.out.println("\n" + count + " lines read from file input");
            System.out.println(bookList);

        } catch (IOException ex) {
            System.out.println("Inputfile could not be found: " + ex.getMessage());
        }
    }

    public void outputBooksToFile(String outputFileName) {
        Path path = Paths.get(outputFileName);
        File file = new File(String.valueOf(path.toFile()));
        //File file = new File("output.txt");

        try (
                PrintWriter pw = new PrintWriter(file)
        ) {

            int count = 0;

            for (Book book : bookList) {
                pw.println(book);
                System.out.println(book);
                count++;

                if (count == 2) {
                    pw.flush();
                }
            }
            System.out.println("\n" + count + " Lines written");
        } catch (FileNotFoundException e) {
            System.out.println("Output file could not be found:" + e.getMessage());
            //throw new RuntimeException(e);
        }
    }

    public void printSortedBookList() {
        Comparator<Book> TitleSorter = (b1, b2) -> b1.getTitle().compareTo(b2.getTitle());
        Stream<Book> bookStream = bookList.stream().sorted(TitleSorter);
        bookStream.forEach(System.out::println);
    }

    public void printFilteredBookList(String filterString) {
        System.out.println("Booktitle contains input: " + predicateBookFilter(bookList, (Book book) -> book.getTitle().contains(filterString)));
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

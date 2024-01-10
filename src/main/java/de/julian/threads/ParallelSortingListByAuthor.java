package de.julian.threads;

import de.julian.models.Book;
import de.julian.models.BookList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
  This ParallelSorting divides the whole bookList into two parts to a/A-m/M and n/N-z/Z,
  sorts them in two seperate Threads and merges the result back into a result list.
  If the book list contains large amounts of data, this can lead to performance optimisation.

  Usage is for parallel sorting demonstration purpose.
 */
public class ParallelSortingListByAuthor {

    public void parallelAuthorSortingInTwoThreads() {
        List<Book> bookList1;
        List<Book> bookList2;

        bookList1 = BookList.getBookList()
                .stream()
                .filter(book -> startsWithAtoM(book.getAuthor()))
                .collect(Collectors.toList());

        bookList2 = BookList.getBookList()
                .stream()
                .filter(book -> startsWithNtoZ(book.getAuthor()))
                .collect(Collectors.toList());


        // generate two threads for the sorting
        SortingThread t1 = new SortingThread(bookList1);
        SortingThread t2 = new SortingThread(bookList2);

        // start threads
        t1.start();
        t2.start();

        try {
            // Wait until the threads are terminated
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            System.out.println("InterruptedException occured" + ex.getMessage());
        }

        // After both Threads are terminated, it is possible to access the sorted list
        List<Book> sortedBookList1 = t1.getSortedBookList();
        List<Book> sortedBookList2 = t2.getSortedBookList();

        // Since both lists are already sorted we can append it with addAll to the end of the List
        sortedBookList1.addAll(sortedBookList2);

        System.out.println("Sorted List regarding author after parallel sorting in threads: " + sortedBookList1);

    }

    private static boolean startsWithAtoM(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        char firstChar = str.charAt(0);
        return (firstChar >= 'a' && firstChar <= 'm') || (firstChar >= 'A' && firstChar <= 'M');
    }

    private static boolean startsWithNtoZ(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        char firstChar = str.charAt(0);
        return (firstChar >= 'n' && firstChar <= 'z') || (firstChar >= 'N' && firstChar <= 'Z');
    }
}

class SortingThread extends Thread {
    private List<Book> bookList;

    public SortingThread(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public void run() {
        Collections.sort(bookList);
    }

    public List<Book> getSortedBookList() {
        return bookList;
    }
}


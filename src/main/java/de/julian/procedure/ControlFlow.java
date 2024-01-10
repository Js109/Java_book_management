package de.julian.procedure;

import de.julian.models.BookList;
import de.julian.threads.ParallelSortingListByAuthor;

import java.util.Scanner;

public class ControlFlow {

    public void startControlFlow() {
        Scanner sc = new Scanner(System.in);
        BookList bookList = new BookList();
        int counter = 0;

        String sip;
        int idToUpdate;

        while (counter == 0) {
            System.out.println("\nWhich operation would you like to use? \n Type in 1 to 'Show Books' \n Type in 2 to 'Add Book' \n Type in 3 to 'Delete Book \n Type in 4 to 'Update Book' \n Type in 5 to 'Add Book - Input from text file' \n Type in 6 to 'Show Books - Output in text file' \n Type in 7 to 'Show Books in alphabetic sorted order regarding title' \n Type in 8 to 'Show Books in alphabetic sorted order regarding author' \n Type in 9 to 'Show filtered Books' \n Type in 10 to quit the program.");
            int number = sc.nextInt();
            sc.nextLine();

            switch (number) {
                case 1:
                    System.out.println("Show Books: \n");
                    bookList.printBookList();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        System.out.println("Error in thread" + ex.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Add Book - example: title, author, isbn");
                    sip = sc.nextLine();
                    if (matchesInputFormat(sip)) {
                        addBook(sip);
                    } else {
                        System.out.println("Input was not in the correct format. The correct format is: title, author, isbn");
                    }
                    break;
                case 3:
                    System.out.println("Remove Book - example: title");
                    sip = sc.nextLine();
                    bookList.removeBook(sip);
                    break;
                case 4:
                    System.out.println("Update Book. Give ID of the book to update - example: 5");
                    idToUpdate = sc.nextInt();
                    System.out.println("Update Book. Give in new values - example: title, author, isbn");
                    sc.nextLine();
                    sip = sc.nextLine();
                    if (matchesInputFormat(sip)) {
                        updateBook(idToUpdate, sip);
                    } else {
                        System.out.println("Input was not in the correct format. The correct format is: title, author, isbn");
                    }
                    break;
                case 5:
                    System.out.println("Add Books - Input from text file - example: booksinput.txt");
                    sip = sc.nextLine();
                    if (matchesFileFormat(sip)) {
                        bookList.inputBooksFromFile(sip);
                    } else {
                        System.out.println("Input was not in the correct format. The correct format is filename.txt");
                    }
                    break;
                case 6:
                    System.out.println("Show Books - Output in text file - example output name: outputbooks.txt");
                    sip = sc.nextLine();
                    if (matchesFileFormat(sip)) {
                        bookList.outputBooksToFile(sip);
                    } else {
                        System.out.println("Output name was not in the correct format. The correct format is filename.txt");
                    }
                    break;
                case 7:
                    System.out.println("Show Books in alphabetic sorted order regarding title \n");
                    bookList.printSortedBookList();
                    break;
                case 8:
                    System.out.println("Show Books in alphabetic sorted order regarding author \n");
                    new ParallelSortingListByAuthor().parallelAuthorSortingInTwoThreads();
                    break;
                case 9:
                    System.out.println("Show filtered Books - Example: title");
                    sip = sc.nextLine();
                    bookList.printFilteredBookList(sip);
                    break;
                case 10:
                    System.out.println("Quitted program");
                    counter = counter + 1;
                    break;
            }
        }
        sc.close();
    }

    public boolean matchesInputFormat(String input) {
        return input.matches("[\\w\\s]+,[\\w\\s]+,\\d+") || input.matches("[\\w\\s]+, [\\w\\s]+, \\d+");
    }

    public boolean matchesFileFormat(String input) {
        return input.matches("\\w+.\\w+");
    }

    public void addBook(String bookString) {
        String[] bookInput = bookString.split(",");

        for (int i = 0; i < bookInput.length; i++) {
            bookInput[i] = bookInput[i].trim();
        }

        new BookList().addBook(bookInput[0], bookInput[1], Integer.parseInt(bookInput[2]));
    }

    public void updateBook(int id, String bookString) {
        String[] bookInput = bookString.split(",");

        for (int i = 0; i < bookInput.length; i++) {
            bookInput[i] = bookInput[i].trim();
        }

        new BookList().updateBook(id, bookInput[0], bookInput[1], Integer.parseInt(bookInput[2]));
    }
}

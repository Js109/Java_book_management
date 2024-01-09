package de.julian.procedure;

import de.julian.models.BookList;

import java.util.Scanner;

public class ControlFlow {

    public void startControlFlow() {
        Scanner sc = new Scanner(System.in);
        int counter = 0;

        String sip;
        int idToUpdate;

        while (counter == 0) {
            System.out.println("\nWhich operation would you like to use? \n Type in 1 to 'Show Books' \n Type in 2 to 'Add Book' \n Type in 3 to 'Delete Book \n Type in 4 to 'Update Book' \n Type in 5 to 'Add Book - Input from text file' \n Type in 6 to 'Show Books - Output in text file' \n Type in 7 to quit the program.");
            int number = sc.nextInt();
            sc.nextLine();

            switch (number) {
                case 1:
                    System.out.println("Show Books: \n");
                    new BookList().printBookList();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        System.out.println("Fehler in Thread verzögerung" + ex.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Add Book - example: title, author, isbn");
                    sip = sc.nextLine();
                    addBook(sip);
                    break;
                case 3:
                    System.out.println("Remove Book - example: title");
                    sip = sc.nextLine();
                    new BookList().removeBook(sip);
                    break;
                case 4:
                    System.out.println("Update Book. Give ID of the book to update - example: 5");
                    idToUpdate = sc.nextInt();
                    System.out.println("Update Book. Give in new values - example: title, author, isbn");
                    sc.nextLine();
                    sip = sc.nextLine();
                    updateBook(idToUpdate, sip);
                    break;
                case 5:
                    System.out.println("Add Book - Input from text file");
                    break;
                case 6:
                    System.out.println("Show Books - Output in text file");
                    break;
                case 7:
                    System.out.println("Show Books in sorted order regarding title: \n");
                    new BookList().printSortedBookList();
                    break;
                case 8:
                    System.out.println("Show filtered Books - Example: title");
                    sip = sc.nextLine();
                    new BookList().printFilteredBookList(sip);
                    break;
                case 9:
                    System.out.println("Quitted program");
                    counter = counter + 1;
                    break;
            }
        }
        sc.close();
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

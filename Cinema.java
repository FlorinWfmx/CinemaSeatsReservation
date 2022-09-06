/* by Wfmx
 * 09.2022
 */

import java.util.*;

class Cinema {

    private static Scanner scanner = new Scanner(System.in);

    private String name;
    private char lastRow;
    private int seatsPerRow;

    private static List<String> seatsList = new ArrayList<>();
    private static List<String> removedSeatsList = new ArrayList<>();
    private static Map<Integer, String> deletedIndex = new TreeMap<>();

    Cinema (String name, char lastRow, int seatsPerRow) {
        this.name = name;
        this.lastRow = lastRow;
        this.seatsPerRow = seatsPerRow;

        for (char row = 'A'; row <= lastRow; row++) {
            for (int i = 1; i <= seatsPerRow; i++) {
                seatsList.add(row + String.format("%02d", i));
            }
        }
    }

    public void seatsList() {
        for (int i = 0; i <= ((int)lastRow - 65) * seatsPerRow; i += seatsPerRow) {
            System.out.print(seatsList.subList(i, i + seatsPerRow) + "\n");
        }
        System.out.println("Please select a menu option!");
    }

    public void countSeats() {
        System.out.println("Cinema " + name + " has now " + (seatsList.size() - removedSeatsList.size()) + " available seats. Full capacity is " +
                (int) (lastRow - 64) + " rows and " + seatsPerRow + " seats per row (" +  (int) (lastRow - 64) * seatsPerRow + " seats).");
        if (!seatsList.equals(removedSeatsList)) {
            System.out.println("Reserved seats list: " + removedSeatsList + ".");
        }
        System.out.println("Please select a menu option!");
    }

    public static void reserveSeat() {
        scanner = new Scanner(System.in);
        System.out.println("Enter seat to reserve:");
        String seat = scanner.nextLine();
        if (!seatsList.contains(seat) && !removedSeatsList.contains(seat)){
            System.out.println("Seat " + seat + " does not exist." +
                    "\nPlease select a menu option!");
        }
        else {
            if (seatsList.contains(seat)) {
                System.out.println("Seat " + seat + " is available!");
                deletedIndex.put(seatsList.indexOf(seat), seat);
                seatsList.add(seatsList.indexOf(seat), "(-)");
                seatsList.remove(seat);
                removedSeatsList.add(seat);
                System.out.println("Seat " + seat + " was successfully reserved!");
                System.out.println("Going to payment interface. Please wait...");
                goToPayment();
            }
            else {
                System.out.println("Seat " + seat + " is no longer available." +
                        " Please select a menu option!");
            }
        }
    }

    private static int retrieveIndex(String seat) {

        for (Map.Entry<Integer, String> elem : deletedIndex.entrySet()) {
            if ((elem.getValue().equals(seat))) {
                return elem.getKey();
            }
        } return 0;
    }

    public void unreserveSeat() {
        scanner = new Scanner(System.in);
        System.out.println("Enter seat to unreserve:");
        String seat = scanner.nextLine();
        if (!removedSeatsList.contains(seat) && !seatsList.contains(seat)){
            System.out.println("Seat " + seat + " does not exist!");
        } else {
            if (removedSeatsList.contains(seat)) {
                seatsList.add(retrieveIndex(seat), seat);
                seatsList.remove(seatsList.indexOf(seat) + 1);
                removedSeatsList.remove(seat);
                System.out.println("Seat " + seat + " was unreserved!");
            } else {
                System.out.println("Seat " + seat + " is not reserved!");
            }
        }
        System.out.println("Please select a menu option!");
    }

    private static void unreserveSeat(String seat) {

                seatsList.add(retrieveIndex(seat), seat);
                seatsList.remove(seatsList.indexOf(seat) + 1);
                removedSeatsList.remove(seat);
                System.out.println("Seat " + seat + " was unreserved." +
                        " Please select a menu option!");
    }

    private static void goToPayment (){
        scanner = new Scanner(System.in);
        System.out.println("\n\t\tYOU ENTERED PAYMENT INTERFACE\n" +
                "\nType 'yes' to proceed with payment.\n" +
                "Type anything else to cancel and the seat will be automatically unreserved.");
        String option = scanner.nextLine();
            if (option.equals("yes")){
                System.out.println("Enter card number: (Only for demo purposes, type any number...)");
                int cardNumber = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter name: (Only for demo purposes, type any name...)");
                String customerName = scanner.nextLine();
                System.out.println("Enter CVV code from the back of the card: (Only for demo purposes, type any number...)");
                int pinNumber = scanner.nextInt();
                System.out.println("Payment successfully validated! (no real account withdrawal, only for demo purposes...)" +
                        "\nReturning to main menu...");
                System.out.println();
                printMenu();

            }
            else {
                unreserveSeat(removedSeatsList.get(removedSeatsList.size()-1));
            }
    }

    static void printMenu() {
        System.out.println("MAIN MENU\n\tPress 1 to show number of available seats;" +
                "\n\tPress 2 to show seats map;" +
                "\n\tPress 3 to reserve a seat;" +
                "\n\tPress 4 to unreserve a seat;" +
                "\n\tPress 5 to show main menu;" +
                "\n\tPress 0 to exit.");
    }

    public void showOptions() {
        boolean quit = false;

        while (!quit) {
            try {
                if (scanner.hasNextInt()) {
                    int x = scanner.nextInt();
                    switch (x) {
                        case 0:
                            System.out.println("Exiting...");
                            quit = true;
                            break;
                        case 1:
                            countSeats();
                            break;
                        case 2:
                            seatsList();
                            break;
                        case 3:
                            reserveSeat();
                            break;
                        case 4:
                            unreserveSeat();
                            break;
                        case 5:
                        default:
                            printMenu();
                            break;
                    }
                }
                else {
                    throw new WrongInputException();
                }
            }
            catch (WrongInputException e){
                System.out.println(e);
                scanner.nextLine();
            }
        }
    }
}

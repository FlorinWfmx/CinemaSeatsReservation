package Collections.Cinema;

/* by Wfmx
 * 09.2022
 */

import java.util.Scanner;

public class CinemaMain {

    public static void main(String[] args) throws Exception {

        System.out.println("Type name of the cinema:");
        Scanner scanner = new Scanner (System.in);
        String cinemaName = scanner.nextLine();

        boolean quit = false;

        System.out.println("Type number of rows:");
        char lastRow = 0;
        while (!quit){
            if (scanner.hasNextInt()){
                lastRow = (char) (scanner.nextInt()+64);
                quit = true;
            }
            else {
                System.out.println ("Input mismatch error. Enter a correct number!");
            }
            scanner.nextLine();
        }

        System.out.println("Type number of seats per row:");
        int seatsPerRow = 0;
        quit = false;

        while (!quit){
            if (scanner.hasNextInt()){
                seatsPerRow = scanner.nextInt();
                quit = true;
            }
            else {
                System.out.println ("Input mismatch error. Enter a correct number!");
            }
            scanner.nextLine();
        }

        Cinema cinema = new Cinema(cinemaName, lastRow, seatsPerRow);
        cinema.printMenu();
        cinema.showOptions();
    }
}

package com.hms;

import java.util.*;

import com.hms.fileHandling.FileHandling;
import com.hms.Hotel;
import com.hms.rooms.Room;
import com.hms.persons.Guest;
import com.hms.persons.Staff;

import java.io.*;

class ReaderThread implements Runnable {
    @Override
    public void run() {
        try {
            FileHandling.readFromCSV();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // creating a thread to read from CSV files;
        ReaderThread readerThread = new ReaderThread();
        Thread thread = new Thread(readerThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ---------------------------

        // main(); Body
        Staff s = new Staff();
        s.addPerson(10, 200);
        Scanner cin = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            String inp = "";
            Integer choice;
            System.out.print("\n\n---------------------------");
            System.out.print("\n\n- HOTEL MANAGEMENT SYSTEM -");
            System.out.print("\n\n---------------------------\n");
            System.out.print("\n[01] Login (Staff Only)");
            System.out.print("\n[02] See Available Rooms");
            System.out.print("\n[03] Book a Room");
            System.out.print("\n[04] Check-Out");
            System.out.print("\n\n[00] Exit");
            System.out.print("\n\n---------------------------");
            System.out.print("\n\nEnter your choice:\n");
            inp = cin.next();
            inp += cin.nextLine();
            try {
                choice = Integer.parseInt(inp);
            } catch (Exception e) {
                System.out.print("\n\nInvalid Input!\n");
                continue;
            }
            System.out.print("\n---------------------------");

            switch (choice) {
            case 1:
                System.out.print("\n\n---------- LOGIN ----------");
                System.out.print("\n\n---------------------------\n");
                // ask for username and password
                System.out.print("\nEnter your username: ");
                inp = cin.next();
                inp += cin.nextLine();
                String username = inp;
                System.out.print("Enter your password: ");
                inp = cin.next();
                inp += cin.nextLine();
                String password = inp;
                System.out.print("\n---------------------------\n");
                Staff staff = new Staff();
                if (staff.login(username, password)) {
                    System.out.print("\n-------- LOGGED IN --------");
                    System.out.print("\n\n---------------------------\n");
                    staff.printDetails();
                    System.out.print("\n---------------------------\n");
                    // staff menu
                } else {
                    System.out.print("Login failed!\nInvalid username or password.");
                }

                System.out.print("\n\n---------------------------");
                break;
            case 2:
                // Room Details
                System.out.println("\n\n---------------------------");
                Hotel.printRoomDetails();
                System.out.println("\n\n---------------------------");

                break;
            case 3:
                // Book a Room
                System.out.print("\n\n---------------------------");
                Hotel.printRoomDetails();
                System.out.println("Room Number: ");
                int roomNum = cin.nextInt();
                if (!Hotel.roomsList.containsKey(roomNum)) {
                    System.out.println("N/A");
                    break;
                }
                if (!Hotel.roomsList.get(roomNum).isAvailable()) {
                    System.out.println("Room N/A");
                    break;
                }
                Guest guest = new Guest();
                guest.inputDetails();
                for (var entry : Hotel.guestsList.entrySet()) {
                    int tempID = entry.getValue().getId();
                    entry.getValue().setID(-1);
                    if (guest.equals(entry.getValue())) {
                        entry.getValue().addRoomNumber(roomNum);
                        entry.getValue().setID(tempID);
                        System.out.println("Room booked successfully!");
                        break;
                    }
                    entry.getValue().setID(tempID);
                }
                guest.addRoomNumber(roomNum);
                Hotel.roomsList.get(roomNum).book();
                guest.setID(Hotel.guestsList.lastEntry().getKey() + 1);
                Hotel.guestsList.put(guest.getId(), new Guest(guest));
                break;
            case 0:
                System.out.print("\n\nExiting...");
                done = true;
                System.out.print("\n\n---------------------------");
                break;
            case 4:
                System.out.print("\n\n---------------------------");
                System.out.print("\n\nCheck-Out");
                System.out.print("\n---------------------------\n");
                System.out.print("\nEnter your Phone No. : ");
                inp = cin.next();
                inp += cin.nextLine();
                String phoneNo = inp;
                System.out.print("\n---------------------------\n");
                for (var entry : Hotel.guestsList.entrySet()) {
                    if (entry.getValue().getMobileNumber().equals(phoneNo)) {
                        System.out.print("\n\nGuest Details");
                        System.out.print("\n---------------------------\n");
                        entry.getValue().printDetails();
                        System.out.print("\n---------------------------\n");
                        System.out.print("\nEnter the Room Number: ");
                        inp = cin.next();
                        inp += cin.nextLine();
                        int roomNum2 = Integer.parseInt(inp);
                        if ((!Hotel.roomsList.containsKey(roomNum2))) {
                            System.out.println("N/A");
                            break;
                        }
                        if (Hotel.roomsList.get(roomNum2).isAvailable()) {
                            System.out.println("Room not Booked");
                            break;
                        }
                        if (!entry.getValue().getRoomNumbers().contains(roomNum2)) {
                            System.out.println("This isn't a room Booked by you.");

                            break;
                        }
                        entry.getValue().removeRoomNumber(roomNum2);
                        Hotel.roomsList.get(roomNum2).checkOut();
                        System.out.println("Check-Out Successful!");
                        if (entry.getValue().getRoomNumbers().size() == 0) {
                            Hotel.guestsList.remove(entry.getKey());
                        }
                        Hotel.roomsList.get(roomNum2).generateBill();
                        break;
                    }
                }
                break;

            default:
                System.out.print("\n\nInvalid choice!");
                System.out.print("\n\n---------------------------");
                break;
            }

        }

        FileHandling.writeToCSV();
        return;
    }

}
package com.hms.persons;

import java.util.*;
import java.io.*;

abstract class Person {
    int id;
    String name;
    int age;
    char gender;
    String mobNumber;
    com.hms.Address add;
    String cat;

    Person() {
        id = -1;
    }

    public abstract void fillMap();

    public abstract void saveMap();

    public void addPerson(int minAge, int maxAge) {
        Scanner cin = new Scanner(System.in);
        String inp;
        System.out.print("\nEnter name:\n");
        inp = cin.nextLine();
        name = inp;
        System.out.print("\nEnter age:\n");
        boolean flag = true;
        while (flag) {
            inp = cin.nextLine();
            try {
                age = Integer.parseInt(inp);
                if (age <= 0)
                    System.out.print("Please enter a valid age:\n");
                else if (age < minAge) {
                    System.out.print("\nSorry, person should be atleast " + minAge
                            + " years old to be registered as a new " + cat + ".\n");
                    return;
                } else if (age > maxAge) {
                    System.out.print(
                            "\nSorry, we can't register a person older than " + maxAge + " years as a " + cat + ".\n");
                    return;
                } else
                    flag = false;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid integer age:\n");
            }
        }
        System.out.print("\nGender (M = Male || F = Female):\n");
        inp = "X";
        while (inp != "M" && inp != "F") {
            inp = cin.nextLine();
            gender = inp.charAt(0);
            if (inp != "M" && inp != "F")
                System.out.print("M or F?\n");
        }
        System.out.print("\nEnter mobile number (with country code):\n");
        inp = cin.nextLine();
        mobNumber = inp;
        add.takeInput();
        return;
    }

    public void printDetails() {
        if (id == -1)
            return;
        System.out.print("\nDetails:\n");
        System.out.print("ID              :" + id + "\n");
        System.out.print("Name            :" + name + "\n");
        System.out.print("Gender          :" + gender + "\n");
        System.out.print("Age             :" + age + "\n");
        System.out.print("Mobile          :" + mobNumber + "\n");
        System.out.print("Address         :\n");
        add.print();
        return;
    }

    public void printDetailsFromHistory() {
        if (id == -1)
            return;
        System.out.print("\nHistory Details:\n");
        System.out.print("Name            :" + name + "\n");
        System.out.print("Gender          :" + gender + "\n");
        System.out.print("Age             :" + age + "\n");
        System.out.print("Mobile          :" + mobNumber + "\n");
        System.out.print("Address         :\n");
        add.print();
        return;
    }

    public abstract void getDetails(int id);

    public abstract void getDetailsFromHistory();
}
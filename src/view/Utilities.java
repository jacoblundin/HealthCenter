package view;

import java.util.Scanner;

public class Utilities
{
    public static int getInteger(int lowLimit, int upperLimit) {
        int number = 0;
        boolean goodNumber = false;
        try {
            Scanner reader = new Scanner(System.in);
            do {
                //System.out.println();
                number = reader.nextInt();

                goodNumber = (number >= lowLimit) && (number <= upperLimit);
                if (!goodNumber)
                    System.out.println("Invalid number. Please try again!");

            } while (!goodNumber);
        }catch(Exception e){

        }
        return number;
    }
    public static double getDouble() {
        double number = 0;
        boolean goodNumber = false;
        try {
            Scanner reader = new Scanner(System.in);
            do {
                //System.out.println();

                number = reader.nextDouble();

                goodNumber = (number >= 0);
                if (!goodNumber)
                    System.out.println("Cannot be negative. Please try again!");

            } while (!goodNumber);
        }catch(Exception e){

        }
        return number;
    }

    public static String getString() {
        Scanner reader = new Scanner(System.in);
        boolean done = true;
        String str = "";

        do
        {
            done = reader.hasNextLine();

            if (done)
            {
                str = reader.nextLine();
                //delete spaces from the ends of the string
                str = str.trim();

                done = !(str.isEmpty() || str.isBlank());
            }
            else
                System.out.println("Invalid format Please try again!");

        }while (!done);
        return str;
    }
    public static String getDate() {
        Scanner reader = new Scanner(System.in);
        boolean done = true;
        String str = "";

        do
        {
            done = reader.hasNextLine();

            if (done)
            {
                str = reader.nextLine();
                //delete spaces from the ends of the string
                str = str.trim();
                done = !(str.length()!=8 || str.isEmpty() || str.isBlank());
            }
            else
                System.out.println("Invalid format Please try again!");

        }while (!done);
        return str;
    }

    public static String getTime() {

        Scanner reader = new Scanner(System.in);
        boolean done = true;
        String str = "";

        do
        {
            done = reader.hasNextLine();

            if (done)
            {
                str = reader.nextLine();
                //delete spaces from the ends of the string
                str = str.trim();
                done = !(str.isEmpty() || str.isBlank());
            }
            else
                System.out.println("Invalid format Please try again!");

        }while (!done);
        str = str.substring(0,2) + ":" + str.substring(2, str.length());
        System.out.println(str);
        return str;
    }

}

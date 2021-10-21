import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {
        String[] arrOT = originalAOT();
        showOptions();
        optionChoose(arrOT);
    }

    public static void showOptions() {
        System.out.println();
        System.out.println(ConsoleColors.BLUE + "Select an option please");
        String[] options = {"add", "remove", "list", "exit"};
        System.out.print(ConsoleColors.RESET);
        for (int i = 0; i < options.length; i++) {
            System.out.println(options[i]);
        }
    }

    public static long linesOfFile() {
        Path csvpath = Paths.get("src/tasks.csv");
        long lines = 0;
        try {
            lines = Files.lines(csvpath).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void optionChoose(String[] arr) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        switch (input) {
            case "add":
                optionAdd(arr);
                break;
            case "remove":
                optionRemove(arr);
                showOptions();
                optionChoose(arr);
                break;
            case "list":
                optionList(arr);
                showOptions();
                optionChoose(arr);
            case "exit":
                System.out.println(ConsoleColors.RED + "Bye bye!" + ConsoleColors.RESET);
                writeToFile(arr);
                break;
            default:
                System.out.println("Give me a correct input.");
                optionChoose(arr);
        }
    }

    public static void optionRemove(String[] arr) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Tell me which task you want to remove.");
            int index = scan.nextInt() - 1;
            String[] temp = ArrayUtils.remove(arr, index);
            arr = temp;
            System.out.println("Option removed.");
            System.out.println();
            showOptions();
            optionChoose(arr);
        } catch (InputMismatchException e) {
            System.out.println("I need to know which element should I remove! Type again!");
            optionRemove(arr);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no task with such a number! Type again!");
            optionRemove(arr);
        }
    }

    public static void optionList(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(i + 1 + " : " + arr[i].replace(",", "  "));
        }
    }

    public static void optionAdd(String[] arr) {
        String[] temp = Arrays.copyOf(arr, arr.length + 1);
        StringBuilder newEntry = new StringBuilder();
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Please add task description.");
            String description = scan.nextLine();
            newEntry.append(description + ", ");
            System.out.println("Please add task due date. rrrr-mm-dd");
            String date = scan.nextLine();
            newEntry.append(date + ", ");
            System.out.println("Is this task important? true/false");
            boolean important = scan.nextBoolean();
            newEntry.append(String.valueOf(important));
            String newEntryStr = newEntry.toString();
            temp[temp.length - 1] = newEntryStr;
            arr = temp;
            System.out.println("Option added.");
            System.out.println();
            showOptions();
            optionChoose(arr);

        } catch (InputMismatchException e) {
            System.out.println("You need to give me correct data! Type everything again.");
            optionAdd(arr);
        }
    }

    public static String[] originalAOT() {
        long lines = linesOfFile();
        int linie = (int) lines;
        String[] csvarr = new String[linie];
        Path csvpath = Paths.get("src/tasks.csv");
        try {
            for (int i = 0; i < csvarr.length; i++) {
                csvarr[i] = Files.readAllLines(csvpath).get(i);
            }
        } catch (IOException e) {
            System.out.println("Cos jest nie tak " + e.getMessage());
        }

        return csvarr;
    }

    public static void writeToFile(String[] arr) {
        try {
            PrintWriter writer = new PrintWriter("src/tasks.csv");
            writer.print("");
            //Path csvpath = Paths.get("src/tasks.csv");
            for (int i = 0; i < arr.length; i++) {
                writer.println(arr[i]);
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Cos jest nie tak " + e.getMessage());
        }

    }


}

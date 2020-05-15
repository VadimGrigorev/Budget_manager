package main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Purchase> purchases = new ArrayList<>();
    private static double balance = 0;
    private static String fileName = "purchases.txt";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            showMenu();

            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                addIncome();
            }
            else if (choice == 2) {
                addPurchase();
            }
            else if (choice == 3) {
                showPurchases();
            }
            else if (choice == 4) {
                showBalance();
            }
            else if (choice == 5){
                save();
            }
            else if (choice == 6){
                load();
            }
            else if (choice == 7){
                sort();
            }
            else if (choice == 0) {
                System.out.println("\nBye!");
                break;
            }
        }
    }

    private static void sort() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("\nHow do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back\n");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                if(!purchases.isEmpty()){
                    System.out.println("All:");
                    Collections.sort(purchases);
                    double total = 0;
                    for(Purchase p : purchases){
                        System.out.println(p);
                        total += p.getCost();
                    }
                    System.out.println("total: $" + total);
                }
                else{
                    System.out.println("\n" +
                            "Purchase list is empty!");
                }

            } else if (choice == 2) {

                    List<Type> types = new ArrayList<>();
                    types.add(new Type("Food"));
                    types.add(new Type("Entertainment"));
                    types.add(new Type("Clothes"));
                    types.add(new Type("Other"));
                    double total = 0;

                    for(Purchase p : purchases){
                        total += p.getCost();
                        for(Type type : types){
                            if(p.getType().equals(type.getType())){
                                double currentSum = type.getSum();
                                type.setSum(currentSum += p.getCost());
                            }
                        }
                    }
                    Collections.sort(types);
                    Collections.reverse(types);
                    System.out.println("");
                    for(Type type : types){
                        System.out.println(type);
                    }
                    System.out.printf("Total sum: $%.2f\n",total);
            } else if (choice == 3) {
                System.out.println("\nChoose the type of purchase\n" +
                        "1) Food\n" +
                        "2) Clothes\n" +
                        "3) Entertainment\n" +
                        "4) Other");
                int chosenType = Integer.parseInt(scanner.nextLine());
                System.out.println("");
                String[] types = {"Food", "Clothes", "Entertainment", "Other"};
                String type = types[chosenType-1];
                List<Purchase> purchasesOfChosenType = new ArrayList<>();
                for(Purchase purchase : purchases){
                    if(purchase.getType().equals(type)){
                        purchasesOfChosenType.add(purchase);
                    }
                }

                if(!purchasesOfChosenType.isEmpty()) {
                    Collections.sort(purchasesOfChosenType);
                    double total = 0;
                    System.out.println("\n" + type + ":");
                    for (Purchase purchase : purchasesOfChosenType) {
                        total += purchase.getCost();
                        System.out.println(purchase);
                    }
                    System.out.printf("Total sum: $%.2f\n", total);
                }
                else{
                    System.out.println("Purchase list is empty!");
                }

            } else if (choice == 4) {
                System.out.println("");
                break;
            }
        }
    }

    private static void load() throws IOException{
        File file = new File(fileName);

        List<String> lines = Files.readAllLines(file.toPath());
        for(String s : lines){
            if(s.matches("(?i)[a-z0-9 '\\$-]+ !\\$[0-9\\.]+ !\\$[a-z]+")) {
                String[] line = s.split(" !\\$");
                purchases.add(new Purchase(line[0], Double.parseDouble(line[1]), line[2]));
            }
            else{
                balance = Double.parseDouble(s);
            }
        }

        System.out.println("\nPurchases were loaded!\n");
    }

    private static void save() throws IOException {
        File file = new File(fileName);
        boolean created = file.createNewFile();

        List<String> lines = new ArrayList<>();
        for(Purchase p : purchases){
            lines.add(p.saveToFileFormat());
        }
        lines.add(Double.toString(balance));

        Files.write(file.toPath(), lines, StandardCharsets.UTF_8);

        System.out.println("\nPurchases were saved!\n");
    }

    private static void addPurchase(){
        Scanner scanner = new Scanner(System.in);

        while(true) {
            Purchase purchase = new Purchase();
            System.out.println("\nChoose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) Back");
            String[] types = {"Food", "Clothes", "Entertainment", "Other"};
            int choice = Integer.parseInt(scanner.nextLine());
            if(choice == 5){
                break;
            }

            System.out.println("\nEnter purchase name:");
            purchase.setName(scanner.nextLine());
            System.out.println("Enter its price:");
            purchase.setCost(Double.parseDouble(scanner.nextLine()));
            purchase.setType(types[choice-1]);
            balance -= purchase.getCost();

            purchases.add(purchase);
            System.out.println("Purchase was added!");
        }
    }

    private static void addIncome(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter income:");
        balance += Double.parseDouble(scanner.nextLine());
        System.out.println("Income was added!\n");
    }

    private static void showPurchases(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("\nChoose the type of purchases\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back");
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.print("\n");
            String[] types = {"Food", "Clothes", "Entertainment", "Other", "All"};
            List<Purchase> resultList = new ArrayList<>();
            if(choice == 6){
                break;
            }
            else if(choice == 5){
                resultList = purchases;
            }
            for(Purchase p : purchases){
                if(p.getType().equals(types[choice-1])){
                    resultList.add(p);
                }
            }
            System.out.println("\n"+ types[choice-1] + ":");
            if(resultList.isEmpty()){
                System.out.println("Purchase list is empty!");
            }
            else {
                double total = 0;
                for (Purchase p : resultList) {
                    total += p.getCost();
                    System.out.println(p);
                }

                System.out.printf("Total sum: $%.2f\n", total);
            }
        }
    }

    private static void showBalance(){
        System.out.println("\nBalance: $" + balance + "\n");
    }

    private static void showMenu(){
        System.out.println("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (Sort)\n" +
                "0) Exit");
    }
}

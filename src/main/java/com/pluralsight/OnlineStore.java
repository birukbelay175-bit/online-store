package com.pluralsight;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
public class OnlineStore {
    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("src/main/resources/products.csv"));

            String line = reader.readLine();
            // skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String sku = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                String department = parts[3];

                Product product = new Product(sku, name, price, department);
                products.add(product);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading file");
        }
        for (Product p : products) {
            System.out.println(p.getProductName());
        }
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Home Screen ---");
            System.out.println("1. Display Products");
            System.out.println("2. Display Cart");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine(); // clear enter

                    System.out.print("Search by name or department, or press Enter to show all: ");
                    String search = scanner.nextLine();

                    System.out.println("\n--- Product List ---");

                    for (Product p : products) {
                        if (search.isEmpty() ||
                                p.getProductName().toLowerCase().contains(search.toLowerCase()) ||
                                p.getDepartment().toLowerCase().contains(search.toLowerCase())) {

                            System.out.println(
                                    "SKU: " + p.getSku() +
                                            " | Name: " + p.getProductName() +
                                            " | Price: $" + p.getPrice() +
                                            " | Dept: " + p.getDepartment()
                            );
                        }
                    }

                    System.out.print("Enter SKU to add to cart (or 0 to go back): ");
                    String input = scanner.next();

                    if (!input.equals("0")) {
                        for (Product p : products) {
                            if (p.getSku().equalsIgnoreCase(input)) {
                                cart.add(p);
                                System.out.println("Added to cart!");
                                break;
                            }
                        }
                    }

                    break;
                case 2:
                    System.out.println("\n--- Your Cart ---");

                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty.");
                        break;
                    }

                    double total = 0;

                    for (Product p : cart) {
                        System.out.println(
                                p.getSku() + " | " +
                                        p.getProductName() + " | $" +
                                        p.getPrice()
                        );
                        total += p.getPrice();
                    }

                    System.out.println("Total: $" + total);
                    System.out.print("Type C to checkout or 0 to go back: ");
                    String choice2 = scanner.next();

                    if (choice2.equalsIgnoreCase("C")) {
                        cart.clear();
                        System.out.println("Thank you for your purchase!");
                    }

                    // 👇 NEW PART
                    System.out.print("Enter SKU to remove (or 0 to go back): ");
                    String removeInput = scanner.next();

                    if (!removeInput.equals("0")) {
                        boolean removed = false;

                        for (int i = 0; i < cart.size(); i++) {
                            if (cart.get(i).getSku().equalsIgnoreCase(removeInput)) {
                                cart.remove(i);
                                System.out.println("Item removed from cart!");
                                removed = true;
                                break;
                            }
                        }

                        if (!removed) {
                            System.out.println("Item not found in cart.");
                        }
                    }

                    break;
                case 3:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}

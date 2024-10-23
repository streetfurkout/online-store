package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static void main(String[] args) {
        // Initialize variables
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        // Load inventory from CSV file
        loadInventory("products.csv", inventory);


        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 3) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
        scanner.close(); // Close the scanner
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                String[] variables = line.split("\\|"); // Ensure the delimiter matches your CSV format
                if (variables.length == 3) {
                    String id = variables[0].trim();
                    String name = variables[1].trim();
                    double price = Double.parseDouble(variables[2].trim());
                    inventory.add(new Product(id, name, price));
                } else {
                    System.err.println("Invalid input format, please try again");
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading inventory: ");
        }
    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        while (true) {
            System.out.println("These are the available products:");
            for (Product product : inventory) {
                System.out.println(product);
            }
            System.out.println("     Selections");
            System.out.println("1. Search Product");
            System.out.println("2. Add to your cart");
            System.out.println("3. Go back to the Home Screen");
            String selection = scanner.nextLine();

            switch (selection) {
                case "1":
                    searchProduct(inventory, scanner);
                    break;
                case "2":
                    addProductToCart(inventory, cart, scanner);
                    break;
                case "3":
                    return; // Go back to the main menu
                default:
                    System.err.println("Invalid selection! Please try again.");
            }
        }
    }

    public static void searchProduct(ArrayList<Product> inventory, Scanner scanner) {
        System.out.println("Please enter a product name or ID:");
        String searchItem = scanner.nextLine().trim().toLowerCase();
        boolean found = false;

        for (Product product : inventory) {
            if (product.getId().toLowerCase().contains(searchItem) ||
                    product.getProduct().toLowerCase().contains(searchItem)) {
                System.out.println(product);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nothing matched with the inventory.");
        }
    }

    public static void addProductToCart(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        System.out.println("Please enter a product ID to add to your cart (or type 'Go to previous menu' to go back):");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("Go to previous menu")) {
            return; // Go back to the previous menu
        }

        Product selectedProduct = findProductById(input, inventory);
        if (selectedProduct != null) {
            cart.add(selectedProduct);
            System.out.println(selectedProduct.getProduct() + " has been added to your cart.");
        } else {
            System.out.println("Product ID couldn't be found. Please try again!");
        }
    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {
        for (Product product : inventory) {
            if (product.getId().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null; // Return null if not found
    }

    public static void displayCart(ArrayList<Product> cart) {
        if (cart.isEmpty()){
            System.out.println("Your cart is empty please add at least 1 item.");
            return;
        }
        double totalAmount = 0; // Initialize totalAmount here
        System.out.println("Items in your cart:");
        for (Product product : cart) {
            System.out.println(product);
            totalAmount += product.getPrice(); // Assuming getPrice() returns product price
        }
        System.out.printf("Total Amount: $%.2f%n", totalAmount);

        System.out.println("Please Choose One of Them:");
        System.out.println("1. Check Out");
        System.out.println("2. Remove an item from the cart");
        System.out.println("Go back to the home screen");


        String option = scanner.nextLine().trim();
            switch (option) {
                case "1":
                    checkout(totalAmount, cart);
                    break;
                case "2":
                    removeProductFromCart(cart,scanner);
                    break;
                case "3":
                    return; //to go back to the home screen!!!
                default:
                    System.err.println("Please enter a valid selection! Now going to the main menu");
                    break;
            }
    }
    
}

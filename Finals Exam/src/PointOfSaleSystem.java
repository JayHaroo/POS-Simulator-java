/* Create a Point of Sale System using Data structures
 * 
 * Submitted By:
 * Leader: Saturno , M-Jey
 * Arandia , Amira Ashxena
 * Gotengco , Marione Nicole
 * Mendoza , Allyzza
 * Tapas , John Ren D. 
 * 
 * From 2CS-A
 */
import java.util.Scanner;

public class PointOfSaleSystem {
private static final int MAX_ITEMS = 100; // Maximum number of items in the inventory
private static final int MAX_TRANSACTIONS = 100; // Maximum number of recorded transactions
private static final int ITEM_FIELDS = 4; // Number of fields for each item (ID, description, price, stock)
private static final int TRANSACTION_FIELDS = 3; // Number of fields for each transaction (ID, itemID, quantity)
private static String[][] inventory = new String[MAX_ITEMS][ITEM_FIELDS]; // Inventory array
private static int[][] transactions = new int[MAX_TRANSACTIONS][TRANSACTION_FIELDS]; // Transactions array
private static int itemCount = 0; // Number of items in the inventory
private static int transactionCount = 0; // Number of recorded transactions
private static int lastItemID = 0; // ID of the last item in the inventory

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    
    /* This part of code represents a menu-driven Point of Sale and Inventory System 
     * that continuously prompts the user to select an option (1, 2, 3, or 4) 
     * and performs the corresponding functionality based on their choice until 
     * the program is manually exited.
     */
    
    while (true) {
        System.out.println("=== Point of Sale and Inventory System ===");
        System.out.println("1. Inventory Module");
        System.out.println("2. Sales Module");
        System.out.println("3. Generate Report");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                inventoryModule(scanner);
                break;
            case 2:
                salesModule(scanner);
                break;
            case 3:
                generateReport();
                break;
            case 4:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}

/* This code defines a static method called inventoryModule that takes a Scanner object as input.
 * It creates a menu-based inventory module where the user can choose options such as displaying 
 * the inventory, adding a new item, replenishing stock, or going back to the main menu. The user's 
 * choice is read from the scanner, and based on the choice, the corresponding method is called. 
 * The module continues to loop until the user chooses to go back to the main menu.
 */
private static void inventoryModule(Scanner scanner) {
    while (true) {
        System.out.println("=== Inventory Module ===");
        System.out.println("1. Display");
        System.out.println("2. Add New Item");
        System.out.println("3. Replenish Stock");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                displayInventory();
                break;
            case 2:
                addItem(scanner);
                break;
            case 3:
                replenishStock(scanner);
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}

/* This code defines a private static method called "salesModule" that takes a Scanner object as input. 
 * It creates a loop that displays a sales menu with three options: transact order, display inventory,
 *  or go back to the main menu. The user's choice is obtained from the scanner, and based on the choice, 
 *  it either calls other methods, returns from the sales module, or displays an error message for an 
 *  invalid choice.
 */
private static void salesModule(Scanner scanner) {
    while (true) {
        System.out.println("=== Sales Module ===");
        System.out.println("1. Transact Order");
        System.out.println("2. Display");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                transactOrder(scanner);
                break;
            case 2:
                displayInventory();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}

/* is a method called "generateReport" that generates a sales report. 
 * It iterates over a list of transactions and calculates the subtotal for each transaction 
 * by multiplying the price of an item with its quantity. The total sales are calculated by 
 * summing up all the subtotals, and the report is printed to the console, including transaction IDs, 
 * item descriptions, quantities, subtotals, and the total sales amount.
 */
private static void generateReport() {
    System.out.println("=== Sales Report ===");
    double totalSales = 0.0;

    for (int i = 0; i < transactionCount; i++) {
        int transactionID = transactions[i][0];
        int itemID = transactions[i][1];
        int quantity = transactions[i][2];

        double price = Integer.parseInt(inventory[itemID - 1][2]);
        double subtotal = price * quantity;
        totalSales += subtotal;

        System.out.println("Transaction ID: " + transactionID);
        System.out.println(getItemDescription(itemID) + " x " + quantity + "          " + subtotal);
    }

    System.out.println("Total Sales: " + totalSales);
    System.out.println();
}

/* The "displayInventory" method is a private static method that is responsible for printing the 
 * inventory information in a formatted manner. It starts by printing a header line indicating that 
 * it is displaying the inventory. Then, it iterates over each item in the inventory array, extracting 
 * the item ID, description, price, and stock information, and prints them in a formatted table-like 
 * structure. Finally, it adds an empty line for visual separation.
 */
private static void displayInventory() {
    System.out.println("=== Inventory ===");
    System.out.printf("%-8s%-30s%-15s%-8s%n", "Item ID", "Description", "Price", "Stock");

    for (int i = 0; i < itemCount; i++) {
        int itemID = Integer.parseInt(inventory[i][0]);
        String description = inventory[i][1];
        int price = Integer.parseInt(inventory[i][2]);
        int stock = Integer.parseInt(inventory[i][3]);

        System.out.printf("%-8d%-30s%-15d%-8d%n", itemID, description, price, stock);
    }

    System.out.println();
}

/* The addItem method is a private static method that adds a new item to an inventory system. 
 * It takes a Scanner object as a parameter to read user input. The method prompts the user to 
 * enter the description, price, and initial stock of the new item. It then increments the last 
 * item ID, stores the item details in a multi-dimensional array called inventory, and updates the 
 * item count. Finally, it displays a success message indicating that the new item has been added.
 */
private static void addItem(Scanner scanner) {
    scanner.nextLine(); // Consume the newline character

    System.out.println("=== Add New Item ===");
    System.out.print("Enter description of the new item: ");
    String description = scanner.nextLine();
    System.out.print("Enter price of the new item: ");
    String price = scanner.nextLine();
    System.out.print("Enter initial stock: ");
    String stock = scanner.nextLine();

    lastItemID++;
    inventory[itemCount][0] = String.valueOf(lastItemID);
    inventory[itemCount][1] = description;
    inventory[itemCount][2] = price;
    inventory[itemCount][3] = stock;
    itemCount++;

    System.out.println("The new item has been added successfully!\n");
}

/* The method replenishStock is a private static function that allows the user to replenish the stock 
 * of an item in an inventory system. It prompts the user to select an item by its ID, checks if the item
 * exists, asks for the number of stocks to be added, and updates the stock quantity of the selected item
 * accordingly. Finally, it displays a success message.
 */
private static void replenishStock(Scanner scanner) {
    System.out.println("=== Replenish Stock ===");
    displayInventory();

    System.out.print("Select an item by entering its ID: ");
    int itemID = scanner.nextInt();
    int itemIndex = findItemIndex(itemID);

    if (itemIndex == -1) {
        System.out.println("Item not found.\n");
        return;
    }

    System.out.print("Number of stocks to be added: ");
    int quantity = scanner.nextInt();

    int currentStock = Integer.parseInt(inventory[itemIndex][3]);
    int newStock = currentStock + quantity;
    inventory[itemIndex][3] = String.valueOf(newStock);

    System.out.println("Update successful!\n");
}

/* The method transactOrder is a private static void method that handles the process of transacting an order. 
 * It takes a Scanner object as a parameter for user input. The method starts by displaying the inventory
 * and incrementing the transaction ID. It then enters a loop where it prompts the user to enter an item ID
 * and quantity. It checks if the item is available in the inventory and if it has already been added to 
 * the current transaction. If the item is available and not already in the transaction, it updates the 
 * inventory stock, records the transaction details, calculates the subtotal, and increments the 
 * transaction count. After each item, it asks the user if they wish to add another item. If the user 
 * chooses not to add another item, the transaction is recorded and the loop breaks.
 */
private static void transactOrder(Scanner scanner) {
    scanner.nextLine(); // Consume the newline character

    System.out.println("=== Transact Order ===");
    displayInventory();

    int transactionID = transactionCount + 1;
    transactions[transactionCount][0] = transactionID;

    while (true) {
        System.out.print("Enter Item ID: ");
        int itemID = scanner.nextInt();
        int itemIndex = findItemIndex(itemID);

        if (itemIndex == -1) {
            System.out.println("The item is not in the inventory. Please select another item.\n");
            continue;
        }

        if (isItemInTransaction(transactionID, itemID)) {
            System.out.println("The item is already in the list. Please select another item.\n");
            continue;
        }

        System.out.print("How many " + getItemDescription(itemID) + "? ");
        int quantity = scanner.nextInt();

        int availableStock = Integer.parseInt(inventory[itemIndex][3]);
        if (quantity > availableStock) {
            System.out.println("Insufficient stock. Available stock: " + availableStock + "\n");
            continue;
        }

        int currentStock = Integer.parseInt(inventory[itemIndex][3]);
        int newStock = currentStock - quantity;
        inventory[itemIndex][3] = String.valueOf(newStock);

        transactions[transactionCount][1] = itemID;
        transactions[transactionCount][2] = quantity;

        int price = Integer.parseInt(inventory[itemIndex][2]);
        int subtotal = price * quantity;

        System.out.println("Subtotal: " + subtotal);
        transactionCount++;

        System.out.print("Do you wish to add another item? (y/n) ");
        scanner.nextLine(); // Consume the newline character
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("n")) {
            System.out.println("Transaction recorded!\n");
            break;
        }
    }
}

/* The method findItemIndex, searches for the index of an item in an inventory array by 
 * iterating over the items and comparing their IDs with the given itemID. It returns the index if found, 
 * or -1 if the item is not found.
 */
private static int findItemIndex(int itemID) {
    for (int i = 0; i < itemCount; i++) {
        if (Integer.parseInt(inventory[i][0]) == itemID) {
            return i;
        }
    }
    return -1;
}

/* The method, getItemDescription, utilizes the findItemIndex method to retrieve the description of an 
 * item from the inventory array. If the item is found (i.e., the itemIndex is not -1), it returns the 
 * description; otherwise, it returns "N/A".
 */
private static String getItemDescription(int itemID) {
    int itemIndex = findItemIndex(itemID);
    if (itemIndex != -1) {
        return inventory[itemIndex][1];
    }
    return "N/A";
}

/* The method, isItemInTransaction, checks if a specific item is present in a transaction by iterating 
 * over the transactions and comparing the transactionID and itemID with the given parameters. It returns 
 * true if the item is found in the transaction, or false if it is not found.
 */
private static boolean isItemInTransaction(int transactionID, int itemID) {
    for (int i = 0; i < transactionCount; i++) {
        if (transactions[i][0] == transactionID && transactions[i][1] == itemID) {
            return true;
        }
    }
    return false;
}
}
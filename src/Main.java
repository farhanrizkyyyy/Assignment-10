import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static private final WarehouseController controller = new WarehouseController(); // controller object initialization with WarehouseController data type
    final static private String[] menu = {"Show Items", "Manage Category", "Manage Items", "Show Logs"}; // list of app primary menu
    final static private String[] submenu = {"Add", "Remove", "Update", "Find"};
    final static private String[] updateItemSubmenu = {"Update Item Category", "Update Item Name", "Update Item Quantity"};
    final static private Scanner scanner = new Scanner(System.in); // scanner object initialization

    public static void main(String[] args) {
        controller.addDummies();
        displayMenu();
    }

    private static void displayMenu() {
        while (true) {
            for (int i = 0; i < menu.length; i++) { // display all menu as list
                System.out.println(i + 1 + ". " + menu[i]);
            }
            System.out.println("\n0. Exit");
            try {
                System.out.print("Select menu: ");
                int selectedMenu = scanner.nextInt(); // input menu
                scanner.nextLine(); // clear scanner buffer
                System.out.println();
                menuNavigation(selectedMenu);
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number, please try again.\n"); // print error message if input not a number
                scanner.next(); // clear scanner buffer from invalid input
            }
        }
    }

    private static void displaySubmenu(boolean isItem) {
        String menuType = "Category"; // set default value to menuType to show it at the menu
        if (isItem) menuType = "Item"; // change menuType value if selected menu is for managing items
        while (true) {
            System.out.println("========== " + menuType.toUpperCase() + " MENU ==========");
            for (int i = 0; i < submenu.length; i++) { // display submenu as a list
                System.out.println(i + 1 + ". " + submenu[i]);
            }
            System.out.println("\n0. Back");
            System.out.print("Select menu: ");
            try {
                int selectedSubmenu = scanner.nextInt(); // input selected submenu
                scanner.nextLine(); // clear scanner buffer
                System.out.println();
                submenuNavigation(selectedSubmenu, isItem);
            } catch (Exception e) {
                System.out.println("Input must be a number, please try again.\n"); // print error message if given input is not a number
                scanner.next(); // clear scanner buffer from invalid input
            }
        }
    }

    private static void menuNavigation(int selectedMenu) {
        switch (selectedMenu) {
            case 1 -> { // show items
                if (!controller.isItemsEmpty()) controller.showItemsWithCategories(true);
                else System.out.println("There is no item in your inventory.\n");
            }
            case 2 -> displaySubmenu(false); // manage category
            case 3 -> displaySubmenu(true); // manage items
            case 4 -> { // show logs
                if (!controller.isLogsEmpty()) controller.showLogs();
                else System.out.println("There is no action recently on this app.\n");
            }
            case 0 -> displayExitConfirmation();
            default -> System.out.println("Menu not available, please input with given menu number.\n");
        }
    }

    private static void submenuNavigation(int selectedSubmenu, boolean isItem) {
        switch (selectedSubmenu) {
            case 1 -> displayAddMenu(isItem); // display add
            case 2 -> displayRemoveMenu(isItem);// display remove
            case 3 -> displayUpdateMenu(isItem);  // display update
            case 4 -> displayFindMenu(isItem); // display find
            case 0 -> displayMenu(); // back to primary menu
            default -> System.out.println("Menu not available, please input with given menu number.\n");
        }
    }

    private static void displayExitConfirmation() {
        char confirm;
        while (true) {
            System.out.print("Are you sure want to exit this app (Y/n)? ");
            try {
                confirm = scanner.next().charAt(0);
                if (confirm == 'Y' || confirm == 'y') {
                    System.exit(0);
                    scanner.close();
                } else if (confirm == 'N' || confirm == 'n') {
                    System.out.println();
                    displayMenu();
                } else {
                    System.out.println("Input not valid, please try again.");
                }
            } catch (Exception e) {
                System.out.println("Input not valid, please try again.");
                scanner.next();
            }
        }
    }

    private static void displayAddMenu(boolean isItem) {
        if (isItem) {
            if (controller.isCategoriesEmpty()) {
                System.out.println("You cannot add item with no category, please add category first."); // display this message if categories is empty
                displayAddCategoryMenu(); // display add category menu
                displayAddItemMenu(); // display add item menu
            } else {
                displayAddItemMenu(); // display add item menu
            }
        } else {
            displayAddCategoryMenu(); // display add category menu
        }
    }

    private static void displayAddItemMenu() {
        String itemName;
        int qty, categoryIndex;
        while (true) {
            controller.showCategories();
            System.out.println("\n0. Back");
            System.out.print("Select category for your item: ");
            try {
                categoryIndex = scanner.nextInt(); // select category
                if (categoryIndex == 0) {
                    System.out.println();
                    displaySubmenu(true);
                } else if (categoryIndex > 0 && categoryIndex <= controller.getCategoriesLength()) {
                    break; // exit loop if category index > 0 & <= categories length
                } else {
                    System.out.println("Input not valid, please select category with given number.\n"); // print this message if input is invalid
                }
            } catch (Exception e) {
                System.out.println("Input must be a number, please try again.\n"); // print this message if input is not a number
                scanner.next(); // clear scanner from invalid input
            }
        }

        scanner.nextLine(); // clear scanner from previous input

        while (true) {
            System.out.print("Insert item name: ");
            try {
                itemName = scanner.nextLine(); // insert item name
                if (!controller.isItemExist(categoryIndex, itemName))
                    break; // exit loop if items in inventory doesn't have same name with new item name
                else System.out.println("Item already exist."); // print this message if item already exist in inventory
            } catch (Exception e) {
                System.out.println("Input not valid, please try again."); // print this message if input is invalid
                scanner.next(); // clear scanner from invalid input
            }
        }

        while (true) {
            System.out.print("Insert item quantity: ");
            try {
                qty = scanner.nextInt(); // insert item qty
                if (qty > 1) break; // exit loop if qty is a positive number
                else
                    System.out.println("Item quantity must be positive, please try again."); // print this message if qty is 0 or negative number
            } catch (Exception e) {
                System.out.println("Input must be a number, please try again."); // print this message if qty is not a number
                scanner.next(); // clear scanner from invalid input
            }
        }

        String categoryName = controller.getCategoryByIndex(categoryIndex); // get category name by selected category index
        controller.addItem(categoryIndex, categoryName, itemName, qty); // add item to inventory
        System.out.println("Item successfully added!\n"); // print success message
    }

    private static void displayAddCategoryMenu() {
        String categoryName;

        System.out.print("Enter category name: ");
        categoryName = scanner.nextLine(); // input category name to add
        if (controller.isCategoryExist(categoryName)) {
            System.out.println("Category already exist.\n"); // print this message if category already exist in inventory
        } else {
            controller.addCategory(categoryName); // add new category to list
            System.out.println("Category successfully added!\n"); // print success message
        }
    }

    private static void displayRemoveMenu(boolean isItem) {
        if (isItem) {
            displayRemoveItemMenu();
        } else {
            if (!controller.isCategoriesEmpty()) displayRemoveCategoryMenu(); // display remove category menu
            else
                System.out.println("There is no category to be deleted.\n"); // print this message if categories is empty
        }
    }

    private static void displayRemoveItemMenu() {
        if (controller.isItemsEmpty()) {
            System.out.println("There is no item in your inventory.\n");
        } else {
            int selectedCategory, selectedItem;
            while (true) {
                controller.showItemsWithCategories(false); // display available categories with its item
                System.out.println("\n0. Back");
                System.out.print("Select category: ");
                try {
                    selectedCategory = scanner.nextInt(); // select category
                    if (selectedCategory == 0) {
                        System.out.println();
                        displaySubmenu(true);
                    } else if (selectedCategory > 0 && selectedCategory <= controller.getCategoriesLength()) {
                        if (controller.isItemsByCategoryEmpty(selectedCategory))
                            System.out.println("There is no item in this category.\n");
                        else break;
                    } else {
                        System.out.println("Input not valid, please select category with given number."); // print this message if input is invalid
                    }
                } catch (Exception e) {
                    System.out.println("Input must be a number, please try again."); // print this message if input is not a number
                    scanner.next(); // clear scanner from invalid input
                }
            }

            scanner.nextLine(); // clear scanner from previous input

            while (true) {
                controller.showItemsByCategory(selectedCategory); // display item filtered by selected category
                System.out.println("\n0. Back");
                System.out.print("Select item: ");
                try {
                    selectedItem = scanner.nextInt(); // input item
                    if (selectedItem == 0) {
                        System.out.println();
                        displaySubmenu(true);
                    } else if (selectedItem > 0 && selectedItem <= controller.getItemsLengthByCategory(selectedCategory))
                        break;
                    else
                        System.out.println("Input not valid, please select category with given number."); // print this message if input is invalid
                } catch (Exception e) {
                    System.out.println("Input must be a number, please try again."); // print this message if input is not a number
                    scanner.next(); // clear scanner from invalid input
                }
            }

            String itemName = controller.getItemByIndexes(selectedCategory, selectedItem).get(1); // get item name by given indexes
            controller.removeItem(selectedCategory, selectedItem); // remove item from inventory
            System.out.println(itemName + " successfully deleted!\n"); // display success message
        }
    }

    private static void displayRemoveCategoryMenu() {
        while (true) {
            controller.showCategories(); // display available categories
            System.out.println("\n0. Back");
            System.out.print("Select category to delete: ");
            try {
                int categoryIndex = scanner.nextInt(); // input category
                if (categoryIndex == 0) {
                    System.out.println();
                    displaySubmenu(false);
                } else if (categoryIndex > 0 && categoryIndex <= controller.getCategoriesLength()) {
                    if (!controller.isItemsByCategoryEmpty(categoryIndex)) {
                        while (true) {
                            System.out.print("There is items in this category, are you sure want to delete it (Y/n)? "); // print this message if there is item in selected category
                            try {
                                char confirmation = scanner.next().charAt(0); // input confirmation
                                if (confirmation == 'Y' || confirmation == 'y') {
                                    String deletedCategory = controller.getCategoryByIndex(categoryIndex); // get category name
                                    controller.removeCategory(categoryIndex); // remove category with its items from inventory
                                    System.out.println("Category " + deletedCategory + " successfully removed!\n"); // display success message
                                    break; // exit loop
                                } else if (confirmation == 'N' || confirmation == 'n') {
                                    System.out.println();
                                    displaySubmenu(false); // back to submenu
                                } else {
                                    System.out.println("Input not valid, please try again."); // print this message if input is invalid
                                }
                            } catch (Exception e) {
                                System.out.println("Input not valid, please try again."); // print this message if input is invalid
                                scanner.next(); // clear scanner from invalid input
                            }
                        }
                    } else {
                        String deletedCategory = controller.getCategoryByIndex(categoryIndex); // get category name
                        controller.removeCategory(categoryIndex); // remove selected category
                        System.out.println("Category " + deletedCategory + " successfully removed!\n"); // print success message
                    }
                    break;
                } else {
                    System.out.println("Input out of range, please try again.\n"); // print this message if input is invalid
                }
            } catch (Exception e) {
                System.out.println("Input must be a number, please try again.\n"); // print this message if input is not a number
                scanner.next(); // clear scanner from invalid input
            }
        }
    }

    private static void displayUpdateMenu(boolean isItem) {
        if (isItem) displayUpdateItemMenu();
        else displayUpdateCategoryMenu();
    }

    private static void displayUpdateItemMenu() {
        if (controller.isItemsEmpty()) {
            System.out.println("There is no item in your inventory.\n");
        } else {
            int selectedMenu;
            while (true) {
                System.out.println("========== UPDATE ITEM MENU ==========");
                for (int i = 0; i < updateItemSubmenu.length; i++) {
                    System.out.println(i + 1 + ". " + updateItemSubmenu[i]);
                }
                System.out.println("\n0. Back");
                System.out.print("Select menu: ");
                try {
                    selectedMenu = scanner.nextInt();
                    updateItemMenuNavigation(selectedMenu);
                } catch (Exception e) {
                    System.out.println("Input must be a number, please try again.\n");
                    scanner.next();
                }
            }
        }
    }

    private static void updateItemMenuNavigation(int selectedMenu) {
        switch (selectedMenu) {
            case 1 -> displayUpdateItemMenu("category"); // update item category
            case 2 -> displayUpdateItemMenu("name"); // update item name
            case 3 -> displayUpdateItemMenu("qty"); // update item qty
            case 0 -> { // back to submenu
                System.out.println();
                displaySubmenu(true);
            }
            default -> System.out.println("Menu not available, please input with given menu number.\n");
        }
    }

    private static void displayUpdateItemMenu(String type) {
        int selectedCategory, selectedItem, selectedNewCategory;
        while (true) {
            controller.showItemsWithCategories(false);
            System.out.println("\n0. Back");
            System.out.print("Select category: ");
            try {
                selectedCategory = scanner.nextInt();
                if (selectedCategory == 0) {
                    System.out.println();
                    displayUpdateItemMenu();
                } else if (selectedCategory > 0 && selectedCategory <= controller.getCategoriesLength()) break;
                else
                    System.out.println("Input not valid, please select category with given number.\n"); // print this message if input is invalid
            } catch (Exception e) {
                System.out.println("Input must be a number, please try again.\n"); // print this line if input from user not valid
                scanner.next();
            }
        }

        scanner.nextLine();

        while (true) {
            controller.showItemsByCategory(selectedCategory);
            System.out.println("\n0. Back");
            System.out.print("Select item: ");
            try {
                selectedItem = scanner.nextInt();
                if (selectedItem == 0) {
                    displayUpdateItemMenu();
                } else if (selectedItem > 0 && selectedItem <= controller.getItemsLengthByCategory(selectedCategory))
                    break;
                else
                    System.out.println("Input not valid, please select item with given number.\n"); // print this message if input is invalid
            } catch (Exception e) {
                System.out.println("Input must be a number, please try again.\n"); // print this line if input from user not valid
                scanner.next();
            }
        }

        scanner.nextLine();

        switch (type) {
            case "category" -> {
                while (true) {
                    controller.showCategories();
                    System.out.println("\n0. Back");
                    System.out.print("Select new category for the item: ");
                    try {
                        selectedNewCategory = scanner.nextInt();
                        if (selectedNewCategory == 0) {
                            System.out.println();
                            displayUpdateItemMenu();
                        } else if (selectedNewCategory > 0 && selectedNewCategory <= controller.getCategoriesLength())
                            break;
                        else
                            System.out.println("Input not valid, please select category with given number.\n"); // print this message if input is invalid
                    } catch (Exception e) {
                        System.out.println("Input must be a number, please try again.\n"); // print this line if input from user not valid
                        scanner.next();
                    }
                }
                String newCategoryName = controller.getCategoryByIndex(selectedNewCategory);
                String itemName = controller.getItemByIndexes(selectedCategory, selectedItem).get(1);
                controller.updateItemCategory(selectedCategory, selectedItem, selectedNewCategory);
                System.out.println(itemName + " successfully moved to " + newCategoryName + " category!\n");
            }
            case "name" -> {
                String newItemName;
                while (true) {
                    System.out.print("Insert new item name: ");
                    try {
                        newItemName = scanner.nextLine();
                        break;
                    } catch (Exception e) {
                        System.out.println("Input not valid, please try again.\n"); // print this line if input from user not valid
                        scanner.next();
                    }
                }
                controller.updateItemName(selectedCategory, selectedItem, newItemName);
            }
            case "qty" -> {
                int qtyToAdd;
                while (true) {
                    System.out.print("Insert quantity to add (+/-): ");
                    try {
                        qtyToAdd = scanner.nextInt();
                        break;
                    } catch (Exception e) {
                        System.out.println("Input must be a number, please try again.\n"); // print this line if input from user not valid
                        scanner.next();
                    }
                }
                controller.updateItemQty(selectedCategory, selectedItem, qtyToAdd);
            }
        }
    }

    private static void displayUpdateCategoryMenu() {
        if (controller.isCategoriesEmpty()) {
            System.out.println("There is no category in your inventory.\n");
        } else {
            while (true) {
                controller.showCategories();
                System.out.println("\n0. Back");
                System.out.print("Select category to update: ");
                try {
                    int categoryIndex = scanner.nextInt();
                    scanner.nextLine();
                    if (categoryIndex == 0) {
                        System.out.println();
                        displaySubmenu(false);
                    } else if (categoryIndex > 0 && categoryIndex <= controller.getCategoriesLength()) {
                        if (controller.isItemsByCategoryEmpty(categoryIndex)) {
                            while (true) {
                                System.out.print("Insert new category name: ");
                                try {
                                    String newCategoryName = scanner.nextLine();
                                    String oldCategoryName = controller.getCategoryByIndex(categoryIndex);
                                    controller.updateCategoryName(categoryIndex, newCategoryName);
                                    System.out.println("Category " + oldCategoryName + " successfully changed to " + newCategoryName + "!\n");
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Input not valid, please try again.\n"); // print this line if input from user not valid
                                    scanner.next();
                                }
                            }
                        } else {
                            while (true) {
                                System.out.print("There is items in this category, are you sure want to update it (Y/n)? ");
                                try {
                                    char confirmation = scanner.next().charAt(0);
                                    if (confirmation == 'Y' || confirmation == 'y') {
                                        scanner.nextLine();
                                        while (true) {
                                            System.out.print("Insert new category name: ");
                                            try {
                                                String newCategoryName = scanner.nextLine();
                                                String oldCategoryName = controller.getCategoryByIndex(categoryIndex);
                                                controller.updateCategoryName(categoryIndex, newCategoryName);
                                                System.out.println("Category " + oldCategoryName + " successfully changed to " + newCategoryName + "!\n");
                                                break;
                                            } catch (Exception e) {
                                                System.out.println("Input not valid, please try again.\n"); // print this line if input from user not valid
                                                scanner.next();
                                            }
                                        }
                                        break;
                                    } else if (confirmation == 'N' || confirmation == 'n') {
                                        System.out.println();
                                        displaySubmenu(false);
                                    } else {
                                        System.out.println("Input not valid, please try again.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Input not valid, please try again.");
                                    scanner.next();
                                }
                            }
                        }
                        break;
                    } else {
                        System.out.println("Input out of range, please try again.\n");
                    }
                } catch (Exception e) {
                    System.out.println("Input must be a number, please try again.\n");
                    scanner.next();
                }
            }
        }
    }

    private static void displayFindMenu(boolean isItem) {
        if (isItem) displayFindItemMenu();
        else displayFindCategoryMenu();
    }

    private static void displayFindItemMenu() {
        if (controller.isItemsEmpty()) {
            System.out.println("There is no item in your inventory.\n");
        } else {
            String itemKeyword;
            while (true) {
                System.out.print("Insert keyword: ");
                try {
                    itemKeyword = scanner.nextLine();
                    break;
                } catch (Exception e) {
                    System.out.println("Input not valid, please try again.\n"); // print this line if input from user not valid
                    scanner.next();
                }
            }
            controller.filterItemsByName(itemKeyword);
        }
    }

    private static void displayFindCategoryMenu() {
        if (controller.isCategoriesEmpty()) {
            System.out.println("There is no category in your inventory.\n");
        } else {
            String categoryKeyword;
            while (true) {
                System.out.print("Insert keyword: ");
                try {
                    categoryKeyword = scanner.nextLine();
                    break;
                } catch (Exception e) {
                    System.out.println("Input not valid, please try again.\n"); // print this line if input from user not valid
                    scanner.next();
                }
            }
            controller.filterCategoriesByName(categoryKeyword);
        }
    }
}

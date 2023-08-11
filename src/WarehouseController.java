import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WarehouseController {
    private final List<String> categories = new ArrayList<>();
    private final List<List<List<String>>> items = new ArrayList<>();
    private final List<String> logs = new ArrayList<>();
    private final String username = "Farhan";

    // ==================== CATEGORY SECTION ====================
    public boolean isCategoriesEmpty() {
        return this.categories.size() == 0; // return true if categories size is 0, otherwise false
    }

    public int getCategoriesLength() {
        return this.categories.size(); // return the size of categories
    }

    public void addCategory(String categoryName) {
        this.categories.add(categoryName); // add new value to categories
        this.items.add(new ArrayList<>()); // add new empty list to items when new category added
        addLog(categoryName + " category added by " + this.username); // record action & add it to logs
    }

    public String getCategoryByIndex(int index) {
        return this.categories.get(index - 1); // get category by given index
    }

    public void showCategories() {
        for (int i = 0; i < this.categories.size(); i++) {
            System.out.println(i + 1 + ". " + this.categories.get(i)); // display all category if categories not empty
        }
    }

    public void updateCategoryName(int index, String newCategoryName) {
        String oldCategoryName = this.categories.get(index - 1); // get category by given index
        this.categories.set(index - 1, newCategoryName); // update value on categories by given index
        addLog(oldCategoryName + " category was changed to " + newCategoryName + " by " + this.username); // record action & add it to logs
    }

    public void removeCategory(int index) {
        String categoryName = categories.get(index - 1); // get category name by given index
        this.categories.remove(index - 1); // remove value on categories by given index
        this.items.remove(index - 1); // remove all items by given index
        addLog(categoryName + " category was removed by " + this.username); // record action & add it to logs
    }

    public void filterCategoriesByName(String categoryKeyword) {
        ArrayList<String> filteredCategories = new ArrayList<>(); // create new list to store category that matches with given keyword

        for (String category : this.categories) {
            if (category.toLowerCase().contains(categoryKeyword.toLowerCase())) {
                filteredCategories.add(category); // add category to new list if category name matches with given keyword
            }
        }

        if (filteredCategories.size() == 0) {
            System.out.println("No result for keyword " + categoryKeyword + ".\n");
        } else {
            for (int i = 0; i < filteredCategories.size(); i++) {
                int categoryIndex = this.categories.indexOf(filteredCategories.get(i));
                System.out.println(i + 1 + ". " + filteredCategories.get(i)); // display list of filtered categories
                for (int j = 0; j < this.items.get(categoryIndex).size(); j++) {
                    System.out.println("   > " + this.items.get(categoryIndex).get(j).get(1) + " (" + this.items.get(categoryIndex).get(j).get(2) + "pcs)"); // display list of item by category
                }
            }
            System.out.println();
        }
    }

    public boolean isCategoryExist(String categoryName) {
        boolean exist = false;
        for (String category : this.categories) {
            if (category.toLowerCase().contains(categoryName.toLowerCase())) {
                exist = true;
                break;
            }
        }
        return exist;
    }
    // ==================== END CATEGORY SECTION ====================

    // ==================== ITEMS SECTION ====================
    public boolean isItemsEmpty() {
        return this.items.size() == 0; // return true if items size is 0, otherwise false
    }

    public int getItemsLengthByCategory(int categoryIndex) {
        return this.items.get(categoryIndex - 1).size();
    }

    public void showItemsWithCategories(boolean addEmptyLine) {
        for (int i = 0; i < this.categories.size(); i++) {
            System.out.println(i + 1 + ". " + this.categories.get(i)); // display category name
            for (int j = 0; j < this.items.get(i).size(); j++) {
                System.out.println("   > " + this.items.get(i).get(j).get(1) + " (" + this.items.get(i).get(j).get(2) + "pcs)"); // display list of item by category
            }
        }
        if (addEmptyLine) System.out.println(); // print empty line after all items shown
    }

    public void filterItemsByName(String keyword) {
        List<List<String>> allItems = new ArrayList<>();
        List<List<String>> filteredItems = new ArrayList<>();

        for (List<List<String>> item : this.items) {
            allItems.addAll(item);
        }

        for (List<String> item : allItems) {
            if (item.get(1).toLowerCase().contains(keyword.toLowerCase())) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.size() == 0) {
            System.out.println("No result for keyword " + keyword + ".\n");
        } else {
            for (int i = 0; i < filteredItems.size(); i++) {
                System.out.println(i + 1 + ". (" + filteredItems.get(i).get(0) + ") " + filteredItems.get(i).get(1) + " - " + filteredItems.get(i).get(2) + "pcs");
            }
            System.out.println();
        }
    }

    public void showItemsByCategory(int categoryIndex) {
        for (int j = 0; j < this.items.get(categoryIndex - 1).size(); j++) {
            System.out.println(j + 1 + ". " + this.items.get(categoryIndex - 1).get(j).get(1) + " - " + this.items.get(categoryIndex - 1).get(j).get(2) + "pcs"); // display list of item by given category index
        }
    }

    public boolean isItemExist(int categoryIndex, String itemName) {
        boolean exist = false; // initialize exist variable with false

        for (int i = 0; i < this.items.get(categoryIndex - 1).size(); i++) {
            if (this.items.get(categoryIndex - 1).get(i).get(1).equalsIgnoreCase(itemName)) {
                exist = true; // exist turns true if given itemName == item name that already in the list
                break; // exit loop if exist turns true
            }
        }

        return exist; // return exist value
    }

    public List<String> getItemByIndexes(int categoryIndex, int itemIndex) {
        return this.items.get(categoryIndex - 1).get(itemIndex - 1); // return item by given categoryIndex & itemIndex
    }

    public boolean isItemsByCategoryEmpty(int categoryIndex) {
        return items.get(categoryIndex - 1).size() == 0;
    }

    public void removeItem(int categoryIndex, int itemIndex) {
        String categoryName = items.get(categoryIndex - 1).get(itemIndex - 1).get(0); // get category name
        String itemName = items.get(categoryIndex - 1).get(itemIndex - 1).get(1); // get item name
        this.items.get(categoryIndex - 1).remove(itemIndex - 1); // remove item from items
        addLog(itemName + " on category " + categoryName + " was removed by " + this.username); // record action & add it to logs
    }

    public void addItem(int categoryIndex, String categoryName, String itemName, int qty) {
        List<String> item = Arrays.asList(categoryName, itemName, Integer.toString(qty)); // add item's properties into a new list
        this.items.get(categoryIndex - 1).add(item); // add item into items
        addLog(itemName + " was added to category " + categoryName + " with " + qty + " quantity by " + this.username); // record action & add it to logs
    }

    public void updateItemCategory(int oldCategoryIndex, int itemIndex, int newCategoryIndex) {
        List<String> item = this.items.get(oldCategoryIndex - 1).get(itemIndex - 1); // assign item to new variable
        String oldCategoryName = item.get(0);
        String newCategoryName = this.categories.get(newCategoryIndex - 1);
        this.items.get(oldCategoryIndex - 1).remove(itemIndex - 1); // remove item from old category index
        this.items.get(newCategoryIndex - 1).add(item); // add item to new category index
        addLog(item.get(1) + " was moved from " + oldCategoryName + " to " + newCategoryName + " by " + this.username);
    }

    public void updateItemName(int categoryIndex, int itemIndex, String newItemName) {
        String itemName = this.items.get(categoryIndex - 1).get(itemIndex - 1).get(1);
        if (isItemExist(categoryIndex, newItemName)) {
            System.out.println("Item already exist.\n");
        } else {
            this.items.get(categoryIndex - 1).get(itemIndex - 1).set(1, newItemName);
            addLog(itemName + " was renamed to " + newItemName + " by " + this.username);
            System.out.println(itemName + " successfully renamed to " + newItemName + "!\n");
        }
    }

    public void updateItemQty(int categoryIndex, int itemIndex, int qtyToAdd) {
        String itemName = this.items.get(categoryIndex - 1).get(itemIndex - 1).get(1);
        int qty = Integer.parseInt(this.items.get(categoryIndex - 1).get(itemIndex - 1).get(2));
        qty = qty + qtyToAdd;
        if (qty > 0) {
            this.items.get(categoryIndex - 1).get(itemIndex - 1).set(2, Integer.toString(qty));
            if (qtyToAdd < 0) {
                System.out.println(itemName + " quantity successfully reduced by " + Math.abs(qtyToAdd) + ", current quantity: " + qty + ".\n");
                addLog(itemName + " quantity was reduced by " + Math.abs(qtyToAdd) + " by " + this.username);
            } else if (qtyToAdd > 0) {
                System.out.println(itemName + " quantity successfully added by " + Math.abs(qtyToAdd) + ", current quantity: " + qty + ".\n");
                addLog(itemName + " quantity was added by " + Math.abs(qtyToAdd) + " by " + this.username);
            } else {
                System.out.println(itemName + " quantity is still " + qty + ".\n");
            }
        } else if (qty == 0) {
            this.items.get(categoryIndex - 1).get(itemIndex - 1).set(2, Integer.toString(qty));
            System.out.println(itemName + " quantity successfully reduced by " + Math.abs(qtyToAdd) + ", current quantity: " + qty + ".\n");
            addLog(itemName + " quantity was reduced by " + Math.abs(qtyToAdd) + " by " + this.username);
        } else {
            System.out.println("Operation resulted negative, item quantity cannot be negative number.\n");
        }
    }
    // ==================== END ITEMS SECTION ====================

    // ==================== LOGS SECTION ====================
    public boolean isLogsEmpty() {
        return this.logs.size() == 0;
    }

    public void showLogs() {
        for (String log : this.logs) {
            System.out.println(log);
        }
        System.out.println();
    }

    private void addLog(String message) {
        this.logs.add(" -> " + new Timestamp(System.currentTimeMillis()) + " - " + message);
    }
    // ==================== END LOGS SECTION ====================

    public void addDummies() {
        addCategory("Topi");
        addCategory("Saya");
        addCategory("Bundar");
        addItem(1, "Topi", "Topi 01", 10);
        addItem(1, "Topi", "Topi 02", 10);
        addItem(1, "Topi", "Topi 03", 10);
        addItem(2, "Saya", "Saya 01", 10);
        addItem(2, "Saya", "Saya 02", 10);
        addItem(2, "Saya", "Saya 03", 10);
        addItem(3, "Bundar", "Bundar 01", 10);
        addItem(3, "Bundar", "Bundar 02", 10);
        addItem(3, "Bundar", "Bundar 03", 10);
    }
}

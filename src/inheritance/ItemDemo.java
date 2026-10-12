package inheritance;

import java.util.ArrayList;
public class ItemDemo {
 
    public static void main(String[] args) {
 
        // ===== A polymorphic collection of Items =====
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("generic item", Color.BEIGE, Size.SMALL));
        items.add(new RedItem("strawberry", Color.CRIMSON, Size.SMALL));
        items.add(new BigRedItem("firetruck", Color.RED));
        items.add(new MisbehavingRedItem("lime", Color.LIMEGREEN, Size.SMALL));
 
        // The list holds Items, but each object is its real type.
        System.out.println("--- 1. Loop through Items: whose dynamicType() runs? ---");
        for (Item item : items) {
            System.out.println(item.dynamicType() + ": " + item);
        }
        // Note: "lime" stayed LIMEGREEN, but RedItem would have forced RED.
 
        // ===== Same call, different behavior (overriding) =====
        System.out.println("\n--- 2. setColor(BLUE) on every item ---");
        for (Item item : items) {
            item.setColor(Color.BLUE);
            System.out.println(item.dynamicType() + " -> " + item.getColor());
        }
        // Item and MisbehavingRedItem accept BLUE. RedItem and BigRedItem
        // run validateColor and switch it to RED.
 
        System.out.println("\n--- 3. setSize(MEDIUM) on every item ---");
        for (Item item : items) {
            item.setSize(Size.MEDIUM);
            System.out.println(item.dynamicType() + " -> " + item.getSize());
        }
        // Only BigRedItem refuses: its setSize always keeps LARGE.
 
        // ===== Static method: called on the CLASS, no object needed =====
        System.out.println("\n--- 4. static validateColor ---");
        System.out.println("validateColor(MAROON) = " + RedItem.validateColor(Color.MAROON));
        System.out.println("validateColor(BLUE)   = " + RedItem.validateColor(Color.BLUE));
 
        // ===== Variable type vs object type =====
        System.out.println("\n--- 5. Variable type vs object type ---");
        Item thing = new BigRedItem("fire hydrant", Color.MAROON);  // Item variable
        System.out.println("Variable type: Item, object type: " + thing.dynamicType());
        System.out.println("thing instanceof RedItem?    " + (thing instanceof RedItem));
        System.out.println("thing instanceof BigRedItem? " + (thing instanceof BigRedItem));
        // thing.validateColor(...) is NOT an Item method, so it would not
        // compile on an Item variable even though the object is a RedItem.
 
        // ===== Bonus: lambdas working with the same list =====
        System.out.println("\n--- 6. Lambdas on the list ---");
        items.forEach(item -> System.out.println("Item: " + item.dynamicType()));  // Consumer
        items.removeIf(item -> item.getColor() == Color.BLUE);                    // Predicate
        System.out.println("After removing BLUE items, " + items.size() + " remain.");
    }
}
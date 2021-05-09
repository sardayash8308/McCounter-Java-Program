import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Yash Ramchandra Sarda
 * @version 0.1
 * @since 09-05-2021
 */
/** Enum to specify the size of Items*/
enum Size{Big,Small,Medium}

/**
 * class Items implements Serializable to do serialization and deserialization of Items List
 */
public class Items implements Serializable {
    /** Items list of type List to store objects of Items*/
    public static List<Items> itemsList = new ArrayList<>();
    /** Name of Item  --- >> type String*/
    private String name;
    /** Price of Item --- >> type float*/
    private float price;
    /** Size of Item --- >> type Size*/
    private Size size;

    /**
     * Constructor of Item class
     * @param name Name of the Item as String
     * @param price Price of the Item in Floating type
     * @param size Size of Item as type of Size.
     * After setting all variables it add the item to itemsList.
     */
    Items(String name,float price,Size size){
        this.name = name;
        this.size = size;
        this.price = price;
        itemsList.add(this);
    }
    /** Getter to get Name of the Item
     * @return Name of the Item*/
    public String getName() { return name; }

    /** Setter to set the Name of the Item
     * @param name Name of the item to set
     */
    public void setName(String name) { this.name = name; }
    /** Getter to get the Price of the Item
     * @return floating Price of Item*/
    public float getPrice() { return price; }
    /** Setter to set Price of the Item
     * @param price  Floating price to be set*/
    public void setPrice(float price) { this.price = price; }
    /** Getter to get Size of the Item
     * @return Size of the Item*/
    public Size getSize() { return size; }
    /** Setter to set Size of the Item
     * @return Size of the Item*/
    public static Size setSize(){
        Size s = null; int choice = 0;
        System.out.println("\t\tPlease select any of the three sizes from below options");
        System.out.println("\t\t1. -> Big\n\t\t2. -> Medium\n\t\t3. -> Small");
        choice = McCounter.integerChoice(1,3);
        switch (choice){ case 1 -> s = Size.Big;   case 2 -> s = Size.Medium;   case 3 -> s = Size.Small; }
        return s;
    }
    /** Main Method of Items class
     * @throws IOException If fail to readItem()*/
    public static void main() throws IOException {
        System.out.println("\t\t\tPlease Select the following options ");
        System.out.println("\t\t\t1. To add item");
        System.out.println("\t\t\t2. To remove item");
        System.out.println("\t\t\t3. To return to Manager Menu");
        int choice = McCounter.integerChoice(1,3);
        switch (choice){
            case 1 -> addItem();
            case 2 -> removeItem();
            case 3 -> Manager.main(new String[]{"d","c"});
            default -> main();
        }
    }
    private static void wi() throws IOException {
        Items m = new Items("Burger",20.5f,Size.Big);
        Items k = new Items("Drink",25f,Size.Small);
        Items l = new Items("Meal",100f,Size.Medium);
        writeItems();
    }
    /** toString to represent the Item in special format*/
    public String toString(){
        System.out.println("\n");
        System.out.println("\t\t --> Item Name\t-->\t"+this.getName());
        System.out.println("\t\t --> Item Price\t-->\t"+this.getPrice());
        System.out.println("\t\t --> Item Size\t-->\t"+this.getSize());
        return "";
    }
    /** Print ItemsList in special format for user
     * @param list ItemsList to trace*/
    public static void printItems(List<Items> list){
        int j = 1;
        for (Items i:list){ System.out.println("\t\t\t"+j+++" --> "+i.name+" --> "+i.price+" --> "+i.size); }
    }
    /** Same as to String*/
    public static void printItems(){
        for (Items i:itemsList) { System.out.println(i); }
    }

    /**
     * Add Item to the ItemsList.
     * @throws IOException If writeItems() Method fails.
     */
    private static void addItem() throws IOException {
        System.out.println("Please provide following details you want to add  ");
        System.out.print("\t\t\t1. Name of the new Item : ");
        String name = Manager.sc.nextLine();
        System.out.print("\t\t\t2. Price of the " + name + " : ");
        float price = 0;
        try {
            price = Manager.sc.nextFloat();
        } catch (Exception e) {
            System.out.println("\t\t\tPlease give valid price ");
        }
        Size s = setSize();
        new Items(name, price, s);
        writeItems();
        main();
    }

    /**
     * Remove Item from the ItemsList
     * @throws IOException On failure of writeItems Method()
     */
    private static void removeItem() throws IOException {
        Items.printItems(itemsList);
        System.out.print("Please Specify which Item you want to remove : ");
        int choice = McCounter.integerChoice(1,itemsList.size());
        if (0<choice||choice<Items.itemsList.size()) {
            try {
                Items.itemsList.remove(choice-1);
            } catch (Exception e) {
                System.out.println("Some Exceptions Occured....");
            }
        }else System.out.println("Invalid Choice ");
        writeItems();
        main();
    }

    /** Serialize the ItemsList in file name "items".
     * <p>If the file is not present it will handle the exception by creating new file of name "items"</p>
     * @throws IOException If some Exception other than FileNotFoundException Occurs.
     */
    public static void writeItems() throws IOException {
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream("items");
        }catch (FileNotFoundException e){
            File f = new File("items");
            if (!f.exists()){
                f.createNewFile();
            }fos = new FileOutputStream("items");
        }catch (Exception e) {System.out.println(e.getMessage());}
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(Items.itemsList);
        System.out.println("Object Serialization done successfully");
        fos.close();
    }

    /**
     * Deserialize the ItemsList from file "items"
     */
    public static void readItems() {
        FileInputStream fis = null;
        try{
            fis = new FileInputStream("items");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            Items.itemsList = (List<Items>) obj;
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Exception occured : "+e.getMessage());
        }
    }
}


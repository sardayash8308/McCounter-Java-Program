import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Yash Ramchandra Sarda
 * @version 0.1
 * @since 09-05-2021
 */

class Order{
    /** Order class for Order Items*/
    /** Item i */
    Items i;
    /** Quantity of Item i*/
    int quantity = 1;
    /** Total  = quantity * i.getPrice*/
    float total;
    /** Constructor for Order*/
    Order(Items i, int quantity){
        this.i = i;
        this.quantity = quantity;
        this.total = i.getPrice() * quantity;
    }
}

/** McCounter class*/
public class McCounter {
    /** Scanner Object for Mc Counter class.*/
    public static Scanner sc = new Scanner(System.in);
    /** Method to validate the choice of user.
     * @param lower The lower choice which user have to give.
     * @param upper The highest choice which user have to give.
     * If user gives choices out of this range then it will repeatedly ask for valid choice from User.
     * @return the value given by user.
     * */
    public static int integerChoice(int lower,int upper){
        System.out.print(" Enter Your choice : ");
        int choice=0;
        String choice1 = sc.nextLine();
        try{
            choice = Integer.parseInt(choice1);
            if (lower<=choice && choice<=upper) return choice;
            else System.out.println("Please Give Valid Input");
        }catch (Exception e){
            System.out.println("Please Give Valid Input");
        }return choice;
    }
    /** List of Items ordered by customer*/
    static List<Order> customerList = new ArrayList<>();
    /** Add items to user bill
     * @throws IOException If fail due to some input output fault*/
    public static void addItem() throws IOException {
        int choice;boolean finished = true;
        do{
        Items.printItems(Items.itemsList);
        System.out.print("\t\t\tPlease select which Item you want to add");
        choice = integerChoice(1,Items.itemsList.size()+100);
        if (choice<1) System.out.println("\t\t\tPlease give valid Choice.");
        else if(choice>Items.itemsList.size()+1){finished = false;break;}
        else if (choice>1||choice<Items.itemsList.size()){
            System.out.print("how many "+Items.itemsList.get(choice-1).getName() +" of "+Items.itemsList.get(choice-1).getSize()+" size should be add" );
            int quantity = integerChoice(1,500);
            Order o = new Order(Items.itemsList.get(choice-1),quantity);
            customerList.add(o);
        }
        }while (finished);doBill();
    }

    /** Consider it as main method of Mc Counter class.
     * It will give 3 options to the Employee.
     * <p>1. To add item to order ---> calls addItem Method in McCounter</p>
     * <p>2. To remove Item from Order ---> calls removeItem Method in McCounter </p>
     * <p>3. To Finalize the bill ---> calls PrintBill Method</p>
     * @throws IOException If main fails
     */
    public static void doBill() throws IOException {
        System.out.println("\t\t\tPlease specify any of the following choices");
        System.out.println("\t\t\t1. To add Item to Order");
        System.out.println("\t\t\t2. To remove Item from Order");
        System.out.println("\t\t\t3. To finalize the bill");
        System.out.println("\t\t\t4. to return to main menu");
        int choice;
        choice = integerChoice(1,3);
        switch (choice){
            case 1 -> addItem();
            case 2 -> removeItem();
            case 3 -> printBill();
            case 4 -> Main.main(new String[]{"d","e"});
        }
    }
    /** Remove Item from the Order List or customerList
     * @throws IOException -*/
    public static void removeItem() throws IOException {
        System.out.println("\t\t\t Your Ordered Items are.");
        printOrder();
        System.out.print("Please specify which Item you want to remove. If you give number greater than the given bound the operation will be cancelled : ");
        int choice = integerChoice(1,customerList.size());
        if (choice<1||choice>customerList.size()){
            System.out.println("Redirecting to addItem...");
            addItem();
        }else {
            try{ customerList.remove(choice-1);
                System.out.println("Your Item has been successfully removed");
            }catch (Exception e){
                System.out.println("Unable to remove the item "+e.getMessage());
            }
        }doBill();
    }
    /** Print the Ordered Items in special way*/
    public static void printOrder(){
        System.out.printf("\t\t\t%-5s | %-20s | %-20s | %-20s | %-20s | %-20s \n","Srno.","Item Name","Item Price","Item Size","Quantity","Total");
        int j = 0;
        for (Order o:customerList){
            System.out.printf("\t\t\t%-5s | %-20s | %-20s | %-20s | %-20s | %-20s \n",++j,o.i.getName(),o.i.getPrice(),o.i.getSize(),o.quantity,o.total);
        }
    }
    /** Get and Finalize the bill by doing total of all items
     * @throws IOException IOException*/
    public static void printBill() throws IOException {
        System.out.println("\t\t\tYour Order is.");
        printOrder();
        float totalBill = 0f;
        for (Order o:customerList){
            totalBill+=o.total;
        }customerList.clear();
        System.out.println("\t\t\t---- Your Total Bill Is "+totalBill+"\n \t\t\t-------> Visit Again Thank you");
        doBill();
    }

}

import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;
/**
 * @author Yash Ramchandra Sarda
 * @version 0.1
 * @since 09-05-2021
 * @use The Manager Class is used to handle the employees list, item list and to modify them<p>It give s3 options to user</p><p>1. To edit Employee Details</p><p>2. To edit Items from menu</p><p>3. To change the credentials of Manager.</p>
 *  */


/** Manager Class implements Serializable and Manage the whole McDonald's*/
public class Manager implements Serializable {
    /** Static scanner Class so do not want to repeatedly make the scanner class in every different classes. Thus reducing repeatability.*/
    public static Scanner sc = new Scanner(System.in);
    /** User Name for Manager */
    private static String ManagerUserName;
    /** Password of Manager */
    private static String ManagerPassword;
    /** To verify if the Manager has entered valid credentials of his/her.*/
    static boolean isManager = false;


    /** Main Method for Manager class
     * <p>Had made it default so Employee class and item class could access it.</p>
     * At first it does validation of Manager by taking and verifying the credentials so no one else could access this private data and functions.
     * @param args Main method so ignore it
     * @throws IOException If fail to readItems or readEmployee.
     */
    public static void main(String[]args) throws IOException {
        Hashtable <String,String> cre = readManager();
        ManagerUserName = cre.get("UserName");
        ManagerPassword = cre.get("Password");
        System.out.println(ManagerUserName +"\t"+ManagerPassword);
        if (!isManager) {isManager = validation();}
        if (createManagerFile()){
            try{Items.readItems();}catch (Exception e){ System.out.println("Exception : "+e.getMessage());}
            //Items.printItems();
            try{Items.writeItems();} catch (Exception e){ System.out.println(e.getMessage());}
            int choice;
            if (isManager){
                do{
                System.out.println("\t\t\t   Operations to Performed");
                System.out.println("\t\t\t1. Edit Employee Details");
                System.out.println("\t\t\t2. Edit items from Menu.");
                System.out.println("\t\t\t3. Change User Name and Password.");
                System.out.println("\t\t\t4. To exit from Appln :");
                choice = McCounter.integerChoice(1,4);
                }while (choice<1||choice>4 );
                switch (choice){
                    case 1 -> Employee.editEmployeeDetails();
                    case 2 -> Items.main();
                    case 3 -> changeCredentials();
                    case 4 -> {isManager = false;System.out.println("Thank you");}
                }
            }
        }else System.out.println("Unable to create Manager file");
    }

    /** Method to create the manager file to serialize and deserialize the Manager objects into file
     *
     * @return boolean true if the file exists already, else create files and then return true.
     */
    private static boolean createManagerFile(){
        File f = new File("manager");
        if (!f.exists()){
            try {
                f.createNewFile();
            }catch (Exception e){
                System.out.println("Unable to create Manager file");
            }
        }return f.exists();
    }
    /**  Method to change credentials of Manager if Manager wants to change.*/
    private static void changeCredentials() throws IOException {
        System.out.println("\t\t\tYour Previous username : "+ManagerUserName);
        System.out.print("\t\t\tPlease enter your new User Name : ");
        ManagerUserName = sc.nextLine();
        System.out.println("\t\t\tYour new User Name is : "+ManagerUserName);
        System.out.print("\t\t\tPlease Enter Your old password : ");
        if (sc.nextLine().equals(ManagerPassword)) {
            System.out.print("\t\t\tPlease enter your new password : ");
            String np1 = sc.nextLine();
            System.out.print("\t\t\tPlease confirm your new password : ");
            if (sc.nextLine().equals(np1)) {
                System.out.println("\t\t\tYour credentials have been changed successfully");
                ManagerPassword = np1;
                writeManager();
                main(new String[]{"d","e"});
            } else {System.out.println("\t\t\tYour password doesn't match ");Manager.main(new String[]{"d","c"});}
        }else {System.out.println("\t\t\tInvalid password");Manager.main(new String[]{"d","c"});}
    }
    /** Method to serialize the Manager Object, means its credentials into "manager" file.*/
    private static void writeManager() throws IOException {
        if (createManagerFile()){
            FileOutputStream fos = new FileOutputStream("manager");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Hashtable<String,String> managerCredentials = new Hashtable<>();
            managerCredentials.put("UserName",ManagerUserName);
            managerCredentials.put("Password",ManagerPassword);
            oos.writeObject(managerCredentials);
            fos.close();
        }
    }

    /** Method to deserialize the Manager objects from manager file and setting the username and password to it
     *
     * @return Hashtable(String,String) where the key is user name and value is password
     * @throws IOException If the manager file is empty thus making Hashtable to null
     */
    private static Hashtable<String, String> readManager() throws IOException {
        FileInputStream fis = null;
        Hashtable<String,String> cre = new Hashtable<>();
        try{
            fis = new FileInputStream("manager");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            cre = (Hashtable<String, String>) obj;
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Exception occured : "+e.getMessage());
        }
        return cre;
    }

    /**
     * Method to validate the Manager by asking him credentials
     * @return boolean true value if the credentials match else false and ask repeatedly for credential.
     * @throws IOException If the Hashtable is null because manager file is empty.
     */
    private static boolean validation() throws IOException {
        boolean validated = false;
        Hashtable <String,String> cre = readManager();
        System.out.println(cre.keySet());
        ManagerUserName = cre.get("UserName");
        ManagerPassword = cre.get("Password");
        do{
        System.out.print("Please give your user name : ");
        String enteredUserName = sc.nextLine();
        if (ManagerUserName.equals(enteredUserName)) {
            System.out.print("Enter password \t:\t");
            String enteredPassword = sc.nextLine();
            if (enteredPassword.equals(ManagerPassword)){
                System.out.println("Welcome "+ManagerUserName);
                validated = true;
            }else System.out.println("Invalid Password");
        }else System.out.println("Invalid User Name ");
        }while (!validated);
    return validated;
    }
}

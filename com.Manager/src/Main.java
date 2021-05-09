import java.io.IOException;

/**
 * @author Yash Ramchandra Sarda
 * @version 0.1
 * @since 09-05-2021
 */
/** Main class to run the Program*/
public class Main {
    /** Main Method from where the program executes
     * At start it executes Employee.readEmployees Method and Items.readItems Method
     * <p>Thus it do deserialization of Employee List and Items List.</p>
     * @param args Nothing to do with it
     * @throws IOException If fail to read the employee or ItemList*/
    public static void main(String[] args) throws IOException {
        //Items.wi();
        Items.readItems();
        Employee.readEmployees();
        System.out.println("Please specify who you are and what you want to do");
        System.out.println("\t\t\t1. I am Employee and want to do billing.");
        System.out.println("\t\t\t2. I am Manager and want to do some Modification.");
        int choice;
        do {
            choice = McCounter.integerChoice(1, 2);
        } while (choice < 1 || choice > 2);
        switch (choice) {
            case 1 -> {
                if (doEmployeeValidation()) {
                    McCounter.doBill();
                }
            }
            case 2 -> Manager.main(new String[]{"d", "e"});
        }
    }
    /** Method to do Employee Validation
     * @return boolean true if the employee credentials are valid else false*/
    public static boolean doEmployeeValidation () {
        System.out.print("Please Enter your User name : ");
        boolean valid = false;
        String username = Manager.sc.nextLine();
        if (Employee.employeesList.containsKey(username)) {
            System.out.print("Please Enter your Password : ");
            String password = Manager.sc.nextLine();
            if (Employee.employeesList.get(username).equals(password)) {
                System.out.println("*********  Welcome " + username);
                valid = true;
            } else
                System.out.println("Password doesn't Match for " + username + ". If you had forgot your password then you could ask Manager for help.");
        } else System.out.println("Invalid User.....");
        return valid;
    }
}


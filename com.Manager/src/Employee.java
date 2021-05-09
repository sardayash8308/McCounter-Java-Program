import java.io.*;
import java.util.Hashtable;
import java.util.Set;

/**
 * @author Yash Ramchandra Sarda
 * @version 0.1
 * @since 09-05-2021
 */
class Employee implements Serializable {
    /** User name for employee*/
    private String username;
    /** Password for Employee */
    private String password;
    /**List of Employees Present in Hashtable format where key is username of employee and value is it's password */
    static Hashtable<String,String> employeesList = new Hashtable<String,String>();


    /** Constructor of Employee
     * It first call setParameter() method to verify whether a employee with same user name is present or not.
     * If it's not present then it add it to the Employee List on successfully creating employee.
     * @param username User name for new employee
     * @param password Password for Employee
     */
    Employee(String username, String password) throws IOException {
        boolean done = false;
        if (validatingUserName(username)){
            this.username = username;
            this.password = password;
            employeesList.put(username,password);
            done = true;
        } else System.out.println("User Name "+username+" is already present.");
        try{writeEmployees();}catch (Exception e){ System.out.println("unable to write employee list in employees"); }
}

    /** Gives option to edit the employee details.
     * <p>1. To add new Employee </p>
     * <p>2. To edit the details of existing employee</p>
     * <p>3. to remove employee.</p>
     */

    static void editEmployeeDetails() throws IOException {
        File f = new File("employees");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try { readEmployees(); }catch (Exception e){ System.out.println("Unable to read employee List from Employees "+e.getMessage()); }
        System.out.println("\n\t\tPlease select any one of the below option");
        System.out.println("\t\t\t1. To add Employee");
        System.out.println("\t\t\t2. To edit Present Employee Details");
        System.out.println("\t\t\t3. To Remove employee.");
        System.out.println("\t\t\t4. To move to main menu of Manager");
        int choice;
        do {
            choice = McCounter.integerChoice(1,4);
        }while (choice<1||choice>4);
        switch (choice) {
            case 1 -> addEmployee();
            case 2 -> editPresentEmployee();
            case 3 -> removeEmployee();
            case 4 -> {
                try {
                    writeEmployees();Manager.main(new String[]{"d","e"});
                } catch (Exception e) {
                    System.out.println("Unable to write Employee List");
                }
            }
        }Manager.main(new String[]{"d","e"});
    }
    /** Method to remove Employee from employees list*/
    public static void removeEmployee() throws IOException {
        if (employeesList.size()>=1){
            printEmployees(true);boolean done = false;
            System.out.print("Please give username of employee to remove : ");
            String choice = Manager.sc.nextLine();
            try{
                employeesList.remove(choice);
                done = true;
            }catch (Exception e){
                System.out.println("Invalid choice "+choice);
            }if (done){ writeEmployees(); }
        }else System.out.println("Employees List is Empty ");
        editEmployeeDetails();
    }
    /** Print Employees in specific format*/
    public static void printEmployees(boolean a){
        Object[] kry = employeesList.keySet().toArray();
        System.out.printf("\t\t\t%-5s | %-5s | %-20s \n","Srno.","Employee Name","Password");
        System.out.println("\t\t\t------------------------------------------------------------------------------------------------");
        for (int j=0;j<employeesList.size();j++){ System.out.printf("\t\t\t%-5s | %-15s | %-15s \n",j,kry[j],employeesList.get((String)kry[j])); }
    }
    /** Function to serialize the Employee List for future use*/
    public static void writeEmployees() throws IOException {
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream("employees");
        }catch (FileNotFoundException e){
            File f = new File("employees");
            if (!f.exists()){
                f.createNewFile();
            }fos = new FileOutputStream("employees");
        }catch (Exception e) {System.out.println(e.getMessage());}
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(Employee.employeesList);
        System.out.println("Object Serialization done successfully");
        fos.close();
    }

    /** Function to read Employee list from serialize File*/
    public static void readEmployees() {
        FileInputStream fis = null;
        try{
            fis = new FileInputStream("employees");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            Employee.employeesList = (Hashtable<String, String>) obj;
            System.out.println("Read objects Successfully....");
        }catch (IOException | ClassNotFoundException | NullPointerException e){
            System.out.println("Exception occured : "+e.getMessage());
        }
    }

    /** Method to add new Employee
     * It calls setParameter() Method to verify whether user name is present or not*/
    private static void addEmployee() throws IOException {
        System.out.println("\t\t\tEnter Employee details ");
        System.out.print("\t\t\t1. Enter Employee user name : ");
        String username = Manager.sc.nextLine();
        System.out.print("\t\t\tEnter Password : ");
        String password = Manager.sc.nextLine();
        try {
            new Employee(username,password);
        }catch (Exception e){ System.out.println("Unable to add Employee ");}
        editEmployeeDetails();
    }
    /**Returns the boolean false value if the user name is not present else return true.
     * This is done to avoid duplicate user names.*/
    private static boolean validatingUserName(String userName) { return employeesList.containsKey(userName); }

    /** To edit the present Employee Details
     * <p>Through this method the manager could retrieve the password of any employee.</p>
     * @throws IOException If the employee List is empty.
     */
    private static void editPresentEmployee() throws IOException,NullPointerException {
        printEmployees(true);
        System.out.print("\t\t\tPlease specify which employee you want to edit ");
        String choice = Manager.sc.nextLine();
        if (employeesList.containsKey(choice)){
            System.out.println("Employee Name --> "+choice);
            System.out.println("Employee Password --> "+employeesList.get(choice));
            System.out.print("Do you want to edit this employee if yes then press 'y' else any option to exit : ");
            String c = Manager.sc.nextLine();
            if (c.equalsIgnoreCase("y")){
                employeesList.remove(choice);
                System.out.print("\t\t\tEnter user Name : ");
                String username = Manager.sc.nextLine();
                if (!validatingUserName(username)){
                    System.out.print("\t\t\tEnter Password : ");
                    String password = Manager.sc.nextLine();
                    employeesList.put(username,password);
                    new Employee(username,password);
                }else System.out.println("User Name "+username+" already present");
            }
        }
    }

//    public static void main(String[] args) throws IOException {
//        Employee e = new Employee("Labour 1","labour 1");
//        Employee e2 = new Employee("yash","yash");
//        writeEmployees();
//    }
}

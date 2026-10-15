import java.util.Scanner;

public class PasswordManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Account.loadAccounts();
        boolean keepRunning = true;
        while(keepRunning) {
            System.out.println("\n===== Password Manager =====");
            //menu choice 1
            System.out.println("1. Add account");
            //menu choice 2
            System.out.println("2. View accounts");
            //menu choice 3
            System.out.println("3. Exit");
            //menu choice 4
            System.out.println("4. Search account");
            //menu choice 5
            System.out.println("5. Delete account");
            //menu choice 6
            System.out.println("6. Login");

            //input from user
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            //output based on the user input
            switch (choice) {
                case 1:
                    System.out.println("You chose Add Account");
                    System.out.println();
                    Account.addAccount(sc);
                    Account.saveAccounts();
                    break;
                case 2:
                    System.out.println("You chose View Accounts");
                    System.out.println();
                    Account.viewAccounts();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    System.out.println();
                    keepRunning = false;
                    break;
                case 4:
                    System.out.println("You chose Search Account");
                    System.out.println();
                    Account.searchAccount(sc);
                    break;
                case 5:
                    System.out.println("You chose Delete Account");
                    System.out.println();
                    Account.deleteAccount(sc);
                    Account.saveAccounts();
                    break;
                case 6:
                    System.out.println("You chose Login");
                    System.out.println();
                    Account user = Account.login(sc);

                    if (user != null) {
                        boolean loggedIn = true;

                        while (loggedIn) {
                            System.out.println("\n===== Logged In Menu =====");
                            System.out.println();
                            System.out.println("1. View my account");
                            System.out.println("2. Change password");
                            System.out.println("3. Logout");
                            System.out.println();
                            System.out.print("Enter choice: ");
                            int option = sc.nextInt();
                            sc.nextLine();

                            switch (option) {
                                case 1:
                                    System.out.println(user.toString());
                                    System.out.println();
                                    break;
                                case 2:
                                    System.out.print("Enter current password: ");
                                    System.out.println();
                                    String current = sc.nextLine();

                                    if (user.getPassword().equals(current)) {
                                        String newPass;

                                        while (true) {
                                            System.out.print("Enter new password: ");
                                            System.out.println();
                                            newPass = sc.nextLine();

                                            if (Account.isStrongPassword(newPass)) {
                                                break;
                                            } else {
                                                System.out.println("Weak password.");
                                                System.out.println();
                                                Account.printPasswordRequirements();
                                            }
                                        }

                                        user.setPassword(newPass);
                                        Account.saveAccounts();
                                        System.out.println("Password updated successfully.");
                                        System.out.println();
                                    } else {
                                        System.out.println("Incorrect current password.");
                                        System.out.println();
                                    }
                                    break;
                                case 3:
                                    loggedIn = false;
                                    System.out.println("Logged out.");
                                    break;
                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }

                            System.out.println(); //for spacing
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }

            System.out.println(); //for spacing
        }
    }
}

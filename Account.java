import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;;

public class Account {
    private String name;
    private String password;
    private static ArrayList<Account> accounts = new ArrayList<>();

    public Account(String name, String password) {
        if (name != null) {
            this.name = name;
        } else {
            System.out.println("Invalid name");
        }
        
        if (password != null) {
            this.password = password;
        } else {
            System.out.println("Invalid password");
        }
    }

    public Account() {
        this("No name set", "no password set");
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        if (password == null) {
            return String.format("Name: %s, Password: N/A\n", name);
        }
        String maskedPassword = "";
        for (int i = 0; i < password.length(); i++) {
            maskedPassword += "*";
        }
        return String.format("Name: %s, Password: %s\n", name, maskedPassword);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Account)) {
            return false;
        }

        Account o = (Account) other;

        return (this.name.equals(o.name)) && (this.password.equals(o.password));
    }

    public static void addAccount(Scanner sc) {
        System.out.print("Enter account name: ");
        String name = sc.nextLine();

        System.out.print("Enter password: ");
        String password;

        while (true) {
            System.out.print("Enter password: ");
            password = sc.nextLine();

            if (isStrongPassword(password)) {
                break;
            } else {
                System.out.println("Weak password.");
                printPasswordRequirements();
            }
        }

        Account newAccount = new Account(name, password);
        accounts.add(newAccount);

        System.out.println("Account " + name + " added successfully!");
    }

    public static void viewAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts stored.");
        } else {
            for (int i = 0; i < accounts.size(); i++) {
                System.out.print("Account " + (i + 1) + ": " + accounts.get(i).toString());
            }
        }
    }

    public static void searchAccount(Scanner sc) {
        System.out.print("Enter account name to search: ");
        String nameSearch = sc.nextLine();
        boolean found = false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getName().equalsIgnoreCase(nameSearch)) {
                System.out.println(accounts.get(i).toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Account not found.");
        }
    }

    public static void deleteAccount(Scanner sc) {
        int maxAttempts = 3;
        if (accounts.isEmpty()) {
            System.out.println("No accounts to delete.");
        } else {
            Account.viewAccounts();
            System.out.print("Enter account number to delete: ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            int index = choice - 1;
            if (index < 0 || index >= accounts.size()) {
                System.out.println("Invalid selection");
            } else {
                Account selectedAccount = accounts.get(index);
                System.out.print("Enter password to confirm deletion: ");
                for (int i = 0; i < maxAttempts; i++) {
                    String inputPassword = sc.nextLine();
                    if (inputPassword.equals(selectedAccount.getPassword())) {
                    accounts.remove(index);
                    System.out.println("Account deleted successfully.");
                    return;
                    } else {
                        int attemptsLeft = maxAttempts - (i + 1);
                        System.out.println("Incorrect password. Attempts left: " + attemptsLeft);

                        if (attemptsLeft > 0) {
                            System.out.print("Enter password to confirm deletion: ");
                        }
                    }
                }
                System.out.println("Deletion cancelled.");
            }
        }
    }

    public static Account login(Scanner sc) {
        int maximumAttempts = 2;
        System.out.print("Account name: ");
        String inputAccount = sc.nextLine();
        System.out.print("Account password: ");
        String inputPassword = sc.nextLine();
        Account temp = new Account(inputAccount, inputPassword);

        boolean found = false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).equals(temp)) {
                System.out.println("Login successful!");
                found = true;
                return accounts.get(i);
            }
        }
        if (!(found)) {
            System.out.println("Invalid credentials. Please try again.");
            for (int i = 0; i < maximumAttempts; i++) {
                System.out.print("Account name: ");
                inputAccount = sc.nextLine();
                System.out.print("Account password: ");
                inputPassword = sc.nextLine();
                temp = new Account(inputAccount, inputPassword);
                
                for (int j = 0; j < accounts.size(); j++) {
                    if (accounts.get(j).equals(temp)) {
                        System.out.println("Login successful!");
                        found = true;
                        return accounts.get(j);
                    }
                }
                if (!(found)) {
                    System.out.println("Invalid credentials. Please try again.");
                }
            }
            if (!(found)) {
                System.out.println("Too many failed attempts. Account locked.");
            }
        }
        return null;
    }

    public static void saveAccounts() {
        try {
            FileWriter writer = new FileWriter("accounts.txt");
            for (Account a : accounts) {
                writer.write(a.getName() + "," + a.getPassword() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public static void loadAccounts() {
        try {
            File file = new File("accounts.txt");
            if (!file.exists()) {
                return;
            }

            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    accounts.add(new Account(parts[0], parts[1]));
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }

    //special feature
    public static boolean isStrongPassword(String password) {
        boolean hasLetter = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else {
                hasSpecial = true;
            }
        }
        return password.length() >= 6 && hasLetter && hasNumber && hasSpecial;
    }

    public static void printPasswordRequirements() {
        System.out.println("Password must:");
        System.out.println();
        System.out.println("- Be at least 6 characters long");
        System.out.println("- Contain at least one letter");
        System.out.println("- Contain at least one number");
        System.out.println("- Contain at least one special character");
    }
}

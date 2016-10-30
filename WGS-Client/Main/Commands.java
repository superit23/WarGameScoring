
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Alex on 10/12/2016.
 */
public class Commands {

    public static void help(String command){

        switch (command){

            case "login":
                clear();
                String loginHelp = "This method is used to login to the server. login supports both an interactive\n" +
                        "mode and a quick mode. To use the interactive mode simply type in login. To use\n" +
                        "the quick mode type login followed by the username and then the password of the\n" +
                        "account.\n" +
                        "Ex. login User Password";
                System.out.println(loginHelp);
                break;

            case "getCoins":
                clear();
                String getCoinsHelp = "This method is used to get your coins from the server. You must be logged in to\n" +
                        "use the getCoins method. getCoins supports both a quick mode and an interactive\n" +
                        "mode. To use the interactive mode simply type in getCoins. To use the quick\n" +
                        "mode type in getCoins followed by the name of the file you wish to overwrite\n" +
                        "the coins to.\n" +
                        "Ex. getCoins File";
                System.out.println(getCoinsHelp);
                break;

            case "sendCoins":
                clear();
                String sendCoinsHelp = "This method is used to send coins to the bank to increase your score. this \n" +
                        "method supports both a quick mode and an interactive mode. To use the\n" +
                        "interactive mode simply type in sendCoins. To use the quick mode type in\n" +
                        "sendCoins followed by the username and the path to the coin file.\n" +
                        "Ex. sendCoins username /home/user/file";
                System.out.println(sendCoinsHelp);
                break;

            case "createAccount":
                clear();
                String createAccountHelp = "This method is used to create a new User account. The create account method\n" +
                        "should be used by users looking to set up a new account. While there is no\n" +
                        "limit on the number of accounts you may have, only one account will scored at\n" +
                        "the end of the semester. createAccount supports both a quick mode and an\n" +
                        "interactive mode. To use the interactive mode simply type in createAccount. To\n" +
                        "use the quick mode type in createAccount followed by new username and a\n" +
                        "password.\n" +
                        "Ex. createAccount test password";
                System.out.println(createAccountHelp);
                break;

            case "deleteAccount":
                clear();
                String deleteAccountHelp = "This method is used to delete a User account. To delete an account you will\n" +
                        "need to be logged in and have permission to delete the account. When an account\n" +
                        "is deleted the score of the account will be lost. deleteAccount supports both\n" +
                        "and interactive mode and a quick mode. To use the interactive mode simply type\n" +
                        "in deleteAccount. To use the quick mode type in deleteAccount followed by the\n" +
                        "username of the account you wish to delete.\n" +
                        "Ex. deleteAccount account";
                System.out.println(deleteAccountHelp);
                break;

            case "transferScore":
                clear();
                String transferScoreHelp = "This method is used to transfer score between two different users. You must be\n" +
                        "logged in and have proper permissions to transfer score between players.\n" +
                        "transferScore supports both a quick mode and an interactive mode. To use the\n" +
                        "interactive mode simply type in transferScore. To use the quick mode type in\n" +
                        "transferScore followed by the name of account you wish to transfer to and the\n" +
                        "amount you wish to transfer. Please use an integer for the amount.\n" +
                        "Ex. transferScore account 10";
                System.out.println(transferScoreHelp);
                break;

            case "getBalance":
                clear();
                String getBalanceHelp = "This method is used to get your current balance. You must be logged in to use\n" +
                        "getBalance. Get balance only uses a quick mode. Just type in getBalance to use\n" +
                        "the method.\n" +
                        "Ex. getBalance";
                System.out.println(getBalanceHelp);
                break;

            case "changePassword":
                clear();
                String changePasswordHelp = "This method is used to change your current password. changePassword supports\n" +
                        "both an interactive mode and a quick mode. To use the interactive mode type in\n" +
                        "changePassword. To use the quick mode type in changePassword then account name,\n" +
                        "the current password and a new password for the account.\n" +
                        "Ex. changePassword account currentPassword newPassword";
                System.out.println(changePasswordHelp);
                break;

            case "createCoin":
                clear();
                String createCoinHelp = "This method is used to create coins on the server for users to acquire.\n" +
                        "createCoin supports both an interactive mod and a quick mode. To use the\n" +
                        "interactive mode type in createCoin. To use the quick mode type in createCoin\n" +
                        "followed by and initial user and the amount of coins you wish to create. Please\n" +
                        "enter the amount in an integer form.\n" +
                        "Ex. createCoin username 5";
                System.out.println(createCoinHelp);
                break;

            case "clear":
                String clearHelp = "Used to clear the console of previous output.";
                System.out.println(clearHelp);
                break;

            case "exit":
                String exitHelp = "Used to exit the bank program";
                System.out.println(exitHelp);
                break;

            default:
                String basicHelp = "The banking Client supports the following commands.\n" +
                        "For more information type help and then the command name\n" +
                        "login \n" +
                        "getCoins \n" +
                        "sendCoins \n" +
                        "createAccount \n" +
                        "deleteAccount \n" +
                        "transferScore\n" +
                        "getBalance \n" +
                        "changePassword \n" +
                        "createCoin \n" +
                        "clear \n" +
                        "exit \n";
                System.out.print(basicHelp);
        }


    }

    //test with tomcat Server
    public static String login(){
        String username;
        String password;
        String sessionVariable = "";
        int attempt = 1;
        boolean loggedIn = false;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your username: ");
        username = scanner.nextLine();

        //Console console = System.console();
        //password = new String(console.readPassword("Please enter your password: "));
        System.out.print("Please enter your password: ");
        password = scanner.nextLine();

        //send username and password to server. Store success in loggedIn and sessionVariable in sessionVariable

        while((attempt<3) && !loggedIn) {
            attempt++;
            System.out.println("Incorrect username or password please try again");
            System.out.print("Please enter your username: ");
            username = scanner.nextLine();

            //Console console = System.console();
            //password = new String(console.readPassword("Please enter your password: "));
            System.out.print("Please enter your password: ");
            password = scanner.nextLine();

            //send username and password to server. Store success in loggedIn and sessionVariable in sessionVariable
        }

        if(loggedIn)
            System.out.println("Welcome you are now logged in.");
        else
            System.out.println("You have failed to login 3 times. Please try again.");

        return sessionVariable;
    }

    //test with tomcat Server
    public static String login(String userName, String Password){
        String sessionVariable = "";
        boolean loggedIn = false;

        //send username and password to server. Store success in loggedIn and sessionVariable in sessionVariable

        if(loggedIn)
            System.out.println("Welcome you are now logged in.");
        else
            System.out.println("You have failed to login.");

        return sessionVariable;

    }

    //test with tomcat Server
    public static void getCoins(String sessionVar){
        String fileName = "/";
        String coins = "";
        File file;
        Scanner scanner = new Scanner(System.in);

        //send sessionVariable to server and save returned string in coins

        System.out.print("What would you like to name the file with your coins?: ");
        fileName = scanner.nextLine();

        try {
            file = new File("C:/Users/Alex/" + fileName + ".txt");
            FileUtils.writeStringToFile(file, coins, "UTF-8", true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //test with tomcat Server
    public static void getCoins(String sessionVar, String fileName){
        String coins = "";
        File file;
        //send SessionVariable and place stuff in coins string

        try {
            file = new File("C:/Users/Alex/" + fileName + ".txt");
            FileUtils.writeStringToFile(file, coins, "UTF-8", true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //test with tomcat Server
    public static void sendCoins(){
        int attempts = 1;
        String serverData = "";
        String fileLocation = "";
        String fileContent = "";
        String username = "";
        File coinFile;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter in the username you wish to send to: ");
        username = scanner.nextLine();

        System.out.print("Please enter in the coin files exact location: ");
        fileLocation = scanner.nextLine();
        coinFile = new File(fileLocation);

        while((attempts<3) && !coinFile.exists()){
            attempts++;
            System.out.print("The file in question was not found. Please try again: ");
            fileLocation = scanner.nextLine();
            coinFile = new File(fileLocation);
        }

        if (coinFile.exists()) {
            try {
                fileContent = FileUtils.readFileToString(coinFile, "UTF-8");
                //send username and fileContent to server. Return server message to serverData
                System.out.println(serverData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("File was not found after 3 attempts. Please try again.");
        }

    }

    //simplify code
    public static void sendCoins(String user, String fileLocation){
        File coinFile = new File(fileLocation);
        if(coinFile.exists()){
            System.out.println("CoinFile in question has sent.");
            //send("");
        }
        else{
            System.out.println("The file in question was not found. Please try again.");
        }

    }

    //simplify code
    public static void createAccount(){
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        String confirmPassword;
        boolean usernameTaken;
        boolean samePassword = false;

        System.out.print("Please enter in a username to create: ");
        username = scanner.nextLine();

        usernameTaken = false;//Send to Server and test

        for(int i=0; i<2; i++){
            if(usernameTaken/*If username is taken*/){
                System.out.print("The username you have used is already in use. please enter in a different name: ");
                username = scanner.nextLine();
                usernameTaken = true;//send to Server and test
            }
            else
                break;
        }

        if(usernameTaken){
            System.out.println("Three attempts have been made at choosing a username please try again.");
        }
        else {
            System.out.print("Please Enter in a password: ");
            password = scanner.nextLine();
            System.out.print("Please confirm your password: ");
            confirmPassword = scanner.nextLine();

            for(int i=0; i<2; i++){
                if(!password.equals(confirmPassword)) {
                    System.out.println("The passwords entered do not match. Please try again.");
                    System.out.print("Please Enter in a password: ");
                    password = scanner.nextLine();
                    System.out.print("Please confirm your password: ");
                    confirmPassword = scanner.nextLine();
                }
                else{
                    //send username and password to server to create account
                    samePassword = true;
                    break;
                }
            }

            if(samePassword){
                System.out.println("A new account has been create with the username of: " + username);
            }
            else{
                System.out.println("Three attempts to create a password have failed. Please try again.");
            }
        }

    }

    //simplify code
    public static void createAccount(String username, String password){
        boolean usernameTaken;
        usernameTaken = true;
        if(!usernameTaken){
            System.out.println("Created new account for " + username);
        }
        else{
            System.out.println("The username you have selected was already taken. Please try again.");
        }
    }

    //simplify code
    public static void deleteAccount(String sessionVar){
        Scanner scanner = new Scanner(System.in);
        String username;
        boolean deleted;
        deleted = false;

        if(!sessionVar.isEmpty()){
            System.out.println("Enter in a username to delete: ");
            username = scanner.nextLine();
            //send sessionVar and username to server
            if(deleted){
                System.out.println("The account " + username + " was deleted.");
            }
            else{
                System.out.println("The account " + username + " either does not exist or you do not have permission to\n" +
                        "delete it. Please try again.");
            }
        }

    }

    //simplify code
    public static void deleteAccount(String sessionVar, String userName){
        boolean deleted = false;//send sessionVar then send username
        if(deleted){
            System.out.println("The users account was successfully deleted.");
        }
        else{
            System.out.println("The user account was not deleted. You either do not have permission or have referred to \n" +
                    "an account that dosen't exist. Please try again.");
        }
    }

    //simplify code
    public static void transferScore(String sessionVar){
        String account;
        boolean success = true;
        String amount;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please Enter the name of the account that you want to transfer the coins to. ");
        account = scanner.nextLine();

        System.out.println("Please enter in the amount of coins you wish to transfer.");
        amount = scanner.nextLine();

        //send sessionVar
        if(success){
            System.out.println("You have transferred " + amount + " to " + account);
        }
        else{
            System.out.println("Your attempted transfer has failed. Please try again.");
        }

    }

    //simplify code
    public static void transferScore(String sessionVar, String account, String amount){
        boolean success = true;
        //send sessionVar account and amount to server
        if(success){
            System.out.println("You have transferred " + amount + " to " + account);
        }
        else {
            System.out.println("Your attempted transfer has failed. Please try again.");
        }

    }

    //simplify code
    public static void getBalance(String sessionVar){
        String balance;
        balance = "test";//Retrieve from server
        System.out.println(balance);
    }

    //simplify code
    public static void changePassword(){
        String account;
        String currentPassword;
        String newPassword;
        String newPasswordConfirm;
        String failMessage = "Password change has not been successful. Please try again";
        boolean passwordsMatch = false;
        boolean updated = false;
        Scanner scanner = new Scanner(System.in);
        //Console console = System.console();

        System.out.println("Please enter in the name of the account that you wish to change the password for.");
        account = scanner.nextLine();

        //String currentPassword = new String(console.readPassword("Please enter in password for the account: "));
        System.out.println("Please enter in password for the account: ");
        currentPassword = scanner.nextLine();

        //String newPassword = new String(console.readPassword("Please type in your new password: "));
        System.out.print("Please type in your new password: ");
        newPassword = scanner.nextLine();

        //String newPasswordConfirm = new String(Please confirm the new password: "));
        System.out.print("Please confirm the new password: ");
        newPasswordConfirm = scanner.nextLine();

        if(!newPassword.equals(newPasswordConfirm)){
            for(int i=0; i<2; i++){
                System.out.println("The passwords entered do not match. Please try again.");

                //String newPassword = new String(console.readPassword("Please type in your new password: "));
                System.out.print("Please type in your new password: ");
                newPassword = scanner.nextLine();

                //String newPasswordConfirm = new String(Please confirm the new password: "));
                System.out.print("Please confirm the new password: ");
                newPasswordConfirm = scanner.nextLine();

                if(newPassword.equals(newPasswordConfirm)){
                    passwordsMatch = true;
                    break;
                }
            }
        }
        else{
            passwordsMatch = true;
        }

        if(passwordsMatch){
            //send password and username data
            if(updated){
                System.out.println("Your password has been updated for " + account);
            }
            else{
                System.out.println(failMessage);
            }

        }
        else{
            System.out.println(failMessage);
        }

    }

    //simplify code
    public static void changePassword(String account, String currentPassword, String newPassword){
        boolean updated = false;
        //return success in updated send account, currentPassword and newPassword to server
        if(updated){
            System.out.println("The password for " + account + " has been updated.");
        }
        else{
            System.out.println("Password change has not been successful. Please try again.");
        }
    }

    //simplify code
    public static void createCoin(String sessionVar){
        String initialUser;
        String amount;
        boolean updated = false;
        Scanner scanner = new Scanner(System.in);


        System.out.print("Please type in an initial username for the coins: ");
        initialUser = scanner.nextLine();

        System.out.print("Please enter the number of coins you wish to create: ");
        amount = scanner.nextLine();

        //send sessionVar, initialUser and amount to server save success in updated
        if(updated){
            System.out.println(amount + " coins have been created for " + initialUser);
        }
        else{
            System.out.println("Coin creation has failed please try again.");
        }

    }

    //simplify code
    public static void createCoin(String sessionVar, String initialUser, String amount){
        boolean updated = false;
        //send sessionVar, initialUser and amount to server save success in updated

        if(updated){
            System.out.println(amount + " coins have been created for " + initialUser);
        }
        else{
            System.out.println("Coin creation has failed please try again.");
        }
    }

    //Will only work from terminal
    public static void clear(){
        String os = System.getProperty("os.name");

        try {
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

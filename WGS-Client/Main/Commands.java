
import bb.rackmesa.wargamescoring.User;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;


import java.io.*;
import java.net.CookieHandler;
import java.util.Scanner;

/**
 * Created by Alex on 10/12/2016.
 */
public class Commands {

    private static UserDataAdapter userDataAdapter = new UserDataAdapter();
    private static String serverURL = "http://localhost:8080/WGS-Server/";

    //TODO modify createAccount help to createUser
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

    public static void login(){
        String username;
        String password;
        int attempt = 1;
        boolean loggedIn;
        Scanner scanner = new Scanner(System.in);

        System.out.println("You will have 3 attempts to enter in your username and password");

        do {
            if(attempt > 1)
                System.out.println("Incorrect username or password please try again");

            System.out.println("Attempt " + attempt);
            System.out.print("Please enter your username: ");
            username = scanner.nextLine();

            //Console console = System.console();
            //password = new String(console.readPassword("Please enter your password: "));
            System.out.print("Please enter your password: ");
            password = scanner.nextLine();

            loggedIn = userDataAdapter.login(username, password);
            attempt++;
        }while((attempt<3)&& !loggedIn);

        if(loggedIn)
            System.out.println("Welcome you are now logged in.");
        else
            System.out.println("You have failed to login 3 times. Please try again.");

    }


    public static void login(String username, String password){
        boolean loggedIn;
        loggedIn = userDataAdapter.login(username, password);

        if(loggedIn)
            System.out.println("Welcome you are now logged in.");
        else
            System.out.println("You have failed to login.");
    }

    public static void logout(){
        userDataAdapter.logout();
        System.out.println("You have logged out.");
    }

    //TODO
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

    //TODO
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

    //TODO
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

    //TODO
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

    public static void createUser(){
        User user;
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        String role;
        String team;
        int score;
        boolean created;

        System.out.print("Please enter in a username to create: ");
        username = scanner.nextLine();

        System.out.print("Please enter in a password: ");
        password = scanner.nextLine();

        System.out.print("Please enter in a role for the user: ");
        role = scanner.nextLine();

        System.out.print("Please enter in a team for the user: ");
        team = scanner.nextLine();

        System.out.print("Please enter in a starting score as an integer: ");
        score = Integer.parseInt(scanner.nextLine());

        userDataAdapter.CreateUser(username, password, role, team, score);

    }

    public static void createUser(String username, String password, String role, String team, int score){
        userDataAdapter.CreateUser(username, password, role, team, score);
    }

    public static void deleteUser(){
        Scanner scanner = new Scanner(System.in);
        String username;

        System.out.println("Enter in a username to delete: ");
        username = scanner.nextLine();
        userDataAdapter.DeleteUser(username);
    }

    public static void deleteUser(String userName){
        userDataAdapter.DeleteUser(userName);
    }

    //TODO
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

    //TODO
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

    //TODO create interactive method
    public static void getBalance(){

    }

    //TODO
    public static void getBalance(String username){
        User userAccount = userDataAdapter.RetrieveUser(username);

        System.out.println(userAccount.getScore());
    }

    //TODO
    public static void changePassword(){
        String account;
        String currentPassword;
        String newPassword;
        String newPasswordConfirm;
        String failMessage = "Password change has not been successful. Please try again";
        int attempts = 1;
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

        while(!(newPassword.equals(newPasswordConfirm)) && (attempts < 3)){
            System.out.println("The passwords entered do not match. Please try again.");

            //String newPassword = new String(console.readPassword("Please type in your new password: "));
            System.out.print("Please type in your new password: ");
            newPassword = scanner.nextLine();

            //String newPasswordConfirm = new String(Please confirm the new password: "));
            System.out.print("Please confirm the new password: ");
            newPasswordConfirm = scanner.nextLine();
        }

        if(newPassword.equals(newPasswordConfirm)){
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

    //TODO
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

    //TODO
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

    //TODO
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

    //TODO
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

    //TODO
    public static void passwordConfirm(){
        System.out.println("");
    }

}

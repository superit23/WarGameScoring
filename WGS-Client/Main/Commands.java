
import bb.rackmesa.wargamescoring.User;
import org.apache.commons.io.FileUtils;


import java.io.*;
import java.util.Scanner;

/**
 * Created by Alex on 10/12/2016.
 */
public class Commands {

    private String user;
    private String cookie;
    private String serverURL = "http://localhost:8080/WGS-Server/";

    public Commands(){

    }

    //TODO createUser fix also check information on all commands
    public void help(String command){

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

    public void login(){
        String username;
        String password;
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        Scanner scanner = new Scanner(System.in);
        //Console console = System.console();
        cookie = null;

        System.out.println("Wlcome to the interactive login.");
        System.out.print("Please enter in your username: ");
        username = scanner.nextLine();

        //password = new String(console.readPassword("Please enter your password: "));
        System.out.print("Please enter in your password: ");
        password = scanner.nextLine();

        cookie = userDataAdapter.login(username, password);

        if(cookie != null)
            System.out.println("Welcome you are now logged in.");

        else
            System.out.println("You have failed to login.");
    }


    public void login(String username, String password){
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        cookie = userDataAdapter.login(username, password);

        if(cookie != null)
            System.out.println("Welcome you are now logged in.");
        else
            System.out.println("You have failed to login.");
    }

    public void logout(){
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);
        userDataAdapter.logout();
    }

    //TODO
    public void getCoins(String sessionVar){
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
    public void getCoins(String sessionVar, String fileName){
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
    public void sendCoins(){
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
    public void sendCoins(String user, String fileLocation){
        File coinFile = new File(fileLocation);
        if(coinFile.exists()){
            System.out.println("CoinFile in question has sent.");
            //send("");
        }
        else{
            System.out.println("The file in question was not found. Please try again.");
        }

    }

    public void createUser(){
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);
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

    public void createUser(String username, String password, String role, String team, int score){
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);
        userDataAdapter.CreateUser(username, password, role, team, score);
    }

    public void deleteUser(){
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);
        Scanner scanner = new Scanner(System.in);
        String username;

        System.out.print("Enter in a username to delete: ");
        username = scanner.nextLine();
        userDataAdapter.DeleteUser(username);
    }

    public void deleteUser(String userName){
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);
        userDataAdapter.DeleteUser(userName);
    }

    //TODO
    public void transferScore(String sessionVar){
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
    public void transferScore(String sessionVar, String account, String amount){
        boolean success = true;
        //send sessionVar account and amount to server
        if(success){
            System.out.println("You have transferred " + amount + " to " + account);
        }
        else {
            System.out.println("Your attempted transfer has failed. Please try again.");
        }

    }

    public void getBalance(){
        String username;
        Scanner scanner = new Scanner(System.in);
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);

        System.out.print("Enter in the username of the account who's balance you wish to check: ");
        username = scanner.nextLine();
        User userAccount = userDataAdapter.RetrieveUser(username);
        if(!(userAccount == null))
            System.out.println(username + " current Score is " + userAccount.getScore());
        else
            System.out.println("Unable to retrieve " + username + " balance.");
    }

    public void getBalance(String username){
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);
        User userAccount = userDataAdapter.RetrieveUser(username);
        if(!(userAccount == null))
            System.out.println(username + " current Score is " + userAccount.getScore());
        else
            System.out.println("Unable to retrieve " + username + " balance.");
    }

    //TODO
    public void changePassword(){
        String username;
        String password;
        Scanner scanner = new Scanner(System.in);
        //Console console = System.console();
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);

        System.out.print("Enter in the username who's password you wish to change: ");
        username = scanner.nextLine();

        System.out.print("Enter in the new password: ");
        //password = new String(console.readPassword("Please enter your password: "));
        password = scanner.nextLine();
        User user = userDataAdapter.RetrieveUser(username);
        user.setPassword(password);
        userDataAdapter.UpdateUser(user);

    }

    //TODO
    public void changePassword(String username, String newPassword){
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);
        User user = userDataAdapter.RetrieveUser(username);
        //user.setUserName(username);
        user.setCredentials(newPassword);
        //user.setPassword("null");
        userDataAdapter.UpdateUser(user);
    }

    //TODO
    public void createCoin(String sessionVar){
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
    public void createCoin(String sessionVar, String initialUser, String amount){
        boolean updated = false;
        //send sessionVar, initialUser and amount to server save success in updated

        if(updated){
            System.out.println(amount + " coins have been created for " + initialUser);
        }
        else{
            System.out.println("Coin creation has failed please try again.");
        }
    }

    public void clear(){
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
    public void passwordConfirm(){
        System.out.println("");
    }

}

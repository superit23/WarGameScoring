
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
                String loginHelp = "Type in this command to login and start a session with the server.\n" +
                        "You may either type the command by itself and use an interactive mode or type \n" +
                        "in the username and password after the command to login. \n" +
                        "Ex. login User Password";
                System.out.println(loginHelp);
                break;

            case "getCoins":
                clear();
                String getCoinsHelp = "Type in this command once you are logged in. You may use this method to get your coins from the server\n" +
                        "You may either type the command by itself and use an interactive mode or type \n" +
                        "in the name of the file you wish to put the coins in.\n" +
                        "Ex. getCoins File";
                System.out.println(getCoinsHelp);
                break;

            case "sendCoins":
                clear();
                String sendCoinsHelp = "This method is used to send coins to the bank to increase your score.\n" +
                        "You may either type in the command by itself and use an interactive mode \n" +
                        "or you may type in the coin file after the command to submit coins. \n" +
                        "Ex. sendCoins /home/user/file";
                System.out.println(sendCoinsHelp);
                break;

            case "createAccount":
                clear();
                String createAccountHelp = "This method is used to create a new User account.\n" +
                        "The create account method should be used by users looking to set up a new account.\n" +
                        "While there is no limit on the number of accounts you may have only one account will\n" +
                        "scored at the end of the semester. \n" +
                        "In interactive mode users will be asked to provide a username a password,\n" +
                        " and then to confirm the password. Providing that the username is not taken and \n" +
                        "the passwords entered match a new account will be created.\n" +
                        "A quick mode is available for createAccount. The quick mode will only request a unique \n" +
                        "username and a password. To use the quick command enter in the command and then \n" +
                        "a new username and a password. \n" +
                        "Ex. createAccount test password";
                System.out.println(createAccountHelp);
                break;

            case "deleteAccount":
                clear();
                String deleteAccountHelp = "This method is used to delete a User account.\n" +
                        "To delete an account you will need to be logged in and have permission to delete the account\n" +
                        "When an account is deleted the score of the account will be lost. deleteAccount supports both\n" +
                        "and interactive mode and a quick mode. In the interactive mode user will be asked to supply\n" +
                        "the name of the user account. In the quick mode users will enter in the command followed by a\n" +
                        "username. " +
                        "Ex. deleteAccount account";
                System.out.println(deleteAccountHelp);
                break;

            case "transferScore":
                clear();
                String transferScoreHelp = "This method is used to transfer score between two different users. You must\n" +
                        "be logged in and have proper permissions to transfer score between players. To start the\n" +
                        "interactive mode type in transferScore. transferScore also supports a quick mode. to use the \n" +
                        "quick mode type in transferScore followed by the name of the account and the amount you wish \n" +
                        "to transfer to the other player.\n" +
                        "Ex. transferScore account 10";
                System.out.println(transferScoreHelp);
                break;

            case "getBalance":
                clear();
                String getBalanceHelp = "This method is used to get your current balance. You must be logged in to use\n" +
                        "getBalance. Get balance only uses a quick mode. Just type in getBalance to use the command \n" +
                        "Ex. getBalance";
                System.out.println(getBalanceHelp);
                break;

            case "changePassword":
                clear();
                String changePasswordHelp = "This method is used to change your current password. changePassword supports\n" +
                        "both an interactive mode and a quick mode. To use the interactive mode type in changePassword.\n" +
                        "To use the quick mode type in changePassword then account name, the current password and a new" +
                        "password for the account.\n" +
                        "Ex. changePassword account currentPassword newPassword";
                System.out.println(changePasswordHelp);
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

    public static String login(){

        // This is temp code
        boolean loggedIn = false;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your username: ");
        String userName = scanner.nextLine();


        System.out.print("Please enter your password: ");
        String password = scanner.nextLine();

        for(int i=0; i<2; i++){
            //Console console = System.console();
            //String password = new String(console.readPassword("Please enter your password: "));


            if(false){
                System.out.println("Login successful.");
                loggedIn = true;
                break;
            }
            else{
                System.out.print("Incorrect username or password please try again: ");
                password = scanner.nextLine();
            }
        }


        if(!loggedIn){
            System.out.println("\nYou have gotten the password incorrect three times. Please try again.");
            return "";
        }

        return "sessionVar";
    }

    public static String login(String userName, String Password){
        //send Username send Password

        if(true){
            System.out.println("You are now logged in.");
            return "key";
        }
        else{
            System.out.println("Failed to login.");
            return "";
        }
    }

    public static void getCoins(String sessionVar){
        Scanner scanner = new Scanner(System.in);

        //http client command to get coin
        System.out.print("What would you like to name the file with your coins?: ");
        String fileName = scanner.nextLine();

        String coins = "coin";

        try {
            File file = new File("/" + fileName + ".txt");

            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(coins);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getCoins(String sessionVar, String fileName){
        //send Session Variable

        String coins = "test";

        try {
            File file = new File("/" + fileName + ".txt");

            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(coins);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendCoins(){
        Scanner scanner = new Scanner(System.in);
        File coinFile;
        System.out.print("Enter in the username you wish to send to: ");
        String username = scanner.nextLine();


        for (int i=0; i<3; i++) {

            System.out.print("Please enter in the coin files exact location: ");
            String fileLocation = scanner.nextLine();
            coinFile = new File(fileLocation);

            if(coinFile.exists()){
                System.out.println("Sending Coins");
                //send("");
                break;
            }
            else{
                System.out.println("The file in question was not found. Please try again.");
            }
        }

    }

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

    //Simplify code if possible
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

    public static void getBalance(String sessionVar){
        String balance;
        balance = "test";//Retrieve from server
        System.out.println(balance);
    }

    //finish writing
    public static void changePassword(){
        String account;
        String currentPassword;
        String newPassword;
        String newPasswordConfirm;
        Scanner scanner = new Scanner(System.in);
        //Console console = System.console();

        System.out.println("Please enter in the name of the account that you wish to change the password for.");
        account = scanner.nextLine();

        //String currentPassword = new String(console.readPassword("Please enter in password for the account."));
        System.out.println("Please enter in password for the account. ");
        currentPassword = scanner.nextLine();


        System.out.println("");




    }
    public static void changePassword(String account, String currentPassword, String newPassword){

    }

    //Will only work from terminal
    public static void clear(){
        String os = System.getProperty("os.name");

        try {
            if (os.contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

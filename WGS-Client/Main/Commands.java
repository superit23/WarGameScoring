import bb.rackmesa.wargamescoring.Coin;
import bb.rackmesa.wargamescoring.User;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Alex on 10/12/2016.
 */
public class Commands {

    private String user;
    private String cookie;
    private String saveLocation = System.getProperty("user.home") + "/";

    public Commands(){

    }

    public void help(String command){

        switch (command){

            case "login":
                clear();
                String loginHelp = "This method is used to login to the server. 'login' supports both an interactive\n" +
                        "mode and a quick mode. To use the interactive mode, simply type in 'login'. To use\n" +
                        "the quick mode, type 'login' followed by the username and then the password of the\n" +
                        "account.\n" +
                        "Ex. login User Password";
                System.out.println(loginHelp);
                break;

            case "logout":
                clear();
                String logoutHelp = "This method is used to log users out of the server.\n" +
                        "Ex. logout";
                System.out.println(logoutHelp);
                break;

            case "getCoins":
                clear();
                String getCoinsHelp = "This method is used to get your coins from the server. You must be logged in to\n" +
                        "use the 'getCoins' method. 'getCoins' supports both a quick mode and an interactive\n" +
                        "mode. To use the interactive mode, simply type in 'getCoins'. To use the quick\n" +
                        "mode type in 'getCoins' followed by the name of the file you wish to write the\n" +
                        "coins to.\n" +
                        "Ex. getCoins File";
                System.out.println(getCoinsHelp);
                break;

            case "depositCoins":
                clear();
                String depositCoinsHelp = "This method is used to send coins to the bank to increase your score. This \n" +
                        "method supports both a quick mode and an interactive mode. To use the\n" +
                        "interactive mode, simply type in 'depositCoins'. To use the quick mode, type in\n" +
                        "'depositCoins' followed by the username and the path to the coin file.\n" +
                        "Ex. depositCoins username /home/user/file";
                System.out.println(depositCoinsHelp);
                break;

            case "createUser":
                clear();
                String createUserHelp = "This method is used to create a new user account. The 'createUser' method should\n" +
                        "be used by admins looking to set up a new account. While there is no limit on \n" +
                        "on the number of accounts you may have, only one account will scored at the end\n" +
                        "of the semester. 'createUser' supports both a quick mode and an interactive mode.\n" +
                        "To use the interactive mode simply type in 'createUser'. To use the quick mode,\n" +
                        "type in 'createUser' followed by new username and a password. You must be logged\n" +
                        "in as an admin to use this command.\n" +
                        "Ex. createUser test password";
                System.out.println(createUserHelp);
                break;

            case "deleteUser":
                clear();
                String deleteAccountHelp = "This method is used to delete a user account. To delete an account, you will\n" +
                        "need to be logged in and have permission to delete the account. When an account\n" +
                        "is deleted, the score of the account will be lost. 'deleteUser' supports both an\n" +
                        "interactive mode and a quick mode. To use the interactive mode, simply type in\n" +
                        "'deleteUser'. To use the quick mode, type in 'deleteUser' followed by the username\n" +
                        "of the account you wish to delete.\n" +
                        "Ex. deleteUser user";
                System.out.println(deleteAccountHelp);
                break;

            case "transferScore":
                clear();
                String transferScoreHelp = "This method is used to transfer score between two different users. You must be\n" +
                        "logged in and have proper permissions to transfer score between players.\n" +
                        "'transferScore' supports both a quick mode and an interactive mode. To use the\n" +
                        "interactive mode, simply type in 'transferScore'. To use the quick mode, type in\n" +
                        "'transferScore' followed by the name of account you wish to transfer to and the\n" +
                        "amount you wish to transfer. Please use an integer for the amount.\n" +
                        "Ex. transferScore account 10";
                System.out.println(transferScoreHelp);
                break;

            case "getBalance":
                clear();
                String getBalanceHelp = "This method is used to get your current balance. You must be logged in to use\n" +
                        "'getBalance'. 'getBalance' supports both a quick mode and an interactive mode. To \n" +
                        "use the interactive mode, type in 'getBalance'. To use the quick mode, type in\n" +
                        "'getBalance' followed by the name of the account whose balance you wish to check.\n" +
                        "Ex. getBalance";
                System.out.println(getBalanceHelp);
                break;

            case "changePassword":
                clear();
                String changePasswordHelp = "This method is used to change your current password. 'changePassword' supports\n" +
                        "both an interactive mode and a quick mode. To use the interactive mode, type in\n" +
                        "'changePassword'. To use the quick mode type in 'changePassword' then account name,\n" +
                        "the current password and a new password for the account.\n" +
                        "Ex. changePassword account currentPassword newPassword";
                System.out.println(changePasswordHelp);
                break;

            case "updateUser":
                clear();
                String updateUserHelp = "This method is used to update a users information. 'updateUser' will not change\n" +
                        "a users password. To change a users password, use the changePassword method.\n" +
                        "'updateUser' supports both an interactive mode and a quick mode. To use the\n" +
                        "interactive mode, simply type in 'updateUser'. To use the quick mode, type in\n"+
                        "updateUser followed by the username of the account you wish to update, the new\n" +
                        "role of the user, the new team of the user, and the users new score.\n" +
                        "Ex. updateUser user admin team1 0";
                System.out.println(updateUserHelp);
                break;

            case "createCoin":
                clear();
                String createCoinHelp = "This method is used to create coins on the server for users to acquire.\n" +
                        "'createCoin' supports both an interactive mode and a quick mode. To use the\n" +
                        "interactive mode, type in 'createCoin'. To use the quick mode, type in 'createCoin'\n" +
                        "followed by and initial user and the amount of coins you wish to create. Please\n" +
                        "enter the amount in the form of an integer.\n" +
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
                        "login\n" +
                        "logout\n" +
                        "getCoins\n" +
                        "depositCoins\n" +
                        "createUser\n" +
                        "deleteUser\n" +
                        "transferScore\n" +
                        "getBalance\n" +
                        "changePassword\n" +
                        "updateUser\n" +
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

        System.out.println("Welcome to the interactive login.");
        System.out.print("Please enter in your username: ");
        username = scanner.nextLine();

        //password = new String(console.readPassword("Please enter your password: "));
        System.out.print("Please enter in your password: ");
        password = scanner.nextLine();

        cookie = userDataAdapter.login(username, password);
        user = username;

        if(cookie != null)
            System.out.println("Welcome, you are now logged in.");
        else
            System.out.println("You have failed to login.");

    }

    public void login(String username, String password){
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        cookie = userDataAdapter.login(username, password);
        user = username;
        if(cookie != null)
            System.out.println("Welcome, you are now logged in.");
        else
            System.out.println("You have failed to login.");
    }

    public void logout(){
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);
        userDataAdapter.logout();
        user = null;
    }

    public void getCoins(){
        String fileName;
        File file;
        Scanner scanner = new Scanner(System.in);

        if(!(user.isEmpty())){
            CoinDataAdapter coinDataAdapter = new CoinDataAdapter();
            coinDataAdapter.setSessionCookie(cookie);
            ArrayList<Coin> coinList = coinDataAdapter.RetrieveCoinsForUser(user);
            String coins = "";
            System.out.print("What would you like to name the file with your coins?: ");
            fileName = scanner.nextLine();

            for(int i=0; i<coinList.size();i++){
                coins += coinList.get(i).getCoin() + ";";
            }

            try {
                file = new File( saveLocation + fileName + ".txt");
                FileUtils.writeStringToFile(file, coins, "UTF-8", true);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            System.out.println("You must be logged in to get your coins");
        }

    }

    public void getCoins(String fileName){
        File file;
        if(!(user.isEmpty())){
            CoinDataAdapter coinDataAdapter = new CoinDataAdapter();
            coinDataAdapter.setSessionCookie(cookie);
            ArrayList<Coin> coinList = coinDataAdapter.RetrieveCoinsForUser(user);
            String coins = "";

            for(int i=0; i<coinList.size();i++){
                coins += coinList.get(i).getCoin() + ";";
            }
            try {
                file = new File( saveLocation + fileName + ".txt");
                FileUtils.writeStringToFile(file, coins, "UTF-8", true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("You must be logged in to get your coins");
        }

    }


    public void depositCoins(){
        int attempts = 1;
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
                String[] coins = fileContent.split(";");

                for(int i=0; i<coins.length; i++){
                    CoinDataAdapter coinDataAdapter = new CoinDataAdapter();
                    coinDataAdapter.setSessionCookie(cookie);
                    coinDataAdapter.depositCoin(coins[i], username);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("File was not found after 3 attempts. Please try again.");
        }

    }


    public void depositCoins(String username, String fileLocation){
        File coinFile = new File(fileLocation);
        if(coinFile.exists()){
            try {
                String fileContent = FileUtils.readFileToString(coinFile, "UTF-8");
                String[] coins = fileContent.split(";");

                for(int i=0; i<coins.length; i++){
                    CoinDataAdapter coinDataAdapter = new CoinDataAdapter();
                    coinDataAdapter.setSessionCookie(cookie);
                    coinDataAdapter.depositCoin(coins[i], username);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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


    public void transferScore(){

        if(!(user.isEmpty())){
            String account;
            String amount;
            Scanner scanner = new Scanner(System.in);
//            CoinDataAdapter coinDataAdapter = new CoinDataAdapter();
//            coinDataAdapter.setSessionCookie(cookie);

            System.out.print("Please enter the name of the account that you want to transfer the coins to: ");
            account = scanner.nextLine();

            System.out.print("Please enter in the amount of coins you wish to transfer: ");
            amount = scanner.nextLine();

//            coinDataAdapter.TransferScore(account, Integer.parseInt(amount));
            transferScore(account, amount);
        }
        else
            System.out.println("You must be logged in to transfer your score.");

    }


    public void transferScore(String account, String amount){
        if(!(user.isEmpty())){
            UserDataAdapter userDataAdapter = new UserDataAdapter();
            CoinDataAdapter coinDataAdapter = new CoinDataAdapter();
            userDataAdapter.setSessionCookie(cookie);
            coinDataAdapter.setSessionCookie(cookie);

            int reqScore = Integer.parseInt(amount);
            int realScore = userDataAdapter.RetrieveUser(user).getScore();

            coinDataAdapter.TransferScore(account, reqScore > realScore ? realScore : reqScore);
        }
        else
            System.out.println("You must be logged in to transfer your score.");
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

    public void changePassword(){
        String username;
        String password;
        Scanner scanner = new Scanner(System.in);
        //Console console = System.console();
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);

        System.out.print("Enter in the username whose password you wish to change: ");
        username = scanner.nextLine();

        System.out.print("Enter in the new password: ");
        //password = new String(console.readPassword("Please enter your password: "));
        password = scanner.nextLine();
        User user = userDataAdapter.RetrieveUser(username);
        if(user != null){
            user.setPassword(password);
            userDataAdapter.UpdateUser(user);
        }
        else{
            System.out.println("You do not have permission to change this users password.");
        }
    }

    public void changePassword(String username, String newPassword){
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);
        User user = userDataAdapter.RetrieveUser(username);
        if(user != null){
            user.setPassword(newPassword);
            userDataAdapter.UpdateUser(user);
        }
        else{
            System.out.println("You do not have permission to change this users password.");
        }
    }

    public void updateUser(){
        String username;
        String role;
        String team;
        String score;
        Scanner scanner = new Scanner(System.in);
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);
        User user;

        System.out.print("Enter in the username who's information you wish to change: ");
        username = scanner.nextLine();
        user = userDataAdapter.RetrieveUser(username);

        if(user != null){
            System.out.println("Leave areas that you do not wish to change blank.");

            System.out.print("Enter in a new user role or leave blank: ");
            role = scanner.nextLine();
            if(!role.isEmpty())
                user.setRole(role);

            System.out.print("Enter in a new user team or leave blank: ");
            team = scanner.nextLine();
            if(!team.isEmpty())
                user.setTeam(team);

            System.out.print("Enter in a new user score as an integer or leave blank: ");
            score = scanner.nextLine();
            if(!score.isEmpty())
                user.setScore(Integer.parseInt(score));

            userDataAdapter.UpdateUser(user);
        }
        else{
            System.out.println("You do not have permission to change this users information.");
        }

    }

    public void updateUser(String username, String role, String team, String score){
        UserDataAdapter userDataAdapter = new UserDataAdapter();
        userDataAdapter.setSessionCookie(cookie);
        User user = userDataAdapter.RetrieveUser(username);
        if(user != null){
            if(!role.isEmpty())
                user.setRole(role);
            if(!team.isEmpty())
                user.setTeam(team);
            if(!score.isEmpty())
                user.setScore(Integer.parseInt(score));
            userDataAdapter.UpdateUser(user);
        }
        else {
            System.out.print("You do not have permission to change this users information.");
        }

    }

    public void createCoin(){
        String initialUser;
        String amount;
        Scanner scanner = new Scanner(System.in);
        CoinDataAdapter coinDataAdapterTest = new CoinDataAdapter();
        coinDataAdapterTest.setSessionCookie(cookie);

        System.out.print("Please type in an initial username for the coins: ");
        initialUser = scanner.nextLine();

        System.out.print("Please enter the number of coins you wish to create: ");
        amount = scanner.nextLine();

        coinLoop(initialUser, amount);
    }

    public void createCoin(String initialUser, String amount){
        CoinDataAdapter coinDataAdapterTest = new CoinDataAdapter();
        coinDataAdapterTest.setSessionCookie(cookie);

        coinLoop(initialUser, amount);

    }

    private void coinLoop(String initialUser, String amount)
    {
        CoinDataAdapter coinDataAdapterTest = new CoinDataAdapter();
        coinDataAdapterTest.setSessionCookie(cookie);
        Coin coin = coinDataAdapterTest.CreateCoin(initialUser);

        if(coin != null) {
            int loop = Integer.parseInt(amount)-1;
            for (int i = 0; i < loop; i++) {
                CoinDataAdapter coinDataAdapter = new CoinDataAdapter();
                coinDataAdapter.setSessionCookie(cookie);
                coinDataAdapter.CreateCoin(initialUser);
            }
            System.out.println("Created " + amount + " coins for " + initialUser);
        }
        else{
            System.out.println("Failed to create coins");
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


}

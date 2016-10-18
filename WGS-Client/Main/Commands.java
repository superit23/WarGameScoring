package Main;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Alex on 10/12/2016.
 */
public class Commands {

    public static void help(String command){

        switch (command){

            case "login":
                String loginHelp = "Type in this command to login and start a session with the server.\n" +
                        "You may either type the command by itself and use an interactive mode or type \n" +
                        "in the username and password after the command to login. \n" +
                        "Ex. login User Password";
                System.out.println(loginHelp);
                break;

            case "getCoins":
                String getCoinsHelp = "Type in this command once you are logged in. You may use this method to  your coins from the server\n" +
                        "You may either type the command by itself and use an interactive mode or type \n" +
                        "in the name of the file you wish to put the coins in.\n" +
                        "Ex. getCoins File";
                System.out.println(getCoinsHelp);
                break;

            case "sendCoins":
                String sendCoinsHelp = "This method is used to send coins to the bank to increase your score\n" +
                        "You may either type in the command by itself and use an interactive mode or type \n" +
                        "or you may type in the coin file after the command to submit coins \n" +
                        "Ex. sendCoins /home/user/file";
                System.out.println(sendCoinsHelp);
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
                        "transferCoins\n" +
                        "changePassword \n" +
                        "createCoin \n" +
                        "exit \n";
                System.out.print(basicHelp);
        }


    }

    public static String login(){

        boolean loggedIn = false;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please Enter your username: ");
        String userName = scanner.nextLine();


        System.out.print("Please enter your password: ");


        for(int i=0; i<3; i++){
            //Console console = System.console();
            //String password = new String(console.readPassword("Please Enter your password: "));
            String password = scanner.nextLine();

            if(false){
                System.out.println("Correct You are now Logged in");
                loggedIn = true;
                break;
            }
            else{
                System.out.print("Incorrect Username or password please try again: ");
            }
        }


        if(!loggedIn){
            System.out.println("\nYou have gotten the incorrect Password 3 times Please try again.");
            return "";
        }

        return "sessionVar";
    }

    public static String login(String userName, String Password){
        //send Username send Password

        if(true){
            System.out.println("You are now Logged in.");
            return "key";
        }
        else{
            System.out.println("Failed to Login");
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

}

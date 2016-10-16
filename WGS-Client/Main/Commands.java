package Main;
import java.util.Scanner;

/**
 * Created by Alex on 10/12/2016.
 */
public class Commands {

    public static void help(String command){

        if(command.isEmpty()){
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

        System.out.print("Please Enter your username:");
        String userName = scanner.nextLine();

        System.out.print("Please Enter your pasword:");

        for(int i=0; i<3; i++){

            String passweord = scanner.nextLine();

            if(true){
                System.out.println("Correct You are now Logged in");
                loggedIn = true;
                break;
            }
            else{
                System.out.println("Incorrect Password Please try again:");
            }
        }


        if(!loggedIn){
            System.out.println("You have gotten the incorrect Password 3 times Please try again.");
            return "Wrong";
        }

        return "sessionVar";
    }

    public static void getCoin(String sessionVar){

    }

    public static void sendCoin(){


    }

}

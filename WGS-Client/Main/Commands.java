package Main;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
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

            if(false){
                System.out.println("Correct You are now Logged in");
                loggedIn = true;
                break;
            }
            else{
                System.out.print("Incorrect:");
            }
        }


        if(!loggedIn){
            System.out.println("You have gotten the incorrect Password 3 times Please try again.");
            return "";
        }

        return "sessionVar";
    }

    public static void getCoin(String sessionVar){
        //http client command to get coin


    }

    public static void sendCoins(){
        Scanner scanner = new Scanner(System.in);
        File coinFile;
        System.out.print("Enter in the username you wish to send to:");
        String username = scanner.nextLine();


        for (int i=0; i<3; i++) {

            System.out.print("Please enter in the coin files exact location:");
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

}

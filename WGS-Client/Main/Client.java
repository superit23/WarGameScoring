package Main;

import java.util.Scanner;

/**
 * Created by Alex on 10/12/2016.
 */
public class Client {
    public static void main(String[] args){
        String sessionVariable = "";
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(" ");

        System.out.println("Welcome to the client banking operations type help for assistance. ");

        while(loop){
            System.out.print(">");
            String input = scanner.nextLine();


            switch(input){

                case "help":
                    Commands.help("");
                    break;

                case "login":
                    sessionVariable = Commands.login();
                    break;

                case "createAccount":
                    System.out.println("createAccounts");
                    break;

                case "deleteAccount":
                    System.out.println("deleteAccount");
                    break;

                case "getCoins":
                    if(!sessionVariable.isEmpty())
                        Commands.sendCoins();
                    else
                        System.out.println("Please Login Before getting Coins.");
                    break;

                case "sendCoins":
                    Commands.sendCoins();
                    break;

                case "createCoin":
                    break;

                case "exit":
                    System.out.print("Thank you for using the Banking system!");
                    loop = false;
                    break;

                default:
                    //System.out.println("No command by that name was found. Type Help for help.");
                    break;

            }
        }


    }
}

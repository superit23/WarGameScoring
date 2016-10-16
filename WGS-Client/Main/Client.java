package Main;

import java.util.Scanner;

/**
 * Created by Alex on 10/12/2016.
 */
public class Client {
    public static void main(String[] args){
        String sessionVariable;
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(" ");

        System.out.println("Welcome to the client banking operations type help for assistance. ");

        while(loop){
            System.out.print(">");
            String input = scanner.nextLine();


            switch(input.toLowerCase()){

                case "help":
                    Commands.help("");
                    break;

                case "login":
                    sessionVariable = Commands.login();
                    break;

                case "createAccount":
                    break;

                case "deleteAccount":
                    break;

                case "getCoins":
                    break;

                case "sendCoins":
                    break;

                case "createCoin":
                    break;

                case "exit":
                    System.out.print("Thank you for using the Banking system!");
                    loop = false;
                    break;

                default:
                    break;

            }
        }


    }
}


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Alex on 10/12/2016.
 */
public class Client {
    public static void main(String[] args){
        String sessionVariable = "";
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);


        System.out.println("Welcome to the client banking operations. Type help for assistance. ");

        while(loop){
            System.out.print(">");
            String input = scanner.nextLine();
            ArrayList<String> commandsList = new ArrayList<String>(Arrays.asList(input.split("\\s+")));

            switch(commandsList.get(0)){

                case "help":
                    if(commandsList.size() > 1)
                        Commands.help(commandsList.get(1));
                    else
                        Commands.help("");
                    break;

                case "login":
                    if(commandsList.size() == 3)
                        sessionVariable = Commands.login(commandsList.get(1), commandsList.get(2));
                    else
                        sessionVariable = Commands.login();
                    break;

                case "getCoins":
                    if(!sessionVariable.isEmpty()){
                        if(commandsList.size() == 2)
                            Commands.getCoins(sessionVariable, commandsList.get(1));
                        else
                            Commands.getCoins(sessionVariable);
                    }
                    else{
                        System.out.println("Please Login before getting Coins.");
                    }
                    break;

                case "sendCoins":
                    if(commandsList.size() == 3)
                        Commands.sendCoins(commandsList.get(1), commandsList.get(2));
                    else
                        Commands.sendCoins();
                    break;

                case "createAccount":
                    if(commandsList.size() == 3)
                        Commands.createAccount(commandsList.get(1), commandsList.get(2));
                    else
                        Commands.createAccount();
                    break;

                case "deleteAccount":
                    if(!sessionVariable.isEmpty()){
                        if(commandsList.size() == 2)
                            Commands.deleteAccount(sessionVariable, commandsList.get(1));
                        else
                            Commands.deleteAccount(sessionVariable);
                    }
                    else
                        System.out.println("Please login before using deleteAccount.");
                    break;

                case "transferScore":
                    if(!sessionVariable.isEmpty()){
                        if(commandsList.size() ==3)
                            Commands.transferScore(sessionVariable, commandsList.get(1), commandsList.get(2));
                        else
                            Commands.transferScore(sessionVariable);
                    }
                    else
                        System.out.println("Please login before using transferScore.");
                    break;

                case "getBalance":
                    if(!sessionVariable.isEmpty())
                        Commands.getBalance(sessionVariable);
                    else
                        System.out.println("Please login before using getBalance");
                    break;

                case "changePassword":
                    if(commandsList.size() == 4)
                        Commands.changePassword(commandsList.get(1), commandsList.get(2), commandsList.get(3));
                    else
                        Commands.changePassword();
                    break;

                case "createCoin":
                    if(commandsList.size() == 2)
                        Commands.createCoin(sessionVariable, commandsList.get(1), commandsList.get(2));
                    else
                        Commands.createCoin(sessionVariable);
                    break;

                case "clear":
                    Commands.clear();
                    break;

                case "exit":
                    System.out.print("Thank you for using the banking system!");
                    loop = false;
                    break;

                default:
                    System.out.println("No command by that name was found. Type 'help' for help.");
                    break;

            }
        }


    }
}

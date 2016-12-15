
import bb.rackmesa.wargamescoring.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Alex on 10/12/2016.
 */
public class Client {
    public static void main(String[] args){

        try{
            Configuration.Init();
            Configuration.RegisterDrivers();
        }
        catch(Exception ex){

        }

        Commands commands = new Commands();
        Scanner scanner = new Scanner(System.in);
        String sessionVariable = "";
        boolean loop = true;

        System.out.println("Welcome to the client banking operations. Type help for assistance. ");

        while(loop){

            System.out.print(">");
            String input = scanner.nextLine();
            ArrayList<String> commandsList = new ArrayList<String>(Arrays.asList(input.split("\\s+")));

            switch(commandsList.get(0)){

                case "help":
                    if(commandsList.size() == 2)
                        commands.help(commandsList.get(1));
                    else
                        commands.help("");
                    break;

                case "login":
                    if(commandsList.size() == 3)
                        commands.login(commandsList.get(1), commandsList.get(2));
                    else
                        commands.login();
                    break;

                case "logout":
                    commands.logout();
                    break;

                case "getCoins":
                    if(!sessionVariable.isEmpty()){
                        if(commandsList.size() == 2)
                            commands.getCoins(commandsList.get(1));
                        else
                            commands.getCoins();
                    }
                    else{
                        System.out.println("Please Login before getting Coins.");
                    }
                    break;

                case "sendCoins":
                    if(commandsList.size() == 3)
                        commands.sendCoins(commandsList.get(1), commandsList.get(2));
                    else
                        commands.sendCoins();
                    break;

                case "createUser":
                    if(commandsList.size() == 6)
                        commands.createUser(commandsList.get(1), commandsList.get(2), commandsList.get(3), commandsList.get(4), Integer.parseInt(commandsList.get(5)));
                    else
                        commands.createUser();
                    break;

                case "deleteUser":
                    if(commandsList.size() == 2)
                        commands.deleteUser(commandsList.get(1));
                    else
                        commands.deleteUser();
                    break;

                case "transferScore":
                    if(!sessionVariable.isEmpty()){
                        if(commandsList.size() == 3)
                            commands.transferScore(commandsList.get(1), commandsList.get(2));
                        else
                            commands.transferScore();
                    }
                    else
                        System.out.println("Please login before using transferScore.");
                    break;

                case "getBalance":
                    if(commandsList.size() == 2){
                        commands.getBalance(commandsList.get(1));
                    }
                    else {
                        commands.getBalance();
                    }
                    break;

                case "changePassword":
                    if(commandsList.size() == 3)
                        commands.changePassword(commandsList.get(1), commandsList.get(2));
                    else
                        commands.changePassword();
                    break;

                case "updateUser":
                    if(commandsList.size() == 5)
                        commands.updateUser(commandsList.get(1), commandsList.get(2), commandsList.get(3), commandsList.get(4));
                    else
                        commands.updateUser();
                    break;

                case "createCoin":
                    if(commandsList.size() == 3)
                        commands.createCoin(commandsList.get(1), commandsList.get(2));
                    else
                        commands.createCoin();
                    break;

                case "clear":
                    commands.clear();
                    break;

                case "exit":
                    System.out.print("Thank you for using the banking system!");
                    commands.logout();
                    loop = false;
                    break;

                default:
                    System.out.println("No command by that name was found. Type 'help' for help.");
                    break;

            }
        }


    }
}

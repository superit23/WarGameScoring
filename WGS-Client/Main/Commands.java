package Main;

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

    public static void login(){
        System.out.println("Yeah stuff");
    }

}

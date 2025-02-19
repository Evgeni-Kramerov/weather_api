import model.User;
import view.View;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        View view = new View();

        view.printWelcomeMenu();
        String answer = "";
        try (Scanner scan = new Scanner(System.in)){
            boolean running = true;
            while(running) {
                answer = scan.nextLine().toLowerCase();
                switch(answer) {
                    case "1":
                        //Existing User
                        User existingUser = view.chooseExistingUserMenu(scan);
                        view.setActiveUser(existingUser);
                        view.renderViewForUser();
                        break;
                    case "2":
                        //New User
                        User newUser = view.createNewUser(scan);
                        view.setActiveUser(newUser);
                        view.renderViewForUser();
                        break;
                    case "3":
                        //Exit
                        running = false;
                        break;
                    default:
                        System.out.println("Couldn't read that, please try again?");
                        continue;
                }
                view.printWelcomeMenu();
            }
            System.out.println("Goodbye!");
        }

    }

}

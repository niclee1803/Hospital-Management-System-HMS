import login.LoginMenu;
import login.UserType;
import usermenus.DoctorMenu;
import usermenus.IMenu;
import usermenus.PatientMenu;

public class Main {
    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("\n" +
                    "██     ██ ███████ ██       ██████  ██████  ███    ███ ███████        ████████  ██████         ██   ██ ███    ███ ███████ \n" +
                    "██     ██ ██      ██      ██      ██    ██ ████  ████ ██                ██    ██    ██        ██   ██ ████  ████ ██      \n" +
                    "██  █  ██ █████   ██      ██      ██    ██ ██ ████ ██ █████             ██    ██    ██        ███████ ██ ████ ██ ███████ \n" +
                    "██ ███ ██ ██      ██      ██      ██    ██ ██  ██  ██ ██                ██    ██    ██        ██   ██ ██  ██  ██      ██ \n" +
                    " ███ ███  ███████ ███████  ██████  ██████  ██      ██ ███████           ██     ██████         ██   ██ ██      ██ ███████ \n" +
                    "                                                                                                                         \n" +
                    "                                                                                                                         \n");
            LoginMenu loginMenu = new LoginMenu();
            loginMenu.displayMenu();
            IMenu menu;
            if (loginMenu.getUserType() == UserType.PATIENT && loginMenu.isAuthenticated()) {
                menu = new PatientMenu(loginMenu.getId());
                menu.displayMenu();
            } else if (loginMenu.getUserType() == UserType.STAFF && loginMenu.isAuthenticated()) {
                switch (loginMenu.getId().charAt(0)) {
                    case 'D':
                        menu = new DoctorMenu(loginMenu.getId());
                        menu.displayMenu();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}

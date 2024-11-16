import login.LoginMenu;
import login.UserType;
import usermenus.AdministratorMenu;
import usermenus.DoctorMenu;
import usermenus.IUserMenu;
import usermenus.PatientMenu;
import usermenus.PharmacistMenu;

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
            IUserMenu menu;
            if (loginMenu.getUserType() == UserType.PATIENT && loginMenu.isAuthenticated()) {
                menu = new PatientMenu(loginMenu.getId());
                menu.mainMenu();
            } else if (loginMenu.getUserType() == UserType.STAFF && loginMenu.isAuthenticated()) {
                switch (loginMenu.getId().charAt(0)) {
                    case 'A':
                        menu = new AdministratorMenu(loginMenu.getId());
                        menu.mainMenu();
                        break;
                    case 'D':
                        menu = new DoctorMenu(loginMenu.getId());
                        menu.mainMenu();
                        break;
                    case 'P':
                        menu = new PharmacistMenu(loginMenu.getId());
                        menu.mainMenu(); 
                        break;
                    default:
                        break;
                }
            }
        }
    }
}

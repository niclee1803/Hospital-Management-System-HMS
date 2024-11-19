import login.LoginMenu;
import login.UserType;
import usermenus.AdministratorMenu;
import usermenus.DoctorMenu;
import usermenus.IUserMenu;
import usermenus.PatientMenu;
import usermenus.PharmacistMenu;

/**
 * The Main class serves as the entry point for the application.
 * It displays the welcome screen, handles user login, and redirects the user to the appropriate menu based on their role.
 *
 * <p>This class continuously runs a loop that allows users to log in and access their respective menus.
 * It checks the user's credentials and type, then displays a menu based on the user's role (Patient, Administrator, Doctor, or Pharmacist).</p>
 */
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

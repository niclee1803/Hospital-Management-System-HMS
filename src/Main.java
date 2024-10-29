import login.LoginManager;
import login.UserType;
import usermenus.DoctorMenu;
import usermenus.IHasMenu;
import usermenus.PatientMenu;
import appts.apptMain;

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
            LoginManager loginManager = new LoginManager();
            loginManager.login();
            IHasMenu menu;
            if (loginManager.getUserType() == UserType.PATIENT && loginManager.isAuthenticated()) {
                menu = new PatientMenu(loginManager.getId());
                menu.displayMenu();
            } else if (loginManager.getUserType() == UserType.STAFF && loginManager.isAuthenticated()) {
                switch (loginManager.getId().charAt(0)) {
                    case 'D':
                        menu = new DoctorMenu(loginManager.getId());
                        menu.displayMenu();
                        break;
                    default:
                        break;
                }
            }
        } 


    }
}

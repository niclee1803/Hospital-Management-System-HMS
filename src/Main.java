import Login.LoginManager;
import Login.UserType;
import UserMenus.DoctorMenu;
import UserMenus.PatientMenu;

import java.util.Scanner;

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
            if (loginManager.getUserType() == UserType.PATIENT && loginManager.isAuthenticated()) {
                PatientMenu patientMenu = new PatientMenu(loginManager.getId());
                patientMenu.displayMenu();
            } else if (loginManager.getUserType() == UserType.STAFF && loginManager.isAuthenticated()) {
                switch (loginManager.getId().charAt(0)) {
                    case 'D':
                        DoctorMenu doctorMenu = new DoctorMenu(loginManager.getId());
                        doctorMenu.displayMenu();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}

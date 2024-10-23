import Login.LoginManager;
import Login.UserType;
import User.Doctor;
import User.Patient;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
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
                Patient patient = new Patient(loginManager.getId());
                patient.displayMenu();
            } else if (loginManager.getUserType() == UserType.STAFF && loginManager.isAuthenticated()) {
                switch (loginManager.getId().charAt(0)) {
                    case 'D':
                        Doctor doctor = new Doctor(loginManager.getId());
                        doctor.displayMenu();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}

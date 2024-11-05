package managers;

import entities.User;
import filehandlers.FileHandler;

public abstract class UserManager {
    protected FileHandler fileHandler;

    protected UserManager(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public abstract User createUser(String id);

    public abstract String[] createRecordFromUser(User user);
}

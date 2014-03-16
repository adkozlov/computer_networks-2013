package coursework.common;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author adkozlov
 */
public final class UsersContainer {
    private final static UsersContainer INSTANCE = new UsersContainer();

    public static UsersContainer getInstance() {
        return INSTANCE;
    }

    private Map<String, Integer> authentication;
    private Map<String, String> students;
    private Map<String, String> lecturers;

    private static final String STUDENT_TYPE = "0";
    private static final String LECTURER_TYPE = "1";
    private static final String SEPARATOR = ", ";
    private static final int FIELDS_COUNT = 4;

    private UsersContainer() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(Configuration.AUTHENTICATION_DB_PATH));

            Map<String, Integer> authentication = new ConcurrentHashMap<>();
            Map<String, String> students = new ConcurrentHashMap<>();
            Map<String, String> lecturers = new ConcurrentHashMap<>();

            while (scanner.hasNextLine()) {
                String[] strings = scanner.nextLine().split(SEPARATOR);
                if (strings.length != FIELDS_COUNT) {
                    throw new DataBaseParsingException();
                }

                String name = strings[1];
                String login = strings[2];
                int passwordHashCode = strings[3].hashCode();

                switch (strings[0]) {
                    case STUDENT_TYPE:
                        students.put(login, name);
                        break;
                    case LECTURER_TYPE:
                        lecturers.put(login, name);
                        break;
                    default:
                        throw new DataBaseParsingException();
                }

                authentication.put(login, passwordHashCode);
            }

            this.authentication = Collections.unmodifiableMap(authentication);
            this.students = Collections.unmodifiableMap(students);
            this.lecturers = Collections.unmodifiableMap(lecturers);
        } catch (IOException e) {
            Logger.getInstance().logException(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static class DataBaseParsingException extends IOException {

        private DataBaseParsingException() {
            super("Cannot parse database");
        }
    }

    public boolean isAuthenticationPassed(boolean isStudent, String login, int passwordHashCode) {
        return authentication.containsKey(login) && authentication.get(login) == passwordHashCode && students.containsKey(login) == isStudent;
    }

    public String getName(String login) {
        return students.containsKey(login) ? students.get(login) : lecturers.get(login);
    }
}

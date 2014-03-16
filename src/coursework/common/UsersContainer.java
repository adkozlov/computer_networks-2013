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

    private Map<Integer, Integer> authentication;
    private Map<Integer, String> students;
    private Map<Integer, String> lecturers;

    private static final String STUDENT_TYPE = "0";
    private static final String LECTURER_TYPE = "1";
    private static final String SEPARATOR = ", ";
    private static final int FIELDS_COUNT = 4;

    private UsersContainer() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(Configuration.AUTHENTICATION_DB_PATH));

            Map<Integer, Integer> authentication = new ConcurrentHashMap<>();
            Map<Integer, String> students = new ConcurrentHashMap<>();
            Map<Integer, String> lecturers = new ConcurrentHashMap<>();

            while (scanner.hasNextLine()) {
                String[] strings = scanner.nextLine().split(SEPARATOR);
                if (strings.length != FIELDS_COUNT) {
                    throw new DataBaseParsingException();
                }

                String name = strings[1];
                int loginHashCode = strings[2].hashCode();
                int passwordHashCode = strings[3].hashCode();

                switch (strings[0]) {
                    case STUDENT_TYPE:
                        students.put(loginHashCode, name);
                        break;
                    case LECTURER_TYPE:
                        lecturers.put(loginHashCode, name);
                        break;
                    default:
                        throw new DataBaseParsingException();
                }

                authentication.put(loginHashCode, passwordHashCode);
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

    public boolean isAuthenticationPassed(int loginHashCode, int passwordHashCode) {
        return authentication.containsKey(loginHashCode) ? authentication.get(loginHashCode) == passwordHashCode : false;
    }

    public boolean isAuthenticationPassed(String login, String password) {
        return isAuthenticationPassed(login.hashCode(), password.hashCode());
    }

    public String getStudentName(int loginHashCode) {
        return students.get(loginHashCode);
    }

    public String getStudentName(String login) {
        return getStudentName(login.hashCode());
    }

    public String getLecturerName(int loginHashCode) {
        return lecturers.get(loginHashCode);
    }

    public String getLecturerName(String login) {
        return getLecturerName(login.hashCode());
    }

    public String getName(int loginHashCode) {
        String result = getStudentName(loginHashCode);

        if (result != null) {
            return result;
        }

        return getLecturerName(loginHashCode);
    }

    public String getName(String login) {
        return getName(login.hashCode());
    }
}

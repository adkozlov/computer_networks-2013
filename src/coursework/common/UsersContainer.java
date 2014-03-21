package coursework.common;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    private final Map<String, Integer> authentication;
    private final Map<String, Signature> signatures;
    private final Map<Signature, String> logins;
    private final Map<String, String> students;
    private final Map<String, String> lecturers;

    private static final String STUDENT_TYPE = "0";
    private static final String LECTURER_TYPE = "1";
    private static final String SEPARATOR = ", ";
    private static final int FIELDS_COUNT = 4;

    private UsersContainer() {
        Map<String, Integer> authentication = new ConcurrentHashMap<>();
        Map<String, Signature> signatures = new ConcurrentHashMap<>();
        Map<Signature, String> logins = new ConcurrentHashMap<>();
        Map<String, String> students = new ConcurrentHashMap<>();
        Map<String, String> lecturers = new ConcurrentHashMap<>();

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(Configuration.AUTHENTICATION_DB_PATH));

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

                add(login, signatures, logins);
            }
        } catch (IOException e) {
            Logger.getInstance().logException(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }

            for (int i = 0; i < Configuration.SERVERS_COUNT; i++) {
                add(String.format(Configuration.SERVER_NAME, i), signatures, logins);
            }

            this.authentication = Collections.unmodifiableMap(authentication);
            this.signatures = Collections.unmodifiableMap(signatures);
            this.logins = Collections.unmodifiableMap(logins);
            this.students = Collections.unmodifiableMap(students);
            this.lecturers = Collections.unmodifiableMap(lecturers);
        }
    }

    private static void add(String login, Map<String, Signature> signatures, Map<Signature, String> logins) {
        Signature signature = createSignature(login);
        signatures.put(login, signature);
        logins.put(signature, login);
    }

    private static class DataBaseParsingException extends IOException {

        private DataBaseParsingException() {
            super("Cannot parse database");
        }
    }

    public boolean isAuthenticationPassed(boolean isStudent, String login, int passwordHashCode) {
        return authentication.containsKey(login) && authentication.get(login) == passwordHashCode && isStudent(login) == isStudent;
    }

    public boolean isAuthenticated(Signature signature) {
        return logins.containsKey(signature);
    }

    public boolean isStudent(String login) {
        return students.containsKey(login);
    }

    public boolean isLecturer(String login) {
        return lecturers.containsKey(login);
    }

    public String getName(String login) {
        return students.containsKey(login) ? students.get(login) : lecturers.get(login);
    }

    public Signature getSignature(String login) {
        return signatures.get(login);
    }

    public String getLogin(Signature signature) {
        return logins.get(signature);
    }

    private static Signature createSignature(String login) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(Configuration.HASH_FUNCTION_NAME);
            messageDigest.reset();

            messageDigest.update(login.getBytes());
            return new Signature(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            Logger.getInstance().logException(e);
        }

        return null;
    }
}

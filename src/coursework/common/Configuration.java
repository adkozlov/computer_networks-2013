package coursework.common;

import java.io.File;

/**
 * @author adkozlov
 */
public final class Configuration {

    public static final String SERVER_IP = "localhost";

    public static final int AUTHENTICATION_PORT = 1234;
    public static final int STUDENTS_PORT = 1235;
    public static final int LECTURERS_PORT = 1236;
//    public static final int SYNCHRONIZATION_PORT = 1237;

    public static final int BUFFER_LENGTH = 4096;

    public static final String STORAGE_PATH = "storage" + File.separator;
    public static final String SERVER_FILES_PATH = STORAGE_PATH + "server" + File.separator;
    public static final String STUDENT_FILES_PATH = STORAGE_PATH + "student" + File.separator;
    public static final String LECTURER_FILES_PATH = STORAGE_PATH + "lecturer" + File.separator;

    public static final String AUTHENTICATION_DB_PATH = SERVER_FILES_PATH + "authentication.db";
}

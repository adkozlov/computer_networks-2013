package coursework.common;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @author adkozlov
 */
public final class Configuration {

    public static final int SERVERS_COUNT = 2;
    public static final String SERVER_NAME_FORMAT = "server%d";

    public static final int INT_BYTES_LENGTH = 4;

    protected static final String HASH_FUNCTION_NAME = "md5";
    protected static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    protected static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final long AUTO_UPDATE_PERIOD = 60000 / 10;

    public static final int STUDENTS_AUTHENTICATION_PORT = 1234;
    public static final int LECTURERS_AUTHENTICATION_PORT = 1235;
    public static final int STUDENTS_PORT = 1236;
    public static final int LECTURERS_PORT = 1237;
    public static final int STUDENTS_SERVER_PORT = 1238;
    public static final int LECTURERS_SERVER_PORT = 1239;
    public static final int SYNCHRONIZATION_PORT = 1240;

    public static final int BUFFER_LENGTH = 4096;

    public static final String STORAGE_PATH = "storage" + File.separator;
    public static final String SERVER_FILES_PATH = STORAGE_PATH + SERVER_NAME_FORMAT + File.separator;
    public static final String STUDENT_FILES_PATH = STORAGE_PATH + "student" + File.separator;
    public static final String LECTURER_FILES_PATH = STORAGE_PATH + "lecturer" + File.separator;

    public static final String SOLUTIONS_FOLDER = "solutions" + File.separator;
    public static final String VERDICTS_FOLDER = "verdicts" + File.separator;
    public static final String VERDICT_EXTENSION = ".vrd";
    public static final String TASKS_FOLDER = "tasks" + File.separator;
    public static final String TASK_EXTENSION = ".tsk";

    public static final String AUTHENTICATION_DB_PATH = STORAGE_PATH + "authentication.db";

    public static final String FILE_DELIMITER = "__";

    public static final String SOLUTION_FILE_FORMAT = "%s" + SOLUTIONS_FOLDER + "%s" + File.separator + "%s" + File.separator + "%s" + File.separator + "%s" + FILE_DELIMITER + "%s";
    public static final String VERDICT_FILE_FORMAT = "%s" + VERDICTS_FOLDER + "%s" + File.separator + "%s" + File.separator + "%s" + File.separator + "%s" + FILE_DELIMITER + "%s" + VERDICT_EXTENSION;
    public static final String TASK_FILE_FORMAT = "%s" + TASKS_FOLDER + "%s" + File.separator + "%s" + FILE_DELIMITER + "%s" + TASK_EXTENSION;
}

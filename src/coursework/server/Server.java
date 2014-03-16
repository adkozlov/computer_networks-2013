package coursework.server;

import coursework.common.Configuration;
import coursework.common.Utils;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class Server implements Runnable {

    public static void main(String[] args) {
        Utils.startRunnable(new Server());
    }

    private final ServerRunnable[] SERVERS = {new AuthenticationRunnable(), new StudentsRunnable(), new LecturersRunnable()};

    @Override
    public void run() {
        for (Runnable runnable : SERVERS) {
            Utils.startRunnable(runnable);
        }
    }


    private class AuthenticationRunnable extends ServerRunnable {
        @Override
        protected int getPort() {
            return Configuration.AUTHENTICATION_PORT;
        }

        @Override
        protected void readAndWrite(Socket socket) throws IOException {

        }
    }

    private class StudentsRunnable extends ServerRunnable {
        @Override
        protected int getPort() {
            return Configuration.STUDENTS_PORT;
        }

        @Override
        protected void readAndWrite(Socket socket) throws IOException {

        }
    }

    private class LecturersRunnable extends ServerRunnable {
        @Override
        protected int getPort() {
            return Configuration.LECTURERS_PORT;
        }

        @Override
        protected void readAndWrite(Socket socket) throws IOException {

        }
    }
}

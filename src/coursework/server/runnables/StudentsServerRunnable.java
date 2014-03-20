package coursework.server.runnables;

import coursework.common.Configuration;
import coursework.common.messages.SolutionMessage;
import coursework.common.model.Solution;
import coursework.server.Server;

import java.io.IOException;
import java.net.Socket;

/**
 * @author adkozlov
 */
public class StudentsServerRunnable extends ServerRunnable {

    public StudentsServerRunnable(Server server) {
        super(server);
    }

    @Override
    protected int getPort() {
        return Configuration.STUDENTS_PORT;
    }

    @Override
    protected void readAndWrite(Socket socket) throws IOException {
        Solution solution = readMessage(readBytes(socket)).getSolution();
        writeSolution(solution);
        getServer().addSolution(solution);
    }

    @Override
    protected SolutionMessage readMessage(byte[] bytes) throws IOException {
        return new SolutionMessage(bytes);
    }

    @Override
    public boolean isStudentsObject() {
        return true;
    }
}
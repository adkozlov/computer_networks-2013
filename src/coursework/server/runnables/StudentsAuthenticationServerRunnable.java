package coursework.server.runnables;

import coursework.common.Configuration;
import coursework.server.Server;

/**
 * @author adkozlov
 */
public class StudentsAuthenticationServerRunnable extends AuthenticationServerRunnable {

    public StudentsAuthenticationServerRunnable(Server server) {
        super(server);
    }

//    @Override
//    protected void writeAll(Socket socket, Signature signature) throws IOException {
//        for (Map.Entry<Signature, Task> tasksEntry : getServer().getTasks().entrySet()) {
//            Task task = new Task(tasksEntry.getValue(), signature);
//
//            writeMessage(socket, new TaskMessage(task));
//            getServer().sendTask(signature, task);
//        }
//
//        for (Map.Entry<Signature, Verdict> verdictsEntry : getServer().getVerdicts().entrySet()) {
//            Verdict verdict = new Verdict(verdictsEntry.getValue(), signature);
//
//            writeMessage(socket, new VerdictMessage(verdict));
//            getServer().sendVerdict(signature, verdict);
//        }
//    }

    @Override
    protected int getPort() {
        return Configuration.STUDENTS_AUTHENTICATION_PORT;
    }

    @Override
    public boolean isStudentsObject() {
        return true;
    }
}

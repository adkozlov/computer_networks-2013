package coursework.server.runnables;

import coursework.common.Configuration;
import coursework.server.Server;

/**
 * @author adkozlov
 */
public class LecturersAuthenticationServerRunnable extends AuthenticationServerRunnable {

    public LecturersAuthenticationServerRunnable(Server server) {
        super(server);
    }

//    @Override
//    protected void writeAll(Socket socket, Signature signature) throws IOException {
//        for (Map.Entry<Signature, Solution> solutionsEntry : getServer().getSolutions().entrySet()) {
//            Solution solution = new Solution(solutionsEntry.getValue(), signature);
//
//            writeMessage(socket, new SolutionMessage(solution));
//            getServer().sendSolution(signature, solution);
//        }
//    }

    @Override
    protected int getPort() {
        return Configuration.LECTURERS_AUTHENTICATION_PORT;
    }

    @Override
    public boolean isStudentsObject() {
        return false;
    }
}

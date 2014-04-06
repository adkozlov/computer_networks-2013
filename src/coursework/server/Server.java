package coursework.server;

import coursework.common.*;
import coursework.common.model.SignedObject;
import coursework.common.model.Solution;
import coursework.common.model.Task;
import coursework.common.model.Verdict;
import coursework.server.runnables.*;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author adkozlov
 */
public final class Server extends Thread implements ICleanable {

    private final Connection anotherServerConnection;

    public Server(InetAddress anotherServerAddress) {
        anotherServerConnection = new Connection(anotherServerAddress, UsersContainer.getInstance().getSignature(Configuration.SERVER_NAME));

        Utils.cleanDirectory(getStoragePath());
    }

    public Connection getServerConnection() {
        return anotherServerConnection;
    }

    private final ServerRunnable[] SERVERS = {new StudentsAuthenticationServerRunnable(this), new LecturersAuthenticationServerRunnable(this), new StudentsServerRunnable(this), new LecturersServerRunnable(this)};
    private final SynchronizationServerRunnable synchronization = new SynchronizationServerRunnable(this);

    @Override
    public void run() {
        for (ServerRunnable serverRunnable : SERVERS) {
            serverRunnable.start();
        }
        synchronization.start();
    }

    private final Map<Signature, Task> tasks = new ConcurrentHashMap<>();

    public void addTask(Task task, boolean isServer) {
        add(task, tasks, sentTasks, isServer);
    }

    public Map<Signature, Task> getTasks() {
        return tasks;
    }

    private final Map<Signature, Solution> solutions = new ConcurrentHashMap<>();

    public void addSolution(Solution solution, boolean isServer) {
        add(solution, solutions, sentSolutions, isServer);
    }

    public Map<Signature, Solution> getSolutions() {
        return solutions;
    }

    private final Map<Signature, Verdict> verdicts = new ConcurrentHashMap<>();

    public void addVerdict(Verdict verdict, boolean isServer) {
        add(verdict, verdicts, sentVerdicts, isServer);
    }

    public Map<Signature, Verdict> getVerdicts() {
        return verdicts;
    }

    private final Map<Connection, Set<Task>> sentTasks = new ConcurrentHashMap<>();

    public Map<Connection, Set<Task>> getSentTasks() {
        return sentTasks;
    }

    private final Map<Connection, Set<Solution>> sentSolutions = new ConcurrentHashMap<>();

    public Map<Connection, Set<Solution>> getSentSolutions() {
        return sentSolutions;
    }

    private final Map<Connection, Set<Verdict>> sentVerdicts = new ConcurrentHashMap<>();

    public Map<Connection, Set<Verdict>> getSentVerdicts() {
        return sentVerdicts;
    }

    public static <E extends SignedObject> boolean isSent(Connection connection, E signedObject, Map<Connection, Set<E>> sentSignedObjects) {
        return sentSignedObjects.containsKey(connection) && sentSignedObjects.get(connection).contains(signedObject);
    }

    public static <E extends SignedObject> void send(Connection connection, E signedObject, Map<Connection, Set<E>> sentSignedObjects) {
        Set<E> signedObjects = sentSignedObjects.containsKey(connection) ? sentSignedObjects.get(connection) : new HashSet<E>();

        signedObjects.add(signedObject);

        sentSignedObjects.put(connection, signedObjects);
    }

    private <E extends SignedObject> void synchronize(E signedObject, Map<Connection, Set<E>> sentSignedObjects) {
        if (!isSent(getServerConnection(), signedObject, sentSignedObjects)) {
            synchronization.synchronize(signedObject);

            send(getServerConnection(), signedObject, sentSignedObjects);
        }
    }

    private <E extends SignedObject> void add(E signedObject, Map<Signature, E> signedObjects, Map<Connection, Set<E>> sentSignedObjects, boolean isServer) {
        if (!signedObjects.values().contains(signedObject)) {
            signedObjects.put(signedObject.getSignature(), signedObject);

            if (!isServer) {
                synchronize(signedObject, sentSignedObjects);
            }
        }
    }

    @Override
    public String getStoragePath() {
        return Configuration.SERVER_FILES_PATH;
    }
}

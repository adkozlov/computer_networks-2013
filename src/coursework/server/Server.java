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

    private final InetAddress anotherServerAddress;

    public Server(InetAddress anotherServerAddress) {
        this.anotherServerAddress = anotherServerAddress;

        Utils.cleanDirectory(getStoragePath());
    }

    public InetAddress getAnotherServerAddress() {
        return anotherServerAddress;
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

    public void addTask(Task task) {
        add(task, tasks, sentTasks);
    }

    public Map<Signature, Task> getTasks() {
        return tasks;
    }

    private final Map<Signature, Solution> solutions = new ConcurrentHashMap<>();

    public void addSolution(Solution solution) {
        add(solution, solutions, sentSolutions);
    }

    public Map<Signature, Solution> getSolutions() {
        return solutions;
    }

    private final Map<Signature, Verdict> verdicts = new ConcurrentHashMap<>();

    public void addVerdict(Verdict verdict) {
        add(verdict, verdicts, sentVerdicts);
    }

    public Map<Signature, Verdict> getVerdicts() {
        return verdicts;
    }

    private final Map<Signature, Set<Task>> sentTasks = new ConcurrentHashMap<>();

    public Map<Signature, Set<Task>> getSentTasks() {
        return sentTasks;
    }

    private final Map<Signature, Set<Solution>> sentSolutions = new ConcurrentHashMap<>();

    public Map<Signature, Set<Solution>> getSentSolutions() {
        return sentSolutions;
    }

    private final Map<Signature, Set<Verdict>> sentVerdicts = new ConcurrentHashMap<>();

    public Map<Signature, Set<Verdict>> getSentVerdicts() {
        return sentVerdicts;
    }

    public static <E extends SignedObject> boolean isSent(Signature signature, E signedObject, Map<Signature, Set<E>> sentSignedObjects) {
        return sentSignedObjects.containsKey(signature) && sentSignedObjects.get(signature).contains(signedObject);
    }

    public static <E extends SignedObject> void send(Signature signature, E signedObject, Map<Signature, Set<E>> sentSignedObjects) {
        Set<E> signedObjects = sentSignedObjects.containsKey(signedObject) ? sentSignedObjects.get(signedObject) : new HashSet<E>();
        signedObjects.add(signedObject);

        sentSignedObjects.put(signature, signedObjects);
    }

    private <E extends SignedObject> void synchronize(E signedObject, Map<Signature, Set<E>> sentSignedObjects) {
        if (!isSent(getServerSignature(), signedObject, sentSignedObjects)) {
            synchronization.synchronize(signedObject);

            send(getServerSignature(), signedObject, sentSignedObjects);
        }
    }

    private <E extends SignedObject> void add(E signedObject, Map<Signature, E> signedObjects, Map<Signature, Set<E>> sentSignedObjects) {
        if (!signedObjects.values().contains(signedObject)) {
            signedObjects.put(signedObject.getSignature(), signedObject);
            synchronize(signedObject, sentSignedObjects);
        }
    }

    public static Signature getServerSignature() {
        return UsersContainer.getInstance().getSignature(Configuration.SERVER_NAME);
    }

    @Override
    public String getStoragePath() {
        return Configuration.SERVER_FILES_PATH;
    }
}

package coursework.server;

import coursework.common.Signature;
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
public final class Server extends Thread {

    private final int serverId;
    private final InetAddress anotherServerAddress;

    public Server(int serverId, InetAddress anotherServerAddress) {
        this.serverId = serverId;
        this.anotherServerAddress = anotherServerAddress;
    }

    public int getServerId() {
        return serverId;
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
        tasks.put(task.getSignature(), task);
        synchronization.synchronize(task);
    }

    public Map<Signature, Task> getTasks() {
        return tasks;
    }

    private final Map<Signature, Solution> solutions = new ConcurrentHashMap<>();

    public void addSolution(Solution solution) {
        solutions.put(solution.getSignature(), solution);
        synchronization.synchronize(solution);
    }

    public Map<Signature, Solution> getSolutions() {
        return solutions;
    }

    private final Map<Signature, Verdict> verdicts = new ConcurrentHashMap<>();

    public void addVerdict(Verdict verdict) {
        verdicts.put(verdict.getSignature(), verdict);
        synchronization.synchronize(verdict);
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
}

package kone.nassara.m1.ccsr.wok;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Restaurant {
    private final int CAPACITE_MAX = 25;
    private int placesOccupees = 0;
    private final Buffet buffet = new Buffet();
    private final StandCuisson standCuisson = new StandCuisson();
    private final Cuisinier cuisinier = new Cuisinier(standCuisson, this);
    private final BlockingQueue<Client> clientsDansRestaurant = new LinkedBlockingQueue<>();
    private final EmployeBuffet employeBuffet = new EmployeBuffet(buffet, 1000);
    private final ExecutorService poolClients = Executors.newFixedThreadPool(10);

    // MÉTRIQUES
    private final AtomicInteger totalPlatsCuits = new AtomicInteger(0);
    private long totalTempsAttenteBuffet = 0;
    private int totalClients = 0;

    public void startSimulation(int nbClients) {
        totalClients = nbClients;
        new Thread(employeBuffet).start();
        cuisinier.start();

        for (int i = 1; i <= nbClients; i++) {
            poolClients.submit(new Client(i, this));
        }
        poolClients.shutdown(); // Arrêter le pool après tous les clients
    }

    public synchronized void entrer(Client client) throws InterruptedException {
        while (placesOccupees >= CAPACITE_MAX) {
            wait();
        }
        placesOccupees++;
        Logger.log("Client " + client.getClientId() + " entre dans le restaurant.");
    }

    public synchronized void sortir(Client client) {
        placesOccupees--;
        clientsDansRestaurant.remove(client);
        Logger.log("Client " + client.getClientId() + " quitte le restaurant.");
        notifyAll();
    }

    public Buffet getBuffet() {
        return buffet;
    }

    public StandCuisson getStandCuisson() {
        return standCuisson;
    }

    public void incrementerPlatsCuits() {
        totalPlatsCuits.incrementAndGet();
    }

    public synchronized void ajouterTempsAttenteBuffet(long temps) {
        totalTempsAttenteBuffet += temps;
    }

    public int getTotalPlatsCuits() {
        return totalPlatsCuits.get();
    }

    public long getTotalTempsAttenteBuffet() {
        return totalTempsAttenteBuffet;
    }

    public int getTotalClients() {
        return totalClients;
    }
}

package kone.nassara.m1.ccsr.wok;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private final int CAPACITE_MAX = 25;
    private int placesOccupees = 0;
    private final Buffet buffet = new Buffet();
    private final StandCuisson standCuisson = new StandCuisson();
    private final Cuisinier cuisinier = new Cuisinier(standCuisson);
    private final EmployeBuffet employeBuffet = new EmployeBuffet(buffet);
    private final BlockingQueue<Client> fileAttente = new LinkedBlockingQueue<>();

    public void startSimulation(int nbClients) {
        // Démarrer les employés
        new Thread(employeBuffet).start();
        cuisinier.start();

        // Créer et démarrer les clients
        for (int i = 1; i <= nbClients; i++) {
            Client client = new Client(i, this);
            new Thread(client).start();
        }
    }

    public synchronized void entrer(Client client) throws InterruptedException {
        while (placesOccupees >= CAPACITE_MAX) {
            System.out.println("Client " + client.getClientId() + " attend une place...");
            wait();
        }
        placesOccupees++;
        System.out.println("Client " + client.getClientId() + " entre dans le restaurant.");
    }

    public synchronized void sortir(Client client) {
        placesOccupees--;
        System.out.println("Client " + client.getClientId() + " quitte le restaurant.");
        notifyAll(); // Libérer une place pour un autre client
    }

    public Buffet getBuffet() {
        return buffet;
    }

    public StandCuisson getStandCuisson() {
        return standCuisson;
    }

    public Cuisinier getCuisinier() {
        return cuisinier;
    }
}

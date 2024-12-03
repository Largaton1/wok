package kone.nassara.m1.ccsr.wok;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class StandCuisson {
    private final BlockingQueue<Client> fileCuisson = new LinkedBlockingQueue<>();

    public void ajouterClient(Client client) {
        try {
            fileCuisson.put(client);
        } catch (InterruptedException e) {
            System.err.println("Erreur lors de l'ajout du client au stand de cuisson.");
        }
    }

    public Client prochainClient() throws InterruptedException {
        return fileCuisson.take();
    }
}

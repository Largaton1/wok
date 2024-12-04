package kone.nassara.m1.ccsr.wok;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private final List<Client> clients = new ArrayList<>();
    private final StandCuisson standCuisson = new StandCuisson();
    private final Cuisinier cuisinier = new Cuisinier(standCuisson, this);

    private int totalPlatsCuits = 0;  // Compteur des plats cuits
    private int totalLegumes = 0;     // Consommation totale de légumes
    private int totalViande = 0;      // Consommation totale de viande
    private int totalPoisson = 0;     // Consommation totale de poisson
    private int totalNouilles = 0;    // Consommation totale de nouilles

    private final int MAX_PLACES = 25; // Limite du nombre de places
    private int clientsDansRestaurant = 0;  // Nombre de clients actuellement dans le restaurant

    // Démarre la simulation avec un nombre de clients
    public void startSimulation(int nbClients) {
        // Crée et démarre les clients
        for (int i = 1; i <= nbClients; i++) {
            Client client = new Client(i, this);
            new Thread(client).start();  // Démarre chaque client dans un thread
        }

        // Démarre le cuisinier
        cuisinier.start();
    }

    // Attendre la fin de tous les clients et du cuisinier
    public void attendreFinSimulation() {
        try {
            // Attendre que tous les clients finissent
            for (Client client : clients) {
                client.join();
            }

            // Attendre que le cuisinier termine
            cuisinier.interrupt(); // Arrêter le cuisinier
            cuisinier.join();

      
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    

    public synchronized boolean entrer(Client client) {
        // Si le restaurant est plein, le client attend
        while (clientsDansRestaurant >= MAX_PLACES) {
            try {
                System.out.println("Client " + client.getClientId() + " attend pour entrer dans le restaurant.");
                wait();  // Attente qu'une place se libère
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Ajouter le client dans le restaurant
        clientsDansRestaurant++;
        System.out.println("Client " + client.getClientId() + " entre dans le restaurant.");
        return true;
    }

    public synchronized void sortir(Client client) {
        // Le client quitte le restaurant et une place se libère
        clientsDansRestaurant--;
        System.out.println("Client " + client.getClientId() + " quitte le restaurant.");
        notify();  // Notifie les clients en attente
    }

    

    // Met à jour les statistiques de consommation chaque fois qu'un client se sert
    public void consommer(Compartiment compartiment, int quantite) {
        switch (compartiment) {
            case LEGUMES:
                totalLegumes += quantite;
                break;
            case VIANDE:
                totalViande += quantite;
                break;
            case POISSON:
                totalPoisson += quantite;
                break;
            case NOUILLES:
                totalNouilles += quantite;
                break;
        }
    }

    public StandCuisson getStandCuisson() {
        return standCuisson;
    }

    public Cuisinier getCuisinier() {
        return cuisinier;
    }

    public void incrementerPlatsCuits() {
        totalPlatsCuits++;
    }

    public int getPlatsCuits() {
        return totalPlatsCuits;
    }

    public int getTotalLegumes() {
        return totalLegumes;
    }

    public int getTotalViande() {
        return totalViande;
    }

    public int getTotalPoisson() {
        return totalPoisson;
    }

    public int getTotalNouilles() {
        return totalNouilles;
    }

    public List<Client> getClients() {
        return clients;
    }
}

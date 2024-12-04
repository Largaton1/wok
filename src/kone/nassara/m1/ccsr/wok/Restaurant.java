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

    // Démarre la simulation avec un nombre de clients
    public void startSimulation(int nbClients) {
        // Crée et démarre les clients
        for (int i = 1; i <= nbClients; i++) {
            Client client = new Client(i, this);
            clients.add(client);
            client.start();
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

            afficherResume();  // Afficher le résumé final
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour afficher le résumé de la simulation
    private void afficherResume() {
        System.out.println("\n=== Résumé Simulation ===");
        System.out.println("Nombre total de clients servis : " + clients.size());
        System.out.println("Nombre de plats cuits : " + totalPlatsCuits);
        System.out.println("Consommation totale par compartiment :");
        System.out.println("  - Légumes : " + totalLegumes + "g");
        System.out.println("  - Viande : " + totalViande + "g");
        System.out.println("  - Poisson : " + totalPoisson + "g");
        System.out.println("  - Nouilles : " + totalNouilles + "g");
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

	public Integer getPlatsCuits() {
		// TODO Auto-generated method stub
		return null;
	}
}

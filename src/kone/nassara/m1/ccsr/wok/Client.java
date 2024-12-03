package kone.nassara.m1.ccsr.wok;

import java.util.concurrent.ThreadLocalRandom;

public class Client extends Thread {
    private final int clientId;
    public int getClientId() {
		return clientId;
	}

	private final Restaurant restaurant;

    public Client(int clientId, Restaurant restaurant) {
        this.clientId = clientId;
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            // Entrer dans le restaurant
            restaurant.entrer(this);
            afficherAction("Entre dans le restaurant");

            // Choisir un compartiment aléatoire parmi tous les compartiments
            Compartiment[] compartiments = Compartiment.values();
            Compartiment compartimentChoisi = compartiments[ThreadLocalRandom.current().nextInt(compartiments.length)];

            // Se servir au buffet
            int tempsService = ThreadLocalRandom.current().nextInt(500, 1500); // Temps variable pour le service
            afficherAction("Se sert dans le compartiment " + compartimentChoisi + "...");
            Thread.sleep(tempsService);

            // Réaliser l'action de servir dans le compartiment choisi
            while (!restaurant.getBuffet().servir(compartimentChoisi, 200)) {
                afficherAction("Attente au buffet pour " + compartimentChoisi + "...");
                Thread.sleep(100); // Attente si le buffet est insuffisant
            }

            // Aller à la cuisson
            restaurant.getStandCuisson().ajouterClient(this);
            afficherAction("Attends la cuisson...");

            synchronized (this) {
                wait(); // Attendre de finir la cuisson
            }

            // Manger
            afficherAction("Mange...");

            // Quitter
            Thread.sleep(1000 + (int) (Math.random() * 1000)); // Temps de repas
            restaurant.sortir(this);
            afficherAction("Quitte le restaurant");

        } catch (InterruptedException e) {
            System.err.println("Erreur pour le client " + clientId + ": " + e.getMessage());
        }
    }

    // Affichage des actions des clients
    private void afficherAction(String action) {
        System.out.println("Client " + clientId + ": " + action);
    }
}

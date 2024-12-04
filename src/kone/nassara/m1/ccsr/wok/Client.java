package kone.nassara.m1.ccsr.wok;

public class Client extends Thread {
    private final int clientId;
    private final Restaurant restaurant;

    public Client(int clientId, Restaurant restaurant) {
        this.clientId = clientId;
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            // Entrer dans le restaurant
            System.out.println("Client " + clientId + " entre dans le restaurant.");

            // Choisir un compartiment et se servir (simulé ici)
            Compartiment[] compartiments = Compartiment.values();
            Compartiment compartimentChoisi = compartiments[(int) (Math.random() * compartiments.length)];

            System.out.println("Client " + clientId + " se sert dans " + compartimentChoisi + ".");

            // Simule la consommation
            int quantite = 200;  // Quantité consommée par client (par exemple, 200g)
            restaurant.consommer(compartimentChoisi, quantite);

            // Attente de la cuisson
            restaurant.getStandCuisson().ajouterClient(this);
            synchronized (this) {
                wait(); // Attendre la cuisson
            }

            // Manger
            System.out.println("Client " + clientId + " mange.");

            // Quitter le restaurant
            System.out.println("Client " + clientId + " quitte le restaurant.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getClientId() {
        return clientId;
    }
}

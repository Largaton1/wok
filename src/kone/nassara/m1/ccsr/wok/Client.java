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
            restaurant.entrer(this);  // Attente si le restaurant est plein

            // Choisir un compartiment et se servir
            Compartiment[] compartiments = Compartiment.values();
            Compartiment compartimentChoisi = compartiments[(int) (Math.random() * compartiments.length)];
            System.out.println("Client " + clientId + " se sert dans " + compartimentChoisi + ".");

            // Simule la consommation
            int quantite = 200;  // Quantité consommée par client (par exemple, 200g)
            restaurant.consommer(compartimentChoisi, quantite);  // Met à jour la consommation totale

            // Attente de la cuisson
            restaurant.getStandCuisson().ajouterClient(this);
            synchronized (this) {
                wait(); // Attendre la cuisson
            }

            // Manger
            System.out.println("Client " + clientId + " mange.");

            // Incrémenter le nombre de plats cuits
            restaurant.incrementerPlatsCuits();

            // Quitter le restaurant
            restaurant.sortir(this);  // Libère une place pour un autre client
            System.out.println("Client " + clientId + " quitte le restaurant.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public int getClientId() {
        return clientId;
    }
}

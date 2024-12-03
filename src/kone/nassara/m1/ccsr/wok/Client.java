package kone.nassara.m1.ccsr.wok;

public class Client extends Thread {
    private final int clientId;
    private final Restaurant restaurant;

    public Client(int clientId, Restaurant restaurant) {
        this.clientId = clientId;
        this.restaurant = restaurant;
    }

    public int getClientId() {
        return clientId;
    }

    @Override
    public void run() {
        try {
            restaurant.entrer(this);
            System.out.println("Client " + clientId + " se sert au buffet.");
            Thread.sleep(200 + (int) (Math.random() * 100));
            restaurant.getStandCuisson().ajouterClient(this);
            synchronized (this) {
                wait(); // Attend que le cuisinier termine
            }
            System.out.println("Client " + clientId + " mange son plat.");
            Thread.sleep(1000 + (int) (Math.random() * 1000));
            restaurant.sortir(this);
        } catch (InterruptedException e) {
            System.err.println("Erreur pour le client " + clientId + ": " + e.getMessage());
        }
    }
}

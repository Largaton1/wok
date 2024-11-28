package kone.nassara.m1.ccsr.wok;

public class Client extends Thread {
    private final int id;
    private final Restaurant restaurant;

    public Client(int id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void run() {
        try {
            // Étape 1 : Entrer dans le restaurant
            restaurant.entrer(this);

            // Étape 2 : Se servir au buffet
            restaurant.servirAuBuffet(this);

            // Étape 3 : Aller au stand de cuisson
            restaurant.attendreCuisson(this);

            // Étape 4 : Sortir du restaurant
            restaurant.sortir(this);
        } catch (Exception e) {
            System.err.println("Erreur pour le client " + id + ": " + e.getMessage());
        }
    }
}

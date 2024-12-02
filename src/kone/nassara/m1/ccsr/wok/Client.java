package kone.nassara.m1.ccsr.wok;

public class Client extends Thread {
    private final String nom;
    private final Restaurant restaurant;

    public Client(String nom, Restaurant restaurant) {
        this.nom = nom;
        this.restaurant = restaurant;
    }
    public String getNom() {
        return nom;
    }

  
    @Override
    public void run() {
        try {
            // Étape 1 : Entrer dans le restaurant
            restaurant.entrer(this);
            System.out.println(nom + " entre dans le restaurant.");

            // Simule un temps d'attente avant d'aller au buffet
            Thread.sleep(100);

            // Étape 2 : Se servir au buffet
            servirAuBuffet();

            // Étape 3 : Aller au stand de cuisson
            attendreCuisson();

            // Étape 4 : Simuler le temps de consommation
            manger();

            // Étape 5 : Sortir du restaurant
            restaurant.sortir(this);

        } catch (InterruptedException e) {
            System.err.println("Le client " + nom + " a été interrompu : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur pour le client " + nom + " : " + e.getMessage());
        }
    }

    private void servirAuBuffet() throws InterruptedException {
        System.out.println("Client " + nom + " est au buffet...");
        // Simule la prise de portions aléatoires
        Thread.sleep(200 + (int) (Math.random() * 100)); // 200 à 300 ms
        restaurant.servirAuBuffet(this); // Appelle la méthode du restaurant pour gérer l'accès au buffet
    }

    private void attendreCuisson() throws InterruptedException {
        System.out.println("Client " + nom + " attend la cuisson de son plat...");
        restaurant.attendreCuisson(this);
    }

    private void manger() throws InterruptedException {
        System.out.println("Client " + nom + " mange son repas...");
        Thread.sleep(1000 + (int) (Math.random() * 1000)); // 1 à 2 secondes
    }
}

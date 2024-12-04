package kone.nassara.m1.ccsr.wok;

public class Cuisinier extends Thread {
    private final StandCuisson standCuisson;
    private final Restaurant restaurant;

    public Cuisinier(StandCuisson standCuisson, Restaurant restaurant) {
        this.standCuisson = standCuisson;
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) { // Vérifier l'état d'interruption
                Client client = standCuisson.prochainClient();
                Logger.log("Cuisinier commence à cuire pour le client " + client.getClientId());

                // Temps de cuisson simulé
                Thread.sleep(500 + (int) (Math.random() * 500));

                // Si l'interruption se produit pendant le sommeil, sortir de la boucle
                if (Thread.currentThread().isInterrupted()) {
                    Logger.log("Cuisinier interrompu pendant la cuisson.");
                    break;  // Sortir de la boucle si l'interruption est détectée
                }

                synchronized (client) {
                    client.notify(); // Notifie que la cuisson est terminée
                }
                Logger.log("Plat du client " + client.getClientId() + " est prêt !");
                restaurant.incrementerPlatsCuits(); // Incrémenter le nombre de plats cuits
            }
        } catch (InterruptedException e) {
            Logger.log("Cuisinier interrompu.");
        }
    }
}

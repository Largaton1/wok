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
            while (true) {
                Client client = standCuisson.prochainClient();
                Logger.log("Cuisinier commence à cuire pour le client " + client.getClientId());
                Thread.sleep(500 + (int) (Math.random() * 500)); // Temps de cuisson aléatoire
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

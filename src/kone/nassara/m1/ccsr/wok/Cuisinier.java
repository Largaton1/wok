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
                Logger.log("Cuisinier commence � cuire pour le client " + client.getClientId());
                Thread.sleep(500 + (int) (Math.random() * 500)); // Temps de cuisson al�atoire
                synchronized (client) {
                    client.notify(); // Notifie que la cuisson est termin�e
                }
                Logger.log("Plat du client " + client.getClientId() + " est pr�t !");
                restaurant.incrementerPlatsCuits(); // Incr�menter le nombre de plats cuits
            }
        } catch (InterruptedException e) {
            Logger.log("Cuisinier interrompu.");
        }
    }
}

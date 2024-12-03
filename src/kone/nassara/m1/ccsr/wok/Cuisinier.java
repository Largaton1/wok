package kone.nassara.m1.ccsr.wok;

public class Cuisinier extends Thread {
    private final StandCuisson standCuisson;

    public Cuisinier(StandCuisson standCuisson) {
        this.standCuisson = standCuisson;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Client client = standCuisson.prochainClient();
                System.out.println("Cuisinier commence à cuire pour le client " + client.getClientId());
                Thread.sleep(500 + (int) (Math.random() * 500)); // Temps de cuisson aléatoire
                synchronized (client) {
                    client.notify();
                }
                System.out.println("Plat du client " + client.getClientId() + " est prêt !");
            }
        } catch (InterruptedException e) {
            System.out.println("Cuisinier interrompu.");
        }
    }
}

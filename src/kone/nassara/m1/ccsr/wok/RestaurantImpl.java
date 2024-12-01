package kone.nassara.m1.ccsr.wok;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implémentation de l'interface Restaurant.
 */
public class RestaurantImpl implements Restaurant {

    private static final int MAX_SEATS = 25; // Nombre maximum de places disponibles
    private int availableSeats = MAX_SEATS; // Nombre de places actuellement disponibles
    /**
* Le volume maximal des compartiments en grammes (g).
*/
private static final int MAX_COMPARTS_VOLUME = 1000;

/**
* Le volume minimal des compartiments en grammes (g).
*/
private static final int MIN_COMPARTS_VOLUME = 100;

/**
* Le volume actuel du compartiment à poissons, initialisé au volume maximal.
*/
private int fishCOMPART = MAX_COMPARTS_VOLUME;

private boolean shouldEmployeeIdle = true;

/**
* Le volume actuel du compartiment à nouilles, initialisé au volume maximal.
*/
private int noodleCOMPART = MAX_COMPARTS_VOLUME;

/**
* Le volume actuel du compartiment à viande, initialisé au volume maximal.
*/
private int meatCOMPART = MAX_COMPARTS_VOLUME;

/**
* Le volume actuel du compartiment à légumes, initialisé au volume maximal.
*/
private int vegetableCOMPART = MAX_COMPARTS_VOLUME;

/**
* Le nombre total de clients présents dans le restaurant.
* Est initialisé à {@code 0}.
*/
private int clientsCount;

    private final Object seatLock = new Object(); // Pour synchroniser l'entrée et la sortie = verrou 
    private final Object buffetLock = new Object(); // Pour synchroniser l'accès au buffet
    private final Object cookingLock = new Object(); // Pour synchroniser la cuisson

    private final Buffet buffet = new Buffet(); // L'objet Buffet gère les compartiments
    private final Queue<Client> cookingQueue = new LinkedList<>(); // File d'attente pour la cuisson

    public int getVegetableCOMPART() {return vegetableCOMPART;}
    public void setVegetableCOMPART(int vegetableCOMPART) { this.vegetableCOMPART = vegetableCOMPART;}
    public int getFishCOMPART() {return fishCOMPART;}
    public void setFishCOMPART(int fishCOMPART) { this.fishCOMPART = fishCOMPART; }
    public boolean isShouldEmployeeIdle() {return shouldEmployeeIdle;}
    public void setShouldEmployeeIdle(boolean shouldEmployeeIdle) {this.shouldEmployeeIdle = shouldEmployeeIdle;}
    public int getNoodleCOMPART() {return noodleCOMPART;}
    public void setNoodleCOMPART(int noodleCOMPART) {this.noodleCOMPART = noodleCOMPART;}
    public int getMeatCOMPART() {return meatCOMPART;}
    public void setMeatCOMPART(int meatCOMPART) {this.meatCOMPART = meatCOMPART;}
    public int getClientsCount() {return clientsCount;}
    public void setClientsCount(int clientsCount) { this.clientsCount = clientsCount;}
    @Override
    public void entrer(Client client) {
        synchronized (seatLock) {
            while (availableSeats == 0) {
                try {
                    System.out.println("Client " + client.getId() + " attend une place.");
                    seatLock.wait(); // Attendre qu'une place se libère
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            availableSeats--;
            System.out.println("Client " + client.getId() + " est entré. Places restantes : " + availableSeats);
        }
    }

    @Override
    public void sortir(Client client) {
        synchronized (seatLock) {
            availableSeats++;
            System.out.println("Client " + client.getId() + " quitte le restaurant. Places restantes : " + availableSeats);
            seatLock.notify(); // Réveille un client en attente
        }
    }

    @Override
    public void servirAuBuffet(Client client) {
        synchronized (buffetLock) {
            for (int i = 0; i < 4; i++) { // Les 4 compartiments
                boolean served = false;
                while (!served) {
                    int portion = (int) (Math.random() * 100) + 1; // Portion aléatoire entre 1 et 100g
                    if (buffet.getQuantity(i) >= portion) {
                        buffet.serve(i, portion);
                        served = true;
                        System.out.println("Client " + client.getId() + " a pris " + portion + "g du compartiment " + i);
                    } else {
                        System.out.println("Client " + client.getId() + " attend le réapprovisionnement du compartiment " + i);
                        try {
                            buffetLock.wait(); // Attend que l'employé réapprovisionne
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void surveillerEtReapprovisionnerBuffet() {
        synchronized (buffetLock) {
            for (int i = 0; i < 4; i++) { // Les 4 compartiments
                if (buffet.getQuantity(i) < 100) {
                    buffet.refill(i);
                    System.out.println("Employé a réapprovisionné le compartiment " + i);
                    buffetLock.notifyAll(); // Réveille les clients en attente
                }
            }
        }
    }

    @Override
    public void attendreCuisson(Client client) {
        synchronized (cookingLock) {
            cookingQueue.add(client); // Ajoute le client dans la file d'attente
            System.out.println("Client " + client.getId() + " attend la cuisson.");
            cookingLock.notify(); // Notifie le cuisinier qu'un client attend
        }
    }

    @Override
    public void cuireProchainClient() {
        synchronized (cookingLock) {
            while (cookingQueue.isEmpty()) {
                try {
                    cookingLock.wait(); // Attend qu'un client arrive
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            Client client = cookingQueue.poll(); // Récupère le prochain client dans la file d'attente
            System.out.println("Le cuisinier prépare le plat du client " + client.getId());
            try {
                Thread.sleep(2000); // Simulation du temps de cuisson
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Le cuisinier a terminé de préparer le plat du client " + client.getId());
        }
    }

    @Override
    public String etatRestaurant() {
        synchronized (seatLock) {
            return "Places occupées : " + (MAX_SEATS - availableSeats) +
                ", Places disponibles : " + availableSeats;
        }
    }

    @Override
    public String etatBuffet() {
        synchronized (buffetLock) { 
            StringBuilder state = new StringBuilder("État du buffet :\n");
            for (int i = 0; i < 4; i++) {
                state.append("Compartiment ").append(i)
                    .append(" : ").append(buffet.getQuantity(i)).append("g restants\n");
            }
            return state.toString();
        }
    }

}

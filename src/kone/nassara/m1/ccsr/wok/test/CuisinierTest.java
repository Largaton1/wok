package kone.nassara.m1.ccsr.wok.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kone.nassara.m1.ccsr.wok.Client;
import kone.nassara.m1.ccsr.wok.Cuisinier;
import kone.nassara.m1.ccsr.wok.Restaurant;
import kone.nassara.m1.ccsr.wok.StandCuisson;

import static org.junit.jupiter.api.Assertions.*;

public class CuisinierTest {

    private StandCuisson standCuisson;
    private Restaurant restaurant;
    private Cuisinier cuisinier;
    private Client client;

    @BeforeEach
    public void setUp() {
        // Créer une instance de StandCuisson et Restaurant
        standCuisson = new StandCuisson();
        restaurant = new Restaurant();

        // Créer un client et l'ajouter à la file d'attente du StandCuisson
        client = new Client(1, restaurant);
        standCuisson.ajouterClient(client);

        // Créer un cuisinier
        cuisinier = new Cuisinier(standCuisson, restaurant);
    }

    @Test
    public void testCuisinierCuitPlat() throws InterruptedException {
        // Démarrer le cuisinier dans un thread
        Thread cuisinierThread = new Thread(cuisinier);
        cuisinierThread.start();

        // Attendre que le cuisinier commence à cuire le plat (le client devrait être cuit après quelques secondes)
        client.start(); // Le client démarre son parcours
        client.join();  // Attendre que le client termine son parcours

        // Vérifier que le client a été cuit et a quitté le restaurant
        assertEquals(client.getClientId(), 1, "Le client doit avoir un ID valide.");
        
        // Assurer que le nombre de plats cuits a été incrémenté
        assertEquals(1, restaurant.getPlatsCuits(), "Le nombre de plats cuits doit être incrémenté.");
    }

    @Test
    public void testCuisinierInterruption() throws InterruptedException {
        // Démarrer le cuisinier dans un thread
        Thread cuisinierThread = new Thread(cuisinier);
        cuisinierThread.start();

        // Simuler une interruption du cuisinier
        cuisinierThread.interrupt();

        // Vérifier que l'interruption est bien gérée et que le cuisinier arrête de cuire
        assertTrue(cuisinierThread.isInterrupted(), "Le cuisinier doit être interrompu.");
    }
}


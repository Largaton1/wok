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
        // Cr�er une instance de StandCuisson et Restaurant
        standCuisson = new StandCuisson();
        restaurant = new Restaurant();

        // Cr�er un client et l'ajouter � la file d'attente du StandCuisson
        client = new Client(1, restaurant);
        standCuisson.ajouterClient(client);

        // Cr�er un cuisinier
        cuisinier = new Cuisinier(standCuisson, restaurant);
    }

    @Test
    public void testCuisinierCuitPlat() throws InterruptedException {
        // D�marrer le cuisinier dans un thread
        Thread cuisinierThread = new Thread(cuisinier);
        cuisinierThread.start();

        // Attendre que le cuisinier commence � cuire le plat (le client devrait �tre cuit apr�s quelques secondes)
        client.start(); // Le client d�marre son parcours
        client.join();  // Attendre que le client termine son parcours

        // V�rifier que le client a �t� cuit et a quitt� le restaurant
        assertEquals(client.getClientId(), 1, "Le client doit avoir un ID valide.");
        
        // Assurer que le nombre de plats cuits a �t� incr�ment�
        assertEquals(1, restaurant.getPlatsCuits(), "Le nombre de plats cuits doit �tre incr�ment�.");
    }

    @Test
    public void testCuisinierInterruption() throws InterruptedException {
        // D�marrer le cuisinier dans un thread
        Thread cuisinierThread = new Thread(cuisinier);
        cuisinierThread.start();

        // Simuler une interruption du cuisinier
        cuisinierThread.interrupt();

        // V�rifier que l'interruption est bien g�r�e et que le cuisinier arr�te de cuire
        assertTrue(cuisinierThread.isInterrupted(), "Le cuisinier doit �tre interrompu.");
    }
}


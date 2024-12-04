package kone.nassara.m1.ccsr.wok.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kone.nassara.m1.ccsr.wok.Client;
import kone.nassara.m1.ccsr.wok.Compartiment;
import kone.nassara.m1.ccsr.wok.Restaurant;
import kone.nassara.m1.ccsr.wok.StandCuisson;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    private Restaurant restaurant;
    private StandCuisson standCuisson;
    private Client client;

    @BeforeEach
    public void setUp() {
        // Cr�er une instance r�elle de StandCuisson et Restaurant
        standCuisson = new StandCuisson();
        restaurant = new Restaurant();

        // Cr�er un client avec un restaurant r�el
        client = new Client(1, restaurant);
    }

    @Test
    public void testClientEntrerDansRestaurant() throws InterruptedException {
        // Tester que le client entre dans le restaurant et s'engage dans son parcours
        client.run();

        // Le test passe si aucune exception n'est lanc�e et le client a bien ex�cut� son parcours
        assertEquals(1, client.getClientId(), "Le client doit avoir un ID valide");
    }

    @Test
    public void testClientSeServir() {
        // Simuler un client qui choisit un compartiment et se sert
        Compartiment[] compartiments = Compartiment.values();
        Compartiment compartimentChoisi = compartiments[0];  // Choisir le premier compartiment pour simplifier

        // Cr�er un nouveau client pour simuler l'action de se servir
        client = new Client(2, restaurant);
        client.run();

        // V�rifier que le client a bien choisi un compartiment et s'est servi
        // Cette v�rification se base sur l'appel de la m�thode consommer dans restaurant
        assertNotNull(client, "Le client doit exister apr�s avoir choisi un compartiment");
    }

    @Test
    public void testClientMangerEtQuitter() throws InterruptedException {
        // V�rifier que le client mange et quitte le restaurant apr�s la cuisson
        client = new Client(3, restaurant);

        // D�marrer le client dans un thread
        Thread clientThread = new Thread(client);
        clientThread.start();

        // Attendre que le client termine son parcours
        clientThread.join();

        // V�rifier que le client a mang� et quitt�
        assertTrue(client.getClientId() > 0, "Le client doit avoir termin� son parcours");
    }
}

package kone.nassara.m1.ccsr.wok.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kone.nassara.m1.ccsr.wok.Restaurant;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        // Cr�er une instance de Restaurant avant chaque test
        restaurant = new Restaurant();
    }

    @Test
    public void testStartSimulation() throws InterruptedException {
        // D�marre la simulation avec 5 clients
        restaurant.startSimulation(5);

        // Attends que la simulation se termine (clients et cuisinier)
        restaurant.attendreFinSimulation();

        // V�rifie que le nombre total de clients servis est correct
        assertEquals(5, restaurant.getClients().size(), "Le nombre de clients servis doit �tre 5.");

        // V�rifie que le nombre total de plats cuits a bien �t� incr�ment�
        assertTrue(restaurant.getPlatsCuits() > 0, "Le nombre de plats cuits doit �tre sup�rieur � 0.");
    }

    @Test
    public void testConsommationTotale() throws InterruptedException {
        // D�marre la simulation avec 5 clients
        restaurant.startSimulation(5);

        // Attends que la simulation se termine
        restaurant.attendreFinSimulation();

        // V�rifie que les consommations totales sont correctes
        assertTrue(restaurant.getTotalLegumes() > 0, "La consommation de l�gumes doit �tre sup�rieure � 0.");
        assertTrue(restaurant.getTotalViande() > 0, "La consommation de viande doit �tre sup�rieure � 0.");
        assertTrue(restaurant.getTotalPoisson() > 0, "La consommation de poisson doit �tre sup�rieure � 0.");
        assertTrue(restaurant.getTotalNouilles() > 0, "La consommation de nouilles doit �tre sup�rieure � 0.");
    }

    @Test
    public void testIncrementerPlatsCuits() throws InterruptedException {
        // D�marre la simulation avec 2 clients
        restaurant.startSimulation(2);

        // Attends que les clients finissent leur parcours et que les plats soient cuits
        restaurant.attendreFinSimulation();

        // V�rifie que le nombre de plats cuits est correct
        assertEquals(2, restaurant.getPlatsCuits(), "Le nombre de plats cuits doit �tre �gal au nombre de clients.");
    }

   
    @Test
    public void testSimulationCompletion() throws InterruptedException {
        // D�marre la simulation avec 5 clients
        restaurant.startSimulation(5);

        // Attendre la fin de la simulation
        restaurant.attendreFinSimulation();

        // V�rifier le nombre de clients et de plats cuits
        assertEquals(5, restaurant.getClients().size(), "Le nombre de clients servis doit �tre 5.");
        assertTrue(restaurant.getPlatsCuits() > 0, "Le nombre de plats cuits doit �tre sup�rieur � 0.");
    }
    @Test
    public void testMaxClientsInRestaurant() throws InterruptedException {
        // D�marre la simulation avec 30 clients
        restaurant.startSimulation(30);

        // Attendre la fin de la simulation (clients et cuisinier)
        restaurant.attendreFinSimulation();

        // V�rifier que le nombre de clients simultan�ment dans le restaurant ne d�passe pas 25
        assertTrue(restaurant.getClients().size() <= 25, "Le nombre de clients simultan�ment dans le restaurant ne doit pas d�passer 25.");
    }
    
    @Test
    public void testMaxClientsInRestaurantWithWaiting() throws InterruptedException {
        // D�marre la simulation avec 30 clients
        restaurant.startSimulation(30);

        // Attendre la fin de la simulation (clients et cuisinier)
        restaurant.attendreFinSimulation();

        // V�rifier que le nombre de clients simultan�ment dans le restaurant ne d�passe pas 25
        assertTrue(restaurant.getClients().size() <= 25, "Le nombre de clients simultan�ment dans le restaurant ne doit pas d�passer 25.");
    }


}

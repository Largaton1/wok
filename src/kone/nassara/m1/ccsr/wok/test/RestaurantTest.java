package kone.nassara.m1.ccsr.wok.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kone.nassara.m1.ccsr.wok.Restaurant;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        // Créer une instance de Restaurant avant chaque test
        restaurant = new Restaurant();
    }

    @Test
    public void testStartSimulation() throws InterruptedException {
        // Démarre la simulation avec 5 clients
        restaurant.startSimulation(5);

        // Attends que la simulation se termine (clients et cuisinier)
        restaurant.attendreFinSimulation();

        // Vérifie que le nombre total de clients servis est correct
        assertEquals(5, restaurant.getClients().size(), "Le nombre de clients servis doit être 5.");

        // Vérifie que le nombre total de plats cuits a bien été incrémenté
        assertTrue(restaurant.getPlatsCuits() > 0, "Le nombre de plats cuits doit être supérieur à 0.");
    }

    @Test
    public void testConsommationTotale() throws InterruptedException {
        // Démarre la simulation avec 5 clients
        restaurant.startSimulation(5);

        // Attends que la simulation se termine
        restaurant.attendreFinSimulation();

        // Vérifie que les consommations totales sont correctes
        assertTrue(restaurant.getTotalLegumes() > 0, "La consommation de légumes doit être supérieure à 0.");
        assertTrue(restaurant.getTotalViande() > 0, "La consommation de viande doit être supérieure à 0.");
        assertTrue(restaurant.getTotalPoisson() > 0, "La consommation de poisson doit être supérieure à 0.");
        assertTrue(restaurant.getTotalNouilles() > 0, "La consommation de nouilles doit être supérieure à 0.");
    }

    @Test
    public void testIncrementerPlatsCuits() throws InterruptedException {
        // Démarre la simulation avec 2 clients
        restaurant.startSimulation(2);

        // Attends que les clients finissent leur parcours et que les plats soient cuits
        restaurant.attendreFinSimulation();

        // Vérifie que le nombre de plats cuits est correct
        assertEquals(2, restaurant.getPlatsCuits(), "Le nombre de plats cuits doit être égal au nombre de clients.");
    }

   
    @Test
    public void testSimulationCompletion() throws InterruptedException {
        // Démarre la simulation avec 5 clients
        restaurant.startSimulation(5);

        // Attendre la fin de la simulation
        restaurant.attendreFinSimulation();

        // Vérifier le nombre de clients et de plats cuits
        assertEquals(5, restaurant.getClients().size(), "Le nombre de clients servis doit être 5.");
        assertTrue(restaurant.getPlatsCuits() > 0, "Le nombre de plats cuits doit être supérieur à 0.");
    }
    @Test
    public void testMaxClientsInRestaurant() throws InterruptedException {
        // Démarre la simulation avec 30 clients
        restaurant.startSimulation(30);

        // Attendre la fin de la simulation (clients et cuisinier)
        restaurant.attendreFinSimulation();

        // Vérifier que le nombre de clients simultanément dans le restaurant ne dépasse pas 25
        assertTrue(restaurant.getClients().size() <= 25, "Le nombre de clients simultanément dans le restaurant ne doit pas dépasser 25.");
    }
    
    @Test
    public void testMaxClientsInRestaurantWithWaiting() throws InterruptedException {
        // Démarre la simulation avec 30 clients
        restaurant.startSimulation(30);

        // Attendre la fin de la simulation (clients et cuisinier)
        restaurant.attendreFinSimulation();

        // Vérifier que le nombre de clients simultanément dans le restaurant ne dépasse pas 25
        assertTrue(restaurant.getClients().size() <= 25, "Le nombre de clients simultanément dans le restaurant ne doit pas dépasser 25.");
    }


}

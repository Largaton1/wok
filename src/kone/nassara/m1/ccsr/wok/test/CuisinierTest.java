package kone.nassara.m1.ccsr.wok.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import kone.nassara.m1.ccsr.wok.StandCuisson;
import kone.nassara.m1.ccsr.wok.Cuisinier;
import kone.nassara.m1.ccsr.wok.Restaurant;
import kone.nassara.m1.ccsr.wok.Client;
class CuisinierTest {

    @Test
    void testCuisson() throws InterruptedException {
        StandCuisson standCuisson = new StandCuisson();
        Restaurant restaurant = new Restaurant();
        Cuisinier cuisinier = new Cuisinier(standCuisson, restaurant);

        Client client1 = new Client(1, restaurant);
        standCuisson.ajouterClient(client1);
        
        // Start cuisinier in a separate thread
        cuisinier.start();

        // Wait for the cuisinier to finish cooking
        Thread.sleep(2000); 

        assertEquals(1, restaurant.getTotalPlatsCuits());  // Only 1 plate should be cooked
    }
}

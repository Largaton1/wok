package kone.nassara.m1.ccsr.wok.test;

import kone.nassara.m1.ccsr.wok.StandCuisson;
import kone.nassara.m1.ccsr.wok.Client;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StandCuissonTest {

    @Test
    void testAjouterClient() throws InterruptedException {
        StandCuisson standCuisson = new StandCuisson();
        Client client1 = new Client(1, null);  // Client 1
        Client client2 = new Client(2, null);  // Client 2

        standCuisson.ajouterClient(client1);
        standCuisson.ajouterClient(client2);

        assertEquals(client1, standCuisson.prochainClient());  // Should be client 1 first
        assertEquals(client2, standCuisson.prochainClient());  // Then client 2
    }
}

package kone.nassara.m1.ccsr.wok.test;


import org.junit.jupiter.api.Test;

import kone.nassara.m1.ccsr.wok.Compartiment;
import kone.nassara.m1.ccsr.wok.Buffet;
import static org.junit.jupiter.api.Assertions.*;

class BuffetTest {

    @Test
    void testServir() {
        Buffet buffet = new Buffet();

        // Initial state: Buffet contains 1000g of each compartment
        assertTrue(buffet.servir(Compartiment.POISSON, 200));  // Should be successful (200g)
        assertEquals(800, buffet.getQuantite(Compartiment.POISSON));  // 1000 - 200 = 800
    }

    @Test
    void testServirInsuffisant() {
        Buffet buffet = new Buffet();
        assertFalse(buffet.servir(Compartiment.POISSON, 1200));  // Not enough food
    }

    @Test
    void testReapprovisionner() {
        Buffet buffet = new Buffet();
        buffet.servir(Compartiment.POISSON, 200);  // Use some of the poisson
        buffet.recharger(Compartiment.POISSON);  // Refill poisson to 1000g

        assertEquals(1000, buffet.getQuantite(Compartiment.POISSON));  // Poisson should be 1000g again
    }
}

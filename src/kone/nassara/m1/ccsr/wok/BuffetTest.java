package kone.nassara.m1.ccsr.wok;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;

public class BuffetTest {

    private Buffet buffet;

    @BeforeEach
    public void setUp() {
        buffet = new Buffet();
    }

    @Test
    public void testServeSuccess() throws InterruptedException {
        boolean result = buffet.serve(Buffet.Compartiment.POISSON, 200);
        assertTrue(result, "Le service doit réussir si la quantité est suffisante.");
        assertEquals(800, buffet.getQuantity(Buffet.Compartiment.POISSON), "La quantité restante doit être correcte.");
    }

    @Test
    public void testServeInsufficientQuantity() throws InterruptedException {
        // Réduire la quantité à un niveau inférieur à la demande
        buffet.serve(Buffet.Compartiment.POISSON, 950);
        
        boolean result = buffet.serve(Buffet.Compartiment.POISSON, 200);
        assertFalse(result, "Le service doit échouer si la quantité est insuffisante.");
        assertEquals(50, buffet.getQuantity(Buffet.Compartiment.POISSON), "La quantité restante doit être correcte après un service partiel.");
    }

    @Test
    public void testRefill() throws InterruptedException {
        buffet.serve(Buffet.Compartiment.POISSON, 500);
        buffet.refill(Buffet.Compartiment.POISSON);
        assertEquals(1000, buffet.getQuantity(Buffet.Compartiment.POISSON), "Le réapprovisionnement doit remettre la quantité à 1000g.");
    }

    @Test
    public void testServeAndRefillConcurrency() throws InterruptedException {
        // Utilisation de CountDownLatch pour synchroniser les threads
        CountDownLatch latch = new CountDownLatch(1);
        
        // Client thread qui tente de se servir
        Thread clientThread = new Thread(() -> {
            buffet.serve(Buffet.Compartiment.POISSON, 950);
			latch.countDown(); // Signaler que le client a pris sa portion
        });

        // Employé thread qui remplit le compartiment
        Thread employeThread = new Thread(() -> {
            try {
                Thread.sleep(500); // Attendre avant de remplir
                buffet.refill(Buffet.Compartiment.POISSON);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        clientThread.start();
        employeThread.start();

        latch.await(); // Attendre que le client termine
        assertEquals(1000, buffet.getQuantity(Buffet.Compartiment.POISSON), "Le réapprovisionnement doit avoir eu lieu pendant que le client attendait.");
    }
}

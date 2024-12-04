package kone.nassara.m1.ccsr.wok.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kone.nassara.m1.ccsr.wok.Buffet;
import kone.nassara.m1.ccsr.wok.Compartiment;
import kone.nassara.m1.ccsr.wok.EmployeBuffet;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeBuffetTest {

    private Buffet buffet;
    private EmployeBuffet employe;
    private Thread employeThread;

    @BeforeEach
    public void setUp() {
        // Initialiser le buffet avec des quantités complètes
        buffet = new Buffet();
        
        // Créer l'employé avec un délai de 100ms entre les vérifications
        employe = new EmployeBuffet(buffet, 100);

        // Démarrer l'employé dans un thread séparé
        employeThread = new Thread(employe);
        employeThread.start();
    }

    @Test
    public void testReapprovisionnement() throws InterruptedException {
        // Modifier la quantité d'un compartiment pour qu'il soit sous le seuil de réapprovisionnement
        buffet.servir(Compartiment.LEGUMES, 950); // Réduire la quantité des légumes à 50g
        
        // Attendre un peu pour que l'employé ait le temps de réapprovisionner
        Thread.sleep(200); 

        // Vérifier que le compartiment LEGUMES a bien été réapprovisionné
        assertEquals(Compartiment.CAPACITY, buffet.getQuantite(Compartiment.LEGUMES), "Le compartiment LEGUMES doit être réapprovisionné.");
    }

    @Test
    public void testDelaiPauseEntreVerifications() throws InterruptedException {
        // Nous allons réduire le délai de vérification à 10ms pour tester la réactivité
        int delaiInitial = 100;
        employe = new EmployeBuffet(buffet, 10); // Modifier le délai
        employeThread = new Thread(employe);
        employeThread.start();

        // Modifier un compartiment pour qu'il nécessite un réapprovisionnement
        buffet.servir(Compartiment.POISSON, 950);  // Réduire à 50g

        // Attendre assez longtemps pour que l'employé ait eu le temps de réagir
        Thread.sleep(50);  // Attendre plus longtemps que le délai de 10ms

        // Vérifier que l'employé a réapprovisionné le compartiment
        assertEquals(Compartiment.CAPACITY, buffet.getQuantite(Compartiment.POISSON), "Le compartiment POISSON doit être réapprovisionné.");
    }

    @Test
    public void testInterruption() throws InterruptedException {
        // Démarrer l'employé dans un thread
        Thread employeThread = new Thread(employe);
        employeThread.start();

        // Attendre un peu avant d'interrompre l'employé
        Thread.sleep(100); // Attendre un temps avant d'interrompre l'employé

        // Interrompre l'employé
        employeThread.interrupt();

        // Attendre un peu pour vérifier que l'employé a bien été interrompu
        Thread.sleep(50);  // L'employé sera interrompu avant de continuer

        // Vérifier que l'employé a bien été interrompu
        assertTrue(employeThread.isInterrupted(), "L'employé doit être interrompu.");
    }

}

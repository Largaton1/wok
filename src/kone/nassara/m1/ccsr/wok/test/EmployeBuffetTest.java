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
        // Initialiser le buffet avec des quantit�s compl�tes
        buffet = new Buffet();
        
        // Cr�er l'employ� avec un d�lai de 100ms entre les v�rifications
        employe = new EmployeBuffet(buffet, 100);

        // D�marrer l'employ� dans un thread s�par�
        employeThread = new Thread(employe);
        employeThread.start();
    }

    @Test
    public void testReapprovisionnement() throws InterruptedException {
        // Modifier la quantit� d'un compartiment pour qu'il soit sous le seuil de r�approvisionnement
        buffet.servir(Compartiment.LEGUMES, 950); // R�duire la quantit� des l�gumes � 50g
        
        // Attendre un peu pour que l'employ� ait le temps de r�approvisionner
        Thread.sleep(200); 

        // V�rifier que le compartiment LEGUMES a bien �t� r�approvisionn�
        assertEquals(Compartiment.CAPACITY, buffet.getQuantite(Compartiment.LEGUMES), "Le compartiment LEGUMES doit �tre r�approvisionn�.");
    }

    @Test
    public void testDelaiPauseEntreVerifications() throws InterruptedException {
        // Nous allons r�duire le d�lai de v�rification � 10ms pour tester la r�activit�
        int delaiInitial = 100;
        employe = new EmployeBuffet(buffet, 10); // Modifier le d�lai
        employeThread = new Thread(employe);
        employeThread.start();

        // Modifier un compartiment pour qu'il n�cessite un r�approvisionnement
        buffet.servir(Compartiment.POISSON, 950);  // R�duire � 50g

        // Attendre assez longtemps pour que l'employ� ait eu le temps de r�agir
        Thread.sleep(50);  // Attendre plus longtemps que le d�lai de 10ms

        // V�rifier que l'employ� a r�approvisionn� le compartiment
        assertEquals(Compartiment.CAPACITY, buffet.getQuantite(Compartiment.POISSON), "Le compartiment POISSON doit �tre r�approvisionn�.");
    }

    @Test
    public void testInterruption() throws InterruptedException {
        // D�marrer l'employ� dans un thread
        Thread employeThread = new Thread(employe);
        employeThread.start();

        // Attendre un peu avant d'interrompre l'employ�
        Thread.sleep(100); // Attendre un temps avant d'interrompre l'employ�

        // Interrompre l'employ�
        employeThread.interrupt();

        // Attendre un peu pour v�rifier que l'employ� a bien �t� interrompu
        Thread.sleep(50);  // L'employ� sera interrompu avant de continuer

        // V�rifier que l'employ� a bien �t� interrompu
        assertTrue(employeThread.isInterrupted(), "L'employ� doit �tre interrompu.");
    }

}

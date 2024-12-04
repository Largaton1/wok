package kone.nassara.m1.ccsr.wok.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kone.nassara.m1.ccsr.wok.Buffet;
import kone.nassara.m1.ccsr.wok.Compartiment;

import static org.junit.jupiter.api.Assertions.*;

public class BuffetTest {

    private Buffet buffet;

    @BeforeEach
    public void setUp() {
        buffet = new Buffet();
    }

    @Test
    public void testServirPortionSuffisante() {
        // Test de servir une portion suffisante
        Compartiment compartiment = Compartiment.LEGUMES;
        boolean result = buffet.servir(compartiment, 500);

        // V�rifier que la portion a �t� servie
        assertTrue(result);
        assertEquals(500, buffet.getQuantite(compartiment));
    }

    @Test
    public void testServirPortionInsuffisante() {
        // Test de servir une portion insuffisante
        Compartiment compartiment = Compartiment.POISSON;
        buffet.servir(compartiment, 500); // Premi�re portion servie
        boolean result = buffet.servir(compartiment, 600); // Tentative de servir plus que ce qui reste

        // V�rifier que la portion n'a pas �t� servie (insuffisant)
        assertFalse(result);
        assertEquals(500, buffet.getQuantite(compartiment)); // Il reste encore 500g
    }

    @Test
    public void testRechargerCompartiment() {
        // Test de r�approvisionnement d'un compartiment
        Compartiment compartiment = Compartiment.NOUILLES;
        buffet.servir(compartiment, 400); // R�duire la quantit� de nouilles
        buffet.recharger(compartiment); // R�approvisionner

        // V�rifier que le compartiment est r�approvisionn�
        assertEquals(Compartiment.CAPACITY, buffet.getQuantite(compartiment));
    }

    @Test
    public void testNombreReapprovisionnements() {
        // Test du nombre de r�approvisionnements
        Compartiment compartiment = Compartiment.VIANDE;

        // Initialement, il n'y a pas de r�approvisionnement
        assertEquals(0, buffet.getNombreReapprovisionnements());

        // Servir un peu de viande et r�approvisionner
        buffet.servir(compartiment, 300);
        buffet.recharger(compartiment);

        // V�rifier que le nombre de r�approvisionnements a augment�
        assertEquals(1, buffet.getNombreReapprovisionnements());
    }

    @Test
    public void testGetQuantite() {
        // Test de r�cup�ration de la quantit� restante
        Compartiment compartiment = Compartiment.POISSON;

        // Au d�part, la quantit� doit �tre �gale � la capacit�
        assertEquals(Compartiment.CAPACITY, buffet.getQuantite(compartiment));

        // Servir une portion et v�rifier la quantit� restante
        buffet.servir(compartiment, 200);
        assertEquals(800, buffet.getQuantite(compartiment));
    }
}

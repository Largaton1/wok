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

        // Vérifier que la portion a été servie
        assertTrue(result);
        assertEquals(500, buffet.getQuantite(compartiment));
    }

    @Test
    public void testServirPortionInsuffisante() {
        // Test de servir une portion insuffisante
        Compartiment compartiment = Compartiment.POISSON;
        buffet.servir(compartiment, 500); // Première portion servie
        boolean result = buffet.servir(compartiment, 600); // Tentative de servir plus que ce qui reste

        // Vérifier que la portion n'a pas été servie (insuffisant)
        assertFalse(result);
        assertEquals(500, buffet.getQuantite(compartiment)); // Il reste encore 500g
    }

    @Test
    public void testRechargerCompartiment() {
        // Test de réapprovisionnement d'un compartiment
        Compartiment compartiment = Compartiment.NOUILLES;
        buffet.servir(compartiment, 400); // Réduire la quantité de nouilles
        buffet.recharger(compartiment); // Réapprovisionner

        // Vérifier que le compartiment est réapprovisionné
        assertEquals(Compartiment.CAPACITY, buffet.getQuantite(compartiment));
    }

    @Test
    public void testNombreReapprovisionnements() {
        // Test du nombre de réapprovisionnements
        Compartiment compartiment = Compartiment.VIANDE;

        // Initialement, il n'y a pas de réapprovisionnement
        assertEquals(0, buffet.getNombreReapprovisionnements());

        // Servir un peu de viande et réapprovisionner
        buffet.servir(compartiment, 300);
        buffet.recharger(compartiment);

        // Vérifier que le nombre de réapprovisionnements a augmenté
        assertEquals(1, buffet.getNombreReapprovisionnements());
    }

    @Test
    public void testGetQuantite() {
        // Test de récupération de la quantité restante
        Compartiment compartiment = Compartiment.POISSON;

        // Au départ, la quantité doit être égale à la capacité
        assertEquals(Compartiment.CAPACITY, buffet.getQuantite(compartiment));

        // Servir une portion et vérifier la quantité restante
        buffet.servir(compartiment, 200);
        assertEquals(800, buffet.getQuantite(compartiment));
    }
}

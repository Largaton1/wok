package kone.nassara.m1.ccsr.wok;

public enum EtatClient {
    WAITING_TO_ENTER, // En attente pour entrer dans le restaurant
    AT_THE_BUFFET,    // En train de se servir ou d’attendre un remplissage
    WAITING_FOR_THE_COOK, // En attente du cuisinier pour cuire son plat
    EATING,           // En train de manger
    OUT               // A quitté le restaurant
}

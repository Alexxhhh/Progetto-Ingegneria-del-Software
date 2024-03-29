package Componenti.Dadi;

import java.util.Random;

//classe astratta che viene concretizzata con Dadi e Dado in base alle impostazioni dell'utente
public abstract class AbstractDadi {
    protected int val;
    protected Random r;

    public abstract int tira();
    public int getTiro(){
        return val;
    }
    public int tiraSingolo(){
        return tira();
    }
}

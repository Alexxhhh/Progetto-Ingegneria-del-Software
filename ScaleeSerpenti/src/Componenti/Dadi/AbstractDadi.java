package Componenti.Dadi;

import java.util.Random;

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

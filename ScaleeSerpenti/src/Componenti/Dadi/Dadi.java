package Componenti.Dadi;

import java.util.Random;

public class Dadi extends AbstractDadi{
    public Dadi(){
        r=new Random();
    };
    @Override
    public int tira() {
        val=r.nextInt(11)+2;
        return val;
    }

    public int tiraSingolo(){
        val=r.nextInt(6)+1;
        return val;
    }
}

package Componenti.Dadi;

import java.util.Random;

public class Dado extends AbstractDadi{

    public Dado(){
        r=new Random();
    };
    @Override
    public int tira() {
        val=r.nextInt(6)+1;
        return val;
    }

}

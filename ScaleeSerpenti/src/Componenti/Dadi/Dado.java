package Componenti.Dadi;

import java.util.Random;

//classe concreta quando si utilizza un dado
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

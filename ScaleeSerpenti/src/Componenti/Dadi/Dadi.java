package Componenti.Dadi;

import java.util.Random;

//classe concreta quando si utilizzano due dadi
public class Dadi extends AbstractDadi{
    public Dadi(){
        r=new Random();
    };
    @Override
    public int tira() {
        val=r.nextInt(11)+2;
        return val;
    }

    public int tiraSingolo(){ //se presente la regola del tiro singolo, è possibile tirare un solo dado
        val=r.nextInt(6)+1;
        return val;
    }
}

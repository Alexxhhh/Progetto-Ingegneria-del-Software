package Componenti.Carte;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

public class Mazzo {
    Queue<Carta> mazzo;
    private final int dimensione=20;

    public Mazzo(boolean ulterioriCarte){
        mazzo= new ArrayDeque<>();
        Random r=new Random();
        for (int i=0;i<dimensione;i++){
            while(true){
                Carta c=new Carta(TipologiaCarte.values()[r.nextInt(TipologiaCarte.values().length)]);
                if (!((c.getTipo()==TipologiaCarte.ANNULLA) && !(ulterioriCarte))){
                    mazzo.add(c);
                    break;
                }
            }

        }
    }

    public Carta getCarta(){
        return mazzo.poll();
    }

    public void putCarta(Carta c){
        mazzo.add(c);
    }


}

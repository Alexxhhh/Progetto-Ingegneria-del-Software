package Componenti.Carte;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

//Il mazzo viene generato se viene selezionata l'opzione pesca nel gioco
public class Mazzo {
    private Queue<Carta> mazzo;
    private final int dimensione=20;

    public Mazzo(boolean ulterioriCarte){
        mazzo= new ArrayDeque<>();
        Random r=new Random();
        for (int i=0;i<dimensione;i++){
            while(true){
                Carta c=new Carta(TipologiaCarte.values()[r.nextInt(TipologiaCarte.values().length)]);
                //controlla che sia presente la regola dell'ulteriore carta in caso in cui venga generata la carta
                //annulla, ovvero divieto di sosta. se viene generata la carta annulla ma non è presente la regola,
                //il while non si interrompe
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

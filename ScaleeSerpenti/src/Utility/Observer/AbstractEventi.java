package Utility.Observer;

import Utility.Observer.Messaggio.Messaggio;

import java.util.ArrayList;

//classe astratta observer
public abstract class AbstractEventi {
    private ArrayList<AscolatoreEventi> arrayList=new ArrayList<>();

    protected void notifica(Messaggio m){
        for(AscolatoreEventi a:arrayList){
            a.aggiornamento(m);
        }
    }
    public void addAscolatoreEventi(AscolatoreEventi a){
        arrayList.add(a);
    }
}

package Utility.Observer.Messaggio;

import Componenti.Scacchiera.Pedina;

//messaggio trasferito
public class Messaggio {
    private TipologiaMessaggio tipologiaMessaggio;
    private Pedina p;
    private int val=-1;
    private boolean b=false;
    public Messaggio(TipologiaMessaggio tipologiaMessaggio,Pedina p,boolean b){
        this.tipologiaMessaggio=tipologiaMessaggio;
        this.p=p;
        this.b=b;
    }
    public Messaggio(TipologiaMessaggio tipologiaMessaggio){//DOPPIO
        this.tipologiaMessaggio=tipologiaMessaggio;
    }
    public Messaggio(TipologiaMessaggio tipologiaMessaggio,Pedina p, int val){//DADI
        this.tipologiaMessaggio=tipologiaMessaggio;
        this.p=p;
        this.val=val;
    }

    public TipologiaMessaggio getTipologiaMessaggio() {
        return tipologiaMessaggio;
    }

    public Pedina getP() {
        return p;
    }

    public int getVal() {
        return val;
    }

    public boolean isB() {
        return b;
    }
}

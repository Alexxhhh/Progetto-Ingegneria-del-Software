package Esecuzione;

import Componenti.Carte.Carta;
import Componenti.Carte.Mazzo;
import Componenti.Dadi.AbstractDadi;
import Componenti.Scacchiera.Pedina;
import Componenti.Scacchiera.Scacchiera;
import Salvataggio.Regole;
import Utility.Observer.AbstractEventi;
import Utility.Observer.Messaggio.Messaggio;
import Utility.Observer.Messaggio.TipologiaMessaggio;

import java.util.Queue;
import java.util.concurrent.Semaphore;

public abstract class AbstractGioco extends AbstractEventi implements Runnable{
    protected Scacchiera scacchiera;
    protected Regole regole;
    protected AbstractDadi dadi;
    protected Mazzo mazzo;
    public Queue<Pedina> giocatori;
    public boolean flagGioco;
    protected boolean simulata;
    protected Semaphore semaphore=new Semaphore(1);


    public void genera(){
        for (int i=0; i<scacchiera.getScale().length;i++){
            System.out.println(scacchiera.getScale()[i]);
        }
        for (int i=0; i<scacchiera.getSerpenti().length;i++){
            System.out.println(scacchiera.getSerpenti()[i]);
        }
        for (int i=0;i<scacchiera.getCoordinate().getY();i++){
            System.out.print("[");
            for (int j=0;j<scacchiera.getCoordinate().getX();j++){
                System.out.print(scacchiera.getScacchiera()[i][j].getCasella()+",");
            }
            System.out.println("]");
        }
    }
    public void gioca(Pedina p){
        notifica(new Messaggio(TipologiaMessaggio.ITURNO,p,true));
        do{
            p.setDoppio(false);
            if (p.turno()){
                tira(p);
            }else{
                notifica(new Messaggio(TipologiaMessaggio.SOSTA,p,true));
            }
        }while(p.isDoppio()&&flagGioco);
        notifica(new Messaggio(TipologiaMessaggio.FTURNO,p,true));
    }
    public void tira(Pedina p) {
        int val;
        if ((regole.isLancioSingolo() &&
                (p.getPos().getY()*scacchiera.getCoordinate().getY()+p.getPos().getX())>
                        (scacchiera.getCoordinate().getY()*scacchiera.getCoordinate().getX())-7)){
            val=tiraSingolo();
        }
        else{
            val=dadi.tira();
        }
        p.incrPos(val,scacchiera.getCoordinate());
        if (p.getPos().getY()>=scacchiera.getCoordinate().getY()){
            p.indietreggia(scacchiera.getCoordinate());
        }
        notifica(new Messaggio(TipologiaMessaggio.DADI,p,val));
        if (val==12&&regole.isDoppioSei()){
            notifica(new Messaggio(TipologiaMessaggio.DOPPIO));
            p.setDoppio(true);
        }
        controlla(p);
    }
    public int tiraSingolo(){
        return dadi.tiraSingolo();
    }
    public void controlla(Pedina p){
        switch (scacchiera.getCasella(p)){
            case TESTA_SE -> {
                notifica(new Messaggio(TipologiaMessaggio.SERPENTE,p,true));
                serpente(p);
            }
            case INIZIO_SC -> {
                notifica(new Messaggio(TipologiaMessaggio.SCALA,p,true));
                scala(p);
            }
            case PANCHINA -> {
                sosta(p, 1);
                notifica(new Messaggio(TipologiaMessaggio.PANCHINA,p,true));
            }
            case LOCANDA -> {
                sosta(p, 3);
                notifica(new Messaggio(TipologiaMessaggio.LOCANDA,p,true));
            }
            case MOLLA -> {
                notifica(new Messaggio(TipologiaMessaggio.MOLLA,p,true));
                molla(p);
            }
            case PESCA -> {
                pesca(p);
                notifica(new Messaggio(TipologiaMessaggio.PESCA,p,false));
            }
            case DADI -> {
                notifica(new Messaggio(TipologiaMessaggio.DADI,p,true));
                tira(p);
            }
            case VUOTA -> {
                if (p.getPos().getY()==scacchiera.getCoordinate().getY()-1&&p.getPos().getX()==scacchiera.getCoordinate().getX()-1){
                    flagGioco=false;
                }
            }
        }
    }
    public void molla(Pedina p){
        p.incrPos(dadi.getTiro(),scacchiera.getCoordinate());
        if (p.getPos().getY()>=scacchiera.getCoordinate().getY()){
            p.indietreggia(scacchiera.getCoordinate());
        }
        notifica(new Messaggio(TipologiaMessaggio.MUOVI,p,true));
        controlla(p);
    }
    public void serpente(Pedina p){
        p.setPos(scacchiera.codaSe(p.getPos()));
        notifica(new Messaggio(TipologiaMessaggio.MUOVI,p,true));
        controlla(p);
    }
    public void scala(Pedina p){
        p.setPos(scacchiera.inizioSc(p.getPos()));
        notifica(new Messaggio(TipologiaMessaggio.MUOVI,p,true));
        controlla(p);
    }
    public void pesca(Pedina p){
        Carta c=mazzo.getCarta();
        switch (c.getTipo()){
            case PANCHINA -> {
                sosta(p, 1);
                notifica(new Messaggio(TipologiaMessaggio.PANCHINA,p,false));
            }
            case LOCANDA -> {
                sosta(p, 3);
                notifica(new Messaggio(TipologiaMessaggio.LOCANDA,p,false));
            }
            case MOLLA -> {
                notifica(new Messaggio(TipologiaMessaggio.MOLLA,p,false));
                molla(p);
            }
            case DADI -> {
                notifica(new Messaggio(TipologiaMessaggio.DADI,p,false));
                tira(p);
            }
            case ANNULLA -> {
                p.aggiungiAnn(c);
                notifica(new Messaggio(TipologiaMessaggio.ANNULLA,p,false));
                return;
            }
        }
        mazzo.putCarta(c);
    }
    public void sosta(Pedina p, int val){
        if (p.isAnnulla()){
            p.setSosta(val);
            return;
        }
        notifica(new Messaggio(TipologiaMessaggio.RIMANNULLA,p,false));
        mazzo.putCarta(p.rimuoviAnn());
    }
    public Scacchiera getScacchiera() {
        return scacchiera;
    }
    public abstract void tira();

}

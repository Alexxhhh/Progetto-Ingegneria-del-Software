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

//La classe astratta si concretizza con la classe gioco. Gestisce i turni, i tiri e il comportamenteo delle pedine
//in base alla casella in cui si capita. La classe viene avvita da un thread in modo da non bloccare la parte grafica.
public abstract class AbstractGioco extends AbstractEventi implements Runnable{
    protected Scacchiera scacchiera;
    protected Regole regole;
    protected AbstractDadi dadi;
    protected Mazzo mazzo;
    public Queue<Pedina> giocatori;
    public boolean flagGioco;
    protected boolean simulata;
    protected Semaphore semaphore=new Semaphore(1); //serve per evitare che lavorino più thread contemporaneamente

    public void gioca(Pedina p){ //avvia e gestisce i turni di gioco
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
    private void tira(Pedina p) { //il giocatore tira i dadi e in base alle regole scelte e alla posizione della pedina può avviarsi il tiro singolo
        int val;
        if ((regole.isLancioSingolo() &&
                (p.getPos().getY()*scacchiera.getCoordinate().getX()+p.getPos().getX())>
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
        notifica(new Messaggio(TipologiaMessaggio.DADI,p,val));//notifica all'observer l'incremento di posizione
        if (val==12&&regole.isDoppioSei()){ //se presente la regola del doppio 6 ed esce 12 il turno viene ripetuto
            notifica(new Messaggio(TipologiaMessaggio.DOPPIO));
            p.setDoppio(true);
        }
        controlla(p);
    }
    private int tiraSingolo(){
        return dadi.tiraSingolo();
    }

    //dopo ogni spostamento viene controllata la posizione della pedina sulla scacchiera e viene avviato un nuovo
    //movimento in base alla tipologia della casella l'unica casella che non fa nulla è la casella vuota, che controlla
    //se la pedina attuale è quella finale. Per ogni spostamento viene notificato all'observer la tipologia della casella
    private void controlla(Pedina p){
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
    //molle, serpenti e scala muovono la pedina
    private void molla(Pedina p){
        p.incrPos(dadi.getTiro(),scacchiera.getCoordinate());
        if (p.getPos().getY()>=scacchiera.getCoordinate().getY()){
            p.indietreggia(scacchiera.getCoordinate());
        }
        notifica(new Messaggio(TipologiaMessaggio.MUOVI,p,true));
        controlla(p);
    }
    private void serpente(Pedina p){
        p.setPos(scacchiera.codaSe(p.getPos()));
        notifica(new Messaggio(TipologiaMessaggio.MUOVI,p,true));
        controlla(p);
    }
    private void scala(Pedina p){
        p.setPos(scacchiera.inizioSc(p.getPos()));
        notifica(new Messaggio(TipologiaMessaggio.MUOVI,p,true));
        controlla(p);
    }
    //se presenta la regola pesca, il metodo viene richiamato quando si capita sulla casella pesca. In base alla carta
    //pescata viene richiamato un metodo
    private void pesca(Pedina p){
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
    //se non presente la carta annulla, da una sosta alla pedina. Altrimenti scarta la carta divieto di sosta
    private void sosta(Pedina p, int val){
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

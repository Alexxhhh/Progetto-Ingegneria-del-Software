package Esecuzione;

import Componenti.Carte.Mazzo;
import Componenti.Dadi.Dadi;
import Componenti.Dadi.Dado;
import Componenti.Scacchiera.Pedina;
import Componenti.Scacchiera.Scacchiera;
import Salvataggio.Regole;
import Utility.Observer.Messaggio.Messaggio;
import Utility.Observer.Messaggio.TipologiaMessaggio;

import java.util.Queue;

public class Gioco extends AbstractGioco {

    public static class Builder{
        private int righe;
        private int colonne;
        private Queue<Pedina> giocatori;
        private boolean dadoSingolo=false;
        private boolean lancioSingolo=false;
        private boolean doppioSei=false;
        private boolean ulterioriCarte=false;
        private boolean sosta=false;
        private boolean pesca=false;
        private boolean premio=false;
        private boolean simulata=true;

        public Builder(int righe, int colonne,Queue<Pedina> giocatori){
            this.giocatori=giocatori;
            this.righe=righe;
            this.colonne=colonne;
        }


        public Builder setSosta(boolean sosta) {
            this.sosta = sosta;
            return this;
        }

        public Builder setPesca(boolean pesca) {
            this.pesca = pesca;
            return this;
        }

        public Builder setPremio(boolean premio) {
            this.premio = premio;
            return this;
        }

        public Builder setDadoSingolo(boolean dadoSingolo) {
            this.dadoSingolo = dadoSingolo;
            return this;
        }

        public Builder setLancioSingolo(boolean lancioSingolo) {
            this.lancioSingolo = lancioSingolo;
            return this;
        }

        public Builder setDoppioSei(boolean doppioSei) {
            this.doppioSei = doppioSei;
            return this;
        }

        public Builder setUlterioriCarte(boolean ulterioriCarte) {
            this.ulterioriCarte = ulterioriCarte;
            return this;
        }

        public Builder setSimulata(boolean simulata) {
            this.simulata = simulata;
            return this;
        }

        public Gioco build(){
            return new Gioco(this);
        }

    }
    private Gioco(Builder b){
        this.scacchiera=new Scacchiera.Builder(b.righe,b.colonne).setPesca(b.pesca).setPremio(b.premio).setSosta(b.sosta).build();
        this.giocatori=b.giocatori;
        this.simulata=b.simulata;
        if (b.pesca){
            this.mazzo=new Mazzo(b.ulterioriCarte);
        }
        this.regole=new Regole.Builder(b.righe,b.colonne).setDadoSingolo(b.dadoSingolo).setLancioSingolo(b.lancioSingolo)
                .setDoppioSei(b.doppioSei).setPesca(b.pesca).setPremio(b.premio).setSosta(b.sosta).setUlterioriCarte(b.ulterioriCarte)
                .build();
        if (b.dadoSingolo){
            dadi=new Dado();
        }
        else {
            dadi = new Dadi();
        }
        flagGioco=true;
    }

    public void run(){
        try {
            semaphore.acquire();
            if(!simulata){
                if(flagGioco){
                    Pedina pedina=giocatori.poll();
                    giocatori.add(pedina);
                    gioca(pedina);
                }
                else{
                    notifica(new Messaggio(TipologiaMessaggio.FINEPARTITA));
                }
            }else{
                while (flagGioco){
                    Pedina pedina=giocatori.poll();
                    giocatori.add(pedina);
                    gioca(pedina);
                }
                notifica(new Messaggio(TipologiaMessaggio.FINEPARTITA));
            }
            semaphore.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void tira(){
        Thread t=new Thread(this);
        t.start();
    }

}

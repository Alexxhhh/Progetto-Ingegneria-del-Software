package Componenti.Scacchiera;

import Componenti.Carte.Carta;
import Componenti.Salti.DadiC;
import Componenti.Salti.Molla;
import Componenti.Salti.Prolungati.Prolungati;
import Componenti.Scacchiera.Caselle.Casella;
import Componenti.Scacchiera.Caselle.TipologiaCasella;
import Componenti.Salti.Prolungati.Scale;
import Componenti.Salti.Prolungati.Serpenti;
import Componenti.Soste.Locanda;
import Componenti.Soste.Panchina;
import Utility.Coordinate;

public final class Scacchiera {
    private Coordinate coordinate;
    private Prolungati[] scale;
    private Prolungati[] serpenti;
    private Casella[][] scacchiera;

    public static class Builder{
        private int righe;
        private int colonne;
        private boolean sosta=false;
        private boolean pesca=false;
        private boolean premio=false;
        static private Scacchiera s;

        public Builder(int righe, int colonne){
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
        public Scacchiera build(){
            if(s==null){
                s=new Scacchiera(this);
            }
            return s;

        }
    }

    private Scacchiera(Builder b){
        this.coordinate=new Coordinate(b.colonne,b.righe);
        scacchiera= new Casella[b.righe][b.colonne];
        int numScale= (int) (b.righe*0.7);
        int numSerpenti= (int) (b.righe*0.6);
        scale=new Scale[numScale];
        serpenti=new Serpenti[numSerpenti];
        for (int i=0;i<numScale;i++){
            scale[i]=new Scale(coordinate, scacchiera);
        }
        for (int i=0;i<numSerpenti;i++){
            serpenti[i]=new Serpenti(coordinate, scacchiera);
        }
        if (b.premio){
            int dadip= (int) (b.righe*0.7);
            int molle= (int) (b.righe*0.6);
            for (int i=0;i<dadip;i++){
                DadiC.setDadi(coordinate, scacchiera);
            }
            for (int i=0;i<molle;i++){
                Molla.setMolla(coordinate, scacchiera);
            }
        }
        if (b.sosta){
            int panchine= (int) (b.righe*0.7);
            int locanda= (int) (b.righe*0.6);
            for (int i=0;i<panchine;i++){
                Panchina.setPanchina(coordinate, scacchiera);
            }
            for (int i=0;i<locanda;i++){
                Locanda.setLocanda(coordinate, scacchiera);
            }
        }
        if(b.pesca){
            int pescare= (int) (b.righe*0.7);
            for (int i=0;i<pescare;i++){
                Carta.setPesca(coordinate, scacchiera);
            }
        }
        for (int i=0;i<b.righe;i++){
            for (int j=0;j<b.colonne;j++){
                if (scacchiera[i][j]==null) {
                    scacchiera[i][j] = new Casella(TipologiaCasella.VUOTA);
                }
            }
        }
    }

    public Coordinate codaSe(Coordinate c){
        for (Prolungati s:serpenti){
            if(s.isInizio(c)){
                return s.getDestinazione();
            }
        }
        return null;
    }

    public Coordinate inizioSc(Coordinate c){
        for (Prolungati s:scale){
            if(s.isInizio(c)){
               return s.getDestinazione();
            }
        }
        return null;
    }

    public int numProl(Coordinate c){
        for(int i=0;i<serpenti.length;i++){
            if (serpenti[i].isFine(c)||serpenti[i].isInizio(c)){
                return i;
            }
        }
        for(int i=0;i<scale.length;i++){
            if (scale[i].isFine(c)||scale[i].isInizio(c)){
                return i;
            }
        }
        return -1;
    }

    public TipologiaCasella getCasella(Pedina p){
        return scacchiera[p.getPos().getY()][p.getPos().getX()].getCasella();
    }
    public TipologiaCasella getCasella(Coordinate c){
        return scacchiera[c.getY()][c.getX()].getCasella();
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Prolungati[] getScale() {
        return scale;
    }

    public Prolungati[] getSerpenti() {
        return serpenti;
    }

    public Casella[][] getScacchiera() {
        return scacchiera;
    }

}

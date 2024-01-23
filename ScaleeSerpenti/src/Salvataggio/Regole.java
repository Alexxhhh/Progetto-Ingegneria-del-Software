package Salvataggio;

import Utility.Coordinate;

//Memorizza le regole del gioco
public class Regole {
    private Coordinate coordinate;
    private boolean sosta;
    private boolean pesca;
    private boolean premio;
    private boolean dadoSingolo;
    private boolean lancioSingolo;
    private boolean doppioSei;
    private boolean ulterioriCarte;


    public static class Builder{ //pattern Builder
        private int righe;
        private int colonne;
        private boolean dadoSingolo=false;
        private boolean lancioSingolo=false;
        private boolean doppioSei=false;
        private boolean ulterioriCarte=false;
        private boolean sosta=false;
        private boolean pesca=false;
        private boolean premio=false;

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

        public Regole build(){
            return new Regole(this);
        }
    }
    private Regole(Builder b) {
        this.coordinate=new Coordinate(b.colonne,b.righe);
        this.sosta = b.sosta;
        this.pesca = b.pesca;
        this.premio = b.premio;
        this.dadoSingolo = b.dadoSingolo;
        this.lancioSingolo = b.lancioSingolo;
        this.doppioSei = b.doppioSei;
        this.ulterioriCarte = b.ulterioriCarte;
    }

    public boolean isLancioSingolo() {
        return lancioSingolo;
    }

    public boolean isDoppioSei() {
        return doppioSei;
    }

}

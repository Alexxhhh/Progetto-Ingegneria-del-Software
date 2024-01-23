package Componenti.Scacchiera.Caselle;

//La classe memorizza la tipologia di casella nella scacchiera
public class Casella {
    private TipologiaCasella casella;

    public Casella(TipologiaCasella casella){
        this.casella=casella;
    }

    public TipologiaCasella getCasella() {
        return casella;
    }
}
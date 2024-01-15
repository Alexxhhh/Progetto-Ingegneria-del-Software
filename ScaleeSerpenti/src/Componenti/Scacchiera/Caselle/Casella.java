package Componenti.Scacchiera.Caselle;

public class Casella {
    private TipologiaCasella casella;

    public Casella(TipologiaCasella casella){
        this.casella=casella;
    }

    public TipologiaCasella getCasella() {
        return casella;
    }
}

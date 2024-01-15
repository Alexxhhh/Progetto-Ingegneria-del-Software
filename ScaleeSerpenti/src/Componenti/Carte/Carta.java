package Componenti.Carte;

import Componenti.Scacchiera.Caselle.Casella;
import Componenti.Scacchiera.Caselle.TipologiaCasella;
import Utility.Coordinate;

import java.util.Random;

public class Carta {
    private TipologiaCarte c;
    public Carta(TipologiaCarte t){
        c=t;
    }
    public TipologiaCarte getTipo(){
        return c;
    }

    public static void setPesca(Coordinate c, Casella[][] scacchiera){
        Random r=new Random();
        while (true){
            int rig1=r.nextInt(c.getY());
            int col1=r.nextInt(c.getX());
            if (!((rig1==0 && col1==0)||(rig1==c.getY()-1 && col1==c.getX()-1))) {
                if (scacchiera[rig1][col1] == null) {
                    scacchiera[rig1][col1] = new Casella(TipologiaCasella.PESCA);
                    break;
                }
            }
        }
    }
}

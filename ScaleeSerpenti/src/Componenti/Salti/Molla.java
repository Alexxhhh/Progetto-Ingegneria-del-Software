package Componenti.Salti;

import Componenti.Scacchiera.Caselle.Casella;
import Componenti.Scacchiera.Caselle.TipologiaCasella;
import Utility.Coordinate;

import java.util.Random;

//La classe serve a generare le molle nella scacchiera se viene attivata la regola premio
public final class Molla {
    private Molla(){}
    public static void setMolla(Coordinate c, Casella[][] scacchiera){
        Random r=new Random();
        while (true){
            int rig=r.nextInt(c.getY()-1); //l'ultima riga non presenta molle per evitare loop
            int col=r.nextInt(c.getX());
            if (!((rig==0 && col==0)||(rig==c.getY()-1 && col==c.getX()-1))) {
                if (scacchiera[rig][col] == null) {
                    scacchiera[rig][col] = new Casella(TipologiaCasella.MOLLA);
                    break;
                }
            }
        }
    }
}

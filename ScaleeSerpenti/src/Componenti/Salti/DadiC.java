package Componenti.Salti;

import Componenti.Scacchiera.Caselle.Casella;
import Componenti.Scacchiera.Caselle.TipologiaCasella;
import Utility.Coordinate;

import java.util.Random;

//La classe serve a generare le caselle dado nella scacchiera se viene attivata la regola premio per ritirare nuovamente
public final class DadiC {
    private DadiC(){}
    public static void setDadi(Coordinate c, Casella[][] scacchiera){
        Random r=new Random();
        while (true){
            int rig=r.nextInt(c.getY());
            int col=r.nextInt(c.getX());
            if (!((rig==0 && col==0)||(rig==c.getY()-1 && col==c.getX()-1))) {
                if (scacchiera[rig][col] == null) {
                    scacchiera[rig][col] = new Casella(TipologiaCasella.DADI);
                    break;
                }
            }
        }
    }
}

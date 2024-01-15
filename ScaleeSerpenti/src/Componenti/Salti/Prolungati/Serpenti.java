package Componenti.Salti.Prolungati;

import Componenti.Scacchiera.Caselle.Casella;
import Componenti.Scacchiera.Caselle.TipologiaCasella;
import Utility.Coordinate;

import java.util.Random;

public class Serpenti extends Prolungati {

    public Serpenti(Coordinate c, Casella[][] scacchiera){
        Random r=new Random();
        while (true){
            int rig1=r.nextInt(c.getY());
            int rig2=r.nextInt(c.getY());
            if (rig1<rig2){
                int col1=r.nextInt(c.getX());
                int col2=r.nextInt(c.getX());
                if (!((rig1==0 && col1==0)||(rig2==c.getY()-1 && col2==c.getX()-1))){
                    if(scacchiera[rig1][col1]==null && scacchiera[rig2][col2]==null){
                        scacchiera[rig1][col1]=new Casella(TipologiaCasella.CODA_SE);
                        scacchiera[rig2][col2]=new Casella(TipologiaCasella.TESTA_SE);
                        inizio=new Coordinate(col2,rig2);
                        fine=new Coordinate(col1,rig1);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Serpenti{" +
                "inizio=" + inizio +
                ", fine=" + fine +
                '}';
    }
}

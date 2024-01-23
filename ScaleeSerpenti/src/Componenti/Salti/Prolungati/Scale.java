package Componenti.Salti.Prolungati;

import Componenti.Scacchiera.Caselle.Casella;
import Componenti.Scacchiera.Caselle.TipologiaCasella;
import Utility.Coordinate;

import java.util.Random;

//La classe serve a generare le scale nella scacchiera e a definire inizio e fine della scala stessa
public class Scale extends Prolungati {
    public Scale(Coordinate c, Casella[][] scacchiera){
        Random r=new Random();
        while (true){
            int rig1=r.nextInt(c.getY());
            int rig2=r.nextInt(c.getY());
            if (rig1<rig2){
                int col1=r.nextInt(c.getX());
                int col2=r.nextInt(c.getX());
                if (!((rig1==0 && col1==0)||(rig2==c.getY()-1 && col2==c.getX()-1))){
                    if(scacchiera[rig1][col1]==null && scacchiera[rig2][col2]==null){
                        scacchiera[rig1][col1]=new Casella(TipologiaCasella.INIZIO_SC);
                        scacchiera[rig2][col2]=new Casella(TipologiaCasella.FINE_SC);
                        inizio=new Coordinate(col1,rig1);
                        fine=new Coordinate(col2,rig2);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Scale{" +
                "inizio=" + inizio +
                ", fine=" + fine +
                '}';
    }
}

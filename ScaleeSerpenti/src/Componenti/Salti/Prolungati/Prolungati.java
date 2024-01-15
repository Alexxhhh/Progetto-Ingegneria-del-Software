package Componenti.Salti.Prolungati;

import Utility.Coordinate;

public abstract class Prolungati {

    protected Coordinate inizio;
    protected Coordinate fine;
    public boolean isInizio(Coordinate c){
        return c.getX() == inizio.getX() && c.getY() == inizio.getY();
    }
    public boolean isFine(Coordinate c){
        return c.getX() == fine.getX() && c.getY() == fine.getY();
    }


    public Coordinate getDestinazione(){
        return fine;
    }

}

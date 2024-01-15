package Componenti.Scacchiera;

import Componenti.Carte.Carta;
import Utility.Coordinate;

public class Pedina {
    private Coordinate pos;
    private Coordinate prePos;

    private Carta annulla;

    private int colore;

    private String nome;

    private int sosta;

    private boolean doppio;

    public Pedina(String nome,int colore){
        this.nome=nome;
        this.colore=colore;
        pos=new Coordinate(0,0);
        prePos=new Coordinate(0,0);
        sosta=0;
        doppio=false;
    }

    public Coordinate getPos() {
        return pos;
    }
    public Coordinate getPrePos() {
        return prePos;
    }

    public Coordinate incrPos(int val, Coordinate p) {
        prePos=new Coordinate(pos.getX(), pos.getY());
        for (int i=0;i<val;i++){
            pos.setX(pos.getX()+1);
            if(pos.getX()==p.getX()){
                pos.setY(pos.getY()+1);
                pos.setX(0);
            }
        }
        return pos;
    }

    public void decrPos(int val, Coordinate p) {
        for (int i=0;i<val;i++){
            pos.setX(pos.getX()-1);
            if(pos.getX()==-1){
                pos.setY(pos.getY()-1);
                pos.setX(p.getX()-1);
            }
        }
    }

    public void indietreggia(Coordinate p){
        int val=0;
        while (pos.getY()>=p.getY()){
            val++;
            pos.setX(pos.getX()-1);
            if(pos.getX()==-1){
                pos.setX(p.getX()-1);
                pos.setY(pos.getY()-1);
            }
        }
        decrPos(val,p);
    }

    public void setPos(Coordinate c) {
        prePos=new Coordinate(pos.getX(), pos.getY());
        pos.setX(c.getX());
        pos.setY(c.getY());
    }

    public boolean isAnnulla() {
        return annulla == null;
    }

    public void aggiungiAnn(Carta annulla) {
        this.annulla = annulla;
    }

    public Carta rimuoviAnn() {
        Carta c=annulla;
        annulla=null;
        return c;
    }

    public int getColore() {
        return colore;
    }

    public void setColore(int colore) {
        this.colore = colore;
    }

    public String getNome() {
        return nome;
    }


    public boolean turno(){
        if(sosta==0){
            return true;
        }
        sosta-=1;
        return false;
    }

    public void setSosta(int sosta){
        this.sosta=sosta;
    }

    public int getSosta() {
        return sosta;
    }

    public boolean isDoppio() {
        return doppio;
    }

    public void setDoppio(boolean doppio) {
        this.doppio = doppio;
    }

    @Override
    public String toString() {
        return "Pedina{" +
                "pos=" + pos +
                ", annulla=" + annulla +
                ", colore=" + colore +
                ", nome='" + nome + '\'' +
                ", sosta=" + sosta +
                '}';
    }
}

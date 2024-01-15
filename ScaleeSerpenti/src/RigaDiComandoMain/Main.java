package RigaDiComandoMain;

import Componenti.Scacchiera.Pedina;
import Esecuzione.AbstractGioco;
import Esecuzione.Gioco;
import Utility.Observer.Riga;

import java.util.ArrayDeque;
import java.util.Queue;

//riga di valori
public class Main {
    public static void main(String[] args){
        Queue<Pedina> ped= new ArrayDeque<>();
        Pedina p1=new Pedina("a",1);
        Pedina p2=new Pedina("b",2);
        ped.add(p1);
        ped.add(p2);
        for(int i=0;i<1;i++){
            AbstractGioco gioco=new Gioco.Builder(10,10,ped).setDadoSingolo(false).setLancioSingolo(true)
                    .setDoppioSei(true).setPesca(true).setPremio(true).setSosta(true).setUlterioriCarte(true).setSimulata(true).build();
            gioco.genera();
            gioco.addAscolatoreEventi(new Riga());
            gioco.run();
        }

    }
}

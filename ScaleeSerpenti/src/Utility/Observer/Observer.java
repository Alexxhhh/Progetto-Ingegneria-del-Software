package Utility.Observer;

import GuiMain.Main;
import Utility.Observer.Messaggio.Messaggio;
import Utility.Observer.Messaggio.TipologiaMessaggio;

import java.util.concurrent.TimeUnit;

public class Observer implements AscolatoreEventi{

    //CASELLE true:ITURNO,FTURNO,SERPENTE,SCALA,PANCHINA,LOCANDA,MOLLA,PESCA,DADI,SOSTA
    //CARTE false:PANCHINA,LOCANDA,MOLLA,DADI,ANNULLA,RIMANNULLA
    @Override
    public void aggiornamento(Messaggio m) {
        if (m.getP()!=null) {
            if (m.isB()) {
                switch (m.getTipologiaMessaggio()) {
                    case ITURNO -> {
                        Main.getIstance().fGioco.textArea.setText(m.getP().getNome() + " inizia il turno\n");
                        aggiornaP(m);
                    }
                    case FTURNO -> {
                        Main.getIstance().fGioco.textArea.append(m.getP().getNome() + " finisce il turno\n");
                        aggiornaP(m);
                        attendi(1500);
                    }
                    case SERPENTE -> {
                        Main.getIstance().fGioco.textArea.append("Hai preso un serpente\n");

                    }
                    case SCALA -> {
                        Main.getIstance().fGioco.textArea.append("Hai preso una scala\n");

                    }
                    case PANCHINA -> {
                        Main.getIstance().fGioco.textArea.append("Hai preso una panchina, fermo per 1 turno\n");
                        aggiornaP(m);
                    }
                    case LOCANDA -> {
                        Main.getIstance().fGioco.textArea.append("Hai preso una locanda, fermo per 3 turni\n");
                        aggiornaP(m);
                    }
                    case MOLLA -> {
                        Main.getIstance().fGioco.textArea.append("Hai preso una molla\n");

                    }
                    case PESCA -> {
                        Main.getIstance().fGioco.textArea.append("Pesca una carta\n");

                    }
                    case DADI -> {
                        Main.getIstance().fGioco.textArea.append("Ritira i dadi!\n");

                    }
                    case SOSTA -> {
                        Main.getIstance().fGioco.textArea.append(m.getP().getNome() + " salta il turno, ancora "+m.getP().getSosta()+"\n");

                    }
                    case MUOVI ->{
                        sostituisci(m);
                    }
                }
            } else {
                if (m.getVal() == -1) {
                    switch (m.getTipologiaMessaggio()) {
                        case PANCHINA -> {
                            Main.getIstance().fGioco.textArea.append("Hai pescato una panchina\n");
                            aggiornaP(m);
                        }
                        case LOCANDA -> {
                            Main.getIstance().fGioco.textArea.append("Hai pescato una locanda\n");
                            aggiornaP(m);
                        }
                        case MOLLA -> {
                            Main.getIstance().fGioco.textArea.append("Hai pescato una molla\n");

                        }
                        case DADI -> {
                            Main.getIstance().fGioco.textArea.append("Hai pescato dei dadi\n");

                        }
                        case ANNULLA -> {
                            Main.getIstance().fGioco.textArea.append("Hai pescato un divieto di sosta\n");
                            aggiornaP(m);
                        }
                        case RIMANNULLA -> {
                            Main.getIstance().fGioco.textArea.append("Hai scartato il divieto di sosta\n");
                            aggiornaP(m);
                        }
                    }
                } else {
                    Main.getIstance().fGioco.textArea.append("I dadi hanno fatto "+m.getVal()+"\n");
                    sostituisci(m);
                }
            }
        }else if(m.getTipologiaMessaggio()== TipologiaMessaggio.DOPPIO){
            Main.getIstance().fGioco.textArea.append("Hai fatto doppio!\n");

        }
        if(m.getTipologiaMessaggio()==TipologiaMessaggio.FINEPARTITA){
            Main.getIstance().fGioco.textArea.append("Parita finita!\n");
        }
        attendi();
    }
    private void sostituisci(Messaggio m){
        int iv;
        int jv;
        int i=m.getP().getPrePos().getY();
        int j=m.getP().getPrePos().getX();
        while(i!=m.getP().getPos().getY()||j!=m.getP().getPos().getX()){
            if(i>m.getP().getPos().getY()){
                iv=i;
                i--;
            }
            else if(i<m.getP().getPos().getY()){
                iv=i;
                i++;
            }
            else{
                iv=i;
            }
            if(j>m.getP().getPos().getX()){
                jv=j;
                j--;
            }
            else if(j<m.getP().getPos().getX()){
                jv=j;
                j++;
            }
            else{
                jv=j;
            }
            Main.getIstance().scacchiera[iv][jv].rim(m.getP().getColore());
            Main.getIstance().scacchiera[i][j].set(m.getP().getColore());
            attendi(200);
        }
    }
    private void aggiornaP(Messaggio m){
        Main.getIstance().fGioco.giocatoriL[m.getP().getColore()-1].setText(m.getP().getNome()+", Sosta: "+m.getP().getSosta()+" turni, Carta divieto di sosta: "+!m.getP().isAnnulla());
    }
    private void attendi(int val){
        try {
            TimeUnit.MILLISECONDS.sleep(val);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Main.getIstance().fGioco.repaint();
    }
    private void attendi(){
        attendi(500);
    }

}

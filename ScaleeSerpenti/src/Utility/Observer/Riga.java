package Utility.Observer;

import Utility.Observer.Messaggio.Messaggio;

public class Riga implements AscolatoreEventi{
    @Override
    public void aggiornamento(Messaggio m) {
        if (m.getP()!=null) {
            if (m.isB()) {
                switch (m.getTipologiaMessaggio()) {
                    case ITURNO -> {
                        System.out.println(m.getP().getNome() + " inizia il turno");
                        System.out.println("Sei a "+m.getP().getPos());
                    }
                    case FTURNO -> {
                        System.out.println("Finisce a "+m.getP().getPos());
                        System.out.println(m.getP().getNome() + " finisce il turno");
                    }
                    case SERPENTE -> {
                        System.out.println("Hai preso un serpente");
                    }
                    case SCALA -> {
                        System.out.println("Hai preso una scala");
                    }
                    case PANCHINA -> {
                        System.out.println("Hai preso una panchina, fermo per 1 turno");
                    }
                    case LOCANDA -> {
                        System.out.println("Hai preso una locanda, fermo per 3 turni");
                    }
                    case MOLLA -> {
                        System.out.println("Hai preso una molla");
                    }
                    case PESCA -> {
                        System.out.println("Pesca una carta");
                    }
                    case DADI -> {
                        System.out.println("Ritira i dadi");
                    }
                    case SOSTA -> {
                        System.out.println("Salta il turno, ancora "+m.getP().getSosta());
                    }
                }
            } else {
                if (m.getVal() == -1) {
                    switch (m.getTipologiaMessaggio()) {
                        case PANCHINA -> {
                            System.out.println("Hai pescato una panchina");
                        }
                        case LOCANDA -> {
                            System.out.println("Hai pescato una locanda");
                        }
                        case MOLLA -> {
                            System.out.println("Hai pescato una molla");
                        }
                        case DADI -> {
                            System.out.println("Hai pescato dei dadi");
                        }
                        case ANNULLA -> {
                            System.out.println("Hai pescato un divieto di sosta");
                        }
                        case RIMANNULLA -> {
                            System.out.println("Hai scartato il divieto di sosta");
                        }
                    }
                } else {
                    System.out.println("I dadi hanno fatto "+m.getVal());
                    System.out.println("Vai a "+m.getP().getPos());
                }
            }
        }else{
            System.out.println("Hai fatto doppio");
        }
    }
}

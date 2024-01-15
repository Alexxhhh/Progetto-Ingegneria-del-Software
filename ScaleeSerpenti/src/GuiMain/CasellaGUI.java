package GuiMain;

import Componenti.Scacchiera.Scacchiera;
import Utility.Coordinate;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CasellaGUI {
    private JPanel panel;
    private JPanel panelSup, pa, pb, pc, pd;
    private int num;
    private String tipologia;

    public CasellaGUI(Coordinate c, Scacchiera s){
        panel=new JPanel();
        panel.setBorder(new LineBorder(Color.BLACK));
        panel.setLayout(new GridLayout(2,1));
        panelSup=new JPanel();
        panelSup.setLayout(new GridLayout(1,4));
        pa=new JPanel();
        pa.add(new JLabel(""));
        pa.setBackground(Color.WHITE);
        panelSup.add(pa);
        pb=new JPanel();
        pb.add(new JLabel(""));
        pb.setBackground(Color.WHITE);
        panelSup.add(pb);
        pc=new JPanel();
        pc.add(new JLabel(""));
        pc.setBackground(Color.WHITE);
        panelSup.add(pc);
        pd=new JPanel();
        pd.add(new JLabel(""));
        pd.setBackground(Color.WHITE);
        panelSup.add(pd);
        panelSup.setBorder(new LineBorder(Color.BLACK));
        panel.add(panelSup);
        num=(c.getY()*s.getCoordinate().getX())+c.getX()+1;
        switch (s.getCasella(c)) {
            case VUOTA -> {
                tipologia="";
            }
            case TESTA_SE -> {
                tipologia="TSE:"+s.numProl(c);
            }
            case CODA_SE -> {
                tipologia="CSE:"+s.numProl(c);
            }
            case INIZIO_SC -> {
                tipologia="ISC:"+s.numProl(c);
            }
            case FINE_SC -> {
                tipologia="FSC:"+s.numProl(c);
            }
            case PANCHINA -> {
                tipologia="PANCH";
            }
            case LOCANDA -> {
                tipologia="LOCAN";
            }
            case MOLLA -> {
                tipologia="MOLLA";
            }
            case PESCA -> {
                tipologia="PESCA";
            }
            case DADI -> {
                tipologia="DADI";
            }
        }
        JLabel label=new JLabel(num+" "+tipologia);
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);
    }
    public JPanel getPanel(){
        return panel;
    }
    public void set(int val){
        switch (val){
            case 1 ->{
                pa.setBackground(Color.RED);
            }
            case 2 ->{
                pb.setBackground(Color.GREEN);
            }
            case 3 ->{
                pc.setBackground(Color.BLUE);
            }
            case 4 ->{
                pd.setBackground(Color.YELLOW);
            }
        }
    }
    public void rim(int val){
        switch (val){
            case 1 ->{
                pa.setBackground(Color.WHITE);
            }
            case 2 ->{
                pb.setBackground(Color.WHITE);
            }
            case 3 ->{
                pc.setBackground(Color.WHITE);
            }
            case 4 ->{
                pd.setBackground(Color.WHITE);
            }
        }
    }

}
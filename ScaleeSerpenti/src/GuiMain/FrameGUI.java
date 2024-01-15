package GuiMain;

import Componenti.Scacchiera.Pedina;
import Esecuzione.AbstractGioco;
import Esecuzione.Gioco;
import Utility.Observer.Observer;
import Utility.Coordinate;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class FrameGUI extends JFrame {
    //bottoni menu'
    private JMenuItem open, save, saveAs, exit, about;

    private FrameM fM;
    private FrameGiocatori fGiocatori;
    public FrameGioco fGioco;
    private String title="Scale e serpenti"; //nome finestra
    private String dir="default_data.txt";

    public AbstractGioco gioco;
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("File TXT", "txt");
    private JFileChooser directory=null;
    private File file=new File("default_data.txt"); //directory di salvataggio

    private int brighe=10;
    private int bcolonne=10;
    public Queue<Pedina> giocatori=new ArrayDeque<>();
    private boolean dadoSingolo;
    private boolean blancioSingolo;
    private boolean bdoppioSei;
    private boolean bulterioriCarte;
    private boolean bsosta;
    private boolean bpesca;
    private boolean bpremio;
    private boolean bsimulata=true;
    public CasellaGUI[][] scacchiera;



    public FrameGUI() {
        FrameM menu= new FrameM();
        menu.setVisible(true);

    }
    private class FrameM extends JFrame implements ActionListener {
        private JPanel panel, confirm, giocat;
        private JLabel text;
        private JButton conf, gioc;
        private JCheckBox lancioSingolo, doppioSei, ulterioriCarte, sosta, pesca, premio, simulata;
        private JTextField righe, colonne;
        private JRadioButton dado, dadi;
        private ButtonGroup choices;
        public FrameM() {
            setTitle(title);
            setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
            addWindowListener(
                    new WindowAdapter() {
                        public void windowClosing(WindowEvent e){
                            if( cExit() ) System.exit(0);
                        }
                    }
            );

            //creazione menu' bar
            JMenuBar menuB=new JMenuBar();
            this.setJMenuBar(menuB);

            JMenu fileM=new JMenu("File");
            menuB.add(fileM);
            JMenu otherM=new JMenu("Altro");
            menuB.add(otherM);


            //creazione menu' file
            open=new JMenuItem("Carica");
            fileM.add(open);
            open.addActionListener(this);
            fileM.addSeparator();

            save=new JMenuItem("Salva");
            fileM.add(save);
            save.addActionListener(this);
            saveAs=new JMenuItem("Salva con nome");
            fileM.add(saveAs);
            saveAs.addActionListener(this);
            fileM.addSeparator();

            exit=new JMenuItem("Esci");
            fileM.add(exit);
            exit.addActionListener(this);

            //creazione menu' altro
            about=new JMenuItem("Informazioni");
            otherM.add(about);
            about.addActionListener(this);



            setLayout(new GridLayout(8, 2));

            text = new JLabel("Regole di gioco:");
            text.setFont(new Font("Serif", Font.PLAIN, 20));
            text.setHorizontalAlignment(JLabel.CENTER);
            add(text, BorderLayout.NORTH);
            add(new JLabel(""));
            add(new JLabel(""));
            add(new JLabel(""));

            JLabel label=new JLabel("Righe:");
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            righe=new JTextField(brighe+"");
            righe.addActionListener(this);
            add(righe);
            label=new JLabel("Colonne:");
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            colonne=new JTextField(bcolonne+"");
            colonne.addActionListener(this);
            add(colonne);

            label=new JLabel("Dado:");
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            dado = new JRadioButton("Singolo");
            dado.addActionListener(this);
            dadi = new JRadioButton("Doppio");
            dadi.addActionListener(this);
            if(dadoSingolo){
                dado.setSelected(true);
            }
            else{
                dadi.setSelected(true);
            }
            choices = new ButtonGroup();
            choices.add(dado);
            choices.add(dadi);
            panel=new JPanel();
            panel.add(dado);
            panel.add(dadi);
            add(panel);
            label=new JLabel("Lancio singolo:");
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            lancioSingolo=new JCheckBox("*");
            lancioSingolo.addActionListener(this);
            lancioSingolo.setSelected(blancioSingolo);
            add(lancioSingolo);

            label=new JLabel("Doppio sei:");
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            doppioSei=new JCheckBox("*");
            doppioSei.addActionListener(this);
            doppioSei.setSelected(bdoppioSei);
            add(doppioSei);
            label=new JLabel("Ulteriori carte:");
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            ulterioriCarte=new JCheckBox("**");
            ulterioriCarte.addActionListener(this);
            ulterioriCarte.setSelected(bulterioriCarte);
            add(ulterioriCarte);

            label=new JLabel("Sosta:");
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            sosta=new JCheckBox();
            sosta.addActionListener(this);
            sosta.setSelected(bsosta);
            add(sosta);
            label=new JLabel("Pesca:");
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            pesca=new JCheckBox();
            pesca.addActionListener(this);
            pesca.setSelected(bpesca);
            add(pesca);

            label=new JLabel("Premio:");
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            premio=new JCheckBox();
            premio.addActionListener(this);
            premio.setSelected(bpremio);
            add(premio);
            label=new JLabel("Simulazione:");
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            simulata=new JCheckBox();
            simulata.addActionListener(this);
            simulata.setSelected(bsimulata);
            add(simulata);

            label=new JLabel("Giocatori: "+giocatori.toArray().length);
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            giocat = new JPanel();
            gioc = new JButton("Giocatori");
            gioc.addActionListener(this);
            giocat.add(gioc);
            add(giocat);
            label=new JLabel("Regole attivabili due dadi (*)");
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            label=new JLabel("Regole attivabili con pesca  (**)");
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);

            add(new JLabel(""));
            confirm = new JPanel();
            conf = new JButton("Conferma");
            conf.addActionListener(this);
            confirm.add(conf);
            add(confirm);

            //dimensione e posizione finestra
            pack();
            setSize(750, 400);
            setResizable(false);
            setLocationByPlatform(true);
            setDefaultCloseOperation((JFrame.DO_NOTHING_ON_CLOSE));
        }
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==dado) {
                dadoSingolo=true;
            }
            else if(e.getSource()==dadi) {
                dadoSingolo=false;
            }
            else if(e.getSource()==lancioSingolo){
                blancioSingolo= !blancioSingolo;
            }
            else if(e.getSource()==doppioSei){
                bdoppioSei=!bdoppioSei;
            }
            else if(e.getSource()==ulterioriCarte){
                bulterioriCarte=!bulterioriCarte;
            }
            else if(e.getSource()==premio){
                bpremio=!bpremio;
            }
            else if(e.getSource()==sosta){
                bsosta=!bsosta;
            }
            else if(e.getSource()==pesca){
                bpesca=!bpesca;
            }
            else if(e.getSource()==simulata){
                bsimulata=!bsimulata;
            }
            else if(e.getSource()==gioc){
                dispose();
                fGiocatori = new FrameGiocatori();
                fGiocatori.setVisible(true);
            }
            else if(e.getSource()==conf){
                try {
                    if (dadoSingolo && (bdoppioSei || blancioSingolo)) {
                        JOptionPane.showMessageDialog(null, "Regole dado singolo non rispettate");
                    } else if (!bpesca && bulterioriCarte) {
                        JOptionPane.showMessageDialog(null, "Regole ulteriori attivabile solo con regola pesca");
                    } else if (Integer.parseInt(righe.getText()) < 7 || Integer.parseInt(righe.getText()) > 18
                            || Integer.parseInt(colonne.getText()) < 7 || Integer.parseInt(colonne.getText()) > 18) {
                        JOptionPane.showMessageDialog(null, "Dimensione scacchiera minima 7x7, massima 18x18");
                    } else if (giocatori.toArray().length == 0) {
                        JOptionPane.showMessageDialog(null, "Aggiungere almeno un giocatore");
                    } else {
                        brighe = Integer.parseInt(righe.getText());
                        bcolonne = Integer.parseInt(colonne.getText());
                        gioco = new Gioco.Builder(brighe, bcolonne, giocatori).setDadoSingolo(dadoSingolo)
                                .setLancioSingolo(blancioSingolo).setDoppioSei(bdoppioSei).setPesca(bpesca)
                                .setPremio(bpremio).setSosta(bsosta).setUlterioriCarte(bulterioriCarte)
                                .setSimulata(bsimulata).build();
                        gioco.addAscolatoreEventi(new Observer());
                        fGioco = new FrameGioco();
                        fGioco.setVisible(true);
                        dispose();
                    }
                }catch (Exception error){
                    JOptionPane.showMessageDialog(null, "Dimensione scacchiera non valida");
                }
            }
            else if (e.getSource()==open) {
                openFile();
                dispose();
                fM = new FrameM();
                fM.setVisible(true);
            }
            else if (e.getSource()==save) {
                brighe=Integer.parseInt(righe.getText());
                bcolonne=Integer.parseInt(colonne.getText());
                saveFile();
            }
            else if (e.getSource()==saveAs) {
                brighe=Integer.parseInt(righe.getText());
                bcolonne=Integer.parseInt(colonne.getText());
                saveAsFile();
            }
            else if (e.getSource()==exit) {
                exitProg();
            }
            else if (e.getSource()==about) {
                info();
            }
        }
    }
    private class FrameGiocatori extends JFrame implements ActionListener{
        private JTextField nome;
        private String bnome;
        private JButton ok, cancella1, cancella2, cancella3, cancella4;
        public FrameGiocatori(){
            setTitle("Giocatori");
            setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
            addWindowListener(
                    new WindowAdapter() {
                        public void windowClosing(WindowEvent e){
                            dispose();
                            FrameM menu= new FrameM();
                            menu.setVisible(true);
                        }
                    }
            );
            this.setLayout(new GridLayout(5, 1));
            JPanel agg=new JPanel();
            agg.setLayout(new GridLayout(1, 3));

            JLabel label=new JLabel("Aggiungi giocatore:");
            label.setHorizontalAlignment(JLabel.CENTER);
            agg.add(label);
            agg.add( nome=new JTextField("",12) );
            agg.add( ok=new JButton("OK") );
            add(agg);
            Queue<Pedina> temp=new ArrayDeque<>();
            boolean flag=true;
            Pedina tempp;
            while (flag){
                tempp=giocatori.poll();
                JPanel b=new JPanel();
                b.setLayout(new GridLayout(1, 2));
                if(tempp!=null){
                    label=new JLabel(tempp.getNome());
                    label.setHorizontalAlignment(JLabel.CENTER);
                    b.add(label);
                    if(tempp.getColore()==1){
                        cancella1=new JButton("Rimuovi giocatore");
                        b.add(cancella1);
                        cancella1.addActionListener(this);
                    }
                    else if(tempp.getColore()==2){
                        cancella2=new JButton("Rimuovi giocatore");
                        b.add(cancella2);
                        cancella2.addActionListener(this);
                    }
                    else if(tempp.getColore()==3){
                        cancella3=new JButton("Rimuovi giocatore");
                        b.add(cancella3);
                        cancella3.addActionListener(this);
                    }
                    else{
                        cancella4=new JButton("Rimuovi giocatore");
                        b.add(cancella4);
                        cancella4.addActionListener(this);
                    }
                    temp.add(tempp);
                    add(b);
                }
                else{
                    giocatori=temp;
                    flag=false;
                }
            }
            nome.addActionListener(this);
            ok.addActionListener(this);
            setResizable(false);
            setLocationByPlatform(true);
            setSize(500,250);
        }
        public void actionPerformed( ActionEvent e ){
            if( e.getSource()==ok){
                if(nome.getText().isEmpty()||nome.getText().length()>10){
                    JOptionPane.showMessageDialog(null, "Nome non valido.");
                }
                else{
                    bnome=nome.getText();
                    Queue<Pedina> temp=new ArrayDeque<>();
                    boolean flag=true;
                    boolean flag1=true;
                    int lung=1;
                    Pedina tempp;
                    while (flag){
                        tempp=giocatori.poll();
                        if(tempp==null){
                            giocatori=temp;
                            flag=false;
                        }
                        else{
                            temp.add(tempp);
                            lung+=1;
                            if(tempp.getNome().equals(bnome)){
                                JOptionPane.showMessageDialog(null, "Nome esistente.");
                                flag1=false;
                            }
                        }
                    }
                    if(lung==5){
                        flag1=false;
                        JOptionPane.showMessageDialog(null, "Limite massimo giocatore raggiunti.");
                    }
                    if(flag1){
                        giocatori.add(new Pedina(bnome,lung));
                        JOptionPane.showMessageDialog(null, "Giocatore aggiunto");
                    }
                }
                this.dispose();
                fM=new FrameM();
                fM.setVisible(true);

            }
            else if (e.getSource()==cancella1) {
                Queue<Pedina> temp=new ArrayDeque<>();
                boolean flag=true;
                int lung=1;
                boolean rimosso=false;
                Pedina tempp;
                while (flag){
                    tempp=giocatori.poll();
                    if(tempp==null){
                        giocatori=temp;
                        flag=false;
                        JOptionPane.showMessageDialog(null, "Giocatore rimosso");
                    }
                    else{
                        if (lung!=1){
                            if(rimosso){
                                tempp.setColore(lung-1);
                            }
                            temp.add(tempp);
                        }
                        else{
                            rimosso=true;
                        }
                        lung+=1;
                    }
                }
                this.dispose();
                fM=new FrameM();
                fM.setVisible(true);
            }
            else if (e.getSource()==cancella2) {
                Queue<Pedina> temp=new ArrayDeque<>();
                boolean flag=true;
                int lung=1;
                boolean rimosso=false;
                Pedina tempp;
                while (flag){
                    tempp=giocatori.poll();
                    if(tempp==null){
                        giocatori=temp;
                        flag=false;
                        JOptionPane.showMessageDialog(null, "Giocatore rimosso");
                    }
                    else{
                        if (lung!=2){
                            if(rimosso){
                                tempp.setColore(lung-1);
                            }
                            temp.add(tempp);
                        }
                        else{
                            rimosso=true;
                        }
                        lung+=1;
                    }
                }
                this.dispose();
                fM=new FrameM();
                fM.setVisible(true);
            }
            else if (e.getSource()==cancella3) {
                Queue<Pedina> temp=new ArrayDeque<>();
                boolean flag=true;
                int lung=1;
                boolean rimosso=false;
                Pedina tempp;
                while (flag){
                    tempp=giocatori.poll();
                    if(tempp==null){
                        giocatori=temp;
                        flag=false;
                        JOptionPane.showMessageDialog(null, "Giocatore rimosso");
                    }
                    else{
                        if (lung!=3){
                            if(rimosso){
                                tempp.setColore(lung-1);
                            }
                            temp.add(tempp);
                        }
                        else{
                            rimosso=true;
                        }
                        lung+=1;
                    }
                }
                this.dispose();
                fM=new FrameM();
                fM.setVisible(true);
            }
            else if (e.getSource()==cancella4) {
                Queue<Pedina> temp=new ArrayDeque<>();
                boolean flag=true;
                int lung=1;
                boolean rimosso=false;
                Pedina tempp;
                while (flag){
                    tempp=giocatori.poll();
                    if(tempp==null){
                        giocatori=temp;
                        flag=false;
                        JOptionPane.showMessageDialog(null, "Giocatore rimosso");
                    }
                    else{
                        if (lung!=4){
                            if(rimosso){
                                tempp.setColore(lung-1);
                            }
                            temp.add(tempp);
                        }
                        else{
                            rimosso=true;
                        }
                        lung+=1;
                    }
                }
                this.dispose();
                fM=new FrameM();
                fM.setVisible(true);
            }
        }

    }
    public class FrameGioco extends JFrame implements ActionListener{
        private JPanel giocatoriP, tasti, legenda, evento, scacchieraP;
        private JButton lancio;
        public JTextArea textArea;
        private JScrollPane scrollPane;
        public JTextPane giocatoriL[];
        public FrameGioco() {
            setTitle(title);
            setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
            addWindowListener(
                    new WindowAdapter() {
                        public void windowClosing(WindowEvent e){
                            if( cExit() ) System.exit(0);
                        }
                    }
            );
            setLayout(new BorderLayout());
            giocatoriL=new JTextPane[4];

            scacchieraP=new JPanel();
            scacchieraP.setBorder(new LineBorder(Color.BLACK));
            scacchieraP.setLayout(new GridLayout(brighe,bcolonne));
            scacchieraP.setAlignmentX(JPanel.CENTER_ALIGNMENT);
            if(scacchiera==null){
                scacchiera=new CasellaGUI[brighe][bcolonne];
                for(int i=0;i<brighe;i++){
                    for(int j=0;j<bcolonne;j++){
                        scacchiera[i][j]=new CasellaGUI(new Coordinate(j,i), gioco.getScacchiera());
                        scacchieraP.add(scacchiera[i][j].getPanel());
                    }
                }
            }
            for(int i=0;i<brighe;i++){
                for(int j=0;j<bcolonne;j++){
                    scacchieraP.add(scacchiera[i][j].getPanel());
                }
            }
            add(scacchieraP,BorderLayout.CENTER);

            giocatoriP=new JPanel();
            Queue<Pedina> temp=new ArrayDeque<>();
            boolean flag=true;
            Pedina tempp;
            int lung=1;
            while (flag){
                tempp=giocatori.poll();
                if(tempp==null){
                    giocatori=temp;
                    gioco.giocatori=temp;
                    flag=false;
                }
                else{
                    giocatoriL[lung-1]=new JTextPane();
                    giocatoriL[lung-1].setText(tempp.getNome()+", Sosta: "+tempp.getSosta()+" turni, Carta divieto di sosta: "+!tempp.isAnnulla());
                    giocatoriL[lung-1].setEditable(false);
                    giocatoriL[lung-1].setAlignmentX(JTextArea.CENTER_ALIGNMENT);
                    if(lung==1){
                        giocatoriL[lung-1].setBackground(Color.RED);
                    }
                    else if(lung==2){
                        giocatoriL[lung-1].setBackground(Color.GREEN);
                    }
                    else if(lung==3){
                        giocatoriL[lung-1].setBackground(Color.BLUE);
                    }
                    else if(lung==4){
                        giocatoriL[lung-1].setBackground(Color.YELLOW);
                    }
                    SimpleAttributeSet attribs = new SimpleAttributeSet();
                    StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
                    giocatoriL[lung-1].setParagraphAttributes(attribs, true);
                    giocatoriP.add(giocatoriL[lung-1],BorderLayout.CENTER);
                    scacchiera[0][0].set(lung);
                    lung+=1;
                    temp.add(tempp);
                }
            }
            giocatoriP.setLayout(new GridLayout(1,lung));
            giocatoriP.setAlignmentX(JPanel.CENTER_ALIGNMENT);
            add(giocatoriP,BorderLayout.NORTH);

            if(bsimulata){
                tasti = new JPanel();
                tasti.setLayout(new GridLayout(1,3));
                tasti.add(new JLabel(""));
                lancio = new JButton("Avvia simulazione");
                lancio.addActionListener(this);
                tasti.add(lancio);
                tasti.add(new JLabel(""));
                add(tasti,BorderLayout.SOUTH);
            }
            else {
                tasti = new JPanel();
                tasti.setLayout(new GridLayout(1,3));
                tasti.add(new JLabel(""));
                lancio = new JButton("Avanza turno");
                lancio.addActionListener(this);
                tasti.add(lancio);
                tasti.add(new JLabel(""));
                add(tasti,BorderLayout.SOUTH);
            }


            legenda=new JPanel();
            legenda.setLayout(new GridLayout(9,1));
            legenda.add(new JLabel(" TSE=testa serpente n "));
            legenda.add(new JLabel(" CSE=coda serpente n "));
            legenda.add(new JLabel(" ISC=inizio scala n "));
            legenda.add(new JLabel(" FSC=fine scala n "));
            legenda.add(new JLabel(" PANCH=panchina, sosta per 1 turno "));
            legenda.add(new JLabel(" LOCAN=locanda, sosta per 3 turni "));
            legenda.add(new JLabel(" MOLLA=avanza senza ritirare i dadi "));
            legenda.add(new JLabel(" PESCA=pesca dal mazzo "));
            legenda.add(new JLabel(" DADI=ritira i dadi "));
            add(legenda,BorderLayout.EAST);

            evento=new JPanel();
            textArea = new JTextArea();
            textArea.setPreferredSize(new Dimension(150,300));
            textArea.setSize(new Dimension(150,300));
            textArea.setText("");
            textArea.setEditable(false);
            scrollPane = new JScrollPane(textArea);
            evento.add(scrollPane);
            add(evento,BorderLayout.WEST);

            pack();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setResizable(true);
            setLocationByPlatform(true);
            setDefaultCloseOperation((JFrame.DO_NOTHING_ON_CLOSE));

        }
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==lancio) {
                gioco.tira();
            }
        }

    }

    private void exitProg (){
        if( cExit() ) System.exit(0);
    }
    private void openFile () {
        directory=new JFileChooser();
        directory.setFileFilter(filter);
        boolean con=true;
        if( directory.showSaveDialog(null)==JFileChooser.APPROVE_OPTION ){
            if(directory.getSelectedFile().exists()) {
                file = directory.getSelectedFile();
                con=inOpen();
            }
            else {
                con=false;
            }
        }
        if (con) {
            JOptionPane.showMessageDialog(null, "Regole caricate");
        }else{
            JOptionPane.showMessageDialog(null, "Errore durante l'apertura del file");
        }
    }
    private boolean inOpen() {
        boolean c=true;
        try {
            if (!(file.exists())) {
                file.createNewFile();
            }
            BufferedReader rea = new BufferedReader(new FileReader(file));
            String val=rea.readLine();
            //lettura dati
            StringTokenizer str=new StringTokenizer(val," ",false);
            brighe=Integer.parseInt(str.nextToken());
            bcolonne=Integer.parseInt(str.nextToken());
            if (str.nextToken().equals("true")){
                dadoSingolo=true;
            }
            else{
                dadoSingolo=false;
            }
            if (str.nextToken().equals("true")){
                blancioSingolo=true;
            }
            else{
                blancioSingolo=false;
            }
            if (str.nextToken().equals("true")){
                bdoppioSei=true;
            }
            else{
                bdoppioSei=false;
            }
            if (str.nextToken().equals("true")){
                bulterioriCarte=true;
            }
            else{
                bulterioriCarte=false;
            }
            if (str.nextToken().equals("true")){
                bsosta=true;
            }
            else{
                bsosta=false;
            }
            if (str.nextToken().equals("true")){
                bpesca=true;
            }
            else{
                bpesca=false;
            }
            if (str.nextToken().equals("true")){
                bpremio=true;
            }
            else{
                bpremio=false;
            }
            if (str.nextToken().equals("true")){
                bsimulata=true;
            }
            else{
                bsimulata=false;
            }
            rea.close();
        } catch(Exception ex) {
            ex.printStackTrace();
            c=false;
        }
        return c;
    }
    private void saveFile () {
        boolean con=true;
        if(file.exists()) {
            int c = JOptionPane.showConfirmDialog(null, "File gia esistene, sovrascriverlo?", "Attenzione", JOptionPane.YES_NO_OPTION);
            if(c==JOptionPane.NO_OPTION) {
                con=false;
            }
        }
        else {
            try {
                if(directory==null){
                    saveAsFile();
                    return;
                }
                File f=directory.getSelectedFile();
                String ext = f.getAbsolutePath();
                if(!(ext.endsWith(".txt"))) {
                    file= new File(file.getAbsolutePath()+".txt");
                }
                else {
                    file.createNewFile();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        if (!con) {
            JOptionPane.showMessageDialog(null, "Salvataggio annullato");
        }
        else {
            try {
                PrintWriter scr=null;
                String ext = file.getAbsolutePath();
                if(!(ext.endsWith(".txt"))) {
                    scr = new PrintWriter(new FileWriter(file.getAbsolutePath()+".txt"));
                }
                else {
                    scr = new PrintWriter(new FileWriter(file.getAbsolutePath()));
                }
                scr.print(brighe+" ");
                scr.print(bcolonne+" ");
                scr.print(dadoSingolo+" ");
                scr.print(blancioSingolo+" ");
                scr.print(bdoppioSei+" ");
                scr.print(bulterioriCarte+" ");
                scr.print(bsosta+" ");
                scr.print(bpesca+" ");
                scr.print(bpremio+" ");
                scr.print(bsimulata+" ");
                scr.close();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "File corrotto");
                ex.printStackTrace();
                con=false;
            }
            if (con) {
                dir=file.getName();
                JOptionPane.showMessageDialog(null, "Regole salvate correttamente");
            }
        }
    }
    private void saveAsFile ( ){
        directory=new JFileChooser();
        directory.setFileFilter(filter);
        if( directory.showSaveDialog(null)==JFileChooser.APPROVE_OPTION ){
            file=directory.getSelectedFile();
            saveFile();
        }
        else{
            directory=null;
        }
    }
    private static void info() {
        JOptionPane.showMessageDialog(null, "Scale e serprenti \n\n"
                + "Per caricare o salvare le impostazioni andare nel menu' file.\n"
                + "La scacchiera deve avere una dimensione minima di 7x7. Nella legenda sono indicate delle regole"
                + "attivabili solo in concomitanza con altre. \n\n\n"
                + "Alessandro Cordopatri, 223557 - Ingegneria Informatica");
    }
    private boolean cExit(){
        int exit=JOptionPane.showConfirmDialog(null, "Uscire?", "Attenzione",JOptionPane.YES_NO_OPTION);
        return exit==JOptionPane.YES_OPTION;
    }
}

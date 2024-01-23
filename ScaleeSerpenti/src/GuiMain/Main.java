package GuiMain;

public final class Main {
    private static FrameGUI gioco;
    public static synchronized FrameGUI getIstance(){ //pattern singleton
        if (gioco==null){
            gioco=new FrameGUI();
        }
        return gioco;
    }
    public static void main( String []args ){
        getIstance();
    }

}



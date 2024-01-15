package GuiMain;

public class Main {
    private static FrameGUI gioco;
    public static FrameGUI getIstance(){
        if (gioco==null){
            gioco=new FrameGUI();
        }
        return gioco;
    }
    public static void main( String []args ){
        getIstance();
    }

}



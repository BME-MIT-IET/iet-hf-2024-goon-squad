

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 * Ez az osztály felelős egy cső vizuális megjelenítéséért
 * és programbeli komponens állapotának összekötéséért.
 */

public class GraphicsPipe extends Graphics   {

    public int getPosX(){
        return relative.getPosX();
    }

    public int getPosY(){
        return relative.getPosY();
    }

    /**
     * Tárolja azt a csövet aminek az állapotát meg kell jelenítenie.
     */
    private Pipe relative;

    public SComponent getRelative(){
        return relative;
    }

    public GraphicsPipe(Pipe p) {
        relative = p;
        this.id = relative.getId();
        picLabel = new JLabel();
        icon = new ImageIcon(filePath+"pipe_no_wk_nw.png");
        IMG = icon.getImage();
        picLabel = new JLabel(new ImageIcon(IMG));
        picLabel.setOpaque(false);
        picLabel.setBounds(relative.posX, relative.posY, 80, 80);
    }
    public JLabel getIdLabel(){
        JLabel picLabel2 = new JLabel(id);
        picLabel2.setOpaque(false);
        picLabel2.setBounds(relative.posX+40, relative.posY+20, 80, 80);
        return  picLabel2;
    }


    /*
     * Segédfüggvények az update függvény komplexitáscsökkentéséhez 
    */

    private String calculateStateBroken(){
        if(relative.broken)
            return "_br";
        return "_wk";
    }
    private String calculateStateWater(){
        if(relative.hasWater)
            return "_hw";
        return "_nw";
    }
    private String calculateStateSurfice(){
        switch (relative.getState()) {
            case NORMAL:
                return "_no";
            case SLIPPERY:
                return "_sl";
            case STICKY:
                return "_st";
            default:
                return "_no";
        }
    }
    /**
     * Ezzel a függvénnyel frissítjük az eltárolt cső kinézetét.
     * Lekéri az adott komponens adott állapotát és a szerint választja ki a képernyőn lévő képet.
     */
    public void update(){
        picLabel.setBounds(relative.posX, relative.posY, 80, 80);
        String stateString = "pipe";
        stateString += calculateStateSurfice();
        stateString += calculateStateBroken();
        stateString += calculateStateWater();
        ChangeAppearance(stateString);
    }
    
    void refresh (){picLabel = new JLabel(new ImageIcon(IMG));}
    
}
    
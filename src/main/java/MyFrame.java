import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MyFrame extends JFrame {

    private int pipeOffset;
    private int activeXOffset;
    private int activeYOffset;




    private HashMap<String,Passive> pipes = new HashMap<>();

    public void setPumps(HashMap<String,Passive> p)
    {
        pipes = p;
    }

    private void setOffsets(int i, Passive pipe){
        pipeOffset = i==0?20:-25;
        int side = pipe.getNeighbours()[i].getNeighbourIndex(pipe);
        activeXOffset=0;
        activeYOffset=0;
        if(side==0||side==2)
        {
            activeYOffset=(side==0?30:110);
            activeXOffset = 50;
        }
        else
        {
            activeXOffset=(side==1?90:10);
            activeYOffset=70;
        }
    }

    private void drawLines(java.awt.Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        for (Passive pipe:pipes.values()) {
            int neighbourCount = pipe.getNeighbours().length;
            for(int i = 0;i<neighbourCount;i++)
            {
                if(pipe.getNeighbours()[i]!=null)
                {
                    setOffsets(i, pipe);
                    g2d.drawLine(pipe.getPosX()+50+pipeOffset, pipe.getPosY()+70, pipe.getNeighbours()[i].getPosX()+activeXOffset, pipe.getNeighbours()[i].getPosY()+activeYOffset);
                }
           }
        }

    }

    public void paint(java.awt.Graphics g) {
        super.paint(g);
        drawLines(g);
    }
}

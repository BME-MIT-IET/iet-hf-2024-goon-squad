import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Image;


public class Renderer {
    static MyFrame frame = new MyFrame();
    static public Scoreboard sb = new Scoreboard();
    static public GraphicsMap gmap = new GraphicsMap();

    public static void MechanicKeyBinds(){
        JLabel label2 = new JLabel("<html><font color='white'>UP:..........................w<br>" +
                "RIGHT:....................d<br>" +
                "DOWN:....................s<br>" +
                "LEFT:......................a<br>" +
                "ESCAPE:................e<br>" +
                "PASS:.....................q<br>" +
                "SET PUMP:.............v<br>"+
                "PUNCTURE:............p<br>"+
                "FIX:..........................f<br>"+
                "STICKY:..................t<br>"+
                "COLLECT PUMP:...c<br>"+
                "PLACE PUMP:........g<br>"+
                "RELOCATE PIPE:...r<br>"+
                "DETACH PIPE:.......h</font></html>");
        label2.setFont(new Font("Arial",1,18));
        label2.setBounds(frame.getWidth()-215,210,198, frame.getHeight()-210);
        label2.setOpaque(true);
        label2.setBackground(Color.BLACK);
        frame.add(label2);
    }
    public static void SaboteurKeyBinds(){
        JLabel label = new JLabel("<html><font color='white'>UP:..........................w<br>" +
                "RIGHT:....................d<br>" +
                "DOWN:....................s<br>" +
                "LEFT:.......................a<br>" +
                "ESCAPE:.................e<br>" +
                "PASS:......................q<br>" +
                "SET PUMP:..............v<br>"+
                "PUNCTURE:............p<br>"+
                "SLIPPERY:...............l<br>"+
                "STICKY:...................t</font></html>");
        label.setFont(new Font("Arial",1,18));
        label.setBounds(frame.getWidth()-215,210,198, frame.getHeight()-210);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        frame.add(label);
    }
    
    public static void drawMainMenu(ActionListener al) {
        JLabel picLabel;
        Image IMG;
        ImageIcon icon ;
        String filePath = "RESOURCES/IMAGES/Texture2D/";
        icon = new ImageIcon(filePath+"BG_desert.png");
        IMG = icon.getImage();
        picLabel = new JLabel(new ImageIcon(IMG));
        picLabel.setOpaque(false);
        picLabel.setBounds(0, 0, 1280, 720);
        JButton newGameButton=new JButton("Start");
        newGameButton.setBounds(1280/2-100,200,200, 70);
        newGameButton.addActionListener(al);

        frame.getContentPane().add(newGameButton);
        JButton exitGameButton=new JButton("Exit");
        exitGameButton.setBounds(1280/2-100,400,200, 70);
        exitGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        } );
        frame.getContentPane().add(exitGameButton);
        frame.getContentPane().add(picLabel);
        frame.setLayout(null);
        frame.setVisible( true );
        frame.repaint();
    }
    public static void endScreen(HashMap<String,Passive> passiveComponents, GameKeyListener gameKeyListener)
    {
        passiveComponents.clear();
        frame.removeKeyListener(gameKeyListener);
        frame.getContentPane().removeAll();
        frame.getContentPane().setBackground(Color.BLACK);
        JLabel winTeam;
        if(sb.getMechPoints()>sb.getSabPoints())
        {
            winTeam = new JLabel("Mechanics won");
            winTeam.setBounds(1280/2-150,100,500,200);
        }
        else if (sb.getMechPoints()<sb.getSabPoints())
        {
            winTeam = new JLabel("Saboteurs won");
            winTeam.setBounds(1280/2-150,100,500,200);
        }
        else
        {
            winTeam = new JLabel("Draw");
            winTeam.setBounds(1280/2-60,100,500,200);
        }
        winTeam.setForeground(Color.WHITE);
        winTeam.setFont(new Font("Comic Sans MS",Font.BOLD,40));

        JLabel mechScore = new JLabel("Mechanics: "+sb.getMechPoints());
        mechScore.setForeground(Color.WHITE);
        mechScore.setFont(new Font("Comic Sans MS",Font.BOLD,40));
        mechScore.setBounds(300,300,500,200);
        JLabel sabScore = new JLabel("Saboteurs: "+sb.getSabPoints());
        sabScore.setForeground(Color.WHITE);
        sabScore.setFont(new Font("Comic Sans MS",Font.BOLD,40));
        sabScore.setBounds(700,300,500,200);
        frame.getContentPane().add(winTeam);
        frame.getContentPane().add(mechScore);
        frame.getContentPane().add(sabScore);
        JButton exitGameButton=new JButton("Exit");
        exitGameButton.setBounds(1280/2-100,600,200, 70);
        exitGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        } );
        frame.add(exitGameButton);
        frame.setLayout(null);
        frame.setVisible( true );
        frame.repaint();

        
    }

    public static void drawScene(ArrayList<Mechanic> mechanics, Player activePlayer, GraphicsMap gmap, int roundNums)
    {
        frame.getContentPane().removeAll();
        if (mechanics.contains(activePlayer))
            MechanicKeyBinds();
        else
            SaboteurKeyBinds();
            for (int i =gmap.getComponents().size()-1; i>-1;i--){
                frame.add(gmap.getComponents().get(i).getLabel());
                frame.add(gmap.getComponents().get(i).getIdLabel());
            }


        JLabel scoreLabel = new JLabel("<html><font color='white'>SAB POINTS: "+sb.getSabPoints()+"<br>MEC POINTS: "+sb.getMechPoints()+"<br><br>ROUND: "+(30-roundNums)+"/30<br><br>AP: "+activePlayer.getAP()+"<br>"+activePlayer.getId()+"</font></html>");
        scoreLabel.setFont(new Font("Arial",1,20));
        scoreLabel.setBounds(frame.getWidth()-215,0,198, 220);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(Color.GRAY);
        frame.add(scoreLabel);
        SwingUtilities.updateComponentTreeUI(frame);

        JLabel picLabel;
        Image IMG;
        ImageIcon icon ;
        String filePath = "RESOURCES/IMAGES/Texture2D/";
        icon = new ImageIcon(filePath+"BG_desert.png");
        IMG = icon.getImage();
        picLabel = new JLabel(new ImageIcon(IMG));
        picLabel.setOpaque(false);
        picLabel.setBounds(0, 0, 1280, 720);
        frame.getContentPane().add(picLabel);
        frame.setLayout(null);
        frame.setVisible( true );
        frame.repaint();
    }
}
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;

public class Main {

    static public Map map = new Map();
    static public GraphicsMap gmap = new GraphicsMap();
    static public GameKeyListener gameKeyListener;
    static ArrayList<Notifiable> notifiableList = new ArrayList<Notifiable>();
    static HashMap<String,Passive> passiveComponents = new HashMap<String,Passive>();
    static HashMap<String,Active> activeComponents = new HashMap<String,Active>();
    static ArrayList<Mechanic> mechanics = new ArrayList<Mechanic>();
    static ArrayList<Saboteur> saboteurs = new ArrayList<Saboteur>();
    static int roundNums = 30;
    static int activePlayerIndex = 0;
    static Player activePlayer;
    static Renderer renderer = new Renderer();
    public static void main(String[] args) {
        gameKeyListener= new GameKeyListener(gmap);
        renderer.frame.setSize(1280,720);
        renderer.frame.setLayout(null);
        renderer.frame.setVisible(true);
        renderer.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        renderer.frame.setResizable(false);
        setupMainMenu();
    }

    private static void setupMainMenu()
    {
        ActionListener newGameListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    setupGame();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                gameLoop();
            }
        };

        renderer.drawMainMenu(newGameListener);
    }

    private static void setupGame() throws FileNotFoundException {
        File loadFile = new File("src/main/resources/map.txt");
        Scanner fr = new Scanner(loadFile);
        String line;
        while(fr.hasNextLine())
        {
            line = fr.nextLine();
            if(!line.equals("") && line.charAt(0) != '>')
            {
                handleInput(line);
            }
        }
        fr.close();
        renderer.frame.setPumps(passiveComponents);
        activePlayer= mechanics.get(0);
        gameKeyListener.setActivePlayer(activePlayer);
        renderer.frame.addKeyListener(gameKeyListener);
        renderer.frame.requestFocus();
        renderer.drawScene(mechanics, activePlayer, gmap, roundNums);
    }

    public static void gameLoop()
    {
            if(roundNums>0)
            {
                if (activePlayer.getAP() <= 0) {
                    activePlayerIndex += 1;
                if (activePlayerIndex == 4) {
                    roundNums--;
                    activePlayerIndex %= 4;
                }
                if (activePlayerIndex==0||activePlayerIndex==2) {
                    activePlayer = mechanics.get(activePlayerIndex/2);
                    renderer.MechanicKeyBinds();
                } else {
                    activePlayer = saboteurs.get((activePlayerIndex/2));
                    renderer.SaboteurKeyBinds();
                }
                activePlayer.resetAP();
                gameKeyListener.setActivePlayer(activePlayer);
                for(SComponent c : map.getComponents())
                {
                    c.MoveWater(null);	
                }
                map.resetMoveWater();
                renderer.sb.TallyPoints(map);
                int mapSize=map.getComponents().size();
                for (Notifiable n:notifiableList) 
                {
                    n.Notify();
                }
                if(mapSize<map.getComponents().size()){
                    for (int i=mapSize;i<map.getComponents().size();i++){
                        Pipe pii =(Pipe)Main.map.getComponents().get(i);
                        Main.notifiableList.add(pii);
                        Main.gmap.addItem(new GraphicsPipe(pii));
                        Main.passiveComponents.put(pii.getId(),pii);
                    }
                }
                renderer.drawScene(mechanics, activePlayer, gmap, roundNums);
                new JOptionPane().showMessageDialog(null, "Uj aktiv jatekos: " + activePlayer.getId(), "JATEKOS VALTAS!", JOptionPane.WARNING_MESSAGE);
                }
            renderer.drawScene(mechanics, activePlayer, gmap, roundNums);
            if(roundNums <= 0)
            {
                renderer.endScreen(passiveComponents, gameKeyListener);
            }
        }
    }

    private static void handleInput(String line)
    {
        String command = line.split(":")[0];
        switch (command) {
            case "CreateComponent":
                CreateComponent(line);
                break;
            case "CreatePlayer":
                CreatePlayer(line);
                break;
            case "SetPump":
                SetPump(line);
                break;
            case "ConnectComponents":
                ConnectComponents(line);
                break;
        }
    }

    private static void CreateComponent(String cmd){
        String[] args = cmd.split(":")[1].split(",");
        int xpos;
        int ypos;
        xpos = Integer.parseInt(args[2]);
        ypos = Integer.parseInt(args[3]);
        switch (args[1].toUpperCase()) {
            case "PUMP":
                Pump pu = new Pump(args[0],xpos,ypos);
                GraphicsPump gpu = new GraphicsPump(pu);
                gmap.addItem(gpu);
                activeComponents.put(args[0],pu);
                Main.notifiableList.add(pu);
                Main.map.addComponents(pu);
                pu.setMap(Main.map);
                break;
            case "PIPE":
                Pipe pi = new Pipe(args[0],xpos,ypos);
                GraphicsPipe gpi = new GraphicsPipe(pi);
                passiveComponents.put(args[0],pi);
                gmap.addItem(gpi);
                Main.notifiableList.add(pi);
                Main.map.addComponents(pi);
                pi.setMap(Main.map);
                break;
            case "CISTERN":
                Cistern c = new Cistern(args[0],xpos,ypos);
                GraphicsCistern gc = new GraphicsCistern(c);
                activeComponents.put(args[0],c);
                gmap.addItem(gc);
                Main.notifiableList.add(c);
                Main.map.addComponents(c);
                c.setMap(Main.map);
                break;
            case "SPRING":
                Spring s = new Spring(args[0],xpos,ypos);
                GraphicsSpring gs = new GraphicsSpring(s);
                activeComponents.put(args[0],s);
                gmap.addItem(gs);
                Main.map.addComponents(s);
                s.setMap(Main.map);
                break;
        }
    }
    private static void CreatePlayer(String cmd)
    {
        String[] args = cmd.split(":")[1].split(",");
        SComponent pos;
        if(activeComponents.containsKey(args[2]))
            pos = activeComponents.get(args[2]);
        else
            pos = passiveComponents.get(args[2]);
        if(args[1].equals("Mechanic"))
        {
            Mechanic m = new Mechanic(pos, 10, args[0]);
            String fileName = "player_eng_";
            if(m.getId().contains("1"))
            {
            	fileName += 0;
            }
            else if(m.getId().contains("2"))
            {
            	fileName += 3;
            }
            GraphicsPlayer gm = new GraphicsPlayer(fileName,m);
            gmap.addItem(gm);
            mechanics.add(m);
        }
        else {
            Saboteur s = new Saboteur(pos, 10, args[0]);
            String fileName = "player_sab_";
            if(s.getId().contains("1"))
            {
            	fileName += 0;
            }
            else if(s.getId().contains("2"))
            {
            	fileName += 3;
            }
            GraphicsPlayer gm = new GraphicsPlayer(fileName,s);
            gmap.addItem(gm);
            saboteurs.add(s);
        }

    }
    private static void ConnectComponents(String cmd){
        String[] args = cmd.split(":")[1].split(",");
        int endp1 = Integer.parseInt(args[1]);
        int endp2 = Integer.parseInt(args[3]);
        if(activeComponents.containsKey(args[0]))
        {
            activeComponents.get(args[0]).setNeighbours(endp1-1,passiveComponents.get(args[2]));
            passiveComponents.get(args[2]).setNeighbours(endp2-1,activeComponents.get(args[0]));
        }
        else
        {
            activeComponents.get(args[2]).setNeighbours(endp2-1,passiveComponents.get(args[0]));
            passiveComponents.get(args[0]).setNeighbours(endp1-1,activeComponents.get(args[2]));
        }

    }
    private static void SetPump(String cmd)
    {
        String[] args = cmd.split(":")[1].split(",");
        Active c = activeComponents.get(args[0]);
        int newIn = Integer.parseInt(args[1]);
        int newOut = Integer.parseInt(args[2]);
        c.SetPump(newIn-1, newOut-1);
    }
}
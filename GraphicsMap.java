
//Grafikus map

import java.util.ArrayList;
import java.util.List;

public class GraphicsMap {

    private ArrayList<Graphics> GrafCompList;//Palya komponenseinek listaja.

    /**
     * Default Map konstruktor
     */
    GraphicsMap(){
        GrafCompList = new ArrayList<>();
    }

    /**
     * Konstruktor, meg lehet adni SComponent listaval a terkepet
     * @param components
     */
    GraphicsMap(ArrayList<Graphics> components){
        GrafCompList = components;
    }

    /**
     * Copy-Constructor
     * @param gmap
     */
    GraphicsMap(GraphicsMap gmap){
        GrafCompList = gmap.getComponents();
    }

    /**
     * Visszaadja az eltarolt komponenseket
     * @return ArrayList<SComponent>
     */
    public  ArrayList<Graphics> getComponents(){
        return GrafCompList;
    }

    /**
     * Hozzaad egy komponenst a terkephez
     * @param c
     */
    public void addItem(Graphics c){
        GrafCompList.add(c);
    }
    public void updateAll(){
        for (Graphics g: GrafCompList) {
            g.update();
        }
    }
}
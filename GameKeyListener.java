import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GameKeyListener implements KeyListener {

	/**
	 * a pályán található grafikus elemek listája referenciaként átadva (ezen
	 * keresztül hívja meg a GraphicsMap osztály UpdateAll() függvényét egy akció
	 * lefutása esetén, illetve bizonyos esetekben frissíti is a gMap tartalmát)
	 */
	private GraphicsMap gMap;
	/**
	 * tárolja az aktív játékost (az a játékos, aki jelenleg cselekszik)
	 */
	private Player activePlayer;

	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}
	public GameKeyListener(GraphicsMap gm)
	{
		gMap = gm;
		felsoAlsoOtions.add("felso");
		felsoAlsoOtions.add("jobb");
		felsoAlsoOtions.add("also");
		felsoAlsoOtions.add("bal");
		for (SComponent g: Main.map.getComponents()) {
			String str= g.getId();
			options.add(str);
		}
	}

	private ArrayList<String> felsoAlsoOtions = new ArrayList<>();

	private ArrayList<String> options = new ArrayList<>();













	/**
	 * A KeyListener interfész egyik kötelezően
	 * implementálandó függvénye. A gombnyomások ellenőrzését mi a
	 * keyReleased()-ben ellenőrizzük, tehát ez a függvény üresen marad.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		//A gombnyomások ellenőrzését mi a keyReleased()-ben ellenőrizzük, tehát ez a függvény üresen marad.
	}
	/**
	 * A KeyListener interfész egyik kötelezően
	 * implementálandó függvénye. Egy billentyű megnyomását és felengedését
	 * követően ellenőrzi, hogy az adott billentyűhöz tartozik-e felhasználói akció. Ha
	 * igen, akkor az adott akció végrehajtását megpróbálja elindítani. Hogyha a
	 * művelet végrehajtásához szükség van további felhasználói paraméterekre,
	 * akkor megjeleníti a szükséges almenüket. A művelet végrehajtását követően
	 * meghívja a gMap UpdateAll() függvényét, hogy a grafikus elemek frissüljenek,
	 * illetve szükség esetén frissíti a gMap tartalmát.
	 */
	
	/**
	 * A KeyListener interfész egyik kötelezően
	 * implementálandó függvénye. Egy billentyű megnyomását és felengedését
	 * követően ellenőrzi, hogy az adott billentyűhöz tartozik-e felhasználói akció. Ha
	 * igen, akkor az adott akció végrehajtását megpróbálja elindítani. Hogyha a
	 * művelet végrehajtásához szükség van további felhasználói paraméterekre,
	 * akkor megjeleníti a szükséges almenüket. A művelet végrehajtását követően
	 * meghívja a gMap UpdateAll() függvényét, hogy a grafikus elemek frissüljenek,
	 * illetve szükség esetén frissíti a gMap tartalmát.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		//A gombnyomások ellenőrzését mi a keyReleased()-ben ellenőrizzük, tehát ez a függvény üresen marad.
	}
	

	private SComponent pos;


	/*
	 * Helper függvények a keyReleased függvényhez
	 * A különböző akciókat ezek végzik el
	 */

	private void moveUp(){
		if(pos.getNeighbours().length == 2)
		{
			JOptionPane.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
		}
		else if(pos.getNeighbours().length == 4)
		{
			if(pos.getNeighbours()[0] == null)
			{
				JOptionPane.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
			else if(activePlayer.getAP() < 1)
			{
				JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
			else if(pos.getState()!=null && pos.getState().equals(PipeState.STICKY) && activePlayer.getRooted())
			{
				JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem tud lepni, mivel be van ragadva!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
			else if(!activePlayer.Move(pos.getNeighbours()[0]))
			{
				JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem lephet at a(z) " + pos.getNeighbours()[0].getId() + " komponensre, mivel az mar foglalt!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private void moveRight(){
		if(pos.getNeighbours().length == 2)
		{
			if(pos.getNeighbours()[0] == null)
			{
				JOptionPane.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
			else if(activePlayer.getAP() < 1)
			{
				JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
			else if(pos.getState()!=null && pos.getState().equals(PipeState.STICKY)&& activePlayer.getRooted())
			{
				JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem tud lepni, mivel be van ragadva!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
			else if(!activePlayer.Move(pos.getNeighbours()[0]))
			{
				JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem lephet at a(z) " + pos.getNeighbours()[0].getId() + " komponensre, mivel az mar foglalt!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(pos.getNeighbours().length == 4)
		{
			if(pos.getNeighbours()[1] == null)
			{
				JOptionPane.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
			else if(activePlayer.getAP() < 1)
			{
				JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
			else if(pos.getState()!=null && pos.getState().equals(PipeState.STICKY)&& activePlayer.getRooted())
			{
				JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem tud lepni, mivel be van ragadva!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
			else if(!activePlayer.Move(pos.getNeighbours()[1]))
			{
				JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem lephet at a(z) " + pos.getNeighbours()[1].getId() + " komponensre, mivel az mar foglalt!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private void moveDown(){
		if(pos.getNeighbours().length == 2)
			{
				JOptionPane.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
			else if(pos.getNeighbours().length == 4)
			{
				if(pos.getNeighbours()[2] == null)
				{
					JOptionPane.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				else if(activePlayer.getAP() < 1)
				{
					JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				else if(pos.getState()!=null && pos.getState().equals(PipeState.STICKY)&& activePlayer.getRooted())
				{
					JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem tud lepni, mivel be van ragadva!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				else if(!activePlayer.Move(pos.getNeighbours()[2]))
				{
					JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem lephet at a(z) " + pos.getNeighbours()[2].getId() + " komponensre, mivel az mar foglalt!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
			}
	}
	private void moveLeft(){
		if(pos.getNeighbours().length == 2)
			{
				if(pos.getNeighbours()[1] == null)
				{
					JOptionPane.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				else if(activePlayer.getAP() < 1)
				{
					JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				else if(pos.getState()!=null && pos.getState().equals(PipeState.STICKY)&& activePlayer.getRooted())
				{
					JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem tud lepni, mivel be van ragadva!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				else if(!activePlayer.Move(pos.getNeighbours()[1]))
				{
					JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem lephet at a(z) " + pos.getNeighbours()[1].getId() + " komponensre, mivel az mar foglalt!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(pos.getNeighbours().length == 4)
			{
				if(pos.getNeighbours()[3] == null)
				{
					JOptionPane.showMessageDialog(null, "Ebben az iranyban nem talalhato szomszedos komponens!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				else if(activePlayer.getAP() < 1)
				{
					JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				else if(pos.getState()!=null && pos.getState().equals(PipeState.STICKY)&& activePlayer.getRooted())
				{
					JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem tud lepni, mivel be van ragadva!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
				else if(!activePlayer.Move(pos.getNeighbours()[3]))
				{
					JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem lephet at a(z) " + pos.getNeighbours()[3].getId() + " komponensre, mivel az mar foglalt!", "HIBA!", JOptionPane.ERROR_MESSAGE);
				}
			}
	}

	private void mechanicFix(){
		if(!Main.mechanics.contains((activePlayer)))
		{
			JOptionPane.showMessageDialog(null, "Ezt a muveletet csak szerelok hajthatjak vegre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int idxP = 0;
		for(int i = 0; i < Main.mechanics.size(); i++)
		{
			if(Main.mechanics.get(i).getId().equals(activePlayer.getId()))
			{
				idxP = i;
				break;
			}
		}
		Mechanic activeMechanicF = Main.mechanics.get(idxP);
		if(!pos.getBroken())
		{
			JOptionPane.showMessageDialog(null, "A komponenst nem lehet megszerelni, mivel nem torott!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(activePlayer.getAP() < 1)
		{
			JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		activeMechanicF.FixComponent();
	}

	private void puncture(){
		if(activePlayer.getAP() < 1)
		{
			JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
		}
		else if(!activePlayer.Puncture())
		{
			JOptionPane.showMessageDialog(null, "A komponenst nem lehet kiszurni (vagy mar ki van szurva)!", "HIBA!", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void mechanicCollect(){
		if(!Main.mechanics.contains((activePlayer)))
		{
			JOptionPane.showMessageDialog(null, "Ezt a muveletet csak szerelok hajthatjak vegre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int idxP = 0;
		for(int i = 0; i < Main.mechanics.size(); i++)
		{
			if(Main.mechanics.get(i).getId().equals(activePlayer.getId()))
			{
				idxP = i;
				break;
			}
		}
		Mechanic activeMechanicP = Main.mechanics.get(idxP);
		if(pos.getItem() == null)
		{
			JOptionPane.showMessageDialog(null, pos.getId() + "-nel jelenleg nincsen generalt pumpa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(activeMechanicP.getItem() != null)
		{
			JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nel mar van pumpa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(activePlayer.getAP() < 1)
		{
			JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		activeMechanicP.CollectPump();
	}

	private void mechanicPlace(){
		if(!Main.mechanics.contains((activePlayer)))
		{
			JOptionPane.showMessageDialog(null, "Ezt a muveletet csak szerelok hajthatjak vegre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int idxP = 0;
		for(int i = 0; i < Main.mechanics.size(); i++)
		{
			if(Main.mechanics.get(i).getId().equals(activePlayer.getId()))
			{
				idxP = i;
				break;
			}
		}
		Mechanic activeMechanicP2 = Main.mechanics.get(idxP);
		if(activeMechanicP2.getItem() == null)
		{
			JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nel nincs pumpa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(activePlayer.getAP() < 1)
		{
			JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!activeMechanicP2.PlacePump()){
			JOptionPane.showMessageDialog(null, "A jatekos jelenlegi poziciojara nem helyezheto pumpa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Pipe pii =(Pipe)Main.map.getComponents().get(Main.map.getComponents().size()-2);
		Main.notifiableList.add(pii);
		Main.gmap.addItem(new GraphicsPipe(pii));
		Main.passiveComponents.put(pii.getId(),pii);
		Pump pu =(Pump)Main.map.getComponents().get(Main.map.getComponents().size()-1);
		Main.notifiableList.add(pu);
		Main.gmap.addItem(new GraphicsPump(pu));
		Main.activeComponents.put(pu.getId(),pu);
	}

	private void makeSlippery(){
		if(!Main.saboteurs.contains((activePlayer)))
		{
			JOptionPane.showMessageDialog(null, "Ezt a muveletet csak szabotorok hajthatjak vegre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int idxP = 0;
		for(int i = 0; i < Main.saboteurs.size(); i++)
		{
			if(Main.saboteurs.get(i).getId().equals(activePlayer.getId()))
			{
				idxP = i;
				break;
			}
		}
		Saboteur activeSaboteurP = Main.saboteurs.get(idxP);
		if(activePlayer.getAP() < 1)
		{
			JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try{
			Pipe p = (Pipe) activePlayer.position;
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Ez a tipusu komponens nem teheto csuszossa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!activeSaboteurP.MakePipeSlippery()){
			JOptionPane.showMessageDialog(null, "A cso nem teheto csuszossa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void makeSticky(){
		if(activePlayer.getAP() < 1)
		{
			JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
		}
		else if(!activePlayer.MakePipeSticky())
		{
			JOptionPane.showMessageDialog(null, "A komponenst nem lehet ragadossa tenni (vagy mar ragad)!", "HIBA!", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void playerEscape(){
		if(activePlayer.getAP() != activePlayer.maxAP)
		{
			JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String activeElem="";
		try
		{
			activeElem = (String)JOptionPane.showInputDialog(null, "Adja meg melyik komponensre szeretne elszokni!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, options.toArray(), options.get(0));
		}
		catch(Exception ex) {}
		SComponent newPostion = null;
		for (SComponent g: Main.map.getComponents()) {
			if(g.getId().equals(activeElem))
				newPostion=g;
		}
		if(newPostion!=null && !activePlayer.Escape(newPostion))
			{
				JOptionPane.showMessageDialog(null, activePlayer.getId() + " nem menekulhet at a(z) " +newPostion.getId() + " komponensre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			}
	}


	private void playerSetPump(){
		if(activePlayer.getAP() < 1)
		{
			JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String elsoVegStr="";
		String masodikVegStr="";
		try
		{
			elsoVegStr= (String)JOptionPane.showInputDialog(null, "Adja meg a pumpa bemenetet!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, felsoAlsoOtions.toArray(), felsoAlsoOtions.get(0));
		}
		catch(Exception ex) {}
		try
		{
			masodikVegStr= (String)JOptionPane.showInputDialog(null, "Adja meg a pumpa kimenetet!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, felsoAlsoOtions.toArray(), felsoAlsoOtions.get(0));
		}
		catch(Exception ex) {}

		if(elsoVegStr.equals("") || masodikVegStr.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Nem megfeleloek a pumpa bemenete es kimenete!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int elsoVeg=-1;
		int masodikVeg=-1;

		switch (elsoVegStr)
		{
			case "felso":elsoVeg=0; break;
			case "jobb":elsoVeg=1; break;
			case "also":elsoVeg=2; break;
			case "bal":elsoVeg=3; break;
			default: break;
		}
		switch (masodikVegStr)
		{
			case "felso":masodikVeg=0; break;
			case "jobb":masodikVeg=1; break;
			case "also":masodikVeg=2; break;
			case "bal":masodikVeg=3; break;
			default: break;
		}
		if(!activePlayer.SetPump(elsoVeg,masodikVeg))
		{
			JOptionPane.showMessageDialog(null, "A komponens nem pumpa!", "HIBA!", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void playerRelocatePipe(){
		if(!Main.mechanics.contains((activePlayer)))
		{
			JOptionPane.showMessageDialog(null, "Ezt a muveletet csak szerelok hajthatjak vegre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int idxP = 0;
		for(int i = 0; i < Main.mechanics.size(); i++)
		{
			if(Main.mechanics.get(i).getId().equals(activePlayer.getId()))
			{
				idxP = i;
				break;
			}
		}
		Mechanic activeMechanicP3 = Main.mechanics.get(idxP);
		if(activePlayer.getAP() < 1)
		{
			JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String elsoVegStr="";
		String masodikVegStr="";
		try
		{
			elsoVegStr= (String)JOptionPane.showInputDialog(null, "Adja meg a melyik csovet szeretne athelyezni!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, felsoAlsoOtions.toArray(), felsoAlsoOtions.get(0));
		}
		catch(Exception ex) {}
		String activeElem="";
		try
		{
			activeElem = (String)JOptionPane.showInputDialog(null, "Adja meg a melyik aktiv elemhez akarja csatlakoztatni a csovet!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, options.toArray(), options.get(0));
		}
		catch(Exception ex) {}
		try
		{
			masodikVegStr= (String)JOptionPane.showInputDialog(null, "Adja meg a melyik vegponthoz szeretne athelyezni a csovet!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, felsoAlsoOtions.toArray(), felsoAlsoOtions.get(0));
		}
		catch(Exception ex) {}

		if(elsoVegStr.equals("") || masodikVegStr.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Nem megfeleloek a pumpa bemenete es kimenete!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int elsoVeg=-1;
		int masodikVeg=-1;

		switch (elsoVegStr)
		{
			case "felso":elsoVeg=0; break;
			case "jobb":elsoVeg=1; break;
			case "also":elsoVeg=2; break;
			case "bal":elsoVeg=3; break;
			default: break;
		}
		switch (masodikVegStr)
		{
			case "felso":masodikVeg=0; break;
			case "jobb":masodikVeg=1; break;
			case "also":masodikVeg=2; break;
			case "bal":masodikVeg=3; break;
			default: break;
		}

		Active activeComponent = Main.activeComponents.get(activeElem);
		if(!activeMechanicP3.RelocatePipe(elsoVeg,activeComponent,masodikVeg)){
			JOptionPane.showMessageDialog(null, "Nem lehet athelyezni a csovet", "HIBA!", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void playerDetachPipe(){
		if(!Main.mechanics.contains((activePlayer)))
		{
			JOptionPane.showMessageDialog(null, "Ezt a muveletet csak szerelok hajthatjak vegre!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int idxP = 0;
		for(int i = 0; i < Main.mechanics.size(); i++)
		{
			if(Main.mechanics.get(i).getId().equals(activePlayer.getId()))
			{
				idxP = i;
				break;
			}
		}
		Mechanic activeMechanicP4 = Main.mechanics.get(idxP);
		if(activePlayer.getAP() < 1)
		{
			JOptionPane.showMessageDialog(null, activePlayer.getId() + "-nek nincs eleg AP-je a muvelet elvegzesehez!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String VegStr="";
		try
		{
			VegStr= (String)JOptionPane.showInputDialog(null, "Adja meg a melyik csovet szeretne lecsatlakoztatni!", "CÍM", JOptionPane.QUESTION_MESSAGE, null, felsoAlsoOtions.toArray(), felsoAlsoOtions.get(0));
		}
		catch(Exception ex) {}
		if(VegStr.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Ervenytelen vegpont!", "HIBA!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int activeVegPont2=-1;
		switch (VegStr)
		{
			case "felso":activeVegPont2=0; break;
			case "jobb":activeVegPont2=1; break;
			case "also":activeVegPont2=2; break;
			case "bal":activeVegPont2=3; break;
			default: break;
		}
		if(!activeMechanicP4.Detach(activeVegPont2)){
			JOptionPane.showMessageDialog(null, "A komponens nem aktiv!", "HIBA!", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * A KeyListener interfész egyik kötelezően
	 * implementálandó függvénye. A gombnyomások ellenőrzését mi a
	 * keyReleased()-ben ellenőrizzük, tehát ez a függvény üresen marad.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		pos = activePlayer.getPosition();

		switch(e.getKeyChar())
		{
			
			case 'w':
				moveUp();
				break; //moveUp
			case 'd': 
				moveRight();
				break; //moveRight
			case 's':
				moveDown();
				break; //moveDown
			case 'a': 
				moveLeft();
				break; //moveLeft
			case 'f':
				mechanicFix();
				break; //fix
			case 'p': 
				puncture();
				break; //puncture
			case 'c': 
				mechanicCollect();
				break; //collect
			case 'g':
				mechanicPlace();
				break; //place
			case 'l':
				makeSlippery();
				break; //slippery
			case 't':
				makeSticky();
				break; //sticky
			case 'e':
				playerEscape();
				break; //escape
			case 'q':
				activePlayer.Pass();
				break; //pass
			case 'v':
				playerSetPump();
				break; //set pump
			case 'r':
				playerRelocatePipe();
				break; //relocate pipe
			case 'h':
				playerDetachPipe();
				break; //detach pipe
			default: return;
		}
		Main.gameLoop();
		gMap.updateAll();
	}

	
}

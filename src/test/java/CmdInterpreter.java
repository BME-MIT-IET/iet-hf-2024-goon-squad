import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class CmdInterpreter {

	/**
	 * Osszekoti a komponensek neveit tenyleges komponensekkel
	 */
	private HashMap<String, SComponent> components = new HashMap<String, SComponent>();
	/**
	 * Eltarolja osszes szerelot a jatekban.
	 */
	private HashMap<String, Mechanic> team1 = new HashMap<String, Mechanic>();
	/**
	 * Eltarolja osszes szabotort a jatekban.
	 */
	private HashMap<String, Saboteur> team2 = new HashMap<String, Saboteur>();

	/**
	 * Eltarolja az osszes kiadott parancsot
	 */
	private ArrayList<String> inputs = new ArrayList<String>();
	/**
	 * Eltarolja az osszes kiadott kimentet
	 */
	private ArrayList<String> outputs = new ArrayList<String>();

	/**
	 * Eltarolja hany akcio pont kell, hogy a jatekos cselekedni tudjon.
	 */
	private int requiredAP = 1;

	/**
	 * Eltarolja hogy egedelyezve vannak-e a random esemenyek.
	 */
	private boolean randomEnabled = false;
	
	public CmdInterpreter()
	{
		init();
	}

	public String Fixed()
	{
		return "Fixed";
	}

	/**
	 * Lekezeli a parancsok helyesseget.
	 */
	public String NewCommand(String cmd)
	{
		inputs.add(cmd);
		String cmdType;
		if(cmd.contains(":"))
		{
			cmdType = cmd.split(":")[0];
		}
		else
		{
			cmdType = cmd;
		}
		String res;
		switch(cmdType)
		{
			default: res = "[HIBA]: Nem talalhato ilyen parancs!"; outputs.add(">"+res); return res;
			case "CreateComponent": res = CreateComponent(cmd); outputs.add(">"+res); return res;
			case "CreatePlayer": res = CreatePlayer(cmd); outputs.add(">"+res); return res;
			case "ConnectComponents": res = ConnectComponents(cmd); outputs.add(">"+res); return res;
			case "SetPlayerPos": res = SetPlayerPos(cmd); outputs.add(">"+res); return res;
			case "MovePlayer": res = MovePlayer(cmd); outputs.add(">"+res); return res;
			case "PlayerSimpleAction": res = PlayerSimpleAction(cmd); outputs.add(">"+res); return res;
			case "PlayerEscape": res = PlayerEscape(cmd); outputs.add(">"+res); return res;
			case "PlayerSetPump": res = PlayerSetPump(cmd); outputs.add(">"+res); return res;
			case "PlayerRelocatePipe": res = PlayerRelocatePipe(cmd); outputs.add(">"+res); return res;
			case "PlayerDetachPipe": res = PlayerDetachPipe(cmd); outputs.add(">"+res); return res;
			case "MoveWater": res = MoveWater(cmd); outputs.add(">"+res); return res;
			case "BreakPump": res = BreakPump(cmd); outputs.add(">"+res); return res;
			case "GeneratePump": res = GeneratePump(cmd); outputs.add(">"+res); return res;
			case "GeneratePipe": res = GeneratePipe(cmd); outputs.add(">"+res); return res;
			case "SetSlipperyEndPoint": res = SetSlipperyEndPoint(cmd); outputs.add(">"+res); return res;
			case "UnstickPlayer": res = UnstickPlayer(cmd); outputs.add(">"+res); return res;
			case "ChangePipeState": res = ChangePipeState(cmd); outputs.add(">"+res); return res;
			case "ChangeBreakState": res = ChangeBreakState(cmd); outputs.add(">"+res); return res;
			case "SetPump": res = SetPump(cmd); outputs.add(">"+res); return res;
			case "SetAP": res = SetAP(cmd); outputs.add(">"+res); return res;
			case "SetItem": res = SetItem(cmd); outputs.add(">"+res); return res;
			case "ToggleRandom": res = ToggleRandom(cmd); outputs.add(">"+res); return res;
			case "EndTurn": res = EndTurn(cmd); outputs.add(">"+res); return res;
			case "CalculatePoints": res = CalculatePoints(cmd); outputs.add(">"+res); return res;
			case "NewTest": res = NewTest(cmd); outputs.add(">"+res); return res;
			case "Save": res = Save(cmd); outputs.add(">"+res); return res;
			case "Load": res = Load(cmd); outputs.add(">"+res); return res;
			case "List": res = List(cmd); outputs.add(">"+res); return res;
			case "Exit": res = Exit(cmd); outputs.add(">"+res); return res;
		}
	}

	/**
	 * A parancs egy új játékos létrehozására és egy pályán lévő
	 * komponensre való elhelyezésére szolgál. A parancs akkor fut le sikeresen,
	 * hogyha nincs név -és pozíciókonfliktus.
	 */
	private String CreatePlayer(String cmd)
	{
		String argCheck = ArgCheck(cmd, 3);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			if(team1.containsKey(args[0]) || team2.containsKey(args[0]))
			{
				return "[HIBA] Ilyen nevu jatekos mar letezik!";
			}
			if(!args[1].equals("Mechanic") && !args[1].equals("Saboteur"))
			{
				return "[HIBA] Ervenytelen jatekos tipus! (Elvart: Mechanic/Saboteur)";
			}
			if(!components.containsKey(args[2]))
			{
				return "[HIBA] A jatekos poziciojanak valasztott komponens nem letezik!";
			}
			SComponent pos = components.get(args[2]);
			if(args[1].equals("Mechanic"))
			{
				Mechanic m = new Mechanic(pos, 10, args[0]);
				if(!pos.AddPlayer(m))
				{
					return "[HIBA] A jatekos nem hozhato letre a " + args[2] + " komponensen, mivel az mar foglalt!";
				}
				team1.put(args[0], m);
			}
			else
			{
				Saboteur s = new Saboteur(pos, 10, args[0]);
				if(!pos.AddPlayer(s))
				{
					return "[HIBA] A jatekos nem hozhato letre a " + args[2] + " komponensen, mivel az mar foglalt!";
				}
				team2.put(args[0], s);
			}
			return "[SIKERES]: Sikeresen letrehozva egy jatekos\n\t>Nev: " + args[0] + "\n\t>Tipus: " + args[1] + "\n\t>Pozicio: " + args[2];
		}
	}
	/**
	 * A parancs egy játékos pozíciójának átállítására szolgál (bármelyik, a
	 * pályán lévő komponensre). A parancs akkor fut le sikeresen, hogyha nincs
	 * pozíciókonfliktus.
	 */
	private String SetPlayerPos(String cmd)
	{
		String argCheck = ArgCheck(cmd, 2);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			if(!team1.containsKey(args[0]) && !team2.containsKey(args[0]))
			{
				return "[HIBA] Ilyen nevu jatekos nem letezik!";
			}
			if(!components.containsKey(args[1]))
			{
				return "[HIBA] A jatekos uj poziciojanak valasztott komponens nem letezik!";
			}
			SComponent pos = components.get(args[1]);
			if(team1.containsKey(args[0]))
			{
				Mechanic m = team1.get(args[0]);
				if(!pos.AddPlayer(m))
				{
					return "[HIBA] A jatekos nem helyezheto at a " + args[1] + " komponensre, mivel az mar foglalt!";
				}
				m.setPosition(pos);
			}
			if(team2.containsKey(args[0]))
			{
				Saboteur s = team2.get(args[0]);
				if(!pos.AddPlayer(s))
				{
					return "[HIBA] A jatekos nem helyezheto at a " + args[1] + " komponensre, mivel az mar foglalt!";
				}
				s.setPosition(pos);
			}
			return "[SIKERES]: " + args[0] + " uj pozicio beallitva\n\t>Nev: " + args[0] + "\n\t>Uj pozicio: " + args[1];	
		}
	}
	/**
	 * A parancs egyszerű (értsd: extra paraméter nélküli), játékoshoz
	 * kapcsolódó műveletek elvégzésére szolgál. A parancs csak akkor hajtódik
	 * végre sikeresen, hogyha a parancsoknál megfogalmazódott követelmények
	 * teljesülnek, illetve ha a játékosnak van elég AP-je az egyes műveletek
	 * elvégzéséhez.
	 */
	private String PlayerSimpleAction(String cmd)
	{
		String argCheck = ArgCheck(cmd, 2);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			switch(args[1])
			{
				default: return "[HIBA] Ilyen action nem letezik!";
				case "fix":
					if(!team1.containsKey(args[0]))
					{
						return "[HIBA] Ilyen nevu szerelo nem letezik!";
					}
					Mechanic mFix = team1.get(args[0]);
					if(!mFix.getPosition().getBroken())
					{
						return "[HIBA] A komponenst nem lehet megszerelni, mivel nem torott!";
					}
					if(mFix.getAP() < requiredAP)
					{
						return "[HIBA] A jatekosnak nincs eleg AP-je a muvelet elvegzesehez!";
					}
					mFix.FixComponent();
					return "[SIKERES] A(z) " + args[0] + " jatekos megszerelte a komponenst, amin all";
				case "puncture":
					if(!team1.containsKey(args[0]) && !team2.containsKey(args[0]))
					{
						return "[HIBA] Ilyen nevu jatekos nem letezik!";
					}
					Player pPun;
					if(team1.containsKey(args[0]))
					{
						pPun = team1.get(args[0]);
					}
					else
					{
						pPun = team2.get(args[0]);
					}
					if(pPun.getAP() < requiredAP)
					{
						return "[HIBA] A jatekosnak nincs eleg AP-je a muvelet elvegzesehez!";
					}
					if(!pPun.Puncture())
					{
						return "[HIBA] A komponenst nem lehet kiszurni (vagy mar ki van szurva)!";
					}
					return "[SIKERES] A(z) " + args[0] + " jatekos kiszurta a komponenst, amin all";
				case "collect":
					if(!team1.containsKey(args[0]))
					{
						return "[HIBA] Ilyen nevu szerelo nem letezik!";
					}
					Mechanic mCol = team1.get(args[0]);
					if(mCol.getItem() != null)
					{
						return "[HIBA] A jatekosnal mar van pumpa!";
					}
					if(mCol.getPosition().getItem() == null)
					{
						return "[HIBA] A jatekos jelenlegi poziciojan nincs generalt pumpa!";
					}
					if(mCol.getAP() < requiredAP)
					{
						return "[HIBA] A jatekosnak nincs eleg AP-je a muvelet elvegzesehez!";
					}
					mCol.CollectPump();
					return "[SIKERES] A(z) " + args[0] + " jatekos osszeszedett egy pumpat";
				case "place":
					if(!team1.containsKey(args[0]))
					{
						return "[HIBA] Ilyen nevu szerelo nem letezik!";
					}
					Mechanic mPla = team1.get(args[0]);
					if(mPla.getItem() == null)
					{
						return "[HIBA] A jatekosnal nincsen pumpa!";
					}
					if(mPla.getAP() < requiredAP)
					{
						return "[HIBA] A jatekosnak nincs eleg AP-je a muvelet elvegzesehez!";
					}
					if(!mPla.PlacePump())
					{
						return "[HIBA] A jatekos jelenlegi poziciojara nem helyezheto pumpa!";
					}
					String gID = "g_pu";
					for(int i = 1; i < 10001; i++)
					{
						if(!components.containsKey(gID+i))
						{
							gID += i;
							break;
						}
					}
					if(gID.equals("g_pu"))
					{
						return "[HIBA] Tul sok pumpat helyeztek mar le a jatek soran (>10000 db)";
					}
					//components.put(gID,Main.map.getComponents().get(Main.map.getComponents().size()-1));
					return "[SIKERES] A(z) " + args[0] + " jatekos lehelyezett egy pumpat";
				case "slippery":
					if(!team2.containsKey(args[0]))
					{
						return "[HIBA] Ilyen nevu szabotor nem letezik!";
					}
					Saboteur sSli = team2.get(args[0]);
					if(sSli.getAP() < requiredAP)
					{
						return "[HIBA] A jatekosnak nincs eleg AP-je a muvelet elvegzesehez!";
					}
					if(!sSli.MakePipeSlippery())
					{
						return "[HIBA] Ez a tipusu komponens nem teheto csuszossa! (Elvart: Pipe)";
					}
					return "[SIKERES] A(z) " + args[0] + " jatekos csuszossa tette a komponenst, amin all";
				case "sticky":
					if(!team1.containsKey(args[0]) && !team2.containsKey(args[0]))
					{
						return "[HIBA] Ilyen nevu jatekos nem letezik!";
					}
					Player pSti;
					if(team1.containsKey(args[0]))
					{
						pSti = team1.get(args[0]);
					}
					else
					{
						pSti = team2.get(args[0]);
					}
					if(pSti.getAP() < requiredAP)
					{
						return "[HIBA] A jatekosnak nincs eleg AP-je a muvelet elvegzesehez!";
					}
					if(!pSti.MakePipeSticky())
					{
						return "[HIBA] A komponenst nem lehet ragadossa tenni (vagy mar ragad)!";
					}
					return "[SIKERES] A(z) " + args[0] + " jatekos ragadossa tette a komponenst, amin all";
				case "pass":
					Player pPass;
					if(team1.containsKey(args[0]))
					{
						pPass = team1.get(args[0]);
					}
					else
					{
						pPass = team2.get(args[0]);
					}
					pPass.Pass();
					return "[SIKERES] A(z) " + args[0] + " jatekos passzolt";
			}
		}
	}
	/**
	 * Lekezeli a jatekos megszokesenek parancsat.
	 */
	private String PlayerEscape(String cmd)
	{
		String argCheck = ArgCheck(cmd, 2);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			if(!team1.containsKey(args[0]) && !team2.containsKey(args[0]))
			{
				return "[HIBA] Ilyen nevu jatekos nem letezik!";
			}
			if(!components.containsKey(args[1]))
			{
				return "[HIBA] A jatekos menekulesi helyenek valasztott komponens nem letezik!";
			}
			SComponent pos = components.get(args[1]);
			if(team1.containsKey(args[0]))
			{
				Mechanic m = team1.get(args[0]);
				if(m.getRooted())
				{
					return "[HIBA] A jatekos nem menekulhet, ugyanis be van ragadva!";
				}
				if(!m.Escape(pos))
				{
					return "[HIBA] A jatekos nem helyezheto at a "+args[1]+" komponensre!";
				}
				m.setPosition(pos);
			}
			if(team1.containsKey(args[0]))
			{
				Saboteur s = team2.get(args[0]);
				if(s.getRooted())
				{
					return "[HIBA] A jatekos nem menekulhet, ugyanis be van ragadva!";
				}
				if(!s.Escape(pos))
				{
					return "[HIBA] A jatekos nem helyezheto at a " + args[1] + " komponensre, mivel az mar foglalt!";
				}
				s.setPosition(pos);
			}
			return "[SIKERES]: A(z) " + args[0] + " sikeresen megszokott\n\t>Nev: " + args[0] + "\n\t>Uj pozicio: " + args[1];	
		}
	}
	/**
	 * A parancs annak az aktív komponensnek valamelyik végpontjához
	 * kapcsolódó cső másik végpontjának átkötésére szolgál, amelyen a játékos áll.
	 * A parancs csak akkor végződik el sikeresen, hogyha a játékos egy aktív
	 * komponensen áll, a megadott új komponens is aktív, a megadott végpontok
	 * validak, illetve a játékosnak van elég AP-je a művelet elvégzéséhez (azaz
	 * nincs-e a választott komponens túl messze).
	 */
	private String PlayerRelocatePipe(String cmd)
	{
		String argCheck = ArgCheck(cmd, 4);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			if(!team1.containsKey(args[0]))
			{
				return "[HIBA] Ilyen nevu szerelo nem letezik!";
			}
			if(!args[1].equals("1") && !args[1].equals("2") && !args[1].equals("3") && !args[1].equals("4"))
			{
				return "[HIBA] Ervenytelen vegpont! (Elvart: 1-4)";
			}
			if(!components.containsKey(args[2]))
			{
				return "[HIBA] A bekotesi komponensnek valasztott komponens nem letezik!";
			}
			if(!args[3].equals("1") && !args[3].equals("2") && !args[3].equals("3") && !args[3].equals("4"))
			{
				return "[HIBA] Ervenytelen uj vegpont! (Elvart: 1-4)";
			}
			Mechanic p = team1.get(args[0]);
			if(p.getAP() < requiredAP)
			{
				return "[HIBA] A jatekosnak nincs eleg AP-je a muvelet elvegzesehez!";
			}
			Active connectTo = null;
			try
			{
				connectTo = (Active)components.get(args[2]);
			}
			catch(Exception e)
			{
				return "[HIBA] A bekotesi komponensnek valasztott komponens nem megfelelo tipusu!";
			}
			int endPoint = Integer.parseInt(args[1])-1;
			int newEndPoint = Integer.parseInt(args[3])-1;
			if(!p.RelocatePipe(endPoint, connectTo, newEndPoint))
			{
				return "[HIBA] Ez a muvelet ezekkel a parameterekkel nem hajthato vegre!";
			}
			return "[SIKERES] A(z) " + args[0] + " sikeresen átkötötte a pozíciókomponensének " + args[1] + ". kimenetét a " + args[2] + " " + args[3] + ". kimenetére";
		}
	}
	/**
	 * A parancs egy komponens általi vízmozgatás megvalósítására
	 * szolgál.
	 */
	private String MoveWater(String cmd)
	{
		String argCheck = ArgCheck(cmd, 2);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			if(!components.containsKey(args[0]))
			{
				return "[HIBA] A megadott aktiv komponens nem letezik!";
			}
			if(!components.containsKey(args[1]) && !args[1].equals("null"))
			{
				return "[HIBA] A megadott passziv komponens nem letezik!";
			}
			try
			{
				if(!args[1].equals("null"))
				{
					SComponent reciever = components.get(args[0]);
					SComponent sender = components.get(args[1]);
					if(sender.getBroken())
					{
						return "[HIBA] a(z) " + args[0] + " nem tudott vizet fogadni a(z) "  + args[1] + " komponenstol, mivel a(z) "  + args[1] + " komponens elromlott";
					}
					if(!sender.getHasWater())
					{
						return "[HIBA] a(z) " + args[0] + " nem tudott vizet fogadni a(z) "  + args[1] + " komponenstol, mivel a(z) "  + args[1] + " komponensnek nincs vize";
					}
					if(reciever.getBroken())
					{
						return "[HIBA] " + args[0] + " komponens nem tud vizet szallitani, mivel elromlott";
					}
					reciever.MoveWater(sender);
				}
				else
				{
					try
					{
						Spring sp = (Spring)components.get(args[0]);
						sp.MoveWater(null);
						return "[SIKERES] a(z) " + args[0] + " vizet mozgatott a szomszedos komponenseibe";
					}
					catch(Exception e)
					{
						return "[HIBA] Csak Spring tipusu komponens kepes magatol vizet generalni (from_component parameter kulonben nem lehet 'null')!";
					}
				}
			}
			catch(Exception e)
			{
				return "[HIBA] A vizmozgatashoz valasztott komponensek nem megfelelo tipusuak!";
			}
			return "[SIKERES] A(z) " + args[0] + " vizmozgatasi folyamata elvegzodott";
		}
	}
	/**
	 * A parancs egy választott pumpa determinisztikus tönkretételére
	 * szolgál. A parancs akkor végződik el sikeresen, hogyha a választott
	 * komponens valóban egy pumpa, amely a parancs kiadása előtt működik.
	 */
	private String BreakPump(String cmd)
	{
		String argCheck = ArgCheck(cmd, 1);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			if(!components.containsKey(args[0]))
			{
				return "[HIBA] Ilyen nevu komponens nem letezik!";
			}
			Pump c = null;
			try
			{
				 c = (Pump)components.get(args[0]);
			}
			catch(Exception e)
			{
				return "[HIBA] A(z) " + args[0] + " komponens nem egy pumpa!";
			}
			if(c.getBroken())
			{
				return "[HIBA] " + args[0] + " nem rontható el, mivel már elromlott";
			}
			c.Failure();
			return "[SIKERES] " + args[0] + " komponens elrontva";
		}
	}
	/**
	 * A parancs egy cső determinisztikus generálására szolgál egy
	 * választott ciszternán. A parancs akkor végződik el sikeresen, hogyha a
	 * választott komponens valóban egy ciszterna, amelynek a parancs kiadása
	 * előtt a választott végpontja szabad.
	 */
	private String GeneratePipe(String cmd)
	{
		String argCheck = ArgCheck(cmd, 2);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			if(!components.containsKey(args[0]))
			{
				return "[HIBA] Ilyen nevu komponens nem letezik!";
			}
			if(!args[1].equals("1") && !args[1].equals("2") && !args[1].equals("3") && !args[1].equals("4"))
			{
				return "[HIBA] Ervenytelen vegpont! (Elvart: 1-4)";
			}
			Cistern c = null;
			try
			{
				 c = (Cistern)components.get(args[0]);
			}
			catch(Exception e)
			{
				return "[HIBA] A(z) " + args[0] + " komponens nem egy ciszterna!";
			}
			int endPoint = Integer.parseInt(args[1])-1;
			if(!c.GeneratePipe(endPoint))
			{
				return "[HIBA] A(z) " + args[0] + " komponens " + args[1] + ". vegpontjan nem hozhato letre cso (a vegpont foglalt)!";
			}
			String gID = "g_pi";
			for(int i = 1; i < 10001; i++)
			{
				if(!components.containsKey(gID+i))
				{
					gID += i;
					break;
				}
			}
			if(gID.equals("g_pi"))
			{
				return "[HIBA] Tul sok cso generalodott mar a jatek soran (>10000 db)";
			}
			return "[SIKERES] Cso generalva a(z) " + args[0] + " komponens " + args[1] + ". vegpontjan (ID: " + gID + ")";  
		}
	}
	/**
	 * A parancs egy ragadós csőnél ragadt játékos azonnali kiszabadítására
	 * szolgál (cél: ne kelljen több körnek eltelnie egy játékos kiszabadításához). A
	 * parancs akkor végződik el sikeresen, hogyha a választott játékos valóban egy
	 * ragadós csövön áll és éppen be van ragadva.
	 */
	private String UnstickPlayer(String cmd)
	{
		String argCheck = ArgCheck(cmd, 1);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			if(!team1.containsKey(args[0]) && !team2.containsKey(args[0]))
			{
				return "[HIBA] Ilyen nevu jatekos nem letezik!";
			}
			Player p;
			if(team1.containsKey(args[0]))
			{
				p = team1.get(args[0]);
			}
			else
			{
				p = team2.get(args[0]);
			}
			if(!p.getRooted())
			{
				return "[HIBA] A(z) " + args[0] + " jatekos nincs beragadva!";
			}
			p.setRooted(false);
			return "[SIKERES] A(z) " + args[0] + " jatekos sikeresen kiszabaditva!";
		}
	}
	/**
	 * A parancs egy cső vagy pumpa működési állapotának
	 * megváltoztatására szolgál játékos beavatkozása nélkül (cél: törött/javított
	 * komponensek működésének egyszerű tesztelése). A parancs akkor végződik
	 * el helyesen, hogyha a megadott komponens egy cső vagy egy pumpa, illetve
	 * hogyha a megadott állapotérték helyes.
	 */
	private String ChangeBreakState(String cmd)
	{
		String argCheck = ArgCheck(cmd, 2);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			if(!components.containsKey(args[0]))
			{
				return "[HIBA] Ilyen nevu komponens nem letezik!";
			}
			SComponent c = components.get(args[0]);
			switch(args[1])
			{
				default: return "[HIBA] Ervenytelen 2. parameter! (Elvart: broken/breakable/unbreakable)";
				case "broken":
					if(!c.setBroken(true))
					{
						return "[HIBA] A(z) " + args[0] + " komponens nem ronthato el!";
					}
					break;
				case "breakable":
					if(!c.setBroken(false))
					{
						return "[HIBA] A(z) " + args[0] + " komponens nem szerelheto meg!";
					}
					break;
				case "unbreakable":
					if(!c.setBroken(false) || !c.setBreakable(true))
					{
						return "[HIBA] A(z) " + args[0] + " komponens nem allithato torhetetlenne!";
					}
			}
			return "[SIKERES] A(z) " + args[0] + " komponens toresi allapota beallitva!";
		}
	}
	/**
	 * A parancs egy pumpa be -és kimenetének átállítására szolgál játékos
	 * beavatkozása nélkül. A parancs csak akkor végződik el sikeresen, hogyha a
	 * megadott értékek helyesek.
	 */
	private String SetPump(String cmd)
	{
		String argCheck = ArgCheck(cmd, 3);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			if(!components.containsKey(args[0]))
			{
				return "[HIBA] Ilyen nevu komponens nem letezik!";
			}
			if(!args[1].equals("1") && !args[1].equals("2") && !args[1].equals("3") && !args[1].equals("4"))
			{
				return "[HIBA] Ervenytelen bemeneti vegpont! (Elvart: 1-4)";
			}
			if(!args[2].equals("1") && !args[2].equals("2") && !args[2].equals("3") && !args[2].equals("4"))
			{
				return "[HIBA] Ervenytelen kimeneti vegpont! (Elvart: 1-4)";
			}
			SComponent c = components.get(args[0]);
			int newIn = Integer.parseInt(args[1]);
			int newOut = Integer.parseInt(args[2]);
			if(!c.SetPump(newIn-1, newOut-1))
			{
				return "[HIBA] A(z) " + args[0] + " komponens nem egy pumpa!";
			}
			return "[SIKERES] A(z) " + args[0] + " bemenete és kimenete sikeresen átállítva";
		}
	}
	/**
	 * A parancs pumpát ad egy játékosnak. (cél: pumpát generáló ciszterna
	 * nélkül is tesztelhető legyen pl. a pumpa lehelyezés)
	 */
	private String SetItem(String cmd)
	{
		String argCheck = ArgCheck(cmd, 1);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			if(!team1.containsKey(args[0]) && !team2.containsKey(args[0]))
			{
				return "[HIBA] Ilyen nevu jatekos nem letezik!";
			}
			if(!team1.containsKey(args[0]))
			{
				return "[HIBA] Ilyen tipusu jatekos nem kaphat pumpat! (Elvart: Mechanic)";
			}
			Player p = team1.get(args[0]);

			p.setItem(new Pump(args[0], 0, 0));
			return "[SIKERES] " + args[0] + " jatekosnak pumpa beallitva";
		}
	}
	/**
	 * A pálya egy kör befejezésére szolgál, azaz minden kör végén
	 * bekövetkező esemény végrehajtódik (vízmozgatás, pontszámítás, AP-k
	 * visszaállítása, bizonyos körönként a beragadt játékosok kiszabadítása).
	 */
	private String EndTurn(String cmd)
	{
		for(Player p : team1.values())
		{
			p.setAP(10);
		}
		for(Player p : team2.values())
		{
			p.setAP(10);
		}
		if(randomEnabled)
		{
			for(Notifiable c : Main.notifiableList)
			{
				c.Notify();
			}
		}
		for(SComponent c : components.values())
		{
			c.MoveWater(c);
		}
		Main.sb.TallyPoints(Main.map);
		
		return "[SIKERES] A kornek vege\n>[JELENLEGI ALLAS]:\n>Szerelok: " + Main.sb.getMechPoints() + "\n>Szabotorok: " + Main.sb.getSabPoints();
	}
	/**
	 * A parancs egy teszteset befejezésére, a létrehozott/importált
	 * komponensek és játékosok törlésére, és egy teljesen új teszteset
	 * létrehozására szolgál.
	 */
	private String NewTest(String cmd)
	{
		components.clear();
		team1.clear();
		team2.clear();
		Main.map.getComponents().clear();
		Main.sb.resetpoints();
		randomEnabled = false;
		return "[SIKERES] Uj teszteset letrehozva!";
	}
	/**
	 * Parancsok szöveges fájlból történő beolvasására és feldolgozására
	 * szolgál. A parancs akkor végződik el sikeresen, hogyha a megadott file létezik
	 * és az útvonala helyes.
	 */
	private String Load(String cmd)
	{
		String argCheck = ArgCheck(cmd, 1);
		if(!argCheck.equals(""))
		{
			return argCheck;
		}
		else
		{
			String[] args = cmd.split(":")[1].split(",");
			try
			{
				File loadFile = new File("src/test/resources/" + args[0]);
				/*Scanner fr = new Scanner(loadFile);
				String line;
				while(fr.hasNextLine())
				{
					line = fr.nextLine();
					if(!line.equals("") && line.charAt(0) != '>')
					{
						NewCommand(line);
						//System.out.println(NewCommand(line));
					}
				}
				fr.close();
				*/
				return "[SIKERES] " + args[0] + " betoltve";
			}
			catch(Exception e)
			{
				return "[HIBA] Hiba a fajl megnyitasa kozben (serult vagy nem letezik)";
			}
		}
	}
	/**
	 * A tesztelés befejezésére és a programból való kilépésre szolgál.
	 */
	private String Exit(String cmd)
	{
		System.out.println("VISZLAT!");
		System.exit(0);
		return "";
	}
	/**
	 * A bemenet argumentum szam ellenorzesere szolgal
	 */
	private String ArgCheck(String cmd, int argCount)
	{
		String argsStr;
		try
		{
			argsStr = cmd.split(":")[1];
		}
		catch(Exception e)
		{
			return "[HIBA] Az argumentumok hianyoznak!";
		}
		ArrayList<String> args = new ArrayList<String>();
		String[] argsArray = argsStr.split(",");
		for(String  arg : argsArray)
		{
			if(!arg.equals(""))
			{
				args.add(arg);	
			}
		}
		if(args.size() != argCount)
		{
			return "[HIBA] Az argumentumok szama nem megfelelo!";
		}
		return "";
	}
	/**
	 * Elmenti a lehetseges komponens bemeneteket.
	 */
	public static ArrayList<String> komponensek= new ArrayList<>();
	/**
	 * Elmenti a irany bemeneteket.
	 */
	public static ArrayList<String> iranyok= new ArrayList<>();
	/**
	 * Feltolti a komponensek es iranyok listakat.
	 */
	private static void init() {
		komponensek.add("PUMP");
		komponensek.add("PIPE");
		komponensek.add("CISTERN");
		komponensek.add("SPRING");
		iranyok.add("1");
		iranyok.add("2");
		iranyok.add("3");
		iranyok.add("4");
	}

	/**
	 * A parancs egy új komponens létrehozására és a pályán való
	 * elhelyezésére szolgál. A parancs akkor fut le sikeresen, hogyha a pozíciók
	 * helyesek és nincs név -és pozíciókonfliktus.
	 */
	private String CreateComponent(String cmd){
		String argCheck = ArgCheck(cmd,4);
		if(argCheck.equals("")){
			String[] args = cmd.split(":")[1].split(",");
			if(args[0].contains("g_")){
				return "[HIBA]: A komponens neve tiltott karaktereket tartalmaz('g_')!";
			}
			if(args[0].equals("null")){
				return "[HIBA]: A komponens neve tiltott ('null')!";
			}
			if(!komponensek.contains(args[1].toUpperCase()))
			{
				return "[HIBA]: Nincs ilyen komponens tipus!";
			}
			int xpos;
			int ypos;
			try {
				xpos = Integer.parseInt(args[2]);
				ypos = Integer.parseInt(args[3]);
			}
			catch (Exception e){
				return "[HIBA]: Nem megfelelo a pozicio!";
			}
			if(components.containsKey(args[0]) ||team1.containsKey(args[0]) || team2.containsKey(args[0]) ){
				return "[HIBA]: Mar letezik ilyen komponens ilyen nevvel!: "+args[0];
			}
			String visszaTeres= "[SIKERES]: Komponens sikeresen létrehozva";
			visszaTeres+= "\n>\tNev: "+args[0];
			switch (args[1].toUpperCase()) {
				case "PUMP":Pump pu = new Pump(args[0], xpos, ypos); components.put(args[0],pu); Main.notifiableList.add(pu);
					Main.map.addComponents(pu);
					pu.setMap(Main.map);
					visszaTeres+= "\n>\tTipus: Pump";
					break;
				case "PIPE":Pipe pi = new Pipe(args[0], xpos, ypos); components.put(args[0],pi);  Main.notifiableList.add(pi);
					Main.map.addComponents(pi);
					pi.setMap(Main.map);
					visszaTeres+= "\n>\tTipus: Pipe";
					break;
				case "CISTERN":Cistern c = new Cistern(args[0], xpos, ypos);components.put(args[0],c); Main.notifiableList.add(c);
					Main.map.addComponents(c);
					c.setMap(Main.map);
					visszaTeres+= "\n>\tTipus: Cistern";
					break;
				case "SPRING":Spring s = new Spring(args[0], xpos, ypos);components.put(args[0],s);
					Main.map.addComponents(s);
					s.setMap(Main.map);
					visszaTeres+= "\n>\tTipus: Spring";
					break;
			}
			visszaTeres+= "\n>\tXkor: "+xpos;
			visszaTeres+= "\n>\tYkor: "+ypos;
			return visszaTeres;
		}
		else{
			return argCheck;
		}
	}
	/**
	 * A parancs összeköt két komponenst a választott végpontjaikon
	 * keresztül. A parancs akkor fut le sikeresen, hogyha az egyik komponens Aktív
	 * és a másik Passzív, illetve hogyha a megadott végpontok helyesek.
	 */
	private String ConnectComponents(String cmd){
		String argCheck = ArgCheck(cmd,4);
		if(argCheck.equals("")){
			String[] args = cmd.split(":")[1].split(",");
			if(!components.containsKey(args[0])){
				return "[HIBA]: Nem letezik ilyen komponens ilyen nevvel!: "+args[0];
			}
			if(!components.containsKey(args[2])){
				return "[HIBA]: Nem letezik ilyen komponens ilyen nevvel!: "+args[2];
			}
			int endp1;
			int endp2;
			try {
				endp1 = Integer.parseInt(args[1]);
				endp2 = Integer.parseInt(args[3]);
			}
			catch (Exception e){
				return "[HIBA]: Nem szamok a vegpontok!(1-4)";
			}
			try{
				SComponent elso = components.get(args[0]);
				Active elso2 = (Active)elso;
				try{
					SComponent masodik = components.get(args[2]);
					Passive masodik2 = (Passive)masodik;
					if(!(endp1>0 && endp1<5 && endp2>0 && endp2<3)){
						return "[HIBA]: A szamok nincsenek a megfelelo intervallumban!(1-4)";
					}
					elso2.setNeighbours(endp1-1, masodik2);
					masodik2.setNeighbours(endp2-1, elso2);
					return "[SIKERES]: "+args[0]+" komponens sikeresen osszekotve "+args[2]+" komponensel!";
				}
				catch (Exception e)
				{
					e.printStackTrace();
					return "[HIBA]: Ezt a ket komponenst nem lehet osszekotni! passiv";
				}
			}
			catch (Exception e)
			{
				try {
					SComponent elso = components.get(args[0]);
					Passive elso2 = (Passive)elso;
					try{
						SComponent masodik = components.get(args[2]);
						Active masodik2 = (Active)masodik;
						if(!(endp2>0 && endp2<5 && endp1>0 && endp1<3)){
							return "[HIBA]: A szamok nincsenek a megfelelo intervallumban!(1-4)";
						}
						elso2.setNeighbours(endp1-1, masodik2);
						masodik2.setNeighbours(endp2-1, elso2);
						return "[SIKERES]: "+args[0]+" komponens sikeresen osszekotve "+args[2]+" komponensel!";
					}
					catch (Exception e2)
					{
						e2.printStackTrace();
						return "[HIBA]: Ezt a ket komponenst nem lehet osszekotni! active";
					}
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
					return "[HIBA]: Ezt a ket komponenst nem lehet osszekotni! passiv";
				}
			}
		}
		else{
			return argCheck;
		}
	}
	/**
	 * A parancs egy játékos mozgatására szolgál egyik szomszédos
	 * komponensről a másikra. A parancs csak akkor hajtódik végre sikeresen,
	 * hogyha a játékos ráléphet az adott komponensre, illetve ha van elég AP-je
	 * (action point) a mozgás elvégzéséhez.
	 */
	private String MovePlayer(String cmd){
		String argCheck = ArgCheck(cmd,2);
		if(argCheck.equals("")){
			String[] args = cmd.split(":")[1].split(",");
			if(!(team1.containsKey(args[0]) || team2.containsKey(args[0]))){
				return "[HIBA]: Nem letezik "+args[0]+" nevu jatekos!";
			}
			if(!components.containsKey(args[1])){
				return "[HIBA]: Nem letezik ilyen komponens ilyen nevvel!: "+args[1];
			}
			Player p;
			if(team1.containsKey(args[0])){
				p= team1.get(args[0]);
			}
			else{
				p= team2.get(args[0]);
			}
			if(p.getRooted()){
				return "[HIBA]: Nem tud atlepni "+args[0]+" jatekos "+args[1]+" komponensre, mert le van ragadva!";
			}
			SComponent c = components.get(args[1]);
			Pipe p1;
			try{
				p1 = (Pipe)c;
				if(p1.getState()== PipeState.SLIPPERY){
					p.Move(p1);
					return "[SIKERES]: A "+args[0]+" sikeresen megcsuszott a csovon!";
				}
				if(p.Move(p1)){
					return "[SIKERES]: "+args[0]+" sikeresen atlepett a "+args[1]+" komponensre!";
				}
				return "[HIBA]: "+args[0]+" nevu jatekosnak nem sikerult atlepnie "+args[1]+" komponensre!";
			}
			catch (Exception e){
				if(p.Move(c)){
					return "[SIKERES]: "+args[0]+" sikeresen atlepett a "+args[1]+" komponensre!";
				}
				return "[HIBA]: "+args[0]+" nevu jatekosnak nem sikerult atlepnie "+args[1]+" komponensre!";
			}
		}
		else{
			return argCheck;
		}
	}
	/**
	 * A parancs annak a pumpának a be -és kimenetének átállítására
	 * szolgál, amelyen a játékos áll. A parancs csak akkor végződik el sikeresen,
	 * hogyha a játékos egy pumpán áll, a megadott értékek helyesek, illetve a
	 * játékosnak van elég AP-je a művelet elvégzéséhez.
	 */
	private String PlayerSetPump(String cmd){
		String argCheck = ArgCheck(cmd,3);
		if(argCheck.equals("")){
			String[] args = cmd.split(":")[1].split(",");
			if(!(team1.containsKey(args[0]) || team2.containsKey(args[0]))){
				return "[HIBA]: Nem letezik "+args[0]+" nevu jatekos!";
			}
			if(!iranyok.contains(args[1]) || !iranyok.contains(args[2]))
			{
				return "[HIBA]: Nem megfeleloek a vegpontok!(1-4)";
			}
			int endp1 = Integer.parseInt(args[1]);
			int endp2 = Integer.parseInt(args[2]);
			Player p;
			if(team1.containsKey(args[0])){
				p= team1.get(args[0]);
			}
			else{
				p= team2.get(args[0]);
			}
			if(p.position.SetPump(endp1-1, endp2-1)){
				return "[SIKERES] A "+args[0]+" sikeresen megveltoztatta "+args[1]+" bemenetet es kimenetet.";
			}
			return "[HIBAS]: Pumpat nem lehet atallitani!";
		}
		else{
			return argCheck;
		}
	}
	/**
	 * A parancs annak az aktív komponensnek valamelyik végpontjához
	 * kapcsolódó cső lecsatolására szolgál, amelyen a játékos áll. A parancs csak
	 * akkor végződik el sikeresen, hogyha a játékos egy aktív komponensen áll, a
	 * megadott végpont valid, illetve a játékosnak van elég AP-je a művelet
	 * elvégzéséhez.
	 */
	private String PlayerDetachPipe(String cmd){
		String argCheck = ArgCheck(cmd,2);
		if(argCheck.equals("")){
			String[] args = cmd.split(":")[1].split(",");
			if(!(team1.containsKey(args[0]) || team2.containsKey(args[0]))){
				return "[HIBA]: Nem letezik "+args[0]+" nevu jatekos!";
			}
			if(!iranyok.contains(args[1]))
			{
				return "[HIBA]: Nem megfelelo a vegpont!(1-4)";
			}
			int endp1 = Integer.parseInt(args[1]);
			Player p;
			if(team1.containsKey(args[0])){
				p= team1.get(args[0]);
			}
			else{
				p= team2.get(args[0]);
			}
			if(p.position.Detach(endp1)){
				return "[SIKERES]: Cso sikeresen levalasztva!";
			}
			return "[HIBAS]: Csovet nem lehet levalasztani!";
		}
		else{
			return argCheck;
		}
	}
	/**
	 * A parancs egy pumpa determinisztikus generálására szolgál egy
	 * választott ciszternán. A parancs akkor végződik el sikeresen, hogyha a
	 * választott komponens valóban egy ciszterna, amelynek a parancs kiadása
	 * előtt nincsen éppen generált pumpája.
	 */
	private String GeneratePump(String cmd){
		String argCheck = ArgCheck(cmd,1);
		if(argCheck.equals("")){
			String[] args = cmd.split(":")[1].split(",");
			if(!components.containsKey(args[0])){
				return "[HIBA]: Nem letezik ilyen komponens ilyen nevvel!: "+args[0];
			}
			try{
				SComponent elso = components.get(args[0]);
				Cistern elso2 = (Cistern) elso;
				if(elso2.getItem() ==null){
					elso2.GeneratePump();
					return "[SIKERES]: Pumpa sikeresen legeneralva!";
				}
				else{
					return "[HIBA]: nem lehet letrehozni a pumpat a "+args[0]+" komponensen, mert mar van rajta egy masik";
				}
			}
			catch (Exception e)
			{
				return "[HIBA]: "+args[1]+" komponens nem ciszterna!";
			}
		}
		else{
			return argCheck;
		}
	}
	/**
	 * A parancs egy csúszós cső azon végpontjának determinisztikus
	 * beállítására szolgál, amelyre a csőre lépő játékos csúszik. A parancs akkor
	 * végződik el sikeresen, hogyha a választott komponens valóban egy cső, a cső
	 * állapota csúszós, illetve a végpont valid.
	 */
	private String SetSlipperyEndPoint(String cmd){
		String argCheck = ArgCheck(cmd,2);
		if(argCheck.equals("")){
			String[] args = cmd.split(":")[1].split(",");
			if(!components.containsKey(args[0])){
				return "[HIBA]: Nem letezik ilyen komponens ilyen nevvel!: "+args[0];
			}
			int endp1;
			try {
				endp1 = Integer.parseInt(args[1]);
			}
			catch (Exception e){
				return "[HIBA]: Nem szam a vegpont!(1-2)";
			}
			if(!(endp1==1 || endp1==2))
			{
				return "[HIBA]: A vegnek 1-nak vagy 2-nek kell lennie!";
			}
			try{
				SComponent elso = components.get(args[0]);
				Pipe elso2 = (Pipe) elso;
				if(elso2.getState()==PipeState.SLIPPERY)
				{
					return "[SIKERES]: "+args[0]+" cso csuszos vege sikeresen beallitva!";
				}
				else{
					return "[HIBA]: "+args[0]+" cso nem csuszos!";
				}
			}
			catch (Exception e)
			{
				return "[HIBA]: "+args[0]+" komponens nem cso!";
			}
		}
		else{
			return argCheck;
		}
	}
	/**
	 * A parancs egy cső állapotának megváltoztatására szolgál játékos
	 * beavatkozása nélkül (cél: ragadós, csúszós állapotok egyszerű tesztelése). A
	 * parancs akkor végződik el helyesen, hogyha a megadott komponens valóban
	 * egy cső, illetve ha a megadott állapotérték helyes.
	 */
	private String ChangePipeState(String cmd){
		String argCheck = ArgCheck(cmd,2);
		if(argCheck.equals("")){
			String[] args = cmd.split(":")[1].split(",");
			if(!components.containsKey(args[0])){
				return "[HIBA]: Nem letezik ilyen komponens ilyen nevvel!: "+args[0];
			}
			try{
				SComponent elso = components.get(args[0]);
				Pipe elso2 = (Pipe) elso;
				String kimenet="";
				switch (args[1].toUpperCase()){
					case "NORMAL": elso2.ChangeState(PipeState.NORMAL);
						kimenet= "[SIKERES]: "+args[0]+" cso allapota atallitva:"+args[1];
						break;
					case "STICKY": elso2.ChangeState(PipeState.STICKY); kimenet= "[SIKERES]: "+args[0]+" cso allapota atallitva:"+args[1];break;
					case "SLIPPERY": elso2.ChangeState(PipeState.SLIPPERY); kimenet= "[SIKERES]: "+args[0]+" cso allapota atallitva:"+args[1];break;
					default: kimenet= "[HIBAS]: Nincs ilyen cso allapot!";break;
				}
				return  kimenet;
			}
			catch (Exception e)
			{
				return "[HIBA]: "+args[0]+" komponens nem cso!";
			}
		}
		else{
			return argCheck;
		}
	}

	/**
	 * A parancs egy játékos action point-jainak manuális beállítását teszi
	 * lehetővé cselekvések végzése nélkül. Teszteléshez szinte elengedhetetlen. A
	 * parancs akkor végződik el helyesen, hogyha a megadott érték helyes.
	 */
	private String SetAP(String cmd){
		String argCheck = ArgCheck(cmd,2);
		if(argCheck.equals("")){
			String[] args = cmd.split(":")[1].split(",");
			if(!(team1.containsKey(args[0]) || team2.containsKey(args[0]))){
				return "[HIBA]: Nem letezik "+args[0]+" nevu jatekos!";
			}
			int point;
			try {
				point = Integer.parseInt(args[1]);
			}
			catch (Exception e){
				return "[HIBA]: Nem szam ad megadott pont!";
			}
			Player p;
			if(team1.containsKey(args[0])){
				p= team1.get(args[0]);
			}
			else{
				p= team2.get(args[0]);
			}
			p.setAP(point);
			return "[SIKERES]: "+args[0]+" nevu jatekos pontja beallitva "+point+" pontra";
		}
		else{
			return argCheck;
		}
	}
	/**
	 * A parancs a véletlenszerűen bekövetkező események (pumpa -és
	 * csőgenerálás, pumpa elromlása, cső lyukaszthatóvá tétele, csövön való
	 * csúszás random végponthoz) engedélyezését és letiltását valósítja meg. Ha
	 * ez a funkció ki van kapcsolva, akkor csak a korábban leírt determinisztikus
	 * parancsokkal történhetnek meg ezeknek a funkcióknak a végrehajtódásai. A
	 * parancs akkor végződik el helyesen, hogyha a megadott érték helyes.
	 */
	private String ToggleRandom(String cmd){
		String argCheck = ArgCheck(cmd,1);
		if(argCheck.equals("")){
			String[] args = cmd.split(":")[1].split(",");
			if(args[0].equals("true")){
				randomEnabled=true;
				return "[SIKERES]: Random generalas sikeres atallitva igazra!";
			}
			if(args[0].equals("false")){
				randomEnabled=false;
				return "[SIKERES]: Random generalas sikeres atallitva hamisre!";
			}
			return "[HIBA]: Nem megfelelo bemenet!(true/false)";
		}
		else{
			return argCheck;
		}
	}
	/**
	 * A parancs a két csapat pontjainak kiszámítására és kiírására szolgál a
	 * pálya pillanatnyi állapotában.
	 */
	private String CalculatePoints(String cmd){
		Main.sb.TallyPoints(Main.map);
		return "[JELENLEGI ALLAS]:\n>Szerelok: " + Main.sb.getMechPoints() + "\n>Szabotorok: " + Main.sb.getSabPoints();
	}
	/**
	 * A parancs a tesztelés során írt parancsok és kapott kimenetek
	 * szöveges fájlba történő kiexportálásra szolgál. A fájlba ezeken kívül kiírodik
	 * még az összes komponens és player neve, típusa és attribútumai is
	 * (tulajdonképpen egy List parancs).
	 */
	private String Save(String cmd){
		String argCheck = ArgCheck(cmd,1);
		if(argCheck.equals("")){
			String[] args = cmd.split(":")[1].split(",");
			String kimenet="";
			try {
				File myObj = new File(args[0]);
				if (myObj.createNewFile()) {
					kimenet +="[SIKERES]: " + myObj.getName()+" fajl letrehozva";
					try {
						FileWriter myWriter = new FileWriter(myObj);
						for (int i =0; i<outputs.size(); i++){
							myWriter.write(inputs.get(i)+"\n");
							myWriter.write(outputs.get(i)+"\n");
						}
						myWriter.close();
					} catch (IOException e) {
						kimenet +="\n>[HIBA]: A fajl nem irhato!";
					}
				}
				else {
					kimenet +="\n>[HIBA]: Mar letezik ilyen fajl a mappaban";
				}
			} catch (IOException e) {
				kimenet +="\n>[HIBA]: Valami hiba tortent!";
			}
			return kimenet;
		}
		else{
			return argCheck;
		}
	}
	/**
	 * Parancsok szöveges fájlból történő beolvasására és feldolgozására
	 * szolgál. A parancs akkor végződik el sikeresen, hogyha a megadott file létezik
	 * és az útvonala helyes.
	 */
	private String List(String cmd){
			String kimenet="";
			for ( String key : components.keySet() ) {
				kimenet +="\n>Nev: "+ key;
				kimenet +="\n>Viz: "+ components.get(key).getHasWater();
				kimenet +="\n>Torott: "+ components.get(key).getBroken();
				kimenet +="\n>Torheto: "+ components.get(key).getBreakable();
				kimenet +="\n>X koordinata: "+ components.get(key).getPosX();
				kimenet +="\n>Y koordinata: "+ components.get(key).getPosY();
			}
			return kimenet;
	}
	
}

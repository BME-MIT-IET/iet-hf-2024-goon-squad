import java.util.Random;

public class Pump extends Active implements Notifiable {

	//Attribútumok
	private int inputIndex; //Tárolja annak a kimenetének az indexét amelyikből folyik a víz a pumpán keresztül
	private int outputIndex; //Tárolja annak a kimenetének az indexét amibe folyik a víz a pumpán keresztül
	private boolean randomizable = true;
	private Random r = new Random();

	//Konstruktor
	public Pump(String s,int X, int Y)
	{
		hasWater = false; //Alapból nincs vize a pumpának
		PassiveComponents= new Passive[4];
		broken = false; //Alapból a pumpa működőképes
		inputIndex = 0; //Alapból nincs input választva (mivel a pumpa nem kapcsolódik semmihez)
		outputIndex = 0; //Alapból nincs input választva (mivel a pumpa nem kapcsolódik semmihez)
		posX= X;
		posY= Y;
		id=s;
	}

	//Ez a függvény javítja meg a pumpát
	public boolean Fix()
	{
		if(broken == true) {
			broken = false;
			return true;
		}
		return false;

	}
	public boolean setBroken(boolean b)
	{
		broken = b;
		return true;

	}
	public boolean isInput(Passive p){
		if(PassiveComponents[inputIndex]==p){
			return true;
		}
		return false;
	}

	//A Pumpa a paraméterként kapott komponensből vizet vesz át, és a kimeneti komponensének vizet ad át
	public void MoveWater(SComponent from)
	{
		if(hasWater && !broken && !movedWater) {
			if(PassiveComponents[outputIndex]!=null) {
				PassiveComponents[outputIndex].MoveWater(this);
				hasWater = false;
			}
		}
		else if( from == PassiveComponents[inputIndex])
		{
			hasWater = true;
			movedWater = true;
		}
	}

	//Állítja a víz folyásának irányát a pumpán; az első paraméter lesz a víz bemenete, a második a kimenete
	public boolean SetPump(int newIn, int newOut)
	{
		if(newIn>=0 && newOut>=0 && newIn <4 && newOut <4 ) {
			//belső működés
			inputIndex = newIn;
			outputIndex = newOut;
			return true;
		}
		return false;

	}

	//Ezt a függvényt hívja meg egy passzív elem a pumpalehelyezés elvégzéséhez
	public boolean Insert(Passive oldPassive) {
		if(oldPassive == null) return false;
		Passive NewPassive;
		if(oldPassive.getMap() == null) System.out.println("No map?");
		try{
			NewPassive = oldPassive.CreateCopy();
			addComponentsToMap(oldPassive, NewPassive);
			swapComponents(oldPassive, NewPassive);
			setComponentPositions(oldPassive, NewPassive);
			swapOldPipeWithNew(oldPassive, NewPassive);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	private void addComponentsToMap(Passive oldPassive, Passive NewPassive) {
		oldPassive.getMap().addComponents(NewPassive);
		oldPassive.getMap().addComponents(this);
		this.Swap(NewPassive,null);
		this.Swap(oldPassive, null);
		NewPassive.ActiveComponents[NewPassive.inputIndex] = this;
		oldPassive.ActiveComponents[oldPassive.outputIndex] = this;
		this.PassiveComponents[3] = oldPassive;
		this.PassiveComponents[1] = NewPassive;
	}
	
	private void swapComponents(Passive oldPassive, Passive NewPassive) {
		this.Swap(NewPassive,null);
		this.Swap(oldPassive, null);
		NewPassive.ActiveComponents[NewPassive.inputIndex] = this;
		oldPassive.ActiveComponents[oldPassive.outputIndex] = this;
	}
	
	private void setComponentPositions(Passive oldPassive, Passive NewPassive) {
		setPosForPassive(oldPassive);
		setPosForPassive(NewPassive);
	}
	
	private void setPosForPassive(Passive passive) {
		if(passive.ActiveComponents[0] != null && passive.ActiveComponents[1] != null) {
			passive.setPos((passive.ActiveComponents[0].getPosX() + passive.ActiveComponents[1].getPosX()) / 2, (passive.ActiveComponents[0].getPosY() + passive.ActiveComponents[1].getPosY()) / 2);
		}
		if(passive.ActiveComponents[0] == null && passive.ActiveComponents[1] != null){
			setPosForSingleEnd(passive, passive.ActiveComponents[1]);
		}
		if(passive.ActiveComponents[1] == null && passive.ActiveComponents[0] != null){
			setPosForSingleEnd(passive, passive.ActiveComponents[0]);
		}
	}
	
	private void setPosForSingleEnd(Passive passive, Active activeComponent) {
		if(activeComponent.PassiveComponents[0] != null && activeComponent.PassiveComponents[0].equals(passive))	passive.setPos(activeComponent.getPosX(), activeComponent.getPosY() - 180);
		else if(activeComponent.PassiveComponents[1] != null && activeComponent.PassiveComponents[1].equals(passive))	passive.setPos(activeComponent.getPosX() + 180, activeComponent.getPosY());
		else if(activeComponent.PassiveComponents[2] != null && activeComponent.PassiveComponents[2].equals(passive))	passive.setPos(activeComponent.getPosX(), activeComponent.getPosY() + 180);
		else if(activeComponent.PassiveComponents[3] != null)	passive.setPos(activeComponent.getPosX() - 180, activeComponent.getPosY());
	}
	
	private void swapOldPipeWithNew(Passive oldPassive, Passive NewPassive) {
		if(NewPassive.ActiveComponents[NewPassive.outputIndex] != null)
			NewPassive.ActiveComponents[NewPassive.outputIndex].Swap(oldPassive,NewPassive);
	}
	//Ez a függvény teszi tönkre a pumpát ténylegesen
	public void Failure()
	{
		broken = true;
	}

	// Ez függvény rontja el a pumpát bizonyos időnként (ha a pumpa működik)
	public void Notify()
	{
		if(randomizable) {
			
			int ran =r.nextInt(50);
			if(ran == 0)
				if(!broken){
					Failure();
				}
		}
	}

}

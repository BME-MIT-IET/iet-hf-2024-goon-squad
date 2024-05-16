import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Tests
{
    CmdInterpreter testCmds = new CmdInterpreter();

    @BeforeEach
    public void setup() 
    {
        testCmds = new CmdInterpreter();
    }

    @Test
    public void test0()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
    }

    /*
    //Szerelo vegig lep komponenseken
    @Test
    public void test1()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES]: me1 uj pozicio beallitva\n" + "\t>Nev: me1\n" + "\t>Uj pozicio: sp1", testCmds.NewCommand("SetPlayerPos:me1,sp1"));
        Assertions.assertEquals("[SIKERES]: me1 sikeresen atlepett a pi1 komponensre!", testCmds.NewCommand("MovePlayer:me1,pi1"));
        Assertions.assertEquals("[SIKERES]: me1 sikeresen atlepett a pu1 komponensre!", testCmds.NewCommand("MovePlayer:me1,pu1"));
        Assertions.assertEquals("[SIKERES]: me1 sikeresen atlepett a ci1 komponensre!", testCmds.NewCommand("MovePlayer:me1,ci1"));
    }

    //Szabotor ralep egy csuszos csore
    @Test
    public void test2()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES]: pi1 cso allapota atallitva:Slippery", testCmds.NewCommand("ChangePipeState:pi1,Slippery"));
        Assertions.assertEquals("[SIKERES]: pi1 cso csuszos vege sikeresen beallitva!", testCmds.NewCommand("SetSlipperyEndPoint:pi1,2"));
        Assertions.assertEquals("[SIKERES]: sa1 uj pozicio beallitva\n" + "\t>Nev: sa1\n" + "\t>Uj pozicio: sp1", testCmds.NewCommand("SetPlayerPos:sa1,sp1"));
        Assertions.assertEquals("[SIKERES]: A sa1 sikeresen megcsuszott a csovon!", testCmds.NewCommand("MovePlayer:sa1,pi1"));
    }

    //Szerelo ralep egy ragados csore (es utana mozogni probal)
    @Test
    public void test3()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES]: pi1 cso allapota atallitva:Sticky", testCmds.NewCommand("ChangePipeState:pi1,Sticky"));
        Assertions.assertEquals("[SIKERES]: me1 uj pozicio beallitva\n" + "\t>Nev: me1\n" + "\t>Uj pozicio: pi1", testCmds.NewCommand("SetPlayerPos:me1,pi1"));
        Assertions.assertEquals("[HIBA]: Nem tud atlepni me1 jatekos pu1 komponensre, mert le van ragadva!", testCmds.NewCommand("MovePlayer:me1,pu1"));
    }

    //Szerelo atallit egy pumpat
    @Test
    public void test4()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES]: me1 uj pozicio beallitva\n" + "\t>Nev: me1\n" + "\t>Uj pozicio: pu1", testCmds.NewCommand("SetPlayerPos:me1,pu1"));
        Assertions.assertEquals("[SIKERES] A me1 sikeresen megveltoztatta 2 bemenetet es kimenetet.", testCmds.NewCommand("PlayerSetPump:me1,2,4"));
    }

    //Szerelo ragadossa tesz egy csovet
    @Test
    public void test5()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES]: me1 uj pozicio beallitva\n" + "\t>Nev: me1\n" + "\t>Uj pozicio: pi1", testCmds.NewCommand("SetPlayerPos:me1,pi1"));
        Assertions.assertEquals("[SIKERES] A(z) me1 jatekos ragadossa tette a komponenst, amin all", testCmds.NewCommand("PlayerSimpleAction:me1,sticky"));
    }

    //Szerelo megjavit egy pumpat
    @Test
    public void test6()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES]: me1 uj pozicio beallitva\n" + "\t>Nev: me1\n" + "\t>Uj pozicio: pu1", testCmds.NewCommand("SetPlayerPos:me1,pu1"));
        Assertions.assertEquals("[SIKERES] A(z) pu1 komponens toresi allapota beallitva!", testCmds.NewCommand("ChangeBreakState:pu1,broken"));
        Assertions.assertEquals("[SIKERES] A(z) me1 jatekos megszerelte a komponenst, amin all", testCmds.NewCommand("PlayerSimpleAction:me1,fix"));
    }

    //Szabotor csovet lyukaszt
    @Test
    public void test7()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES]: sa1 uj pozicio beallitva\n" + "\t>Nev: sa1\n" + "\t>Uj pozicio: pi1", testCmds.NewCommand("SetPlayerPos:sa1,pi1"));
        Assertions.assertEquals("[SIKERES] A(z) sa1 jatekos kiszurta a komponenst, amin all", testCmds.NewCommand("PlayerSimpleAction:sa1,puncture"));
    }

    //Szerelo felvesz egy pumpat egy ciszternarol
    @Test
    public void test8()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES]: Pumpa sikeresen legeneralva!", testCmds.NewCommand("GeneratePump:ci1"));
        Assertions.assertEquals("[SIKERES]: me1 uj pozicio beallitva\n" + "\t>Nev: me1\n" + "\t>Uj pozicio: ci1", testCmds.NewCommand("SetPlayerPos:me1,ci1"));
        Assertions.assertEquals("[SIKERES] A(z) me1 jatekos osszeszedett egy pumpat", testCmds.NewCommand("PlayerSimpleAction:me1,collect"));
    }

    //Szerelo letesz egy pumpat egy csore
    @Test
    public void test9()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES] me1 jatekosnak pumpa beallitva", testCmds.NewCommand("SetItem:me1"));
        Assertions.assertEquals("[SIKERES]: me1 uj pozicio beallitva\n" + "\t>Nev: me1\n" + "\t>Uj pozicio: pi1", testCmds.NewCommand("SetPlayerPos:me1,pi1"));
        Assertions.assertEquals("[SIKERES] A(z) me1 jatekos lehelyezett egy pumpat", testCmds.NewCommand("PlayerSimpleAction:me1,place"));
    }

    //Egy pumpa elromlik (majd megprobal vizet szallitani)
    @Test
    public void test10()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES] pu1 komponens elrontva", testCmds.NewCommand("BreakPump:pu1"));
        Assertions.assertEquals("[SIKERES] a(z) sp1 vizet mozgatott a szomszedos komponenseibe", testCmds.NewCommand("MoveWater:sp1,null"));
        Assertions.assertEquals("[SIKERES] A(z) pi1 vizmozgatasi folyamata elvegzodott", testCmds.NewCommand("MoveWater:pi1,sp1"));
        Assertions.assertEquals("[HIBA] pu1 komponens nem tud vizet szallitani, mivel elromlott", testCmds.NewCommand("MoveWater:pu1,pi1"));
    }

    //Szerelo levalaszt egy csovet
    @Test
    public void test11()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES]: me1 uj pozicio beallitva\n" + "\t>Nev: me1\n" + "\t>Uj pozicio: pu1", testCmds.NewCommand("SetPlayerPos:me1,pu1"));
        Assertions.assertEquals("[SIKERES]: Cso sikeresen levalasztva!", testCmds.NewCommand("PlayerDetachPipe:me1,1"));
    }

    //Egy pumpa generalodik egy ciszternan
    @Test
    public void test12()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES]: Pumpa sikeresen legeneralva!", testCmds.NewCommand("GeneratePump:ci1"));
    }

    //Pontszamitas kor vegen
    @Test
    public void test13()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES] a(z) sp1 vizet mozgatott a szomszedos komponenseibe", testCmds.NewCommand("MoveWater:sp1,null"));
        Assertions.assertEquals("[SIKERES] A(z) pi1 vizmozgatasi folyamata elvegzodott", testCmds.NewCommand("MoveWater:pi1,sp1"));
        Assertions.assertEquals("[SIKERES] A(z) pu1 vizmozgatasi folyamata elvegzodott", testCmds.NewCommand("MoveWater:pu1,pi1"));
        Assertions.assertEquals("[SIKERES] A(z) pi2 vizmozgatasi folyamata elvegzodott", testCmds.NewCommand("MoveWater:pi2,pu1"));
        Assertions.assertEquals("[SIKERES] A(z) ci1 vizmozgatasi folyamata elvegzodott", testCmds.NewCommand("MoveWater:ci1,pi2"));
        Assertions.assertEquals("[JELENLEGI ALLAS]:\n" + ">Szerelok: 1\n" + ">Szabotorok: 0", testCmds.NewCommand("CalculatePoints"));
        Assertions.assertEquals("[SIKERES]: sa1 uj pozicio beallitva\n" + "\t>Nev: sa1\n" + "\t>Uj pozicio: pi1", testCmds.NewCommand("SetPlayerPos:sa1,pi1"));
        Assertions.assertEquals("[SIKERES] A(z) sa1 jatekos kiszurta a komponenst, amin all", testCmds.NewCommand("PlayerSimpleAction:sa1,puncture"));
        Assertions.assertEquals("[SIKERES] a(z) sp1 vizet mozgatott a szomszedos komponenseibe", testCmds.NewCommand("MoveWater:sp1,null"));
        Assertions.assertEquals("[HIBA] pi1 komponens nem tud vizet szallitani, mivel elromlott", testCmds.NewCommand("MoveWater:pi1,sp1"));
        Assertions.assertEquals("[JELENLEGI ALLAS]:\n" + ">Szerelok: 1\n" + ">Szabotorok: 1", testCmds.NewCommand("CalculatePoints"));
    }

    //Szerelo foglalt csore lepne
    @Test
    public void test14()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES]: sa1 uj pozicio beallitva\n" + "\t>Nev: sa1\n" + "\t>Uj pozicio: pi1", testCmds.NewCommand("SetPlayerPos:sa1,pi1"));
        Assertions.assertEquals("[HIBA]: me1 nevu jatekosnak nem sikerult atlepnie pi1 komponensre!", testCmds.NewCommand("MovePlayer:me1,pi1"));
    }

    //Torott cso vizet szallitana
    @Test
    public void test15()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES] A(z) pi1 komponens toresi allapota beallitva!", testCmds.NewCommand("ChangeBreakState:pi1,broken"));
        Assertions.assertEquals("[SIKERES] a(z) sp1 vizet mozgatott a szomszedos komponenseibe", testCmds.NewCommand("MoveWater:sp1,null"));
        Assertions.assertEquals("[HIBA] pi1 komponens nem tud vizet szallitani, mivel elromlott", testCmds.NewCommand("MoveWater:pi1,sp1"));
        Assertions.assertEquals("[HIBA] a(z) pu1 nem tudott vizet fogadni a(z) pi1 komponenstol, mivel a(z) pi1 komponens elromlott", testCmds.NewCommand("MoveWater:pu1,pi1"));
    }

    //Ciszternan sikertelen generalasok (cso es pumpa)
    @Test
    public void test16()
    {
        Assertions.assertEquals("[SIKERES] testmap.txt betoltve", testCmds.NewCommand("Load:testmap.txt"));
        Assertions.assertEquals("[SIKERES]: Pumpa sikeresen legeneralva!", testCmds.NewCommand("GeneratePump:ci1"));
        Assertions.assertEquals("[HIBA]: nem lehet letrehozni a pumpat a ci1 komponensen, mert mar van rajta egy masik", testCmds.NewCommand("GeneratePump:ci1"));
        Assertions.assertEquals("[HIBA] A(z) ci1 komponens 4. vegpontjan nem hozhato letre cso (a vegpont foglalt)!", testCmds.NewCommand("GeneratePipe:ci1,4"));
    }
    */
}
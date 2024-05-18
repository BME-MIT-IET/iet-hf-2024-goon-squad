import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {

    CmdInterpreter cmd = new CmdInterpreter();
    String activePlayer = "";
    String activeComponent = "";
    String testResult = "";
    String neighbourComponent = "";

    @Before
    public void setup() {
        cmd.NewCommand("Load:testmap.txt");
        activePlayer = "";
        activeComponent = "";
        testResult = "";
        neighbourComponent = "";
    }

    @Given("that a pipe has been punctured")
    public void thatAPipeHasBeenPunctured() {
        activeComponent = "pi1";
        cmd.NewCommand("ChangeBreakState:"+activeComponent+",broken");
    }

    @And("I am a Mechanic")
    public void iAmAMechanic() {
        activePlayer = "me1";
    }

    @And("I am standing on the pipe")
    public void iAmStandingOnThePipe() {
        if(Objects.equals(activePlayer, ""))
        {
            activePlayer = "me1";
        }
        activeComponent = "pi1";
        cmd.NewCommand("SetPlayerPos:"+activePlayer+","+activeComponent);
    }

    @And("I have enough AP")
    public void iHaveEnoughAP() {
        cmd.NewCommand("SetAP:"+activePlayer+",10");
    }

    @When("I fix the pipe")
    public void iFixThePipe() {
        testResult = cmd.NewCommand("PlayerSimpleAction:"+activePlayer+",fix");
    }

    @Then("it should be functional again")
    public void itShouldBeFunctionalAgain() {
        assertEquals("[SIKERES] A(z) "+activePlayer+" jatekos megszerelte a komponenst, amin all", testResult);
    }

    @And("I don't have enough AP")
    public void iDonTHaveEnoughAP() {
        cmd.NewCommand("SetAP:"+activePlayer+",0");
    }

    @Then("the pipe shouldn't be fixed")
    public void thePipeShouldnTBeFixed() {
        assertEquals("[HIBA] A jatekosnak nincs eleg AP-je a muvelet elvegzesehez!", testResult);
    }

    @Given("that I am the active player")
    public void thatIAmTheActivePlayer() {
        activePlayer = "me1";
    }

    @When("I pass my turn")
    public void iPassMyTurn() {
        testResult = cmd.NewCommand("PlayerSimpleAction:"+activePlayer+",pass");
    }

    @Then("my turn should be over")
    public void myTurnShouldBeOver() {
        assertEquals("[SIKERES] A(z) "+activePlayer+" jatekos passzolt", testResult);
    }

    @Given("that I am a Cistern")
    public void thatIAmACistern() {
        activeComponent = "ci1";
    }

    @And("I have no generated pumps")
    public void iHaveNoGeneratedPumps() {
        cmd.NewCommand("ToggleRandom:false"); //véletlenszerű eventek letiltása, a ciszternán így nem generálódhat pumpa
    }

    @When("it is time to generate a pump")
    public void itIsTimeToGenerateAPump() {
        testResult = cmd.NewCommand("GeneratePump:"+activeComponent);
    }

    @Then("the pump should get generated")
    public void thePumpShouldGetGenerated() {
        assertEquals("[SIKERES]: Pumpa sikeresen legeneralva!", testResult);
    }

    @And("I already have a generated pump")
    public void iAlreadyHaveAGeneratedPump() {
        cmd.NewCommand("GeneratePump:"+activeComponent);
    }

    @Then("the pump shouldn't get generated")
    public void thePumpShouldnTGetGenerated() {
        assertEquals("[HIBA]: nem lehet letrehozni a pumpat a "+activeComponent+" komponensen, mert mar van rajta egy masik", testResult);
    }

    @Given("that a cistern has a generated pump")
    public void thatACisternHasAGeneratedPump() {
        activeComponent = "ci1";
        cmd.NewCommand("GeneratePump:"+activeComponent);
    }

    @And("I am standing on the cistern")
    public void iAmStandingOnTheCistern() {
        activePlayer = "me1";
        cmd.NewCommand("SetPlayerPos:"+activePlayer+","+activeComponent);
    }

    @And("my inventory is empty")
    public void myInventoryIsEmpty() {
        cmd.NewCommand("SetPlayerPos:"+activePlayer+",pi3"); //Player gets moved to a Pipe
        cmd.NewCommand("PlayerSimpleAction:"+activePlayer+",place"); //Player attempts to place down pump (if he has a pump, it will be placed down, so now his inventory is empty)
        cmd.NewCommand("SetPlayerPos:"+activePlayer+","+activeComponent); //Player gets moved back to the Cistern
    }

    @When("I attempt to collect the pump")
    public void iAttemptToCollectThePump() {
        testResult = cmd.NewCommand("PlayerSimpleAction:"+activePlayer+",collect");
    }

    @Then("the pump should be collected")
    public void thePumpShouldBeCollected() {
        assertEquals("[SIKERES] A(z) "+activePlayer+" jatekos osszeszedett egy pumpat", testResult);
    }

    @Then("the pump shouldn't be collected")
    public void thePumpShouldnTBeCollected() {
        assertTrue(testResult.contains("[HIBA]"));
    }

    @And("my inventory is not empty")
    public void myInventoryIsNotEmpty() {
        cmd.NewCommand("SetItem:"+activePlayer);
    }

    @Given("that a cistern has no generated pumps")
    public void thatACisternHasNoGeneratedPumps() {
        cmd.NewCommand("ToggleRandom:false"); //véletlenszerű eventek letiltása, a ciszternán így nem generálódhat pumpa
        activeComponent = "ci1";
    }

    @Then("there is no pump to collect")
    public void thereIsNoPumpToCollect() {
        assertEquals("[HIBA] A jatekos jelenlegi poziciojan nincs generalt pumpa!", testResult);
    }

    @Given("that a pipe is not slippery")
    public void thatAPipeIsNotSlippery() {
        activeComponent = "pi1";
        cmd.NewCommand("ChangePipeState:"+activeComponent+",Normal");
    }

    @And("I am a Saboteur")
    public void iAmASaboteur() {
        activePlayer = "sa1";
    }

    @When("I attempt to make the pipe slippery")
    public void iAttemptToMakeThePipeSlippery() {
        testResult = cmd.NewCommand("PlayerSimpleAction:"+activePlayer+",slippery");
    }

    @Then("the pipe should get slippery")
    public void thePipeShouldGetSlippery() {
        assertEquals("[SIKERES] A(z) "+activePlayer+" jatekos csuszossa tette a komponenst, amin all", testResult);
    }

    @Given("that a pipe is slippery")
    public void thatAPipeIsSlippery() {
        activeComponent = "pi1";
        cmd.NewCommand("ChangePipeState:"+activeComponent+",Slippery");
    }

    @Then("nothing should happen, as the pipe is already slippery")
    public void nothingShouldHappenAsThePipeIsAlreadySlippery() {
        assertEquals("[HIBA] Ez a tipusu komponens nem teheto csuszossa! (Elvart: Pipe)", testResult); //Egy picit csalóka, de ez a helyes hibaüzenet
    }

    @Then("the pipe shouldn't get slippery")
    public void thePipeShouldnTGetSlippery() {
        assertEquals("[HIBA] A jatekosnak nincs eleg AP-je a muvelet elvegzesehez!", testResult);
    }

    @Given("that I am a pump")
    public void thatIAmAPump() {
        activeComponent = "pu1";
    }

    @And("I am functional")
    public void iAmFunctional() {
        cmd.NewCommand("ChangeBreakState:"+activeComponent+",breakable");
    }

    @When("I experience a breakdown")
    public void iExperienceABreakdown() {
        testResult = cmd.NewCommand("BreakPump:"+activeComponent);
    }

    @Then("I should become non-functional")
    public void iShouldBecomeNonFunctional() {
        assertEquals("[SIKERES] "+activeComponent+" komponens elrontva", testResult);
    }

    @And("I am non-functional")
    public void iAmNonFunctional() {
        cmd.NewCommand("ChangeBreakState:"+activeComponent+",broken");
    }

    @Then("nothing happens, as I am already broken")
    public void nothingHappensAsIAmAlreadyBroken() {
        assertEquals("[HIBA] "+activeComponent+" nem rontható el, mivel már elromlott", testResult);
    }

    @Given("that I intend to move to an existing neighbour")
    public void thatIIntendToMoveToAnExistingNeighbour() {
        activePlayer = "me1";
        neighbourComponent = "pu1";
    }

    @And("I am not stuck")
    public void iAmNotStuck() {
        cmd.NewCommand("UnstickPlayer:"+activePlayer);
    }

    @When("I attempt to move to the selected component")
    public void iAttemptToMoveToTheSelectedComponent() {
        testResult = cmd.NewCommand("MovePlayer:"+activePlayer+","+neighbourComponent);
    }

    @Then("the movement should be successful")
    public void theMovementShouldBeSuccessful() {
        assertEquals("[SIKERES]: "+activePlayer+" sikeresen atlepett a "+neighbourComponent+" komponensre!", testResult);
    }

    @And("I am stuck")
    public void iAmStuck() {
        cmd.NewCommand("MovePlayer:"+activePlayer+","+neighbourComponent); //Temporarily move player to neighbouring component
        cmd.NewCommand("ChangePipeState:"+activeComponent+",Sticky"); //Turn the pipe sticky
        cmd.NewCommand("MovePlayer:"+activePlayer+","+activeComponent); //Move player back to pipe, so he gets stuck
    }

    @Then("the movement should be unsuccessful")
    public void theMovementShouldBeUnsuccessful() {
        assertTrue(testResult.contains("[HIBA]"));
    }

    @And("that I intend to move to a non-existing neighbour")
    public void thatIIntendToMoveToANonExistingNeighbour() {
        neighbourComponent = "pu100";
    }

    @Given("that a pipe is working")
    public void thatAPipeIsWorking() {
        activeComponent = "pi1";
        cmd.NewCommand("ChangeBreakState:"+activeComponent+",breakable");
    }

    @When("I puncture the pipe")
    public void iPunctureThePipe() {
        testResult = cmd.NewCommand("PlayerSimpleAction:"+activePlayer+",puncture");
    }

    @Then("it should be punctured")
    public void itShouldBePunctured() {
        assertEquals("[SIKERES] A(z) " + activePlayer + " jatekos kiszurta a komponenst, amin all", testResult);
    }

    @Then("the pipe shouldn't get punctured")
    public void thePipeShouldnTGetPunctured() {
        assertEquals("[HIBA] A jatekosnak nincs eleg AP-je a muvelet elvegzesehez!", testResult);
    }

    @Given("that a pipe is punctured")
    public void thatAPipeIsPunctured() {
        activeComponent = "pi1";
        cmd.NewCommand("ChangeBreakState:"+activeComponent+",broken");
    }

    @Then("the pipe shouldn't be punctured again")
    public void thePipeShouldnTBePuncturedAgain() {
        assertEquals("[HIBA] A komponenst nem lehet kiszurni (vagy mar ki van szurva)!", testResult);
    }
}

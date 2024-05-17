**
# BDD DOKUMENTÁCIÓ
**

## A feladat részei

A feladatot három fázisra osztottam fel:

 *1. A tesztelendő funkciók meghatározása
 2. A tesztelendő funkciók specifikációja a BDD szabályai szerint
 3. A specifikált esetek implementálása*

## Tesztelendő funkciók

Teszteseteknek nyolc olyan alapvető funckiót választottam, amelyek helyes működése a játék szempontjából igencsak fontos/alapvető/elengedhetetlen. Ezek vegyes komplexitásúak: vannak köztük igazán egyszerűek (pl. passzolás), de olyanok is, ahol akár 3-4 forgatókönyv is előállhat (mozgás, pumpa összeszedése). A fő aktorok is igen változatosak: *Mechanic, Saboteur, Player, Cistern* és *Pump* fókuszú tesztek is megtalálhatóak a választott funkciók között.

**

 *1. Mechanic standing on a punctured pipe attempts to fix it
 2. Player passes their turn
 3. Player standing on a pipe attempts to puncture it
 4. Cistern attempts to generate a pump
 5. Mechanic attempts to collect a pump from a cistern
 6. Saboteur standing on a pipe attempts to make it slippery
 7. A pump experiences a breakdown
 8. Player attempts to move from a pipe to one of its neighbouring components*

## Tesztek specifikációja

A tesztek specifikációja a BDD szabványt követi. Egy címmel (*Title*) kezdődik, majd a fő aktor és a teszt céljának bemutatása következik (*As a, I want, so that*). Ezután a különböző esetek (*Scenario*) leírása következik. Itt először megadjuk az előfeltételeket (*Given, and*), majd a cselekvést (*when*), végül pedig az elvárt eredményt (*then*).

**

**Title: Mechanic standing on a punctured pipe attempts to fix it.**

As a Mechanic,
I want to be able to fix punctured pipes while standing on them,
so that they become functional again.

**Scenario 1:** A Mechanic with enough AP fixes a punctured pipe while standing on it.
Given that a pipe has been punctured
and I am a Mechanic
and I am standing on the pipe
and I have enough AP left,
when I fix the pipe,
then it should be functional again.

**Scenario 2:** A Mechanic without enough AP attempts to fix a punctured pipe while standing on it.
Given that a pipe has been punctured
and I am a Mechanic
and I am standing on the pipe
and I don't have enough AP left,
when I try to fix the pipe,
then the pipe shouldn't be fixed.

---

**Title: Player passes their turn.**

As a Player,
I want to be able to pass my turn,
so that my turn ends and the next player can start theirs.

**Scenario 1:** A Player passes their turn.
Given that I am the active player,
when I pass my turn,
then my turn should be over.

---

**Title: Saboteur standing on a pipe attempts to puncture it.**

As a Player,
I want to be able to puncture pipes while standing on them,
so that they become non-functional.

**Scenario 1:** A Player with enough AP punctures a working pipe while standing on it.
Given that a pipe is working
and I am standing on the pipe
and I have enough AP left,
when I puncture the pipe,
then it should be punctured.

**Scenario 2:** A Player without enough AP attempts to puncture a working pipe while standing on it.
Given that a pipe is working
and I am standing on the pipe
and I don't have enough AP left,
when I attempt to puncture the pipe,
then the pipe shouldn't get punctured.

**Scenario 3:** A Player without enough AP attempts to puncture a broken pipe while standing on it.
Given that a pipe is punctured
and I am standing on the pipe
and I have enough AP left,
when I attempt to puncture the pipe,
then the pipe shouldn't be punctured again.

---

**Title: Cistern attempts to generate a pump.**

As a Cistern,
I want to be able to generate pumps (one at a time),
so that they can be pe picked up and placed down by Mechanics.

**Scenario 1:** A Cistern with no pump on it successfully generates a pump.
Given that I am a Cistern 
and I have no generated pumps,
when it is time to generate a pump,
then the pump should get generated. 

**Scenario 2:** A cistern with a pump on it already attempts to generate another one.
Given that I am a Cistern
and I already have a generated pump,
when it is time to generate a pump,
then the pump shouldn't get generated.

---

**Title: Mechanic attempts to collect a pump from a cistern.**

As a Mechanic,
I want to be able to collect generated pumps (one at a time) from cisterns while standing on them,
so that I can later place them down on the map.

**Scenario 1:** A Mechanic with enough AP and an empty inventory collects a pump from a cistern that has a generated pump.
Given that a cistern has a generated pump
and I am a Mechanic
and I am standing on the cistern
and my inventory is empty
and I have enough AP,
when I attempt to collect the pump,
then the pump should be collected.

**Scenario 2:** A Mechanic without enough AP and an empty inventory attempts to collect a pump from a cistern that has a generated pump.
Given that a cistern has a generated pump
and I am a Mechanic
and I am standing on the cistern
and my inventory is empty
and I don't have enough AP,
when I attempt to collect the pump,
then the pump shouldn't be collected.

**Scenario 3:** A Mechanic with a non-empty inventory attempts to collect a pump from a cistern that has a generated pump.
Given that a cistern has a generated pump
and I am a Mechanic
and I am standing on the cistern
and my inventory is not empty,
when I attempt to collect the pump,
then the pump shouldn't be collected.

**Scenario 4:** A Mechanic with enough AP and an empty inventory attempts to collect a pump from a cistern that doesn't have a generated pump.
Given that a cistern has no generated pumps
and I am a Mechanic
and I am standing on the cistern
and my inventory is empty
and I have enough AP,
when I attempt to collect the pump,
then there is no pump to collect.

---

**Title: Saboteur standing on a pipe attempts to make it slippery**

As a Saboteur,
I want to be able to make pipes slippery,
so that other players will not be able to stay on them.

**Scenario 1:** A Saboteur with enough AP standing on a non-slippery pipe makes it slippery.
Given that a pipe is not slippery
and I am a Saboteur
and I am standing on the pipe
and I have enough AP,
when I attempt to make the pipe slippery,
then the pipe should get slippery.

**Scenario 2:** A Saboteur standing on a slippery pipe attempts to make it slippery again.
Given that a pipe is slippery
and I am a Saboteur
and I am standing on the pipe
and I have enough AP,
when I attempt to make the pipe slippery,
then nothing should happen, as the pipe is already slippery.

**Scenario 3:** A Saboteur without enough AP standing on a pipe attempts to make it slippery.
Given that I am standing on a pipe
and I am a Saboteur
and I don't have enough AP,
when I attempt to make the pipe slippery,
then the pipe shouldn't get slippery.

---

**Title: A pump experiences a breakdown.**

As a Pump,
I want to sometimes break down and become non-functional,
so that I provide an extra challenge for the players.

**Scenario 1:** A functioning pump breaks down.
Given that I am a pump
and I am functional,
when I experience a breakdown,
then I should become non-functional.

**Scenario 2:** A non-functioning pump breaks down.
Given that I am a pump
and I am non-functional,
when I experience a breakdown,
then nothing happens, as I am already broken.

---

 **Title: Player attempts to move from a pipe to one of its neighbouring components.**

As a Player,
I want to be able to move from pipes to one of their neighbouring components.

**Scenario 1:** A non-stuck Player standing on a pipe with enough AP moves to a valid neighbouring component.
Given that I am standing on a pipe
and I am not stuck
and I have enough AP
and I intend to move to an existing neighbour,
when I attempt to move to the selected component,
then the movement should be successful.

**Scenario 2:** A stuck Player standing on a pipe attempts to move to a valid neighbouring component.
Given that I am standing on a pipe
and I am stuck
and I intend to move to an existing neighbour,
when I attempt to move to the selected component,
then the movement should be unsuccessful.

**Scenario 3:** A non-stuck Player standing on a pipe without enough AP attempts to move to a valid neighbouring component.
Given that I am standing on a pipe
and I am not stuck
and I don't have enough AP
and I intend to move to an existing neighbour,
when I attempt to move to the selected component,
then the movement should be unsuccessful.

**Scenario 4:** A Player standing on a pipe attempts to move to a non-existent neighbouring component.
Given that I am standing on a pipe
and I intend to move to a non-existing neighbour,
when I attempt to move to the selected component,
then the movement should be unsuccessful.

## Tesztek implementációjának folyamata 

Elkészült a specifikáció, itt az ideje implementálni. Ehhez az egyik legnépszerűbb BDD implementációs szoftvert, a `Cucumber`-t fogom használni. Java fejlesztőkörnyezetnek az `IntelliJ IDEA`-t választottam.

**

**Cucumber setup-olása**

**Feature file-ok elkészítése**

**Lépésdefiníciós file-ok elkészítése**

**Lépésdefiníciók megírása**

**Tesztek futtatása**

**Eredmények analizálása**

**Tanulság**

---
> *Készítette: Czímer Bende*

# Feltáró tesztelés Dokumentáció

**Tesztelt szoftver:** Szoftver projekt laboron készített játék  
**Tesztelést végezte:** Nagy Gergely

## 1. Cél

A kiválasztott szoftver felhasználói felületének néhány alapvető funkcionalitásának ellenőrzése feltáró tesztelést alkalmazva. A szoftver viselkedését ellenőrzöm manuálisan különféle események hatására, mint például gomb lenyomása, egérkattintás. Nagyobb hangsúlyt fektetek a bonyolultabb logikát megvalósító műveletek tesztelésére. Minden esetben a helyes működést tekintett a teszt vizsgálni.

## 2. Stratégia

A teszteléshez fekete doboz tesztelési technikát alkalmazok. A fekete doboz technika lehetővé teszi a hibák felfedezését és az alkalmazás funkcionalitásának ellenőrzését a felhasználók szemszögéből, javítva ezzel a termék minőségét és a felhasználói elégedettséget.

A program felhasználói felületén megjelenő (a program aktuális állapotát reprezentáló) képek, gombok működését vizsgálom különböző felhasználó interakciók szerint. Ezen interakciók a következők: egér kattintás, billentyűzeten gomb lenyomás.

A teszt minden esetben felhasználói szemszögből közelíti meg magát a szoftvert. Ez azt jelenti, hogy az alapján tesztelem a rendszert, amit a felhasználó lát belőle és amit a felhasználó tehet az adott szituációban.

## 3. Terv

Az alkalmazás felhasználói felületének alapvető funkcionalitásainak ellenőrzése. Hibák, anomáliák felfedezése és dokumentálása.

Tesztelendő funkciók: játék elindítása, játék befejezése, játékosok mozgatása, játékos akciók elvégzése, pontok számítása, nyertes csapat megítélése, pálya műveletek viselkedése.

A funkciókhoz készítek külön-külön teszteseteket az átláthatóság érdekében. Egyes funkciók teszteléséhez képeket is felhasználok.

A tesztelést az egyszerűbb esetektől haladva a bonyolultabbak felé fogom folytatni, biztosítva ezzel a fokozatos és átfogó tesztlefedettséget. A dokumentációban minden tesztesethez részletes leírást és szükség esetén képeket is felhasználok a tesztelési eredmények megőrzéséhez és dokumentálásához.

## 4. Tesztesetek

### 4.1. Néhány alapfunkció

#### 4.1.1. Teszteset: Lyukas cső megjavítása

- **Teszteset célja:**  
A pályán lévő lyukas cső megjavításának helyes működésének ellenőrzése.

- **Teszteset menete:**  
A játékot elindítva, a pályára belépve passzolunk amíg az aktív karakter szerelő nem lesz. Ezzel a karakterrel elnavigálunk egy lyukas csőhöz, majd megjavítjuk azt az ‘f’ gomb megnyomásával. A teszt elvégzése során feltételezzük, hogy a cső elég közel van, így egy körben oda tudunk jutni és meg is tudjuk javítani az adott csövet.

- **Elvárt eredmény:**  
A lyukas cső befoltozódik, ami megjelenik grafikusan is a pályán (tehát frissíti a program), valamint a karakter akciópontja eggyel csökken a javítás után (a mozgást nem számolva).

- **Kapott eredmény:**  
Az elvárt eredménnyel megegyező állapot.

- **Előkészítés:**
![Fix pipe screenshot before](/UITest/images/fix_pipe_before.png)
- **Végállapot:**
![Fix pipe screenshot after](/UITest/images/fix_pipe_after.png)

#### 4.1.2. Teszteset: Pumpa lehelyezése egy alap állapotú cső elemre

- **Teszteset célja:**  
A pályán lévő szerelő karakterrel történő pumpa lehelyezés művelet helyes működésének ellenőrzése.

- **Teszteset menete:**  
A játékot elindítva, a pályára belépve passzolunk, amíg szerelő nem lesz az éppen játszható karakter. Először elnavigálunk egy ciszternára, amin elérhető pumpa és azt felvesszük. Ezután átlépünk egy üres csőre és a ‘g’ gomb megnyomásával letesszük oda a pumpát. FIGYELEM ehhez a teszthez több körre is szükség van, mindig ugyanazzal a karakterrel tevékenykedjünk, mikor elfogy az akciópontunk a többi karakterrel passzoljunk.

- **Elvárt eredmény:**  
Az adott cső ketté szakad és a két elem között megjelenik a pumpa. A karakterünk rákerül a pumpára és csökken eggyel az akciópontjainak száma.

- **Kapott eredmény:**  
Az elvárt eredménnyel megegyező állapot.

#### 4.1.3. Teszteset: Cső átkötése

- **Teszteset célja:**  
A pályán elérhető szerelő karakterrel egy cső átkötésének ellenőrzése.

- **Teszteset menete:**  
A játékot elindítva, a pályára belépve passzolunk, amíg szerelő nem lesz az éppen játszható karakter. Ezután elnavigálunk egy pumpa/ciszterna/forrás elemre (célszerű a legközelebbit választani) és az ‘r’ gomb megnyomásával elindítjuk a cső átkötése műveletet. Először kiválasztjuk, hogy melyik csövet szeretnénk átkötni. Majd azt az elemet, ahová hozzá szeretnénk kötni (ez az elem nem lehet cső, se ugyanaz ahonnan épp kötjük a csövet).

- **Elvárt eredmény:**  
Az átkötött cső egy végpontja megváltozik és azzal az elemmel lesz összeköttetésben, amelyet kiválasztottunk az átkötésnél. Továbbá a karakter akciópontjai csökkennek eggyel.

- **Kapott eredmény:**  
Az elvárt eredménnyel megegyező állapot.

#### 4.1.4. Teszteset: Cső leszerelése

- **Teszteset célja:**  
A pályán elérhető szerelő karakterrel a cső leszerelése művelet elvégzésének ellenőrzése.

- **Teszteset menete:**  
A játékot elindítva, a pályára belépve passzolunk, amíg szerelő nem lesz az éppen játszható karakter. Elnavigálunk egy forrás/ciszterna/pumpa elemre (ha még nem ott áll a karakter), majd a ‘h’ gomb megnyomásával elindítjuk a cső lekötése műveletet. Kiválasztjuk, hogy az adott elem melyik oldaláról kössük le a csövet (olyan oldalt válasszunk, ahol elérhető cső).

- **Elvárt eredmény:**  
A kiválasztott cső mindkét végpontja eltűnik, ezzel jelezve, hogy lekötöttük. Továbbá a karakter akciópontjai csökkennek eggyel (a mozgás költségét nem számolva).

- **Kapott eredmény:**  
Az elvárt eredménnyel megegyező állapot.

- **Előkészítés:**
![Detach pipe screenshot before](/UITest/images/detach_pipe_before.png)
- **Végállapot:**
![Detach pipe screenshot after](/UITest/images/detach_pipe_after.png)
### 4.2 Bonyolultabb funkciók

#### 4.2.1. Teszteset: Műveletvégzés ragadós csövön

- **Teszteset célja:**  
A pályán elérhető valamely karakterrel egy ragacsos cső megjavításának vagy kilyukasztásának vizsgálata.

- **Teszteset menete:**  
a játékot elindítva, a pályára belépve tetszőleges karakterrel elnavigálunk egy cső elemre. Először ragacsossá tesszük, majd a karakter szerepkörének megfelelő műveletet (javítás vagy lyukasztás) végrehajtunk. Egy másik szerepkörben lévő karakterrel rálépünk a szóban forgó csőre és elvégezzük az ellenkező műveletet: ha lyukas volt a cső akkor megjavítjuk, egyébként kilyukasztjuk. Majd abban az esetben, ha még a cső ragacsos megkíséreljük az elmozdulást róla.

- **Elvárt eredmény:**\
A cső kinézete változik a végrehajtott műveleteknek megfelelően: zöld lesz és lyukas vagy javított. Továbbá az odalépő karakterrel (ha még ragacsos mire odaérünk vele) nem tudunk lelépni róla.

- **Kapott eredmény:**\
Az elvárt eredménnyel azonos

#### 4.2.2 Teszteset: Nem létező cső lekötése

- **Teszteset célja:**  
Egy aktív elem (pumpa, forrás, ciszterna) olyan oldaláról egy cső lekötése, melyhez nem csatlakozik cső.

- **Teszteset menete:**  
A játékot elindítva, a pályára belépve passzolunk, amíg szerelő nem lesz az éppen játszható karakter. Elnavigálunk egy forrás/ciszterna/pumpa elemre (ha még nem ott áll a karakter), majd a ’h’ gomb megnyomásával elindítjuk a cső lekötése műveletet. Kiválasztunk egy olyan oldalt, ahol épp nincs csatlakoztatva cső.

- **Elvárt eredmény:**\
A program jelzi a felhasználónak, hogy a kiválasztott oldalon nem található cső, ezért a műveletet nem lehet elvégezni. Továbbá a műveletet végző karakter pontjai nem csökkennek.

- **Kapott eredmény:**\
A felhasználót értesítő szöveg nem a hibát írja le: „A komponens nem aktív!”. Rossz hibaüzenet. Azonban a karakter akciópontja helyesen nem csökken.

- ![Detach nonexistent pipe screenshot](/UITest/images/detach_nonexistent_pipe.png)

#### 4.2.3 Teszteset: Pumpa bemenet, kimenet ugyanaz

- **Teszteset célja:**  
A pályán valamely pumpa beállítása úgy, hogy a kimenete és a bemenete is ugyanaz az oldal legyen.

- **Teszteset menete:**  
A játékot elindítva, a pályára belépve tetszőleges karakterrel elnavigálunk egy pumpa elemre. Majd a ‘v’ gomb megnyomásával elindítjuk a pumpa átállítása műveletet. Válasszuk ki a bemenetnek és a kimenetnek is ugyanazt az irányt.

- **Elvárt eredmény:**\
A játék nem engedi elvégezni a műveletet, mivel értelmetlen, ha egy pumpának ugyanaz a be és kimenete is. Valamilyen formában értesíti erről a felhasználót. Valamint a műveletet végző karakter akciópontjai nem változnak.

- **Kapott eredmény:**\
Sikeresen végrehajtott pumpa átállítás művelet, a program nem értesíti a felhasználót semmilyen formában. Azonban a program működése szempontjából nem okoz további gondot.

#### 4.2.4 Teszteset: Cső átkötése foglalt helyre

- **Teszteset célja:**  
A pályán elérhető szerelő karakterrel egy cső átkötésének ellenőrzése olyan elemre, amely már foglalt.

- **Teszteset menete:**  
A játékot elindítva, a pályára belépve passzolunk, amíg szerelő nem lesz az éppen játszható karakter. Elnavigálunk egy forrás/ciszterna/pumpa elemre (ha még nem ott áll a karakter), majd a ’r’ gomb megnyomásával elindítjuk a cső átkötés műveletet. Kiválasztunk egy oldalt, amelyen elérhető csatlakoztatott cső. Majd egy olyan elemet választunk, melyhez csatlakozik legalább egy cső és azon pontjához csatlakoztatjuk a kiválasztott csövet, amelyik foglalt.

- **Elvárt eredmény:**\
A kiválasztott cső nem kerül elmozdításra, minden marad úgy ahogy volt és a játék értesíti a felhasználót a művelet sikertelenségéről. Valamint a karakter akciópontja nem csökken.

- **Kapott eredmény:**\
Végre lehet hajtani a műveletet. Hatására az adott cső vége átkerül a kiválasztott elem oldalához és az ott lévő cső vége pedig üres lesz. Ezt leszámítva a mozgás és vízáramlás helyesen működik tovább.

- **Előkészítés:**
![Relocate pipe to occupied place screenshot before](/UITest/images/relocate_to_occupied_before.png)

- **Végállapot:**
![Relocate pipe to occupied place screenshot after](/UITest/images/relocate_to_occupied_after.png)

#### 4.2.5 Teszteset: Pumpa lehelyezése lyukas, ragadós csőre

- **Teszteset célja:**  
Azon művelet ellenőrzése, mely során egy ragacsos, lyukas csőre helyezünk le pumpát.

- **Teszteset menete:**  
A játékot elindítva, a pályára belépve passzolunk, amíg szerelő nem lesz az éppen játszható karakter. Felveszünk egy pumpát, majd elnavigálunk egy ragadós, lyukas csőre (ha nincs ilyen akkor egy másik karakterrel előállítjuk azt). Majd végrehajtjuk a pumpa lehelyezése akciót. Teszteljük a létrejött új cső működését, próbáljuk megjavítani és lelépni róla.

- **Elvárt eredmény:**\
Az akció elvégzése után megjelenik még egy az eredeti csővel azonos viselkedésű és kinézetű cső (tehát zöld lesz és lyukas), rálépés után nem tudunk lelépni róla és meg tudjuk javítani.

- **Kapott eredmény:**\
Az elvárt eredménnyel azonos

- **Előkészítés:**
![Place pump on punctured, sticky pipe screenshot before](/UITest/images/place_pump_sticky_before.png)
- **Végállapot:**
![Place pump on punctured, sticky pipe screenshot after](/UITest/images/place_pump_sticky_after.png)

#### 4.2.6 Teszteset: Foglalt csőre történő szökés

- **Teszteset célja:**  
A szökés akció működésének ellenőrzése abban a szituációban, mikor a felhasználó egy foglalt cső elemet választ a szökés célpontjának.

- **Teszteset menete:**  
Létrehozzuk a teszthez szükséges szituációt: egy karakterrel rálépünk egy csőre. Majd passzolás után egy másik karakterrel végrehajtjuk a szökés műveletet. Kiválasztjuk azt a cső elemet, ahova eljuttattuk a másik karaktert.

- **Elvárt eredmény:**\
A művelet sikertelen lesz, mivel egyszerre csak egy karakter tartózkodhat egy csövön. Erről értesíti a program a felhasználót és nem mozdul el a karakter, akciópontjai sem csökkennek.

- **Kapott eredmény:**\
Az elvárt eredménnyel azonos

#### 4.2.7 Teszteset: Ragacsos csőre történő szökés

- **Teszteset célja:**  
A szökés akció működésének ellenőrzése abban a szituációban, mikor a felhasználó egy ragacsos cső elemet választ a szökés célpontjának.

- **Teszteset menete:**  
Létrehozzuk a teszthez szükséges szituációt: egy karakterrel rálépünk egy csőre és ragacsossá tesszük azt, majd lelépünk róla. Ezután passzolással egy másik karaktert irányítva végrehajtjuk a szökés műveletet. Kiválasztjuk azt a cső elemet, amelyet ragadóssá tettük az előzőekben.

- **Elvárt eredmény:**\
A karakter átkerül a kiválasztott csőre és mivel az ragacsos nem tud addig elmozdulni, amíg el nem múlik.

- **Kapott eredmény:**\
Az elvárt eredménnyel azonos

- **Előkészítés:**
![Escape to sticky pipe screenshot before](/UITest/images/escape_sticky_before.png)
- **Végállapot:**
![Escape to sticky pipe screenshot after](/UITest/images/escape_sticky_after.png)

### 4.3 Bonyolultabb funkciók

#### 4.3.1 Teszteset: Cső kilyukasztása, majd megjavítása többször egymás után

- **Teszteset célja:**  
Annak ellenőrzése, hogy egymás után egy körben többször is lyukasztható és javítható-e egy cső.

- **Teszteset menete:**  
A játékot elindítva, a pályára belépve passzolunk, amíg szerelő nem lesz az éppen játszható karakter. Elnavigálunk egy cső elemre (ha még nem ott áll a karakter), majd a cső adott állapotától függően először megjavítjuk vagy kilyukasztjuk azt. Majd megismételjük a művelet ellenkezőjét. Ismételjük felváltva ezeket a lépéseket.

- **Elvárt eredmény:**\
Minden lyukasztás és javítás után a cső állapota frissül, ez megjelenik grafikusan is.

- **Kapott eredmény:**\
Az első javítás-lyukasztás pár akció után nem végezhető az adott csövön több javítás vagy lyukasztás művelet.

- ![Repeat fix and repair actions screenshot](/UITest/images/repeat_repairfix.png)

#### 4.3.2 Teszteset: Ragacsos cső elmúlásának kivárása, majd mozgás rajta

- **Teszteset célja:**  
Egy cső elem ellenőrzése abból a szempontból, hogy ha elmúlik a ragacsossága akkor újra megfelelően működik-e, tehát lehet rajta közlekedni.

- **Teszteset menete:**  
A játékot elindítva, a pályára belépve az aktív karakterrel elnavigálunk egy cső elemre és ragacsossá tesszük azt, és lelépünk róla. Majd passzolunk a többi karakterrel, amíg a cső visszakerül eredeti állapotba. Ezután rá mozgunk és tovább lépünk róla.

- **Elvárt eredmény:**\
A cső kinézete az állapotának megfelelően változik és eredeti állapotba való visszaállás után ismét lehet rajta közlekedni.

- **Kapott eredmény:**\
Az elvárt eredménnyel azonos
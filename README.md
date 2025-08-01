# rock-paper-scissors
Rock Paper Scissors is a coding challenge for a [Senior Java Backend Developer (w/m/d)](https://jobs.netfonds.de/de?id=9d8f36) position at [Netfonds AG](https://www.netfonds.de/)

### Coding challenge
> Die Aufgabe beinhaltet die Implementierung von "Schere, Stein, Paper" (https://de.wikipedia.org/wiki/Schere,_Stein,_Papier) in Java.
> Beim Aufruf des Programms sollen zwei simulierte Spieler „A“ und „B“ 100 mal gegeneinander antreten. Während Spieler „A“ immer das Symbol „Papier“ spielt, soll Spieler „B“ bei jedem Zug per Zufall eines der drei Symbole spielen.
>
> Das Ganze soll als ein einfaches Java Konsolen Programm umgesetzt werden, welches ohne Parameter ausgeführt wird und sich nach den 100 Spielzügen beendet.
> Das Endergebnis soll in der Konsole pro Spieler ausgegeben werden (wie oft gewonnen/verloren/unentschieden).

### Build
The project is build with gradle.  
To build the project the **build** task needs to be executed:
`gradle build`

### Run
Once build the project can be started with gradle.  
To start the project the **run** task needs to be executed:
`gradle run`

## Notes
- The number of rounds of the paper, rock, scissors game can be adjusted in the `Main`class.
- There is a second game mode: paper, rock, scissors, spock, lizard which can be activated if the builder function
in the main class is changed to `Game.Builder.newLizardHandShapeGame()`. The classic game is started with the builder 
function `Game.Builder.newClassicHandShapeGame()`
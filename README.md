# IQ Puzzler Pro Solver
<div>
 <img src="https://github.com/abranhe/programming-languages-logos/blob/master/src/java/java_32x32.png"/>
 <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white" />
</div>

IQ Puzzler Pro is a puzzle board game created by Smart Games https://www.smartgamesusa.com/one-player-games/iq-puzzler-pro. 
The end goal of this game is to fill the board with every piece given. This application uses a brute force approach in finding the solution.
Further details are elaborated in the docs folder

## Project Directory
```
Tucil1_13523006/
├── bin/
│   ├── Tucil_13523006.jar
│   └── iqsolver_setup.exe : One-click installer for the GUI App version
├── docs/ : Extra documentation
├── iq_puzzle_solver/ : GUI App source code
│   ├── src/main
│   │   ├── java/kirisame/
│   │   │   ├── iq_puzzle/ : modified version of ..src/
│   │   │   ├── App.java
│   │   │   ├── Launcher.java
│   │   │   ├── PrimaryController.java
│   │   ├── resources/kirisame/ : Frontend GUI resources
│   ├── target/
│   └── pom.xml
├── src/ : CLI program source code
├── test/ : Testing results
├── README.md
```
## Requirements
None if installing with the installer, otherwise install javaFX 23 to run the GUI version. This program was made with JDK 23.

## Running the Program
For the main GUI version, either directly setup using the installer or run the JAR file. For the CLI version, compile everything in src/ and run Main.java

## Program I/O
### Valid File Input
```
N M P
BoardType
<Piece 1>
<Piece 2>
...
<Piece P>
```
where: 
- N,M,P corresponds to Rows,Columns, and Piece number,
- BoardType : only "DEFAULT" is supported currently,
- Piece : Piece shape using capital latin letters i.e. A-Z, must be unique and continuous

### Outputs
The GUI version allows image or text output of the result while the CLI only allows printing to console
## Author
| Name | NIM | Class |
|------|---|---|
| William Andrian Dharma T | 13523006 | K01 |  


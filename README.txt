This game was a group coursework project created by 8 people with varying contribution. We used gitHub for collaboration but this is a reupload with names removed at the request of a group member.

Aim of the game:
Remove rats from the board using items and stop them from multiplying. Too many rats causes a loss.

Main features (non exhaustive):
- Data persistance between instances (done using profiles).
- Highscores are saved and ranked (Similar to an arcade machine).
- Lots of unique items and level designs.
- Minimap displaying the current level.
- Music.

NOTES:
- We used Java 17, JavaFX 17, and Gson 2.8.2.
- We've included the Gson dependency within this submission (gson-2.8.2.jar).
- We assume JavaFX has already been installed by the user (perhaps by following https://gluonhq.com/products/javafx/).

Launch by self-compiling the source:
1. Create a new plain Java project within your chosen IDE.
2. Import JavaFX and Gson as a dependency.
3. Copy all files and directories from 'source' into the project's source folder (usually 'src').
4. Configure VM arguments within your chosen IDE to include:
   --module-path {PATH} --add-modules=ALL-MODULE-PATH
   (where {PATH} is your JavaFX 17 SDK installation's 'lib' directory)
5. Run the project within your IDE.

Remember, you'll need to create at least one profile before being able to play any levels!

![alt text](https://i.ibb.co/TKn87SW/MainPage.png)
![alt text](https://i.ibb.co/f8Nd0sx/Profile-Page.png)
![alt text](https://i.ibb.co/D5VH3HX/Level1.png)
![alt text](https://i.ibb.co/Qb2wrJW/Level2.png)

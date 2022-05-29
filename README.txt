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

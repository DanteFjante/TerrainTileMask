# TileMaker
A terrain tile mask generator for 3x3 terrain tile sections for game engines.


Either compile and run the java source code to generate the tiles in the folder where you have placed the java file
or if just download the zip folder containing 64x64 mask of terrain tiles. 

in commandline run these two lines in the folder where the GenerateMasks.java file is located (if you have jdk installed)
javac .\GenerateMasks.java
java -cp .\ GenerateMasks

or if you only have normal java you can run this line from the folder where you downloaded the GenerateMasks.class file.
java -cp .\ GenerateMasks

You can also add arguments to the java file like this to create 16x16 textures with 4 thickness of side space next to roads and including mirrored versions as well.
java -cp .\ GenerateMasks 16 16 4 4 true 

By default all mirrored masks are removed as well as rotated masks.
default command options are:
java -cp .\ GenerateMasks 64 64 16 16 false


You now have all the possible tiles that a road or other tile can intersect with.

In the future I might add a unity script and shader for making use of these masks in unity as well as 
include options to load in images directly to generate ready masks from this script.

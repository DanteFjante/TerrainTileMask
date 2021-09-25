import java.util.Arrays;
import java.util.Scanner;

public class GenerateMasks {

    public static MaskGenerator generator;
    public static int width = 0, height = 0, sideWidth = 0, sideHeight = 0;
    public static boolean includeMirrors;

    /**
     * Args:
     * 1. Width (default: 64)
     * 2. Height (default: 64)
     * 3. Width of sides (default: 16 or width / 4)
     * 4. Height of sides (default: 16 or width / 4)
     * 5. Generate Mirrors (default: false) Set to true to give generate mirrored masks too.
     *
     * Default: The sides are 16 pixels thick and the middle is 32 pixels thick.
     */
    public static void main(String[] args) {



        System.out.print("Width: ");
        System.out.print("\nHeight: ");
        System.out.print("\nSideWidth: ");
        System.out.print("\nSideHeight: ");
        System.out.print("\nInclude Mirrored Masks(y/n): ");
        System.out.println("\nWant to add side texture? (y/n)");
        System.out.println("\nAdd path to texture: ");
        System.out.println("\nWant to add middle texture? (y/n)");
        System.out.println("\nAdd path to texture: ");





        try {

            if (args.length >= 1) {
                width = Integer.parseInt(args[0]);
            }
            sideWidth = width / 4;
            if (args.length >= 2) {
                height = Integer.parseInt(args[1]);
            }
            sideHeight = height / 4;
            if (args.length >= 3) {
                sideWidth = Integer.parseInt(args[2]);
            }
            if (args.length >= 4) {
                sideHeight = Integer.parseInt(args[3]);
            }

            if (args.length >= 5) {
                if(args[4].equalsIgnoreCase("true"))
                    includeMirrors = true;
            }

        } catch (NumberFormatException e) {
            System.out.println("Could not understand args\n" + Arrays.toString(e.getStackTrace()));
            return;
        }

        if(sideWidth <= 0 || sideHeight <= 0 || height < 4 || width < 4 || sideHeight * 2 >= height || sideWidth * 2 >= width)
            throw new IllegalArgumentException("All 9 sections needs to have at least 1 pixel thickness.");

        generator = new MaskGenerator(width, height, sideWidth, sideHeight);


    }


}

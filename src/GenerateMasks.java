import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateMasks {

    private static int width = 64;
    private static int height = 64;

    private static int sideWidth = 16;
    private static int sideHeight = 16;

    private static boolean GenerateMirrors;

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

        Set<String> transforms = new HashSet<String>();

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
                width = Integer.parseInt(args[2]);
            }
            if (args.length >= 4) {
                width = Integer.parseInt(args[3]);
            }

            if (args.length >= 5) {

            }

        } catch (NumberFormatException e) {
            System.out.println("Could not understand args\n" + Arrays.toString(e.getStackTrace()));
            return;
        }

        if(sideWidth >= width || sideWidth <= 0 || sideHeight >= height || sideHeight <= 0 || height < 4 || width < 4 || sideHeight * 2 >= height || sideWidth * 2 >= width)
            throw new IllegalArgumentException("All 9 sections needs to have at least 1 pixel thickness.");



        for (int i = 0; i < 512; i++) {
            boolean[][] mask = createMask(i);
            Collection<String> strings = createTransforms(mask, true);
            if(transforms.stream().anyMatch(strings::contains))
                continue;
            BufferedImage image = createImage(mask);
            File file = new File("Mask_"+i+".png");
            transforms.addAll(strings);
            try {
                ImageIO.write(image,"png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Collection<String> createTransforms(boolean[][] mask, boolean includeMirror) {
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        StringBuilder s3 = new StringBuilder();
        StringBuilder s4 = new StringBuilder();
        StringBuilder s5 = new StringBuilder();
        StringBuilder s6 = new StringBuilder();
        StringBuilder s7 = new StringBuilder();
        StringBuilder s8 = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                s1.append(mask[i][j] ? 1 : 0);
                s3.append(mask[2-i][2-j] ? 1 : 0);
                s5.append(mask[2-i][j] ? 1 : 0);
                s7.append(mask[i][2-j] ? 1 : 0);
                s4.append(mask[j][2-i] ? 1 : 0);
                s2.append(mask[2-j][i] ? 1 : 0);
                s6.append(mask[2-j][2-i] ? 1 : 0);
                s8.append(mask[j][i] ? 1 : 0);
            }
        }
        if(includeMirror)
            return Stream.of(s1.toString(), s2.toString(), s3.toString(), s4.toString(), s5.toString(), s6.toString(), s7.toString(), s8.toString()).collect(Collectors.toList());
        return Stream.of(s1.toString(), s2.toString(), s3.toString(), s4.toString()).collect(Collectors.toList());
    }

    public static BufferedImage createImage(boolean[][] mask) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < width; i++ ) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, getColor(i, j, mask));
            }
        }
        return image;
    }

    public static int getColor(int x, int y, boolean[][] mask) {

        int color = 0;

        int i = 0;
        if(x >= sideWidth)
            i++;
        if(x >= width - sideWidth)
            i++;

        int j = 0;
        if(y >= sideWidth)
            j++;
        if(y >= width - sideWidth)
            j++;

        if(mask[i][j])
            return Integer.MAX_VALUE;
        return 0;
    }

    public static boolean[][] createMask(int permutation) {

        boolean[][] mask = new boolean[3][3];

        String binary = Integer.toBinaryString(permutation);

        for (int i = 0; i < 9 && i < binary.length(); i++) {
            mask[i/3][i%3] = binary.charAt(binary.length() - i - 1) == '1';
        }
        return mask;
    }
}

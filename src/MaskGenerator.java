import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MaskGenerator {

    private int width = 64;
    private int height = 64;

    private int sideWidth = 16;
    private int sideHeight = 16;


    public MaskGenerator(int width, int height, int sideWidth, int sideHeight) {

        this.width = width;
        this.height = height;

        this.sideWidth = sideWidth;
        this.sideHeight = sideHeight;

    }

    public void CreateMasks(boolean includeMirrors) {
        Set<String> transforms = new HashSet<String>();

        for (int i = 0; i < 512; i++) {
            boolean[][] mask = createMask(i);
            Collection<String> strings = createTransforms(mask, !includeMirrors);
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

    public int getColor(int x, int y, boolean[][] mask) {

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

    public BufferedImage createImage(boolean[][] mask) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < width; i++ ) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, getColor(i, j, mask));
            }
        }
        return image;
    }

    public static Collection<String> createTransforms(boolean[][] mask, boolean filterMirror) {
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
        if(filterMirror)
            return Stream.of(s1.toString(), s2.toString(), s3.toString(), s4.toString(), s5.toString(), s6.toString(), s7.toString(), s8.toString()).collect(Collectors.toList());
        return Stream.of(s1.toString(), s2.toString(), s3.toString(), s4.toString()).collect(Collectors.toList());
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

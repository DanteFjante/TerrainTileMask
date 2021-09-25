import java.io.File;
import java.util.Scanner;

public class CLITool {

    public static int promptInt(String question) {
        return promptInt(question, 0);
    }

    public static int promptInt(String question, int defaultValue) {
        return promptInt(question, Integer.MIN_VALUE, Integer.MAX_VALUE, defaultValue);
    }

    public static int promptInt(String question, int minValue, int maxValue, int defaultValue) {

        ask(question);

        String read = "";

        int tries = 0;
        int i = defaultValue;

        do {
            read = read();

            if (read.isBlank()) {
                write("Default Value decided");
                break;
            }

            try {
                i = Integer.parseInt(read);
                if (i > maxValue || i < minValue) {
                    writeError("Value has to be more than " + minValue + " and less than " + maxValue);
                    i = defaultValue;
                } else
                    tries = 3;
            } catch (NumberFormatException e) {
                writeError("Value has to be an integer");
            }
        } while (tries++ < 3);
        return i;
    }

    public static boolean promptBoolean(String question) {
        return promptBoolean(question, false);
    }

    public static boolean promptBoolean(String question, boolean defaultValue) {

        ask(question);

        String read = "";
        int tries = 3;
        boolean result = defaultValue;

        do {
            read = readNext();

            if (read.isBlank()) {
                write("Default Value decided");
                break;
            }

            if(read.toLowerCase().charAt(0) == 'y')
                return true;
            else if(read.toLowerCase().charAt(0) == 'n')
                return false;
            else
                writeError("Must be either 'yes' or 'no'");

        } while (tries-- > 0);

        return result;
    }


    //@Todo: Implement!
    public static File promptFile(String question, boolean createIfNotExists) {
        ask(question);

        String read = "";
        int tries = 3;

        do {
            read = readNext();

            if (read.isBlank()) {
                write("Default Value decided");
                break;
            }

            if(read.toLowerCase().charAt(0) == 'y')
                return null;
            else if(read.toLowerCase().charAt(0) == 'n')
                return null;
            else
                writeError("Must be either 'yes' or 'no'");

        } while (tries-- > 0);

        return null;
    }

    private static String read() {
        String read = "";
        Scanner scanner = new Scanner(System.in);
        read = scanner.nextLine();
        scanner.close();
        return read;
    }

    private static String readNext() {
        String read = "";
        Scanner scanner = new Scanner(System.in);
        read += scanner.next();
        scanner.close();
        return read;
    }


    private static void write(Object print) {
        System.out.print("\n" + print + "");
    }

    private static void ask(Object print) {
        write(print + ": ");
    }

    private static void writeError(Object print) {
        System.out.println("\nError: " + print);
    }


}

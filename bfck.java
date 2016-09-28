
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class bfck {
    private List<Character> separators = new ArrayList<>(Arrays.asList(' ', '\n', '\t'));
    private List<Character> keywords = new ArrayList<>(Arrays.asList('+', '-', '<', '>', '.', ',', '[', ']'));

    private byte[] M = new byte[30000];
    private int p = 0;
    private int i = 0;

    private bfck() { initialize(); }

    private int execute(String source) {
        initialize();
        int length = source.length();

        while (i < length) {
            switch (getKeyword(source)) {
                case '+':
                    if (!incr()) return 1;
                    break;
                case '-':
                    if (!decr()) return 1;
                    break;
                case '<':
                    if (!left()) return 2;
                    break;
                case '>':
                    if (!right()) return 2;
                    break;
                case '.':
                    out();
                    break;
                case ',':
                    break;
                case '[':
                    break;
                case ']':
                    break;
                case 0:
                    break;
                default:
                    display("Error in file at " + i);
            }
            i++;
        }
        return 0;
    }

    private char getKeyword(String source) {
        StringBuilder keyword = new StringBuilder();

        while (i < source.length()) {
            char ascii = source.charAt(i);

            if (keywords.contains(ascii)) {
                if (keyword.length() == 0) return ascii;
                else {
                    char key = longToShort(keyword.toString());
                    if (key == 0) return ascii;
                    else {
                        i--; // support mixed syntax without separator
                        return key;
                    }
                }
            }
            if (separators.contains(ascii)) {
                if (keyword.length() != 0) {
                    return longToShort(keyword.toString());
                }
            }
            else keyword.append(ascii);

            i++;
        }
        return longToShort(keyword.toString());
    }

    private char longToShort(String keyword) {
        switch (keyword) {
            case "INCR":
                return '+';
            case "DECR":
                return '-';
            case "LEFT":
                return '<';
            case "RIGHT":
                return '>';
            case "OUT":
                return '.';
            case "IN":
                break;
            case "JUMP":
                return '[';
            case "BACK":
                return ']';
        }
        return 0;
    }

    private boolean incr() {
        if (M[p] < 127) { M[p]++; return true; }
        else return false;
    }

    private boolean decr() {
        if (M[p] > -128) { M[p]--; return true; }
        else return false;
    }

    private boolean left() {
        if (p > 0) { p--; return true; }
        else return false;
    }

    private boolean right() {
        if (p < 29999) { p++; return true; }
        else return false;
    }

    private void in() {
        Scanner scanner = new Scanner(System.in);
        M[p] = (byte) (scanner.nextInt() - 128);
    }

    private void out() {
        display(String.valueOf(getCell(p)));
    }

    private void initialize() { i = 0; p = 0; Arrays.fill(M, (byte) -128);}

    private int getCell(int pointer) { return M[pointer] + 128; }

    private void display(String string) {
        System.out.println(string);
    }

    private boolean exit(int code) {
        switch (code) {
            case 0:
                display("Process finished with exit code 0");
                break;
            case 1:
                display("Process finished with exit code 1");
                // display("Overflow or Underflow at pointer " + p + ", exception reached at position " + i);
                break;
            case 2:
                display("Process finished with exit code 2");
                // display("Pointer " + p + " is out of memory, exception reached at position " + i);
                break;
        }
        return true;
    }

    private String print(String source) {
        int exitCode = execute(source);
        StringBuilder output = new StringBuilder();

        for (int j=0; j<30000; j++) {
            if (M[j] != -128) {
                String temp = "C" + j + ": " + getCell(j);
                display(temp);
                output.append(temp);
                output.append('\n');
            }
        }
        exit(exitCode);
        return output.toString();
    }

    private String rewrite(String source) {
        initialize();
        StringBuilder shortSyntax = new StringBuilder();
        char ascii;
        int length = source.length();
        while (i < length) {
            ascii = source.charAt(i);
            if (keywords.contains(ascii)) shortSyntax.append(ascii);
            else shortSyntax.append(getKeyword(source));
            i++;
        }
        display(shortSyntax.toString());
        return shortSyntax.toString();
    }

    private BufferedImage translate(String source) {
        String rewrite = rewrite(source);
        initialize();
        int length = rewrite.length();
        int size = (int) Math.ceil(Math.sqrt((double) length));
        int red;
        int green;
        int blue;
        int[] rgbArray;
        BufferedImage image = new BufferedImage(3 * size, 3 * size, BufferedImage.TYPE_INT_RGB);
        for (int j=0; j<length; j++) {
            switch (rewrite.charAt(j)) {
                case '+':
                    red = 255;
                    green = 255;
                    blue = 255;
                    rgbArray = new int[9];
                    Arrays.fill(rgbArray, (red << 16) | (green << 8) | blue);
                    image.setRGB(3*(j%size), 3*(j/size), 3, 3, rgbArray, 0, 3);
                    break;
                case '-':
                    red = 75;
                    green = 0;
                    blue = 130;
                    rgbArray = new int[9];
                    Arrays.fill(rgbArray, (red << 16) | (green << 8) | blue);
                    image.setRGB(3*(j%size), 3*(j/size), 3, 3, rgbArray, 0, 3);
                    break;
                case '<':
                    red = 148;
                    green = 0;
                    blue = 211;
                    rgbArray = new int[9];
                    Arrays.fill(rgbArray, (red << 16) | (green << 8) | blue);
                    image.setRGB(3*(j%size), 3*(j/size), 3, 3, rgbArray, 0, 3);
                    break;
                case '>':
                    red = 0;
                    green = 0;
                    blue = 255;
                    rgbArray = new int[9];
                    Arrays.fill(rgbArray, (red << 16) | (green << 8) | blue);
                    image.setRGB(3*(j%size), 3*(j/size), 3, 3, rgbArray, 0, 3);
                    break;
                case '.':
                    break;
                case ',':
                    break;
                case '[':
                    break;
                case ']':
                    break;
                case 0:
                    break;
            }
        }

        return image;
    }

    private static void help() {
        System.out.println("Syntax : java Brainfuck filename.bf or java Brainfuck filename.bmp");
    }

    public static void main(String... args) {
        if (args.length == 0) help();
        else {
            List<String> flags = new ArrayList<>(Arrays.asList("-p", "--rewrite", "--translate", "-i", "-o", "--check"));
            List<String> options = new ArrayList<>();
            File file = null;

            for (String arg : args) {
                if (flags.contains(arg)) options.add(arg);
                else if (arg.endsWith(".bf") || arg.endsWith(".bmp")) file = new File(arg);
                else System.out.println("The command \"" + arg + "\" is not recognized");
            }

            if (file != null) {
                try {
                    Scanner scanner = new Scanner(file);
                    StringBuilder builder = new StringBuilder((int) file.length());
                    while(scanner.hasNextLine()) {
                        builder.append(scanner.nextLine());
                        builder.append('\n');
                    }
                    bfck brainfuck = new bfck();
                    String source = builder.toString();
                    for (String option : options) {
                        switch (option) {
                            case "-p":
                                brainfuck.print(source);
                                break;
                            case "--rewrite":
                                brainfuck.rewrite(source);
                                break;
                            case "--translate":
                                BufferedImage image = brainfuck.translate(source);
                                try {
                                    ImageIO.write(image, "BMP", new File("image.bmp"));
                                } catch (java.io.IOException exception) {
                                    System.out.println("image error");
                                }
                                break;
                            case "-i":

                                break;
                            case "-o":
                                break;
                            case "--check":
                                break;

                        }
                    }
                } catch (FileNotFoundException exception) {
                    System.out.println("The file \"" + file.getName() + "\" does not exist");
                }
            }
        }

    }
}


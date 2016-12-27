package macro;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Le pre-processeur remplacant les macros par leurs valeurs.
 * <p>
 * <P>Exemple d'une macro
 * <pre>
 * // L'operateur MACRO definissant une macro est insensible a la casse
 * // Le nom de la macro ainsi que les parametres sont sensible a la casse
 * // Le corps de la macro doit etre indentee
 *
 * MACRO NOM_DE_LA_MACRO parametre1 parametre2 ... parametreN
 *      INSTRUCTION_1
 *      INSTRUCTIONS_2
 *      ...
 *      INSTRUCTIONS_N
 * // Retour a la ligne vide obligatoire
 *
 * // Pour appeler une macro
 * NOM_DE_LA_MACRO valeurs_parametre1 valeurs_parametre2 ... valeurs_parametreN
 * // Les valeurs des parametres doivent etre des valeurs constantes (pas de variables ni d'expressions)
 * </pre>
 *
 * @author Alexandre Clement
 * @since 24/12/2016.
 */
public class MacroBuilder
{
    /**
     * Une tabulation equivaut a {@value} espaces.
     */
    static final int INDENTATION = 4;
    /**
     * Le pattern definissant une macro. i.e MACRO NOM_DE_LA_MACRO parametre1 parametre2 ... parametreN
     */
    private final Pattern definition;
    /**
     * Pattern definissant le corps d'une macro. (doit etre indentee et suivis d'un retour a la ligne)
     */
    private final Pattern sequence;
    /**
     * Le fichier d'entree.
     */
    private RandomAccessFile file;
    /**
     * Le fichier de sortie.
     */
    private PrintWriter writer;
    /**
     * Le fichier temporaire dans lequel on ecrit.
     */
    private File tmp;

    /**
     * Construit le pre-processeur.
     *
     * @param filename le noms du fichier
     * @throws IOException si le fichier n'est pas trouver
     */
    public MacroBuilder(String filename) throws IOException
    {
        tmp = File.createTempFile(filename, ".tmp", new File("target"));
        tmp.deleteOnExit();
        writer = new PrintWriter(new BufferedWriter(new FileWriter(tmp)));
        file = new RandomAccessFile(filename, "r");
        definition = Pattern.compile("(?i)^[ \\t]*MACRO[ \\t]+(\\w+)" + Macro.MATCH_ALL);
        sequence = Pattern.compile("^(?:\\t| {" + INDENTATION + "})+" + Macro.MATCH_ALL);
    }

    /**
     * Remplace les macros par leurs valeurs.
     *
     * @return le fichier temporaires apres remplacement des macros
     * @throws IOException si le fichier n'est pas trouver
     * @see Macro
     */
    public File build() throws IOException
    {
        Deque<Macro> macros = new ArrayDeque<>();
        Matcher defMatcher;

        for (String line = file.readLine(); line != null; line = file.readLine())
        {
            defMatcher = definition.matcher(line);
            if (defMatcher.find())
                macros.push(createMacro(defMatcher));
            else
                writer.write(applyMacroOn(macros, line) + '\n');
        }
        writer.flush();
        writer.close();
        return tmp;
    }

    private String applyMacroOn(Deque<Macro> macros, String line)
    {
        String temp;
        temp = line;
        for (Macro macro : macros)
        {
            temp = macro.match(temp);
        }
        return temp;
    }

    private Macro createMacro(Matcher defMatcher) throws IOException
    {
        StringBuilder body;
        String line;
        Matcher seqMatcher;
        body = new StringBuilder();
        for (line = file.readLine(); line != null; line = file.readLine())
        {
            seqMatcher = sequence.matcher(line);
            if (!seqMatcher.matches())
                break;
            body.append(seqMatcher.group()).append('\n');
        }
        return new Macro(defMatcher.group(1), defMatcher.group(2), body + "\n ");
    }
}

package macro;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Le pré-processeur remplaçant les macros par leurs valeurs
 * <p>
 * Exemple d'une macro
 * <p>
 * // L'opérateur MACRO définissant une macro est insensible à la casse
 * // Le nom de la macro ainsi que les paramètres sont sensible à la casse
 * // Le corps de la macro doit être indentée
 * <p>
 * MACRO NOM_DE_LA_MACRO paramètre1 paramètre2 ... paramètreN
 *      INSTRUCTION_1
 *      INSTRUCTIONS_2
 *      ...
 *      INSTRUCTIONS_N
 * // Retour à la ligne vide obligatoire
 * <p>
 * // Pour appeler une macro
 * NOM_DE_LA_MACRO valeurs_paramètre1 valeurs_paramètre2 ... valeurs_paramètreN
 * // Les valeurs des paramètres doivent être des valeurs constantes (pas de variables et d'expressions)
 *
 * @author Alexandre Clement
 * @since 24/12/2016.
 */
public class MacroBuilder
{
    /**
     * Une tabulation équivaut à INDENTATION espaces
     */
    static final int INDENTATION = 4;
    /**
     * Le pattern définissant une macro i.e MACRO NOM_DE_LA_MACRO paramètre1 paramètre2 ... paramètreN
     */
    private final Pattern definition;
    /**
     * Pattern définissant le corps d'une macro (doit être indentée et suivis d'un retour à la ligne)
     */
    private final Pattern sequence;
    /**
     * Le fichier d'entrée
     */
    private RandomAccessFile file;
    /**
     * Le fichier de sortie
     */
    private PrintWriter writer;
    /**
     * Le fichier temporaire dans lequel on écrit
     */
    private File tmp;

    /**
     * Construit le pré-processeur
     *
     * @param filename le noms du fichier
     * @throws IOException si le fichier n'est pas trouver
     */
    public MacroBuilder(String filename) throws IOException
    {
        tmp = File.createTempFile(filename, ".tmp");
        writer = new PrintWriter(new BufferedWriter(new FileWriter(tmp)));
        file = new RandomAccessFile(filename, "r");
        definition = Pattern.compile("(?i)^[ \\t]*MACRO[ \\t]+(\\w+)([\\w \\t]*)$");
        sequence = Pattern.compile("^(?:\\t| {" + INDENTATION + "})+" + Macro.MATCH_ALL);
    }

    /**
     * Remplace les macros par leurs valeurs
     *
     * @return le fichier temporaires après remplacement des macros
     * @see Macro
     */
    public File build() throws IOException
    {
        Deque<Macro> macros = new ArrayDeque<>();
        Matcher defMatcher;
        Matcher seqMatcher;
        StringBuilder body;
        String temp;

        for (String line = file.readLine(); line != null; line = file.readLine())
        {
            defMatcher = definition.matcher(line);
            if (defMatcher.matches())
            {
                body = new StringBuilder();
                for (seqMatcher = sequence.matcher(file.readLine()); seqMatcher.matches(); seqMatcher = sequence.matcher(file.readLine()))
                {
                    body.append(seqMatcher.group()).append('\n');
                }
                macros.push(new Macro(defMatcher.group(1), defMatcher.group(2), body + "\n "));
            }
            else
            {
                temp = line;
                for (Macro macro : macros)
                {
                    temp = macro.match(temp);
                }
                writer.write(temp + '\n');
            }
        }
        writer.flush();
        writer.close();
        return tmp;
    }
}
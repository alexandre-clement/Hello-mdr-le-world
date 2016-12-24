package probe;

import core.ExecutionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandre Clement
 *         Created the 07/12/2016.
 *         <p>
 *         La sonde contenant toutes les métriques
 */
public class Probe
{
    /**
     * Liste des métriques
     */
    private List<Meter> probes = new ArrayList<>();

    /**
     * Ajoute une métrique à la sonde
     *
     * @param meter la métrique à ajoutée
     */
    public void addMeter(Meter meter)
    {
        probes.add(meter);
    }

    /**
     * Initialise toutes les métriques
     */
    public void initialize()
    {
        probes.forEach(Meter::initialize);
    }

    /**
     * Récupère une image du contexte et l'injecte dans les métriques
     *
     * @param executionContext le contexte d'exécution
     */
    public void acknowledge(ExecutionContext executionContext)
    {
        probes.forEach(probe -> probe.acknowledge(executionContext));
    }

    /**
     * Récupère le résultat de toutes les métriques
     */
    public void getResult()
    {
        probes.forEach(Meter::getResult);
    }
}

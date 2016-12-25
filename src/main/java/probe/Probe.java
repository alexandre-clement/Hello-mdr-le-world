package probe;

import core.ExecutionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * La sonde contenant toutes les metriques.
 *
 * @author Alexandre Clement
 * @see Meter
 * @since 07/12/2016.
 */
public class Probe
{
    /**
     * Liste des metriques.
     */
    private List<Meter> probes = new ArrayList<>();

    /**
     * Ajoute une metrique a la sonde.
     *
     * @param meter la metrique a ajoutee
     */
    public void addMeter(Meter meter)
    {
        probes.add(meter);
    }

    /**
     * Initialise toutes les metriques.
     */
    public void initialize()
    {
        probes.forEach(Meter::initialize);
    }

    /**
     * Recupere une image du contexte et l'injecte dans les metriques.
     *
     * @param executionContext le contexte d'execution
     */
    public void acknowledge(ExecutionContext executionContext)
    {
        probes.forEach(probe -> probe.acknowledge(executionContext));
    }

    /**
     * Recupere le resultat de toutes les metriques.
     */
    public void getResult()
    {
        probes.forEach(Meter::getResult);
    }
}

package probe;

import core.ExecutionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandre Clement
 *         Created the 07/12/2016.
 */
public class Probe
{
    private List<Meter> probes = new ArrayList<>();

    public void addMeter(Meter meter)
    {
        probes.add(meter);
    }

    public void initialize()
    {
        probes.forEach(Meter::initialize);
    }

    public void acknowledge(ExecutionContext executionContext)
    {
        probes.forEach(probe -> probe.acknowledge(executionContext));
    }

    public void getResult()
    {
        probes.forEach(Meter::getResult);
    }
}

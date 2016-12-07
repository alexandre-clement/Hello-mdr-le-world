package core;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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

    public void acknowledge(ExecutionContext executionContext)
    {
        probes.forEach(probe -> probe.acknowledge(executionContext));
    }

    public void getResult()
    {
        probes.forEach(Meter::getResult);
    }

    private interface Meter
    {
        void getResult();

        /**
         * @param executionContext the current execution context of the program
         */
        void acknowledge(ExecutionContext executionContext);
    }

    /**
     * Calculate the metrics of the program and print them out using the method getResult
     */
    public static class Metrics implements Meter
    {
        private long start;
        long exec_move = 0;
        long data_move = 0;
        long data_write = 0;
        long data_read = 0;
        private long length;

        public Metrics(long length)
        {
            this.length = length;
            exec_move = 0;
            data_move = 0;
            data_write = 0;
            data_read = 0;
            start = System.currentTimeMillis();
        }

        /**
         * Print out the metrics
         */
        @Override
        public void getResult()
        {
            String metrics = "\nPROG_SIZE: " + length + '\n';
            metrics += "EXEC_TIME: " + (System.currentTimeMillis() - start) + " ms\n";
            metrics += "EXEC_MOVE: " + exec_move + '\n';
            metrics += "DATA_MOVE: " + data_move + '\n';
            metrics += "DATA_WRITE: " + data_write + '\n';
            metrics += "DATA_READ: " + data_read + '\n';
            System.out.println(metrics);
        }

        /**
         * calculate the metrics with the current instance
         * @param executionContext the current execution context of the program
         */
        @Override
        public void acknowledge(ExecutionContext executionContext)
        {
            exec_move += 1;
            switch (executionContext.program[executionContext.instruction].getType())
            {
                case DATA_WRITE:
                    data_write += 1;
                    break;
                case DATA_READ:
                    data_read += 1;
                    break;
                case DATA_MOVE:
                    data_move += 1;
                    break;
            }
        }
    }

    /**
     * Create the trace of the file in a p.log file
     */
    public static class Trace implements Meter
    {
        private String log;
        private long step;
        private Writer writer;

        public Trace(String filename)
        {
            step = 0;

            try
            {
                writer = new FileWriter(filename + ".log");
            }
            catch (IOException e)
            {
                System.err.println("This should never happen");
            }
        }

        /**
         * Close the logfile
         */
        @Override
        public void getResult()
        {
            try
            {
                writer.close();
            }
            catch (IOException e)
            {
                System.err.println("This should never happen");
            }
        }

        /**
         * Add the context in log
         *
         * @param executionContext the current execution context of the program
         */
        @Override
        public void acknowledge(ExecutionContext executionContext)
        {
            log = String.format("Execution step: %10d | Execution pointer: %10d | Data pointer: %10d | %s%n", ++step, executionContext.instruction, executionContext.pointer, executionContext.getMemorySnapshot());
            try
            {
                writer.write(log);
                writer.flush();
            }
            catch (IOException e)
            {
                System.err.println("This should never happen");
            }
        }
    }
}

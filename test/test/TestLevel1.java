package test;

import interpreter.Interpreter;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;//libraries from maven "system-rules"

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * test Level 1 with the bf files
 *
 * exitcode check tests should be running separately
 * outputStream check doesn't work, perhaps because of called after system.exit
 */
public class TestLevel1 {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void TestS0() {
        exit.expectSystemExitWithStatus(0);//expecting Exit code 0
        new Interpreter("-p", "test\\empty.bf").buildSystem();
    }

    @Test
    public void TestS1() {
        exit.expectSystemExitWithStatus(0);//expecting Exit code 0
        new Interpreter("-p", "test\\incrC0by255.bf").buildSystem();//INCR * 255
        assertEquals("C0:255-seg", outContent.toString());// doesn't work;
    }
    @Test
    public void TestS1overFlow() {
        exit.expectSystemExitWithStatus(1);//expecting Exit code 1
        new Interpreter("-p", "test\\incrC0by256.bf").buildSystem();//INCR * 256
    }
    @Test
    public void TestS2() {
        exit.expectSystemExitWithStatus(0);//expecting Exit code 0
        new Interpreter("-p", "test\\incrdecr.bf").buildSystem();//INCR*2 + DECR*1
        //assertEquals("C0:255-seg", outContent.toString());
    }
    @Test
    public void TestS2overFlow() {
        exit.expectSystemExitWithStatus(1);//expecting Exit code 1
        new Interpreter("-p", "test\\decr.bf").buildSystem();//DECR*1
    }
    @Test
    public void TestS3() {
        exit.expectSystemExitWithStatus(0);//expecting Exit code 0
        new Interpreter("-p", "test\\right29999.bf").buildSystem();//RIGHT*29999 + INCR*1
        //assertEquals("C0:255-seg", outContent.toString());
    }
    @Test
    public void TestS3overFlow() {
        exit.expectSystemExitWithStatus(2);//expecting Exit code 2
        new Interpreter("-p", "test\\right30000.bf").buildSystem();//RIGHT*30000
    }
    @Test
    public void TestS4() {
        exit.expectSystemExitWithStatus(0);//expecting Exit code 0
        new Interpreter("-p", "test\\mixed.bf").buildSystem();//RIGHT*2 + LEFT +  INCR*2 + DECR
        //assertEquals("C0:255-seg", outContent.toString());
    }
    @Test
    public void TestS4overFlow() {
        exit.expectSystemExitWithStatus(2);//expecting Exit code 2
        new Interpreter("-p", "test\\left.bf").buildSystem();//LEFT
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
}

package io.github.donut.proj;

import io.github.donut.proj.utils.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.logging.Level;

public class LoggerTest {
    @BeforeAll
    static void setup() {
        Logger.init("io/github/donut/proj/configs/logging.properties");
    }

    @Test
    public void testLogException() {
        Logger.log(new Exception("custom exception"));

    }

    @Test
    public void testLogExceptionMessage() {
        Logger.log(new Exception("custom message"), "another message");
        /*
        [Feb 19, 2021 2:49:34 PM] [SEVERE][tic-tac-toe-LoggerTest] another message: java.lang.Exception: custom message
        [Feb 19, 2021 2:49:34 PM] [SEVERE][tic-tac-toe-LoggerTest] 	 io.github.donut.proj.LoggerTest.testLogExceptionMessage(LoggerTest.java:24)
        [Feb 19, 2021 2:49:34 PM] [SEVERE][tic-tac-toe-LoggerTest] 	 java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
         */
    }

    @Test
    public void testRegularLog() {
        Logger.log("this is a message");
        /*
        [Feb 19, 2021 2:52:25 PM] [INFO][tic-tac-toe-LoggerTest] this is a message
        */
    }

    @Test
    public void testLogWithParams() {
        Logger.log("test {0}, {1}, {2}", "1", "2", "3");
        /*
        [Feb 19, 2021 2:53:52 PM] [INFO][tic-tac-toe-LoggerTest] test 1, 2, 3
        */
    }

    @Test
    public void testCustomWarnings() {
        Logger.log(Level.WARNING, "test {0}, {1}, {2}", "1", "2", "3");
        /*
        [Feb 19, 2021 2:55:29 PM] [WARNING][tic-tac-toe-LoggerTest] test 1, 2, 3
        */
    }

    @Test
    public void testExceptionCause() {
        Logger.log(new Exception("custom message", new Exception("custom cause")));
        /*
        [Feb 19, 2021 2:57:31 PM] [SEVERE][tic-tac-toe-LoggerTest] java.lang.Exception: custom message
        [Feb 19, 2021 2:57:31 PM] [SEVERE][tic-tac-toe-LoggerTest] 	 io.github.donut.proj.LoggerTest.testExceptionClause(LoggerTest.java:62)
        [Feb 19, 2021 2:57:31 PM] [SEVERE][tic-tac-toe-LoggerTest] 	 java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        [Feb 19, 2021 2:57:31 PM] [SEVERE][tic-tac-toe-LoggerTest] Caused by: java.lang.Exception: custom clause
        [Feb 19, 2021 2:57:31 PM] [SEVERE][tic-tac-toe-LoggerTest] 	 io.github.donut.proj.LoggerTest.testExceptionClause(LoggerTest.java:62)
        [Feb 19, 2021 2:57:31 PM] [SEVERE][tic-tac-toe-LoggerTest] 	 java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        */
    }

}

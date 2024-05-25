package CW2;

import org.junit.Assert;
import org.junit.Test;

public class TestEvenOrOdd {
    @Test
    public void testEvenOrOdd1() {
        byte[] testarray = {1, 2, 3, 4};
        int answer = 1;
        int counter = 0;
        for (byte i : testarray)
            counter += Main.evenOrOdd(i);
        counter %= 2;
        Assert.assertEquals(answer, counter);
    }

    @Test
    public void testEvenOrOdd2() {
        byte[] testarray = {1, 1, 1, 1, 1, 1};
        int answer = 0;
        int counter = 0;
        for (byte i : testarray)
            counter += Main.evenOrOdd(i);
        counter %= 2;
        Assert.assertEquals(answer, counter);
    }

    @Test
    public void testEvenOrOdd3() {
        byte[] testarray = {0,0,0,0};
        int answer = 0;
        int counter = 0;
        for (byte i : testarray)
            counter += Main.evenOrOdd(i);
        counter %= 2;
        Assert.assertEquals(answer, counter);
    }
}

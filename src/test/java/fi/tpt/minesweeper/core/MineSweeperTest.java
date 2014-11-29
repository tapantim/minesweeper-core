package fi.tpt.minesweeper.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by timotapanainen on 16.11.14.
 */
public class MineSweeperTest {

    @Test
    public void testClearMinefield() throws Exception {
        Minefield mf = new Minefield(3, 3);
        mf.plantMine(0, 0);
        MineSweeper ms = new MineSweeper(mf);
        assertEquals(false, ms.isGameEnded());
        assertEquals(false, ms.isGameEndedExplosion());
        assertEquals(false, ms.isGameEndedSuccessfully());
        assertEquals(false, ms.isGameRunning());
        assertEquals(0, ms.getTime());

        ms.openCell(2, 2);
        assertEquals(true, ms.isGameEnded());
        assertEquals(true, ms.isGameEndedSuccessfully());
        assertEquals(false, ms.isGameRunning());
        assertEquals(false, ms.isGameEndedExplosion());
        assertEquals(true, ms.getTime() > 0);
    }

    @Test
    public void testClearing2() throws Exception {
        Minefield mf = new Minefield(3, 3);
        mf.plantMine(0, 0);
        MineSweeper ms = new MineSweeper(mf);
        ms.openCell(1, 0);
        assertEquals(false, ms.isGameEnded());
        assertEquals(true, ms.isGameRunning());

        ms.openCell(0, 1);
        assertEquals(false, ms.isGameEnded());
        assertEquals(true, ms.isGameRunning());

        Thread.sleep(10);

        ms.openCell(2, 2);
        assertEquals(true, ms.isGameEnded());
        assertEquals(false, ms.isGameRunning());
        assertEquals(false, ms.isGameEndedExplosion());
        assertEquals(true, ms.getTime() > 0);
    }
}

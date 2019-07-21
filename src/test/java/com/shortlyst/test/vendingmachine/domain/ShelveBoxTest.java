package com.shortlyst.test.vendingmachine.domain;

import com.shortlyst.test.vendingmachine.service.ShelveBoxService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShelveBoxTest {

    private ShelveBoxService shelveBox;

    @Before
    public void setUp() throws Exception {
        shelveBox = new ShelveBoxService();
    }

    @After
    public void tearDown() throws Exception {
        shelveBox = null;
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void negativeShelfIndex() {
        shelveBox.isEmptyAt(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void outOfBoundsShelfIndex() {
        shelveBox.addToShelf("Energy Drink", 120, 1);
        shelveBox.isEmptyAt(1);
    }

    /**
     * our shelves never empty, only the quantity, but physically is empty
     * */
    @Test
    public void shouldEmptyAfterUse() throws Exception {
        shelveBox.addToShelf("Energy Drink", 120, 1);
        shelveBox.addToShelf("Apple Juice", 110, 1);
        Assert.assertFalse(shelveBox.isEmpty());
        shelveBox.releaseGoodsFromIndex(0);
        Assert.assertFalse(shelveBox.isEmpty());
        shelveBox.releaseGoodsFromIndex(1);
        Assert.assertFalse(shelveBox.isEmpty());
    }
}

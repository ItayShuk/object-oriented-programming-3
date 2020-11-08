import static org.junit.Assert.*;

import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class LongTermTest {

    private LongTermStorage storage1 = new LongTermStorage();

    private LongTermStorage storage2 = new LongTermStorage();

    private Item baseballBat = ItemFactory.createSingleItem("baseball bat");

    private Item helmetSize1 = ItemFactory.createSingleItem("helmet, size 1");

    private Item helmetSize3 = ItemFactory.createSingleItem("helmet, size 3");

    private Item sporesEngine = ItemFactory.createSingleItem("spores engine");

    private Item football = ItemFactory.createSingleItem("football");

    /**
     * Clean storage before tests
     */
    @Before
    public void BeforeTest() {
        LongTermStorage storage1 = new LongTermStorage();

        LongTermStorage storage2 = new LongTermStorage();
    }

    /**
     * Capacity Tests
     */
    @Test
    public void CapacityTest() {
        /*getAvailableCapacity tests*/
        assertEquals("Capacity don't match", 1000, storage1.getAvailableCapacity());
    }

    /**
     * Adding items tests
     */
    @Test
    public void addingItems() {
        assertEquals("regular adding", 0, storage1.addItem(sporesEngine, 3));

        assertEquals("insane adding, no room", -1, storage1.addItem(sporesEngine, 30000));

        assertEquals("Negative adding", -1, storage2.addItem(sporesEngine, -2));

        assertEquals("Null adding", -1, storage2.addItem(null, 3));

    }

    /**
     * Check if reset function works
     */
    @Test
    public void resetting() {
        assertEquals("regular adding", 0, storage2.addItem(sporesEngine, 3));
        storage2.resetInventory();

        assertEquals("inventory should be empty", new HashMap<String, Integer>(), storage2.getInventory());
    }

    /**
     * Checking inventory function
     */
    @Test
    public void Inventory_check() {
        storage1.addItem(baseballBat, 1);
        storage1.addItem(helmetSize1, 2);
        storage1.addItem(helmetSize3, 3);

        assertEquals("items check", 1, storage1.getItemCount("baseball bat"));

        assertEquals("items check", 2, storage1.getItemCount("helmet, size 1"));

        assertEquals("items check", 3, storage1.getItemCount("helmet, size 3"));

        assertEquals("items check", 0, storage1.getItemCount("football"));
    }
}

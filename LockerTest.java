import static org.junit.Assert.*;

import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class LockerTest {

    private Locker locker1 = new Locker(0);

    private Locker locker2 = new Locker(100);

    private Locker locker3 = new Locker(10);

    private Locker locker4 = new Locker(10000);

    private Item baseballBat = ItemFactory.createSingleItem("baseball bat");

    private Item helmetSize1 = ItemFactory.createSingleItem("helmet, size 1");

    private Item helmetSize3 = ItemFactory.createSingleItem("helmet, size 3");

    private Item sporesEngine = ItemFactory.createSingleItem("spores engine");

    private Item football = ItemFactory.createSingleItem("football");

    @Before
    public void BeforeTests() {
        Locker locker1 = new Locker(0);

        Locker locker2 = new Locker(100);

        Locker locker3 = new Locker(10);

        Locker locker4 = new Locker(10000);
    }


    @Test
    public void CapacityTest() {
        /*getAvailableCapacity tests*/
        assertEquals("Capacity don't match", 0, locker1.getAvailableCapacity());

        assertEquals("Capacity don't match", 100, locker2.getAvailableCapacity());

        assertEquals("Capacity don't match", 10, locker3.getAvailableCapacity());

        assertEquals("Capacity don't match", 10000, locker4.getAvailableCapacity());
    }

    @Test
    public void ManagingLockers() {
        /*addItem tests*/
        /*locker1*/
        assertEquals("Not enough room", -1, locker1.addItem(baseballBat, 3));

        /*locker2*/
        assertEquals("Adding null", -1, locker2.addItem(null, 2));

        assertEquals("Adding negative number of items", -1, locker2.addItem(baseballBat, -1));

        assertEquals("Adding items", 0, locker2.addItem(baseballBat, 10));

        assertEquals("Adding items, some to storage", 1, locker2.addItem(helmetSize1, 20));

        assertEquals("Adding problem items, football to bat", -2, locker2.addItem(football, 1));

        /*locker3*/
        assertEquals("Adding item", 0, locker3.addItem(football, 1));

        assertEquals("Adding problem items, bat to football", -2, locker3.addItem(baseballBat, 1));

        assertEquals("Should'nt be enough room", -1, locker3.addItem(sporesEngine, 1));

        /*checking inventory*/
        /*locker4*/

        assertEquals("Items are more then 50% of locker and less then 100%, but storage cant hold them", -1,
                locker4.addItem(football, 2000));

        assertEquals("Map shouldn't be changed", new HashMap<String, Integer>(), locker4.getInventory());

        assertEquals("Removing items that not in locker", -1, locker4.removeItem(football, 2000));

        assertEquals("Removing negative number of items", -1, locker4.removeItem(helmetSize1, -2));
    }

    @Test
    public void LockerPercentageTest() {
        /*Getting to 50%*/
        assertEquals("Should add all items", 0, locker2.addItem(helmetSize3, 10));

        /*Getting over 50%*/
        assertEquals("Should Move items to storage", 1, locker2.addItem(helmetSize3, 1));

        /*Checking Items Percentage*/
        assertEquals("Should be 20% of 100, 20 items in locker", 4,
                locker2.getItemCount(helmetSize3.getType()));

        assertEquals("as of only 20% should be left", 80, locker2.getAvailableCapacity());
    }

    @Test
    public void RemoveTests() {
        /*remove tests*/
        assertEquals("Adding items", 0, locker3.addItem(football, 1));

        assertEquals("Removing null", -1, locker3.removeItem(null, 1));

        assertEquals("Didn't remove an item", 0, locker3.removeItem(football, 1));

        assertEquals("Removing negative number of items", -1, locker3.removeItem(football, -1));

        assertEquals("Removing more then there is items", -1, locker4.removeItem(helmetSize1, 100));
    }

    @Test
    public void Inventory_Check() {
        locker2.addItem(baseballBat, 2);
        locker2.addItem(helmetSize1, 3);
        locker2.addItem(helmetSize3, 4);

        assertEquals("items check", 2, locker2.getItemCount("baseball bat"));

        assertEquals("items check", 3, locker2.getItemCount("helmet, size 1"));

        assertEquals("items check", 4, locker2.getItemCount("helmet, size 3"));

        assertEquals("items check", 0, locker2.getItemCount("spores engine"));
    }
}

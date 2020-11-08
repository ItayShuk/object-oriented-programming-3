import java.util.*;

public class StorageUnit {

    protected int capacity;

    protected int freeCapacity;

    protected Map<String, Integer> map = new HashMap<String, Integer>();

    /**
     * Parent class to Locker and LTS
     */
    public StorageUnit() {
    }


    /**
     * This method returns the number of Items of type type
     * the locker contains.
     *
     * @param type
     * @return
     */
    public int getItemCount(String type) {
        for (Map.Entry<String, Integer> entry : this.map.entrySet()) {
            if (entry.getKey().equals(type)) {
                return entry.getValue();
            }
        }
        return 0;
    }

    /**
     * This method returns a map of all the item types
     * contained in the locker, and their respective quantities.
     *
     * @return
     */
    public Map<String, Integer> getInventory() {
        return this.map;
    }

    /**
     * This method returns the locker’s total capacity.
     *
     * @return
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * This method returns the locker’s available capacity, i.e.
     * how many storage units are unoccupied by Items.
     *
     * @return
     */
    public int getAvailableCapacity() {
        return this.freeCapacity;
    }

}

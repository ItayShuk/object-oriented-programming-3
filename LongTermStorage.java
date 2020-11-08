import oop.ex3.spaceship.*;

import java.util.*;

public class LongTermStorage extends StorageUnit {

    /**
     * Constructor for LTS
     */
    public LongTermStorage() {
        this.capacity = 1000;
        this.freeCapacity = 1000;
    }

    /**
     * This method adds n Items of the given type to the longterm storage unit. If the action is successful,
     * this method should return 0. If n Items cannot be added
     * to the locker at this time, no Items should be added, the method should return -1
     *
     * @param item
     * @param n
     * @return
     */
    public int addItem(Item item, int n) {
        if (n < 0 || item == null) {
            System.out.print("Not a Valid input");
            return -1;
        }
        if (n * item.getVolume() > this.freeCapacity) {
            System.out.print("Error: Your request cannot be completed at this\n" +
                    "time. Problem: no room for " + n + " Items of type " + item.getType());
            return -1;
        }
        this.map.put(item.getType(), this.getItemCount(item.getType()) + n);
        this.freeCapacity -= n * item.getVolume();
        return 0;
    }

    /**
     * This method resets the long-term storage’s inventory (i.e. after
     * it is invoked the inventory does not contain any Item)
     */
    public void resetInventory() {
        this.map = new HashMap<String, Integer>();
        this.freeCapacity = 1000;
    }
}

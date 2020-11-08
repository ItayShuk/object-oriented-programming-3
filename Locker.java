import oop.ex3.spaceship.*;

public class Locker extends StorageUnit {

    protected static LongTermStorage Storage = new LongTermStorage();
    private String INPUT_ERROR = "Not a Valid input";
    private String FOOTBALL_ERROR = "Error: Your request cannot be" +
            "completed at this time. Problem: the locker cannot contain items of type" +
            "Football, as it contains a contradicting" +
            "item";
    private String BASEBALL_BAT_ERROR = "Error: Your request cannot be" +
            "completed at this time. Problem: the locker cannot contain items of type" +
            "Football, as it contains a contradicting" +
            "item";
    private String ERROR_CANNOT_COMPLETE = "Error: Your request cannot be completed at this time.";
    private String WARNNING_TO_STORAGE = "Warning: Action successful, but has caused items to be moved to " +
            "storage";

    /**
     * Constructor Locker
     *
     * @param capacity
     */
    public Locker(int capacity) {
        this.capacity = capacity;
        this.freeCapacity = capacity;
    }


    /**
     * This method adds n Items of the given type to the
     * locker. If the addition is successful and doesn’t cause Items to be moved to long-term storage, this
     * method should return 0
     * . If n Items cannot be added to the locker at this time, no Items should be added,
     * the method should return -1
     * If this action causes n
     * Items to be moved to long-term storage and it can accommodate all n Items,
     * the method should return 1
     *
     * @param item
     * @param n
     * @return
     */
    public int addItem(Item item, int n) {
        if (n < 0 || item == null) {
            System.out.print(INPUT_ERROR);
            return -1;
        }
        if (item.getType().equals("football") || item.getType().equals("baseball bat")) {
            if (item.getType().equals("football")) {
                if (getItemCount("baseball bat") != 0) {
                    System.out.print(FOOTBALL_ERROR);
                    return -2;
                }
            } else {
                if (getItemCount("football") != 0) {
                    System.out.print(BASEBALL_BAT_ERROR);
                    return -2;
                }
            }
        }
        if (getAvailableCapacity() >= item.getVolume() * n) {
            int currentPrecent = (int) Math.floor(this.capacity * 0.2) / item.getVolume();
            //how many items for 20% locker
            int toStorage = (this.getItemCount(item.getType()) + n) - currentPrecent;
            if (((this.getItemCount(item.getType()) + n) * item.getVolume()) > 0.5 * this.capacity) {
                //if more then 50%
                int longTermStorageStatus = this.Storage.addItem(item, toStorage);//trying to move to storage
                if (longTermStorageStatus == -1) {//moving to storage was a disaster
                    System.out.print(ERROR_CANNOT_COMPLETE +
                            " Problem: no room for " + n + " items of type " + item.getType());
                    return -1;
                } else {//if moving to storage successful
                    this.map.put(item.getType(), currentPrecent);
                    //resetting if moving to storage was successful
                    this.freeCapacity += (toStorage - n) * item.getVolume();
                    System.out.print(WARNNING_TO_STORAGE);
                    return 1;
                }
            }
            //regular adding of items
            this.map.put(item.getType(), this.getItemCount(item.getType()) + n);
            this.freeCapacity -= n * item.getVolume();
        } else {//not enough room in locker
            System.out.print(ERROR_CANNOT_COMPLETE +
                    " Problem: no room for " + n + " items of type " + item.getType());
            return -1;
        }
        return 0;
    }


    /**
     * This method removes n Items of the type type from
     * the locker. If the action is successful, this method should return 0.
     * In case there are less than n Items
     * of this type in the locker, no Items should be removed, the method should return -1
     *
     * @param item
     * @param n
     * @return
     */
    public int removeItem(Item item, int n) {
        if (item == null) {
            System.out.print(ERROR_CANNOT_COMPLETE +
                    " Problem: cannot remove a negative number of items of type null");
            return -1;
        }
        if (n < 0) {
            System.out.print(ERROR_CANNOT_COMPLETE +
                    " Problem: cannot remove a negative number of items of type " + item.getType());
            return -1;
        } else if (getItemCount(item.getType()) < n) {
            System.out.print(ERROR_CANNOT_COMPLETE +
                    " Problem: the locker does not contain " + n + " items of type " + item.getType());
            return -1;
        }
        this.map.put(item.getType(), getItemCount(item.getType()) - n);
        this.freeCapacity += n * item.getVolume();
        return 0;
    }
}

package tutorial;

import java.util.ArrayList;

public class Day {
    Timeslot slot1 = new Timeslot(4, "9am - 12pm ($4)");
    Timeslot slot2 = new Timeslot(3, "12pm - 3pm ($3)");
    Timeslot slot3 = new Timeslot(5, "3pm - 6pm ($5)");
    Timeslot slot4 = new Timeslot(2, "6pm - 9pm ($2)");

    Day(int capacity) {
        this.slot1.changeCapacity(capacity);
        this.slot2.changeCapacity(capacity);
        this.slot3.changeCapacity(capacity);
        this.slot4.changeCapacity(capacity);
    }
    Timeslot[] slots = new Timeslot[] {slot1, slot2, slot3, slot4};
    ArrayList<String> result = new ArrayList<String>();

    ArrayList<String> getOptions() {
        for (Timeslot slot : slots) {
            result.add(slot.toString());
        }
        return result;
    }
}

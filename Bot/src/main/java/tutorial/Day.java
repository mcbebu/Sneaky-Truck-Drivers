package tutorial;

import java.util.HashMap;

public class Day {
    static int _9AMTO12PMPRICING = 4;
    static int _12PMTO3PMPRICING = 3;
    static int _3PMTO6PMPRICING = 5;

    static int _6PMTO9PMPRICING = 2;
    int _9amTo12pmSlot;
    int _12pmTo3pmSlot;
    int _3pmTo6pmSlot;

    int _6pmTo9pmSlot;
    Day(int capacity) {
        this._9amTo12pmSlot = capacity;
        this._12pmTo3pmSlot = capacity;
        this._3pmTo6pmSlot = capacity;
        this._6pmTo9pmSlot = capacity;
    }

    HashMap<String, Integer> getOptions() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        if (this._9amTo12pmSlot > 0) {
            hashMap.put("9am - 12pm ($4)", Day._9AMTO12PMPRICING);
        }
        if (this._12pmTo3pmSlot > 0) {
            hashMap.put("12pm - 3pm ($3)", Day._12PMTO3PMPRICING);
        }
        if (this._3pmTo6pmSlot > 0) {
            hashMap.put("3pm - 6pm ($5)", Day._3PMTO6PMPRICING);
        }
        if (this._6pmTo9pmSlot > 0) {
            hashMap.put("6pm - 9pm ($2)", Day._6PMTO9PMPRICING);
        }
        return hashMap;
    }

    void updateOptions(String slotName) {
        if (slotName.equals("9am - 12pm ($4)")) {
            this._9amTo12pmSlot -= 1;
        } else if (slotName.equals("12pm - 3pm ($3)")) {
            this._12pmTo3pmSlot -= 1;
        } else if (slotName.equals("3pm - 6pm ($5)")) {
            this._3pmTo6pmSlot -= 1;
        } else if (slotName.equals("6pm - 9pm ($2)")) {
            this._6pmTo9pmSlot -= 1;
        }
    }
}

package com.example;

import java.util.HashMap;

public class Day {
    static int _9AMTO12PMPRICING = 4;
    static int _12PMTO3PMPRICING = 3;
    static int _3PMTO6PMPRICING = 5;
    int _9amTo12pmSlot;
    int _12pmTo3pmSlot;
    int _3pmTO6pmSlot;

    Day(int capacity) {
        this._9amTo12pmSlot = capacity;
        this._12pmTo3pmSlot = capacity;
        this._3pmTO6pmSlot = capacity;
    }

    HashMap<String, Integer> getOptions() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        if (this._9amTo12pmSlot > 0) {
            hashMap.put("9amTo12pmSlot", this._9AMTO12PMPRICING);
        }
        if (this._12pmTo3pmSlot > 0) {
            hashMap.put("12pmTo3pmSlot", this._12PMTO3PMPRICING);
        }
        if (this._3pmTO6pmSlot > 0) {
            hashMap.put("3pmTo6pmSlot", this._3PMTO6PMPRICING);
        }
        return hashMap;
    }

    void updateOptions(String slotName) {
        if (slotName.equals("9amTo12pmSlot")) {
            this._9amTo12pmSlot -= 1;
        } else if (slotName.equals("12pmTo3pmSlot")) {
            this._12pmTo3pmSlot -= 1;
        } else if (slotName.equals("3pmTo6pmSlot")) {
            this._3pmTO6pmSlot -= 1;
        }
    }
}
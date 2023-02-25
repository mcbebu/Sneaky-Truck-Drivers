package com.example;

import org.json.simple.JSONObject;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        Day day1 = new Day(10);
        HashMap<String,Integer> map = day1.getOptions();

        JSONObject json = new JSONObject(map);
        System.out.println(json);
    }
}

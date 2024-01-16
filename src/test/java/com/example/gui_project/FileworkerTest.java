package com.example.gui_project;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class FileworkerTest {

    @Test
    void fill_inf() throws FileNotFoundException {
        Fileworker flw = new Fileworker("Input", "txt");
        flw.ReadFromFile(true);
        flw.Fill_inf();
        Vector<Integer> task_num = new Vector<Integer>();
        task_num.add(1);
        task_num.add(2);
        task_num.add(3);
        task_num.add(4);
        Vector<String> task = new Vector<String>();
        task.add("ax^2 + bx + c");
        task.add("3x + 2y");
        task.add("4a - 6b");
        task.add("a + b + c + d + e / f");
        Vector<Map<Character, Double>> var = getMaps();
        for(int i = 0; i < flw.inf_.size(); i++) {
            assertEquals(flw.inf_.get(i).task_, task.get(i));
            assertEquals(flw.inf_.get(i).task_number_, task_num.get(i));
            assertEquals(flw.inf_.get(i).result, 0);
            assertEquals(flw.inf_.get(i).variables_, var.get(i));
        }
        flw = new Fileworker("Tasks", "json");
        flw.ReadFromFile(Boolean.TRUE);
        flw.Fill_inf();
        task_num = new Vector<>();
        task = new Vector<>();
    }

    private static Vector<Map<Character, Double>> getMaps() {
        Vector<Map<Character, Double>> var = new Vector<Map<Character, Double>>();
        Map<Character, Double> tmp = new HashMap<Character, Double>();
        tmp.put('a', 2.0);
        tmp.put('b', 5.0);
        tmp.put('c', 1.0);
        tmp.put('x', 3.0);
        tmp.put('y', 9.0);
        var.add(tmp);
        tmp = new HashMap<Character, Double>();
        tmp.put('x', 2.0);
        tmp.put('y', 2.0);
        var.add(tmp);
        tmp = new HashMap<Character, Double>();
        tmp.put('a', 6.0);
        tmp.put('b', -3.0);
        var.add(tmp);
        tmp = new HashMap<Character, Double>();
        tmp.put('a', 7.0);
        tmp.put('b', 5.0);
        tmp.put('c', 1.0);
        tmp.put('d', 10.0);
        tmp.put('e', 11.0);
        tmp.put('f', 0.0);
        var.add(tmp);
        return var;
    }


    @Test
    void readFromFile() throws FileNotFoundException {
        Fileworker flw = new Fileworker("Input", "txt");
        flw.ReadFromFile(Boolean.TRUE);
        Vector<String> check = new Vector<String>();
        check.add("Task 1");
        check.add("ax^2 + bx + c");
        check.add("a = 2");
        check.add("b = 5");
        check.add("c = 1");
        check.add("x = 3");
        check.add("y = 9");
        check.add("Task 2");
        check.add("3x + 2y");
        check.add("x = 2");
        check.add("y = 2");
        check.add("Task 3");
        check.add("4a - 6b");
        check.add("a = 6");
        check.add("b = -3");
        check.add("Task 4");
        check.add("a + b + c + d + e / f");
        check.add("a = 7");
        check.add("b = 5");
        check.add("c = 1");
        check.add("d = 10");
        check.add("e = 11");
        check.add("f = 0");
        assertEquals(flw.content_, check);
        flw = new Fileworker("Tasks", "json");
        flw.ReadFromFile(Boolean.TRUE);
        check = new Vector<String>();
        check.add("Task 1");
        check.add("((ax)^2 + b) * x + c");
        check.add("a = 2");
        check.add("b = 5");
        check.add("c = 1");
        check.add("x = 3");
        check.add("Task 2");
        check.add("3x + 2y");
        check.add("x = 2");
        check.add("y = 2");
        check.add("Task 3");
        check.add("4a - 6b");
        check.add("a = 6");
        check.add("b = -3");
        assertEquals(flw.content_, check);
    }

    @Test
    void fileWrite() {

    }
}
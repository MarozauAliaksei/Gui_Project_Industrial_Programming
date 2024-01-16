package com.example.gui_project;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InformationTest {

    @org.junit.jupiter.api.Test
    void change_var() {
        Map<Character, Double> tmp = new HashMap<Character, Double>();

        tmp.put('x', 3.0);
        tmp.put('y', 1.0);
        tmp.put('a', 0.0);
        Information inf = new Information(1, "yx^2 + y - a", tmp);
        inf.Change_var();
        assertEquals("1.0 * 3.0^2 + 1.0 - 0.0", inf.task_without_var);
    }

    @org.junit.jupiter.api.Test
    void remove_bruh() {
        Map<Character, Double> tmp = new HashMap<Character, Double>();
        tmp.put('x', 3.0);
        tmp.put('y', 1.0);
        tmp.put('a', 4.0);
        tmp.put('b', 4.0);
        tmp.put('c', 6.0);
        Information inf = new Information(1, "((ax)^2 + b) * x + c", tmp);
        inf.Change_var();
        inf.task_without_bruh = inf.Remove_bruh(inf.task_without_var);
        assertEquals("450.0", inf.task_without_bruh);


    }

    @org.junit.jupiter.api.Test
    void findResult() {
        Information inf = new Information();
        assertEquals("120.0", inf.FindResult("60 * 2 - 10 + 5 * 2"));
    }

}
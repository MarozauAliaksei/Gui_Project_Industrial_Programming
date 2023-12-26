package com.example.gui_project;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Information {
    int task_number_;
    String task_;
    String task_without_bruh;
    String task_without_var;
    Map<Character, Double> variables_;
    double result;
    Boolean solvebale = Boolean.TRUE;
    // String regex_brackets = ".*\(([^)]*)\).*";
    Information()
    {
        task_ = "";
        task_number_ = -1;
        variables_ = new HashMap<Character, Double>();
        result = 0;
        task_without_var = task_;
        task_without_bruh = task_;
    }
    Information(int num, String inf, Map<Character, Double> var) {
        task_number_ = num;
        task_ = inf;
        variables_ = var;
        result = 0;
        task_without_var = task_;
        task_without_bruh = task_;
    }
    Information(int a){
        task_number_ = a;
        task_ = "";
        variables_ = new HashMap<Character, Double>();
        result = 0;
        task_without_var = task_;
        task_without_bruh = task_;
    }
    void Change_var(){
        Pattern pow_var = Pattern.compile("([A-Z]|[a-z])?([A-Z]|[a-z])\\^");
        Pattern regex_var = Pattern.compile("(-?\\d*\\.?\\d*)([A-Z]|[a-z])");
        task_without_var = task_;
        Matcher poww = pow_var.matcher(task_without_var);
        while(poww.find()){
            String tmp = "";
            if(!poww.group(1).isEmpty()){
                tmp = poww.group(1) + " * ";

            }
            task_without_var = poww.replaceFirst(tmp + Double.toString(variables_.get(poww.group(2).charAt(0))) + '^');
            poww = pow_var.matcher(task_without_var);
        }
        Matcher mat = regex_var.matcher(task_without_var);
        while(mat.find()){
            double res = 0.0;
            if(mat.group(1).isEmpty()){
                res = 1.0;
            }
            else {
                res = Double.parseDouble(mat.group(1));
            }
            res = res * variables_.get(mat.group(2).charAt(0));
            task_without_var = mat.replaceFirst(Double.toString(res));
            mat = regex_var.matcher(task_without_var);
        }
    }

    String Remove_bruh(String line){
        Pattern regex_bruh  = Pattern.compile("\\(([^(]*?)\\)");
        Matcher mat = regex_bruh.matcher(line);
        while(mat.find()){
            String temp = (mat.group(1));
            line = mat.replaceFirst(Remove_bruh(temp));
            mat = regex_bruh.matcher(line);
        }
        line = FindResult(line);
        return line;
    }

    String FindResult(String inf){
        Pattern plus = Pattern.compile("(-?\\d+\\.?\\d*)\\s\\+\\s(-?\\d+\\.?\\d*)");
        Pattern minus = Pattern.compile("(-?\\d+\\.?\\d*)\\s-\\s(-?\\d+\\.?\\d*)");
        Pattern multi = Pattern.compile("(-?\\d+\\.?\\d*)\\s\\*\\s(-?\\d+\\.?\\d*)");
        Pattern divide = Pattern.compile("(-?\\d+\\.?\\d*)\\s/\\s(-?\\d+\\.?\\d*)");
        Pattern pow = Pattern.compile("(-?\\d+\\.?\\d*)\\^(-?\\d+\\.?\\d*)");
        Matcher powww = pow.matcher(inf);
        while(powww.find()){
            double res = Math.pow(Double.parseDouble(powww.group(1)), Double.parseDouble(powww.group(2)));
            inf = powww.replaceFirst(Double.toString(res));
            powww = pow.matcher(inf);
        }
        Matcher mult = multi.matcher(inf);
        while(mult.find()){
            double res = Double.parseDouble(mult.group(1)) * Double.parseDouble(mult.group(2));
            inf = mult.replaceFirst(Double.toString(res));
            mult = multi.matcher(inf);
        }
        Matcher div = divide.matcher(inf);
        while(div.find()){
            if (Objects.equals(div.group(2), "0.0") || Objects.equals(div.group(2), "0")){
                solvebale = Boolean.FALSE;
                return "Division by zero";
            }
            double res = Double.parseDouble(div.group(1)) / Double.parseDouble(div.group(2));
            inf = div.replaceFirst(Double.toString(res));
            div = divide.matcher(inf);
        }
        Matcher min = minus.matcher(inf);
        while(min.find()){
            double res = Double.parseDouble(min.group(1)) - Double.parseDouble(min.group(2));
            inf = min.replaceFirst(Double.toString(res));
            min = minus.matcher(inf);
        }
        Matcher plu = plus.matcher(inf);
        while(plu.find()){
            double res = Double.parseDouble(plu.group(1)) + Double.parseDouble(plu.group(2));
            inf = plu.replaceFirst(Double.toString(res));
            plu = plus.matcher(inf);
        }
        return inf;
    }
    void CoutError(String ErrorName){
        System.out.println("Task:" + task_number_);
        System.out.println(ErrorName);
    }

    void CoutAll(){
        System.out.println("Task:" + task_number_);
        System.out.println(task_);
        for (Map.Entry<Character, Double> pair: variables_.entrySet()){
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
        System.out.println(result);
    }


}

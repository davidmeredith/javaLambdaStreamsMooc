/**
 * Copyright © 2014, Oracle and/or its affiliates. All rights reserved.
 *
 * JDK 8 MOOC Lesson 1 homework
 */
package lesson1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Speakjava (simon.ritter@oracle.com)
 */
public class Lesson1 {

    /**
     * Run the exercises to ensure we got the right answers
     */
    public void runExercises() {
        System.out.println("JDK 8 Lambdas and Streams MOOC Lesson 1");
        System.out.println("Running exercise 1 solution...");
        exercise1();
        System.out.println("Running exercise 2 solution...");
        exercise2();
        System.out.println("Running exercise 3 solution...");
        exercise3();
        System.out.println("Running exercise 4 solution...");
        exercise4();
        System.out.println("Running exercise 5 solution...");
        exercise5();
    }

    /**
     * All exercises should be completed using Lambda expressions and the new
     * methods added to JDK 8 where appropriate. There is no need to use an
     * explicit loop in any of the code. Use method references rather than full
     * lambda expressions wherever possible.
     */
    /**
     * Exercise 1
     *
     * Create a string that consists of the first letter of each word in the
     * list of Strings provided.
     */
    private void exercise1() {
        List<String> list = Arrays.asList(
                "alpha", "bravo", "charlie", "delta", "echo", "foxtrot");

        StringBuilder sb = new StringBuilder();
        // void Consumer.accept(T) (single arg, void return)
        list.forEach((String s) -> sb.append(s.charAt(0)));
        System.out.println(sb);
    }

    /**
     * Exercise 2
     *
     * Remove the words that have odd lengths from the list.
     */
    private void exercise2() {
        List<String> list = new ArrayList<>(Arrays.asList(
                "alpha", "bravo", "charlie", "delta", "echo", "foxtrot"));

        // bool Predicate.test(T) (single arg, bool return) 
        // Is also BiPredicate, that takes two arguments and returns bool 
        list.removeIf(s -> s.length() % 2 > 0);
        // void Consumer.accept(T) (single arg, void return)
        //list.forEach(s -> System.out.println(s)); 
        // shorthand:
        list.forEach(System.out::println);
    }

    /**
     * Exercise 3
     *
     * Replace every word in the list with its upper case equivalent.
     */
    private void exercise3() {
        List<String> list = new ArrayList<>(Arrays.asList(
                "alpha", "bravo", "charlie", "delta", "echo", "foxtrot"));

        // R UnaryOperator.apply(T)
        //list.replaceAll((String s) -> {return s.toUpperCase();} );
        list.replaceAll((String s) -> {return s.toUpperCase();} );
        
        //list.replaceAll((String s) -> s.toUpperCase()); // equiv 
        
        //list.forEach(s -> System.out.println(s)); 
        // shorthand method ref: 
        list.forEach(System.out::println);
    }

    /**
     * Exercise 4
     *
     * Convert every key-value pair of the map into a string and append them all
     * into a single string, in iteration order.
     */
    private void exercise4() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("c", 3);
        map.put("b", 2);
        map.put("a", 1);

        /*
         Exercise 4: Convert every key-value pair of the map into a string 
         and append them all into a single string, in iteration order.  HINT: 
         Again, use a StringBuilder to construct the result String.  
         Use one of the new JDK 8 methods for Map
         */
        StringBuilder sb = new StringBuilder();
        //forEach(BiConsumer<? super K,? super V> action)  
        // void BiConsumer.accept(K, V) 
        map.forEach((String s, Integer i) -> sb.append(s).append(i));  
        System.out.println(sb);
    }

    /**
     * Exercise 5
     *
     * Create a new thread that prints the numbers from the list.
     */
    private void exercise5() {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        //l.replaceAll(n -> n.hashCode());
        //l.replaceAll(Integer::hashCode);
        //l.replaceAll(Number::hashCode);
        //l.forEach(System.out::println);
        

        /*
         Create a new thread that prints the numbers from the list.  
         HINT: This is a straightforward Lambda expression
         */
        // void Runnable.run()
        //Runnable r2 = () -> System.out.println("Hello world two!"); 
        Runnable r2 = () -> { l.forEach(i -> System.out.println(""+i)); };
        r2.run();
    }



    /**
     * Main entry point for application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Lesson1 lesson = new Lesson1();
        lesson.runExercises();
    }
}

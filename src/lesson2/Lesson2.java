/**
 * Copyright © 2014, Oracle and/or its affiliates. All rights reserved.
 *
 * JDK 8 MOOC Lesson 2 homework
 */
package lesson2;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Speakjava (simon.ritter@oracle.com)
 */
public class Lesson2 {
  private static final String WORD_REGEXP = "[- .:,]+";

  /**
   * Run the exercises to ensure we got the right answers
   *
   * @throws java.io.IOException
   */
  public void runExercises() throws Exception {
    System.out.println("JDK 8 Lambdas and Streams MOOC Lesson 2");
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
    System.out.println("Running exercise 6 solution...");
    exercise6();
    System.out.println("Running exercise 7 solution...");
    exercise7();
  }

  /**
   * Exercise 1
   *
   * Create a new list with all the strings from original list converted to 
   * lower case and print them out.
   */
  private void exercise1() {
    List<String> list = Arrays.asList(
        "The", "Quick", "BROWN", "Fox", "Jumped", "Over", "The", "LAZY", "DOG");
    
    // this don't create a new list, it just processes each element in the terminal foreach 
    //list.stream().forEach( (String s) -> { System.out.println(s.toLowerCase()); } );

    // map() returns a stream which we can then collect 
    List<String> result = list.stream()
            .map((String s) -> s.toLowerCase() )
            .collect(Collectors.toList());
    result.forEach(s -> System.out.println(s) );
  }

  /**
   * Exercise 2
   *
   * Modify exercise 1 so that the new list only contains strings that have an
   * odd length
   */
  private void exercise2() {
    List<String> list = Arrays.asList(
        "The", "Quick", "BROWN", "Fox", "Jumped", "Over", "The", "LAZY", "DOG");
    
    // map() returns a stream which we can then collect 
    List<String> result = list.stream()
            .map((String s) -> s.toLowerCase() )
            .filter(s -> s.length() % 2 != 0 )
            .collect(Collectors.toList());
    result.forEach(s -> System.out.println(s) );
     
  }

  /**
   * Exercise 3
   *
   * Join the second, third and forth strings of the list into a single string,
   * where each word is separated by a hyphen (-). Print the resulting string.
   */
  private void exercise3() {
    List<String> list = Arrays.asList(
        "The", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");

    // This is fine, but it uses forEach which is discouraged as it is sequential 
    // and could be replaced with a map and reduction 
    StringBuilder sb = new StringBuilder(); 
    list.stream().skip(1).limit(3).forEach( (String s) -> sb.append(s).append("-") ); 
    sb.deleteCharAt(sb.length()-1); 
    System.out.println(sb);
   
    // This is fine but i don't like the length-1 
    String str = list.stream()
            .skip(1)  // skip first element 
            .limit(3) // limit stream to next 3 elements 
            .map((String s) -> { return s += "-"; }) // append "-" to each element 
            //.peek(System.out::println)    // debug, print each value 
            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)  //reduction: collect into string
            .toString(); 
    System.out.println(str.substring(0, str.length()-1));

    // This is good, but i wonder if there is a way to get the size of the stream 
    // for the last call to limit, e.g. .limit(streamSize - 1) 
    String str2 = list.stream()
            .skip(1)  // skip first element 
            .limit(3) // limit stream to next 3 elements 
            .flatMap( s -> Stream.of(s, "-")) // create a new stream with new elements 
            .limit(5)  // truncate the input stream 
            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)  //reduction: collect into string
            .toString(); 
    System.out.println(str2); 
  }

  /**
   * Count the number of lines in the file using the BufferedReader provided
   */
  private void exercise4() throws Exception {
    URL url = this.getClass().getResource("SonnetI.txt");
    try (BufferedReader reader = Files.newBufferedReader(
        Paths.get(url.toURI()), StandardCharsets.UTF_8)) {
        System.out.println(reader.lines().count()); 
    }
  }
  
  /**
   * Using the BufferedReader to access the file, create a list of words with
   * no duplicates contained in the file.  Print the words.
   * 
   * HINT: A regular expression, WORD_REGEXP, is already defined for your use.
   */
  private void exercise5() throws Exception {
    URL url = this.getClass().getResource("SonnetI.txt");
    try (BufferedReader reader = Files.newBufferedReader(
        Paths.get(url.toURI()), StandardCharsets.UTF_8)) {

        List<String> result = reader
                .lines()  // get every line of file 
                // Each element of the input stream produces its own stream of elements (Stream.of),  
                // these are subsequently collapsed into the output stream, so size
                // of output stream can be different from size of input stream. 
                .flatMap( 
                        // Stream.of returns a sequential ordered stream whose elements are the specified values.
                        (String line) -> Stream.of(line.split(Lesson2.WORD_REGEXP))
                )
                .distinct() // Returns a stream consisting of the distinct elements of this stream
                .collect(Collectors.toList());  // collect into mutable collection and terminate stream  
        // print
        result.forEach(System.out::println); 
    }
  }
  
  /**
   * Using the BufferedReader to access the file create a list of words from 
   * the file, converted to lower-case and with duplicates removed, which is
   * sorted by natural order.  Print the contents of the list.
   */
  private void exercise6() throws Exception {
    URL url = this.getClass().getResource("SonnetI.txt");
    try (BufferedReader reader = Files.newBufferedReader(
        Paths.get(url.toURI()), StandardCharsets.UTF_8)) {
        
        List<String> result = reader
                .lines() 
                .flatMap( (String line) -> Stream.of(line.split(Lesson2.WORD_REGEXP)))
                .map((String w) -> w.toLowerCase())
                .distinct()
                .sorted()
                .collect(Collectors.toList()); 
        
        result.forEach(System.out::println); 
    }
  }
  
  /**
   * Modify exercise6 so that the words are sorted by length
   */
  private void exercise7() throws Exception {
   URL url = this.getClass().getResource("SonnetI.txt");
    try (BufferedReader reader = Files.newBufferedReader(
        Paths.get(url.toURI()), StandardCharsets.UTF_8)) {
        List<String> result = reader
                .lines()  
                .flatMap( (String line) -> Stream.of(line.split(Lesson2.WORD_REGEXP)))
                .map((String w) -> w.toLowerCase())
                .distinct()
                .sorted(Comparator.comparing(String::length))       // works 
                //.sorted( (String a, String b) -> a.length() - b.length() )  // works 
                .collect(Collectors.toList()); 
        result.forEach(System.out::println); 
        
    }
  }

  /**
   * Main entry point for application
   *
   * @param args the command line arguments
   * @throws IOException If file access does not work
   */
  public static void main(String[] args) throws Exception {
    Lesson2 lesson = new Lesson2();
    lesson.runExercises();
  }
}


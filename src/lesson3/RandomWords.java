/**
 * Copyright © 2014, Oracle and/or its affiliates. All rights reserved.
 * 
 * JDK 8 MOOC Lesson 3 homework
 */
package lesson3;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Class to generate a list of random words
 *
 * @author Simon Ritter (@speakjava)
 */
public class RandomWords {
  private final List<String> sourceWords;

  /**
   * Constructor
   * 
   * @throws IOException If the source words file cannot be read
   */
    public RandomWords() throws IOException {
        URL url = this.getClass().getResource("words");
        //try (BufferedReader reader = Files.newBufferedReader(Paths.get("words"))) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(url.toURI()), StandardCharsets.UTF_8)) {
            //sourceWords = null;    
            // YOUR CODE HERE
            // In the constructor you need to read all the words (which are one per line) from the source file into a list 
            // (remember to use a stream to do this)
            sourceWords = reader.lines().collect(Collectors.toList());
            System.out.println("Loaded " + sourceWords.size() + " words");
            
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

  /**
   * Create a list of a given size containing random words
   *
   * @param listSize The size of the list to create
   * @return The created list
   */
  public List<String> createList(int listSize) {
      // Generate a list of the size specified in the parameter, and select 
      // words at random from the list read in the constructor.  
      // HINT: You can use the ints() method of the Random class, which 
      // returns a stream of random integers. You can specify the size of the 
      // stream using a parameter
      
    Random rand = new Random();

    List<String> wordList = rand.ints()
            .filter(i -> i > 0 && i < this.sourceWords.size()) // filter ints to values in index range of sourceWords 
            .limit(listSize)  // limit the size of the stream to be the size specified 
            .mapToObj(i -> this.sourceWords.get(i))   // map to string 
            .collect(Collectors.toList());    // mutable reduction/collect 

    return wordList;
  }

  /**
   * Return the list of all source words, which cannot be modified
   *
   * @return The unmodifiable list of all source words
   */
  public List<String> allWords() {
    return Collections.unmodifiableList(sourceWords);
  }
}
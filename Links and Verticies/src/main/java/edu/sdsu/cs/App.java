//Jonathan Schaffroth cssc0798
//Jacob Brooks cssc0799

package edu.sdsu.cs;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.File;

public class App {
    private static ArrayList<String> myList = new ArrayList<String>();
    private Path thePath;

    private App(String args) {
        thePath = Paths.get(args);
    }

    private List<String> readFile(Path filePathIn) {
        try {
            List<String> lines = Files.readAllLines(filePathIn, Charset.defaultCharset());
            return lines;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private void writeToFile(Path filePathOut, List<String> toWrite) {
        try {
            Files.write(filePathOut, toWrite, Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static ArrayList<String> displayDirectoryContents(File dir) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    displayDirectoryContents(file);
                } else {
                    String s = (String) file.getCanonicalPath();
                    if (s.substring(s.length() - 5).equals(".java"))
                        myList.add(s);
                    if (s.substring(s.length() - 5).equals(".text"))
                        myList.add(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myList;
    }

    private int longestLine(List<String> fileContents) {
        int longest = 0;
        for (String line : fileContents) {
            if (line.length() > longest) {
                longest = line.length();
            }
        }
        return longest;
    }

    private float averageLength(List<String> fileContents) {
        float numLines = 0;
        float totalLength = 0;
        if (fileContents.size() > 0) {
            for (int i = 0; i < fileContents.size(); i++) {
                numLines++;
                totalLength += fileContents.get(i).length();
            }
        }
        if (numLines == 0)
            return 0;
        return totalLength / numLines;
    }

    private static int mostFrequentTokensNum(List<String> fileContents) {
        String contents = "";
        int most = 0;
        for (String line : fileContents) {
            contents += (line + " ");
        }
        contents.toLowerCase();
        String[] freq = contents.split("\\s+");
        ArrayList<String> frequency = new ArrayList<String>();
        for (int i = 0; i < freq.length; i++) {
            frequency.add(freq[i]);
        }

        for (String line : frequency) {
            if (Collections.frequency(frequency, line) > most) {
                most = Collections.frequency(frequency, line);
            }
        }
        return most;
    }

    private String mostFrequentTokens(List<String> fileContents) {
        String contents = "";
        String result = "";
        int most = 0;
        for (String line : fileContents) {
            contents += (line + " ");
        }
        contents.toLowerCase();
        String[] freq = contents.split("\\s+");
        ArrayList<String> frequency = new ArrayList<String>();
        ArrayList<String> results = new ArrayList<String>();
        for (int i = 0; i < freq.length; i++) {
            frequency.add(freq[i]);
        }
        for (String line : frequency) {
            if (Collections.frequency(frequency, line) > most) {
                most = Collections.frequency(frequency, line);
            }
        }
        for (String line : frequency){
            if((Collections.frequency(frequency, line) == most) && (!(results.contains(line)))){
                results.add(line);
            }
        }
        for (String line : results){
            result += line + " ";
        }
       return result;
    }

    private int numTokens(List<String> fileContents) {
        String contents = "";
        for (String line : fileContents) {
            contents += (line + " ");
        }
        contents.toLowerCase();
        String[] freq = contents.split("\\s+");
        return freq.length;
    }

    private int uniqueCaseSensitiveTokens(List<String> fileContents) {
        StringTokenizer str; //creation of a StringTokenizer variable
        ArrayList<String> wordCheck = new ArrayList<String>();
        boolean match = false;
        //I created a String placeholder so that we could refer to the
        //same token in the code without calling nextToken() again
        wordCheck.add(""); //This is so that wordCheck is not an empty ArrayList
        for (int i = 0; i < fileContents.size(); i++) {
            str = new StringTokenizer(fileContents.get(i));
            String curToken;
            do {
                try {
                    curToken = str.nextToken();
                    for (int j = 0; j < wordCheck.size(); j++) {
                        if (curToken.equals(wordCheck.get(j))) {
                            match = true;
                        }
                    }
                } catch (NoSuchElementException e) {
                    continue;
                }
                if (match == false) {
                    wordCheck.add(curToken);
                }
                match = false;
            }
            while (str.hasMoreTokens());
        }
        return wordCheck.size() - 1; //minus 1 because of the empty string inside wordCheck
    }

    private int uniqueCaseInsensitiveTokens(List<String> fileContents) {
        StringTokenizer str; //creation of a StringTokenizer variable
        ArrayList<String> wordCheck = new ArrayList<String>();
        boolean match = false;
        //I created a String placeholder so that we could refer to the
        //same token in the code without calling nextToken() again
        wordCheck.add(""); //This is so that wordCheck is not an empty ArrayList
        for (int i = 0; i < fileContents.size(); i++) {
            str = new StringTokenizer(fileContents.get(i));
            String curToken;
            do {
                try {
                    curToken = str.nextToken();
                    for (int j = 0; j < wordCheck.size(); j++) {
                        if (curToken.toUpperCase().equals(wordCheck.get(j).toUpperCase())) {
                            match = true;
                        }
                    }
                } catch (NoSuchElementException e) {
                    continue;
                }
                if (match == false) {
                    wordCheck.add(curToken);
                }
                match = false;
            }
            while (str.hasMoreTokens());
        }
        return wordCheck.size() - 1; //minus 1 because of the empty string inside wordCheck
    }

    private String tenMostFrequent(List<String> fileContents) {
        if (fileContents.size() == 0)
            return "EMPTY LIST";
        String topTen = "\n";
        ArrayList<String> tokens = new ArrayList<String>();
        for (String s : fileContents) {
            String str[] = s.split("\\s+");
            for (int i = 0; i < str.length; i++) {
                tokens.add(str[i].toUpperCase());
            }
        }
        StringTokenizer str; //creation of a StringTokenizer variable
        ArrayList<String> wordCheck = new ArrayList<String>();
        ArrayList<Integer> frequency = new ArrayList<Integer>();
        boolean match = false;
        wordCheck.add(""); //This is so that wordCheck is not an empty ArrayList
        for (int i = 0; i < fileContents.size(); i++) {
            str = new StringTokenizer(fileContents.get(i));
            String curToken;
            do {
                try {
                    curToken = str.nextToken();
                    for (int j = 0; j < wordCheck.size(); j++) {
                        if (curToken.toUpperCase().equals(wordCheck.get(j).toUpperCase())) {
                            match = true;
                        }
                    }
                } catch (NoSuchElementException e) {
                    continue;
                }
                if (match == false) {
                    wordCheck.add(curToken.toUpperCase());
                }
                match = false;
            }
            while (str.hasMoreTokens());
        }
        wordCheck.remove(0);
        for (int i = 0; i < wordCheck.size(); i++) {
            frequency.add(Collections.frequency(tokens, wordCheck.get(i)));
        }
        for (int j = 0; j < frequency.size(); j++) { //j is the index of the element we check
            for (int i = 0; i < frequency.size(); i++) { //i is the index of the elements we compare to j
                if (frequency.get(j) > frequency.get(i)) {
                    Collections.swap(frequency, i, j);
                    Collections.swap(wordCheck, i, j);
                }
            }
        }
        int max = 10;
        if (frequency.size() < max)
            max = frequency.size();
        for (int i = 0; i < max; i++) {
            topTen += (frequency.get(i) + " " + wordCheck.get(i) + "\n");
        }
        return topTen;
    }

    private String tenLeastFrequent(List<String> fileContents) {
        if (fileContents.size() == 0)
            return "EMPTY LIST";
        String topTen = "\n";
        ArrayList<String> tokens = new ArrayList<String>();
        for (String s : fileContents) {
            String str[] = s.split(" ");
            for (int i = 0; i < str.length; i++) {
                tokens.add(str[i].toUpperCase());
            }
        }
        StringTokenizer str; //creation of a StringTokenizer variable
        ArrayList<String> wordCheck = new ArrayList<String>();
        ArrayList<Integer> frequency = new ArrayList<Integer>();
        boolean match = false;
        wordCheck.add(""); //This is so that wordCheck is not an empty ArrayList
        for (int i = 0; i < fileContents.size(); i++) {
            str = new StringTokenizer(fileContents.get(i));
            String curToken;
            do {
                try {
                    curToken = str.nextToken();
                    for (int j = 0; j < wordCheck.size(); j++) {
                        if (curToken.toUpperCase().equals(wordCheck.get(j).toUpperCase())) {
                            match = true;
                        }
                    }
                } catch (NoSuchElementException e) {
                    continue;
                }
                if (match == false) {
                    wordCheck.add(curToken.toUpperCase());
                }
                match = false;
            }
            while (str.hasMoreTokens());
        }
        wordCheck.remove(0);
        for (int i = 0; i < wordCheck.size(); i++) {
            frequency.add(Collections.frequency(tokens, wordCheck.get(i)));
        }
        for (int j = 0; j < frequency.size(); j++) { //j is the index of the element we check
            for (int i = 0; i < frequency.size(); i++) { //i is the index of the elements we compare to j
                if (frequency.get(j) < frequency.get(i)) {
                    Collections.swap(frequency, i, j);
                    Collections.swap(wordCheck, i, j);
                }
            }
        }
        int max = 10;
        if (frequency.size() < max)
            max = frequency.size();
        for (int i = 0; i < max; i++) {
            topTen += (frequency.get(i) + " " + wordCheck.get(i) + "\n");
        }
        return topTen;
    }
    public static void main(String[] args) {
        String path;
        File currentDir;
        if (args.length != 0) {
            path = args[0];
        }
        else {
            path = "./";
        }
        currentDir = new File(path); // current directory
        for (String file : displayDirectoryContents(currentDir)) {
            App theApp = new App(file);
            Path thePath = Paths.get(file);
            Path pathOut = Paths.get(file + ".stats");
            List<String> list = theApp.readFile(thePath);
            List<String> output = new ArrayList<String>();
            System.out.println("**** FILE: " + file);
            System.out.println("Length of longest line in file: ");
            System.out.println(theApp.longestLine(list));
            System.out.println("Average line length: ");
            System.out.println(theApp.averageLength(list));
            System.out.println("Number of unique space-delineated tokens (case-sensitive): ");
            System.out.println(theApp.uniqueCaseSensitiveTokens(list));
            System.out.println("Number of unique space-delineated tokens (case-insensitive): ");
            System.out.println(theApp.uniqueCaseInsensitiveTokens(list));
            System.out.println("Number of all space-delineated tokens in file: ");
            System.out.println(theApp.numTokens(list));
            System.out.println("Most frequently occurring token(s): ");
            System.out.println(theApp.mostFrequentTokens(list));
            System.out.println("Count of most frequently occurring token (case-insensitive): ");
            System.out.println(theApp.mostFrequentTokensNum(list));
            System.out.println("Ten most frequent tokens with their counts (case-insensitive): ");
            System.out.println(theApp.tenMostFrequent(list));
            System.out.println("Ten least frequent tokens with their counts (case-insensitive): ");
            System.out.println(theApp.tenLeastFrequent(list));
            output.add("**** FILE: " + file);
            output.add("Length of longest line in file: ");
            output.add(Integer.toString(theApp.longestLine(list)));
            output.add("Average line length: ");
            output.add(Float.toString(theApp.averageLength(list)));
            output.add("Number of unique space-delineated tokens (case-sensitive): ");
            output.add(Integer.toString(theApp.uniqueCaseSensitiveTokens(list)));
            output.add("Number of unique space-delineated tokens (case-insensitive): ");
            output.add(Integer.toString(theApp.uniqueCaseInsensitiveTokens(list)));
            output.add("Number of all space-delineated tokens in file: ");
            output.add(Integer.toString(theApp.numTokens(list)));
            output.add("Most frequently occurring token(s): ");
            output.add(theApp.mostFrequentTokens(list));
            output.add("Count of most frequently occurring token (case-insensitive): ");
            output.add(Integer.toString(theApp.mostFrequentTokensNum(list)));
            output.add("Ten most frequent tokens with their counts (case-insensitive): ");
            output.add((theApp.tenMostFrequent(list)));
            output.add("Ten least frequent tokens with their counts (case-insensitive): ");
            output.add(theApp.tenLeastFrequent(list));
            theApp.writeToFile(pathOut, output);
        }
    }
}





package com.gilllambrigts.exercise;

import java.util.StringJoiner;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class LetterTool {

    private static int maxNumberOfCharacters;
    private static ArrayList<String> extractedPartList;
    private static ArrayList<String> extractedWordList;
    private static ArrayList<String> importedStringList;
    private static ArrayList<String> outputList;
    private static ArrayList<String> outputWordList;

    private static String generatedOutput;


    public void init() {
        maxNumberOfCharacters = 0;
        extractedWordList = new ArrayList<String>();
        importedStringList = new ArrayList<String>();
        extractedPartList = new ArrayList<String>();
        outputList = new ArrayList<String>();
        outputWordList = new ArrayList<String>();

    }

    public void importFromFile(String path) throws IOException {
        init();
        try {
            BufferedReader objReader = new BufferedReader(new FileReader(path));
            String strCurrentLine = "";
            while ((strCurrentLine = objReader.readLine()) != null) {
                importedStringList.add(strCurrentLine);
            }
            System.out.println("File read.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importFromString(String inputString) {
        init();
        //Perform a check whether the generatedList is empty.}
        if (inputString.length() == 0) {
            System.out.println("Empty input");
        }

        importedStringList = new ArrayList<String>(Arrays.asList(inputString.split("\\s+")));
        System.out.println(importedStringList);
        System.out.println("Data read.");
    }

    public String runLetterTool() {

        //Perform a check whether the generatedList is empty.
        if (importedStringList.size() == 0) {
            System.out.println("Empty input");
        }

        //We will assume that the longest string in the list will be a word and that all words that need to be found are of that length.
        maxNumberOfCharacters = findHighestCharacterCount(importedStringList);

        extractCompleteWordsAndParts(importedStringList);
        combinePartsIntoWord();
        generateStringOutput();
        return getGeneratedString();
    }

    private void extractCompleteWordsAndParts(ArrayList<String> importedStringList) {

        for (int i = 0; i < importedStringList.size(); i++) {
            //Count the characters in the string item
            if (importedStringList.get(i).length() == maxNumberOfCharacters) {
                extractedWordList.add(importedStringList.get(i));
            } else {
                extractedPartList.add(importedStringList.get(i));
            }
        }
        return;
    }

    //Useful when you want to expand the tool to also take higher lengths in consideration.
    private int findHighestCharacterCount(ArrayList<String> inputStringList) {
        int highestLength = inputStringList.get(0).length();
        for (int i = 0; i < inputStringList.size(); i++) {
            int currentLength = inputStringList.get(i).length();
            if (currentLength > highestLength) {
                highestLength = currentLength;
            }
        }
        return highestLength;
    }


    private void combinePartsIntoWord() {
        int extractedPartsListSize = extractedPartList.size();

        String firstPart = "";
        String secondPart = "";
        String joinedWord = "";
        String joinedWordReverse = "";

        for (int i = 0; i < extractedPartsListSize; i++) {
            firstPart = extractedPartList.get(i);
            for (int j = i + 1; j < extractedPartsListSize - i - 1; j++) {
                secondPart = extractedPartList.get(j);
                joinedWord = firstPart + secondPart;
                joinedWordReverse = secondPart + firstPart;

                if (doesListContainWord(joinedWord)) {
                    saveOutputRowToList(generateOutputList(firstPart, secondPart, joinedWord), joinedWord);
                }

                if (doesListContainWord(joinedWordReverse)) {
                    saveOutputRowToList(generateOutputList(secondPart, firstPart, joinedWordReverse), joinedWordReverse);
                }
            }
        }
    }

    private void generateStringOutput() {
        StringJoiner sj = new StringJoiner("");
        for (int i = 0; i < outputList.size() - 1; i++) {
            sj.add(outputList.get(i));
        }
        generatedOutput = sj.toString();
    }

    public String getGeneratedString() {
        return generatedOutput;
    }
    public ArrayList<String> getGeneratedArrayList(){
        return outputList;
    }

    public String generateOutputList(String part1, String part2, String word) {
        String outputRow = part1 + "+" + part2 + "=" + word + "\n";
        return outputRow;
    }

    private void saveOutputRowToList(String row, String word){
        //Make sure to not add duplicates.
        if (!outputWordList.contains(word)) {
            outputList.add(row);
            outputWordList.add(word);
        } else {
        }
    }


    private boolean doesListContainWord(String inputWord) {
        if (extractedWordList.contains(inputWord)) {
            return true;
        } else {
            return false;
        }
    }

    private void saveEntryToDatabase(){
    }

    public void test(){
        saveEntryToDatabase();
    }
}
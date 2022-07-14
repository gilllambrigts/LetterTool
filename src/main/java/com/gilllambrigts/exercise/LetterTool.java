package com.gilllambrigts.exercise;

import java.io.FileNotFoundException;
import java.util.StringJoiner;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class LetterTool {

    private int maxNumberOfCharacters;
    private ArrayList<String> extractedPartList;
    private ArrayList<String> extractedWordList;
    private ArrayList<String> importedStringList;
    private ArrayList<String> outputList;
    private ArrayList<String> outputWordList;

    private static String generatedOutput;

    public void init() {
        maxNumberOfCharacters = 0;
        extractedWordList = new ArrayList<>();
        importedStringList = new ArrayList<>();
        extractedPartList = new ArrayList<>();
        outputList = new ArrayList<>();
        outputWordList = new ArrayList<>();
    }

    public void importFromFile(String path) throws  IOException {
        init();
        try {

            BufferedReader objReader = new BufferedReader(new FileReader(path));
            String strCurrentLine;
            while ((strCurrentLine = objReader.readLine()) != null) {
                importedStringList.add(strCurrentLine);
            }
        } catch(IOException eIO){
            eIO.printStackTrace();
            throw eIO;
        }


    }
    public void importFromString(String inputString) {
        init();
        //Perform a check whether the generatedList is empty.}
        if (inputString.length() == 0) {
            return;
        }

        importedStringList = new ArrayList<>(Arrays.asList(inputString.split("\\s+")));
    }

    public void runLetterTool() {

        //Perform a check whether the generatedList is empty.
        if (importedStringList.size() == 0) {
            System.out.println("Empty input");
        }

        //We will assume that the longest string in the list will be a word and that all words that need to be found are of that length.
        maxNumberOfCharacters = findHighestCharacterCount(importedStringList);

        extractCompleteWordsAndParts(importedStringList);
        combinePartsIntoWord();
        generateStringOutput();
    }

    private void extractCompleteWordsAndParts(ArrayList<String> importedStringList) {

        for (String s : importedStringList) {
            //Count the characters in the string item
            if (s.length() == maxNumberOfCharacters) {
                extractedWordList.add(s);
            } else {
                extractedPartList.add(s);
            }
        }
    }

    //Useful when you want to expand the tool to also take higher lengths in consideration.
    private int findHighestCharacterCount(ArrayList<String> inputStringList) {
        int highestLength = inputStringList.get(0).length();
        for (String s : inputStringList) {
            int currentLength = s.length();
            if (currentLength > highestLength) {
                highestLength = currentLength;
            }
        }
        return highestLength;
    }

    private void combinePartsIntoWord() {
        int extractedPartsListSize = extractedPartList.size();

        String firstPart;
        String secondPart;
        String joinedWord;
        String joinedWordReverse;

        for (int i = 0; i < extractedPartsListSize; i++) {
            firstPart = extractedPartList.get(i);
            for (int j = i + 1; j < extractedPartsListSize - 1; j++) {
                secondPart = extractedPartList.get(j);
                joinedWord = firstPart + secondPart;
                joinedWordReverse = secondPart + firstPart;

                if(!(firstPart.length() + secondPart.length() < maxNumberOfCharacters)){
                    if (doesListContainWord(joinedWord)) {
                        saveOutputRowToList(generateOutputList(firstPart, secondPart, joinedWord), joinedWord);
                    }

                    if (doesListContainWord(joinedWordReverse)) {
                        saveOutputRowToList(generateOutputList(secondPart, firstPart, joinedWordReverse), joinedWordReverse);
                    }
                }
            }
        }

    }

    private void generateStringOutput() {
        StringJoiner sj = new StringJoiner("");
        for (String s : outputList) {
            sj.add(s);
        }
        generatedOutput = sj.toString();
    }

    private String generateOutputList(String part1, String part2, String word) {
        return part1 + "+" + part2 + "=" + word + "\n";
    }

    private void saveOutputRowToList(String row, String word){
        //Make sure to not add duplicates.
        if (!outputWordList.contains(word)) {
            outputList.add(row);
            outputWordList.add(word);
        }
    }

    private boolean doesListContainWord(String inputWord) {
        return extractedWordList.contains(inputWord);
    }

    private void saveEntryToDatabase(){
    }

    public void test(){
        saveEntryToDatabase();
    }

    public String getGeneratedString() {
        return generatedOutput;
    }
    public ArrayList<String> getGeneratedArrayList(){
        return outputList;
    }

    public ArrayList<String> getImportedStringList(){
        return importedStringList;
    }

    public int getLongestWord(){
        return maxNumberOfCharacters;
    }
}
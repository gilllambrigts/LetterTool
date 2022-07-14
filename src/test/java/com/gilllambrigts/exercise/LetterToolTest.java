package com.gilllambrigts.exercise;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LetterToolTest {

    @Test
    void doesFileImportResultInCorrectOutput() throws IOException {
        LetterTool tool = new LetterTool();
        String expectedResult = """
                foo+bar=foobar
                bar+foo=barfoo
                """;

        tool.importFromFile("src/main/resources/samples/testInput.txt");
        tool.runLetterTool();

        assertEquals(tool.getGeneratedString(),expectedResult);
    }

    @Test
    void catchIOExceptionForFileImport(){
        LetterTool tool = new LetterTool();

        //Testing non existent file.
        assertThrows(IOException.class,() -> tool.importFromFile(""));
    }

    @Test
    void doesStringImportResultInCorrectOutput() {
        //Test to see if the input will result in the expected output by using a sample.
        LetterTool tool = new LetterTool();
        String sample = """
                foo
                bar
                foobar
                dqsd
                qsd
                bar
                foo
                bar
                foo
                barfoo
                """;
        String expectedResult = """
                foo+bar=foobar
                bar+foo=barfoo
                """;

        tool.importFromString(sample);
        tool.runLetterTool();

        assertEquals(tool.getGeneratedString(), expectedResult);
    }

    @Test
    void checkIfLongestWordIsCorrect(){
        LetterTool tool = new LetterTool();
        String sample = """
                foobarfoobarfoobar
                bar
                barfoo""";

        tool.importFromString(sample);
        tool.runLetterTool();
        assertEquals(tool.getLongestWord(),18);
    }
}
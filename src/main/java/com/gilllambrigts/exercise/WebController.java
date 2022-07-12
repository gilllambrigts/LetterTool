package com.gilllambrigts.exercise;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WebController {
    LetterTool tool = new LetterTool();

    //Used for development. Reads strings from a file.
    @PostMapping("/api/file")
    public String fileProcess() throws IOException {

        tool.importFromFile("C:\\Users\\PC\\Downloads\\exercise\\src\\main\\resources\\samples\\500.txt");
        tool.runLetterTool();
        return "Data processed";
    }

    @GetMapping("/api/file")
    public String fileResult() {
        return tool.getGeneratedOutput();
    }

    @PostMapping("/api/data")
    public String dataProcess(@RequestBody String data) throws IOException {

        if (data.length() == 0) {
            return "Data is empty.";
        }

        tool.importFromString(data);
        tool.runLetterTool();
        return "Data processed";
    }

    @GetMapping("/api/data")
    public String dataResult() {
        return tool.getGeneratedOutput();
    }
}
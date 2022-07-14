package com.gilllambrigts.exercise;

import com.gilllambrigts.exercise.JpaController.EntryController;
import com.gilllambrigts.exercise.JpaController.WordController;
import com.gilllambrigts.exercise.model.EntryModel;
import com.gilllambrigts.exercise.model.WordModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.StringJoiner;

@RestController
public class WebController {
    @Autowired
    EntryController entryController;
    @Autowired
    WordController wordController;
    LetterTool tool = new LetterTool();

    //Used for development. Reads strings from a file.
    @PostMapping("/api/file")
    public String fileProcess() throws IOException {
        tool.importFromFile("src/main/resources/samples/500.txt");
        tool.runLetterTool();
        return generateSuccessString(saveToDatabase(tool.getGeneratedArrayList()));
    }

    @GetMapping("/api/last")
    public String fileResult() {
        return tool.getGeneratedString();
    }

    //Method that is used to submit data that is passed with the HTTP request.
    @PostMapping("/api/data")
    public String dataProcess(@RequestBody String data)  {
        if (data.length() == 0) {
            return "Data is empty.";
        }
        tool.importFromString(data);
        tool.runLetterTool();
        return generateSuccessString(saveToDatabase(tool.getGeneratedArrayList()));
    }

    //Method that is used to read any entries that have been saved previously.
    @GetMapping("/api/read")
    public String readAPI(@RequestParam("id") String inputId){
        if (inputId.isEmpty()){
            return "id parameter is empty.";
        }

        if(!inputId.matches("\\d*")){
            return "id parameter should be an integer";
        }

        //Generate the output for all the words for a specific id.
        long entryId = Long.parseLong(inputId);
        StringJoiner sj = new StringJoiner("");
        ArrayList<WordModel> resultWordList = wordController.getAllForEntryId(entryId);

        //Check if the result has any values for the given id.
        if(resultWordList.size()==0){
            return "No results found for this id.";
        }

        //Convert the arrayList<wordModel> into a string.
        for (WordModel wordModel : resultWordList) {
            sj.add(wordModel.getWord());
        }
        return sj.toString();
    }

    public long saveToDatabase(ArrayList<String> inputList){
        //Goes through the list of all results and adds those to the database.
        EntryModel entryModel = new EntryModel(LocalDate.now().toString());
        entryController.save(entryModel);

        //Create the correct models from the output list:
        ArrayList<WordModel> partCombinationList = new ArrayList<>();
        long entryModelId = entryModel.getId();

        for (String s : inputList) {
            WordModel model = new WordModel(entryModelId, s);
            partCombinationList.add(model);
        }

        wordController.saveAll(partCombinationList);
        return entryModelId;
    }

    private String generateSuccessString(long entryId){
        return "Data has been processed and saved to a database."
                + "\nYou can consult it in the future by making a GET request to localhost:8080/api/read?id="
                + entryId
                + "\n\nResult:\n"
                + tool.getGeneratedString();
    }
}
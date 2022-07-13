package com.gilllambrigts.exercise;

import com.gilllambrigts.exercise.JpaController.EntryController;
import com.gilllambrigts.exercise.JpaController.WordController;
import com.gilllambrigts.exercise.model.EntryModel;
import com.gilllambrigts.exercise.model.wordModel;
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
        tool.importFromFile("C:\\Users\\PC\\Downloads\\exercise\\src\\main\\resources\\samples\\500.txt");
        tool.runLetterTool();
        return generateSuccessString(saveToDatabase(tool.getGeneratedArrayList()));
    }

    @GetMapping("/api/last")
    public String fileResult() {
        return tool.getGeneratedString();
    }

    @PostMapping("/api/data")
    public String dataProcess(@RequestBody String data) throws IOException {

        if (data.length() == 0) {
            return "Data is empty.";
        }
        tool.importFromString(data);
        tool.runLetterTool();
        return generateSuccessString(saveToDatabase(tool.getGeneratedArrayList()));
    }

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
        ArrayList<wordModel> resultWordList = wordController.getAllForEntryId(entryId);

        //Check if the result has any values for the given id.
        if(resultWordList.size()==0){
            return "No results found for this id.";
        }

        //Convert the arrayList<wordModel> into a string.
        for(int i = 0; i < resultWordList.size() - 1; i++){
            sj.add(resultWordList.get(i).getWord());
        }
        String result = sj.toString();
        return result;
    }

    //Used to execute some tests.
    @GetMapping("/api/test")
    public String test() {
        tool.test();
        return "";
    }

    private long saveToDatabase(ArrayList<String> inputList){
        //Goes through the list of all results and adds those to the database.
        //Returns the entry_id.
        ArrayList<String> output = tool.getGeneratedArrayList();
        EntryModel entryModel = new EntryModel(LocalDate.now().toString());
        entryController.save(entryModel);

        //Create the correct models from the output list:
        ArrayList<wordModel> partCombinationList = new ArrayList<>();
        long entryModelId = entryModel.getId();
        for(int i = 0; i < inputList.size() - 1; i++){
            wordModel model = new wordModel(entryModelId, inputList.get(i));
            partCombinationList.add(model);
        }
        wordController.saveAll(partCombinationList);
        return entryModelId;
    }

    private String generateSuccessString(long entryId){
        String response = "Data has been processed and saved to a database. The ID of this entry is: " + String.valueOf(entryId) + "\n" + tool.returnGeneratedOutput();
        return response;
    }
}
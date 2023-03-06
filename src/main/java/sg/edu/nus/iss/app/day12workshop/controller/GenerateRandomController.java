package sg.edu.nus.iss.app.day12workshop.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// import exception to handle user input below 1 / above 30
// import Generate.java model
import sg.edu.nus.iss.app.day12workshop.exception.RandNoException;
import sg.edu.nus.iss.app.day12workshop.model.Generate;

@Controller
@RequestMapping(path="/rand")
public class GenerateRandomController {
    
    @GetMapping(path="/show")
    public String showRandomForm(Model m) {
        Generate g = new Generate(); //numberVal set as 0
        //change numberVal to user form input
        m.addAttribute("generateObj", g);
        return "generate"; //show generate.html
    }

    // get variable from generate.html
    @GetMapping(path="/generate")
    public String generate(@RequestParam Integer numberVal, Model m) {
        this.randomizeNum(m, numberVal.intValue());
        return "result";
    }

    // get variable from path (URL)
    @GetMapping(path="/generate/{numberVal}")
    public String generateRandByPathVar(@PathVariable Integer numberVal, Model m) {
        this.randomizeNum(m, numberVal.intValue());
        return "result";
    }

    // private method to generate an array list of selected images
    // can be considered "Business Logic"; parked under @Service?

    private void randomizeNum(Model m, int noOfGenerateNo) {
        // max number of numbers generated is 30
        int maxGenNo = 30;
        if (noOfGenerateNo < 1 || noOfGenerateNo > maxGenNo) {
            throw new RandNoException(); //Runtime Exception
        }
        // array length of possible numbers generated is 31 (0 to 30)
        // initialise an array of image names following the format image#.jpg
        String[] imgNumbers = new String[maxGenNo+1];
        for (int i = 0; i <= maxGenNo; i++) {
            imgNumbers[i] = "number" + i + ".jpg";
        }

        // generate set of unique random num
        Random rand = new Random();
        Set<Integer> uniqueResult = new LinkedHashSet<>();
        while(uniqueResult.size() < noOfGenerateNo) {
            int randNumberVal = rand.nextInt(maxGenNo+1);
            uniqueResult.add(randNumberVal);
        }

        // iterate through set and store images in array list
        Iterator<Integer> i = uniqueResult.iterator();
        List<String> selectedImg = new ArrayList<>();
        while(i.hasNext()){ 
            selectedImg.add(imgNumbers[i.next().intValue()]);
        }

        // adds number requested by user and array of selected image names to result
        m.addAttribute("numberRandomNum", noOfGenerateNo);
        m.addAttribute("randNumResult", selectedImg.toArray());
    }
}

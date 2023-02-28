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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.app.day12workshop.exception.RandNoException;
import sg.edu.nus.iss.app.day12workshop.model.Generate;

@Controller
@RequestMapping(path="/rand")
public class GenerateRandomController {
    
    @GetMapping(path="/show")
    public String showRandomForm(Model m) {
        Generate g = new Generate();
        m.addAttribute("generateObj", g);
        return "generate";
    }

    @GetMapping(path="/generate")
    public String generate(@RequestParam Integer numberVal, Model m) {
        this.randomizeNum(m, numberVal.intValue());
        return "result";
    }

    @GetMapping(path="/generate/{numberVal}")
    public String generateRandByPathVar(@RequestParam Integer numberVal, Model m) {
        this.randomizeNum(m, numberVal.intValue());
        return "result";
    }

    private void randomizeNum(Model m, int noOfGenerateNo) {
        int maxGenNo = 30;
        String[] imgNumbers = new String[maxGenNo+1];
        if (noOfGenerateNo < 1 || noOfGenerateNo > maxGenNo) {
            throw new RandNoException();
        }
        
        for (int i = 0; i <= maxGenNo; i++) {
            imgNumbers[i] = "number" + i + ".jpg";
        }

        List<String> selectedImg = new ArrayList<>();
        Random rand = new Random();
        Set<Integer> uniqueResult = new LinkedHashSet<>();
        while(uniqueResult.size() < noOfGenerateNo) {
            int randNumberVal = rand.nextInt(maxGenNo+1);
            uniqueResult.add(randNumberVal);
        }

        Iterator<Integer> i = uniqueResult.iterator();
        Integer currElem = null;
        while(i.hasNext()){
            currElem = i.next();
            selectedImg.add(imgNumbers[currElem.intValue()]);
        }

        m.addAttribute("numberRandomNum", noOfGenerateNo);
        m.addAttribute("randNumResult", selectedImg.toArray());
    }

}

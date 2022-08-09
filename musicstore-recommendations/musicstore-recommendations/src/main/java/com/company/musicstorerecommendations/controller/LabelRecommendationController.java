package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class LabelRecommendationController {

    @Autowired
    private LabelRecommendationRepository repo;


    @RequestMapping(value = "/labelRecommendation", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public LabelRecommendation createLabelRecommendation(@RequestBody LabelRecommendation label) {
        return repo.save(label);
    }
    @RequestMapping(value = "/labelRecommendation", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<LabelRecommendation> getAllLabelRecommendations(){
        return repo.findAll();
    }

    @RequestMapping(value = "/labelRecommendation/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public LabelRecommendation findOneLabelRecommendation(@PathVariable Integer id){
        Optional<LabelRecommendation> label = repo.findById(id);

        if (label.isPresent() == false) {

            throw new IllegalArgumentException("Wrong id");

        } else {

            return label.get();
        }
    }
    @RequestMapping(value = "/labelRecommendation/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public LabelRecommendation updateLabelRecommendation(@RequestBody LabelRecommendation label, @PathVariable Integer id){
        if (label.getId() == null) {
            label.setId(id);
        } else if (label.getId() != id) {
            throw new IllegalArgumentException("Ids do not match.");
        }
        return repo.save(label);

    }
    @RequestMapping(value = "/labelRecommendation/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneLabelRecommendation(@PathVariable Integer id) {
        repo.deleteById(id);


    }


}

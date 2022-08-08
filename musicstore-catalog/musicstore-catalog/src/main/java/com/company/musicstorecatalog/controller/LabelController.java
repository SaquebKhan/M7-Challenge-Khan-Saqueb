package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class LabelController {
    @Autowired
    private LabelRepository repo;

    @RequestMapping(value = "/label", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Label createLabel(@RequestBody Label label) {
        return repo.save(label);
    }

    @RequestMapping(value = "/label", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Label> getAllLabels(){
        return repo.findAll();
    }
    @RequestMapping(value = "/label/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Label findOneLabel(@PathVariable Integer id){
        Optional<Label> label = repo.findById(Math.toIntExact(Long.valueOf(id)));
        //if the customer is there we get the customer
        if (label.isPresent() == false) {
            //or throw an error
            throw new IllegalArgumentException("invalid id");

        } else {
            //customer gotten
            return label.get();
        }
    }
    @RequestMapping(value = "/label/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public Label updateLabel(@RequestBody Label label, @PathVariable Integer id){

        if (label.getId() == null) {
            label.setId(id);
        } else if (label.getId() != id) {
            throw new IllegalArgumentException("Error, ID do not match");
        }
        return repo.save(label);

    }
    @RequestMapping(value = "/label/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //passing the id in to delete
    public void deleteOneLabel(@PathVariable Integer id) {
        repo.deleteById(Math.toIntExact(Long.valueOf(id)));

    }

}
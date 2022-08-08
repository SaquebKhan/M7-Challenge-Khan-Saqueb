package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import java.time.LocalDate;
@RestController
public class ArtistRecommendationController {
    @Autowired
    private ArtistRecommendationRepository repo;


    @RequestMapping(value = "/artistRecommendation", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ArtistRecommendation createArtistRecommendation(@Valid @RequestBody ArtistRecommendation album) {
        return repo.save(album);
    }
    @RequestMapping(value = "/artistRecommendation", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<ArtistRecommendation> getAllArtistRecommendations(){
        return repo.findAll();
    }

    @RequestMapping(value = "/artistRecommendation/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ArtistRecommendation findOneArtistRecommendation(@PathVariable Integer id){
        Optional<ArtistRecommendation> artist = repo.findById(id);

        if (artist.isPresent() == false) {

            throw new IllegalArgumentException("invalid id");

        } else {

            return artist.get();
        }
    }
    @RequestMapping(value = "/artistRecommendation/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public ArtistRecommendation updateArtistRecommendation(@RequestBody ArtistRecommendation artist, @PathVariable Integer id){
        if (artist.getId() == null) {
            artist.setId(id);
        } else if (artist.getId() != id) {
            throw new IllegalArgumentException("Ids don't match.");
        }
        return repo.save(artist);

    }
    @RequestMapping(value = "/artistRecommendation/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneArtistRecommendation(@PathVariable Integer id) {
        repo.deleteById(id);

    }
}

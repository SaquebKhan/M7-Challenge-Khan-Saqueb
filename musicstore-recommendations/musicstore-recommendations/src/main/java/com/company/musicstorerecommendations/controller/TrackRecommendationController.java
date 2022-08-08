package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@RestController
public class TrackRecommendationController {

    @Autowired
    private TrackRecommendationRepository repo;


    @RequestMapping(value = "/trackRecommendation", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public TrackRecommendation createTrackRecommendation(@Valid @RequestBody TrackRecommendation album) {
        return repo.save(album);
    }
    @RequestMapping(value = "/trackRecommendation", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<TrackRecommendation> getAllTrackRecommendations(){
        return repo.findAll();
    }

    @RequestMapping(value = "/trackRecommendation/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public TrackRecommendation findOneTrackRecommendation(@PathVariable Integer id){
        Optional<TrackRecommendation> track = repo.findById(id);

        if (track.isPresent() == false) {

            throw new IllegalArgumentException("invalid id");

        } else {

            return track.get();
        }
    }
    @RequestMapping(value = "/trackRecommendation/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public TrackRecommendation updateTrackRecommendation(@RequestBody TrackRecommendation track, @PathVariable Integer id){
        if (track.getId() == null) {
            track.setId(id);
        } else if (track.getId() != id) {
            throw new IllegalArgumentException("Ids don't match.");
        }
        return repo.save(track);

    }
    @RequestMapping(value = "/trackRecommendation/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneTrackRecommendation(@PathVariable Integer id) {
        repo.deleteById(id);


    }




}

package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.LabelRepository;
import com.company.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TrackController {
    @Autowired
    private TrackRepository repo;

    @RequestMapping(value = "/track", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Track createTrack(@RequestBody Track track) {
        track=repo.save(track);
        return track;
    }

    @RequestMapping(value = "/track", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Track> getAllTracks(){
        return repo.findAll();
    }
    @RequestMapping(value = "/track/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Track findOneTrack(@PathVariable Integer id){
        Optional<Track> track = repo.findById(id);
        if (track.isPresent() == false) {
            throw new IllegalArgumentException("invalid id");
        } else {
            return track.get();
        }
    }
    @RequestMapping(value = "/track/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public Track updateTrack(@RequestBody Track track, @PathVariable Integer id){

        if (track.getId() == null) {
            track.setId(id);
        } else if (track.getId() != id) {
            throw new IllegalArgumentException("Error, ID do not match");
        }
        return repo.save(track);

    }
    @RequestMapping(value = "/track/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //passing the id in to delete
    public void deleteOneLabel(@PathVariable Integer id) {
        repo.deleteById(id);

    }

}


package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ArtistController {
    @Autowired
    private ArtistRepository repo;

    @RequestMapping(value = "/artist", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Artist createArtist(@RequestBody Artist artist) {
        return repo.save(artist);
    }

    @RequestMapping(value = "/artist", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Artist> getAllArtists(){
        return repo.findAll();
    }


    @RequestMapping(value = "/artist/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Artist findOneArtist(@PathVariable Integer id){
        Optional<Artist> artist = repo.findById(id);
        //if the customer is there we get the customer
        if (artist.isPresent() == false) {
            //or throw an error
            throw new IllegalArgumentException("invalid id");

        } else {
            //customer gotten
            return artist.get();
        }
    }
    @RequestMapping(value = "/artist/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public Artist updateArtist(@RequestBody Artist artist, @PathVariable Integer id){

        if (artist.getId() == null) {
            artist.setId(id);
        } else if (artist.getId() != id) {
            throw new IllegalArgumentException("Error, ID do not match");
        }
        return repo.save(artist);

    }
    @RequestMapping(value = "/artist/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //passing the id in to delete
    public void deleteOneArtist(@PathVariable Integer id) {
        repo.deleteById(id);

    }

}

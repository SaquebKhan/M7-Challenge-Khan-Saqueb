package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Album;

import com.company.musicstorecatalog.repository.AlbumRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

@RestController
public class AlbumController {
    @Autowired
    private AlbumRepository repo;

    @RequestMapping(value = "/album", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Album createAlbum(@Valid @RequestBody Album album) {
        return repo.save(album);
    }

    @RequestMapping(value = "/album", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Album> getAllAlbum(){
        return repo.findAll();
    }
    @RequestMapping(value = "/album/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Album findOneAlbum(@PathVariable Integer id){
        Optional<Album> album = repo.findById(id);

        if (album.isPresent() == false) {

            throw new IllegalArgumentException("invalid id");

        } else {

            return album.get();
        }
    }
    @RequestMapping(value = "/album/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public Album updateAlbum(@RequestBody Album album, @PathVariable Integer id){

        if (album.getId() == null) {
            album.setId(id);
        } else if (album.getId() != id) {
            throw new IllegalArgumentException("Error, ID do not match");
        }
        return repo.save(album);

    }
    @RequestMapping(value = "/album/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //passing the id in to delete
    public void deleteOneLabel(@PathVariable Integer id) {
        repo.deleteById(id);

    }

}

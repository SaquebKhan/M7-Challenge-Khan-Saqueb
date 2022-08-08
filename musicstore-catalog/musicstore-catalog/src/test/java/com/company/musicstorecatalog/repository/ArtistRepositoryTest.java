package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Artist;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtistRepositoryTest {

    @Autowired
    private ArtistRepository repo;
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private TrackRepository trackRepository;

    @Before
    public void setUp() throws Exception {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        labelRepository.deleteAll();
        repo.deleteAll();
    }

    @Test
    public void shouldShouldInteractWithDatabaseTable() {
        Artist artist = new Artist("Kanye", "@Kanye", "@JayZ");
        repo.save(artist);
        Artist expectedArtist = new Artist(artist.getId(), "Kanye", "@Kanye", "@JayZ");


        assertEquals(expectedArtist.toString(), artist.toString());


        List<Artist> allTheArtist = repo.findAll();


        assertEquals(1, allTheArtist.size());


        repo.deleteById(artist.getId());

        allTheArtist = repo.findAll();
        assertEquals(0, allTheArtist.size());
    }
}
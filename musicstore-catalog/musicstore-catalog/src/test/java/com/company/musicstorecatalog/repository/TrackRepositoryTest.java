package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.model.Track;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackRepositoryTest {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private TrackRepository repo;
    @Autowired
    private AlbumRepository albumRepository;
    @Before
    public void setUp() throws Exception {
        repo.deleteAll();
        albumRepository.deleteAll();
        labelRepository.deleteAll();
        artistRepository.deleteAll();
    }

    @Test
    public void shouldShouldInteractWithDatabaseTable() {
        Label label = new Label("Music LLP", "www.MusicLLP.com");
        labelRepository.save(label);
        Label expectedLabel = new Label(label.getId(), "Music LLP", "www.MusicLLP.com");

        Artist artist = new Artist("naruto", "@naruto", "@naruto");
        artistRepository.save(artist);
        Artist expectedArtist = new Artist(artist.getId(),"naruto", "@naruto", "@naruto");

        Album album = new Album("Halo 3", artist.getId(), LocalDate.of(2003, 11, 14), label.getId(), new BigDecimal("343.99"));
        albumRepository.save(album);
        Album expectedAlbum = new Album(album.getId(), "Halo 3", artist.getId(), LocalDate.of(2003, 11, 14), label.getId(), new BigDecimal("777.99"));


        Track track = new Track(album.getId(), "Finish the Fight",358);
        repo.save(track);
        Track expectedTrack = new Track(track.getId(), album.getId(), "Finish the Fight",358);



        expectedTrack.setId(track.getId());
        assertEquals(expectedTrack.toString(), track.toString());

        // Act
        List<Track> allTheTrack = repo.findAll();

        // Assert
        assertEquals(1, allTheTrack.size());

        // Act
        repo.deleteById(track.getId());

        allTheTrack = repo.findAll();
        assertEquals(0, allTheTrack.size());
    }
}
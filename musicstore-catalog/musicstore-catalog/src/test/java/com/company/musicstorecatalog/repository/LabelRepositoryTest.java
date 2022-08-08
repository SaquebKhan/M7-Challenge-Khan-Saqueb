package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
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
public class LabelRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Before
    public void setUp() throws Exception {
        labelRepository.deleteAll();
        artistRepository.deleteAll();
        albumRepository.deleteAll();
        trackRepository.deleteAll();

    }

    @Test
    public void shouldShouldInteractWithDatabaseTable() {

        Label label = new Label("Music LLP", "www.MusicLLP.com");
        labelRepository.save(label);
        Label expectedLabel = new Label(label.getId(), "Music LLP", "www.MusicLLP.com");

        assertEquals(expectedLabel.toString(), label.toString());

        List<Label> allTheLabel = labelRepository.findAll();


        assertEquals(1, allTheLabel.size());


        labelRepository.deleteById(label.getId());

        allTheLabel = labelRepository.findAll();
        assertEquals(0, allTheLabel.size());
    }
}

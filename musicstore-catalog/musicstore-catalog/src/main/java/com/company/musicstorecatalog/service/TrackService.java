package com.company.musicstorecatalog.service;

import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackService {

    private TrackRepository trackRepository;

    @Autowired
    public void setTrackRepository(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<Track> findAllTrack() {
        return trackRepository.findAll();
    }

    public Track findTrackById(long id) {
        Optional<Track> track = trackRepository.findById((int) id);
        if (track.isPresent()) {
            return track.get();
        } else throw new RuntimeException("Track with this ID does not exist");
    }

    public Track createTrack(Track newTrack) {
        return trackRepository.save(newTrack);
    }

    public Track updateTrack(Track track) {
        Optional<Track> oldTrack = trackRepository.findById(track.getId());
        if (oldTrack.isPresent()) {
            track.setId(oldTrack.get().getId());
            return trackRepository.save(track);
        } else throw new RuntimeException("Track with that ID does not exist");
    }

    public void deleteTrackById(long id) {
        trackRepository.deleteById((int) id);
    }
}

package ar.edu.unnoba.pdyc.mymusic.service;

import ar.edu.unnoba.pdyc.mymusic.model.Song;
import org.jvnet.hk2.annotations.Service;

import java.util.List;

@Service
public interface SongService {
    Song create(Song song);
    void delete(Long id);
    List<Song> findAll();

    Song getSongId(Long id);
}

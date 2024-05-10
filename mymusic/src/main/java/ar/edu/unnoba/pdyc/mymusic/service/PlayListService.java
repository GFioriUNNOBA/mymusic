package ar.edu.unnoba.pdyc.mymusic.service;

import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.Song;
import org.jvnet.hk2.annotations.Service;

import java.util.List;

@Service
public interface PlayListService {
    Playlist create(Playlist playlist);
    void delete(Long id);
    List<Playlist> findAll();

}

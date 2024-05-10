package ar.edu.unnoba.pdyc.mymusic.service;

import ar.edu.unnoba.pdyc.mymusic.model.Song;
import ar.edu.unnoba.pdyc.mymusic.repository.SongRepository;
import org.springframework.stereotype.Service; //puede surgir un error por esto
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SongServiceImp implements SongService {
    @Autowired
    private final SongRepository songRepository;

    @Autowired
    public SongServiceImp(SongRepository songRepository) {
        this.songRepository = songRepository;
    }


    @Override
    public Song create(Song song) {
        return songRepository.save(song);
    }

    @Override
    public void delete(Long id) {
        songRepository.deleteById(id);

    }

    @Override
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public Song getSongId(Long id) {
        return songRepository.findSongById(id);
    }
}

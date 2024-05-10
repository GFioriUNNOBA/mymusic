package ar.edu.unnoba.pdyc.mymusic.service;

import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.Song;
import ar.edu.unnoba.pdyc.mymusic.repository.PlayListRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service; //puede surgir un error por esto
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class PlayListServiceImp implements PlayListService {
    private final PlayListRepository playListRepository;

    @Autowired
    public PlayListServiceImp(PlayListRepository playListRepository) {
        this.playListRepository = playListRepository;
    }

    @Override

    public Playlist create(Playlist playlist) {
        return playListRepository.save(playlist);
    }

    @Override
    public void delete(Long id) {
        playListRepository.deleteById(id);

    }

    @Override
    @Transactional
    public List<Playlist> findAll() {
        return playListRepository.findAll();
    }


}

package ar.edu.unnoba.pdyc.mymusic.repository;

import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayListRepository extends JpaRepository<Playlist,Long> {
    Playlist findAllById(Long id);

}


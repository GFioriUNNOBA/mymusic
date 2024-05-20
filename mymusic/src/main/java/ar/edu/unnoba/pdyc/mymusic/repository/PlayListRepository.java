package ar.edu.unnoba.pdyc.mymusic.repository;

import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PlayListRepository extends JpaRepository<Playlist,Long> {
    Playlist findAllById(Long id);


    @Query ("select p.name from playlist p where p.id=:id")
    public String getNameById (@Param("id") long id);

    @Query ("select p.user.id from playlist p where p.id=:id")
    public long getOwner (@Param("id") long id);

}


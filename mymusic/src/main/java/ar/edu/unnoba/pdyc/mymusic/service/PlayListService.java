package ar.edu.unnoba.pdyc.mymusic.service;

import ar.edu.unnoba.pdyc.mymusic.dto.PlayListDto;
import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.Song;
import org.jvnet.hk2.annotations.Service;

import java.util.List;

@Service
public interface PlayListService {
    Playlist create(PlayListDto playlist, String mail);
    void delete(Long id);
    List<Playlist> findAll();
    public void deleteSong(long idPlaylist, long idSong, String loggedEmail) throws Exception ;


    List<Song> getSongsByPlaylistId(long id);
}

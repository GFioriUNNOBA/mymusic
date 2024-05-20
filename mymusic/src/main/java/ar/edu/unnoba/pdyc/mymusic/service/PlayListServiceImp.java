package ar.edu.unnoba.pdyc.mymusic.service;

import ar.edu.unnoba.pdyc.mymusic.dto.PlayListDto;
import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.Song;
import ar.edu.unnoba.pdyc.mymusic.model.Usuario;
import ar.edu.unnoba.pdyc.mymusic.repository.PlayListRepository;
import ar.edu.unnoba.pdyc.mymusic.repository.SongRepository;
import ar.edu.unnoba.pdyc.mymusic.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Service; //puede surgir un error por esto
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayListServiceImp implements PlayListService {
    private final PlayListRepository playListRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    @Autowired
    public PlayListServiceImp(PlayListRepository playListRepository, SongRepository songRepository, UserRepository userRepository) {
        this.playListRepository = playListRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }



    @Override
    public Playlist create(PlayListDto playListDto, String mail) {
        Usuario userLogged = userRepository.findByEmail(mail);
        Playlist playlist = new Playlist();
        playlist.setName(playListDto.getName());
        playlist.setUser(userLogged);
        playListRepository.save(playlist);
        return playlist;
    }

    @Override
    public void delete(Long id) {
        playListRepository.deleteById(id);

    }

    @Override
    public void deleteSong(long idPlaylist, long idSong, String loggedEmail) throws Exception {
        Usuario userLogged = userRepository.findByEmail(loggedEmail);
        Playlist playlistBD = playListRepository.findById(idPlaylist).get();
        Usuario ownerPlaylist = userRepository.findById(playlistBD.getUser().getId()).get();
        if(ownerPlaylist.equals(userLogged)){
            playListRepository.deleteById(idPlaylist);
            songRepository.deleteById(idSong);
        } else {
            throw new Exception("no podes borrar una cancion a una playlist de la que no sos el dueño");
        }
    }

    @Override
    @Transactional
    public List<Playlist> findAll() {
        return playListRepository.findAll();
    }


    @Override
    public List<Song> getSongsByPlaylistId(long id) {
        Optional<Playlist> optionalPlaylist = playListRepository.findById(id);
        if (optionalPlaylist.isPresent()) {
            return new ArrayList<>(optionalPlaylist.get().getSongs());
        } else {
            return new ArrayList<>();
        }
    }


}

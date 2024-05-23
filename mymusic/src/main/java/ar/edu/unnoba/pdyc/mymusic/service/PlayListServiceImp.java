package ar.edu.unnoba.pdyc.mymusic.service;

import ar.edu.unnoba.pdyc.mymusic.dto.PlayListDto;
import ar.edu.unnoba.pdyc.mymusic.exception.ResourceNotFoundException;
import ar.edu.unnoba.pdyc.mymusic.exception.UnauthorizedException;
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

    private final SongService songService;
    private final UserRepository userRepository;
    @Autowired
    public PlayListServiceImp(PlayListRepository playListRepository, SongRepository songRepository, SongService songService, UserRepository userRepository) {
        this.playListRepository = playListRepository;
        this.songRepository = songRepository;
        this.songService = songService;
        this.userRepository = userRepository;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteSong(long idPlaylist, long idSong, String loggedEmail) throws Exception {

    }


    public Playlist create(PlayListDto playListDto, String userEmail) {
        Usuario user = userRepository.findByEmail(userEmail);
        Playlist playlist = new Playlist();
        playlist.setName(playListDto.getName());
        playlist.setUser(user);
        return playListRepository.save(playlist);
    }

    public void updatePlaylistName(Long playlistId, String newName, String userEmail) {
        Playlist playlist = getOwnedPlaylist(playlistId, userEmail);
        playlist.setName(newName);
        playListRepository.save(playlist);
    }

    public void addSongToPlaylist(Long playlistId, Long songId, String userEmail) {
        Playlist playlist = getOwnedPlaylist(playlistId, userEmail);
        Song song = songRepository.findSongById(songId);
        playlist.getSongs().add(song);
        playListRepository.save(playlist);
    }

    public void removeSongFromPlaylist(Long playlistId, Long songId, String userEmail) {
        Playlist playlist = getOwnedPlaylist(playlistId, userEmail);
        Song song = songService.getSongId(songId);
        playlist.getSongs().remove(song);
        playListRepository.save(playlist);
    }

    public void deletePlaylist(Long playlistId, String userEmail) {
        Playlist playlist = getOwnedPlaylist(playlistId, userEmail);
        playListRepository.delete(playlist);
    }

    private Playlist getOwnedPlaylist(Long playlistId, String userEmail) {
        Playlist playlist = playListRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));

        if (!playlist.getUser().getEmail().equals(userEmail)) {
            throw new UnauthorizedException("You are not allowed to modify this playlist");
        }

        return playlist;
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

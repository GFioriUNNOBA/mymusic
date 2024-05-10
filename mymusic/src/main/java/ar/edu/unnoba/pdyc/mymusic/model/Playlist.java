package ar.edu.unnoba.pdyc.mymusic.model;

import ar.edu.unnoba.pdyc.mymusic.dto.SongDto;
import jakarta.persistence.*;
import java.util.List;

@Entity(name = "playlist")

public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String name;
    @ManyToMany
    @JoinTable(name = "playlist_songs")
    private List<Song> songs;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}

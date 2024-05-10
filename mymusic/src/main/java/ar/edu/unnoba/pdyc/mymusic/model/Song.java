package ar.edu.unnoba.pdyc.mymusic.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "autor")
    private String autor;

    @Column(name = "genero")
    @Enumerated(EnumType.STRING)
    private  Genre genero;
    @ManyToMany(mappedBy = "songs")
    private List<Playlist> playlists;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Genre getGenero() {
        return genero;
    }

    public void setGenero(Genre genero) {
        this.genero = genero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }


}

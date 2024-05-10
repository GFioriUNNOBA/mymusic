package ar.edu.unnoba.pdyc.mymusic.dto;

import ar.edu.unnoba.pdyc.mymusic.model.Song;

import java.util.List;

public class PlayListDto {
    private Long id;
    private String name;

    public int getCantidadDeCanciones() {
        return cantidadDeCanciones;
    }

    public void setCantidadDeCanciones(int cantidadDeCanciones) {
        this.cantidadDeCanciones = cantidadDeCanciones;
    }

    private int cantidadDeCanciones;

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
    }





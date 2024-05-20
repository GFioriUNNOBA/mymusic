package ar.edu.unnoba.pdyc.mymusic.resource;

import ar.edu.unnoba.pdyc.mymusic.dto.PlayListDto;

import ar.edu.unnoba.pdyc.mymusic.dto.SongDto;
import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.Song;
import ar.edu.unnoba.pdyc.mymusic.repository.PlayListRepository;
import ar.edu.unnoba.pdyc.mymusic.service.PlayListService;
import ar.edu.unnoba.pdyc.mymusic.service.SongService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Map;



@Component
@Path("/playlists")
public class PlayListResource{

    @Autowired
    private PlayListService playlistService;

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private SongService songService;
//me quedaria hacer un poco de control de exepciones
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists() {
        ModelMapper modelMapper = new ModelMapper();
        List<Playlist> playlists = playlistService.findAll();
        List<PlayListDto> playListDtoList =  playlists.stream().map(playlist ->{
            PlayListDto playListDto =  modelMapper.map(playlist,PlayListDto.class);
            playListDto.setName(playlist.getName());
            int songSize= playlist.getSongs().size();
            playListDto.setCantidadDeCanciones(songSize);

            return playListDto;
        }).toList();
        return Response.ok(playListDtoList).build();
    }

    @POST
    @Path("/new") // se le agrego que el usuario tenga que estar loggueado para poder crar la playlist
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPlaylist(@RequestBody PlayListDto playListDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //contexto de seguridad de spring
        String loggedEmail = (String) auth.getPrincipal();   //email del usuario loggeado
        ModelMapper modelMapper = new ModelMapper();
        Playlist playlist = modelMapper.map(playListDto, Playlist.class);
        playlistService.create(playListDto,loggedEmail);
        playListDto = modelMapper.map(playlist, PlayListDto.class);
        playListDto.setName(playlist.getName());
        playListDto.setId(playlist.getId());
        return Response.ok(playListDto).build();
    }

    @POST
    @Path("/{idP}/songs")
    @Transactional //para la list del ManyToMany
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSong(@PathParam("idP") Long idP, @RequestBody Map<String, Object> requestBody) {
        Integer songId = (Integer) requestBody.get("songId");
        Long songIdLong = songId.longValue();

        Playlist playlist = playListRepository.findById(idP).orElse(null);
        if (playlist == null) {
            //playlist no encontrada
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Song song = songService.getSongId(songIdLong);
        if (song == null) {
            //canción no encontrada
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<Song> songList = playlist.getSongs();
        songList.add(song);
        playListRepository.save(playlist);

        ModelMapper modelMapper = new ModelMapper();
        PlayListDto playlistDto = modelMapper.map(playlist, PlayListDto.class);
        playlistDto.setCantidadDeCanciones(playlist.getSongs().size());

        return Response.ok(playlistDto).build(); //cambiar el retorno a songDto
    }

    @DELETE // se le agrego que el usuario tenga que estar loggueado para eliminar la cancion
    @Path("/{idP}/songs/{idS}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSong(@PathParam("idP") Long idP, @PathParam("idS")Long idS) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedEmail = (String) auth.getPrincipal();
        Playlist playlist = playListRepository.findAllById(idP);
        playlistService.deleteSong(idP,idS,loggedEmail);
        ModelMapper modelMapper = new ModelMapper();
        PlayListDto playlistDto = modelMapper.map(playlist, PlayListDto.class);
        playlistDto.setCantidadDeCanciones(playlist.getSongs().size());
        return Response.ok(playlistDto).build();

    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNamePlaylist(@RequestBody Map<String, Object> requestBody,@PathParam("id") Long id){
        String playId = (String) requestBody.get("playId");
        Playlist playlist = playListRepository.findAllById(id);
        playlist.setName(playId);
        ModelMapper modelMapper = new ModelMapper();
        PlayListDto playlistDto = modelMapper.map(playlist, PlayListDto.class);
        playlistDto.setCantidadDeCanciones(playlist.getSongs().size());
        return Response.ok(playlistDto).build();
    }

    @GET
    @Path("/{idPlay}/songs")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response songsList(@PathParam("idPlay") Long idPlay){
        ModelMapper modelMapper = new ModelMapper();
        Playlist playlists = playListRepository.findAllById(idPlay);
        List<Song> songList = playlists.getSongs();
        List<SongDto> songDtoList = songList.stream().map(song -> {
            SongDto songDto = modelMapper.map(songList,SongDto.class);
            songDto.setName(song.getName());
            songDto.setAuthor(song.getAutor());
            songDto.setGenre(song.getGenero());
            songDto.setId(song.getId());
            return songDto;
        }).toList();

        return Response.ok(songDtoList).build();
    }

    @DELETE
    @Path("/{idPlaylist}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("idPlaylist") Long idPlaylist){
        playlistService.delete(idPlaylist);
        return Response.ok(idPlaylist).build();

    }

}

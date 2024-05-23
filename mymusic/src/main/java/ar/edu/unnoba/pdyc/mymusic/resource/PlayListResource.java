package ar.edu.unnoba.pdyc.mymusic.resource;

import ar.edu.unnoba.pdyc.mymusic.dto.PlayListDto;

import ar.edu.unnoba.pdyc.mymusic.dto.SongDto;
import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.Song;
import ar.edu.unnoba.pdyc.mymusic.repository.PlayListRepository;
import ar.edu.unnoba.pdyc.mymusic.service.PlayListService;
import ar.edu.unnoba.pdyc.mymusic.service.PlayListServiceImp;
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
    private PlayListServiceImp playListServiceImp;


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
    @Path("/new") // se le agrego que el usuario tenga que estar loggueado para poder crear la playlist
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

    @PUT
    @Transactional
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNamePlaylist(@PathParam("id") Long id, @RequestBody Map<String, Object> requestBody) {
        String newName = (String) requestBody.get("newName");
        String loggedEmail = getLoggedUserEmail();
        playListServiceImp.updatePlaylistName(id, newName, loggedEmail);
        return Response.ok().build();
    }

    @POST
    @Transactional
    @Path("/{id}/songs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSong(@PathParam("id") Long id, @RequestBody Map<String, Object> requestBody) {
        Long songId = ((Number) requestBody.get("songId")).longValue();
        String loggedEmail = getLoggedUserEmail();
        playListServiceImp.addSongToPlaylist(id, songId, loggedEmail);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @Path("/{playlistId}/songs/{songId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeSong(@PathParam("playlistId") Long playlistId, @PathParam("songId") Long songId) {
        String loggedEmail = getLoggedUserEmail();
        playListServiceImp.removeSongFromPlaylist(playlistId, songId, loggedEmail);
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") Long id) {
        String loggedEmail = getLoggedUserEmail();
        playListServiceImp.deletePlaylist(id, loggedEmail);
        return Response.ok().build();
    }

    private String getLoggedUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (String) auth.getPrincipal();
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



}

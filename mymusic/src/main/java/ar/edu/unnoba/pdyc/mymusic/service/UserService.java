package ar.edu.unnoba.pdyc.mymusic.service;

import ar.edu.unnoba.pdyc.mymusic.model.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    Usuario create(Usuario user);

    void delete(Long id);

    Usuario findByEmail(String email);

    List<Usuario> findAllUser();

}

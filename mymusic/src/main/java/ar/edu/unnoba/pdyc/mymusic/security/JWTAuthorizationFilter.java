package ar.edu.unnoba.pdyc.mymusic.security;

import ar.edu.unnoba.pdyc.mymusic.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

import static ar.edu.unnoba.pdyc.mymusic.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING); //consigo authorization del header
        //si no esta authorization o el header no empieza con el prefijo de token correcto, no lo autorizo
        if (header == null || !header.startsWith(TOKEN_PREFIX)){
            chain.doFilter(request,response); //continuar cadena de filtros
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);  //consigo la auntenticacion
        SecurityContextHolder.getContext().setAuthentication(authentication); //seteo la autenticacion en el contexto de spring security
        chain.doFilter(request,response); //continuo cadena de filtros
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String userEmail = JWT.require(Algorithm.HMAC256(SECRET.getBytes())) // usar HMAC256
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (userEmail != null) {
                return new UsernamePasswordAuthenticationToken(userEmail, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }


}


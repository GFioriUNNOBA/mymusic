package ar.edu.unnoba.pdyc.mymusic.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;
import java.util.Collection;

@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String getUsername(){
        return this.email;
    }


    // roles que tienen los usuarios
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    //nunca van a expirar
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    // nunca se van a bloquear
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    // nunca van a expirar
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    //siempre va a estar habilitado
    @Override
    public boolean isEnabled(){
        return true;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0 );
        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        } else if (!(obj instanceof Usuario)){ //sino no es un user
            return false;
        } else if (((Usuario) obj).id.equals(this.id)){    //mismos id en BD -> mismo objeto
            return true;
        } else {
            return false;
        }
    }
}

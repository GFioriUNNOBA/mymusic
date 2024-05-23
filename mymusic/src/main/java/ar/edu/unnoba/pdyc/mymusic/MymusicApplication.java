package ar.edu.unnoba.pdyc.mymusic;


import ar.edu.unnoba.pdyc.mymusic.model.Usuario;
import ar.edu.unnoba.pdyc.mymusic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MymusicApplication {


	public static void main(String[] args) {
		SpringApplication.run(MymusicApplication.class, args);
	}



}






package ar.com.ada.api.martes258challenges.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.martes258challenges.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Usuario findByUsername(String userName);

    public Usuario findByEmail(String email);

    public List<Usuario> findByUsuarioId(Integer usuarioId);

}
package com.example.SpringCurso.dao;


import com.example.SpringCurso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao {

    @PersistenceContext
    private EntityManager entityManager;


    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario ";
        List<Usuario> resultado = entityManager.createQuery(query).getResultList();
        return resultado;
    }

    @Override
    public void eliminar(long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email= :email";

        List<Usuario> lista = entityManager.createQuery(query).setParameter("email", usuario.getEmail()).getResultList();


        if(lista.isEmpty()){
            return null;
        }
        String passwordHashed =lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        boolean passwordCoincide = argon2.verify(passwordHashed,usuario.getPassword());

        if(passwordCoincide){
            return lista.get(0);
        }
            return null;
    }
}

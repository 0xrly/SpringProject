package com.example.SpringCurso.dao;



import com.example.SpringCurso.models.Usuario;

import java.util.List;

public interface UsuarioDao {
    List<Usuario> getUsuarios();

    void eliminar(long id);

    void registrar(Usuario usuario);


    Usuario obtenerUsuarioPorCredenciales(Usuario usuario);
}

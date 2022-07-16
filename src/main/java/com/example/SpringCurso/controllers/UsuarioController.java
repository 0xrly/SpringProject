package com.example.SpringCurso.controllers;


import com.example.SpringCurso.dao.UsuarioDao;
import com.example.SpringCurso.models.Usuario;
import com.example.SpringCurso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;


    @Autowired
    private JWTUtil jwtUtil;

private boolean validarToken(String token){

    String usuarioID = jwtUtil.getKey(token);

    return usuarioID != null;

    }




    @RequestMapping(value ="api/usuarios1", method = RequestMethod.GET) // El method por defecto es RequestMethod.GET

    public List<Usuario> getUsuarios(@RequestHeader(value ="Authorization") String token) {


     if(!validarToken(token)) {return null;
     }
        return usuarioDao.getUsuarios();
    }


    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)

    public void eliminar(@RequestHeader(value ="Authorization") String token,@PathVariable long id) {

        if(!validarToken(token)) {return;
        }
        usuarioDao.eliminar(id);
    }


    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario) {

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1, usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registrar(usuario);
    }

}

package com.alura.forohub.controller;

import com.alura.forohub.domain.usuario.DatosAutenticacionUsuario;
import com.alura.forohub.domain.usuario.Usuario;
import com.alura.forohub.infra.security.DatosJWTToken;
import com.alura.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    datosAutenticacionUsuario.login(),
                    datosAutenticacionUsuario.clave());

            var usuarioAutenticado = authenticationManager.authenticate(authenticationToken);

            var usuario = (Usuario) usuarioAutenticado.getPrincipal();

            var JWTtoken = tokenService.generarToken(usuario);

            return ResponseEntity.ok(new DatosJWTToken(JWTtoken, usuario.getNombre()));

        } catch (Exception e) {
            System.out.println("ERROR EN LOGIN: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
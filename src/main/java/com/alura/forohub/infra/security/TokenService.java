package com.alura.forohub.infra.security;

import com.alura.forohub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("foro hub")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        if (tokenJWT == null || tokenJWT.isBlank()) {
            throw new RuntimeException("El token no puede estar vacío");
        }
        try {
            Algorithm algoritmo = Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier = JWT.require(algoritmo)
                    .withIssuer("foro hub")
                    .build()
                    .verify(tokenJWT);

            String subject = verifier.getSubject();
            if (subject == null) {
                throw new RuntimeException("Subject no encontrado en el token");
            }
            return subject;
        } catch (JWTVerificationException exception) {
            System.out.println("Error de verificación JWT: " + exception.getMessage());
            throw new RuntimeException("Token JWT inválido o expirado");
        }
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
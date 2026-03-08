package com.alura.forohub.infra.security;

public record DatosJWTToken(
        String jwTtoken,
        String nombre
) {
}
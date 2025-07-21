package com.alura.literalura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record LibroDTO(String titulo,
                       String autor,
                       List<String> idiomas,
                       Double numeroDeDescargas) {
}

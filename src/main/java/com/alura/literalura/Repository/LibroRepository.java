package com.alura.literalura.Repository;

import com.alura.literalura.Modelo.Autor;
import com.alura.literalura.Modelo.Idiomas;
import com.alura.literalura.Modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {
        List<Libro> findByAutorNombre(String nombre);

        @Query("SELECT l FROM Libro l WHERE l.autor.fechaDeNacimiento >= :fechaDeNacimiento")
        List<Libro> buscarPorFechaNacimientoAutor(@Param("fechaDeNacimiento") int fechaDeNacimiento);

        List<Libro> findByIdiomas(Idiomas idioma);

        Optional<Libro> findByTitulo(String titulo);
}

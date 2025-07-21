package com.alura.literalura.Modelo;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;
    private Idiomas idiomas;
    @Column(name = "numero_de_descargas", nullable = false)
    private double numeroDeDescargas;

    public Libro() {}

    public Libro(DatosLibro datos) {
        this.titulo = datos.titulo();
        this.idiomas = Idiomas.fromString(datos.idiomas().get(0));
        this.numeroDeDescargas = datos.numeroDeDescargas();
        if (datos.autor() != null &&  !datos.autor().isEmpty()){
            DatosAutor autorDTO = datos.autor().get(0);
            Autor autor = new Autor();
            autor.setNombre(autorDTO.nombre());
            autor.setFechaDeNacimiento(String.valueOf(autorDTO.fechaDeNacimiento()));
            autor.setFechaDeFallecimiento(String.valueOf(autorDTO.fechaDeFallecimiento()));
            this.autor = autor;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setNumeorDeDescargas(double numeorDeDescargas) {
        this.numeroDeDescargas = numeorDeDescargas;
    }

    public Idiomas getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Idiomas idiomas) {
        this.idiomas = idiomas;
    }

    public double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return titulo;
    }
}

package com.alura.literalura.Principal;

import com.alura.literalura.Modelo.*;
import com.alura.literalura.Repository.LibroRepository;
import com.alura.literalura.Service.ConsumoAPI;
import com.alura.literalura.Service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    @Autowired
    private LibroRepository repositorio;
    private List<Libro> libros;


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ----------------------
                    Elije la opcion a traves de su número:
                    1- Buscar libro por titulo
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un determinado año
                    5- Listar libros por idiomas
                    0- Salir
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibrosPorTitulos();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutoresDesdeFechaDeNacimiento();
                    break;
                case 5:
                    buscarLibrosPorIdiomas();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Coloque una opcion correcta");
            }
        }
    }

    private DatosLibro getDatosLibro() {
        try {
            System.out.println("Ingresa el nombre del libro que desea buscar");
            var nombreLibro = teclado.nextLine();
            var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
            Datos datos = conversor.obtenerDatos(json, Datos.class);
            Optional<DatosLibro> libroBuscado = datos.resultados().stream()
                    .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                    .findFirst();

            return libroBuscado.orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        } catch (NullPointerException e) {
            throw new RuntimeException("Libro no encontrado");        }
    }

    private void buscarLibrosPorTitulos() {
        try {
            DatosLibro datos = getDatosLibro();
            Optional<Libro> libroBuscado = repositorio.findByTitulo(datos.titulo());
            if (libroBuscado.isPresent()) {
                System.out.println("No se puede registrar el mismo libro mas de una vez");
                return;
            }else {
                Libro libro = new Libro(datos);
                repositorio.save(libro);
                System.out.println(
                        "------ LIBRO ------" +
                                "\n Titulo : " + datos.titulo() +
                                "\n Autor: " + datos.autor().stream().findFirst().map(DatosAutor::nombre).orElse("Desconocido") +
                                "\n Idioma: " + datos.idiomas().toString().replace("[", "").replace("]", "") +
                                "\n Numero de Descargas: " + datos.numeroDeDescargas() +
                                "\n--------------------\n");
            }
        }catch ( RuntimeException e){
            e.getMessage();
        }

    }

    private String mostrarLibrosBuscados() {
        libros = repositorio.findAll();
        for (Libro libro : libros) {
            try{
                System.out.println(
                        "------ LIBRO ------" +
                                "\n Titulo : " + libro.getTitulo() +
                                "\n Autor: " + libro.getAutor().getNombre() +
                                "\n Idioma: " + libro.getIdiomas().toString() +
                                "\n Numero de Descargas: " + libro.getNumeroDeDescargas() +
                                "\n--------------------\n");
            }catch (NullPointerException e){
                System.out.println(
                        "------ LIBRO ------" +
                                "\n Titulo : " + libro.getTitulo() +
                                "\n Autor: Desconocido"  +
                                "\n Idioma: " + libro.getIdiomas().toString() +
                                "\n Numero de Descargas: " + libro.getNumeroDeDescargas() +
                                "\n--------------------\n");
            }
        }
        return null;
    }

    private void mostrarAutoresRegistrados() {
        libros = repositorio.findAll();
        for (Libro libro : libros) {
                System.out.println(
                        "Autor: " + libro.getAutor().getNombre() +
                                "\nFecha de nacimiento: " + libro.getAutor().getFechaDeNacimiento() +
                                "\nFecha de fallecimiento: " + libro.getAutor().getFechaDeFallecimiento());
                List<Libro> librosPorAutor = repositorio.findByAutorNombre(libro.getAutor().getNombre());
                System.out.println("libros: " + librosPorAutor + "\n");
        }
    }

    private void buscarAutoresDesdeFechaDeNacimiento() {
        System.out.println("Ingrese el año vivo de autor(es) que desee buscar");
        var respuesta = teclado.nextInt();
        List<Libro> autoresBuscados = repositorio.buscarPorFechaNacimientoAutor(respuesta);
        autoresBuscados.forEach(l-> System.out.println("Autor: "+ l.getAutor().getNombre() +
                "\nFecha de nacimiento: " + l.getAutor().getFechaDeNacimiento() +
                "\nFecha de fallecimiento: " + l.getAutor().getFechaDeFallecimiento() +
                "\nLibros: "+ l.getAutor().getLibros()+"\n"));
        }

    private void buscarLibrosPorIdiomas() {
        System.out.println("Ingresa el idioma para buscar los libros" +
                "\nes- Español" +
                "\nen- Ingles" +
                "\nfr- Frances" +
                "\npt- Portugues");
        var respuesta = teclado.nextLine();
        var idioma = Idiomas.fromString(respuesta);
        List<Libro> libroList = repositorio.findByIdiomas(idioma);
        libroList.forEach(l-> System.out.println("------ LIBRO ------"+
                "\nTitulo : " + l.getTitulo() +
                "\nAutor: " + l.getAutor().getNombre() +
                "\nIdioma: " + l.getIdiomas()+
                "\nNumero de Descarga: "+ l.getNumeroDeDescargas() +
                "\n--------------------\n"));
    }
}





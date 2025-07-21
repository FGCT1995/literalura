package com.alura.literalura.Modelo;

public enum Idiomas {
    es ("es", "Español"),
    en ("en", "Ingles"),
    fr ("fr", "Frances"),
    pt("pt", "Portuges");

    private String IdiomaGutendex;
    private String IdiomaGutendexEspañol;

    Idiomas(String IdiomaGutendex,String IdiomaGutendexEspañol){
        this.IdiomaGutendex = IdiomaGutendex;
        this.IdiomaGutendexEspañol = IdiomaGutendexEspañol;
    }

    public static Idiomas fromString (String text){
        for (Idiomas idiomas : Idiomas.values()){
            if (idiomas.IdiomaGutendex.equalsIgnoreCase(text)){
                return idiomas;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrada: " + text);
    }

//    public static Idiomas fromStringEspañol (String text){
//        for (Idiomas idiomas : Idiomas.values()){
//            if (idiomas.IdiomaGutendexEspañol.equalsIgnoreCase(text)){
//                return idiomas;
//            }
//        }
//        throw new IllegalArgumentException("Ningun idioma encontrada: " + text);
//    }
}

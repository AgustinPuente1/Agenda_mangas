package com.agenda;

import com.agenda.exceptions.MangaAlreadyExistException;

public class Main {
    public static void main(String[] args) {
        // Leer la agenda desde el archivo JSON (si existe)
        Agenda_mangas agenda = new Agenda_mangas();

        
        // Mostrar la agenda leída o recién creada
        System.out.println(agenda.lista_mangas());
        for (Manga manga : agenda.lista_mangas()) {
            System.out.println(manga.getTitulo() + " tiene " + manga.getCant_tengo() + " tomos");
        }
    }
}
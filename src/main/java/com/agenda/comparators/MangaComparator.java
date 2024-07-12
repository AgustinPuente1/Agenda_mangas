package com.agenda.comparators;

import java.util.Comparator;

import com.agenda.Manga;

public class MangaComparator implements Comparator<Manga> {
    @Override
    public int compare(Manga t1, Manga t2) {
        String nombre1 = t1.getTitulo();
        String nombre2 = t2.getTitulo();

        // Intentar convertir los nombres a números
        try {
            int numero1 = Integer.parseInt(nombre1);
            int numero2 = Integer.parseInt(nombre2);

            // Si ambos nombres son números, compararlos como números
            return Integer.compare(numero1, numero2);
        } catch (NumberFormatException e) {
            // Si no se pueden convertir a números, compararlos alfabéticamente
            return nombre1.compareToIgnoreCase(nombre2);
        }
    }
}
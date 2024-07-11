package com.agenda;

import java.util.ArrayList;
import java.util.List;

import com.agenda.exceptions.CantTomosLimitException;
import com.agenda.exceptions.MangaAlreadyExistException;
import com.agenda.exceptions.MangaNotFoundException;
import com.agenda.exceptions.TomoAlreadyExistException;
import com.agenda.exceptions.TomoNotFoundException;

public class Agenda_mangas {
    private List<Manga> agenda;
    private float costo_total;
    private float costo_promedio;
    private float costo_esperado_total;
    private float costo_esperado_unitario;

    public Agenda_mangas(){
        this.agenda = new ArrayList<>();
        if (JSONUtil.leerAgenda() != null) {
            this.agenda = JSONUtil.leerAgenda();
        }
        this.costo_total = costo_total();
        this.costo_promedio = costo_promedio();
        this.costo_esperado_total = costo_esperado_total();
        this.costo_esperado_unitario = costo_esperado_unitario();
    }

    public void crear_manga(String tituloM, int cant_mangas, int cant_tengo) throws MangaAlreadyExistException{
        for (Manga manga : agenda) {
            if (manga.getTitulo().equals(tituloM)) {
                throw new MangaAlreadyExistException();
            }
        }

        Manga manga = new Manga(tituloM, cant_mangas, cant_tengo);

        agenda.add(manga);
        JSONUtil.guardarAgenda(agenda);
    }

    public Manga buscar_manga(String nombrem) throws MangaNotFoundException{
        for (Manga manga : agenda) {
            if (manga.getTitulo().equals(nombrem)) {
                return manga;
            }
        }
        throw new MangaNotFoundException();
    }

    public List<Manga> lista_mangas(){
        return agenda;
    }

    public void agregar_tomos_obtenidos(String titulo, String nombre, float precio) throws MangaNotFoundException, TomoAlreadyExistException, CantTomosLimitException{
        for (Manga manga : agenda) {
            if (manga.getTitulo().equals(titulo)) {
                if (manga.getCant_tengo() <= manga.getLista_tomos_tengo().size()) {
                    throw new CantTomosLimitException();
                }
                for (Tomo tomo : manga.getLista_tomos_tengo()) {
                    if (tomo.getNombre().equals(nombre)) {
                        throw new TomoAlreadyExistException();
                    }
                }
                manga.agregar_tomos_obtenidos(nombre, precio);
                actualizar_totales();
                JSONUtil.guardarAgenda(agenda);
                return;
            }
        }
        throw new MangaNotFoundException();
    }

    public void agregar_tomos_faltantes(String titulo, String nombre, float precio) throws MangaNotFoundException, TomoAlreadyExistException, CantTomosLimitException{
        for (Manga manga : agenda) {
            if (manga.getTitulo().equals(titulo)) {
                if (manga.getCant_faltantes() <= manga.getLista_tomos_faltantes().size()) {
                    throw new CantTomosLimitException();
                }
                for (Tomo tomo : manga.getLista_tomos_faltantes()) {
                    if (tomo.getNombre().equals(nombre)) {
                        throw new TomoAlreadyExistException();
                    }
                }
                manga.agregar_tomos_faltantes(nombre, precio);;
                actualizar_totales();
                JSONUtil.guardarAgenda(agenda);
                return;
            }
        }
        throw new MangaNotFoundException();
    }

    public void actualizar_totales(){
        for (Manga manga : agenda) {
            manga.setPrecio_total(0);
            for (Tomo tomo : manga.getLista_tomos_tengo()) {
                manga.setPrecio_total(manga.getPrecio_total()+tomo.getPrecio());
            }
            manga.setPrecio_promedio(manga.getPrecio_total() / manga.getCant_tengo());

            manga.setPrecio_total_esperado(0);
            for (Tomo tomo : manga.getLista_tomos_faltantes()) {
                manga.setPrecio_total_esperado(manga.getPrecio_total_esperado() + tomo.getPrecio());
            }
            manga.setPrecio_unitario_esperado(manga.getPrecio_total_esperado()/manga.getCant_faltantes());
        }
        costo_total = costo_total();
        costo_promedio = costo_promedio();
        costo_esperado_total = costo_esperado_total();
        costo_esperado_unitario = costo_esperado_unitario();

        JSONUtil.guardarAgenda(agenda);
    }

    public void editar_manga(String titulo, int cant_mangas, int cant_tengo) {
        for (Manga manga : agenda) {
            if (manga.getTitulo().equals(titulo)) {
                manga.setCant_tomos(cant_mangas);
                manga.setCant_tengo(cant_tengo);
                manga.setCant_faltantes(cant_mangas - cant_tengo);
                actualizar_totales();
                JSONUtil.guardarAgenda(agenda);
                return;
            }
        }
    }

    public void editar_tomo_obtenido(String titulos, String nombre, String nuevo_nombre, float precio) {
        for (Manga manga : agenda) {
            if (manga.getTitulo().equals(titulos)) {
                for (Tomo tomo : manga.getLista_tomos_tengo()) {
                    if (tomo.getNombre().equals(nombre)) {
                        tomo.setNombre(nuevo_nombre);
                        tomo.setPrecio(precio);
                        actualizar_totales();
                        JSONUtil.guardarAgenda(agenda);
                        return;
                    }
                }
            }
        }
    }

    public void editar_tomo_faltante(String titulos, String nombre, String nuevo_nombre, float precio) {
        for (Manga manga : agenda) {
            if (manga.getTitulo().equals(titulos)) {
                for (Tomo tomo : manga.getLista_tomos_faltantes()) {
                    if (tomo.getNombre().equals(nombre)) {
                        tomo.setNombre(nuevo_nombre);
                        tomo.setPrecio(precio);
                        actualizar_totales();
                        JSONUtil.guardarAgenda(agenda);
                        return;
                    }
                }
            }
        }
    }

    public void eliminar_manga(String titulo) throws MangaNotFoundException {
        for (Manga manga : agenda) {
            if (manga.getTitulo().equals(titulo)) {
                agenda.remove(manga);
                JSONUtil.guardarAgenda(agenda);
                return;
            }
        }
        throw new MangaNotFoundException();
    }

    public void eliminar_tomo_obtenido(String titulo, String nombre) throws TomoNotFoundException {
        for (Manga manga : agenda) {
            if (manga.getTitulo().equals(titulo)) {
                manga.eliminar_tomo_obtenido(nombre);
                JSONUtil.guardarAgenda(agenda);
                return;
            }
        }
        throw new TomoNotFoundException();
    }

    public void eliminar_tomo_faltante(String titulo, String nombre) throws TomoNotFoundException {
        for (Manga manga : agenda) {
            if (manga.getTitulo().equals(titulo)) {
                manga.eliminar_tomo_faltante(nombre);
                JSONUtil.guardarAgenda(agenda);
                return;
            }
        }
        throw new TomoNotFoundException();
    }

    public float costo_total(){
        costo_total = 0;
        for (Manga manga : agenda) {
            costo_total += manga.getPrecio_total();
        }
        return costo_total;
    }

    public float costo_promedio(){
        int cant_mangas = 0;
        for (Manga manga : agenda) {
            cant_mangas += manga.getCant_tengo();
        }
        
        return costo_total / cant_mangas;
    }

    public float costo_esperado_total(){
        costo_esperado_total = 0;
        for (Manga manga : agenda) {
            costo_esperado_total += manga.getPrecio_total_esperado();
        }
        return costo_esperado_total;
    }

    public float costo_esperado_unitario(){
        int cant_mangas = 0;
        for (Manga manga : agenda) {
            cant_mangas += manga.getCant_faltantes();
        }
        return costo_esperado_total / cant_mangas;
    }






}

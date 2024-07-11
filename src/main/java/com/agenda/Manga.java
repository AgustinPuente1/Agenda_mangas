package com.agenda;

import java.util.ArrayList;
import java.util.List;

import com.agenda.exceptions.CantTomosLimitException;
import com.agenda.exceptions.TomoAlreadyExistException;

public class Manga{
    private String titulo; // X
    private int cant_tomos; // X
    private int cant_tengo; // X
    private List<Tomo> lista_tomos_tengo;
    private float precio_total; 
    private float precio_promedio;
    private int cant_faltantes; // X
    private List<Tomo> lista_tomos_faltantes;
    private float precio_total_esperado;
    private float precio_unitario_esperado;

    public Manga(String titulo, int cant_mangas, int mangas_tengo) {
        this.titulo = titulo;
        this.cant_tomos = cant_mangas;
        this.cant_tengo = mangas_tengo;
        this.lista_tomos_tengo = new ArrayList<>();
        this.precio_total = 0;
        this.precio_promedio = 0;
        this.cant_faltantes = cant_mangas - mangas_tengo;
        this.lista_tomos_faltantes = new ArrayList<>();
        this.precio_total_esperado = 0;
        this.precio_unitario_esperado = 0;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCant_tomos() {
        return cant_tomos;
    }
    public void setCant_tomos(int cant_tomos) {
        this.cant_tomos = cant_tomos;
    }

    public int getCant_tengo() {
        return cant_tengo;
    }
    public void setCant_tengo(int cant_tengo) {
        this.cant_tengo = cant_tengo;
    }

    public float getPrecio_total() {
        return precio_total;
    }
    public void setPrecio_total(float precio_total) {
        this.precio_total = precio_total;
    }

    public float getPrecio_promedio() {
        return precio_promedio;
    }
    public void setPrecio_promedio(float precio_promedio) {
        this.precio_promedio = precio_promedio;
    }

    public int getCant_faltantes() {
        return cant_faltantes;
    }
    public void setCant_faltantes(int cant_faltantes) {
        this.cant_faltantes = cant_faltantes;
    }

    public float getPrecio_total_esperado() {
        return precio_total_esperado;
    }
    public void setPrecio_total_esperado(float precio_total_esperado) {
        this.precio_total_esperado = precio_total_esperado;
    }

    public float getPrecio_unitario_esperado() {
        return precio_unitario_esperado;
    }
    public void setPrecio_unitario_esperado(float precio_unitario_esperado) {
        this.precio_unitario_esperado = precio_unitario_esperado;
    }

    public List<Tomo> getLista_tomos_tengo() {
        return lista_tomos_tengo;
    }

    public List<Tomo> getLista_tomos_faltantes() {
        return lista_tomos_faltantes;
    }

    public void agregar_tomos_obtenidos(String nombre, float precio) throws CantTomosLimitException, TomoAlreadyExistException {
        if (cant_tomos <= lista_tomos_tengo.size()) {
            throw new CantTomosLimitException();
        }
        for (Tomo tomo : lista_tomos_tengo) {
            if (tomo.getNombre().equals(nombre)) {
                throw new TomoAlreadyExistException();
            }
        }
        this.lista_tomos_tengo.add(new Tomo(nombre,precio));
    }

    public void agregar_tomos_faltantes(String nombre, float precio) throws CantTomosLimitException, TomoAlreadyExistException {
        if (cant_tomos <= lista_tomos_faltantes.size()) {
            throw new CantTomosLimitException();
        }
        for (Tomo tomo : lista_tomos_faltantes) {
            if (tomo.getNombre().equals(nombre)) {
                throw new TomoAlreadyExistException();
            }
        }
        this.lista_tomos_faltantes.add(new Tomo(nombre,precio));
    }

    public void eliminar_tomo_obtenido(String nombre){
        for (Tomo tomo : lista_tomos_tengo) {
            if (tomo.getNombre().equals(nombre)) {
                lista_tomos_tengo.remove(tomo);
                return;
            }
        }
    }

    public void eliminar_tomo_faltante(String nombre){
        for (Tomo tomo : lista_tomos_faltantes) {
            if (tomo.getNombre().equals(nombre)) {
                lista_tomos_faltantes.remove(tomo);
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "Manga [titulo=" + titulo + ", cant_tomos=" + cant_tomos + ", cant_tengo=" + cant_tengo
                + ", lista_tomos_tengo=" + lista_tomos_tengo + ", precio_total=" + precio_total + ", precio_promedio="
                + precio_promedio + ", cant_faltantes=" + cant_faltantes + ", lista_tomos_faltantes="
                + lista_tomos_faltantes + ", precio_total_esperado=" + precio_total_esperado
                + ", precio_unitario_esperado=" + precio_unitario_esperado + "]";
    }

    
}
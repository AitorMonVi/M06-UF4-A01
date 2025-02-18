package com.iticbcn.aitormontejo.model;

import java.util.Set;

public class Avion {
    // atributos
    private int idAvion;
    private String modelo;
    private int capacidad;
    private Set<Vuelo> vuelos;

    // constructores
    public Avion() {}

    public Avion(int id, String modelo, int capacidad) {
        setIdAvion(id);
        setModelo(modelo);
        setCapacidad(capacidad);
    }

    public void addVuelo(Vuelo vuelo) {
        if (!this.vuelos.contains(vuelo)) {
            this.vuelos.add(vuelo);
        }
    }

    @Override
    public String toString() {
        return String.format("""
            Avion [
                ID: %d
                Modelo: %s
                Capacidad: %d
            ]
            """, idAvion, modelo, capacidad);
    }

    // getters y setters
    public int getIdAvion() { return idAvion; }
    public void setIdAvion(int id) { this.idAvion = id; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) {
        if (modelo!=null && !modelo.isBlank() && !modelo.isEmpty()) this.modelo = modelo;
        else throw new IllegalArgumentException("¡El modelo que has pasado no es válido!");
    }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { 
        if (capacidad > 0) this.capacidad = capacidad;
        else throw new IllegalArgumentException("¡La capacidad debe ser superior a 0!");
    }

    public Set<Vuelo> getVuelos() { return vuelos; }
    public void setVuelos(Set<Vuelo> vuelos) { this.vuelos = vuelos; }
}

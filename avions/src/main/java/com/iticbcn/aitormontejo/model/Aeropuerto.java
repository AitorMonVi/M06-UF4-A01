package com.iticbcn.aitormontejo.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Aeropuerto")
public class Aeropuerto {
    // atributos
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idAeropuerto;

    @Column(nullable = false, unique = true)
    private String ciudad;

    @OneToMany(mappedBy="origen", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    private Set<Vuelo> vuelosSalida;

    @OneToMany(mappedBy="destino", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    private Set<Vuelo> vuelosLlegada;

    // constructores
    public Aeropuerto() {}

    public Aeropuerto(int id, String ciudad) {
        setId(id);
        setCiudad(ciudad);
    }

    public void addVueloSalida(Vuelo vuelo) {
        if (!this.vuelosSalida.contains(vuelo)) {
            this.vuelosSalida.add(vuelo);
        }
    }

    public void addVueloLlegada(Vuelo vuelo) {
        if (!this.vuelosLlegada.contains(vuelo)) {
            this.vuelosLlegada.add(vuelo);
        }
    }

    @Override
    public String toString() {
        return String.format("""
            Aeropuerto [
                ID: %d
                Ciudad: %s
            ]
            """, idAeropuerto, ciudad);
    }

    // getters y setters
    public int getId() { return idAeropuerto; }
    public void setId(int id) { this.idAeropuerto = id; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) {
        if (ciudad!=null && !ciudad.isBlank() && !ciudad.isEmpty()) this.ciudad = ciudad;
        else throw new IllegalArgumentException("La ciudad no es valida");
    }

    public Set<Vuelo> getVuelosSalida() { return vuelosSalida; }
    public void setVuelosSalida(Set<Vuelo> vuelos) { this.vuelosSalida = vuelos; }

    public Set<Vuelo> getVuelosLlegada() { return vuelosLlegada; }
    public void setVuelosLlegada(Set<Vuelo> vuelos) { this.vuelosLlegada = vuelos; }
}
package com.iticbcn.aitormontejo.model;

import java.util.Set;
import java.util.regex.Pattern;

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
@Table(name="Piloto")
public class Piloto {
    // atributos
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idPiloto;

    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false, unique = true)
    private String pasaporte;
    
    @Column(nullable = false, unique = true)
    private String telefono;

    @OneToMany(mappedBy="piloto", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    private Set<Vuelo> vuelos;

    // constructor
    public Piloto() {}

    public Piloto(int id, String nombre, String pasaporte, String telefono) {
        setId(id);
        setNombre(nombre);
        setPasaporte(pasaporte);
        setTelefono(telefono);
    }

    public void addVuelo(Vuelo vuelo) {
        if (!this.vuelos.contains(vuelo)) {
            this.vuelos.add(vuelo);
        }
    }

    @Override
    public String toString() {
        return String.format("""
            Piloto [
                ID: %d
                Nombre: %s
                Pasaporte: %s
                Telefono: %s
            ]
            """, idPiloto, nombre, pasaporte, telefono);
    }

    // getters y setters
    public int getId() { return idPiloto; }
    public void setId(int id) { this.idPiloto = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre!=null && !nombre.isBlank() && !nombre.isEmpty()) this.nombre = nombre;
        else throw new IllegalArgumentException("¡El nombre que has pasado no es válido!");
    }

    public String getPasaporte() { return pasaporte; }
    public void setPasaporte(String pasaporte) {
        Pattern pattern = Pattern.compile("^[A-Z][0-9]{7}[A-Z]$");
        if(pattern.matcher(pasaporte).matches()) this.pasaporte = pasaporte;
        else throw new IllegalArgumentException("¡El pasaporte que has pasado no es válido!");
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) {
        Pattern pattern = Pattern.compile("^[6789]\\d{8}$");
        if(pattern.matcher(telefono).matches()) this.telefono = telefono;
        else throw new IllegalArgumentException("¡El telefono que has pasado no es válido!");
    }

    public Set<Vuelo> getVuelos() { return vuelos; }
    public void setVuelos(Set<Vuelo> vuelos) { this.vuelos = vuelos; }
}
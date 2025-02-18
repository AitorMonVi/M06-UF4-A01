package com.iticbcn.aitormontejo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;

@Entity
@Table(name="Vuelo")
public class Vuelo {
    // atributos
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idVuelo;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="idOrigen", foreignKey = @ForeignKey(name="FK_ORIGEN"), nullable = false)
    private Aeropuerto origen;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="idDestino", foreignKey = @ForeignKey(name="FK_DESTINO"), nullable = false)
    private Aeropuerto destino;
    
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="idAvion", foreignKey = @ForeignKey(name="FK_ID_AVION"), nullable = false)
    private Avion avion;
    
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="idPiloto", foreignKey = @ForeignKey(name="FK_ID_PILOTO"), nullable = false)
    private Piloto piloto;

    // constructores
    public Vuelo() {}

    public Vuelo(int id, int id_origen, int id_destino, int id_avion, int id_piloto) {
        setId(id);
        setOrigen(origen);
        setDestino(destino);
        setAvion(avion);
        setPiloto(piloto);
    }

    @Override
    public String toString() {
        return String.format("""
                Vuelo [
                    ID: %d
                    Origen: %d
                    Destino: %d
                    Avion: %d
                    Piloto: %d
                ]
                """, idVuelo, origen.getId(), destino.getId(), avion.getIdAvion(), piloto.getId());
    }

    // getters y setters
    public int getId() { return idVuelo; }
    public void setId(int id) { this.idVuelo = id; }

    public Aeropuerto getOrigen() { return origen; }
    public boolean setOrigen(Aeropuerto origen) { 
        if (origen!=null) {
            this.origen = origen; 
            return true;
        } else return false;
    }

    public Aeropuerto getDestino() { return destino; }
    public boolean setDestino(Aeropuerto destino) { 
        if (destino!=null) {
            this.destino = destino; 
            return true;
        } else return false;
    }

    public Avion getAvion() { return avion; }
    public boolean setAvion(Avion avion) { 
        if (avion!=null) {
            this.avion = avion;
            return true;
        } else return false;
    }

    public Piloto getPiloto() { return piloto; }
    public boolean setPiloto(Piloto piloto) { 
        if (piloto!=null) {
            this.piloto = piloto;
            return true;
        } else return false;
    }

    @PreRemove
    private void preRemove() {
        if (origen!=null) {
            origen.getVuelosSalida().remove(this);
            this.origen = null;
        }

        if (destino!=null) {
            destino.getVuelosLlegada().remove(this);
            this.destino = null;
        }

        if (avion!=null) {
            avion.getVuelos().remove(this);
            this.avion = null;
        }

        if (piloto!=null) {
            piloto.getVuelos().remove(this);
            this.piloto = null;
        }

    }
}
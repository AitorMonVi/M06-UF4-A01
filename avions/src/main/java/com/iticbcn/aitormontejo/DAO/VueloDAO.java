package com.iticbcn.aitormontejo.DAO;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import com.iticbcn.aitormontejo.Entrada;
import com.iticbcn.aitormontejo.Main;
import com.iticbcn.aitormontejo.model.Aeropuerto;
import com.iticbcn.aitormontejo.model.Avion;
import com.iticbcn.aitormontejo.model.Piloto;
import com.iticbcn.aitormontejo.model.Vuelo;

public class VueloDAO extends GenDAOImpl<Vuelo> {

    private AeropuertoDAO aeropuertoDAO;
    private AvionDAO avionDAO;
    private PilotoDAO pilotoDAO;

    public VueloDAO(SessionFactory factory) {
        super(factory, Vuelo.class);

        this.aeropuertoDAO = new AeropuertoDAO(factory);
        this.avionDAO = new AvionDAO(factory);
        this.pilotoDAO = new PilotoDAO(factory);
    }

    public void execute(String option) {
        switch (option) {
            case "crear" :      {
                Vuelo vuelo = save();
                if (vuelo!=null) System.out.println(vuelo);
                break;
            }
            case "mostrar" :    {
                Set<Vuelo> vuelos = find();
                if (vuelos!=null && !vuelos.isEmpty()) {
                    System.out.println("¡Vuelo/s encontrado!");
                    for (Vuelo vuelo : vuelos) {
                        System.out.println(vuelo);
                    }
                }
                break;
            }
            case "modificar" :  { 
                Vuelo vuelo = update();
                if (vuelo!=null) System.out.println(vuelo);
                break;
            }
            case "borrar" :     { delete(); break; }

            default : { System.out.println("¡Ha habido un error!"); }
        }
    }

    public Vuelo save() {
        Vuelo vuelo = new Vuelo();

        try {
            System.out.println("Rellena la siguiente información para poder crear el vuelo");
            System.out.println("¿Cual es el id del aeropuerto de origen?");
            Aeropuerto origen = aeropuertoDAO.findByID(false);

            if (origen!=null) vuelo.setOrigen(origen);
            else return null;

            System.out.println("¿Cual es el id del aeropuerto de destino?");
            Aeropuerto destino = aeropuertoDAO.findByID(false);

            if (destino!=null) vuelo.setDestino(destino);
            else return null;

            System.out.println("¿Cual es el id del avion que realizara el vuelo?");
            Avion avion = avionDAO.findByID(false);

            if (avion!=null) vuelo.setAvion(avion);
            else return null;
            
            System.out.println("¿Cual es el id del piloto del vuelo?");
            Piloto piloto = pilotoDAO.findByID(false);

            if (piloto!=null) vuelo.setPiloto(piloto);
            else return null;

            super.save(vuelo);

            System.out.println("¡Vuelo creado con éxito!");
            return vuelo;

        } catch (ConstraintViolationException e) {
            e.printStackTrace();
        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Set<Vuelo> find() {
        System.out.println("""
            Específica método de búsqueda:
                1 - Búsqueda por ID
                2 - Búsqueda por origen
                3 - Búsqueda por destino
                4 - Búsqueda por avion
                5 - Búsqueda por piloto
                6 - Muestra todos los vuelos
        """);

        boolean valid = false;
        while(!valid) {
            String option = Main.getOption();
            switch(option) {
                case "1" : {
                    Vuelo vuelo = findByID();
                    if (vuelo!=null) System.out.println(vuelo);
                    return null;
                }
                case "2" : { return findByOrigen(); }
                case "3" : { return findByDestino(); }
                case "4" : { return findByAvion(); }
                case "5" : { return findByPiloto(); }
                case "6" : { return new HashSet<>(super.getAll()); }

                default : { System.out.println("Opción no válida!"); }
            }
        }

        return null;
    }

    public Vuelo findByID() {
        try {
            System.out.println("¿Cual es el id del vuelo que quieres mostrar?");
            int id = askId();

            Vuelo vuelo = super.get(id);

            if (vuelo!=null) return vuelo;
            else System.out.println("No existe ningun vuelo con ese ID");

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public Set<Vuelo> findByOrigen() {
        System.out.println("¿Cual es el id del aeropuerto de origen?");
        Aeropuerto origen = aeropuertoDAO.findByID(false);

        return origen.getVuelosSalida();
    }

    public Set<Vuelo> findByDestino() {
        System.out.println("¿Cual es el id del aeropuerto de destino?");
        Aeropuerto destino = aeropuertoDAO.findByID(false);

        return destino.getVuelosLlegada();
    }

    public Set<Vuelo> findByAvion() {
        System.out.println("¿Cual es el id del avion que realizara el vuelo?");
        Avion avion = avionDAO.findByID(false);

        return avion.getVuelos();
    }

    public Set<Vuelo> findByPiloto() {
        System.out.println("¿Cual es el id del piloto del vuelo?");
        Piloto piloto = pilotoDAO.findByID(false);

        return piloto.getVuelos();
    }

    public Vuelo update() {
        try {
            System.out.println("Rellena la siguiente información para poder modificar el vuelo");
            System.out.println("¿Cual es el id del vuelo que quieres modificar?");
            int id = askId();
            
            Vuelo vuelo = super.get(id);
            
            if (vuelo!=null) {
                System.out.println("Datos actuales - " + vuelo);

                System.out.println("¿Cual es el id del nuevo aeropuerto de origen?");
                Aeropuerto origen = aeropuertoDAO.findByID(false);

                if (origen!=null) vuelo.setOrigen(origen);
                else return null;

                System.out.println("¿Cual es el id del nuevo aeropuerto de destino?");
                Aeropuerto destino = aeropuertoDAO.findByID(false);

                if (destino!=null) vuelo.setDestino(destino);
                else return null;

                System.out.println("¿Cual es el id del nuevo avion que realizara el vuelo?");
                Avion avion = avionDAO.findByID(false);

                if (avion!=null) vuelo.setAvion(avion);
                else return null;
                
                System.out.println("¿Cual es el id del nuevo piloto del vuelo?");
                Piloto piloto = pilotoDAO.findByID(false);

                if (piloto!=null) vuelo.setPiloto(piloto);
                else return null;
    
                super.update(vuelo);
    
                System.out.println("Vuelo modificado con éxito!");

                return vuelo;

            } else System.out.println("No existe ningun vuelo con ese ID");

        } catch (ConstraintViolationException e) {
            e.printStackTrace();
        } catch (HibernateException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Vuelo delete() {
        try {
            System.out.println("Rellena la siguiente información para poder eliminar el vuelo");
            System.out.println("¿Cual es el id del vuelo que quieres eliminar?");
            int id = askId();
            
            Vuelo vuelo = super.get(id);
            
            if (vuelo!=null) {
                
                super.delete(vuelo);
                
                System.out.println("¡Vuelo eliminado con éxito!");
                return vuelo;

            } else System.out.println("No existe ningun vuelo con ese ID");

        } catch (ConstraintViolationException e) {
            e.printStackTrace();
        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int askId() {
        boolean valid = false;
        int id = -1;
        while (!valid) {
            try {
                id = Integer.parseInt(Entrada.readLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("¡El ID debe ser un valor numerico!");
            }
        }
        return id;
    }
}

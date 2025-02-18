package com.iticbcn.aitormontejo.DAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.iticbcn.aitormontejo.Entrada;
import com.iticbcn.aitormontejo.Main;
import com.iticbcn.aitormontejo.model.Aeropuerto;
import com.iticbcn.aitormontejo.model.Avion;
import com.iticbcn.aitormontejo.model.Piloto;
import com.iticbcn.aitormontejo.model.Vuelo;

public class VueloDAO {
    
    private SessionFactory factory;

    private AeropuertoDAO aeropuertoDAO;
    private AvionDAO avionDAO;
    private PilotoDAO pilotoDAO;

    public VueloDAO(SessionFactory factory) {
        this.factory  = factory;

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
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            System.out.println("Rellena la siguiente información para poder crear el vuelo");
            System.out.println("¿Cual es el id del aeropuerto de origen?");
            Aeropuerto origen = aeropuertoDAO.findByID(false);

            if (origen!=null) vuelo.setOrigen(session.merge(origen));
            else return null;

            System.out.println("¿Cual es el id del aeropuerto de destino?");
            Aeropuerto destino = aeropuertoDAO.findByID(false);

            if (destino!=null) vuelo.setDestino(session.merge(destino));
            else return null;

            System.out.println("¿Cual es el id del avion que realizara el vuelo?");
            Avion avion = avionDAO.findByID(false);

            if (avion!=null) vuelo.setAvion(session.merge(avion));
            else return null;
            
            System.out.println("¿Cual es el id del piloto del vuelo?");
            Piloto piloto = pilotoDAO.findByID(false);

            if (piloto!=null) vuelo.setPiloto(session.merge(piloto));            
            else return null;

            session.persist(vuelo);

            session.getTransaction().commit();

            System.out.println("¡Vuelo creado con éxito!");
            return vuelo;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
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
                case "6" : { return findAll(); }

                default : { System.out.println("Opción no válida!"); }
            }
        }

        return null;
    }

    public Vuelo findByID() {
        Session session = factory.openSession();

        try {
            System.out.println("¿Cual es el id del vuelo que quieres mostrar?");
            int id = askId();

            Vuelo vuelo = session.get(Vuelo.class, id);

            if (vuelo!=null) return vuelo;
            else System.out.println("No existe ningun vuelo con ese ID");

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
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

    public Set<Vuelo> findAll() {
        List<Vuelo> lista;
        Set<Vuelo> vuelos = null;
        Session session = factory.openSession();

        try {
            
            lista = session.createQuery("FROM Vuelo", Vuelo.class).list();

            vuelos = new HashSet<>(lista);

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return vuelos;
    }

    public Vuelo update() {
        Session session = factory.openSession();

        try {
            System.out.println("Rellena la siguiente información para poder modificar el vuelo");
            System.out.println("¿Cual es el id del vuelo que quieres modificar?");
            int id = askId();
            
            Vuelo vuelo = session.get(Vuelo.class, id);
            
            if (vuelo!=null) {
                session.beginTransaction();

                System.out.println("Datos actuales - " + vuelo);

                System.out.println("¿Cual es el id del nuevo aeropuerto de origen?");
                Aeropuerto origen = aeropuertoDAO.findByID(false);

                if (origen!=null) {
                    session.merge(origen);
                    vuelo.setOrigen(origen);
                }
                else return null;

                System.out.println("¿Cual es el id del nuevo aeropuerto de destino?");
                Aeropuerto destino = aeropuertoDAO.findByID(false);

                if (destino!=null) {
                    session.merge(destino);
                    vuelo.setDestino(destino);
                }
                else return null;

                System.out.println("¿Cual es el id del nuevo avion que realizara el vuelo?");
                Avion avion = avionDAO.findByID(false);

                if (avion!=null) {
                    session.merge(avion);
                    vuelo.setAvion(avion);
                }
                else return null;
                
                System.out.println("¿Cual es el id del nuevo piloto del vuelo?");
                Piloto piloto = pilotoDAO.findByID(false);

                if (piloto!=null) {
                    session.merge(piloto);   
                    vuelo.setPiloto(piloto);
                }
                else return null;
    
                session.merge(vuelo);
                session.getTransaction().commit();
    
                System.out.println("Vuelo modificado con éxito!");

                return vuelo;

            } else System.out.println("No existe ningun vuelo con ese ID");

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    public Vuelo delete() {
        Session session = factory.openSession();

        try {
            System.out.println("Rellena la siguiente información para poder eliminar el vuelo");
            System.out.println("¿Cual es el id del vuelo que quieres eliminar?");
            int id = askId();
            
            Vuelo vuelo = session.get(Vuelo.class, id);
            
            if (vuelo!=null) {
                session.beginTransaction();
    
                session.remove(vuelo);

                session.getTransaction().commit();
                
                System.out.println("¡Vuelo eliminado con éxito!");

                return vuelo;

            } else System.out.println("No existe ningun vuelo con ese ID");

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
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

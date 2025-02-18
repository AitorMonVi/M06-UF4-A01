package com.iticbcn.aitormontejo.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import com.iticbcn.aitormontejo.Entrada;
import com.iticbcn.aitormontejo.Main;
import com.iticbcn.aitormontejo.model.Aeropuerto;

public class AeropuertoDAO {

    private SessionFactory factory;

    public AeropuertoDAO(SessionFactory factory) {
        this.factory  = factory;
    }

    public void execute(String option) {
        switch (option) {
            case "crear" :      {
                Aeropuerto aeropuerto = save();
                if (aeropuerto!=null) System.out.println(aeropuerto);
                break;
            }
            case "mostrar" :    {
                List<Aeropuerto> aeropuertos = find();
                if (aeropuertos!=null && !aeropuertos.isEmpty()) {
                    System.out.println("Aeropuerto/s encontrado!");
                    for (Aeropuerto aeropuerto : aeropuertos) {
                        System.out.println(aeropuerto);
                    }
                }
                break;
            }
            case "modificar" :  {
                Aeropuerto aeropuerto = update();
                if (aeropuerto!=null) System.out.println(aeropuerto);
                break;
            }
            case "borrar" :     {
                Aeropuerto aeropuerto = delete();
                if (aeropuerto!=null) System.out.println(aeropuerto);
                break;
            }

            default : { System.out.println("¡Ha habido un error!"); }
        }
    }

    public Aeropuerto save() {
        Aeropuerto aeropuerto = new Aeropuerto();
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            System.out.println("Rellena la siguiente información para poder crear el aeropuerto");
            System.out.println("¿En que ciudad se encuentra el aeropuerto?");
            
            aeropuerto.setCiudad(Entrada.readLine());

            session.persist(aeropuerto);

            session.getTransaction().commit();

            System.out.println("¡Aeropuerto creado con éxito!");
            return aeropuerto;

        } catch (ConstraintViolationException e) {
            session.getTransaction().rollback();
            System.out.println("¡Ya existe un aeropuerto en esta ciudad!");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            session.getTransaction().rollback();
            System.out.println("¡La ciudad que has pasado no es válida!");
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    public List<Aeropuerto> find() {
        System.out.println("""
            Específica método de búsqueda:
                1 - Búsqueda por ID
                2 - Búsqueda por ciudad
                3 - Muestra todos los aeropuertos
        """);

        boolean valid = false;
        while(!valid) {
            String option = Main.getOption();
            switch(option) {
                case "1" : { 
                    Aeropuerto aeropuerto = findByID(true);
                    if (aeropuerto!=null) System.out.println(aeropuerto);
                    return null;
                }
                case "2" : { return findByCiudad(); }
                case "3" : { return findAll(); }

                default : { System.out.println("Opción no válida!"); }
            }
        }

        return null;
    }

    public Aeropuerto findByID(boolean show) {
        Session session = factory.openSession();

        try {
            if (show) System.out.println("¿Cual es el id del aeropuerto que quieres mostrar?");
            int id = askId();

            Aeropuerto aeropuerto = session.get(Aeropuerto.class, id);

            if (aeropuerto!=null) return aeropuerto;
            else System.out.println("No existe ningun aeropuerto con ese ID");

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return null;
    }

    public List<Aeropuerto> findByCiudad() {
        List<Aeropuerto> aeropuertos = null;
        Session session = factory.openSession();

        try {
            System.out.println("¿Cual es la ciudad del aeropuerto que quieres mostrar?");
            String ciudad = Entrada.readLine();

            aeropuertos = session.createQuery("FROM Aeropuerto WHERE ciudad LIKE :ciudad", Aeropuerto.class)
                                 .setParameter("ciudad", "%" + ciudad + "%")
                                 .list();

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return aeropuertos;
    }

    public List<Aeropuerto> findAll() {
        List<Aeropuerto> aeropuertos = null;
        Session session = factory.openSession();

        try {
            
            aeropuertos = session.createQuery("FROM Aeropuerto", Aeropuerto.class).list();

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return aeropuertos;
    }

    public Aeropuerto update() {
        Session session = factory.openSession();

        try {
            System.out.println("Rellena la siguiente información para poder modificar el aeropuerto");
            System.out.println("¿Cual es el id del aeropuerto que quieres modificar?");
            
            int id = askId();
            
            Aeropuerto aeropuerto = session.get(Aeropuerto.class, id);
            
            if (aeropuerto!=null) {
                session.beginTransaction();

                System.out.println("Datos actuales - " + aeropuerto);

                System.out.println("¿Cual es la nueva ciudad del aeropuerto?");
                aeropuerto.setCiudad(Entrada.readLine());
    
                session.merge(aeropuerto);
                session.getTransaction().commit();
    
                System.out.println("¡Aeropuerto modificado con éxito!");

                return aeropuerto;

            } else System.out.println("No existe ningun aeropuerto con ese ID");

        } catch (ConstraintViolationException e) {
            session.getTransaction().rollback();
            System.out.println("¡Ya existe un aeropuerto en esta ciudad!");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            session.getTransaction().rollback();
            System.out.println("¡La ciudad que has pasado no es válida!");
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    public Aeropuerto delete() {
        Session session = factory.openSession();

        try {
            System.out.println("Rellena la siguiente información para poder eliminar el aeropuerto");
            System.out.println("¿Cual es el id del aeropuerto que quieres eliminar?");
            
            int id = askId();
            
            Aeropuerto aeropuerto = session.get(Aeropuerto.class, id);
            
            if (aeropuerto!=null) {
                session.beginTransaction();
    
                session.remove(aeropuerto);

                session.getTransaction().commit();
    
                System.out.println("¡Aeropuerto eliminado con éxito!");

                return aeropuerto;

            } else System.out.println("No existe ningun aeropuerto con ese ID");

        } catch (ConstraintViolationException e) {
            session.getTransaction().rollback();
            System.out.println("No puedes eliminar este aeropuerto porque está asociado a vuelos.");
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

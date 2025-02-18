package com.iticbcn.aitormontejo.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import com.iticbcn.aitormontejo.Entrada;
import com.iticbcn.aitormontejo.Main;
import com.iticbcn.aitormontejo.model.Avion;

public class AvionDAO {
    
    private SessionFactory factory;

    public AvionDAO(SessionFactory factory) {
        this.factory  = factory;
    }

    public void execute(String option) {
        switch (option) {
            case "crear" :      {
                Avion avion = save();
                if (avion!=null) System.out.println(avion);
                break;
            }
            case "mostrar" :    {
                List<Avion> aviones = find();
                if (aviones!=null && !aviones.isEmpty()) {
                    System.out.println("Avion/es encontrado!");
                    for (Avion avion : aviones) {
                        System.out.println(avion);
                    }
                }
                break;
            }
            case "modificar" :  {
                Avion avion = update();
                if (avion!=null) System.out.println(avion);
                break;
            }
            case "borrar" :     {
                Avion avion = delete();
                if (avion!=null) System.out.println(avion);
                break;
            }

            default : { System.out.println("¡Ha habido un error!"); }
        }
    }

    public Avion save() {
        Avion avion = new Avion();
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            System.out.println("Rellena la siguiente información para poder crear el avion");
            System.out.println("¿Que modelo es el avion?");
            avion.setModelo(Entrada.readLine());

            System.out.println("¿Cual es la capacidad del avion?");
            avion.setCapacidad(askCapacidad());

            session.persist(avion);

            session.getTransaction().commit();

            System.out.println("Avion creado con éxito!");
            return avion;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    public List<Avion> find() {
        System.out.println("""
            Específica método de búsqueda:
                1 - Búsqueda por ID
                2 - Búsqueda por modelo
                3 - Búsqueda por capacidad
                4 - Muestra todos los aviones
                5 - Muestra la capacidad de todos los aviones juntos
        """);

        boolean valid = false;
        while(!valid) {
            String option = Main.getOption();
            switch(option) {
                case "1" : {
                    Avion avion = findByID(true);
                    if (avion!=null) System.out.println(avion);
                    return null;
                }
                case "2" : { return findByModelo(); }
                case "3" : { return findByCapacidad(); }
                case "4" : { return findAll(); }
                case "5" : {
                    System.out.println("La capacidad conjunta de todos los aviones es: " + showMaxCapacidad());
                    return null;
                }

                default : { System.out.println("Opción no válida!"); }
            }
        }

        return null;
    }

    public Avion findByID(boolean show) {
        Session session = factory.openSession();

        try {
            if (show) System.out.println("¿Cual es el id del avion que quieres mostrar?");
            int id = askId();
            
            Avion avion = session.get(Avion.class, id);
            
            if (avion!=null) return avion;
            else System.out.println("No existe ningun avion con ese ID");

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    public List<Avion> findByModelo() {
        List<Avion> avions = null;
        Session session = factory.openSession();

        try {
            System.out.println("¿Cual es el modelo del avion que quieres mostrar?");
            String modelo = Entrada.readLine();
            
            avions = session.createQuery("FROM Avion WHERE modelo LIKE :modelo", Avion.class)
                                 .setParameter("modelo", "%" + modelo + "%")
                                 .list();

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return avions;
    }

    public List<Avion> findByCapacidad() {
        List<Avion> avions = null;
        Session session = factory.openSession();

        try {
            System.out.println("¿Cual es la capacidad del avion que quieres mostrar?");
            String capacidad = Entrada.readLine();
            
            avions = session.createQuery("FROM Avion WHERE capacidad = :capacidad", Avion.class)
                                 .setParameter("capacidad", capacidad)
                                 .list();

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return avions;
    }

    public List<Avion> findAll() {
        List<Avion> avions = null;
        Session session = factory.openSession();

        try {
            
            avions = session.createQuery("FROM Avion", Avion.class).list();

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return avions;
    }

    public Long showMaxCapacidad() {
        Long suma = 0L;
        Session session = factory.openSession();

        try {

            suma = session.createQuery("SELECT SUM(a.capacidad) FROM Avion a", Long.class).uniqueResult();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return suma;
    }

    public Avion update() {
        Session session = factory.openSession();

        try {
            System.out.println("Rellena la siguiente información para poder modificar el avion");
            System.out.println("¿Cual es el id del avion que quieres modificar?");
            
            int id = askId();
            
            Avion avion = session.get(Avion.class, id);
            
            if (avion!=null) {
                session.beginTransaction();

                System.out.println("Datos actuales - " + avion);

                System.out.println("¿Cual es el nuevo modelo del avion?");
                avion.setModelo(Entrada.readLine());

                System.out.println("¿Cual es la nueva capacidad del avion?");
                avion.setCapacidad(askCapacidad());
    
                session.getTransaction().commit();
    
                System.out.println("¡Avion modificado con éxito!");
                return avion;

            } else System.out.println("No existe ningun avion con ese ID");

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    public Avion delete() {
        Session session = factory.openSession();

        try {
            System.out.println("Rellena la siguiente información para poder eliminar el avion");
            System.out.println("¿Cual es el id del avion que quieres eliminar?");
            
            int id = askId();
            
            Avion avion = session.get(Avion.class, id);
            
            if (avion!=null) {
                session.beginTransaction();
    
                session.remove(avion);

                session.getTransaction().commit();
    
                System.out.println("¡Avion eliminado con éxito!");
                return avion;

            } else System.out.println("No existe ningun avion con ese ID");

        } catch (ConstraintViolationException e) {
            session.getTransaction().rollback();
            System.out.println("No puedes eliminar este avión porque está asociado a vuelos.");
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

    public int askCapacidad() {
        boolean valid = false;
        int capacidad = -1;
        while (!valid) {
            try {
                capacidad = Integer.parseInt(Entrada.readLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("¡La capacidad debe ser un valor numerico!");
            }
        }
        return capacidad;
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

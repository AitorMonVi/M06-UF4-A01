package com.iticbcn.aitormontejo.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import com.iticbcn.aitormontejo.Entrada;
import com.iticbcn.aitormontejo.Main;
import com.iticbcn.aitormontejo.model.Piloto;

public class PilotoDAO extends GenDAOImpl<Piloto> {
    
    private SessionFactory factory;

    public PilotoDAO(SessionFactory factory) {
        super(factory, Piloto.class);
        this.factory = factory;
    }

    public void execute(String option) {
        switch (option) {
            case "crear" :      {
                Piloto piloto = save();
                if (piloto!=null) System.out.println(piloto);
                break;
            }
            case "mostrar" :    {
                List<Piloto> pilotos = find();
                if (pilotos!=null && !pilotos.isEmpty()) {
                    System.out.println("Avion/es encontrado!");
                    for (Piloto piloto : pilotos) {
                        System.out.println(piloto);
                    }
                }
                break;
            }
            case "modificar" :  {
                Piloto piloto = update();
                if (piloto!=null) System.out.println(piloto);
                break;
            }
            case "borrar" :     {
                Piloto piloto = delete();
                if (piloto!=null) System.out.println(piloto);
                break;
            }

            default : { System.out.println("¡Ha habido un error!"); }
        }
    }

    public Piloto save() {
        Piloto piloto = new Piloto();

        try {
            System.out.println("Rellena la siguiente información para poder crear el piloto");
            System.out.println("¿Cual es el nombre del piloto?");
            piloto.setNombre(Entrada.readLine());

            System.out.println("¿Cual es el pasaporte del piloto?");
            piloto.setPasaporte(Entrada.readLine());

            System.out.println("¿Cual es el telefono del piloto?");
            piloto.setTelefono(Entrada.readLine());

            super.save(piloto);

            System.out.println("Piloto creado con éxito!");
            return piloto;

        } catch (ConstraintViolationException e) {
            e.printStackTrace();
        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Piloto> find() {
        System.out.println("""
            Específica método de búsqueda:
                1 - Búsqueda por ID
                2 - Búsqueda por nombre
                3 - Búsqueda por pasaporte
                4 - Búsqueda por telefono
                5 - Muestra todos los pilotos
        """);

        boolean valid = false;
        while(!valid) {
            String option = Main.getOption();
            switch(option) {
                case "1" : {
                    Piloto piloto = findByID(true);
                    if (piloto!=null) System.out.println(piloto);
                    return null;
                }
                case "2" : { return findByNombre(); }
                case "3" : { return findByPasaporte(); }
                case "4" : { return findByTelefono(); }
                case "5" : { return super.getAll(); }

                default : { System.out.println("Opción no válida!"); }
            }
        }

        return null;
    }

    public Piloto findByID(boolean show) {
        try {
            if (show) System.out.println("¿Cual es el id del piloto que quieres mostrar?");
            int id = askId();
            
            Piloto piloto = super.get(id);
            
            if (piloto!=null) return piloto;
            else System.out.println("No existe ningun piloto con ese ID");

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Piloto> findByNombre() {
        List<Piloto> pilotos = null;
        Session session = factory.openSession();

        try {
            System.out.println("¿Cual es el nombre del piloto que quieres mostrar?");
            String nombre = Entrada.readLine();

            pilotos = session.createQuery("FROM Piloto WHERE nombre LIKE :nombre", Piloto.class)
                                 .setParameter("nombre", "%" + nombre + "%")
                                 .list();

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return pilotos;
    }

    public List<Piloto> findByPasaporte() {
        List<Piloto> pilotos = null;
        Session session = factory.openSession();

        try {
            System.out.println("¿Cual es el pasaporte del piloto que quieres mostrar?");
            String pasaporte = Entrada.readLine();

            pilotos = session.createQuery("FROM Piloto WHERE pasaporte = :pasaporte", Piloto.class)
                                 .setParameter("pasaporte", pasaporte)
                                 .list();

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return pilotos;
    }

    public List<Piloto> findByTelefono() {
        List<Piloto> pilotos = null;
        Session session = factory.openSession();

        try {
            System.out.println("¿Cual es el telefono del piloto que quieres mostrar?");
            String telefono = Entrada.readLine();

            pilotos = session.createQuery("FROM Piloto WHERE telefono = :telefono", Piloto.class)
                                 .setParameter("telefono", telefono)
                                 .list();

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return pilotos;
    }

    public Piloto update() {
        try {
            System.out.println("Rellena la siguiente información para poder modificar el piloto");
            System.out.println("¿Cual es el id del piloto que quieres modificar?");
            
            int id = askId();
            
            Piloto piloto = super.get(id);
            
            if (piloto!=null) {
                System.out.println("Datos actuales - " + piloto);

                System.out.println("¿Cual es el nuevo nombre del piloto?");
                piloto.setNombre(Entrada.readLine());

                System.out.println("¿Cual es el nuevo pasaporte del piloto?");
                piloto.setPasaporte(Entrada.readLine());

                System.out.println("¿Cual es el nuevo telefono del piloto?");
                piloto.setTelefono(Entrada.readLine());
    
                super.update(piloto);
    
                System.out.println("¡Piloto modificado con éxito!");
                return piloto;

            } else System.out.println("No existe ningun piloto con ese ID");

        } catch (ConstraintViolationException e) {
            e.printStackTrace();
        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Piloto delete() {
        try {
            System.out.println("Rellena la siguiente información para poder eliminar el piloto");
            System.out.println("¿Cual es el id del piloto que quieres eliminar?");
            
            int id = askId();
            
            Piloto piloto = super.get(id);
            
            if (piloto!=null) {

                super.delete(piloto);
    
                System.out.println("¡Piloto eliminado con éxito!");
                return piloto;

            } else System.out.println("No existe ningun piloto con ese ID");

        } catch (ConstraintViolationException e) {
            System.out.println("No puedes eliminar este piloto porque está asociado a vuelos.");
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

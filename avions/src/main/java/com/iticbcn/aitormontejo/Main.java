package com.iticbcn.aitormontejo;

import org.hibernate.SessionFactory;

import com.iticbcn.aitormontejo.DAO.AeropuertoDAO;
import com.iticbcn.aitormontejo.DAO.AvionDAO;
import com.iticbcn.aitormontejo.DAO.PilotoDAO;
import com.iticbcn.aitormontejo.DAO.VueloDAO;

public class Main {
    // variables globales
    private static SessionFactory factory = HibernateUtil.getSessionFactory();

    private static AeropuertoDAO aeropuertoDAO = new AeropuertoDAO(factory);
    private static AvionDAO avionDAO = new AvionDAO(factory);
    private static PilotoDAO pilotoDAO = new PilotoDAO(factory);
    private static VueloDAO vueloDAO = new VueloDAO(factory);

    // metodo main
    public static void main(String[] args) {
        help();

        boolean cont = true;
        while (cont) {
            String option = getOption();

            switch (option) {
                case "1", "ayuda" :     { help(); break; }
                case "2", "crear" :     {
                    ask("crear"); 
                    help();
                    break; 
                }
                case "3", "mostrar" :   {
                    ask("mostrar"); 
                    help();
                    break; 
                }
                case "4", "modificar" : {
                    ask("modificar");
                    help();
                    break; 
                }
                case "5", "borrar" :    {
                    ask("borrar");
                    help();
                    break;
                }
                case "6", "salir" :     { cont = false; break; }

                default : { System.out.println("Opción no válida!"); help(); }
            }
        }
    }

    // metodos
    public static void ask(String opcion) {
        boolean valid = false;

        System.out.printf("¿Qué elemento quieres %s? (Aeropuerto, Avion, Piloto, Vuelo)%n", opcion);
        System.out.printf("Si quieres terminar de %s escribe 'salir'%n", opcion);

        while (!valid) {
            String option = getOption();
            switch (option) {
                case "Aeropuerto" : {
                    aeropuertoDAO.execute(opcion);
                    valid = true;
                    break;
                }
                case "Avion" :      {
                    avionDAO.execute(opcion);
                    valid = true;
                    break;
                }
                case "Piloto" :     {
                    pilotoDAO.execute(opcion);
                    valid = true;
                    break;
                }
                case "Vuelo" :      {
                    vueloDAO.execute(opcion);
                    valid = true;
                    break;
                }
                case "salir" :      {
                    System.out.printf("¡Has terminado de %s!%n", opcion);
                    valid = true;
                    break;
                }

                default : { System.out.println("¡Esta no es una opción válida!"); }
            }
        }
    }

    public static void help() {
        System.out.println("""
                Opciones disponibles:
                    1 - ayuda     -> muestra este mensaje de error
                    2 - crear     -> inserta un nuevo elemento en la bdd
                    3 - mostrar   -> muestra por pantalla elementos de la bdd
                    4 - modificar -> modifica un elemento de la bdd
                    5 - borrar    -> elimina un elemento de la bdd
                    6 - salir     -> termina la ejecución actual
                """);
    }

    public static String getOption() {
        System.out.print("> ");
        return Entrada.readLine();
    }
}
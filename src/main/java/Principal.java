import jakarta.persistence.*;

import java.util.*;
import java.sql.Date;

public class Principal {


    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicio();
    }

    public static void inicio(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            if(em.createQuery("SELECT j FROM Juego j", Juego.class).getResultList().size() > 0 || em.createQuery("SELECT d FROM Desarrollador d", Desarrollador.class).getResultList().size() > 0){
                System.out.println("Ya hay datos en la base de datos por lo que no se van a añadir los datos de prueba");

            }else {
                Juego juego1 = addJuegos("Super Mario Bros.", Date.valueOf("1985-09-13"), "NES");
                Juego juego2 = addJuegos("Diablo II", Date.valueOf("2000-06-29"), "PC");
                Juego juego3 = addJuegos("The Last of Us", Date.valueOf("2013-06-14"), "PS3");
                Juego juego4 = addJuegos("Uncharted 2: Among Thieves", Date.valueOf("2009-10-13"), "PS3");
                Juego juego5 = addJuegos("Resident Evil 4", Date.valueOf("2005-01-11"), "PS2");
                Juego juego6 = addJuegos("Age of Empires II: The Age of Kings", Date.valueOf("1999-09-30"), "PC");
                Juego juego7 = addJuegos("Resident Evil 2", Date.valueOf("1998-01-21"), "PS1");
                Juego juego8 = addJuegos("Uncharted 3: Drake's Deception", Date.valueOf("2011-11-01"), "PS3");
                Juego juego9 = addJuegos("Diablo III", Date.valueOf("2012-05-15"), "PC");
                Juego juego10 = addJuegos("The Legend of Zelda: Breath of the Wild", Date.valueOf("2017-03-03"), "Switch");


                Desarrollador desarrollador1 = addDesarrolladores("Nintendo", "Japón");
                Desarrollador desarrollador2 = addDesarrolladores("Blizzard Entertainment", "Estados Unidos");
                Desarrollador desarrollador3 = addDesarrolladores("Rockstar North", "Reino Unido");
                Desarrollador desarrollador4 = addDesarrolladores("EA Redwood Shores", "Estados Unidos");
                Desarrollador desarrollador5 = addDesarrolladores("Ensemble Studios", "Estados Unidos");

                juego1.getDesarrolladores().add(desarrollador1);
                juego2.getDesarrolladores().add(desarrollador2);
                juego3.getDesarrolladores().add(desarrollador3);
                juego4.getDesarrolladores().add(desarrollador3);
                juego5.getDesarrolladores().add(desarrollador4);
                juego6.getDesarrolladores().add(desarrollador5);
                juego7.getDesarrolladores().add(desarrollador4);
                juego8.getDesarrolladores().add(desarrollador3);
                juego9.getDesarrolladores().add(desarrollador2);
                juego10.getDesarrolladores().add(desarrollador1);


                em.persist(juego1);
                em.persist(juego2);
                em.persist(juego3);
                em.persist(juego4);
                em.persist(juego5);
                em.persist(juego6);
                em.persist(juego7);
                em.persist(juego8);
                em.persist(juego9);
                em.persist(juego10);

                em.persist(desarrollador1);
                em.persist(desarrollador2);
                em.persist(desarrollador3);
                em.persist(desarrollador4);
                em.persist(desarrollador5);
            }




            em.getTransaction().commit();

            menu();

        } catch (Exception e) {
            if(em.getTransaction() != null && em.getTransaction().isActive())
                em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void menu(){
        int opcion;

        do {

            System.out.println(mostrarMenu());

            opcion = Integer.parseInt(scanner.nextLine());



            switch (opcion) {
                case 1:
                    verJuegos();
                    break;
                case 2:
                    verDesarrolladores();
                    break;
                case 3:
                    mostrarJuegosDessarrollador();
                    break;
                case 4:
                    mostrarDesarrolladoresPorJuego();
                    break;
                case 5:
                    actualizarTitulo();
                    break;
                case 6:
                    removeJuego();
                    break;
                case 7:
                    mostrarJuegosAntesDeFecha();
                    break;
                case 8:
                    mostrarJuegosDespuesDeFecha();
                    break;
                case 9:
                    mostrarJuegosEntreDosFechas();
                    break;
                case 10:
                    mostrarJuegosPlataforma();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Error, opción no válida");
                    break;
            }
        } while (opcion != 0);
    }

    public static String mostrarMenu(){
        StringBuilder menu = new StringBuilder();
        menu.append("1. Ver juegos\n");
        menu.append("2. Ver desarrolladores\n");
        menu.append("3. Ver juegos por desarrollador\n");
        menu.append("4. Ver desarrolladores por juego\n");
        menu.append("5. Actualizar título de juego\n");
        menu.append("6. Eliminar juego\n");
        menu.append("7. Mostrar juegos antes de una fecha\n");
        menu.append("8. Mostrar juegos después de una fecha\n");
        menu.append("9. Mostrar juegos entre dos fechas\n");
        menu.append("10. Mostrar juegos por plataforma\n");
        menu.append("0. Salir\n");
        return menu.toString();
    }



    public static Desarrollador addDesarrolladores(String nombre, String pais) {
        Desarrollador desarrollador = new Desarrollador();
        desarrollador.setNombre(nombre);
        desarrollador.setPais(pais);
        return desarrollador;
    }

    public static Juego addJuegos(String titulo, Date fechaLanzamiento, String plataforma) {
        Juego juego = new Juego();
        juego.setTitulo(titulo);
        juego.setFechaLanzamiento(fechaLanzamiento);
        juego.setPlataforma(plataforma);
        return juego;
    }

    public static void verJuegos(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<Juego> juegos = em.createQuery("SELECT j FROM Juego j", Juego.class).getResultList();
        for (Juego juego : juegos) {
            System.out.println(juego.getTitulo());
        }
    }

    public static void mostrarJuegosDessarrollador(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<Juego> juegos = em.createQuery("SELECT j FROM Juego j", Juego.class).getResultList();
        for (Juego juego : juegos) {
            System.out.println(juego.getTitulo());
            for (Desarrollador desarrollador : juego.getDesarrolladores()) {
                System.out.println(desarrollador.getNombre());
            }
        }
    }

    public static void mostrarDesarrolladoresPorJuego(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<Desarrollador> desarrolladores = em.createQuery("SELECT d FROM Desarrollador d", Desarrollador.class).getResultList();
        for (Desarrollador desarrollador : desarrolladores) {
            System.out.println(desarrollador.getNombre());
            for (Juego juego : desarrollador.getJuegos()) {
                System.out.println(juego.getTitulo());
            }
        }
    }

    public static void verDesarrolladores(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<Desarrollador> desarrolladores = em.createQuery("SELECT d FROM Desarrollador d", Desarrollador.class).getResultList();
        for (Desarrollador desarrollador : desarrolladores) {
            System.out.println(desarrollador.getNombre());
        }
    }

    public static void actualizarTitulo() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        System.out.println("Introduce el título del juego que quieres actualizar:");
        String titulo = scanner.nextLine();
        System.out.println("Introduce el nuevo título del juego:");
        String nuevoTitulo = scanner.nextLine();
        Juego juego = em.createQuery("SELECT j FROM Juego j WHERE j.titulo = :titulo", Juego.class).setParameter("titulo", titulo).getSingleResult();
        em.getTransaction().begin();
        juego.setTitulo(nuevoTitulo);
        em.getTransaction().commit();
    }

    public static void removeJuego() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        System.out.println("Introduce el título del juego que quieres eliminar:");
        String titulo = scanner.nextLine();
        Juego juego = em.createQuery("SELECT j FROM Juego j WHERE j.titulo = :titulo", Juego.class).setParameter("titulo", titulo).getSingleResult();
        em.getTransaction().begin();
        em.remove(juego);
        em.getTransaction().commit();
    }

    public static void mostrarJuegosAntesDeFecha() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        System.out.println("Introduce la fecha:");
        String fecha = scanner.nextLine();
        List<Juego> juegos = em.createQuery("SELECT j FROM Juego j WHERE j.fechaLanzamiento < :fecha", Juego.class).setParameter("fecha", Date.valueOf(fecha)).getResultList();
        for (Juego juego : juegos) {
            System.out.println(juego.getTitulo());
        }
    }

    public static void mostrarJuegosDespuesDeFecha() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        System.out.println("Introduce la fecha:");
        String fecha = scanner.nextLine();
        List<Juego> juegos = em.createQuery("SELECT j FROM Juego j WHERE j.fechaLanzamiento > :fecha", Juego.class).setParameter("fecha", Date.valueOf(fecha)).getResultList();
        for (Juego juego : juegos) {
            System.out.println(juego.getTitulo());
        }
    }

    public static void mostrarJuegosEntreDosFechas() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        System.out.println("Introduce la fecha inicial:");
        String fechaInicial = scanner.nextLine();
        System.out.println("Introduce la fecha final:");
        String fechaFinal = scanner.nextLine();
        List<Juego> juegos = em.createQuery("SELECT j FROM Juego j WHERE j.fechaLanzamiento BETWEEN :fechaInicial AND :fechaFinal", Juego.class).setParameter("fechaInicial", Date.valueOf(fechaInicial)).setParameter("fechaFinal", Date.valueOf(fechaFinal)).getResultList();
        for (Juego juego : juegos) {
            System.out.println(juego.getTitulo());
        }
    }

    public static void mostrarJuegosPlataforma() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        System.out.println("Introduce la plataforma:");
        String plataforma = scanner.nextLine();
        List<Juego> juegos = em.createQuery("SELECT j FROM Juego j WHERE j.plataforma = :plataforma", Juego.class).setParameter("plataforma", plataforma).getResultList();
        for (Juego juego : juegos) {
            System.out.println(juego.getTitulo());
        }
    }

}

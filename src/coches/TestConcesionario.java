package coches;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TestConcesionario {
    static Scanner sc = new Scanner(System.in);  //este Scanner lo vamos a usar en muchos métodos, no solo en el main
    public static void main(String[] args) {
        //Nada mas que arrancar tiene que leer el fichero coches.csv
        //vamos a llenar con esos datos un Set<Coche>
        Set<Coche> coches = new HashSet<>(); //vacío, lo iremos llenando
        try (Scanner in = new Scanner(new File("ficheros/coches.csv"))) {  //try-catch según Java 1.7
            in.nextLine(); //eliminamos la primera línea, la cabecera del csv: matricula,modelo,año,fabricante
            while (in.hasNextLine()) {
                String[] tokens = in.nextLine().split(","); //separamos los campos delimitados por comas
                //7364duq,G-Class,2003,Mercedes-Benz  este es un ejemplo
                //tokens[0], tokens[1], tokens[2], tokens[3]
                if (! Auxiliar.chequearMatricula(tokens[0]))
                    continue; //matricula no válida, nos pasamos a leer la siguiente línea
                if (! Auxiliar.chequearAnnoFabricacion(Integer.parseInt(tokens[2])))
                    continue; //año de fabricación válido
                //si llegamos aquí es que los datos son correctos, creamos objeto Coche y lo añadimos a la lista
                coches.add(new Coche(tokens[0], tokens[1], Integer.parseInt(tokens[2]), tokens[3]));
             }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //creamos el objeto Concesionario, solicitamos el nombre del concesionario
        System.out.println("Introduce el nombre del concesionario");
        String nombreConcesionario = sc.nextLine(); //nextLine por si es un nombre compuesto, ejemplo Coches Francisco
        Concesionario concesionario  = new Concesionario(nombreConcesionario, coches);
        //mostramos el menú

        int opcion = 1;
        do {
            System.out.println("\n\n"); //para mejorar la visualización del menú
            System.out.println("1.- Mostrar todos los coches del concesionaro.");
            System.out.println("2.- Añadir un coche.");
            System.out.println("3-. Eliminar un coche.");
            System.out.println("4.- Listar coches por año de fabricación");
            System.out.println("5.- Listar coches por fabricante.");
            System.out.println("6.- Salir");
            System.out.print("\n Introduce opción: ");
            opcion = sc.nextInt();
            switch (opcion){
                case 1:
                    mostrarTodosLosCoches(concesionario);
                    break;
                case 2:
                    if (annadirCoche(concesionario))
                        System.out.println("Añadido coche correctamente");
                    else
                        System.out.println("No se puede añadir el coche");
                    break;
                case 3:
                    if (eliminarCoche(concesionario))
                        System.out.println("Elimnado coche del concesionario");
                    else
                        System.out.println("No se puede eliminar el coche");
                    break;
                case 4:
                    listarCochesPorAnno(concesionario);
                    break;
                case 5:
                    listarCochesPorFabricante(concesionario);
                    break;
                case 6:
                    System.out.println("FIN DE PROGRAMA");
                    sc.close();  //cerramos el Scanner
                    return; //salimos del main, por lo que el programa termina
                default:
                    System.out.println("\nOpción no válida, vuelve a teclear opción\n");
            }
        } while ( true );
    }

    private static void listarCochesPorFabricante(Concesionario concesionario) {
        System.out.println("Introduce fabricante a mostrar");
        String fabricante = sc.next();
        Set<Coche> cochesFabricantes = concesionario.obtenerCochesPorFabricante(fabricante);
        for (Coche coche: cochesFabricantes) {
            System.out.println(coche);
        }

    }


    private static void listarCochesPorAnno(Concesionario concesionario) {
        String sAnno = "";
        int iAnno = 0;
        do {
            System.out.println("Introduce año de búsqueda");
            sAnno = sc.next();
            //con la regex comprobamos que es un número, si lo es chequeamos el año de fabricación. Importante el orden, al reves puede saltar la excepcion con Integer.parseInt
            if (sAnno.matches("[0-9]{4}") && Auxiliar.chequearAnnoFabricacion(Integer.parseInt(sAnno))) {
                iAnno = Integer.parseInt(sAnno);
                break;
            }
        } while (true);
        Map<String, Coche> diccinarioCoches = concesionario.obtenerCocherFabricadoEnUnAnno(iAnno);
        for (String key : diccinarioCoches.keySet()) {
            System.out.printf("%S: %s%n", key, diccinarioCoches.get(key) );
        }
    }

    private static boolean eliminarCoche(Concesionario concesionario) {
        System.out.println("Introduce la matrícula");
        String matricula = "";
        do {
            matricula = sc.next();
            if (Auxiliar.chequearMatricula(matricula))
                break;
            System.out.println("Matrícula NO válida");
        } while (true);
        System.out.println("Matricula válida");
        return concesionario.quitarCoche(new Coche(matricula, null, 0, null));
        //como dos coches son iguales por matrícula, el resto de campos de creación de objeto coche los dejo con valor por defecto
    }

    private static boolean annadirCoche(Concesionario concesionario) {
        System.out.println("\nIntroduce datos sobre el coche");  // 7364duq,G-Class,2003,Mercedes-Benz  este es un ejemplo
        System.out.println("Introduce la matrícula");
        String matricula = "";
        do {
            matricula = sc.next();
            if (Auxiliar.chequearMatricula(matricula))
                break;
            System.out.println("Matrícula NO válida");
        } while (true);
        System.out.println("Matricula válida");
        System.out.println("Introduce modelo");
        String modelo = sc.next();
        String sAnno = ""; //vamos a chequear que introducimos un número de cuatro letras
        int iAnno = 0;
        do {
            System.out.println("Introduce año de fabricación:");
            sAnno = sc.next();
            //con la regex comprobamos que es un número, si lo es chequeamos el año de fabricación. Importante el orden, al reves puede saltar la excepcion con Integer.parseInt
            if (sAnno.matches("[0-9]{4}") && Auxiliar.chequearAnnoFabricacion(Integer.parseInt(sAnno))) {
                iAnno = Integer.parseInt(sAnno);
                break;
            }
            System.out.println("Año de fabricación NO válido");
        } while (true);
        System.out.println("Año de fabricación válido");
        System.out.println("Introduce fabricante");
        String fabricante = sc.next();
        return concesionario.addCoche(new Coche(matricula, modelo, iAnno, fabricante));

    }

    private static void mostrarTodosLosCoches(Concesionario concesionario) {
        //como hay que devolverlos ordenados por fecha, de mayor a menor.
        //hemos establecido ese orden con la interfaz Comparable e implementando el método toCompare en la clase Coche
        //nos encontramos que Set no tiene el método sort, no podemos hacer Collections.sort(coches), pues si lo podemos hacer con listas
        //podemos crear un List<Coche> usando el método addAll de List
        List<Coche> listaCoches = new ArrayList<>();
        listaCoches.addAll(concesionario.getStockCoches()); //el método addAll permite añadir objetos Collection
        //Collection es clase padre de Set y de List
        //la definición del método es: boolean 	addAll​(Collection<? extends E> c)
        //ordenamos la lista
        Collections.sort(listaCoches);
        //recorremos la lista
        for (Coche coche: listaCoches) {
            System.out.println(coche);
        }
    }
}

//vamos a necesitar traernos el atributo Set<Coche> de la clase concesionario, si no está hecho en la clase Concesionario, volvemos a esa clase
package coches;

/*Es una composición, parecida a la agregación, pero en este caso no puede existir un concesionario si no hay coches.
Como en la agregación unos de los atributos será una colección: array, List, Set, ...
Vamos a elegir Set pues los coches tienen matrícula, que en una base de datos sería la clave primaria
No puede haber dos coches diferentes con la misma matrícula.
Como los Set no pueden repetir objetos, puede ser una colección adecuada, también se podría usar List, pero habría que
controlar que no haya matrículas repetidas
IMPORTANTE!!!!!!!!!!
Nos obliga a decir cuando dos coches son iguales, en este caso cuando tienen la misma matrícula
La clase Coche tiene que tener sobreescrito los métodos hasCode e equals, usando la matricula como indicador de igualdad.
Si no lo hemos hecho, volvemos a la clase Coche y lo hacemos*/

import java.util.*;

public class Concesionario {

    //atributos: nombre de la empresa y una colección Set de objetos Coche
    private String nombreEmpresa;  //mejor que nombre, pueden aparecer otras variables nombre en el proyecto
    private Set<Coche> stockCoches;  //no lo inicializamos a conjunto vacío, por las especificaciones
    //No sabemos que tipo de Set usaremos, lo que si es cierto que es un Set y no un List

    //constructor
    /*Un constructor que inicialice los atributos, tanto nombre del concesionario como la colección de objetos,
     en este caso no vamos a crear la colección vacíıa, sino vamos  a pasar como argumento una colección de objetos Coche
     ESTO ES LO QUE DICE EL ENUNCIADO*/

    public Concesionario(String nombreEmpresa, Set<Coche> stockCoches) {
        this.nombreEmpresa = nombreEmpresa;
        this.stockCoches = stockCoches;

    }

    //getter para poder obtener esta colección desde fuera de esta clase, por ejemplo va a hacer falta en la clase TestConcesionario
    public Set<Coche> getStockCoches() {
        return stockCoches;
    }


    //Todos los métodos que vamos a usar son de INSTANCIA, NO DE CLASE (estáticos), operan sobre los atributos, bien aumentando
    //o disminuyendo el tamaño del atributo stockCoches, haciendo búsquedas en el mismo, etc...

    //método para añadir coches a la colección
    public boolean addCoche (Coche coche){
        return stockCoches.add(coche);  //el método add de Set devuelve un boolean, indicador del éxito de la operación
    }

    //método para quitar coches
    public boolean quitarCoche (Coche coche) {
        return stockCoches.remove(coche); //el método remove de Set devuelve un boolean, indicador del éxito de la operación
    }

    //método que devuelve subcolección de stockCoches, pertenecientes al mismo fabricante, debe devolver Set de acuerdo especificaciones
    public Set<Coche> obtenerCochesPorFabricante(String fabricante) {
        //creamos el set que vamos a devolver, escogemos HashSet pues es mas eficiente y es el que conocemos
        //la inicializamos a conjuto vacío, recorremos la colección Stock y si pertenece al fabricante que indicamos
        //la añadimos a esta nueva colección
        //vamos a necesitar un getter para el fabricante, si no lo hemos hecho en la clase Coche, vamos y lo implementamos
        Set<Coche> cochesDeUnFabricante = new HashSet<>();
        for (Coche coche: stockCoches) {
            if (coche.getFabricante().equalsIgnoreCase(fabricante))  //usamos equals NO == , son objetos, son String
                cochesDeUnFabricante.add(coche);
        }
        return cochesDeUnFabricante;
    }



    //método que devuelve una colección de tipo diccionario (Map), por año de fabricante, es decir metemos en esta colección
    //todos los coches que se fabricaron en un año determinado
    public Map<String, Coche> obtenerCocherFabricadoEnUnAnno(int anno){ //String para la matrícula, es la key y Coche para los valores a guardar
        //creamos el diccionario vacío, para posteriormente ir llenándolo
        Map<String, Coche> cochesFabricadosEnUnAnno = new HashMap<>(); //HasMap porque es la única que conocemos
        //recorremos la colección, si encontramos un coche de ese año lo añadimos, teniendo en cuenta:
        //matricula es la Key y el objeto Coche el Value
        //Igual que antes necesitamos un getter para matrícula y otro para año de fabricación, si no lo hemos hecho en la clase Coche, vamos y lo implementamos
        for (Coche coche: stockCoches) {
            if (coche.getAnnoFabricacion() == anno)  //ahora si usamos == y NO equals, pues es un entero, tipo primitivo
                cochesFabricadosEnUnAnno.put(coche.getMatricula(), coche);

        }
        //No podemos hacer cochesFabricadosEnUnAnno.put(coche.getMatricula(), coche); pues debemos poner un Integer, no un int
        //Se podía resolver en la clase Coche cambiando el tipo de año de fabricación de int a Integer
        //o como hemos hecho nosotros, usando el wrapper para Integer
        return cochesFabricadosEnUnAnno;

    }

    //el toString debe devolver: Nombre del concesionario: node vehículos que tiene
    @Override
    public String toString() {
        return String.format("%S: %d", nombreEmpresa, stockCoches.size()); //%s o %S da igual
    }





}

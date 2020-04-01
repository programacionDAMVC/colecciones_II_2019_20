package coches;

//Em esta clase, todos los métodos son estáticos, nos sirven para ayudar al resto de las clases
//en este proyecto, en particular, vamos a controlar el formato de la matrícula y el año de matriculación
//en la clase TestConcesionario

import java.time.LocalDate;

public class Auxiliar {

    //método que controla matrícula, donde el formato es CCCNNNNN, donde C es una letra mayúscula o minúscula
    //y N es un número entre 0 y 9. No comprobamos matrículas especiales, pues es un concesionario
    public static boolean chequearMatricula( String matricula ) {
        return matricula.toLowerCase().matches("[0-9]{4}[a-z]{3}"); //la ñ no la hemos incluido porque no aparece en matrículas
    }

    //método que controla el año de fabricación, desde 1950 a fecha actual. No usamos expresión regular pues el año es un entero
    //para el año actual usamos el método now de la clase LocalDate y posteriormente el método getYear que devuel el año como int
    public static boolean chequearAnnoFabricacion( int anno) {
        return anno >= 1950 && anno <= LocalDate.now().getYear();
    }
}

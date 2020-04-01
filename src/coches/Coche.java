package coches;

//Clase base, atributos, contructor, getters, setters, hasCode, equals

import java.util.Objects;

public class Coche implements  Comparable<Coche>{  //con esta interfaz nos permite establecer un orden en el conjunto

    //atributos, en la cabecera del csv aparace: matricula,modelo,año,fabricante
    private String matricula;
    private String modelo;
    private int annoFabricacion;  //no hace falta LocalDate, no interesa ni días, ni meses, solo el año de fabricación. No va a cambiar como un atributo edad
    private String fabricante;

    //constructor
    public Coche(String matricula, String modelo, int annoFabricacion, String fabricante) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.annoFabricacion = annoFabricacion;
        this.fabricante = fabricante;
    }

    //getter de fabricante, lo vamos a usar en la clase Concesionario
    public String getFabricante() {
        return fabricante;
    }

    //getter de matrícula, lo vamos a usar en la clase Concesionario
    public String getMatricula() {
        return matricula;
    }

    //getter de año de matriculación, lo vamos a usar en la clase Concesionario
    public int getAnnoFabricacion() {
        return annoFabricacion;
    }

    //toString para formato: 4904vwg-Civic-Honda-2007 como indican las especificaciones
    @Override
    public String toString() {
        return String.format("%s-%s-%s-%d", matricula, modelo, fabricante, annoFabricacion);
    }

    //Dos coches son iguales cuando tienen la misma matrícula, sobreescribimos con ese criterio equals y hasCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coche coche = (Coche) o;
        return Objects.equals(matricula, coche.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    //como en el TestConcesionario el listado de todos los coches hay que hacerlo por año de fabricación de mayor a menor:
    @Override
    public int compareTo(Coche coche) {
        return coche.annoFabricacion - this.annoFabricacion;
    }
}

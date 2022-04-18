package es.wasabi.mockito.ejemplos.services;

import es.wasabi.mockito.ejemplos.models.Examen;

import java.util.Arrays;
import java.util.List;

public class Datos {
    public final static List<Examen> EXAMENES = Arrays.asList(
            new Examen(5L, "Matematicas"),
            new Examen(6L, "Lengua"),
            new Examen(7L, "Historia"));

    public final static List<String> PREGUNTAS = Arrays.asList("aritmetica",
            "trigonometria","calculo","algebra");

    public final static Examen EXAMEN = new Examen(8L,"Filosofia");

    public final static Examen EXAMENID = new Examen(null, "Filosofia");

    public final static List<Examen> EXAMENES_ID_NULL = Arrays.asList(
            new Examen(null, "Matematicas"),
            new Examen(null, "Lengua"),
            new Examen(null, "Historia"));
}

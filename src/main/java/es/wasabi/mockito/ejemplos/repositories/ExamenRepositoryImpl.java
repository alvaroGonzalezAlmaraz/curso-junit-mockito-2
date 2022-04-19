package es.wasabi.mockito.ejemplos.repositories;

import es.wasabi.mockito.ejemplos.Datos;
import es.wasabi.mockito.ejemplos.models.Examen;

import java.util.List;

public class ExamenRepositoryImpl implements ExamenRepository{
    @Override
    public Examen guardar(Examen examen) {
        System.out.println("Guardar Examen");
        return Datos.EXAMEN;
    }

    @Override
    public List<Examen> findAll() {
        System.out.println("Find All examenes");
        return Datos.EXAMENES;
    }
}

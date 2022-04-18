package es.wasabi.mockito.ejemplos.repositories;

import es.wasabi.mockito.ejemplos.models.Examen;

import java.util.List;

public interface ExamenRepository {

    Examen guardar (Examen examen);

    List<Examen> findAll();


}

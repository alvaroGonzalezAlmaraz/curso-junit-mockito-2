package es.wasabi.mockito.ejemplos.repositories;

import es.wasabi.mockito.ejemplos.Datos;

import java.util.List;

public class PreguntaRepositoryImpl implements PreguntaRepository{

    @Override
    public void guardarVarias(List<String> preguntas) {
        System.out.println("Guardado Preguntas");
    }

    @Override
    public List<String> findPreguntasPorExamenId(Long id) {
        System.out.println("Listado Preguntas");
        return Datos.PREGUNTAS;
    }
}

package es.wasabi.mockito.ejemplos.services;

import es.wasabi.mockito.ejemplos.Datos;
import es.wasabi.mockito.ejemplos.models.Examen;
import es.wasabi.mockito.ejemplos.repositories.ExamenRepository;
import es.wasabi.mockito.ejemplos.repositories.ExamenRepositoryImpl;
import es.wasabi.mockito.ejemplos.repositories.PreguntaRepository;
import es.wasabi.mockito.ejemplos.repositories.PreguntaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExamenServiceImplSpyTest {

    //Mocks
    @Spy
    ExamenRepositoryImpl repository;

    @Spy
    PreguntaRepositoryImpl preguntaRepository;

    //Los dos Mock se inyectan aqui
    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp(){

      MockitoAnnotations.openMocks(this);

//        repository = mock(ExamenRepositoryImpl.class);
//        preguntaRepository = mock(PreguntaRepositoryImpl.class);
//
//        service = new ExamenServiceImpl(repository, preguntaRepository);
//        System.out.println("Iniciamos el repositorio");

    }

    @Test
    void testSpy() {

        ExamenService examenService = new ExamenServiceImpl(repository, preguntaRepository);

        List <String> preguntas = Arrays.asList("de la vida");
        //when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(preguntas);//Datos.PREGUNTAS);
        doReturn(preguntas).when(preguntaRepository).findPreguntasPorExamenId(anyLong());

        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5L, examen.getId());
        assertEquals("Matematicas", examen.getNombre());
        assertEquals(1, examen.getPreguntas().size());

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }
}
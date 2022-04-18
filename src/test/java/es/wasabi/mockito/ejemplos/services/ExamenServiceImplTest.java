package es.wasabi.mockito.ejemplos.services;

import es.wasabi.mockito.ejemplos.models.Examen;
import es.wasabi.mockito.ejemplos.repositories.ExamenRepository;
import es.wasabi.mockito.ejemplos.repositories.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ExamenServiceImplTest {

    //Mocks
    @Mock
    ExamenRepository repository;

    @Mock
    PreguntaRepository preguntaRepository;

    //Los dos Mock se inyectan aqui
    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp(){

        MockitoAnnotations.openMocks(this);

//        repository = mock(ExamenRepository.class);
//        preguntaRepository = mock(PreguntaRepository.class);
//
//        service = new ExamenServiceImpl(repository, preguntaRepository);
//        System.out.println("Iniciamos el repositorio");

    }

    @Test
    void findExamenPorNombre() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        Optional<Examen> examen= service.findExamenPorNombre("Matematicas");
        assertTrue(examen.isPresent());
        assertEquals(5L, examen.orElseThrow().getId());
        assertEquals("Matematicas", examen.orElseThrow().getNombre());

    }

    @Test
    void findExamenPorNombreLiustaVacia() {

        List<Examen> datos = Collections.emptyList();

        when(repository.findAll()).thenReturn(datos);

        Optional<Examen> examen= service.findExamenPorNombre("Matematicas");

        assertFalse(examen.isPresent());

    }

    @Test
    void testPreguntasExamen() {

        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");

        assertEquals(4, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmetica"));

    }

    @Test
    void testPreguntasExamenVerify() {

        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");

        assertEquals(4, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmetica"));

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(5l);

    }

    @Test
    void testNoExisteExamenVerify() {

        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");

        //assertNull(examen);

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(5l);

    }

    @Test
    void guardarExamen() {
        when(repository.guardar(any(Examen.class))).thenReturn(Datos.EXAMEN);

        //Examen examen = service.guardar(Datos.EXAMEN);

        Examen examenConPreguntas = Datos.EXAMEN;

        examenConPreguntas.setPreguntas(Datos.PREGUNTAS);

        Examen examen = service.guardar(examenConPreguntas);

        assertNotNull(examen.getId());
        assertEquals(8L, examen.getId());
        assertEquals("Filosofia", examen.getNombre());

        verify(repository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());
    }

    @Test
    void guardarExamenIdIncremental() {
        //Given
        Examen examenConPreguntas = Datos.EXAMENID;
        examenConPreguntas.setPreguntas(Datos.PREGUNTAS);

        when(repository.guardar(any(Examen.class)))
                .then(new Answer<Examen>(){

                    Long secuencia = 9L;
                    @Override
                    public Examen answer(InvocationOnMock invocation) throws Throwable {
                        Examen examen = invocation.getArgument(0);
                        examen.setId(secuencia++);
                        return examen;
                    }
                });

        //When
        Examen examen = service.guardar(examenConPreguntas);

        //Then
        assertNotNull(examen.getId());
        assertEquals(9L, examen.getId());
        assertEquals("Filosofia", examen.getNombre());

        verify(repository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());
    }

    @Test
    void testExceptions() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong()))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () ->{
            service.findExamenPorNombreConPreguntas("Matematicas");
        });
    }

    @Test
    void testExceptions2() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong()))
                .thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->{
            service.findExamenPorNombreConPreguntas("Matematicas");
        });

        assertEquals(IllegalArgumentException.class, exception.getClass());

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testExceptionsNull() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NULL);
        when(preguntaRepository.findPreguntasPorExamenId(isNull()))
                .thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->{
            service.findExamenPorNombreConPreguntas("Matematicas");
        });

        assertEquals(IllegalArgumentException.class, exception.getClass());

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(isNull());
    }

    @Test
    void testArgumentMatchers() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(argThat(arg ->
                arg.equals(5L)));

    }

    @Test
    void testArgumentMatchersPersonalizado() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombreConPreguntas("Matematicas");


        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(argThat(new MiArgsMatchers()));

    }


    public static class MiArgsMatchers implements ArgumentMatcher<Long> {

        private Long argument;

        @Override
        public boolean matches(Long argument) {
            this.argument = argument;
            return argument != null && argument > 0;
        }

        @Override
        public String toString() {
            return "mensaje personalizado de error, " + argument + " debe ser un numero positivo";
        }
    }
}
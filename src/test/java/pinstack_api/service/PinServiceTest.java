package pinstack_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pinstack_api.DTOs.ResponsePinDTO;
import pinstack_api.entities.PinEntity;
import pinstack_api.repositories.PinRepository;
import pinstack_api.services.impls.PinServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PinServiceTest {

    @Mock
    private PinRepository pinRepository;

    @InjectMocks
    private PinServiceImpl pinService;

    private PinEntity pinExemple;

    @BeforeEach
    void setUp() {
        pinExemple = new PinEntity();
        pinExemple.setId("123");
        pinExemple.setTitle("Pin de Teste");
        pinExemple.setDescription("Descricao de Teste");
        pinExemple.setImageUrl("http://imagem.com");
        pinExemple.setLikesCount(5);
    }

    @Test
    @DisplayName("Should return a list of ResponsePinDTO when there are pins in the database")
    void shouldReturnAllPinsSuccessfully() {
        when(pinRepository.findAll()).thenReturn(List.of(pinExemple));

        List<ResponsePinDTO> result = pinService.getAllPins();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pin de Teste", result.get(0).title());
        verify(pinRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return a ResponsePinDTO when searching by an existing ID")
    void shouldReturnPinByIdSuccessfully() {
        when(pinRepository.findById("123")).thenReturn(Optional.of(pinExemple));

        ResponsePinDTO result = pinService.getSpecificPin("123");

        assertNotNull(result);
        assertEquals("123", result.id());
        assertEquals("Pin de Teste", result.title());
    }

    @Test
    @DisplayName("Should throw NotFoundException when the pin is not found by ID")
    void shouldThrowNotFoundExceptionWhenPinDoesNotExist() {
        when(pinRepository.findById("id-fantasma")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            pinService.getSpecificPin("id-fantasma");
        });
    }
}
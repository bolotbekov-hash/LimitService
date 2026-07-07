package bakaibank.PracticeWork2.service;

import bakaibank.PracticeWork2.entity.Limit;
import bakaibank.PracticeWork2.repository.LimitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LimitServiceTest {

    @Mock
    private LimitRepository limitRepository; // Фальшивый репозиторий

    @InjectMocks
    private LimitService limitService; // Тестируемый сервис, куда подставится Mock репозитория

    private Limit existingLimit;
    private Limit limitDetails;

    @BeforeEach
    void setUp() {
        // Подготовка данных перед каждым тестом
        existingLimit = new Limit();
        existingLimit.setId(1L);
        existingLimit.setType("CASH");
        existingLimit.setDefaultSum(new BigDecimal("10000.00"));
        existingLimit.setDefaultOperationCount(5);
        existingLimit.setMaxSum(new BigDecimal("50000.00"));
        existingLimit.setMaxOperationCount(10);

        limitDetails = new Limit();
        limitDetails.setType("CASH_UPDATED");
        limitDetails.setDefaultSum(new BigDecimal("20000.00"));
        limitDetails.setDefaultOperationCount(7);
        limitDetails.setMaxSum(new BigDecimal("80000.00"));
        limitDetails.setMaxOperationCount(15);
    }

    // 1. ТЗ: Получение лимитов (getAllActiveLimits)
    @Test
    void shouldReturnAllActiveLimits() {
        // Arrange: говорим репозиторию вернуть список из одного лимита
        when(limitRepository.findAll()).thenReturn(List.of(existingLimit));

        // Act: вызываем метод сервиса
        List<Limit> result = limitService.getAllActiveLimits();

        // Assert: проверяем результаты
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CASH", result.get(0).getType());
        verify(limitRepository, times(1)).findAll();
    }

    // 2. ТЗ: Изменение лимита (updateLimit)
    @Test
    void shouldUpdateLimitSuccessfully() {
        // Arrange: имитируем, что лимит с ID 1 найден в базе
        when(limitRepository.findById(1L)).thenReturn(Optional.of(existingLimit));
        // Имитируем сохранение: метод save просто возвращает то, что в него передали
        when(limitRepository.save(any(Limit.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act: вызываем обновление
        Limit updated = limitService.updateLimit(1L, limitDetails);

        // Assert: проверяем, что все поля обновились корректно
        assertNotNull(updated);
        assertEquals("CASH_UPDATED", updated.getType());
        assertEquals(new BigDecimal("20000.00"), updated.getDefaultSum());
        assertEquals(7, updated.getDefaultOperationCount());
        assertEquals(new BigDecimal("80000.00"), updated.getMaxSum());
        assertEquals(15, updated.getMaxOperationCount());

        verify(limitRepository, times(1)).findById(1L);
        verify(limitRepository, times(1)).save(existingLimit);
    }

    // 3. ТЗ: Проверка бизнес-правил (Ошибка, если ID лимита не существует)
    @Test
    void shouldThrowExceptionWhenLimitIdNotFound() {
        // Arrange: имитируем, что лимит с ID 99 не найден (возвращаем Optional.empty())
        when(limitRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert: проверяем, что вылетает RuntimeException с вашим текстом
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            limitService.updateLimit(99L, limitDetails);
        });

        assertEquals("Тип лимита с ID 99 не найден", exception.getMessage());

        // Проверяем, что метод save даже не пытался вызваться, так как сработал эксепшн
        verify(limitRepository, never()).save(any(Limit.class));
    }
}
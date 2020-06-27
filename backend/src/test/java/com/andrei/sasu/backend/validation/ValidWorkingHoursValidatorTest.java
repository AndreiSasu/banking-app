package com.andrei.sasu.backend.validation;

import com.andrei.sasu.backend.model.CreateAccountRequest;
import org.assertj.core.api.Assertions;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class ValidWorkingHoursValidatorTest {

    @Mock
    private WorkingDays workingDays;
    @Mock
    private WorkingHours workingHours;

    private ValidWorkingHoursValidator validWorkingHoursValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        validWorkingHoursValidator = new ValidWorkingHoursValidator(workingDays, workingHours);
    }

    @Test
    public void testValidWithinWorkingHoursAndDaysHappyPath() {
        Mockito.when(workingDays.isOpen(Mockito.any(LocalDateTime.class))).thenReturn(true);
        Mockito.when(workingHours.isOpen(Mockito.any(LocalDateTime.class))).thenReturn(true);
        Assertions.assertThat(validWorkingHoursValidator.isValid(new CreateAccountRequest(), Mockito.mock(ConstraintValidatorContext.class))).isTrue();
    }

    @Test
    public void testValidWithinWorkingDaysButNotWithinHours() {
        Mockito.when(workingDays.isOpen(Mockito.any(LocalDateTime.class))).thenReturn(true);
        Mockito.when(workingHours.isOpen(Mockito.any(LocalDateTime.class))).thenReturn(false);
        Assertions.assertThat(validWorkingHoursValidator.isValid(new CreateAccountRequest(), Mockito.mock(ConstraintValidatorContext.class))).isFalse();
    }

    @Test
    public void testValidWithinWorkingHoursButNotWithinDays() {
        Mockito.when(workingDays.isOpen(Mockito.any(LocalDateTime.class))).thenReturn(false);
        Mockito.when(workingHours.isOpen(Mockito.any(LocalDateTime.class))).thenReturn(true);
        Assertions.assertThat(validWorkingHoursValidator.isValid(new CreateAccountRequest(), Mockito.mock(ConstraintValidatorContext.class))).isFalse();
    }
}

package br.com.raphaelneves.test;


import br.com.raphaelneves.cpf.annotation.CPF;
import br.com.raphaelneves.cpf.validator.CpfValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;

public class CpfValidatorTest {

    private ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
    private CpfValidator validadorRequerido;
    private CpfValidator validadorNaoRequerido;

    private static class ObjetoCpfRequerido {
        @CPF(required = true)
        String cpf;
    }

    private static class ObjetoCpfNaoRequerido {
        @CPF
        String cpf;
    }

    @Before
    public void criarValidador() throws NoSuchFieldException {
        CPF cpfRequerido = ObjetoCpfRequerido.class.getDeclaredField("cpf").getAnnotation(CPF.class);
        validadorRequerido = new CpfValidator();
        validadorRequerido.initialize(cpfRequerido);

        CPF cpfNaoRequerido = ObjetoCpfNaoRequerido.class.getDeclaredField("cpf").getAnnotation(CPF.class);
        validadorNaoRequerido = new CpfValidator();
        validadorNaoRequerido.initialize(cpfNaoRequerido);
    }

    @Test
    public void invalidarCpfComDigitosRepetidos(){
        boolean valido = validadorRequerido.isValid("11111111111", context);
        Assert.assertFalse(valido);
    }

    @Test
    public void invalidarCpfForaDoPadrao(){
        boolean valido = validadorRequerido.isValid("a123455a55f", context);
        Assert.assertFalse(valido);
    }

    @Test
    public void invalidarCpfFaltandoDigitos(){
        boolean valido = validadorRequerido.isValid("111", context);
        Assert.assertFalse(valido);
    }

    @Test
    public void invalidarCpfIncorreto(){
        boolean valido = validadorRequerido.isValid("11144477723", context);
        Assert.assertFalse(valido);
    }

    @Test
    public void validarCpfCorreto(){
        boolean valido = validadorRequerido.isValid("11144477735", context);
       Assert.assertTrue(valido);
    }

    @Test
    public void invalidarCpfRequeridoNulo(){
        boolean valido = validadorRequerido.isValid(null, context);
        Assert.assertFalse(valido);
    }

    @Test
    public void validarCpfNaoRequeridoNulo(){
        boolean valido = validadorNaoRequerido.isValid(null, context);
        Assert.assertTrue(valido);
    }

    @Test
    public void validarCpfNaoRequerido(){
        boolean valido = validadorNaoRequerido.isValid("11144477735", context);
    }




}

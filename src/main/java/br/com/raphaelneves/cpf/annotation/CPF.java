package br.com.raphaelneves.cpf.annotation;

import br.com.raphaelneves.cpf.validator.CpfValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CpfValidator.class)
public @interface CPF {

    String message() default "CPF inválido";
    boolean required() default false;

}

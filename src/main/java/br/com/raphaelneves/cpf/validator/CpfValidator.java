package br.com.raphaelneves.cpf.validator;

import br.com.raphaelneves.cpf.annotation.CPF;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CpfValidator implements ConstraintValidator<CPF, String>{

    private CPF annotation;
    private String corpo;
    private String primeiroDigitoVerificador;
    private String segundoDigitoVerificador;

    @Override
    public void initialize(CPF constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(("".equals(value) || value == null) && !annotation.required()) {
            return true;
        }else if(("".equals(value) || value == null) && annotation.required()){
            return false;
        }else if(value != null){
            separarElementosCpf(value);
            if(isFormatoValido(value) && !isCpfDigitoRepetido(value) &&
                    isPrimeiroDigitoVerificadorValido() && isSegundoDigitoVerificadorValido()){
                return true;
            }
        }
        return false;
    }

    public void separarElementosCpf(String cpf){
        this.corpo = cpf.substring(0, cpf.length() - 2);
        this.primeiroDigitoVerificador = cpf.substring(cpf.length() - 2, cpf.length() - 1);
        this.segundoDigitoVerificador = cpf.substring(cpf.length() - 1);
    }

    private boolean isFormatoValido(String value){
        Pattern pattern = Pattern.compile("\\d{11}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    private boolean isCpfDigitoRepetido(String str) {
        if(str.equals("11111111111") || str.equals("22222222222") || str.equals("33333333333") || str.equals("44444444444")
                || str.equals("55555555555") || str.equals("66666666666") || str.equals("77777777777")
                || str.equals("88888888888") || str.equals("99999999999")){
            return true;
        }
        return false;
    }

    private boolean isPrimeiroDigitoVerificadorValido(){
        Integer somaDigitoVerificador = 0;
        for(int i = 0, multiplicador = 10; i < corpo.length(); i++, multiplicador--){
            String num = corpo.substring(i, i+1);
            somaDigitoVerificador += Integer.parseInt(num) * multiplicador;
        }
        int resto = (somaDigitoVerificador * 10)%11;
        if(Integer.valueOf(primeiroDigitoVerificador) == Integer.valueOf(resto)){
            return true;
        }
        return false;
    }

    private boolean isSegundoDigitoVerificadorValido(){
        Integer somaDigitoVerificador = 0;
        String str = corpo + primeiroDigitoVerificador;
        for(int i = 0, multiplicador = 11; i < str.length(); i++, multiplicador--){
            String num = str.substring(i, i+1);
            somaDigitoVerificador += Integer.parseInt(num) * multiplicador;
        }
        int resto = (somaDigitoVerificador * 10)%11;

        if(Integer.valueOf(segundoDigitoVerificador) == Integer.valueOf(resto)){
            return true;
        }
        return false;
    }

}

package br.com.itau.pix.domain.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CpfValidator {

    private static final int[] PESO_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final String REGEX_ONZE_DIGITOS_IGUALS = "^(?=([0-9]))\1{11}$";

    public static boolean validaCpf(String cpf) {
        if ((cpf == null) || (cpf.length() != 11) || ehOnzeDigitosIguais(cpf)) {
            return false;
        }

        Integer digito1 = calcularDigito(cpf.substring(0, 9), PESO_CPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, PESO_CPF);

        return cpf.equals(cpf.substring(0, 9) + digito1 + digito2);
    }

    private static boolean ehOnzeDigitosIguais(String cpf) {
        Pattern pattern = Pattern.compile(REGEX_ONZE_DIGITOS_IGUALS);
        Matcher matcher = pattern.matcher(cpf);
        return matcher.find();
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

}
package br.com.itau.pix.domain.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

 public final class CnpjValidator {

     private CnpjValidator() {
     }

     private static final String REGEX_QUATORZE_DIGITOS_IGUALS = "^(?=([0-9]))\\1{14}$";
    private static final int[] PESO_CNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean validaCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d.]", "");
        if (cnpj.length() != 14 || ehQuatorzeDigitosIguais(cnpj)) {
            return false;
        }
        int digito1 = calcularDigito(cnpj.substring(0,12));
        int digito2 = calcularDigito(cnpj.substring(0,12) + digito1);
        return cnpj.equals(cnpj.substring(0,12) + digito1 + digito2);
    }

    private static int calcularDigito(String str) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*PESO_CNPJ[PESO_CNPJ.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    private static boolean ehQuatorzeDigitosIguais(String cnpj) {
        Pattern pattern = Pattern.compile(REGEX_QUATORZE_DIGITOS_IGUALS);
        Matcher matcher = pattern.matcher(cnpj);
        return matcher.find();
    }

}
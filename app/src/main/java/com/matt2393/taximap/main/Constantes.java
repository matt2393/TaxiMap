package com.matt2393.taximap.main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Constantes {

    public final static String URL="http://radiomoviltourarevalo-or.hol.es/"; //192.168.56.1 virtual box //http://radiomoviltourarevalo-or.hol.es/ dominio
    public final static String URL_GET_USUARIOS=URL+"api/Api_rest/user";
    public final static String URL_PUT_USUARIO=URL+"api/Api_rest/user";
    public final static String URL_POST_RECORRIDO=URL+"api/Api_rest/recorrido";
    public final static String URL_GET_GPS=URL+"api/Api_rest/gps/fecha/";
    public final static String URL_POST_LOGIN=URL+"api/Api_rest/login";
    public final static String URL_PUT_LOGOUT=URL+"api/Api_rest/logout";
    public final static String URL_GET_LIMPIAR=URL+"api/Api_rest/limpiar";

    public final static String GET_USUARIO="";
    public final static String PUT_GPS="";


    private final static char[] HEXADECIMALES = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };

    public static String cryptMD5(String textoPlano)
    {
        try
        {

            MessageDigest msgdgt = MessageDigest.getInstance("MD5");
            byte[] bytes = msgdgt.digest(textoPlano.getBytes());
            StringBuilder strCryptMD5 = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++)
            {
                int low = (int)(bytes[i] & 0x0f);
                int high = (int)((bytes[i] & 0xf0) >> 4);
                strCryptMD5.append(HEXADECIMALES[high]);
                strCryptMD5.append(HEXADECIMALES[low]);
            }
            return strCryptMD5.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

}

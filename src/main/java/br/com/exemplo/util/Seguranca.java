package br.com.exemplo.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author William
 */
public class Seguranca {

    public static String md5(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(senha.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            String retornaSenha = hash.toString(16);
            return retornaSenha;
        } catch (NoSuchAlgorithmException ns) {
            ns.printStackTrace();
        }
        return null;
    }
}

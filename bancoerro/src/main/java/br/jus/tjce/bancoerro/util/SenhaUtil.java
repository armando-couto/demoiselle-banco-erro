package br.jus.tjce.bancoerro.util;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SenhaUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static String md5(String senha){  
        String sen = "";  
        MessageDigest md = null;  
        
        try {  
            md = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
        sen = hash.toString(16);   
        
        return sen;  
    }
}
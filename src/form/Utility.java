/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

import database.KoneksiDB;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author resar
 */
public class Utility {
    KoneksiDB k = new KoneksiDB();
    Connection con = k.connection;
    
    public static String capitalizeWord(String str){  
        String words[]=str.split("\\s");  
        String capitalizeWord="";  
        for(String w:words){  
            String first=w.substring(0,1);  
            String afterfirst=w.substring(1);  
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";  
        }  
        return capitalizeWord.trim();  
    }
    
    public static String getMd5(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            byte[] messageDigest = md.digest(input.getBytes());
  
            BigInteger no = new BigInteger(1, messageDigest);
  
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } 
  
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String[] ShowInfo(){
        Statement st;
        ResultSet rs;
        DynamicArrayString result = new DynamicArrayString(1);

        String query = "SELECT * FROM info";
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);

            while(rs.next()){
                result.insert(rs.getString("pesan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.arr;
    }
}
        

    

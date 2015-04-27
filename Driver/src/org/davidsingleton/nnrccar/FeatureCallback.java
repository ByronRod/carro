package org.davidsingleton.nnrccar;

public interface FeatureCallback {

	public void features(byte[] features, int width, int height, float[] accelerometerFeatures);
}

ConfiguraModuloBluetooth.ino

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/**
 *
 * @author ISCPC
 */
public class manejoArchivos {


    public manejoArchivos() {
    }


/**
     * Metodo que permite leer el arhivo
     * @return un String de cada linea del archivo
     * @exception si el archivo no existe
     */
    public String leerAr(File archivo){
        File Archivolecto=new File(archivo.getAbsolutePath());
        FileReader lector=null;
        BufferedReader buflector=null;
         String linea,linea2="";
        // linea2=System.getProperty("line.separator");
    try{
    lector=new FileReader(Archivolecto);
    buflector=new BufferedReader(lector);
    
    while((linea=buflector.readLine())!=null){
       if(!linea.contains("#")) //filtrando para obtener solo los pesos
     linea2+=linea+System.getProperty("line.separator");
     //una nueva linea al final de cada linea
    
    }


        }catch(Exception e){
    e.printStackTrace();
    }finally{
        try{
        if(Archivolecto==null) {
            } else {
            buflector.close();
            }
        }catch(IOException e){
        }
    }


return linea2;
    }    
/***
 * Funcion que guarda el arbol en un archivo binario
 * @param Guardar
 */
public void GuardarArbol(ArrayList Guardar){
File tmp=  new File("src/lista.bin");
if(tmp.exists()){
tmp.delete();
}
ObjectOutputStream GuArbol=null;
        try {
            GuArbol = new ObjectOutputStream(new FileOutputStream("src/lista.bin",true));
            GuArbol.writeObject(Guardar);
            GuArbol.close();
        } catch (IOException ex) {
  ex.printStackTrace();
        }
}
/***
 * Funcion que permite leer un archivo binario, que contiene
 * el arbol correspondiente con los codigos de huffam y los valores de
 * los caracteres
 * @return el arbol recuperado
 */
public ArrayList RecuperarArbol(){
    ArrayList tmp=null;
    try {
           ObjectInputStream RecuperarA = new ObjectInputStream(
                   new FileInputStream("src/lista.bin"));
      tmp=(ArrayList) RecuperarA.readObject();
      RecuperarA.close();
  File eliminar=new File("src/lista.bin");


      return tmp;
        } catch (IOException ex) {
             ex.printStackTrace();
        }catch(ClassNotFoundException ex){
          ex.printStackTrace();
        }
 
  // eliminar.deleteOnExit();
    return tmp;
}


}

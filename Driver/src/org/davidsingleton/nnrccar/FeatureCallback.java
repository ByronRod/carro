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


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *Clase para realizar diferentes acciones
 * @author ISCPC
 */

public class basic {
    ArrayList pesos=new ArrayList();
    manejoArchivos leer=new manejoArchivos();
    double [][] pesosCapaSalida;
    double [][] pesosCapaOculta;
    double [][] pesosCapaEntrada;
    Red nuevaRed=new Red();
    public basic() {
    }
 
 public String modoEjecucion(){
     String salidaRedNormal="";   
     try {
            double [] arreglo=imagen();
            salidaRedNormal=this.nuevaRed.resultado(arreglo);               
        } catch (IOException ex) {
            Logger.getLogger(basic.class.getName()).log(Level.SEVERE, null, ex);
        }
 return salidaRedNormal;
 
 }   
 /***
  * Metodo para cargar pesos a la red neuronal
  * Paso2
  */  
public void CargarPesosaRed(){
recuperarPesos();//recuperar archivos desde binario
this.nuevaRed.setCapaEntrada(pesosCapaEntrada);
this.nuevaRed.setCapaOculta(pesosCapaOculta);
this.nuevaRed.setCapaSalida(pesosCapaSalida);
}    
    
    
/**
 * Metodo para convertir las imagenes 
 * @return un arreglo double de 1 y 0 
 * @throws IOException 
 */
public double [] imagen() throws IOException{


 double [] resultado = null;
 int [] pixel=new int[3];
 int a=0;
 int i=0;
 int j=0;
 int promedio=0;
 int color=0;
 try{
     //Lectura de imagen en escala de grises
  File bufferedImage = new File("image/i.jpg");
 BufferedImage imagen=ImageIO.read(bufferedImage); 
 ///Creando imagen en blanco y negro 
 BufferedImage blackAndWhiteImg = new BufferedImage(
    imagen.getWidth(), imagen.getHeight(),
    BufferedImage.TYPE_BYTE_BINARY);
   Graphics2D graphics = blackAndWhiteImg.createGraphics();
    graphics.drawImage(imagen,0,0, null); 
     File re= new File("image/nueva.jpg"); 
    
if(re.exists())
{
try{
re.delete(); //si el archivo existe se elimina pues pertenece a otro codigo
}catch(AbstractMethodError ex){
ex.printStackTrace();
}}
    ImageIO.write(blackAndWhiteImg, "jpg",re);
   
 File blanco = new File("image/nueva.jpg");
 BufferedImage nueva=ImageIO.read(blanco); //guardar nueva imagen en blanco y negro
 
    int columnas=nueva.getWidth();
 int filas=nueva.getHeight();
 resultado= new double[columnas*filas+1]; 
  
 //Filtrando la imagen para obtener 1 y 0 puros
for(i=0;i<filas;i++){
     for(a=0;a<columnas;a++){
        pixel= nueva.getRaster().getPixel(a, i, new int[3]);
       promedio =(pixel[0]+pixel[1]+pixel[2])/3;
       if(promedio<=127)
           color=0;
       else
           color=1;
         resultado[j]=color;
         // System.out.println(resultado[j]);
         j++;
     }
 }
 //System.out.print(resultado.length);
 }catch(IOException exception){
 }
     
return resultado;
}
/***
 * Metodo Cargar pesos desde archivos .m
 * Metodo para cargar pesos
 * Paso 1
 */
       public void CargarPesosS(){
           //los pesos se encuentrar en la caprte Pesos
  String capaSalida="Pesos/PesosCapaSalida.m"; 
  String capaOculta="Pesos/PesosCapaOculta.m";
  String capaEntrada="Pesos/PesosCapaEntrada.m";
      this.pesosCapaSalida=ObtenerPesos(capaSalida);
       this.pesosCapaOculta=ObtenerPesos(capaOculta);
        this.pesosCapaEntrada=ObtenerPesos(capaEntrada);   
        
        pesos.add(pesosCapaEntrada);
        pesos.add(pesosCapaOculta);
        pesos.add(pesosCapaSalida);
        leer.GuardarArbol(pesos);
      System.out.println("Bien Hecho!!!");
 
 }  
 /***
  * Obtener archivos desde arbol
  */
 public void recuperarPesos(){
 pesos=leer.RecuperarArbol();
 this.pesosCapaEntrada=(double[][]) pesos.get(0);
 this.pesosCapaOculta=(double[][]) pesos.get(1);
 this.pesosCapaSalida=(double[][]) pesos.get(2);
 
 }
 /***
  * Obtener pesos de una direccion especifica
  * @param direccion
  * @return 
  */   
   public double [][] ObtenerPesos(String direccion)
   {
       
        File archivo;
        archivo = new File(direccion);
        double [][]pesos=ObtenerPesos(archivo);
        return pesos;  
   }
  /***
   * Obtener los pesos de un archivo
   * @param archivo
   * @return una matriz de pesos
   */ 
   public  double [][] ObtenerPesos(File archivo){
        String m,linea=" ";
      
        m=leer.leerAr(archivo);
        StringTokenizer filas=new StringTokenizer(m,System.lineSeparator());
        StringTokenizer filas1=new StringTokenizer(m,System.lineSeparator());
        StringTokenizer ccolumnas=new StringTokenizer(filas1.nextToken());
        double [][]npesos=new double[filas1.countTokens()+1][ccolumnas.countTokens()];
      //numero de filas=numero de neuronas numero de columnas=numero de pesos
        int contarfilas=0;
         while(filas.hasMoreElements()){
             linea=filas.nextToken();
            {
             StringTokenizer columnas=new StringTokenizer(linea);
              int contarColumnas=0;
              while (columnas.hasMoreElements()) {
              {         npesos[contarfilas][contarColumnas]= Float.valueOf(columnas.nextToken());
                        System.out.println(npesos[contarfilas][contarColumnas]);
                        contarColumnas++;}
             }
                contarfilas++;
        }
         }
        
        return npesos;
   
   }
           }

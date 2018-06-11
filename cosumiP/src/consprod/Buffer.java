
package consprod;

import java.util.concurrent.Semaphore;

/**
 *
 * @author daniel
 */
public class Buffer {
    
    /*
    *   Declaración de variables.
    */
    int[] buffer;
    int entrada = 0; //Primera posicion donde puedo poner
    int salida = 0; //Primera posicion donde puedo extraer
    int contador = 0; 	//Toma el tamaño del buffer
    boolean terminan_prod = true;
    boolean terminan_cons = true;
    int tam_buffer;
    
    //Evita que el buffer sea modificado por prod y cons al mismo tiempo
    public Semaphore mutex; 
    public Semaphore lleno;
    public Semaphore vacio;
    
    /*
    *   Constructor del objeto Buffer.
    */
    public Buffer(int tam_buffer, boolean terminan_prod, boolean terminan_cons){
        
        this.tam_buffer = tam_buffer;
        this.terminan_cons = terminan_cons;
        this.terminan_prod = terminan_prod;
        buffer = new int [tam_buffer];
        mutex=new Semaphore(1,true);
        vacio=new Semaphore(tam_buffer,true);
        lleno=new Semaphore(0,true);
        
    }
    
    /*
    *   Módulo para imprimir el buffer.
    */
    public void printBuffer(){
        
        System.out.println("---BUFFER---");
        for(int i = 0; i<tam_buffer; i++){
            System.out.print(buffer[i]);
        }
        System.out.println("");
        System.out.println("------------");
    }
    
    /*
    *   Módulo para producir.
    *   Usar synchronized para utilizar cerrojos en lugar de semáforos
    *   Se eliminarian los semáforos de este módulo.
    */
    public void poner () throws InterruptedException{
       
        do{
            vacio.acquire();
            mutex.acquire();

            this.buffer[entrada] = 1; //Coloco dato en la posicion i
            System.out.println("Hebra productora, produciendo en la posición: "+ entrada);
            printBuffer();
            entrada=(entrada+1)%tam_buffer;
            contador++;

            mutex.release();
            lleno.release(); //Aviso que hay datos
        }while(terminan_prod == false);
    }
    
    /*
    *   Módulo para consumir.
    *   Usar synchronized para utilizar cerrojos en lugar de semáforos
    *   Se eliminarian los semáforos de este módulo
    */
    public void extraer() throws InterruptedException{
        
        do{
            lleno.acquire(); //Compruebo si el buffer esta lleno
            mutex.acquire();

            this.buffer[salida] = 0;
            System.out.println("Hebra consumidora, consumiendo en la posición: "+ salida);
            printBuffer();
            salida = (salida+1)%tam_buffer;
            contador--;

            mutex.release();
            vacio.release(); //Aviso que hay espacio en el buffer
        }while(terminan_cons == false);
    }
}



package monitores;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    
    //Declaracion del cerrojo
    Lock cerrojo = new ReentrantLock();
    
    
    //Declaracion de variables condicion
    Condition mutex = cerrojo.newCondition();
    Condition lleno = cerrojo.newCondition();
    Condition vacio = cerrojo.newCondition();

    /*
    *   Constructor del objeto Buffer.
    */
    public Buffer(int tam_buffer, boolean terminan_prod, boolean terminan_cons){
        
        this.tam_buffer = tam_buffer;
        this.terminan_cons = terminan_cons;
        this.terminan_prod = terminan_prod;
        buffer = new int [tam_buffer];
        
        
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
    *   Usar synchronized para utilizar Monitores en lugar de semáforos
    *   Se eliminarian los semáforos de este módulo.
    */
    public synchronized void poner () throws InterruptedException{
       
        do{
            
            while (contador == tam_buffer){
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            
            this.buffer[entrada] = 1; //Coloco dato en la posicion i
            System.out.println("Hebra productora, produciendo en la posición: "+ entrada);
            printBuffer();
            entrada=(entrada+1)%tam_buffer;
            contador++;

            notify();
            
        }while(terminan_prod == false);
    }
    
    /*
    *   Módulo para consumir.
    *   Usar synchronized para utilizar Monitores en lugar de semáforos
    *   Se eliminarian los semáforos de este módulo
    */
    public synchronized void extraer() throws InterruptedException{
        
        do{
            
            while(contador == 0){
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

            this.buffer[salida] = 0;
            System.out.println("Hebra consumidora, consumiendo en la posición: "+ salida);
            printBuffer();
            salida = (salida+1)%tam_buffer;
            contador--;

            notify();
            
        }while(terminan_cons == false);
    }
}

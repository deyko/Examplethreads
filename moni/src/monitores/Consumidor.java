
package monitores;

/**
 *
 * @author daniel
 */
public class Consumidor implements Runnable{
    
    
    private Buffer buffer;
    public Thread thr ; // objeto hebra encapsulado
    
    
    public Consumidor(String nombre,Buffer buffer){
        
        this.buffer = buffer;
        thr = new Thread (this, nombre);
    }
    
    //Método run de consumidor.
    @Override
    public void run(){
    
        try {
            buffer.extraer();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

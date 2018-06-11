
package cerrojos;

/**
 *
 * @author daniel
 */
public class Productor implements Runnable{
    
    private Buffer buffer;
    public Thread thr ; // objeto hebra encapsulado
    
    public Productor(String nombre, Buffer buffer){
        
        this.buffer=buffer;
        thr = new Thread (this, nombre);
    }
    
    //Método run de Productor.
    @Override
    public void run(){
       
        try {
            buffer.poner();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    
    }
}

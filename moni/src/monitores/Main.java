
package monitores;

/**
 *
 * @author daniel
 * Versión Hebras con Monitores.
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        /*********************
        * VARIABLES GLOBALES * 
        **********************/
       
        boolean terminan_prod = true;
        boolean terminan_cons = true;
        int tam_buffer;
        int num_hebras_prod;
        int num_hebras_cons;

        //-------------------//
        
        if(args.length < 5 ){
            System.out.println( "Faltan parámetros" );
            System.exit( 1 );
        }
        
        tam_buffer = Integer.parseInt(args[0]);
        num_hebras_prod = Integer.parseInt(args[1]);
        terminan_cons = Boolean.parseBoolean(args[2]);
        num_hebras_cons = Integer.parseInt(args[3]);
        terminan_prod = Boolean.parseBoolean(args[4]);
        
        Buffer b = new Buffer(tam_buffer, terminan_prod, terminan_cons); //Reserva de memoria para el vector
        //Reserva de memoria para Procudtor.
        Productor[] prod = new Productor[num_hebras_prod];
        //Reserva de memoria para Consumidor.
        Consumidor[] cons = new Consumidor[num_hebras_cons];
        
        /***********************
        * Inicio de las hebras *
        ************************/
        for ( int i =0 ; i < num_hebras_prod; i++ ){
            prod[i] = new Productor(" ", b);
            prod[i].thr.start();
        }
        for ( int i =0 ; i < num_hebras_cons; i++ ){
            cons[i] = new Consumidor(" ",b);
            cons[i].thr.start();
        }
        
        /***********************************
        * Inicio de los join de las hebras *
        ************************************/
        for( int i = 0 ; i < num_hebras_prod; i++ ){
            prod[i].thr.join();
        }
        for( int i = 0 ; i < num_hebras_cons; i++ ){
            cons[i].thr.join();
        }
    }
}

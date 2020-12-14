import com.labdistibuido.conexion.Cliente;
import com.labdistibuido.escenario.Ventana;

import java.io.IOException;
import java.util.Scanner;

public class TEST_CLIENTE1 {
    public static void main(String[] args) {
        Cliente C;

        try {

            Scanner SC = new Scanner(System.in);
            System.out.println("Presionar una tecla para conectarse");
            SC.next();
            C = new Cliente();
            C.UnirsePartida();
            Ventana V =new Ventana(C);
            V.inicia();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}

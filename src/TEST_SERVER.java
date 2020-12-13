import com.labdistibuido.conexion.Conexion;
import com.labdistibuido.conexion.Server;

import java.io.IOException;
import java.util.Scanner;

public class TEST_SERVER {
    public static void main(String[] args) {
        Server C;
        try {
            C = new Server();
            C.Esperar();
            Scanner SC = new Scanner(System.in);
            System.out.println("Presionar una tecla para iniciar");
            SC.next();
            C.Empezar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
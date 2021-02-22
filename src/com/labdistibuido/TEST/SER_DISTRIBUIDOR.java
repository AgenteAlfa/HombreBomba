package com.labdistibuido.TEST;

import com.labdistibuido.Conexion.Nodo;
import com.labdistibuido.ConexionAnterior.Cliente;
import com.labdistibuido.Escenario.Ventana.Ventana;

import java.io.IOException;
import java.util.Scanner;

public class SER_DISTRIBUIDOR {

    public static void main(String[] args) {



        try
        {

            Nodo D = new Nodo(5000);
            D.Empezar();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}

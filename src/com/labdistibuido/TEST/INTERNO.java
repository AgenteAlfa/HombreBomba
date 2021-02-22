package com.labdistibuido.TEST;

import com.labdistibuido.Conexion.Nodo;
import com.labdistibuido.ConexionAnterior.Cliente;
import com.labdistibuido.Escenario.Ventana.Ventana;

import java.io.IOException;
import java.util.Scanner;

public class INTERNO {

    public static void main(String[] args) {
        Nodo[] Nodos = new Nodo[3];
        try {
            Nodos[0] = new Nodo(5000);
            Nodos[1] = new Nodo();
            Nodos[2] = new Nodo();
        } catch (IOException e) {

            e.printStackTrace();
        }

        try
        {
            for (Nodo N:Nodos)
            {
                N.Empezar();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}

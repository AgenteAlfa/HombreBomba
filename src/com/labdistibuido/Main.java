package com.labdistibuido;

import com.labdistibuido.conexion.Server;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Server S;
        //Ventana V =new Ventana();
        try {
            S = new Server();

            S.Esperar();
            Scanner SC = new Scanner(System.in);
            System.out.println("Presionar una tecla para iniciar");
            SC.next();
            S.Empezar();
            //V.inicia();
        } catch (IOException e) {
            e.printStackTrace();
        }



/*
        File A,B;
        Conexion C;

        A = new File("src\\data\\Movimiento");
        B = new File("src\\data\\Objetos");
        //TODO : Cargar Mapa
        Escenario.Inicializar(10,10,A,B);
        Escenario.getMov().Imprimir();
        Escenario.getObj().Imprimir();
        */

        //TODO : Preparar conexion - Interfaz?
        /*
        try {
            C = new Conexion(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error de configuracion - Conexion");
            C = new Conexion();
            return;
        }
        */
        //TODO : Iniciar Mapa - Dibujado
            /*
            * Aca debe abrir una interfaz grafica con botones de que digan para crear una sala
            * En esa sala debe de esperar a los 5 jugadores, el creador de la sala debe tener el boton
            * inicar partida para empezar a jugar, Esto invocando los metodos Conexion
            */
        //TODO : Iniciando el juego
/*
            Jugador J = C.Iniciar();*/
            


    }
}
package com.labdistibuido.escenario;

import java.io.File;

public class Escenario {
    private static Mapa_Mov Mov;
    private static Mapa_Obj Obj;
    public static int X;
    public static int Y;

    public static void Inicializar(int x ,int y,File M, File O)
    {
        Mov = new Mapa_Mov(x,y,M);
        Obj = new Mapa_Obj(x,y,O);
        X = x;
        Y = y;
    }
    public static void Inicializar(int x ,int y)
    {
        X = x;
        Y = y;
        Mov = new Mapa_Mov(x,y);
        Obj = new Mapa_Obj(x,y);
    }

    public static Mapa_Mov getMov() {
        return Mov;
    }

    public static Mapa_Obj getObj() {
        return Obj;
    }

}

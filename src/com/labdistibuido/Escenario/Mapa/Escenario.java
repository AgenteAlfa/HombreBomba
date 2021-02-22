package com.labdistibuido.Escenario.Mapa;

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

    public static void RevisarBombas()
    {

        for (int i = 0; i < Escenario.X; i++) {
            for (int j = 0; j < Escenario.Y; j++) {
                int c = Escenario.getObj().getPos(i,j);
                if (c < 0 && c > (Sim_Obj.EXPLOSION + 1))
                    Escenario.getObj().setPos(i,j,c - 1);
                else if (c == Sim_Obj.EXPLOSION)
                {
                    Escenario.getObj().setPos(i,j,Sim_Obj.VACIO);
                }
            }
        }
        int[] PosExp;
        while ( (PosExp = Escenario.getObj().Buscar(Sim_Obj.EXPLOSION + 1)) != null )
        {
            ExplotarBomba(PosExp[0], PosExp[1] );
        }
    }
    private static void ExplotarBomba(int x ,  int y)
    {

        //EQ
        Explotar(x,y,true);
        //Der
        Explotar(x + 1,y,false);
        //Izq
        Explotar(x - 1,y,false);
        //Arr
        Explotar(x,y + 1,false);
        //Aba
        Explotar(x,y - 1,false);
    }
    private static void Explotar(int x ,  int y, boolean EQ)
    {
        if (Escenario.getMov().getPos(x,y) == Sim_Mov.PASABLE)
        {

            if (!EQ && Escenario.getObj().getPos(x,y) < 0 && Escenario.getObj().getPos(x,y) > Sim_Obj.EXPLOSION)
                ExplotarBomba(x,y);
            Escenario.getObj().setPos(x,y,Sim_Obj.EXPLOSION);
        }
        else if (Escenario.getMov().getPos(x,y) == Sim_Mov.ARBOL)
        {
            Escenario.getMov().setPos(x,y,Sim_Mov.PASABLE);
            Escenario.getObj().setPos(x,y,Sim_Obj.EXPLOSION);
        }
        else if (Escenario.getMov().getPos(x,y) == Sim_Mov.ROCA_BLANDA)
        {
            Escenario.getMov().setPos(x,y,Sim_Mov.PASABLE);
            Escenario.getObj().setPos(x,y,Sim_Obj.EXPLOSION);
        }
        else if (Escenario.getMov().getPos(x,y) == Sim_Mov.ROCA_DURA)
            Escenario.getMov().setPos(x,y,Sim_Mov.ROCA_BLANDA);
    }

}

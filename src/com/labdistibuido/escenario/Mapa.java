package com.labdistibuido.escenario;

import java.io.*;

public abstract class Mapa{

    private int X, Y;
    private int [][] Arr;
    public Mapa(int x, int y , File Origen) {
        X = x;
        Y = y;
        try {
            Arr = Cargar(Origen);
        } catch (IOException e) {
            e.printStackTrace();
            Arr = new int[X][Y];
        }
    }
    public Mapa(int x , int y)
    {
        X = x;
        Y = y;
        Arr = new int[X][Y];
    }

    public void Imprimir()
    {
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                System.out.print(Arr[i][j]);
            }
            System.out.println();
        }
        System.out.println("________________________");
    }

    public int[][] getArr() {
        return Arr;
    }

    public void setArr(int[][] arr) {
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                Arr[i][j] = arr[i][j];
            }
        }

    }

    public int getPos(int x , int y)
    {
        return Arr[x][y];
    }
    public void setPos(int x ,int y,int val)
    {
        Arr[x][y] = val;
    }
    public int[] Buscar(int E)
    {
        int[] pos = null;
        for (int i = 0; i < Arr.length; i++) {
            int[] K = Arr[i];
            for (int j = 0; j < K.length; j++) {
                int k = K[j];
                if (k == E)
                    pos = new int[]{i,j};
            }
        }
        return pos;
    }

    public boolean MoverObjetos(int[] A , int[] B)
    {
        if(Arr[B[0]][B[1]] == Sim_Obj.VACIO)
        {
            int temp = Arr[A[0]][A[1]];
            Arr[A[0]][A[1]] = Arr[B[0]][B[1]];
            Arr[B[0]][B[1]] = temp;

            System.out.println("Hubo movimiento");
            return true;
        }
        System.out.println("No hubo movimiento, hay error");
            return false;
    }


    public boolean Contiene(int E)
    {
        for (int[] K:Arr) {
            for (int k:K) {
                if (k == E) return true;
            }
        }
        return false;
    }
    /**
     * Solo usar si en el mapa de objetos
     * */
    public void Remover(int E)
    {
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                if (Arr[i][j] == E) Arr[i][j] = Sim_Obj.VACIO;
            }
        }
    }

    private int[][] Cargar(File F) throws IOException {
        int [][] M = new int[X][Y];
        FileReader FR = new FileReader(F);
        int count = 0;
        while(FR.ready())
        {
            int out = FR.read();


            if (out >= '0' && out <= '9')
            {
                //System.out.println((out - '0') + " : " +count/X + " - " + count%X);
                M[count / X][count % X] = out - '0';
                count ++;
            }
        }

        return M;
    }
}

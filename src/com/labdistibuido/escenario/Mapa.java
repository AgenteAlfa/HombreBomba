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
        }
        finally {
            Arr = new int[X][Y];
        }
    }


    private int[][] Cargar(File F) throws IOException {
        int [][] Arr = new int[X][Y];
        FileReader FR = new FileReader(F);
        int x = 0 , y = 0;
        while(FR.ready())
        {
            int out = FR.read();
            if (out != '\n')
                Arr[x][y++] = out;
            else
            {
                x++;
                y = 0;
            }
        }

        return Arr;
    }
}

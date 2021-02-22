package com.labdistibuido.Escenario.Ventana;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

import com.labdistibuido.ConexionAnterior.Cliente;
import com.labdistibuido.Escenario.Data.DataImg;
import com.labdistibuido.Escenario.Data.DataSound;
import com.labdistibuido.Escenario.Mapa.Escenario;
import com.labdistibuido.Escenario.Mapa.Sim_Mov;
import com.labdistibuido.Escenario.Mapa.Sim_Obj;
import com.labdistibuido.Sonidos.EfectosDeSonido;
import com.labdistibuido.Sonidos.ReproducirSonido;

public class Ventana extends JFrame implements Runnable , KeyListener {
    private final Canvas canvas;
    private Thread thread;
    private boolean corriendo;
    private int numJug = 0;
    private boolean jugando = true;

    private BufferStrategy bs;
    private Graphics g;

    private final int FPS = 60;
    private double tiempoObjetivo = 1000000000/FPS;
    private double delta = 0;
    private double FPSPROMEDIO = FPS;
    private Cliente mCliente;
    DataSound mDS;

    //Numero de jugador que se va a enfocar
    private int Njugador=0;

    public Ventana(Cliente C,int njugador) {
        Njugador=njugador;
        setTitle("Hombre Bomba");
        DataImg.Inicializar();
        setSize(Constantes.ANCHO, Constantes.ALTO);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        mCliente = C;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(Constantes.ANCHO, Constantes.ALTO));
        canvas.setMaximumSize(new Dimension(Constantes.ANCHO, Constantes.ALTO));
        canvas.setMinimumSize(new Dimension(Constantes.ANCHO, Constantes.ALTO));

        add(canvas);
        setVisible(true);
    }

    public void init() {
        EfectosDeSonido.init();
    }

    public void actualizar() {
        // estadoJuego.actualizar();
        // teclado.actualizar();
    }

    private void dibujarVictoria()
    {

        /*------------ Dibujar Gráficos ------------*/
        g.setColor(Color.black);
        g.fillRect(0, 0, Constantes.ANCHO, Constantes.ALTO);


        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 35));
        int k = 0;

        k = Escenario.getObj().Buscar(1) != null ? 1 : 0;
        k = Escenario.getObj().Buscar(2) != null ? 2 : 0;
        k = Escenario.getObj().Buscar(3) != null ? 3 : 0;
        k = Escenario.getObj().Buscar(4) != null ? 4 : 0;
        k = Escenario.getObj().Buscar(5) != null ? 5 : 0;

        if(k != 0)

        {
            g.drawString("EL JUGADOR " + k  + "A GANADO!!!",2*Constantes.ALTO/5, Constantes.ANCHO*2/5);
            //System.out.println("EL JUGADOR " + k  + "A GANADO!!!");
        }
        else
        {

            g.drawString("TODOS MUERTOS!!!",2*Constantes.ALTO/5, Constantes.ANCHO*2/5);
            //System.out.println("TODOS MUERTOS!!!");
        }

    }

    private void dibujar() {
        bs = canvas.getBufferStrategy();

        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        /*------------ Dibujar Gráficos ------------*/
        g.setColor(Color.black);
        g.fillRect(0, 0, Constantes.ANCHO, Constantes.ALTO);


        //Dibujando seudo graficos de cuadricula
        //if(numJug >= 2)
        DibujarCuadricula();
        //else
            //dibujarVictoria();

        // estadoJuego.dibujar()
        

        //g.setColor(Color.WHITE);
        //g.setFont(new Font("TimesRoman", Font.PLAIN, 5));
        //g.drawString("FPS: " + FPSPROMEDIO, 0, 10);

        /*------------------------------------------*/
        g.dispose();
        bs.show();        
    }

    private void DibujarCuadricula()
    {
        int temp = numJug;
        numJug = 0;
        int Dx = (Constantes.ANCHO)/(Constantes.XV); //- 5;
        int Dy = (Constantes.ALTO)/(Constantes.YV); //- 5;
        //System.out.println("Dim : " + Dx + " - " + Dy);

        //while para encontrar la posición del jugador pj, se guardara en pji y pjj
        int pj=0;
        int pji=0,pjj=0,i=0,j=0;

        while (i<Constantes.X && Escenario.getObj().getPos(i, j) != Njugador) {
            while ( j<Constantes.Y && Escenario.getObj().getPos(i, j) != Njugador) {
                j++;
            }
            if(j>=Constantes.Y){
                j=0;
                i++;
            }
        }

        //Si la posición del jugador no está en el mapa, entonces se pone lejos
        if (i<=Constantes.X && j<=Constantes.Y) {
            pjj = j;
            pji = i;
        }else {
            pjj=Constantes.Y*-1;
            pji=Constantes.X*-1;
        }

        //XV, YV: casillas de la ventana, i y j: posición en la ventana, ii y jj: posición real en el mapa
        int ii,jj,Mov,Obj;

        for (i = 0 ; i  < Constantes.XV ; i++ )
            for (j = 0; j < Constantes.YV; j++ ) {
                g.setColor(Color.gray);
                g.fillRect(i*Dx,j*Dy,Dx - 1 , Dy - 1);

                //le damos a ii y jj sus posiciones reales en el mapa
                ii= pji + (i-(Constantes.XV/2));
                jj= pjj + (j-(Constantes.YV/2));

                //Si se sale del mapa, rellenaremos con vacío, sino obtendremos datos del mapa
                if (jj<0 || ii<0 || jj>=Constantes.Y || ii>=Constantes.X) {
                    Mov=1;//1 movimiento vacio
                    Obj=0;//0 objeto vacío
                } else {
                    Mov = Escenario.getMov().getPos(ii, jj);
                    Obj = Escenario.getObj().getPos(ii, jj);
                }

                g.drawImage(DataImg.Suelo,i*Dx,j*Dy,Dx,Dy,null);
                switch (Mov)
                {
                    case Sim_Mov.VACIO:
                        g.drawImage(DataImg.Vacio1,i*Dx,j*Dy,Dx,Dy,null);
                        break;
                    case Sim_Mov.ARBOL:
                        g.drawImage(DataImg.Arbol,i*Dx,j*Dy,Dx,Dy,null);
                        break;
                    case Sim_Mov.ROCA_DURA:
                        g.drawImage(DataImg.RocaD,i*Dx,j*Dy,Dx,Dy,null);
                        break;
                    case Sim_Mov.ROCA_BLANDA:
                        g.drawImage(DataImg.RocaB,i*Dx,j*Dy,Dx,Dy,null);
                        break;
                }
                switch (Obj)
                {
                    case Sim_Obj.VACIO:
                        //g.drawImage(DataImg.Suelo,i*Dx,j*Dy,Dx,Dy,null);
                        break;
                    case Sim_Obj.JUGADOR_1:
                        numJug++;
                        g.drawImage(DataImg.J1,i*Dx,j*Dy,Dx,Dy,null);
                        break;
                    case Sim_Obj.JUGADOR_2:
                        numJug++;
                        g.drawImage(DataImg.J2,i*Dx,j*Dy,Dx,Dy,null);
                        break;
                    case Sim_Obj.JUGADOR_3:
                        numJug++;
                        g.drawImage(DataImg.J3,i*Dx,j*Dy,Dx,Dy,null);
                        break;
                    case Sim_Obj.JUGADOR_4:
                        numJug++;
                        g.drawImage(DataImg.J4,i*Dx,j*Dy,Dx,Dy,null);
                        break;
                    case Sim_Obj.JUGADOR_5:
                        numJug++;
                        g.drawImage(DataImg.J5,i*Dx,j*Dy,Dx,Dy,null);
                        break;
                    case Sim_Obj.EXPLOSION:
                        mDS.Explosion.reproducir();
                        g.drawImage(DataImg.Explosion,i*Dx,j*Dy,Dx,Dy,null);
                        break;
                    case Sim_Obj.BOMBA:
                        g.drawImage(DataImg.Bomba,i*Dx,j*Dy,Dx,Dy,null);
                        Random R = new Random();
                        if(R.nextInt(100) < 50)
                            mDS.Bomba1.reproducir();
                        else
                            mDS.Bomba2.reproducir();
                        break;
                    default:
                        g.drawImage(DataImg.Bomba,i*Dx,j*Dy,Dx,Dy,null);
                        break;
                }


            }

        if (temp > numJug)
        {
            mDS.Dead.reproducir();
        }



    }


    @Override
    public void run() {
        long ahora = 0;
        long ultimoTiempo = System.nanoTime();
        long tiempo = 0;
        int cuadros = 0;        

        init();

        /*---- Ejemplo sonido (cargando) ----*/
        long tiempoTitulo = 0;

        mDS = new DataSound();
        ReproducirSonido title = new ReproducirSonido(EfectosDeSonido.title);
        /*------------------------*/

        while (corriendo) {
            ahora = System.nanoTime();
            delta += (ahora - ultimoTiempo)/tiempoObjetivo;
            tiempo += ahora - ultimoTiempo;
            ultimoTiempo = ahora;
            
            if (delta > 1) {
                actualizar();
                dibujar();
                delta--;
                cuadros++;
            }

            if (tiempo > 1000000000) {
                FPSPROMEDIO = cuadros;
                cuadros = 0;
                tiempo = 0;

                /*---- Ejemplo sonido (reproduciendo) ----*/
                tiempoTitulo++;
                if (tiempoTitulo == 2) {
                    //title.reproducir();
                    mDS.Inicio.reproducir();
                }
                /*----------------------------------------*/
            }        

        }

        parar();

    }

    public void inicia() {
        thread = new Thread(this);
        thread.start();
        corriendo = true;
    }

    private void parar() {
        try {
            thread.join();
            corriendo = false;            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("CODIGO : " + e.getKeyCode());
        try {
            mCliente.getBOS().write(e.getKeyCode());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

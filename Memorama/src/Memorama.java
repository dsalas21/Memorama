import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.util.Random;
public class Memorama {

	
	//Salas Coronado Diego Isaias
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Memorama window = new Memorama();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Memorama() {
		initialize();
	}

	

	private JButton[] botones;
	private ImageIcon[] imagenes;
	private int[] pares;
	private int seleccion1 = -1;
	private int seleccion2 = -1;
	private int paresEncontrados = 0;
	private Timer timer;
    private int seconds;
    private int segundos, minutos;
	
	
	private void initialize() {
		
		  botones = new JButton[8];
		  imagenes = new ImageIcon[4];
		  pares = new int[8];
		  int n = botones.length;
		  	
	
		frame = new JFrame();
		frame.setBackground(new Color(102, 204, 153));
		frame.setBounds(100, 100, 498, 429);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel tiempo = new JLabel("00:00");
		panel.add(tiempo);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton reinicio = new JButton("Reiniciar");
		reinicio.setBackground(new Color(255, 255, 204));
		panel_1.add(reinicio);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(2, 4, 0, 0));
		
			//cronometro
		 timer = new Timer(1000, new ActionListener() {
	            
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                seconds++;
	               minutos = seconds / 60;
	                segundos= seconds % 60;
	                tiempo.setText(String.format("%02d:%02d", minutos, segundos));
	            }
	        });
		
		   for (int i = 0; i < 8; i++) {
			   //creamoos los botones y le agregamos la validacion con action listener incluido
		        botones[i] = new JButton();
		       botones[i].setBackground(Color.decode("#8FE667"));
		        botones[i].addActionListener(new Validacion());
		        panel_2.add(botones[i]);
		        
		        pares[i] = i % 4;
		    }
		
		//cargamos las imagenes
		
		   	imagenes[0] = new ImageIcon("memorama1.png");
		    imagenes[1] = new ImageIcon("memorama2.png");
		    imagenes[2] = new ImageIcon("memorama3.png");
		    imagenes[3] = new ImageIcon("memorama4.png");
		   
			
			//acomodar de manera aleatoria los botones
			Random random = new Random();
			for (int i = n - 1; i > 0; i--) {
				int j = random.nextInt(i + 1);
				JButton temp = botones[i];
				botones[i] = botones[j];
				botones[j] = temp;
				
			}
		
			
			
			reinicio.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
											
					panel_2.removeAll();
					
					  for (int i = 0; i < 8; i++) {
						   
					        botones[i] = new JButton();
					        botones[i].setBackground(Color.decode("#8FE667"));
					        botones[i].addActionListener(new Validacion());
					        panel_2.add(botones[i]);
					        
					        pares[i] = i % 4;
					    }
					
					  
					  Random random = new Random();
						for (int i = n - 1; i > 0; i--) {
							int j = random.nextInt(i + 1);
							JButton temp = botones[i];
							botones[i] = botones[j];
							botones[j] = temp;
							
						}
						
						minutos=0;
						segundos=0;
						seconds=0;
						paresEncontrados=0;
						seleccion1=-1;
						seleccion2=-1;
					
					timer.start();
					panel_2.repaint();
					panel_2.revalidate();
				}
				
				
				
				
				
			});
			
	panel_2.repaint();
	panel_2.revalidate();
	
	
	
	
	
	
	

	}
	
	
	private class Validacion implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	        int seleccionActual = -1;
	        
	        for (int i = 0; i < botones.length; i++) {
	            if (e.getSource() == botones[i]) {
	                seleccionActual = i;
	                break;
	            }
	        }
	        
	        if (seleccionActual == seleccion1 || seleccionActual == seleccion2) {
	            return; // Ignorar si se selecciona la misma celda dos veces
	        }
	        
	        if (seleccion1 == -1) {
	            seleccion1 = seleccionActual;
	            botones[seleccion1].setIcon(imagenes[pares[seleccion1]]);
	        } else if (seleccion2 == -1) {
	            seleccion2 = seleccionActual;
	            botones[seleccion2].setIcon(imagenes[pares[seleccion2]]);
	            
	            if (pares[seleccion1] == pares[seleccion2]) {
	                // Si se encontró un par
	                paresEncontrados++;
	                if (paresEncontrados == 4) {
	                	timer.stop();
	                    JOptionPane.showMessageDialog(null, "¡Felicidades, has ganado!");
	                   
	                    
	                }
	                // Reiniciar selecciones
	                seleccion1 = -1;
	                seleccion2 = -1;
	            } else {
	                // Si no es un par, ocultar las imágenes después de un segundo
	                Timer timer = new Timer(1000, new ActionListener() {
	                    public void actionPerformed(ActionEvent e) {
	                        botones[seleccion1].setIcon(null);
	                        botones[seleccion2].setIcon(null);
	                        seleccion1 = -1;
	                        seleccion2 = -1;
	                    }
	                });
	                timer.setRepeats(false);
	                timer.start();
	            }
	        }
	    }
	}
	
	
	
}

package entrainement_cours;

import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;


public class MusicPlayer extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File file;
    private JButton playButton, pauseButton, restartButton, resumeButton,previousButton,nextButton;
    private Clip audioClip;
    private int count;
    private String playlist1;
    private String Statut;
    
    static JMenuBar Barre;
    static JMenu Menu;
    static JMenuItem Men1;
    static JMenuItem Men2;
    static JPanel controlPanel,btnPanel;
    static JTabbedPane tabbedPane;
    
    private JButton captureBtn, stopBtn;
    AudioFormat audioFormat;
    TargetDataLine cibledonneeligne; // objet pour la ligne de données cible
    
    //playlist dynamique //revoir l'idée de programmation 
    static ArrayList<String> playlist = new ArrayList<String>(); 
    static ArrayList<JMenuItem> playlistgraphique = new ArrayList<JMenuItem>(); 
    //static int indice;

    public MusicPlayer() {
    	this.count = 2;
    	//indice = 0;
        //playlist.add("C:\\Users\\Komi\\Downloads\\ar.txt");
    	//this.playlist1 = playlist.get(0);
    	this.playlist1 = "C:\\Users\\Komi\\eclipse-workspace\\Entrainement_TP\\src\\entrainement_cours\\playlist.txt.txt";
    	this.file =new File(lignespecifique(count, playlist1));//je prend ce fichier par defaut
    	this.Statut = "play";
    	// Création du menu Plalist
    	Barre =new JMenuBar();
        Menu =new JMenu("playlist(s)");
        Barre.add(Menu);
        Men2 = new JMenuItem("ajouter une nouvelle playlist");
        Men1 = new JMenuItem("enregistrement");
        Menu.add(Men1);
        Menu.add(Men2);
        this.setJMenuBar(Barre);

        // Créer les boutons et la barre de progression
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        restartButton = new JButton("Restart");
        resumeButton = new JButton("Resume");
        nextButton = new JButton("Next");
        previousButton = new JButton("Previous");
        captureBtn = new JButton("Capture");
        stopBtn = new JButton("Stop");
        

        // Ajouter des écouteurs d'événements aux boutons
        playButton.addActionListener(e -> play());
        pauseButton.addActionListener(e -> pause());
        restartButton.addActionListener(e -> restart());
        resumeButton.addActionListener(e -> resume());
        nextButton.addActionListener(e -> next());
        previousButton.addActionListener(e -> previous());
        Men1.addActionListener(e -> playlist1());//modifier plutard
        Men2.addActionListener(e -> selection());//tester
        captureBtn.addActionListener(e -> capture());
        stopBtn.addActionListener(e -> stop());

        // Créer le panneau de commande avec les boutons et la barre de progression
        controlPanel = new JPanel();
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        controlPanel.add(restartButton);
        controlPanel.add(resumeButton);
        controlPanel.add(nextButton);
        controlPanel.add(previousButton);
        
        btnPanel = new JPanel();
        btnPanel.add(captureBtn);
        btnPanel.add(stopBtn);
        
        // Créer les onglets d'affichage.
        tabbedPane = new JTabbedPane();
        
        // Ajouter les panneaux aux onglets
        tabbedPane.addTab("Lecteur de musique", controlPanel);
        tabbedPane.addTab("Magnétophone", btnPanel);

        // Ajouter le panneau de commande à la fenêtre
        getContentPane().add(tabbedPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 100);
        setVisible(true);
  
    }

    public void play() {
        try {
        
        	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);// Obtenir l'entrée audio
            audioClip = AudioSystem.getClip();
            audioClip.open(audioInputStream);
            audioClip.start();
            Statut = "play";
            /*
            playlistgraphique.add(new JMenuItem("essai"));
            Menu.add(playlistgraphique.get((playlistgraphique.size()) - 1));
            */
            
        } 
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // Gestion de l'événement de fin de clip
        audioClip.addLineListener(new LineListener() {
            public void update(LineEvent evt) {
                if (evt.getType() == LineEvent.Type.STOP  && !Statut.equals("pause")) {
                    next();
                }
            }
        });
        
        
    }
    
    public void next() {
        count = count + 1;
        if(count < nbreligne(playlist1) + 1) {
        	file =new File(lignespecifique(count, playlist1));
        }
        else  {
        	count = 2;
        	file =new File(lignespecifique(count, playlist1));
        }
        if (audioClip != null) {
        	Statut = "pause";
        	audioClip.stop();
        	audioClip.close();
        }
        play();
    }
    
    public void previous() {
        count = count - 1;
        audioClip.stop();
        if(1 < count) {
        	file =new File(lignespecifique(count, playlist1));
        }
        else {
        	count = 2;
        	file =new File(lignespecifique(count, playlist1));
        }
        if (audioClip != null) {
        	Statut = "pause";
            audioClip.stop();
            audioClip.close();
        }
        play();
    }

    public void pause() {
        if (audioClip != null && audioClip.isRunning()) {
        	Statut = "pause";
            audioClip.stop();
            System.out.println(audioClip.getFramePosition()); 
        }
    }

    public void restart() {
        if (audioClip != null) {
        	Statut = "pause";
            audioClip.stop();
            audioClip.setFramePosition(0);
            Statut = "play";
            audioClip.start();
        }
    }

    public void resume() {
        if (audioClip != null && !audioClip.isRunning()) {
            audioClip.start();
            Statut = "play";
        }
    }
    
    
    public void playlist1() {
    	pause();
    	playlist1 = "C:\\Users\\Komi\\Downloads\\ar.txt";//modifier ce chemin plutard
    	play();
    }
    
    public void selection() {//Revenir sur cette methode
    	JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(MusicPlayer.this);
        if (result == JFileChooser.APPROVE_OPTION) {
        	System.out.println("ok");
        	
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("ok");
            int indice = playlistgraphique.size();
            playlistgraphique.add(new JMenuItem("playlist" + Integer.toString(indice)));
            Menu.add(playlistgraphique.get((playlistgraphique.size()) - 1));
            System.out.println("ok");
            (playlistgraphique.get((playlistgraphique.size()) - 1)).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String MenuItemName = ((JMenuItem) e.getSource()).getText();
                    System.out.println("Bouton cliqué : " + MenuItemName);
                    int indice_chemin = Character.getNumericValue(MenuItemName.charAt(MenuItemName.length() - 1));
                    pause();
                	playlist1 = playlist.get(indice_chemin);//modifier ce chemin plutard
                	play();
                }
            });
            
            playlist.add(selectedFile.getAbsolutePath());
            /*
            playlist1 = selectedFile.getAbsolutePath();
            pause();
            play();
            */
        }
    }
    
    
    static public int nbreligne(String playlist) {
    	int i = 1;
    	try
        {
    		String s = new String();
    		FileReader in = new FileReader(new File(playlist));
    		BufferedReader bin = new BufferedReader(in);
    		s=bin.readLine();
    		while (s!=null) {
            i = i + 1;
    		s=bin.readLine(); }
    		bin.close();  
    		//System.out.println("Fin de lecture du fichier/playlist");
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    	return(i);
    }
    
    static public String lignespecifique(int j, String playlist) {
		int i = 1;
	    String line = new String();
	    
	    try {
	      //lire le fichier file.txt
	      FileReader file = new FileReader(playlist);
	      BufferedReader buffer = new BufferedReader(file);
	    
	      // parcourir le fichier
	      while (i != j) {
		    	// Si le numéro de la ligne = j récupérer la ligne
		    	  i = i +1;
			      if (i == j) {
			    	  line = buffer.readLine();//correspond à la musique à lire d'indice i dans ma playlist
			      }
			      else { 
			    	  buffer.readLine();
			      }
		      }
	    } 
	    catch (Exception ex) {
            ex.printStackTrace();
	    }
	    return(line);
    }
    
    ///////

    private void capture() {
    	System.out.println("Bouton Capture");
        captureBtn.setEnabled(false);
        stopBtn.setEnabled(true);
        captureAudio();
    }
    private void stop() {
    	System.out.println("Bouton Stop");
        captureBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        cibledonneeligne.stop(); // arrêter la capture de la ligne de données cible
        cibledonneeligne.close(); // fermer la ligne de données cible
    }

    private void captureAudio() {
        try {
            audioFormat = getAudioFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            cibledonneeligne = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            new capturerligne().start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    // Obtenir le format audio pour l'enregistrement
    private AudioFormat getAudioFormat() {
        return new AudioFormat(8000.0F, 16, 1, true, false);
    }
    
    public static void append(String filename, String text) {
        BufferedWriter bufWriter = null;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filename, true);
            bufWriter = new BufferedWriter(fileWriter);
            //Insérer un saut de ligne
            bufWriter.newLine();
            bufWriter.write(text);
            bufWriter.close();
        } catch (IOException ex) {
            
        } finally {
            try {
                bufWriter.close();
                fileWriter.close();
            } catch (IOException ex) {
                
            }
        }
    } 
    

    // Thread pour capturer les données audio
    class capturerligne extends Thread {
        public void run() {
            try {
            	
        	    SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        	    Date date = new Date();
        	    String am = "enregistrement_du_" +  s.format(date) + ".wav";
            	
        	    append("C:\\Users\\Komi\\Downloads\\ar.txt", am);//fichier dans lequel mes enregistrements seront sauvegardés
            	
                cibledonneeligne.open(audioFormat);
                cibledonneeligne.start(); // démarrer la capture de la ligne de données cible
                AudioSystem.write(new AudioInputStream(cibledonneeligne),
                        AudioFileFormat.Type.WAVE,
                        new File(am)); // enregistrer les données dans un fichier WAV
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MusicPlayer musicPlayer = new MusicPlayer();
    }
}
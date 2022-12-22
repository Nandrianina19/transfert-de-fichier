package client;

import java.net.Socket;
import java.io.*;
import java.nio.file.*;
// charger les options
import static java.nio.file.StandardOpenOption.*;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.Color;

public class Client {

    public void sendSocket() throws IOException {

        // ---------------socket-------------------

        Socket socket = new Socket("localhost", 7771);
        System.out.println("\nConnected!\n");

        System.out.println("Envoie de fichier sur serveur1 en cours ...\n");

        OutputStream output = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(output);

        Path chemin = Paths.get("attachement.txt");

        InputStream input = null;
        input = Files.newInputStream(chemin);

        String message = null;
        String contenu = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        do {
            message = reader.readLine();
            if (message != null) {
                contenu += message;
                contenu += "\n";
            }
        } while (message != null);

        input.close();

        dataOutputStream.writeUTF(contenu);
        // envoie de donnée
        dataOutputStream.flush();

        // fermeture de io
        dataOutputStream.close();

        System.out.println("____Fermeture du socket____\n");
        socket.close();
    }

    public static void main(String[] args) throws IOException {

        Client client = new Client();

        // creation de fenetre
        JFrame frame = new JFrame("Envoie fichier via socket");
        // terminer si on Click sur fermer
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(null);

        // create the button
        JButton button = new JButton("Envoyer");
        button.setBounds(90, 100, 100, 40);
        button.setBackground(new Color(38, 213, 108));

        // add an action listener to the button
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // show a message when the button is clicked

                try {

                    client.sendSocket();

                    JOptionPane.showMessageDialog(frame, "le fichier a été envoyé!");

                } catch (IOException err) {

                    JOptionPane.showMessageDialog(frame, "un erreur!!,veuillez lancer le serveur!");

                    err.printStackTrace();
                }

            }
        });

        frame.add(button);
        frame.setSize(300, 300);
        frame.setVisible(true);

    }
}

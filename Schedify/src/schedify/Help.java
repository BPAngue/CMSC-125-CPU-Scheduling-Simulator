package schedify;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;


public class Help extends Panels{
    
    public JPanel header, footer, instructionsPanel;
    public JLabel titleLabel, logoLabel;
    public JButton backButton;
    public BufferedImage bg, img, logo;
    public Icon logoIcon;
    public Font archivoblack, archivonarrow, font;
    public String instructions;
    public JTextPane instructionsText;
    

    public Help() {
       
    }
    
    @Override
    public void showUIComponents(){
        
        archivoblack = importFont("archivoblack");
        archivonarrow = importFont("archivonarrow");
        
        setLayout(new BorderLayout());
        
        header = new JPanel(new FlowLayout(FlowLayout.LEADING, 0,0));
        header.setPreferredSize(new Dimension(950, 150));
        header.setBackground(new Color(0,0,0,0));
        
        titleLabel = new JLabel("Help");
        titleLabel.setFont(archivoblack.deriveFont(70f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 130, 0, 135));
        
        logo = getImg("/img/logo_small.png");
        logoIcon = new ImageIcon(logo);
        
        logoLabel = new JLabel();
        logoLabel.setIcon(logoIcon);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(55, 180, 0, 0));
        
        header.add(titleLabel);
        header.add(logoLabel);
        
        footer = new JPanel(new BorderLayout());
        footer.setPreferredSize(new Dimension(950, 90));
        footer.setBackground(new Color(0,0,0,0));
        footer.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 80));
        
        backButton = createButton("BACK");
        
        footer.add(backButton, BorderLayout.EAST);
        
        instructions = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. "
                + "Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. "
                + "Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. "
                + "Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a,"
                + " venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. "
                + "Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, "
                + "eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. "
                + "Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet."
                + " Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. "
                + "Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, "
                + "sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. "
                + "Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. "
                + "Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. "
                + "Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc,";
        
        instructionsText = new JTextPane();
        instructionsText.setText(instructions);
        instructionsText.setPreferredSize(new Dimension(620, 500));
        instructionsText.setForeground(Color.WHITE);
        instructionsText.setBackground(new Color(0,0,0,0));
        instructionsText.setFont(archivonarrow.deriveFont(19f));
        instructionsText.setEditable(false);
        instructionsText.setOpaque(false);
        instructionsText.setFocusable(false);
        
        instructionsPanel = new JPanel(new BorderLayout());
        instructionsPanel.add(instructionsText, BorderLayout.WEST);
        instructionsPanel.setBackground(new Color(0,0,0,0));
        instructionsPanel.setBorder(BorderFactory.createEmptyBorder(30,130,0,0));
        
                
        add(header, BorderLayout.NORTH);
        add(instructionsPanel);
        add(footer, BorderLayout.SOUTH);
    }
   
}

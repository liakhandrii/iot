
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.filechooser.FileSystemView;

public class Menu extends JFrame {
	private final JTextField textField_0;
	private final JTextField txtDefault;
	private final JTextField textField_2;
	private final JTextField textField_3;
	private final JTextField textField_4;
	private final JComboBox comboBox;
	private JTextField textField_5;
	
	public Menu() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Home manager");
		setSize(276, 248);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblNameLabel = new JLabel("Name:");
		lblNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNameLabel.setBounds(10, 11, 67, 14);
		getContentPane().add(lblNameLabel);
		
		JLabel lblNewLabel = new JLabel("SSID:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 61, 67, 14);
		getContentPane().add(lblNewLabel);
		
		final JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(10, 86, 67, 14);
		getContentPane().add(lblPassword);
		
		JLabel label = new JLabel("Password:");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(10, 136, 67, 14);
		getContentPane().add(label);
		
		final JLabel lblHomeName = new JLabel("Home name:");
		lblHomeName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHomeName.setBounds(0, 111, 77, 14);
		getContentPane().add(lblHomeName);
		
		final JLabel lblServer = new JLabel("Server:");
		lblServer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblServer.setBounds(10, 36, 67, 14);
		getContentPane().add(lblServer);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.print(textField_0.getText() +" "+ textField_1.getText() + " " + textField_2.getText() + " " + textField_3.getText() + " " + textField_4.getText());
				String[] names = {"name", "server", "ssid", "passwifi", "homename", "passhome"};
				String[] values = {textField_0.getText(), txtDefault.getText(), textField_2.getText(), textField_3.getText(), textField_4.getText(),  textField_5.getText()};
				for (int i =0; i < values.length; i++) {
					if (values[i].length()==0) {
						JOptionPane.showMessageDialog(null, "Fill all fields");
						return;
					}
				}
				if (InitialSettings.writeJson(InitialSettings.makeJson(names, values),comboBox.getSelectedItem().toString())) {
					JOptionPane.showMessageDialog(null, "Settings was saved");
				} else {
					JOptionPane.showMessageDialog(null, "An error occured");
				}
				
			}
		});
		btnOk.setBounds(87, 189, 89, 23);
		getContentPane().add(btnOk);
		
		textField_0 = new JTextField();
		textField_0.setBounds(87, 8, 166, 20);
		getContentPane().add(textField_0);
		textField_0.setColumns(10);
		
		txtDefault = new JTextField();
		txtDefault.setText("default");
		txtDefault.setColumns(10);
		txtDefault.setBounds(87, 33, 166, 20);
		getContentPane().add(txtDefault);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(87, 58, 166, 20);
		getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(87, 83, 166, 20);
		getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(87, 108, 166, 20);
		getContentPane().add(textField_4);
		
		JLabel lblDevice = new JLabel("Device:");
		lblDevice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDevice.setBounds(10, 161, 67, 14);
		getContentPane().add(lblDevice);
		
		comboBox = new JComboBox();
		comboBox.setBounds(87, 158, 166, 20);
		getContentPane().add(comboBox);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(87, 133, 166, 20);
		getContentPane().add(textField_5);
		updateCombobox();	
		
	}

	private void updateCombobox () {
		comboBox.removeAllItems();
		for (String s: USBDetect())
			comboBox.addItem(s);
	}
	
	public ArrayList<String> USBDetect()
    {
        String driveLetter = "";
        FileSystemView fsv = FileSystemView.getFileSystemView();
        ArrayList<String> arr = new ArrayList<>();

        File[] f = File.listRoots();
        for (int i = 0; i < f.length; i++)
        {
            String drive = f[i].getPath();
            String displayName = fsv.getSystemDisplayName(f[i]);
            String type = fsv.getSystemTypeDescription(f[i]);
            boolean isDrive = fsv.isDrive(f[i]);
            boolean isFloppy = fsv.isFloppyDrive(f[i]);
            boolean canRead = f[i].canRead();
            boolean canWrite = f[i].canWrite();
            
            //if (canRead && canWrite && !isFloppy && isDrive && (type.toLowerCase().contains("removable") || type.toLowerCase().contains("rimovibile")))
            {
                System.out.println("Detected PEN Drive: " + drive + " - "+ displayName); 
                driveLetter = drive;
                arr.add(driveLetter);
            }
        }

        if (driveLetter.equals(""))
        {
            System.out.println("Not found!");
        } 
        else 
        {
            System.out.println(driveLetter);
        }
        

        System.out.println(driveLetter);
        return arr;
    }


	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Menu menu = new Menu();
				menu.setVisible(true);
			}
		});
	}
}

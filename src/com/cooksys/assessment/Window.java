/**
 * Andrew Um
 * Cook Systems FastTrack’D Assessment Program
 * PC Parts Builder
 */
package com.cooksys.assessment;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * {@value} list The list to hold strings. 
 * {@value} left The left panel model list to hold parts.
 * {@value} listRight The right panel that holds added parts.
 * {@value} part The computer parts constructor for XML.
 **/
public class Window {

	private JFrame frame;
	protected Parts part = new Parts();
	protected JList<String> listRight;
	protected DefaultListModel<String> left;
	final DefaultListModel list = new DefaultListModel();

	/**
	 * Launch the application. The main method is the entry point to a Java
	 * application. For this assessment, you shouldn't have to add anything to
	 * this.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application. This is the constructor for this Window class.
	 * All of the code here will be executed as soon as a Window object is made.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. This is where Window Builder will generate its code.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initialize() {
		frame = new JFrame("PC Parts Builder");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		left = new DefaultListModel<String>();

		// Creates new Menu Bar
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		// Menu Contents
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

		/**
		 * Action listener for the Load menu.
		 * @param list.clear() Method execution to clear the list, which allows right panel to be empty.
		 * @param load() Method execution of load to read and write XML to object.
		 **/
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					list.clear();
					load();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu.add(mntmLoad);

		/**
		 * Action listener for the Save menu.
		 * @param save() Method execution of save to write object to XML.
		 **/
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					save();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu.add(mntmSave);

		/**
		 * Action listener for the Exit menu item when selected.
		 * @param System.exit(0) Exit program.
		 **/
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmExit);

		/**
		 * Left panel
		 * @param addParts() Method execution to fill left panel with parts.
		 **/
		final JList listLeft = new JList();
		listLeft.setBounds(0, 0, 170, 256);
		addParts();
		listLeft.setModel(left);
		frame.getContentPane().add(listLeft);

		// Right panel
		listRight = new JList();
		listRight.setBounds(275, 0, 189, 256);
		frame.getContentPane().add(listRight);

		// Add/Remove Buttons
		JButton addBtn = new JButton("->");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.addElement(listLeft.getSelectedValue());
				listRight.setModel(list);
			}
		});
		addBtn.setBounds(192, 80, 59, 45);
		frame.getContentPane().add(addBtn);

		JButton rmvBtn = new JButton("<-");
		rmvBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.removeElement(listRight.getSelectedValue());
				listRight.setModel(list);
			}
		});
		rmvBtn.setBounds(192, 130, 59, 45);
		frame.getContentPane().add(rmvBtn);
	}

	// Method to add all parts to left panel.
	private void addParts() {
		String[] parts = { "Case", "Motherboard", "CPU", "GPU", "PSU", "RAM",
				"HDD" };
		for (int i = 0; i < parts.length; i++) {
			left.addElement(parts[i]);
		}
	}

	// Load an existing XML file into the right panel.
	@SuppressWarnings("unchecked")
	public void load() throws Exception {
		try {
			File file = new File("PCPartsList.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Parts.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Parts loadParts = (Parts) jaxbUnmarshaller.unmarshal(file);

			for (int i = 0; i < loadParts.name.size(); i++) {
				list.addElement(loadParts.name.get(i));
			}
			listRight.setModel(list);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save added parts into an XML file.
	 * @param part.newList() Method execution to return parts list.
	 **/
	public void save() throws Exception {
		List<String> list1 = part.getName();
		for (int i = 0; i < list.getSize(); i++) {
			list1.add((String) list.getElementAt(i));
		}
		try {
			File file = new File("PCPartsList.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Parts.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(part, file);
			part.newList();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}

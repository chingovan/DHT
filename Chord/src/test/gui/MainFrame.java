package test.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import vn.vnu.hus.mim.chord.Chord;
import vn.vnu.hus.mim.chord.ChordData;
import vn.vnu.hus.mim.chord.ChordException;
import vn.vnu.hus.mim.chord.ChordNode;
import vn.vnu.hus.mim.chord.Hash;

public class MainFrame extends JFrame {
	public static final String HASH_FUNCTION = "SHA-1";
	public static final int KEY_LENGTH = 160;

	private static final Locale LOCALE = new Locale("vi", "VN");
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle("test.gui.localization.messages", LOCALE); //$NON-NLS-1$
	private JTextField tbxNumberOfNode;

	private int numberOfNodes;
	private Chord chord;
	private JTextField tbxStoreData;
	private JTextField tbxLookupData;
	private JButton btnGenerateNetwork;

	public MainFrame() {

		setTitle(BUNDLE.getString("Application.Title")); //$NON-NLS-1$
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 138, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, BUNDLE.getString("Network.Information"), TitledBorder.LEFT, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 91, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel = new JLabel(BUNDLE.getString("Number.Of.Node"));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		tbxNumberOfNode = new JTextField();
		GridBagConstraints gbc_tbxNumberOfNode = new GridBagConstraints();
		gbc_tbxNumberOfNode.insets = new Insets(0, 0, 5, 0);
		gbc_tbxNumberOfNode.fill = GridBagConstraints.HORIZONTAL;
		gbc_tbxNumberOfNode.gridx = 1;
		gbc_tbxNumberOfNode.gridy = 0;
		panel.add(tbxNumberOfNode, gbc_tbxNumberOfNode);
		tbxNumberOfNode.setText("1000");
		tbxNumberOfNode.setColumns(10);
		
		btnGenerateNetwork = new JButton(BUNDLE.getString("Generate.Chord")); //$NON-NLS-1$
		btnGenerateNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tbxNumberOfNode != null && tbxNumberOfNode.getText() != null && !tbxNumberOfNode.getText().isEmpty()) {
					try {
						numberOfNodes = Integer.parseInt(tbxNumberOfNode.getText());
						generateNetwork();
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(MainFrame.this, BUNDLE.getString("Message.Error.NumberFormat"));
					}
				}
			}
		});
		GridBagConstraints gbc_btnGenerateNetwork = new GridBagConstraints();
		gbc_btnGenerateNetwork.anchor = GridBagConstraints.EAST;
		gbc_btnGenerateNetwork.gridx = 1;
		gbc_btnGenerateNetwork.gridy = 1;
		panel.add(btnGenerateNetwork, gbc_btnGenerateNetwork);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, BUNDLE.getString( "Store.Lookup.Title"), TitledBorder.LEFT, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblNewLabel_1 = new JLabel(BUNDLE.getString("Store.Lookup.Store.Title")); //$NON-NLS-1$
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);

		tbxStoreData = new JTextField();
		tbxStoreData.setText(""); //$NON-NLS-1$
		GridBagConstraints gbc_tbxStoreData = new GridBagConstraints();
		gbc_tbxStoreData.insets = new Insets(0, 0, 5, 5);
		gbc_tbxStoreData.fill = GridBagConstraints.HORIZONTAL;
		gbc_tbxStoreData.gridx = 1;
		gbc_tbxStoreData.gridy = 0;
		panel_1.add(tbxStoreData, gbc_tbxStoreData);
		tbxStoreData.setColumns(10);

		JButton btnStore = new JButton(BUNDLE.getString("Store.Lookup.Store.Button")); //$NON-NLS-1$
		btnStore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = tbxStoreData.getText();
				if (data == null || data.isEmpty()) {
					JOptionPane.showMessageDialog(MainFrame.this, BUNDLE.getString("Store.Lookup.Data.Store.Not.Empty"));
				} else {
					storeData(data);
				}
			}
		});
		GridBagConstraints gbc_btnStore = new GridBagConstraints();
		gbc_btnStore.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStore.insets = new Insets(0, 0, 5, 0);
		gbc_btnStore.gridx = 2;
		gbc_btnStore.gridy = 0;
		panel_1.add(btnStore, gbc_btnStore);

		JLabel lblNewLabel_2 = new JLabel(BUNDLE.getString("Store.Lookup.Lookup.Title")); //$NON-NLS-1$
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);

		tbxLookupData = new JTextField();
		tbxLookupData.setText(""); //$NON-NLS-1$
		GridBagConstraints gbc_tbxLookupData = new GridBagConstraints();
		gbc_tbxLookupData.insets = new Insets(0, 0, 0, 5);
		gbc_tbxLookupData.fill = GridBagConstraints.HORIZONTAL;
		gbc_tbxLookupData.gridx = 1;
		gbc_tbxLookupData.gridy = 1;
		panel_1.add(tbxLookupData, gbc_tbxLookupData);
		tbxLookupData.setColumns(10);

		JButton btnLookup = new JButton(BUNDLE.getString("Store.Lookup.Lookup.Button")); //$NON-NLS-1$
		btnLookup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = tbxLookupData.getText();
				if (data == null || data.isEmpty()) {
					JOptionPane.showMessageDialog(MainFrame.this, BUNDLE.getString("Store.Lookup.Data.Lookup.Not.Empty"));
				} else {
					lookupData(data);
				}
			}
		});
		GridBagConstraints gbc_btnLookup = new GridBagConstraints();
		gbc_btnLookup.gridx = 2;
		gbc_btnLookup.gridy = 1;
		panel_1.add(btnLookup, gbc_btnLookup);
	}

	private void generateNetwork() {
		String host = "";
		try {
			host = InetAddress.getLocalHost().getHostAddress();
			int port = 9000;

			Hash.setFunction(HASH_FUNCTION);
			Hash.setKeyLength(KEY_LENGTH);

			chord = new Chord();
			for (int i = 0; i < numberOfNodes; i++) {
				URL url = new URL("http", host, port + i, "");
				try {
					chord.createNode(url.toString());
				} catch (ChordException e) {
					e.printStackTrace();
					System.exit(0);
				}
				Scanner s = new Scanner(System.in);
				s.next();
			}

			// for (int i = 0; i < numberOfNodes; i++) {
			// ChordNode node = chord.getSortedNode(i);
			// }

			for (int i = 1; i < numberOfNodes; i++) {
				ChordNode node = chord.getNode(i);
				node.join(chord.getNode(0));
				ChordNode preceding = node.getSuccessor().getPredecessor();
				node.stabilize();
				if (preceding == null) {
					node.getSuccessor().stabilize();
				} else {
					preceding.stabilize();
				}
			}

			for (int i = 0; i < numberOfNodes; i++) {
				ChordNode node = chord.getNode(i);
				node.fixFingers();
			}

			JOptionPane.showMessageDialog(MainFrame.this, BUNDLE.getString("Network.Established"));
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

	}

	private void storeData(String data) {
		if (data != null && !data.isEmpty()) {
			ChordData chordData = new ChordData(data);
			if (chord == null) {
				JOptionPane.showMessageDialog(this, BUNDLE.getString("Network.Not.Created.Yet"));
			} else {
				ChordNode owner = chord.getNode(0).storeData(chordData);
				if (owner != null) {
					JOptionPane.showMessageDialog(this, String.format(BUNDLE.getString("Store.Lookup.Owner.Message"), owner.getNodeId()));
				}
			}
		}
	}

	private void lookupData(String data) {
		if (data != null && !data.isEmpty()) {
			ChordData chordData = new ChordData(data);
			if (chord == null) {
				JOptionPane.showMessageDialog(this, BUNDLE.getString("Network.Not.Created.Yet"));
			} else {
				ChordNode owner = chord.getNode(0).lookupData(chordData);
				if (owner != null) {
					JOptionPane.showMessageDialog(this, String.format(BUNDLE.getString("Store.Lookup.Owner.Message"), owner.getNodeId()));
				} else {
					JOptionPane.showMessageDialog(this, BUNDLE.getString("Store.Lookup.Data.Not.Store"));
				}
			}
		}
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		//frame.setTitle("Chord Algorithms GUI Test");
		//frame.setSize(300, 400);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - 300) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 400) / 2);
		frame.setVisible(true);
	}
}

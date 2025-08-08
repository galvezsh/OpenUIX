package gui;

import kernel.Configuration;
import strings.StaticStrings;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MainWindow extends JFrame {
	
	//////////////////////////////////////////
	// ATTRIBUTES ////////////////////////////
	//////////////////////////////////////////

	private final WindowsManager windowsManager;

	private JPanel mainPane;
	private JPanel demoPane;
	private JPanel testingPane;

	private JLabel demoLabel;
	private JButton infoButton;
	private JButton warningButton;
	private JButton errorButton;
	private JButton confimButton;
	private JButton openButton;
	private JButton saveButton;
	private JButton forceInfoRecordButton;
	private JButton forceWarningRecordButton;
	private JButton forceErrorRecordButton;

	//////////////////////////////////////////
	// CONSTRUCTOR ///////////////////////////
	//////////////////////////////////////////
	
	public MainWindow(WindowsManager windowsManager) {
		this.windowsManager = windowsManager;

		// Window Elements
		JMenuBar menuBar = new JMenuBar();
		JMenu menuRecords = new JMenu( Configuration.getString("jmenuRecords") );
		JMenu menuExtras = new JMenu( Configuration.getString("jmenuExtras") );
		JMenuItem menuItemRecordLogs = new JMenuItem( Configuration.getString("jmenuItemRecordLogs") );
		JMenuItem menuItemConfiguration = new JMenuItem( Configuration.getString("jmenuItemConfiguration") );
		JMenuItem menuItemAbout = new JMenuItem( Configuration.getString("jmenuItemAbout") );

		// Window Properties
		setTitle( Configuration.getString("titleApp") );
		setSize( new Dimension(900, 600) );
		setMinimumSize( new Dimension(500, 350) );
		setResizable(true);
		setContentPane(mainPane);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		WindowsManager.centerJFrame(this);

		// Borders
		testingPane.setBorder( new TitledBorder( Configuration.getString( "testingTitle" ) ));
		demoPane.setBorder( new TitledBorder( Configuration.getString( "demoTitle" ) ) );

		// JMenu
		setJMenuBar(menuBar);
		menuBar.add(menuRecords);
		menuBar.add(menuExtras);
		menuRecords.add(menuItemRecordLogs);
		menuExtras.add(menuItemConfiguration);
		menuExtras.add(menuItemAbout);

		// Testing buttons
		infoButton.setText( Configuration.getString("information") );
		warningButton.setText( Configuration.getString("warning") );
		errorButton.setText( Configuration.getString("error") );
		confimButton.setText( Configuration.getString("confirmation") );
		openButton.setText( Configuration.getString("open") );
		saveButton.setText( Configuration.getString("save") );
		forceInfoRecordButton.setText( Configuration.getString( "force" ) + " " + Configuration.getString( "information" ) );
		forceWarningRecordButton.setText( Configuration.getString( "force" ) + " " + Configuration.getString( "warning" ) );
		forceErrorRecordButton.setText( Configuration.getString( "force" ) + " " + Configuration.getString( "error" ) );

		// Demo label
		demoLabel.setText( Configuration.getString("demoLabel") );
		demoLabel.setFont( new Font( StaticStrings.FONT , Font.PLAIN, 15) );

		// Action listeners
		menuItemRecordLogs.addActionListener(e -> showRecordWindow());
		menuItemConfiguration.addActionListener(e -> showConfigurationWindow());
		menuItemAbout.addActionListener(e -> showAboutWindow());

		infoButton.addActionListener(e -> showInfoWindow());
		warningButton.addActionListener(e -> showWarningWindow());
		errorButton.addActionListener(e -> showErrorWindow());
		confimButton.addActionListener(e -> showConfirmWindow());
		openButton.addActionListener(e -> showOpenDialog());
		saveButton.addActionListener(e -> showSaveDialog());
		forceInfoRecordButton.addActionListener(e -> forceInfo());
		forceWarningRecordButton.addActionListener(e -> forceWarn());
		forceErrorRecordButton.addActionListener(e -> forceErr());
	}

	//////////////////////////////////////////
	// METHODS ///////////////////////////////
	//////////////////////////////////////////

	private void showInfoWindow() {
		WindowsManager.showInfoWindow( Configuration.getString("testingInfoAlert") );
	}

	private void showWarningWindow() {
		WindowsManager.showWarningWindow( Configuration.getString("testingWarnAlert") );
	}

	private void showErrorWindow() {
		WindowsManager.showErrorWindow( Configuration.getString("testingErrAlert") );
	}

	private void showConfirmWindow() {
		if (WindowsManager.showConfirmWindow( Configuration.getString("testingConfAlert") ))
			WindowsManager.showInfoWindow( Configuration.getString("testingTrueLabel") );
		else
			WindowsManager.showInfoWindow( Configuration.getString("testingFalseLabel") );
	}

	private void showOpenDialog() {
		new FileManager().showOpenDialog(null);
	}

	private void showSaveDialog() {
		new FileManager().showSaveDialog(null);
	}

	private void forceInfo() {
		RecordWindow.writeInfoInTheRecordLog( Configuration.getString( "testingRecord" ) );
	}

	private void forceWarn() {
		RecordWindow.writeWarningInTheRecordLog( Configuration.getString( "testingRecord" ) );
	}

	private void forceErr() {
		RecordWindow.writeErrorInTheRecordLog( Configuration.getString( "testingRecord" ) );
	}

	private void showRecordWindow() {
		windowsManager.showRecordWindow();
	}
	
	private void showConfigurationWindow() {
		windowsManager.showConfigurationWindow();
	}
	
	private void showAboutWindow() {
		windowsManager.showAboutWindow();
	}
	
	//////////////////////////////////////////
	// GET/SET ///////////////////////////////
	//////////////////////////////////////////
	
}

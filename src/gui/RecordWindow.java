package gui;

import kernel.Configuration;
import strings.StaticStrings;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class RecordWindow extends JFrame {

	//////////////////////////////////////////
	// ATTRIBUTES ////////////////////////////
	//////////////////////////////////////////

	private static final DateTimeFormatter time = DateTimeFormatter.ofLocalizedTime( FormatStyle.MEDIUM );
	private static final JTextArea recordLog = new JTextArea();

    private final FileManager fileManager;

    //////////////////////////////////////////
	// CONSTRUCTOR ///////////////////////////
	//////////////////////////////////////////
	
	public RecordWindow() {
        this.fileManager = new FileManager();

        // Window Elements
		JScrollPane mainPane = new JScrollPane();
		JMenuBar menuBar = new JMenuBar();
		JMenu menuExport = new JMenu( Configuration.getString("jmenuExport") );
		JMenu menuCleaner = new JMenu( Configuration.getString("jmenuClean") );
		JMenuItem menuItemCleanRecord = new JMenuItem( Configuration.getString("jmenuItemCleanRecords") );
		JMenuItem menuItemExportRecord = new JMenuItem( Configuration.getString("jmenuItemExportRecords") );
		JMenuItem menuItemQuickRecord = new JMenuItem( Configuration.getString("jmenuItemQuickExport") );

		// Window Properties
		setTitle( Configuration.getString("recordTitle") );
		setSize(new Dimension(900, 600));
		setMinimumSize(new Dimension(450, 300));
		setResizable(true);
		setContentPane(mainPane);
		WindowsManager.centerJFrame(this);

		// JMenu
		mainPane.setColumnHeaderView(menuBar);
		menuBar.add(menuExport);
		menuBar.add(menuCleaner);
		menuExport.add(menuItemExportRecord);
		menuExport.add(menuItemQuickRecord);
		menuCleaner.add(menuItemCleanRecord);

		// Action listeners
		menuItemCleanRecord.addActionListener(e -> cleanRecord());
		menuItemExportRecord.addActionListener(e -> exportRecord());
		menuItemQuickRecord.addActionListener(e -> quickExport());

		// File Manager
		fileManager.setFileFilter( new FileNameExtensionFilter( Configuration.getString("logExtensionDescription"), StaticStrings.RECORDS_EXTENSION_NAME ) );

		// Record
		mainPane.setViewportView(recordLog);
		recordLog.setEnabled(false);
		recordLog.setFont( new Font( StaticStrings.FONT , Font.PLAIN, 18) );
		recordLog.setText( time.format(LocalDateTime.now()) + " " + Configuration.getString("recordStarted") + recordLog.getText() );
	}
	
	//////////////////////////////////////////
	// METHODS ///////////////////////////////
	//////////////////////////////////////////
		
	public static void writeInfoInTheRecordLog(String newText) {
		recordLog.setText(recordLog.getText() + "\n" + time.format(LocalDateTime.now()) + " " + Configuration.getString("recordInformation") + newText);
	}

	public static void writeWarningInTheRecordLog(String newText) {
		recordLog.setText(recordLog.getText() + "\n" + time.format(LocalDateTime.now()) + " " + Configuration.getString("recordWarning") + newText);
	}
	
	public static void writeErrorInTheRecordLog(String newText) {
		recordLog.setText(recordLog.getText() + "\n" + time.format(LocalDateTime.now()) + " " + Configuration.getString("recordError") + newText);
	}

	private void cleanRecord() {
		if (WindowsManager.showConfirmWindow( Configuration.getString("recordCleanLabel") )) {
			recordLog.setText( time.format(LocalDateTime.now()) + " " + Configuration.getString("recordCleaned") );
		}
	}

	private void exportRecord() {
		fileManager.exportData( recordLog.getText(), StaticStrings.RECORDS_EXTENSION_NAME );
	}

	private void quickExport() {
		int result = FileManager.writeDataInFile( new File( StaticStrings.RECORDS_FILE_NAME ), false, recordLog.getText() );

		if (result == 0) {
			WindowsManager.showInfoWindow( Configuration.getString("successfulQuickExport") );
			RecordWindow.writeInfoInTheRecordLog( Configuration.getString("successfulQuickExport") );

		} else if (result == 1) {
			WindowsManager.showInfoWindow( Configuration.getString("canceledQuickExport") );
			RecordWindow.writeInfoInTheRecordLog( Configuration.getString("canceledQuickExport") );

		} else {
			WindowsManager.showErrorWindow( Configuration.getString("failureQuickExport") );
			RecordWindow.writeErrorInTheRecordLog( Configuration.getString("failureQuickExport") );
		}
	}

	//////////////////////////////////////////
	// GET/SET ///////////////////////////////
	//////////////////////////////////////////
	
}

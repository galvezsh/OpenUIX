package gui;

import kernel.Configuration;
import kernel.Kernel;
import strings.StaticStrings;

import javax.swing.*;
import java.awt.*;

public class WindowsManager {

	//////////////////////////////////////////
	// ATTRIBUTES ////////////////////////////
	//////////////////////////////////////////

    private final Kernel kernel;
	private final MainWindow mainWindow;
	private final RecordWindow recordWindow;
	private final ConfigurationWindow configurationWindow;
	private final AboutWindow aboutWindow;

	//////////////////////////////////////////
	// CONSTRUCTOR ///////////////////////////
	//////////////////////////////////////////

	public WindowsManager(Kernel kernel) {
		this.kernel = kernel;
        Image icon = Toolkit.getDefaultToolkit().getImage( StaticStrings.ICON_FILE_NAME );
		
		// Set the LookAndFeel
		setLookAndFeel();
		
		// Initialize windows
		this.aboutWindow = new AboutWindow();
		this.recordWindow = new RecordWindow();
		this.configurationWindow = new ConfigurationWindow(this);
		this.mainWindow = new MainWindow(this);

		// Set the app icon
		this.aboutWindow.setIconImage(icon);
		this.recordWindow.setIconImage(icon);
		this.configurationWindow.setIconImage(icon);
		this.mainWindow.setIconImage(icon);

        // Set the language for the buttons
        UIManager.put("OptionPane.yesButtonText", Configuration.getString( "yes" ));
        UIManager.put("OptionPane.noButtonText", Configuration.getString( "no" ));
        UIManager.put("OptionPane.okButtonText", Configuration.getString( "ok" ));
        UIManager.put("OptionPane.cancelButtonText", Configuration.getString( "cancel" ));

		showMainWindow();
	}

	//////////////////////////////////////////
	// METHODS ///////////////////////////////
	//////////////////////////////////////////

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel( kernel.getConfiguration().getTheme() );

			// GTK theme may produce this error
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			WindowsManager.showErrorWindow( Configuration.getString( "lookAndFeelError" ) );
			RecordWindow.writeErrorInTheRecordLog( Configuration.getString( "lookAndFeelError" ) );
			setDefaultLookAndFeel();
		}
	}

	private void setDefaultLookAndFeel() {
		try {
			RecordWindow.writeInfoInTheRecordLog( Configuration.getString( "lookAndFeelDefault" ) );
			UIManager.setLookAndFeel( StaticStrings.LOOKANDFEEL_NIMBUS);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			WindowsManager.showErrorWindow( Configuration.getString( "lookAndFeelCritical" ) );
			RecordWindow.writeErrorInTheRecordLog( Configuration.getString( "lookAndFeelCritical" ) );

		}
	}

	public static void showInfoWindow(String message) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, message, Configuration.getString( "information" ), JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showWarningWindow(String message) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, message, Configuration.getString( "warning" ), JOptionPane.WARNING_MESSAGE);
	}

	public static void showErrorWindow(String message) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, message, Configuration.getString( "error" ), JOptionPane.ERROR_MESSAGE);
	}
	
	public static boolean showConfirmWindow(String message) {
		Toolkit.getDefaultToolkit().beep();
        return JOptionPane.showConfirmDialog(null, message, Configuration.getString( "confirmation" ), JOptionPane.YES_NO_OPTION) == 0;
	}
	
	protected static void centerJFrame(JFrame window) {
        // Get the size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // get the size of the window
        Dimension windowSize = window.getSize();

        // Calculate centered position
        int posX = (screenSize.width - windowSize.width) / 3;
        int posY = (screenSize.height - windowSize.height) / 3;

        // Set the centered position
        window.setLocation(posX, posY);
	}

	protected static void centerJDialog(JDialog window) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension windowSize = window.getSize();

		int posX = (screenSize.width - windowSize.width) / 2;
		int posY = (screenSize.height - windowSize.height) / 2;

		window.setLocation(posX, posY);
	}

	protected void showMainWindow() {
		mainWindow.setVisible(true);
	}

	protected void showRecordWindow() {
		recordWindow.setVisible(true);
	}
	
	protected void showConfigurationWindow() {
		configurationWindow.setVisible(true);
	}
	
	protected void showAboutWindow() {
		aboutWindow.setVisible(true);
	}

	//////////////////////////////////////////
	// GET/SET ///////////////////////////////
	//////////////////////////////////////////
	
	public Kernel getKernel() {
		return kernel;
	}

}

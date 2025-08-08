package gui;

import kernel.Configuration;
import strings.StaticStrings;

import javax.swing.*;
import java.awt.*;

public class AboutWindow extends JDialog {

	//////////////////////////////////////////
	// ATTRIBUTES ////////////////////////////
	//////////////////////////////////////////

	private JPanel mainPane;
	private JLabel developerLabel;
	private JLabel developer;
	private JLabel emailLabel;
	private JLabel email;
	private JLabel versionAppLabel;
	private JLabel versionApp;
	private JLabel versionGuiLabel;
	private JLabel versionGui;
	private JLabel changesLabel;
	private JLabel changes;

	//////////////////////////////////////////
	// CONSTRUCTOR ///////////////////////////
	//////////////////////////////////////////
	
	public AboutWindow() {

		// Window Properties
		setTitle( Configuration.getString("aboutLabel") + Configuration.getString("titleApp") );
		setSize(new Dimension(550, 350));
		setResizable(false);
		setContentPane(mainPane);
		WindowsManager.centerJDialog(this);

		// Developer Label
		developerLabel.setText( Configuration.getString("developerLabel") );
		developerLabel.setFont( new Font( StaticStrings.FONT , Font.BOLD, 16) );
		developer.setText( Configuration.getString("developer") );
		developer.setFont( new Font( StaticStrings.FONT , Font.PLAIN, 15) );

		// Email Label
		emailLabel.setText( Configuration.getString("emailLabel") );
		emailLabel.setFont( new Font( StaticStrings.FONT , Font.BOLD, 16) );
		email.setText( Configuration.getString("email") );
		email.setFont( new Font( StaticStrings.FONT , Font.PLAIN, 15) );

		// App Version Label
		versionAppLabel.setText( Configuration.getString("versionAppLabel") );
		versionAppLabel.setFont( new Font( StaticStrings.FONT , Font.BOLD, 16) );
		versionApp.setText( Configuration.getString("versionApp") );
		versionApp.setFont( new Font( StaticStrings.FONT , Font.PLAIN, 15) );

		// Gui Version Label
		versionGuiLabel.setText( Configuration.getString("versionGuiLabel") );
		versionGuiLabel.setFont( new Font( StaticStrings.FONT , Font.BOLD, 16) );
		versionGui.setText( Configuration.getString("versionGui") );
		versionGui.setFont( new Font( StaticStrings.FONT , Font.PLAIN, 15) );

		// Changes Label
		changesLabel.setText( Configuration.getString("changesLabel") );
		changesLabel.setFont( new Font( StaticStrings.FONT , Font.BOLD, 16) );
		changes.setText( Configuration.getString("changes") );
		changes.setFont( new Font( StaticStrings.FONT , Font.PLAIN, 15) );
	}

	//////////////////////////////////////////
	// METHODS ///////////////////////////////
	//////////////////////////////////////////
	
	//////////////////////////////////////////
	// GET/SET ///////////////////////////////
	//////////////////////////////////////////
	
}

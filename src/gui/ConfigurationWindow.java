package gui;

import kernel.Configuration;
import strings.StaticStrings;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Objects;

public class ConfigurationWindow extends JDialog {
	
	//////////////////////////////////////////
	// ATTRIBUTES ////////////////////////////
	//////////////////////////////////////////

	private final WindowsManager windowsManager;

	private JPanel mainPane;
	private JPanel themePane;
	private JPanel languagePane;
	private JLabel labelTheme;
	private JComboBox<String> themes;
	private JLabel labelLanguage;
	private JComboBox<String> languages;
	private JButton applyChanges;
	private JPanel buttonPane;

	//////////////////////////////////////////
	// CONSTRUCTOR ///////////////////////////
	////////////////////////////////////////// 
	
	public ConfigurationWindow(WindowsManager windowsManager) {
		this.windowsManager = windowsManager;

		// Window Properties
		setTitle( Configuration.getString( "configurationTitle" ));
		setSize(new Dimension(350, 350));
		setResizable(false);
		setContentPane(mainPane);
		WindowsManager.centerJDialog(this);

		// Borders
		themePane.setBorder( new TitledBorder( Configuration.getString( "themeBorderLabel" ) ) );
		languagePane.setBorder( new TitledBorder( Configuration.getString( "languageBorderLabel" ) ) );

		// Themes
		labelTheme.setText( Configuration.getString( "selectTheTheme" ) );
		labelTheme.setFont( new Font( StaticStrings.FONT , Font.PLAIN, 15) );

		themes.addItem( StaticStrings.NIMBUS );
		themes.addItem( StaticStrings.METAL );
		themes.addItem( StaticStrings.WINDOWS );
		themes.addItem( StaticStrings.WINDOWS_CLASSIC );
		themes.addItem( StaticStrings.MOTIF );
		themes.addItem( StaticStrings.GTK );
		setCurrentTheme();

		// Languages
		labelLanguage.setText( Configuration.getString( "selectTheLanguage" ));
		labelLanguage.setFont( new Font( StaticStrings.FONT , Font.PLAIN, 15 ) );

		languages.addItem( Configuration.getString( "languageEnLabel" ) );
		languages.addItem( Configuration.getString( "languageEsLabel" ) );
		setCurrentLanguage();

		// Button
		applyChanges.setText( Configuration.getString( "apply" ));
		applyChanges.addActionListener(e -> updateChanges());
	}
	
	//////////////////////////////////////////
	// METHODS ///////////////////////////////
	//////////////////////////////////////////

	private void setCurrentTheme() {
		switch (windowsManager.getKernel().getConfiguration().getTheme()) {
			case StaticStrings.LOOKANDFEEL_METAL:
				themes.setSelectedItem( StaticStrings.METAL);
				break;

			case StaticStrings.LOOKANDFEEL_WINDOWS:
				themes.setSelectedItem( StaticStrings.WINDOWS);
				break;

			case StaticStrings.LOOKANDFEEL_WINDOWS_CLASSIC:
				themes.setSelectedItem( StaticStrings.WINDOWS_CLASSIC);
				break;

			case StaticStrings.LOOKANDFEEL_MOTIF:
				themes.setSelectedItem( StaticStrings.MOTIF);
				break;

			case StaticStrings.LOOKANDFEEL_GTK:
				themes.setSelectedItem( StaticStrings.GTK);
				break;

			default:
				themes.setSelectedItem( StaticStrings.NIMBUS);
				break;
		}
	}

	private void setCurrentLanguage() {
		if (windowsManager.getKernel().getConfiguration().getLanguage().equals( StaticStrings.LANGUAGE_ES_CODE) )
			languages.setSelectedItem( Configuration.getString( "languageEsLabel" ) );

		else
			languages.setSelectedItem( Configuration.getString( "languageEnLabel" ) );
	}

	private void updateChanges() {
		String selectedTheme = (String) Objects.requireNonNull( themes.getSelectedItem() );
		String selectedLanguage = (String) Objects.requireNonNull( languages.getSelectedItem() );

		String newTheme;
		String newLanguage;

		// Themes
		switch (selectedTheme) {
			case StaticStrings.METAL:
				newTheme = StaticStrings.LOOKANDFEEL_METAL;
				break;

			case StaticStrings.WINDOWS:
				newTheme = StaticStrings.LOOKANDFEEL_WINDOWS;
				break;

			case StaticStrings.WINDOWS_CLASSIC:
				newTheme = StaticStrings.LOOKANDFEEL_WINDOWS_CLASSIC;
				break;

			case StaticStrings.MOTIF:
				newTheme = StaticStrings.LOOKANDFEEL_MOTIF;
				break;

			case StaticStrings.LOOKANDFEEL_GTK:
				newTheme = StaticStrings.LOOKANDFEEL_GTK;
				break;

			default:
				newTheme = StaticStrings.LOOKANDFEEL_NIMBUS;
				break;
		}

		// Languages
		if (selectedLanguage.equals( Configuration.getString("languageEsLabel") ))
			newLanguage = StaticStrings.LANGUAGE_ES_CODE;

		else
			newLanguage = StaticStrings.LANGUAGE_EN_CODE;

		windowsManager.getKernel().getConfiguration().updateSettings(newTheme, newLanguage);
	}
	
	//////////////////////////////////////////
	// GET/SET ///////////////////////////////
	//////////////////////////////////////////
	
	}

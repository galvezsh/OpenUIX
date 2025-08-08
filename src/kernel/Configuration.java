package kernel;

import strings.StaticStrings;
import gui.FileManager;
import gui.RecordWindow;
import gui.WindowsManager;

import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Configuration {

	//////////////////////////////////////////
	// ATTRIBUTES ////////////////////////////
	//////////////////////////////////////////

	private static ResourceBundle bundle;
    private final File themeFile;

	private String theme;
	private String language;

	//////////////////////////////////////////
	// CONSTRUCTOR ///////////////////////////
	//////////////////////////////////////////

	public Configuration() {
		themeFile = new File( StaticStrings.SETTINGS_FILE_NAME );
		theme = StaticStrings.LOOKANDFEEL_NIMBUS;
		language = StaticStrings.LANGUAGE_EN_CODE;

		// Create settings file
		try {
			if (themeFile.createNewFile()) {
				// Settings file created successfully

				// System bundle
				bundle = ResourceBundle.getBundle("strings.language", new Locale( language ));

				RecordWindow.writeInfoInTheRecordLog( Configuration.getString( "settingsFileCreated" ) );

				writeSettings();

			} else {
				// Settings file already exists

				// System bundle
				if (!getCurrentTheme().isEmpty())
					theme = getCurrentTheme();
				if (!getCurrentLanguage().isEmpty())
					language = getCurrentLanguage();
				bundle = ResourceBundle.getBundle("strings.language", new Locale( language ));

				RecordWindow.writeInfoInTheRecordLog( Configuration.getString( "settingsFileDetected" ) );

			}

		} catch (IOException e) {
			// Cannot create the file, possibly due of insufficient permissions

			RecordWindow.writeErrorInTheRecordLog( Configuration.getString( "settingsFileCannotCreate" ) );
		}

	}

	//////////////////////////////////////////
	// METHODS ///////////////////////////////
	//////////////////////////////////////////

	private boolean writeSettings() {
		String data = StaticStrings.SETTINGS_FILE_LABEL +
				"\n " +
				"\n" + StaticStrings.LOOKANDFEEL_SETTINGS_LABEL + theme +
				"\n" + StaticStrings.LANGUAGE_SETTINGS_LABEL + language;

		if (FileManager.writeDataInFileDirectly(themeFile, false, data)) {
			RecordWindow.writeInfoInTheRecordLog( Configuration.getString( "settingsWroteSuccessfully" ) );
			return true;

		} else {
			RecordWindow.writeErrorInTheRecordLog( Configuration.getString( "settingsFileCannotWrite" ) );
			return false;
		}
	}
	
	private String getCurrentTheme() {
		String finder;
		String theme = "null";

		try (BufferedReader reader = new BufferedReader(new FileReader(themeFile))) {
			
			while ((finder = reader.readLine()) != null) {

				if (finder.startsWith( StaticStrings.LOOKANDFEEL_SETTINGS_LABEL )) {
					theme = finder.split("=")[1].trim();
				}
				
			}
					
		} catch (FileNotFoundException e) {
			WindowsManager.showErrorWindow( StaticStrings.SETTINGS_FILE_NOT_FOUND );
			RecordWindow.writeErrorInTheRecordLog( StaticStrings.SETTINGS_FILE_NOT_FOUND );
		} catch (IOException e) {
			WindowsManager.showErrorWindow( StaticStrings.SETTINGS_FILE_CANNOT_READ );
			RecordWindow.writeErrorInTheRecordLog( StaticStrings.SETTINGS_FILE_CANNOT_READ );
		}

		return theme;
	}

	private String getCurrentLanguage() {
		String finder;
		String language = "null";

		try (BufferedReader reader = new BufferedReader(new FileReader(themeFile))) {

			while ((finder = reader.readLine()) != null) {

				if (finder.startsWith( StaticStrings.LANGUAGE_SETTINGS_LABEL )) {
					language = finder.split("=")[1].trim();
				}

			}

		} catch (FileNotFoundException e) {
			WindowsManager.showErrorWindow( StaticStrings.SETTINGS_FILE_NOT_FOUND );
			RecordWindow.writeErrorInTheRecordLog( StaticStrings.SETTINGS_FILE_NOT_FOUND );
		} catch (IOException e) {
			WindowsManager.showErrorWindow( StaticStrings.SETTINGS_FILE_CANNOT_READ );
			RecordWindow.writeErrorInTheRecordLog( StaticStrings.SETTINGS_FILE_CANNOT_READ );
		}

		return language;
	}

	//////////////////////////////////////////
	// GET/SET ///////////////////////////////
	//////////////////////////////////////////

	public static String getString(String stringName) {
		return bundle.getString(stringName);
	}

	public String getTheme() {
		return theme;
	}

	public String getLanguage() {
		return language;
	}

	public void updateSettings(String newTheme, String newLanguage) {
		theme = newTheme;
		language = newLanguage;

		if (writeSettings())
			WindowsManager.showInfoWindow( Configuration.getString( "closeToApply" ) );
		else
			WindowsManager.showErrorWindow( Configuration.getString( "settingsFileCannotCreate" ) );

	}
}

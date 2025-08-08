package gui;

import kernel.Configuration;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager extends JFileChooser {

	//////////////////////////////////////////
	// ATTRIBUTES ////////////////////////////
	//////////////////////////////////////////

	//////////////////////////////////////////
	// CONSTRUCTOR ///////////////////////////
	//////////////////////////////////////////
	
	public FileManager() {
	/*
		UIManager.put("acceptAllFileFilterText", "Directorios");
		UIManager.put("lookInLabelText", "Localização");
		UIManager.put("cancelButtonText", "Cancel");
		UIManager.put("cancelButtonToolTipText", "Cancel");
		UIManager.put("openButtonText", "Open");
		UIManager.put("openButtonToolTipText", "Adicionar ficheiro(s)");
		UIManager.put("filesOfTypeLabelText", "Tipo");
		UIManager.put("fileNameLabelText", "Ficheiro(s)");
		UIManager.put("listViewButtonToolTipText", "Lista");
		UIManager.put("listViewButtonAccessibleName", "Lista");
		UIManager.put("detailsViewButtonToolTipText", "Detalhes");
		UIManager.put("detailsViewButtonAccessibleName", "Detalhes");
		UIManager.put("upFolderToolTipText", "Um nível acima");
		UIManager.put("upFolderAccessibleName", "Um nível acima");
		UIManager.put("homeFolderToolTipText", "Ambiente de Trabalho");
		UIManager.put("homeFolderAccessibleName", "Ambiente de Trabalho"); UIManager.put("FileChooser.fileNameHeaderText", "Nome");
		UIManager.put("fileSizeHeaderText", "Tamanho");
		UIManager.put("fileTypeHeaderText", "Tipo");
		UIManager.put("fileDateHeaderText", "Data");
		UIManager.put("fileAttrHeaderText", "Atributos");
		UIManager.put("openDialogTitleText","Open");
		UIManager.put("readOnly", Boolean.TRUE);
	*/
	}
	
	//////////////////////////////////////////
	// METHODS ///////////////////////////////
	//////////////////////////////////////////
	
	protected void exportData(String data, String extensionName) {
		String extension = "." + extensionName;
		int value = this.showSaveDialog(null);
		
		// SAVE CASE
		if (value == JFileChooser.APPROVE_OPTION) {

			// Check is the name is empty
			if (!this.getSelectedFile().getName().isEmpty()) {

				// With the file extension
				if (this.getSelectedFile().getName().endsWith( extension )) {

					exportDataInFile(this.getSelectedFile(), data);

				// Without the file extension
				} else {
					String newExtension = this.getSelectedFile().getName() + extension;

					// Create a duplicate file to rename the latest
					String newPath = this.getSelectedFile().getParent() + File.separator + newExtension;
					File newFile = new File(newPath);

					exportDataInFile(newFile, data);
				}

			} else {

				WindowsManager.showWarningWindow( "emptyFileName" );
				RecordWindow.writeWarningInTheRecordLog( "canceledExport" );

			}
			
		// CANCEL CASE	
		} else if (value == JFileChooser.CANCEL_OPTION) {
			// Do anything for the cancel option
			RecordWindow.writeInfoInTheRecordLog( Configuration.getString("canceledExport") );
			
		// ERROR CASE	
		} else if (value == JFileChooser.ERROR_OPTION) {
			WindowsManager.showErrorWindow( Configuration.getString("errorPath") );
			
		// ALL ERROR CASE	
		} else {
			WindowsManager.showErrorWindow( Configuration.getString("unexpectedErrorFileManager") );
			RecordWindow.writeErrorInTheRecordLog( Configuration.getString("unexpectedErrorFileManager") );
		}
	}

	private void exportDataInFile(File file, String data) {
		int result = writeDataInFile( file, false, data );

		if (result == 0) {
			WindowsManager.showInfoWindow( Configuration.getString("successfulExport") );
			RecordWindow.writeInfoInTheRecordLog( Configuration.getString("successfulExport") );

		} else if (result == 1) {
			WindowsManager.showInfoWindow( Configuration.getString("canceledExport") );
			RecordWindow.writeInfoInTheRecordLog( Configuration.getString("canceledExport") );

		} else {
			WindowsManager.showErrorWindow( Configuration.getString("failureExport") );
			RecordWindow.writeErrorInTheRecordLog( Configuration.getString("failureExport") );
		}
	}

	public static int writeDataInFile(File file, boolean append, String data) {
		if (file.exists()) {

			if (WindowsManager.showConfirmWindow( Configuration.getString("duplicatedFile") )) {

				try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
					writer.write( data );
					return 0;

				} catch (IOException e) {
					return 2;
				}

			} else {
				return 1;
			}

		} else {

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
				writer.write( data );
				return 0;

			} catch (IOException e) {
				return 2;
			}

		}

	}

	public static boolean writeDataInFileDirectly(File file, boolean append, String data) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
			writer.write( data );
			return true;

		} catch (IOException e) {
			return false;
		}
	}
	
	//////////////////////////////////////////
	// GET/SET ///////////////////////////////
	//////////////////////////////////////////

}

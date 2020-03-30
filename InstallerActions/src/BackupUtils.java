import com.zerog.ia.api.pub.InstallerProxy;
import com.zerog.ia.api.pub.UninstallerProxy;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BackupUtils {
    public void backupFolderOnInstall(InstallerProxy installerProxy) throws Exception {
        String userInstallDir = installerProxy.getVariable("USER_INSTALL_DIR").toString();

        backupFolder(userInstallDir);
    }

    public void backupFolderOnUninstall(UninstallerProxy uninstallerProxy) throws Exception {
        String userInstallDir = uninstallerProxy.getVariable("USER_INSTALL_DIR").toString();

        backupFolder(userInstallDir);
    }

    public void backupFolder(String userInstallDir) throws Exception {
        // check if the backup parent folder exists, and if not, attempt to create it.

        String backupParentFolderPath = userInstallDir + System.getProperty("file.separator") + "backups";
        File backupParentFolder = new File(backupParentFolderPath);

        if (!backupParentFolder.exists()) {
            if (!backupParentFolder.mkdirs()) {
                throw new Exception("Couldn't create backup parent folder " + backupParentFolder.getAbsolutePath());
            }
        }

        // get the date and time string to make the backup folder

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        String dateTimeString = dateFormat.format(new Date());

        // check if the backup folder exists, and if not, attempt to create it.

        String backupFolderPath = backupParentFolderPath + System.getProperty("file.separator") + dateTimeString;
        File backupFolder = new File(backupFolderPath);

        if (!backupFolder.exists()) {
            if (!backupFolder.mkdirs()) {
                throw new Exception("Couldn't create backup folder " + backupFolder.getAbsolutePath());
            }
        }

        //

        String excludedFoldersArray[] = new String[]{backupParentFolderPath};
        List<String> excludedFoldersList = Arrays.asList(excludedFoldersArray);

        try {
            copyDirectory(userInstallDir, backupFolderPath, excludedFoldersList);
        } catch (IOException ioe) {

        } catch (Exception e) {

        }
    }

    public void copyDirectory(String sourceDirectoryPath, String destinyDirectoryPath, List<String> excluding) throws IOException {
        String fileSeparator = System.getProperty("file.separator");

        File sourceDirectory = new File(sourceDirectoryPath);

        if (!sourceDirectory.exists()) {
            throw new FileNotFoundException(sourceDirectoryPath + " not found.");
        }

        if (!sourceDirectory.isDirectory()) {
            throw new FileNotFoundException(sourceDirectoryPath + " is not a directory.");
        }

        if (excluding.contains(sourceDirectoryPath)) {
            return;
        }

        String[] filesAndDirs = sourceDirectory.list();
        int numberFiles = filesAndDirs.length;

        File destinyDirectory = new File(destinyDirectoryPath);

        if (!destinyDirectory.exists()) {
            if (!destinyDirectory.mkdirs()) {
                throw new IOException("Couldn't create destiny directory " + destinyDirectory.getAbsolutePath());
            }
        }

        if (numberFiles == 0) {
            return;
        }

        for (int i = 0; i < numberFiles; i++) {
            File currentFile = new File(sourceDirectory.getPath() + fileSeparator + filesAndDirs[i]);

            if (currentFile.isDirectory()) {
                copyDirectory(currentFile.getPath(), destinyDirectory.getPath() + fileSeparator + currentFile.getName(), excluding);
                continue;
            } else {
                if (destinyDirectory.exists() == false) {
                    if (destinyDirectory.mkdirs() == false) {
                        throw new IOException("Couldn't create directory " + destinyDirectory.getAbsolutePath());
                    }
                }

                File newFile = new File(destinyDirectory.getPath() + fileSeparator + currentFile.getName());

                FileUtils.copyFile(currentFile, newFile);
            }
        }
    }
}

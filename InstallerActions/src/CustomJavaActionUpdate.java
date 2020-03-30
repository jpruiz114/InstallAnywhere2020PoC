import com.zerog.ia.api.pub.CustomCodeAction;
import com.zerog.ia.api.pub.InstallException;
import com.zerog.ia.api.pub.InstallerProxy;
import com.zerog.ia.api.pub.UninstallerProxy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CustomJavaActionUpdate extends CustomCodeAction {
    private void pause() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ie) {
            System.err.println(ie.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void install(InstallerProxy installerProxy) throws InstallException {
        pause();

        //

        installerProxy.setVariable("$CHECK_DISK_SPACE$", "FORCE");

        //

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        String dateTimeString = dateFormat.format(new Date());

        String productVersionName = installerProxy.getVariable("PRODUCT_VERSION_NUMBER").toString();

        String filePath = System.getProperty("user.home") + System.getProperty("file.separator") + dateTimeString + "_" + "updating_to_" + productVersionName + ".txt";

        VariablesUtils vu = new VariablesUtils();
        vu.writeVariables(installerProxy, filePath);

        //

        try {
            BackupUtils bu = new BackupUtils();
            bu.backupFolderOnInstall(installerProxy);
        } catch (Exception e) {

        }
    }

    /**
     * The uninstall() method is called at uninstall-time before any files, folders or other installation actions are uninstalled.
     *
     * @param uninstallerProxy Provides access to designer-specified resources, system and user-defined variables, and international resources.
     * @throws InstallException
     */
    @Override
    public void uninstall(UninstallerProxy uninstallerProxy) throws InstallException {
        pause();

        //

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        String dateTimeString = dateFormat.format(new Date());

        String productVersionName = installerProxy.getVariable("PRODUCT_VERSION_NUMBER").toString();

        String filePath = System.getProperty("user.home") + System.getProperty("file.separator") + dateTimeString + "_" + "uninstalling_update_" + productVersionName + ".txt";

        VariablesUtils vu = new VariablesUtils();
        vu.writeVariables(installerProxy, filePath);
    }

    @Override
    public String getInstallStatusMessage() {
        return null;
    }

    @Override
    public String getUninstallStatusMessage() {
        return null;
    }
}

import com.zerog.ia.api.pub.InstallerProxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;

public class VariablesUtils {
    public void writeVariables(InstallerProxy ip, String path) {
        try {
            PrintStream ps = new PrintStream(new FileOutputStream(path));

            File temporaryDirectory = ip.getTempDirectory();
            ps.println(temporaryDirectory.toString());
            ps.println();

            String productVersionName = ip.getVariable("PRODUCT_VERSION_NUMBER").toString();
            ps.println("productVersionName = " + productVersionName);
            ps.println();

            String userInstallDir = ip.getVariable("USER_INSTALL_DIR").toString();
            ps.println("userInstallDir = " + userInstallDir);
            ps.println();

            Enumeration variables = ip.getVariables();

            String currentVariableName = "";
            Object currentVariableValue = "";

            while (variables.hasMoreElements()) {
                currentVariableName = variables.nextElement().toString();
                currentVariableValue = ip.getVariable(currentVariableName);

                ps.println(currentVariableName + " = " + currentVariableValue.toString());
            }

            ps.flush();
            ps.close();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

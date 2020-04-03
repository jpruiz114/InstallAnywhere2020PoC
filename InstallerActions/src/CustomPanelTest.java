import com.zerog.ia.api.pub.CustomCodePanel;
import com.zerog.ia.api.pub.CustomCodePanelProxy;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CustomPanelTest extends CustomCodePanel {
    private boolean isSetup = false;

    private Font plainFont;
    private Font boldFont;

    private Map<String, TextField> varName2TextField = new HashMap<String, TextField>();

    @Override
    public boolean setupUI(CustomCodePanelProxy customCodePanelProxy) {
        if (!isSetup) {
            // define a font name and font size
            String fontName = "Dialog";
            int fontSize = System.getProperty("os.name").contains("Windows") ? 12 : 8;

            // define the fonts
            plainFont = new Font(fontName, Font.PLAIN, fontSize);
            boldFont = new Font(fontName, Font.BOLD, fontSize);

            // create a data entry panel
            Panel dataEntryPanel = new Panel();
            dataEntryPanel.setLayout(new GridLayout(5, 1));

            //

            JTextArea topPrompt = new JTextArea("Please enter the following parameters that ABC will use" + "\n" + "to connect to the database");
            LineBorder border = new LineBorder(Color.GRAY, 1, false);
            topPrompt.setBorder(border);
            topPrompt.setEditable(false);
            topPrompt.setFont(plainFont);
            topPrompt.setMargin(new Insets(10, 10, 10, 10));
            topPrompt.setRows(7);

            //

            Panel topPanel = new Panel() {
                public Insets getInsets() {
                    return new Insets(10, 10, 10, 10);
                }
            };

            topPanel.setLayout(new BorderLayout());
            topPanel.add(topPrompt, BorderLayout.CENTER);

            add(topPanel, BorderLayout.NORTH);

            // use the i18n resource for the current default locale.

            dataEntryPanel.add(makeInputPanel(customCodePanelProxy.getValue("PromptUserConsole.Host"), "$DB_HOST$", false));
            dataEntryPanel.add(makeInputPanel(customCodePanelProxy.getValue("PromptUserConsole.Port"), "$DB_PORT$", false));
            dataEntryPanel.add(makeInputPanel(customCodePanelProxy.getValue("PromptUserConsole.DatabaseName"), "$DB_NAME$", false));
            dataEntryPanel.add(makeInputPanel(customCodePanelProxy.getValue("PromptUserConsole.Username"), "$DB_USERNAME$", false));
            dataEntryPanel.add(makeInputPanel(customCodePanelProxy.getValue("PromptUserConsole.Password"), "$DB_PASSWORD$", true));

            // add the data entry panel
            add(dataEntryPanel, BorderLayout.CENTER);

            isSetup = true;
        }

        return true;
    }

    private JLabel makeRequiredLabel(String text) {
        JLabel label = new JLabel();

        label.setFont(boldFont);
        label.setHorizontalAlignment(Label.RIGHT);
        label.setText("<html>" + text + "<font color=FF0000>*</font></html>");

        return label;
    }

    private Panel makeInputPanel(String label, String variableName, boolean useEchoChar) {
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(1, 2));

        panel.add(makeRequiredLabel(label));

        TextField textField = new TextField();
        textField.setFont(plainFont);

        if (useEchoChar) {
            textField.setEchoChar('*');
        }

        panel.add(textField);

        varName2TextField.put(variableName, textField);

        return panel;
    }

    @Override
    public boolean okToContinue() {
        for (Map.Entry<String, TextField> entry : varName2TextField.entrySet()) {
            String variableName = entry.getKey();
            TextField textField = entry.getValue();

            String value = textField.getText().trim();

            // This variable is accessible from the parent class CustomCodePanel
            customCodePanelProxy.setVariable(variableName, value);
        }

        return true;
    }
}

/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.santulator.gui.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import org.controlsfx.dialog.Wizard;
import org.controlsfx.dialog.WizardPane;

public class TrackedWizardPane extends WizardPane {
    private final Runnable onEnterCallback;

    public TrackedWizardPane(final Node content, final Runnable onEnterCallback) {
        this.onEnterCallback = onEnterCallback;
        setContent(content);
        setHeader(createHeader());
    }

    private Node createHeader() {
        Label label = new Label();

        label.setPrefSize(0, 0);

        return label;
    }

    @Override
    public void onEnteringPage(final Wizard wizard) {
        super.onEnteringPage(wizard);
        onEnterCallback.run();
    }
}

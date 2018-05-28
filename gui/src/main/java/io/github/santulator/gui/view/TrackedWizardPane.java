package io.github.santulator.gui.view;

import javafx.scene.Node;
import org.controlsfx.dialog.Wizard;
import org.controlsfx.dialog.WizardPane;

public class TrackedWizardPane extends WizardPane {
    private final Runnable onEnterCallback;

    public TrackedWizardPane(final Node content, final Runnable onEnterCallback) {
        this.onEnterCallback = onEnterCallback;
        setContent(content);
    }

    @Override
    public void onEnteringPage(final Wizard wizard) {
        super.onEnteringPage(wizard);
        onEnterCallback.run();
    }
}

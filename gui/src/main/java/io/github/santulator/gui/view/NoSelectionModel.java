package io.github.santulator.gui.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

public class NoSelectionModel<T> extends MultipleSelectionModel<T> {
    @Override
    public ObservableList<Integer> getSelectedIndices() {
        return FXCollections.emptyObservableList();
    }

    @Override
    public ObservableList<T> getSelectedItems() {
        return FXCollections.emptyObservableList();
    }

    @Override
    public void selectIndices(final int index, final int... indices) {
        // No implementation - selection is not supported
    }

    @Override
    public void selectAll() {
        // No implementation - selection is not supported
    }

    @Override
    public void selectFirst() {
        // No implementation - selection is not supported
    }

    @Override
    public void selectLast() {
        // No implementation - selection is not supported
    }

    @Override
    public void clearAndSelect(final int index) {
        // No implementation - selection is not supported
    }

    @Override
    public void select(final int index) {
        // No implementation - selection is not supported
    }

    @Override
    public void select(final T obj) {
        // No implementation - selection is not supported
    }

    @Override
    public void clearSelection(final int index) {
        // No implementation - selection is not supported
    }

    @Override
    public void clearSelection() {
        // No implementation - selection is not supported
    }

    @Override
    public boolean isSelected(final int index) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void selectPrevious() {
        // No implementation - selection is not supported
    }

    @Override
    public void selectNext() {
        // No implementation - selection is not supported
    }
}
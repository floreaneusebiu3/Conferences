package view;

import model.User;

import javax.swing.*;

public interface ILoginView {
    void updateOnSuccess();
    void updateOnFail();
    JFrame getFrame();
}

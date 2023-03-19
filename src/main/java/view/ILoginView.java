package view;

import model.User;

public interface ILoginView {
    void updateOnSuccess();
    void updateOnFail();
    void showUserInterface(User user);
}

package model;

import view.ParticipantView;
import viewModel.command.LoginCommand;
import view.LoginView;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        //LoginView loginView = new LoginView();
        User user =new User();
        ParticipantView participantView = new ParticipantView(user);

    }
}

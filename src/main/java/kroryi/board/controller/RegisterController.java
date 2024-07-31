package kroryi.board.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import kroryi.board.dto.User;
import kroryi.board.service.UserService;
import kroryi.board.service.UserServiceImpl;
import kroryi.board.util.SceneUtil;

import java.io.IOException;

public class RegisterController {
    public Button gotoLogin;
    public TextField tfId;
    public TextField tfUsername;
    public PasswordField tfPw;
    public PasswordField tfPwre;

    private UserService userService = new UserServiceImpl();


    public void register(ActionEvent event) throws IOException {
        String userid = tfId.getText();
        String username = tfUsername.getText();
        String password = tfPw.getText();
        String password_re = tfPwre.getText();
        if(userid == null || userid.trim().isEmpty()){
            showAlert("아이디를 입력하시오.");
            return;
        }

        if(username == null || username.trim().isEmpty()){
            showAlert("사용자명을 입력하시오.");
            return;
        }

        if(password == null || password.trim().isEmpty()){
            showAlert("비밀번호를 입력하시오.");
        }

        if(password_re == null || password_re.trim().isEmpty()){
            showAlert("다시 한 번 더 비밀번호를 입력하시오.");
            return;
        }

        if(!password.trim().equals(password_re.trim())){
            showAlert("비밀번호가 일치하지 않습니다.");
            return;
        }else {
            User user= new User(tfId.getText(), tfUsername.getText(), tfPw.getText());
            int result = userService.insert(user);
            if(result > 0){
                showAlert("회원가입 성공");
                SceneUtil.getInstance().switchScene(event,UI.LOGIN.getPath());
            }else {
                showAlert("회원가입에 문제가 발생했습니다.");
            }
        }
    }

    public void gotoLogin(ActionEvent event) {

    }

    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("입력 오류");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

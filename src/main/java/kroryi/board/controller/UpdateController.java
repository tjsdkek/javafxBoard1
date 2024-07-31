package kroryi.board.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kroryi.board.dto.Board;
import kroryi.board.service.BoardService;
import kroryi.board.service.BoardServiceImpl;
import kroryi.board.util.SceneUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateController {
    @FXML
    public TextField tfTitle;
    @FXML
    public TextField tfWriter;
    @FXML
    public TextField tfRegDate;
    @FXML
    public TextArea taContent;

    int boardNo;
    private BoardService boardService = new BoardServiceImpl();

    List<Integer> numArr = new ArrayList<>();
    int targetValue = boardNo;
    int prevValue = -1;
    int nextValue = -1;

    public void read(int boardNo){
        numArr = listNum();
        this.boardNo = boardNo;
        Board board = boardService.select(boardNo);
        tfTitle.setText(board.getTitle());
        tfWriter.setText(board.getWriter());
        taContent.setText(board.getContent());
        tfRegDate.setText(board.getRegDate());
    }

    private List<Integer> listNum() {
        List<Board> boardList = new ArrayList<>();
        boardList = boardService.list();
        numArr.clear();
        for(Board board : boardList){
            numArr.add(board.getNo());
        }

        targetValue = boardNo;
        int idx = numArr.indexOf(targetValue);
        if(idx > 0){
            prevValue = numArr.get(idx-1);
        }
        if(idx < numArr.size()-1){
            nextValue = numArr.get(idx+1);
        }
        return  numArr;
    }

    public void moveToUpdate(ActionEvent event) throws IOException {
        Board board = new Board(tfTitle.getText(),taContent.getText(),tfWriter.getText());
        board.setNo(boardNo);
        int result = boardService.update(board);
        if(result > 0) {
            System.out.println("글 수정 완료");
            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
        }

    }

    public void moveToPrev(ActionEvent event) {
        numArr = listNum();
        read(prevValue);
    }

    public void moveToList(ActionEvent event) throws IOException {
        SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
    }

    public void moveToDelete(ActionEvent event) throws IOException {
        showAlert(event);
    }

    public void moveToNext(ActionEvent event) {
        numArr = listNum();
        System.out.println("Next->" + numArr.toString());
        read(nextValue);
    }

    private void showAlert(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("게시글 삭제");
        alert.setHeaderText("게시글을 정말 삭제 하시나요?(글번호:" + boardNo +")");
        alert.setContentText("삭제 후 되돌릴 수 없습니다.");
        int result =0;
        if(alert.showAndWait().get()  == ButtonType.OK){
            result = boardService.delete(boardNo);
        }
        if(result > 0){
            System.out.println("글삭제 잘됨.");
            SceneUtil.getInstance().switchScene(event, UI.LIST.getPath());
        }
    }

}

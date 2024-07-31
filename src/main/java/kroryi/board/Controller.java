package kroryi.board;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import kroryi.board.controller.ReadController;
import kroryi.board.controller.UI;
import kroryi.board.dto.Board;
import kroryi.board.service.BoardService;
import kroryi.board.service.BoardServiceImpl;
import kroryi.board.util.SceneUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private final int pageSize = 4;
    @FXML
    public CheckBox chkSelected;
    Stage stage;
    Scene scene;
    Parent root;
    BoardService boardService = new BoardServiceImpl();
    @FXML
    private TableView<Board> boardTableView;
    @FXML
    private TableColumn<Board, Integer> colNo;
    @FXML
    private TableColumn<Board, String> colTitle;
    @FXML
    private TableColumn<Board, String> colWriter;
    @FXML
    private TableColumn<Board, String> colRegDate;
    @FXML
    private TableColumn<Board, String> colUpdDate;
    @FXML
    private TableColumn<Board, Boolean> colCheckBox;
    @FXML
    private Pagination pagination;
    private int totalCount = 0;
    private int totalPage;

    public int getPageSize() {
        return pageSize;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        totalCount = boardService.totalListCount();
        totalCount = totalCount == 0 ? 1 : totalCount;
        totalPage = (totalCount + pageSize -1)/ pageSize;

        pagination.setPageCount(totalPage);
        pagination.setMaxPageIndicatorCount(pageSize);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer integer) {
                pageListAll(pagination.getCurrentPageIndex());
                return new Label(String.format("현재 페이지 : %d", pagination.getCurrentPageIndex()+1));
            }
        });



        SwingNode swingNode = new SwingNode();
        // Swing 구성 요소를 생성하고 SwingNode에 설정합니다.
        createAndSetSwingContent(swingNode);

//        List<Board> boardList = new ArrayList<>();
//        boardList = boardService.list();
//
//        System.out.println(boardList);
//
//        ObservableList<Board> list = FXCollections.observableArrayList(boardList);
//        boardTableView.setItems(list);
//
//        // new PropertyValueFactory<>("No")
//        // 이 부분은 Board.java의 getNo()로 된 메서드의
//        // get을 뺀 No를 적어야 함.
//
//        colNo.setCellValueFactory(new PropertyValueFactory<>("No"));
//        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
//        colWriter.setCellValueFactory(new PropertyValueFactory<>("Writer"));
//        colRegDate.setCellValueFactory(new PropertyValueFactory<>("RegDate"));
//        colUpdDate.setCellValueFactory(new PropertyValueFactory<>("UpdDate"));
//        colCheckBox.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
//
//        colCheckBox.setCellFactory(new Callback<TableColumn<Board, Boolean>, TableCell<Board, Boolean>>() {
//            @Override
//            public TableCell<Board, Boolean> call(TableColumn<Board, Boolean> boardBooleanTableColumn) {
//                return new TableCell<Board, Boolean>() {
//                    private final CheckBox checkBox = new CheckBox();
//
//                    {
//                        checkBox.setOnAction(event -> {
//                            int index = getIndex();
//                            if (index >= 0 && index < boardTableView.getItems().size()) {
//                                boardTableView.getSelectionModel().select(index, colCheckBox);
//                                System.out.println(boardTableView.getSelectionModel().getSelectedItem());
//                            }
//                            System.out.println("checkbox index: " + index);
//                        });
//                    }
//
//                    @Override
//                    protected void updateItem(Boolean item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//                            checkBox.setSelected(item != null && item);
//                            setGraphic(checkBox);
//                        }
//                    }
//                };
//            }
//
//        });

        boardTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && boardTableView.getSelectionModel().getSelectedItem() != null) {
                    int boardNo = boardTableView.getSelectionModel().getSelectedItem().getNo();
                    try {
                        ReadController readController = (ReadController) SceneUtil.getInstance().getController(UI.READ.getPath());
                        readController.read(boardNo);
                        Parent root = SceneUtil.getInstance().getRoot();
                        SceneUtil.getInstance().switchScene(mouseEvent, UI.READ.getPath(), root);
                    } catch (IOException e) {
                        System.out.println("목록->읽기 이동중 에러 발생");
                        e.printStackTrace();
                    }
                }
            }
        });

//        chkSelected.setOnAction(event -> {
//            boolean selected = chkSelected.isSelected();
//            for (Board board : list) {
//                board.setSelected(selected);
//            }
//        });
    }

    public void moveToInsert(ActionEvent event) throws IOException {
        System.out.println("글쓰기 화면 이동");
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(UI.INSERT.getPath()));
//        root = loader.load();
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
        SceneUtil.getInstance().switchScene(event, UI.INSERT.getPath());
    }

    public void Close(ActionEvent event) {

    }

    private void createAndSetSwingContent(SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            // Swing JTextField 생성
            JTextField textField = new JTextField(20);
            // JTable 생성 및 설정
            String[] columnNames = {"Column1", "Column2"};
            Object[][] data = {
                    {"Data1", "Data2"},
                    {"Data3", "Data4"},
            };
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);

            // 레이아웃 설정을 위해 JPanel 사용
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(textField, BorderLayout.NORTH);
            panel.add(new JScrollPane(table), BorderLayout.CENTER);

            // SwingNode에 JPanel 추가
            swingNode.setContent(panel);
        });
    }

    public void pageListAll(int pageIndex){
        List<Board> boardList;
        boardList = boardService.pageList(pageIndex);
        totalCount = boardList.size();

        ObservableList<Board> list = FXCollections.observableArrayList(boardList);
        colNo.setCellValueFactory(new PropertyValueFactory<>("No"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colWriter.setCellValueFactory(new PropertyValueFactory<>("Writer"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("RegDate"));
        colUpdDate.setCellValueFactory(new PropertyValueFactory<>("UpdDate"));

        boardTableView.setItems(list);
    }
}
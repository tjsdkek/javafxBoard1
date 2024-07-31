package kroryi.board;

import kroryi.board.dto.Board;
import kroryi.board.service.BoardService;
import kroryi.board.service.BoardServiceImpl;

import java.util.Scanner;

public class BoardApp {

    static BoardService boardService = new BoardServiceImpl();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        //목록보기
//        for (Board board : boardService.list()) {
//            System.out.println(board.getNo() + " " + board.getTitle() + " " + board.getWriter() + " " + board.getRegDate());
//        }
//        // 게시글 등록
        Board board = new Board();
//        board.setTitle("너무 더워요");
//        board.setContent("더워");
//        boardService.insert(board);
//
//        // 게시물 상세보기
        Board boardSelect = boardService.select(5);
//        System.out.println("No : " + boardSelect.getNo());
//        System.out.println("Title : " + boardSelect.getTitle());
//        System.out.println("Writer : " + boardSelect.getWriter());
//        System.out.println("Date : " + boardSelect.getRegDate());

        //게시판 수정
        boardSelect.setWriter("고먐미");
        boardSelect.setTitle("츄르 먹고싶어요");
        boardService.update(boardSelect);
        Board boardSelect2 = boardService.select(5);
        System.out.println("No : " + boardSelect2.getNo());
        System.out.println("Writer : " + boardSelect2.getWriter());
        System.out.println("Title : " + boardSelect2.getTitle());
        System.out.println("Content : " + boardSelect2.getContent());
        System.out.println("Date : " + boardSelect2.getRegDate());

        //게시물 삭제
        System.out.println("게시물을 삭제.");
        System.out.println("삭제할 번호를 입력 : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (board != null) {
            boardService.delete(id);
            System.out.println("게시물 삭제 완료");
        }else {
            System.out.println("번호가 존재하지 않습니다.");
        }

    }
}




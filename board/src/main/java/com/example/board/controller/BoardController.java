package com.example.board.controller;

import com.example.board.domain.Board;
import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    //생성 API
    @PostMapping("/api/boards")
    public Board createBoard(@RequestBody BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        return boardRepository.save(board);
    }

    //전체 조회 API
    @GetMapping("/api/boards")
    public List<BoardResponseDto> getBoards() {
        List<BoardResponseDto> all =  boardService.findAllBoard();
        return all;
    }

    //상세 조회 API
    @GetMapping("/api/boards/{id}")
    public Board gerDetailBoard(@PathVariable long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
        return board;
    }

    //비밀번호 확인
    @GetMapping("/api/boards/{id}/{password}")
    public boolean comparePassword(@PathVariable Long id,@PathVariable String password){
        //아이디 객체 생성
        Long paramId = id;
        //비밀번호 객체 생성
        String passwordParam = password;
        return boardService.comparePassword(paramId, passwordParam);
    }

    //삭제 API
    @DeleteMapping("/api/boards/{id}")
    public Long deleteBoard(@PathVariable Long id) {
        boardRepository.deleteById(id);
        return id;
    }

    //업데이트 API
    @PutMapping("/api/boards/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        boardService.update(id, requestDto);
        return id;
    }
}


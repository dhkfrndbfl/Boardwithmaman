package com.example.board.controller;

import com.example.board.model.Board;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("boards", boardService.getAllBoards());
        return "list";
    }

    @GetMapping("/view")
    public String view(@RequestParam Long id, Model model) {
        Board board = boardService.getBoardById(id).orElseThrow();
        model.addAttribute("board", board);
        return "view";
    }

    @GetMapping("/writeform")
    public String writeForm() {
        return "writeform";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Board board) {
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());
        boardService.saveBoard(board);
        return "redirect:/list";
    }

    @GetMapping("/updateform")
    public String updateForm(@RequestParam Long id, Model model) {
        Board board = boardService.getBoardById(id).orElseThrow();
        model.addAttribute("board", board);
        return "updateform";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Board board) {
        Board existingBoard = boardService.getBoardById(board.getId()).orElseThrow();
        if (!existingBoard.getPassword().equals(board.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        board.setUpdatedAt(LocalDateTime.now());
        boardService.saveBoard(board);
        return "redirect:/view?id=" + board.getId();
    }

    @GetMapping("/deleteform")
    public String deleteForm(@RequestParam Long id, Model model) {
        model.addAttribute("id", id);
        return "deleteform";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, @RequestParam String password) {
        Board board = boardService.getBoardById(id).orElseThrow();
        if (!board.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        boardService.deleteBoard(id);
        return "redirect:/list";
    }
}

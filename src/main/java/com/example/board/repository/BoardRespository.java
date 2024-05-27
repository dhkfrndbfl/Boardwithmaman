package com.example.board.repository;

import com.example.board.model.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long> {
    // Additional query methods if needed
}

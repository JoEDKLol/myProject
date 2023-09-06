package com.jdl.jdlhome.service;

import com.jdl.jdlhome.entity.Board01;
import com.jdl.jdlhome.repository.Board01Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Board01Service {

    private final Board01Repository board01Repository;

    public List<Object[]> board01ListWriter(Integer limitPage, Integer currentPage){
        return board01Repository.getBoard01Writer(limitPage, currentPage);
    }



}

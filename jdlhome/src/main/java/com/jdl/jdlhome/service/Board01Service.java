package com.jdl.jdlhome.service;

import com.jdl.jdlhome.dto.Board01WriterDto;
import com.jdl.jdlhome.entity.Board01;
import com.jdl.jdlhome.repository.Board01Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Board01Service {

    private final Board01Repository board01Repository;

    public List<Object[]> board01ListWriter(Integer limitPage, Integer currentPage){
        return board01Repository.getBoard01Writer(limitPage, currentPage);
    }

    @Transactional  //db 트랜잭션 자동으로 commit 해줌
    public Long borardSave(Board01WriterDto board01WriterDto, Authentication authentication) {
        //dto 를 entity 화 해서 repository 의 save 메소드를 통해 db 에 저장.
        //저장 후 생성한 id 반환해줌
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        System.out.println(board01WriterDto.getTitle());
        System.out.println(board01WriterDto.getContent());
        System.out.println(userDetails.getUsername());
        //Board01 board01 = new Board01();

        Board01 board01 = Board01.builder()
                .user_id(userDetails.getUsername())
                .title(board01WriterDto.getTitle())
                .content(board01WriterDto.getContent())
                .goodCnt(0L)
                .viewCnt(0L)
                .delYn("N")
                .build();
        board01.setReg_user(userDetails.getUsername());
        board01.setUp_id(userDetails.getUsername());

        return board01Repository.save(board01).getNo();
    }

    @Transactional  //db 트랜잭션 자동으로 commit 해줌
    public void borardUpdate(Board01WriterDto board01WriterDto, Long no) {
        //dto 를 entity 화 해서 repository 의 save 메소드를 통해 db 에 저장.
        Optional<Board01> board01 = board01Repository.findById(no);
        board01.ifPresent(
                board -> {
                    board.setContent(board01WriterDto.getContent());
                }
        );
        //System.out.println(board01.get());

    }



}

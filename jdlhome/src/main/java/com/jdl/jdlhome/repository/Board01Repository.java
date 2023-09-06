package com.jdl.jdlhome.repository;

import com.jdl.jdlhome.entity.Board01;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Board01Repository extends JpaRepository<Board01, Long> {
    @Query(
            //value = "select a.* ,b.name from board01 a inner join user b where a.id = b.id order by a.no desc"
            value = "select a.*, b.name from board01 a inner join user b where a.id = b.id order by a.no desc limit ?1 offset ?2"
            , nativeQuery = true
    )
    //List<Board01> getBoard01Writer();
    List<Object[]> getBoard01Writer(Integer limitCnt, Integer currentPage);

}

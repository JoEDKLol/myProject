package com.jdl.jdlhome.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "board01")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Board01 extends Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    private String user_id;
    private String title;
    private String content;
    @Column(name = "good_cnt")
    private Long goodCnt;
    @Column (name = "view_cnt")
    private Long viewCnt;
    @Column (name = "del_yn")
    private String delYn;

//    @ManyToOne
//    @JoinColumn(name="user_id")
//    private User user;

}

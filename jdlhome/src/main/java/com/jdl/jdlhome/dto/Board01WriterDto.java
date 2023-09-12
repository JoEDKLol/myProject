package com.jdl.jdlhome.dto;

import com.jdl.jdlhome.entity.Common;
import lombok.Data;


@Data
public class Board01WriterDto extends Common {
    private Long no;
    private String user_id;
    private String title;
    private String content;
    private Long goodCnt;
    private Long viewCnt;
    private String delYn;
    //private String name;
    private Long totCnt;

}

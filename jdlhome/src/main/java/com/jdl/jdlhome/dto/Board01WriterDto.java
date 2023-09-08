package com.jdl.jdlhome.dto;

import com.jdl.jdlhome.entity.Common;
import com.jdl.jdlhome.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Board01WriterDto extends Common {
    private Long no;
    private Long id;
    private String title;
    private String content;
    private Long goodCnt;
    private Long viewCnt;
    private String delYn;
    private String name;
    private Long totCnt;

}

package com.jdl.jdlhome.controller;

import com.jdl.jdlhome.dto.Board01WriterDto;
import com.jdl.jdlhome.entity.Board01;
import com.jdl.jdlhome.service.Board01Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final Board01Service board01Service;

    @GetMapping("/boardSearchList")
    public String boardSearchList(Model model, String boardNm, String page, Integer currentPage){



        if(boardNm.equals("board101")){
            //model.addAttribute("list",  board01Service.board01ListWriter());
            //model.addAttribute("msg","succese");
        }else if(boardNm.equals("board102")){

        } // ...

        System.out.println("currentPage:::" + currentPage);
        //System.out.println(board01Service.board01ListWriter().get(0).);

        Integer limitPage = 10;
        Integer _currentPage = limitPage* (1-currentPage);

        List<Object[]> obj = board01Service.board01ListWriter(limitPage, _currentPage);


        List<Board01WriterDto> list = new ArrayList<>();
        for (Object[] user : obj) {
            Board01WriterDto board01WriterDto = new Board01WriterDto();
            board01WriterDto.setNo(Long.valueOf((Integer) user[0]));
            board01WriterDto.setId(Long.valueOf((Integer) user[1]));
            board01WriterDto.setTitle((String) user[2]);
            board01WriterDto.setContent((String) user[3]);
            board01WriterDto.setGoodCnt(Long.valueOf((Integer) user[4]));
            board01WriterDto.setViewCnt(Long.valueOf((Integer) user[5]));
            board01WriterDto.setDelYn(String.valueOf(user[6]));
            board01WriterDto.setReg_date(String.valueOf(user[7]));
            board01WriterDto.setReg_user((String) user[8]);
            board01WriterDto.setUp_date( String.valueOf(user[9]));
            board01WriterDto.setUp_id((String) user[10]);
            board01WriterDto.setName((String) user[11]);
            list.add(board01WriterDto);

        }

        model.addAttribute("list",  list);

        String retPage = page + " :: #main_div";
        System.out.println(retPage);
        return retPage;
    }

    public static String NullCheck(String obj, String defaultStr){
        String result = defaultStr;
        if(obj != null && !"".equals(obj)){
            result = String.valueOf(obj);
        }
        return result;
    }

}

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
        Integer _currentPage = limitPage* (currentPage-1);

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
            board01WriterDto.setReg_date(String.valueOf(user[7]).substring(0, 10));
            board01WriterDto.setReg_user((String) user[8]);
            board01WriterDto.setUp_date( String.valueOf(user[9]));
            board01WriterDto.setUp_id((String) user[10]);
            board01WriterDto.setName((String) user[11]);
            //board01WriterDto.setTot_cnt(Long.valueOf(   (String) user[12])   );
            board01WriterDto.setTotCnt((Long) user[12]);
            list.add(board01WriterDto);

        }

        //System.out.println(list.get(0).getTot_cnt());

        model.addAttribute("list",  list);
        model.addAttribute("totCount",  list.get(0).getTotCnt());
        model.addAttribute("currentPage",  currentPage);



        Long totRowCnt = list.get(0).getTotCnt();
//Math.floorDiv(26,10) = 2
        Long totPageCnt = Math.floorDiv(totRowCnt,10);

        if(totRowCnt%10 > 0){
            totPageCnt = totPageCnt+1;
        }

        System.out.println("totPageCnt::" + totPageCnt);
        //currentPage = 12;
        Long startPage = (long) Math.floorDiv(currentPage,5)*5+1;
        System.out.println("startPage111::" + startPage);

        System.out.println("Math.floorDiv(currentPage,5)::" + Math.floorDiv(currentPage,5));

        if(currentPage%5 == 0 && currentPage >= 5){
            System.out.println("여기 온다고");
            startPage = startPage = (long) (Math.floorDiv(currentPage,5)-1)*5+1;
        }

        Long lastPage = startPage + 4;
        if(lastPage > totPageCnt){
            lastPage = totPageCnt;
        }

        System.out.println("currentPage::" + currentPage);
        System.out.println("startPage::" + startPage);
        System.out.println("lastPage::" + lastPage);
        List<Long> pageList = new ArrayList<>();
        for(Long i = startPage; i<=lastPage; i++){
            System.out.println(i);
            pageList.add(i);
        }



        model.addAttribute("pageList",  pageList);
        model.addAttribute("totPageCnt",  totPageCnt);


        String retPage = page + " :: #main_div";
        //System.out.println(retPage);
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

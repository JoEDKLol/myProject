package com.jdl.jdlhome.controller;

import com.google.gson.JsonObject;
import com.jdl.jdlhome.dto.Board01WriterDto;
import com.jdl.jdlhome.service.Board01Service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
            board01WriterDto.setUser_id((String) user[1]);
            board01WriterDto.setTitle((String) user[2]);
            board01WriterDto.setContent((String) user[3]);
            board01WriterDto.setGoodCnt(Long.valueOf((Integer) user[4]));
            board01WriterDto.setViewCnt(Long.valueOf((Integer) user[5]));
            board01WriterDto.setDelYn(String.valueOf(user[6]));
            board01WriterDto.setReg_date(String.valueOf(user[7]).substring(0, 10));
            board01WriterDto.setReg_user((String) user[8]);
            board01WriterDto.setUp_date( String.valueOf(user[9]));
            board01WriterDto.setUp_id((String) user[10]);
            //  board01WriterDto.setName((String) user[11]);
            //board01WriterDto.setTot_cnt(Long.valueOf(   (String) user[12])   );
            board01WriterDto.setTotCnt((Long) user[11]);
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
        //System.out.println("startPage111::" + startPage);

        //System.out.println("Math.floorDiv(currentPage,5)::" + Math.floorDiv(currentPage,5));

        if(currentPage%5 == 0 && currentPage >= 5){
            //System.out.println("여기 온다고");
            startPage = startPage = (long) (Math.floorDiv(currentPage,5)-1)*5+1;
        }

        Long lastPage = startPage + 4;
        if(lastPage > totPageCnt){
            lastPage = totPageCnt;
        }

//        System.out.println("currentPage::" + currentPage);
//        System.out.println("startPage::" + startPage);
//        System.out.println("lastPage::" + lastPage);
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

    @GetMapping("/write")
    public String writePage(HttpServletRequest req){
        //HttpSession session = req.getSession();
        return "main/write.html";
    }

    @PostMapping("/writeAction")
    public String writeAction(Board01WriterDto board01WriterDto,
                              @RequestParam(value="fileList[]") List<String> fileList,
                              Authentication authentication){

        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //System.out.println(userDetails.getUsername()); //userId - PrincipalDetails class check
        //1차 저장
        Long no = board01Service.borardSave(board01WriterDto, authentication);;

        //System.out.println(fileList);

        //img copy after save DB
        String content = board01WriterDto.getContent();
        content = content.replaceAll("/temp", "/" + no);


        //no로 업데이트 필요 :  저장 -> 업데이트 및 temp 이미지 삭제 및 이동
        board01WriterDto.setContent(content);
        board01Service.borardUpdate(board01WriterDto, no);;


        //해당게시글의 이미지만 이동
        
        // temp 폴더 안의 이미지를 게시글 저장소로 이동
        String path_folder1 = realPath + "/upload_image/image/fileupload/temp/";
        String path_folder2 = realPath + "/upload_image/image/fileupload/" + no + "/";

        // 폴더 복사 함수
        fileUpload(path_folder1, path_folder2, fileList);




        return "main/write.html";
    }

    private static String realPath = "C:\\springbootPJ\\jdlhome_img";

    // img temp save to summernote
    @RequestMapping(value = "/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
    @ResponseBody
    public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile,
                                            HttpServletRequest request) {
        // JSON 객체 생성
        //JsonObject jsonObject = new JsonObject(); // -> json-simple dependency
        JsonObject jsonObject = new JsonObject(); // -> gson
        // 이미지 파일이 저장될 경로 설정
        String contextRoot = realPath + "/upload_image/image/fileupload/temp/";
        String fileRoot = contextRoot;

        // 업로드된 파일의 원본 파일명과 확장자 추출
        String originalFileName = multipartFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 새로운 파일명 생성 (고유한 식별자 + 확장자)
        String savedFileName = UUID.randomUUID() + extension;

        // 저장될 파일의 경로와 파일명을 나타내는 File 객체 생성
        File targetFile = new File(fileRoot + savedFileName);

        try {
            // 업로드된 파일의 InputStream 얻기
            java.io.InputStream fileStream = multipartFile.getInputStream();

            // 업로드된 파일을 지정된 경로에 저장
            FileUtils.copyInputStreamToFile(fileStream, targetFile);


            // JSON 객체에 이미지 URL과 응답 코드 추가
            jsonObject.addProperty("url", "/upload_image/image/fileupload/temp/" + savedFileName);
            jsonObject.addProperty("responseCode", "success");
        } catch (IOException e) {
            // 파일 저장 중 오류가 발생한 경우 해당 파일 삭제 및 에러 응답 코드 추가
            FileUtils.deleteQuietly(targetFile);
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        // JSON 객체를 문자열로 변환하여 반환
        String a = jsonObject.toString();
        System.out.println("이미지경로"+a);
        return a;
    }

    // 서머노트 이미지 삭제 temp에서
    @RequestMapping(value = "/deleteSummernoteImageFile", produces = "application/json; charset=utf8")
    @ResponseBody
    public String deleteSummernoteImageFile(@RequestParam("file") String fileName) {
        JsonObject jsonObject = new JsonObject(); // -> gson
        // 폴더 위치
        String filePath = realPath + "/upload_image/image/fileupload/temp/";

        // 해당 파일 삭제
        System.out.println("realPath::"+filePath);
        System.out.println("fileName::"+fileName);
        jsonObject.addProperty("url", "/upload_image/image/fileupload/temp/" + fileName);


        deleteFile(filePath, fileName);

        String a = jsonObject.toString();
        System.out.println("이미지경로"+a);
        return a;
    }

    // 하위 폴더 복사
    private void fileUpload(String path_folder1, String path_folder2, List<String> fileList) {
        // path_folder1에서 path_folder2로 파일을 복사하는 함수입니다.

        File folder1;
        File folder2;
        folder1 = new File(path_folder1);
        folder2 = new File(path_folder2);

        // 복사할 폴더들이 존재하지 않으면 생성합니다.
        if (!folder1.exists())
            folder1.mkdirs();
        if (!folder2.exists())
            folder2.mkdirs();

        for(int i=0; i<fileList.size(); i++){

            String [] arr = fileList.get(i).split("/");
            System.out.println("fileList.get(i)" + arr[arr.length-1]);
        }


        // 폴더1에서 파일 목록을 가져옵니다.
        File[] target_file = folder1.listFiles();
        for (File file : target_file) {
            // 복사 대상 파일의 경로와 이름을 설정합니다.
            //System.out.println("file.getName()::"+file.getName());



            File temp = new File(folder2.getAbsolutePath() + File.separator + file.getName());

            if (file.isDirectory()) {
                // 대상이 폴더인 경우, 해당 폴더를 생성합니다.
                temp.mkdir();
            } else {
                for(int i=0; i<fileList.size(); i++){

                    String [] arr = fileList.get(i).split("/");
                    //System.out.println("fileList.get(i)" + arr[arr.length-1]);
                    if(arr[arr.length-1].equals(file.getName())){

                        System.out.println("파일명 같은 경우 여기 오나?");

                        FileInputStream fis = null;
                        FileOutputStream fos = null;
                        try {
                            // 파일 복사를 위해 FileInputStream과 FileOutputStream을 생성합니다.
                            fis = new FileInputStream(file);
                            fos = new FileOutputStream(temp);

                            byte[] b = new byte[4096];
                            int cnt = 0;
                            while ((cnt = fis.read(b)) != -1) {
                                // 버퍼를 사용하여 파일 내용을 읽고 복사합니다.
                                fos.write(b, 0, cnt);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            // FileInputStream과 FileOutputStream을 닫습니다.
                            try {
                                fis.close();
                                fos.close();

                                //복사된 후 템프 폴더에 파일 삭제
                                String filePath = realPath + "/upload_image/image/fileupload/temp/";
                                deleteFile(filePath, file.getName());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    // 하위 폴더 삭제
    private void deleteFolder(String path) {
        // 주어진 경로에 있는 폴더와 파일을 재귀적으로 삭제하는 함수입니다.

        File folder = new File(path);
        try {
            if (folder.exists()) {
                File[] folder_list = folder.listFiles();
                for (int i = 0; i < folder_list.length; i++) {
                    if (folder_list[i].isFile())
                        // 파일인 경우, 파일을 삭제합니다.
                        folder_list[i].delete();
                    else
                        // 폴더인 경우, 재귀적으로 폴더 내부의 파일 및 폴더를 삭제합니다.
                        deleteFolder(folder_list[i].getPath());
                    // 파일이나 폴더를 삭제합니다.
                    folder_list[i].delete();
                }
                // 폴더를 삭제합니다.
                folder.delete();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    // 위치값으로 내부 파일 이름 가져오기
    private List<String> getFileNamesFromFolder(String folderName) {
        // 파일 이름을 저장할 리스트 생성
        List<String> fileNames = new ArrayList<>();

        // 주어진 폴더 경로를 기반으로 폴더 객체 생성
        File folder = new File(folderName);
        // 폴더 내의 파일들을 가져옴
        File[] files = folder.listFiles();
        if (files != null) {
            // 파일인 경우 파일 이름을 리스트에 추가
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }
        // 파일 이름을 담은 리스트 반환
        return fileNames;
    }

    // 더미 파일 삭제
    private void removeDummyFiles(List<String> fileNames, String filePath, String contents) {
        // 주어진 파일 이름 리스트를 기반으로 파일을 삭제
        for (String fileName : fileNames) {
            // contents 문자열에 파일 이름이 포함되어 있지 않은 경우 파일 삭제
            if (!contents.contains(fileName)) {
                deleteFile(filePath, fileName);
            }
        }
    }


    // 파일 하나 삭제
    private void deleteFile(String filePath, String fileName) {
        // 주어진 파일 경로와 이름을 기반으로 파일 경로 객체 생성
        Path path = Paths.get(filePath, fileName);
        try {
            // 파일 삭제
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

/**
 * 파일명     : FaqService.java
 * 화면명     : FAQ 관리
 * 설명       : FAQ 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.06
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.FaqDAO;
import com.kb.inno.admin.DTO.CategoryDTO;
import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FaqService {

    // DAQ 연결
    private final FaqDAO faqDAO;

    // FAQ 리스트 조회
    public void selectList(int menuId, int ctgry, Model model, String type, String keyword, int page) {
        // Search DTO에 담기
        SearchDTO search = new SearchDTO();
        search.setType(type);
        search.setKeyword(keyword);
        search.setCtgry(ctgry);

        // 페이지의 전체 글 갯수
        int allCount = faqDAO.selectPageCount(search);
        
        // 한 페이지당 글 갯수
        int pageLetter = 10;

        // 전체 글 갯수 / 한 페이지에 나올 글 갯수
        int repeat = allCount / pageLetter;
        // 나머지가 0이 아니면
        if(allCount % pageLetter != 0){
            // 더하기
            repeat += 1;
        }
        
        // repeat이 0이면
        if(repeat == 0) {
            repeat = 1;
        }

        // 만약 가져온 페이지가 repeat 보다 크다면
        if(repeat < page) {
            page = repeat;
        }

        // 만약 가져온 페이지가 0이라면
        if(page < 1) {
            page = 1;
        }

        // 끝 페이지
        int end = page * pageLetter;
        search.setEnd(end);
        // 시작 페이지
        int start = end + 1 - pageLetter;

        search.setStart(start);

        // 리스트 조회
        List<FaqDTO> selectList = faqDAO.selectList(search);

        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
        model.addAttribute("ctgry", ctgry);
    }

    // FAQ 등록
    public int insert(FaqDTO faqDTO, int loginId) {
        // 로그인 한 아이디 대입
        faqDTO.setFrst_rgtr(loginId);
        faqDTO.setLast_mdfr(loginId);

        return faqDAO.insert(faqDTO);
    }

    // FAQ 상세 조회
    public FaqDTO select(int faq_sn) {
        return faqDAO.select(faq_sn);
    }
    
    // FAQ 수정
    public int update(FaqDTO faqDTO, int loginId) {
        // 로그인 한 아이디 대입
        faqDTO.setLast_mdfr(loginId);

        return faqDAO.update(faqDTO);
    }

    // FAQ 삭제
    public void delete(int faq_sn) {
        faqDAO.delete(faq_sn);
    }

    // 카테고리 조회
    public Map<String, Object> selectCategory(int ctgry) {
        return faqDAO.selectCategory(ctgry);
    }

    // FAQ 조회 (미리보기용)
    public void selectListAll(int ctgry, Model model, String type, String keyword, int page, FaqDTO faqDTO) {
        // Search DTO에 담기
        SearchDTO search = new SearchDTO();
        search.setType(type);
        search.setKeyword(keyword);

        if(ctgry != 0) {
            search.setCtgry(ctgry);
        }

        // 페이지의 전체 글 갯수
        int allCount = faqDAO.selectPageCount(search);

        // 한 페이지당 글 갯수
        int pageLetter = 10;

        // 전체 글 갯수 / 한 페이지에 나올 글 갯수
        int repeat = allCount / pageLetter;
        // 나머지가 0이 아니면
        if(allCount % pageLetter != 0){
            // 더하기
            repeat += 1;
        }

        // repeat이 0이면
        if(repeat == 0) {
            repeat = 1;
        }

        // 만약 가져온 페이지가 repeat 보다 크다면
        if(repeat < page) {
            page = repeat;
        }

        // 만약 가져온 페이지가 0이라면
        if(page < 1) {
            page = 1;
        }

        // 끝 페이지
        int end = page * pageLetter;
        search.setEnd(end);
        // 시작 페이지
        int start = end + 1 - pageLetter;

        search.setStart(start);

        // FAQ_SN이 있다면 추가
        if(faqDTO.getFaq_sn() != 0) {
            search.setFaq_sn(faqDTO.getFaq_sn());
        }

        // 리스트 조회
        List<FaqDTO> selectList = faqDAO.selectListAll(search);

        // 선택한 카테고리
        Map<String, Object> category = faqDAO.selectCategory(ctgry);

        // 카테고리 리스트 조회
        List<CategoryDTO> selectCategory = faqDAO.selectCategoryAll();

        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("selectCategory", selectCategory);
        model.addAttribute("ctgry", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);
        model.addAttribute("faq", faqDTO);
    }
}

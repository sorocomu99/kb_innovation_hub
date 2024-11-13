package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.FaqDAO;
import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqService {

    // DAQ 연결
    private final FaqDAO faqDAO;

    // FAQ 리스트 조회
    public void selectList(Model model, String type, String keyword, int page) {
        // Search DTO에 담기
        SearchDTO search = new SearchDTO();
        search.setType(type);
        search.setKeyword(keyword);

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
}
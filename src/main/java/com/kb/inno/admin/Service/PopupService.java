package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.PopupDAO;
import com.kb.inno.admin.DTO.PopupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopupService {
    private final PopupDAO popupDAO;

    // 팝업 리스트
    public List<PopupDTO> selectList() {
        List<PopupDTO> list = popupDAO.selectList();
        return list;
    }
    
    // 팝업 추가
    public int popupAdd(PopupDTO popupDTO, int loginId) {
        // 로그인 한 아이디 세팅
        popupDTO.setFrst_rgtr(loginId);
        popupDTO.setLast_mdfr(loginId);

        // 날짜에서 - 빼기
        String bngn_ymd = popupDTO.getBgng_ymd();
        bngn_ymd = bngn_ymd.replace("-", "");
        popupDTO.setBgng_ymd(bngn_ymd);

        String end_ymd = popupDTO.getEnd_ymd();
        end_ymd = end_ymd.replace("-", "");
        popupDTO.setEnd_ymd(end_ymd);

        // 시간에서 : 빼기
        String bgng_hr = popupDTO.getBgng_hr();
        bgng_hr = bgng_hr.replace(":", "");
        popupDTO.setBgng_hr(bgng_hr);

        String end_hr = popupDTO.getEnd_hr();
        end_hr = end_hr.replace(":", "");
        popupDTO.setEnd_hr(end_hr);

        int result = popupDAO.popupAdd(popupDTO);
        return result;
    }

    // 팝업 조회 페이지
    public PopupDTO popupModify(int id) {
        PopupDTO popupDTO = popupDAO.select(id);

        if(popupDTO == null) {
            return null;
        }

        return popupDTO;
    }

    // 팝업 수정
    public int popupModify(PopupDTO popupDTO, int loginId) {
        // 로그인 한 아이디 세팅
        popupDTO.setLast_mdfr(loginId);

        // 날짜에서 - 빼기
        String bngn_ymd = popupDTO.getBgng_ymd();
        bngn_ymd = bngn_ymd.replace("-", "");
        popupDTO.setBgng_ymd(bngn_ymd);

        String end_ymd = popupDTO.getEnd_ymd();
        end_ymd = end_ymd.replace("-", "");
        popupDTO.setEnd_ymd(end_ymd);

        // 시간에서 : 빼기
        String bgng_hr = popupDTO.getBgng_hr();
        bgng_hr = bgng_hr.replace(":", "");
        popupDTO.setBgng_hr(bgng_hr);

        String end_hr = popupDTO.getEnd_hr();
        end_hr = end_hr.replace(":", "");
        popupDTO.setEnd_hr(end_hr);

        int reuslt = popupDAO.modify(popupDTO);

        return reuslt;
    }
    
    // 팝업 삭제
    public int delete(String popupId) {
        int result = popupDAO.delete(popupId);
        return result;
    }
}

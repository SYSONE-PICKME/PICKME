package project.pickme.notice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.pickme.notice.domain.Notice;
import project.pickme.notice.mapper.NoticeMapper;

@Service
public class NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;

	public List<Notice> getAllNotice(){
		List<Notice> li =  noticeMapper.selectAll();

		System.out.println("li = " + li.toString());
		return li;
	}

	public Notice getNoticeById(long id){
		return noticeMapper.selectById(id);
	}

	public void createNotice(Notice notice){
		noticeMapper.insert(notice);
	}

	public void updateNotice(Notice notice){
		noticeMapper.update(notice);
	}

	public void deleteNotice(long id){
		noticeMapper.delete(id);
	}

}


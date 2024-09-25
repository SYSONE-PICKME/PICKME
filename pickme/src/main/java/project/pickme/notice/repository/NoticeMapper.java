package project.pickme.notice.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.pickme.notice.domain.Notice;

@Mapper
public interface NoticeMapper {

	List<Notice> selectAllNotices();

	List<Notice> selectAllCampaigns();

	Notice selectById(Long id);

	void insert(Notice notice);

	void update(Notice notice);

	void delete(Long id);

	List<String> select4Campaigns();

	List<Notice> selectRecentAll();

	List<Notice> selectRecentNotices();

	List<Notice> selectRecentCampaigns();
}

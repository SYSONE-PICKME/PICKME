package project.pickme.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.pickme.notice.domain.Notice;

@Mapper
public interface NoticeMapper {

	List<Notice> selectAllNotices();
	List<Notice> selectAllCampaigns();
	List<Notice> selectByType(@Param("type") String type);
	Notice selectById(Long id);
	void insert(Notice notice);
	void update(Notice notice);
	void delete(Long id);
}
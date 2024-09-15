package project.pickme.like.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.pickme.like.dto.LikeDto;
import project.pickme.like.repository.LikeMapper;

@Service
@RequiredArgsConstructor
public class LikeService {
	private final LikeMapper likeMapper;

	public void switchOrAddIfNotExist(Long itemId, String userId) {
		Optional<LikeDto> like = likeMapper.findByItemIdAndUserId(itemId, userId);

		if(like.isEmpty()) {
			likeMapper.save(itemId, userId);
			return;
		}

		likeMapper.updateStatus(itemId, userId);
	}
}

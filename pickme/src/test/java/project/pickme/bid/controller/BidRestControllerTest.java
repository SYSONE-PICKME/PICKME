package project.pickme.bid.controller;

import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static project.pickme.user.constant.Type.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.pickme.bid.dto.response.BidDetailsDto;
import project.pickme.bid.dto.response.PriceDto;
import project.pickme.bid.service.BidService;
import project.pickme.user.constant.Role;
import project.pickme.user.domain.User;
import project.pickme.user.repository.UserMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BidRestControllerTest {
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private UserMapper userMapper;

	@MockBean private BidService bidService;


	@BeforeEach
	void initUser(){
		userMapper.save(createUser("testUser"));
	}

	@AfterEach
	void tearDown() {
		userMapper.deleteAll();
	}

	@Test
	@DisplayName("입찰 페이지에서 아이템 정보를 내려주는 컨트롤러")
	@WithUserDetails(value = "testUser", userDetailsServiceBeanName = "userDetailServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
	void showBidDetails() throws Exception {
	    // given
		Long itemId = 1l;
		List<PriceDto> priceDtos = List.of(new PriceDto(1L, 1000, "testUser"));
		BidDetailsDto bidDetailsDto = BidDetailsDto.createOf(priceDtos, 10000l);

		when(bidService.showBidDetails(anyLong(), any(User.class))).thenReturn(bidDetailsDto);

	    // when // then
		mockMvc.perform(get("/user/bid/details/{itemId}", itemId))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.allPrice", contains(1000)))
			.andExpect(jsonPath("$.data.maxPrice").value(1000))
			.andExpect(jsonPath("$.data.userId").value("testUser"))
			.andExpect(jsonPath("$.data.bidId").value(1))
			.andExpect(jsonPath("$.data.myPoint").value(10000));
	}

	private static User createUser(String id) {
		return User.builder()
			.id(id)
			.password("1234")
			.role(Role.ROLE_USER)
			.name("김테스트")
			.email("test@naver.com")
			.type(USER)
			.addr("서울특별시 종로구 창경궁로")
			.phoneNum("010-1111-1111")
			.point(0)
			.businessNum(null)
			.build();
	}
}

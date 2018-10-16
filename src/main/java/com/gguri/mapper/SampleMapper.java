package com.gguri.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.gguri.swp.domain.UserVO;

public interface SampleMapper {
	
	@Select("select now()")
	public String getTime() throws Exception;
	
	@Select("select uname from user where uid = #{uid}")
	public String getUname(@Param("uid") String uid) throws Exception;
	
	@Update("update user set loginip = #{loginip} where uid = #{uid}")
	public void setLoginIp(@Param("loginip") String loginip,@Param("uid") String uid) throws Exception;
	@Update("update user set lastlogin = now() where uid = #{uid}")
	public void setLastLogion(@Param("uid") String uid) throws Exception;
	@Select("select * from user where uid = #{uid}")
	public UserVO getUser(@Param("uid") String uid) throws Exception;
	@SelectProvider(type=SampleProvider.class, method="searchUser")
	public List<UserVO> serachUser(@Param("searchCol") String searchCol,
								   @Param("searchStr") String searchStr) throws Exception;
}

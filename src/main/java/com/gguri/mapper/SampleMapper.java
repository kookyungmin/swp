package com.gguri.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gguri.swp.domain.UserVO;

public interface SampleMapper {
	
	@Select("select now()")
	public String getTime();
	
	@Select("select uname from user where uid = #{uid}")
	public String getUname(@Param("uid") String uid);
	
	@Update("update user set loginip = #{loginip} where uid = #{uid}")
	public void setLoginIp(@Param("loginip") String loginip,@Param("uid") String uid);
	@Update("update user set lastlogin = now() where uid = #{uid}")
	public void setLastLogion(@Param("uid") String uid);
	@Select("select * from user where uid = #{uid}")
	public UserVO getUser(@Param("uid") String uid);
}

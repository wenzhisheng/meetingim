package kingim.dao;

import kingim.model.AdminVO;
import kingim.model.WhiteIP;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface AdminMapper extends Mapper<AdminVO> {

    AdminVO login(@Param("vo") AdminVO adminVO);

}
package kingim.dao;

import kingim.model.LoginLogVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface LoginLogMapper extends Mapper<LoginLogVO> {

    List<LoginLogVO> list(@Param("vo") LoginLogVO loginLogVO);

}
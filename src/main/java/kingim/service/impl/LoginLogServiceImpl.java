package kingim.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import kingim.dao.AdminMapper;
import kingim.dao.LoginLogMapper;
import kingim.model.AdminVO;
import kingim.model.LoginLogVO;
import kingim.model.PageVO;
import kingim.service.AdminService;
import kingim.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 好友Service
 * @author jkxqj
 */
@Service
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLogVO> implements LoginLogService {

	@Autowired
	private LoginLogMapper loginLogMapper;

	@Override
	public Mapper<LoginLogVO> getMapper() {
		return loginLogMapper;
	}

	@Override
	public PageInfo<LoginLogVO> page(PageVO pageVO, LoginLogVO loginLogVO) {
		PageHelper.startPage(pageVO.getPageNo(), pageVO.getPageSize());
		return new PageInfo<LoginLogVO>(loginLogMapper.list(loginLogVO));
	}

}

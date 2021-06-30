package kingim.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import kingim.dao.WhiteIPMapper;
import kingim.exception.DescribeException;
import kingim.model.PageVO;
import kingim.model.WhiteIP;
import kingim.service.WhiteIPService;
import kingim.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 好友Service
 * @author jkxqj
 */
@Service
public class WhiteIPServiceImpl extends BaseServiceImpl<WhiteIP> implements WhiteIPService {

	@Autowired
	private WhiteIPMapper whiteIPMapper;

	@Override
	public Mapper<WhiteIP> getMapper() {
		return whiteIPMapper;
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-11 22:31
	 * @description: 分页条件查询IP白名单
	 * @param: [pageVO, whiteIP]
	 * @return: com.github.pagehelper.PageInfo<kingim.model.WhiteIP>
	 */
	@Override
	public PageInfo<WhiteIP> page(PageVO pageVO, WhiteIP whiteIP) {
		PageHelper.startPage(pageVO.getPageNo(), pageVO.getPageSize());
		return new PageInfo<WhiteIP>(whiteIPMapper.list(whiteIP));
	}

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-11 22:46
	 * @description: 批量删除IP白名单
	 * @param: [whiteIP]
	 * @return: int
	 */
	@Override
	public int deleteWhiteIP(WhiteIP whiteIP) {
		CommonUtil.paramEmptyVerify(String.valueOf(whiteIP.getIds()),"ID不能为空");
		return whiteIPMapper.deleteWhiteIP(whiteIP);
	}

	@Override
	public boolean isWhiteIP(WhiteIP whiteIP) {
		return whiteIPMapper.isWhiteIP(whiteIP);
	}

}

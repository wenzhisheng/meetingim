package kingim.dao;

import kingim.model.WhiteIP;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface WhiteIPMapper extends Mapper<WhiteIP> {

    List<WhiteIP> list(@Param("vo") WhiteIP whiteIP);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-11 22:47
     * @description: 批量删除IP白名单
     * @param: [whiteIP]
     * @return: int
     */
    int deleteWhiteIP(@Param("vo") WhiteIP whiteIP);

    boolean isWhiteIP(@Param("vo") WhiteIP whiteIP);

}
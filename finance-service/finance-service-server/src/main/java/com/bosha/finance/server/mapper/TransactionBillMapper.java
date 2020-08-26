package com.bosha.finance.server.mapper;

import com.bosha.finance.api.dto.request.QueryTransactionBillDto;
import com.bosha.finance.api.dto.request.QueryUserTransactionBillDto;
import com.bosha.finance.api.dto.response.IntoTransactionBillListDto;
import com.bosha.finance.api.dto.response.OutTransactionBillListDto;
import com.bosha.finance.api.dto.response.TransactionBillListDto;
import com.bosha.finance.api.dto.response.UserTransactionBillListDto;
import com.bosha.finance.api.entity.TransactionBill;

import java.util.List;

public interface TransactionBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TransactionBill record);

    int insertSelective(TransactionBill record);

    TransactionBill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransactionBill record);

    int updateByPrimaryKey(TransactionBill record);

    List<IntoTransactionBillListDto> findIntoTransactionBillPage(QueryTransactionBillDto queryTransactionBillDto);


    List<OutTransactionBillListDto> findOutTransactionBillPage(QueryTransactionBillDto queryTransactionBillDto);

    List<TransactionBillListDto> findTransactionBillPage(QueryTransactionBillDto queryTransactionBillDto);


    List<UserTransactionBillListDto> findUserBillByUserIdAndCoinId(QueryUserTransactionBillDto queryUserTransactionBillDto);
}
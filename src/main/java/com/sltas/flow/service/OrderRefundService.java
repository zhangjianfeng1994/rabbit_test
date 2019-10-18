package com.sltas.flow.service;

import com.sltas.flow.dto.RefundOrder;
import com.sltas.flow.rabbit.dto.NackCause;
import com.sltas.flow.util.api.CommonResponseHeader;

public interface OrderRefundService {

	CommonResponseHeader<?> refundExecute(RefundOrder refundOrder);

	Boolean refundExecuteNack(NackCause nc);

	void refundHandleMessage(RefundOrder order);

}

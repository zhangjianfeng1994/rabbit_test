package com.sltas.flow.api.server;

import com.sltas.flow.dto.RefundOrder;
import com.sltas.flow.util.api.CommonResponseHeader;

public interface OrderRefundHandler {

	/**
	 * <p>
	 * Title: refundExecute
	 * </p>
	 * <p>
	 * Description: 受理退款
	 * </p>
	 * @param @param refundOrder
	 * @param @return 
	 * @return CommonResponse<?>
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年10月30日 上午9:56:46 
	 */
	CommonResponseHeader<?> refundExecute(RefundOrder refundOrder);

}

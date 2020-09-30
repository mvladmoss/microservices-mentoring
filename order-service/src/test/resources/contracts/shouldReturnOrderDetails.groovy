package contracts

import org.springframework.cloud.contract.spec.Contract
Contract.make {
    description "should return order details"
    request{
        method GET()
        url("/orders/1")
    }
    response {
        body(
                orderIdentifier: '1',
                externalShopId: 1L,
                deliveryAddress: 'address',
                orderStatus: 'WAITING_VERIFICATION',
                totalAmount: 10.0
        )
        status 200
    }
}
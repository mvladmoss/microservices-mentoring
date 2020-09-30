Feature: Order full flow

  Scenario: create and verify order
    When incoming logistical order is generated
    And service receives request for order creation with generated incoming logistical order
    Then check that created order has the same property values as incoming logistical order
    And check order status is WAITING_VERIFICATION
    And check that ORDER_CREATED_EVENT was generated and send

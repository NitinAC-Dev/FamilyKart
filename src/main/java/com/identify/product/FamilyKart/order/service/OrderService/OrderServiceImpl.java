package com.identify.product.FamilyKart.order.service.OrderService;

import com.identify.product.FamilyKart.address.model.Address;
import com.identify.product.FamilyKart.address.repository.AddressRepository;
import com.identify.product.FamilyKart.cart.model.Cart;
import com.identify.product.FamilyKart.cart.model.CartItems;
import com.identify.product.FamilyKart.cart.repository.CartRepositoryJPA;
import com.identify.product.FamilyKart.cart.service.CartService;
import com.identify.product.FamilyKart.exceptionhandling.ApiException;
import com.identify.product.FamilyKart.exceptionhandling.ResourceNotFoundException;
import com.identify.product.FamilyKart.order.model.Order;
import com.identify.product.FamilyKart.order.model.OrderItems;
import com.identify.product.FamilyKart.order.payload.OrderDTO;
import com.identify.product.FamilyKart.order.payload.OrderItemDTO;
import com.identify.product.FamilyKart.order.repository.OrderItemRepository;
import com.identify.product.FamilyKart.order.repository.OrderRepository;
import com.identify.product.FamilyKart.payment.model.Payment;
import com.identify.product.FamilyKart.payment.repository.PaymentRepository;
import com.identify.product.FamilyKart.product.model.Product;
import com.identify.product.FamilyKart.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepositoryJPA cartRepositoryJPA;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDTO placeOrder(String loggedInUserEmail,
                               Long addressID,
                               String paymentMethod,
                               String paymentGatewayName,
                               String paymentGatewayPaymentID,
                               String paymentGatewayStatus,
                               String paymentGatewayResponseMessage) {
        //getting the user cart like user might have added product to cart so we will use this to add product to order
        Cart userCart = cartRepositoryJPA.findCartByEmail(loggedInUserEmail);
        if (userCart ==null)
        {
            throw new ResourceNotFoundException("Cart","User Email",loggedInUserEmail);
        }

        Address address = addressRepository.findById(addressID).orElseThrow(() -> new ResourceNotFoundException("Address", "Address ID", addressID));


        //create new order with payment info

        // first create order and then payment and then link payment to order and order to user and order to address and order to cart items
          Order newOrder = new Order();

                  newOrder.setEmail(loggedInUserEmail);
                  newOrder.setOrderDate(LocalDate.now());
                  newOrder.setOrderTotal(userCart.getTotalPrice());
                  newOrder.setOrderStatus("Accepted Order Placed");
                  newOrder.setAddress(address);
        Payment payment = new Payment(paymentMethod,
                paymentGatewayPaymentID,
                paymentGatewayStatus,
                paymentGatewayResponseMessage,

                 paymentGatewayName);
        payment.setOrder(newOrder);
        Payment paymentSaved = paymentRepository.save(payment);
        newOrder.setPayment(paymentSaved);

        Order saved = orderRepository.save(newOrder);

        //now we have to order items from cart and from cart items to order items and link them

        List<CartItems> cartItems = userCart.getCartItems();
        if (cartItems.isEmpty())
        {
            throw new ApiException("Cart is empty, cannot place order");
        }

         List<OrderItems> orderItems = new ArrayList<>();
        for(CartItems cartItem:cartItems)
        {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(saved);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderProductPrice(cartItem.getPrice());

            orderItems.add(orderItem);
        }

        List<OrderItems> savedOrderItems = orderItemRepository.saveAll(orderItems);
        // after this line  of code all the cart items will be transferedd to order items



        //update product stock after order placed in product table

        List<CartItems> orderProductToUpdateInProductTable = userCart.getCartItems();
        for(CartItems cartItem:orderProductToUpdateInProductTable)
        {
            int quantity = cartItem.getQuantity();
            Product product = cartItem.getProduct();
            product.setQuantity(product.getQuantity()-quantity);
            productRepository.save(product);
            //now delete the cart and cart items  after order placed
            cartService.deleteProductFromCart(userCart.getCartID(),cartItem.getProduct().getProductId());
            //or
            //cartRepositoryJPA.delete(userCart);

        }


        OrderDTO orderDTO = modelMapper.map(saved,OrderDTO.class);

        //inside orderDTO we have orderItesm so we have to add order items to orderDTO

        for (OrderItems item : savedOrderItems) {
            orderDTO.getOrderItems().add(modelMapper.map(item, OrderItemDTO.class));
        }
          //or
       /* List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for(OrderItems orderItem:savedOrderItems)
        {
            OrderItemDTO orderItemDTO = modelMapper.map(orderItem,OrderItemDTO.class);
            orderItemDTOS.add(orderItemDTO);
        }
        orderDTO.setOrderItems(orderItemDTOS);*/


orderDTO.setAddressID(addressID);
        // or

        //return order dto




        return orderDTO;
    }
}

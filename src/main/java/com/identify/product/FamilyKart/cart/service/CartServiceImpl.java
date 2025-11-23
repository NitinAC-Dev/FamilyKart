package com.identify.product.FamilyKart.cart.service;

import com.identify.product.FamilyKart.cart.model.Cart;
import com.identify.product.FamilyKart.cart.model.CartItems;
import com.identify.product.FamilyKart.cart.payload.CartResponseDTO;
import com.identify.product.FamilyKart.cart.repository.CartItemRepositoryJPA;
import com.identify.product.FamilyKart.cart.repository.CartRepositoryJPA;
import com.identify.product.FamilyKart.exceptionhandling.ApiException;
import com.identify.product.FamilyKart.exceptionhandling.ResourceNotFoundException;
import com.identify.product.FamilyKart.product.model.Product;
import com.identify.product.FamilyKart.product.payload.ProductRequestDTO;
import com.identify.product.FamilyKart.product.payload.ProductResponseDTO;
import com.identify.product.FamilyKart.product.repository.ProductRepository;
import com.identify.product.FamilyKart.security.jwt.payload.MessageResponse;
import com.identify.product.FamilyKart.utils.AuthUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

  private  CartRepositoryJPA cartRepositoryJPA;

  private   AuthUtils authUtil;

    private ProductRepository productrepository;

    private CartItemRepositoryJPA cartItemsRepositoryJPA;

    private ModelMapper modelMapper;


    public CartServiceImpl(CartRepositoryJPA cartRepositoryJPA, AuthUtils authUtil, ProductRepository productrepository, CartItemRepositoryJPA cartItemsRepositoryJPA, ModelMapper modelMapper) {
        this.cartRepositoryJPA = cartRepositoryJPA;
        this.authUtil = authUtil;
        this.productrepository = productrepository;
        this.cartItemsRepositoryJPA = cartItemsRepositoryJPA;
        this.modelMapper = modelMapper;
    }
    /**
     * @param productID
     * @param quantity
     * @return
     */
    @Override
    public CartResponseDTO addProductToCart(Long productID, Integer quantity) {

        Cart cart = createCart();

        //reterive product details from product service
        Product product=productrepository.findById(productID).orElseThrow(
                () -> new ResourceNotFoundException("Product","productID",productID));

        //perform Validation
        // 1. check if product is null which mean product is exist for not in the cart
        // 2. check if quantity is less than or equal to 0 or greater than available stock or avaliable stocks
        // the quantity what we are added do we have enough stock or not
        // 3. check if product is active or not

        //check if this product is already in the usercart or not
        CartItems cartItems=cartItemsRepositoryJPA.findByCartItemByCartIdAndProductId(cart.getCartID(),productID);
        if(cartItems!=null)
        {
           throw new ApiException("product "+product.getProductName()+" is already in the cart");
        }
        //5. check if quantity is less than or equal to 0 or greater than available stock or avaliable stocks
       //   5         <      1   true then throw exception  5< 6 false then go to next condition
        if(product.getQuantity()  < quantity)
        {
            throw new ApiException("product "+product.getProductName()+" has only "+product.getQuantity()+" items in stock you are trying to add "+quantity+" items");
        }
        if(product.getQuantity()==0)
        {
            throw new ApiException("product "+product.getProductName()+" is out of stock please wait for some days to get product back in stock");

        }

        // crete new cart items if condition is passed
        CartItems newCartItems=new CartItems();
                  newCartItems.setCart(cart);
                  newCartItems.setProduct(product);
                  newCartItems.setQuantity(quantity);
                   // newCartItems.setPrice(product.getSpecialPrice()*quantity);//get the price of discounted product
                  newCartItems.setPrice(product.getSpecialPrice());
       // newCartItems.setPrice(product.getPrice());
       // newCartItems.set
                    newCartItems.setDiscount(product.getDiscount());

                   // cartRepositoryJPA.save(newCartItems);

cartItemsRepositoryJPA.save(newCartItems);
        // update the product price once user as added the product to the cart
        // means user added 2 items of product A to the cart so now in the product quantity should be reduced by 2
       // product.setQuantity(product.getQuantity()-quantity);
        product.setQuantity(product.getQuantity());

//            this is when we added to cart let's say yesterday i have added 2 items to cart so the total price is 10 today i have added one more cart item so that price is 10 in total the cart total product price should be 20 and tomorrow if i add one more product the totalproductprice should increace by newly added product price
        cart.setTotalPrice(cart.getTotalPrice() +(product.getSpecialPrice() * quantity));



      cartRepositoryJPA.save(cart);
       // productrepository.save(product);
//return updated cart.
        CartResponseDTO cartDTOmap = modelMapper.map(cart, CartResponseDTO.class);

        //
        List<CartItems> cartItemsList = cart.getCartItems();
        List<ProductRequestDTO> productRequestDTOStream = cartItemsList.stream().map(
                item -> {
                    ProductRequestDTO productRequestDTO = modelMapper.map(item.getProduct(), ProductRequestDTO.class);
                    productRequestDTO.setQuantity(item.getQuantity());
                    return productRequestDTO;
                }
        ).toList();

        cartDTOmap.setListOfProducts(productRequestDTOStream);

        return cartDTOmap;
    }

    /**
     * @return
     */
    @Override
    public List<CartResponseDTO> getListOfCarts() {

        List<Cart> allCart =cartRepositoryJPA.findAll();
        if (allCart.isEmpty() || allCart.size()==0)
        {
            throw new ApiException("No carts are available");
        }

        List<CartResponseDTO> cartResponseDTOS = allCart.stream().map(cart -> {

                    CartResponseDTO eachCart = modelMapper.map(cart, CartResponseDTO.class);
                    List<ProductRequestDTO> productRequestDTOList =
                            cart.getCartItems().stream().map( cartItem->{
                                    ProductRequestDTO eachproductRequestDTO=    modelMapper.map(cartItem.getProduct(), ProductRequestDTO.class);
                                    eachproductRequestDTO.setQuantity(cartItem.getQuantity());

                    return eachproductRequestDTO;
                }).toList();
                    eachCart.setListOfProducts(productRequestDTOList);
                    return eachCart;
                }).collect(Collectors.toList());


        return cartResponseDTOS;

    }

    /**
     * @param userEmail
     * @param carID
     * @return
     */
    @Override
    public CartResponseDTO getUserCart(String userEmail, Long carID) {

        Cart userCart=cartRepositoryJPA.findCartByEmailAndCartID(userEmail,carID);

            if (userCart ==null)
            {
                throw new ResourceNotFoundException("Cart","userEmail "+userEmail+" and cartID ",carID);
            }
        for (CartItems eachcartitems : userCart.getCartItems()) {
            eachcartitems.getProduct().setQuantity(eachcartitems.getQuantity());
        }
        List<ProductRequestDTO> productRequestDTOList = userCart.getCartItems().stream()
                .map(productList ->

                    modelMapper.map(productList.getProduct(), ProductRequestDTO.class)
                ).toList();


        CartResponseDTO cartResponseDTO = modelMapper.map(userCart, CartResponseDTO.class);
            cartResponseDTO.setListOfProducts(productRequestDTOList);

        return cartResponseDTO;
    }

    /**
     * @param productID
     * @param quantityOperation
     * @return
     */
    @Transactional
    @Override
    public CartResponseDTO updateProductQuantityInCart(Long productID, Integer quantityOperation) {

        //first we have to get the cart of logged in user
        String email =authUtil.getLoggedInUserEmail();
        Long loggedInUserCartID = authUtil.getLoggedInUserCartID();
        Cart userCart=cartRepositoryJPA.findById(loggedInUserCartID).orElseThrow(() ->new ResourceNotFoundException("Cart","cartID",loggedInUserCartID));
        Product userProduct = productrepository.findById(productID).orElseThrow(() -> new ResourceNotFoundException("Product", "productID", productID));
        CartItems byCartItemByCartIdAndProductId = cartItemsRepositoryJPA.findByCartItemByCartIdAndProductId(loggedInUserCartID, productID);

        if (byCartItemByCartIdAndProductId==null)
        {
            throw new ApiException("product with productID "+productID+" is not in the cart to update quantity");
        }

        if(userProduct.getQuantity()  < quantityOperation)
        {
            throw new ApiException("product "+userProduct.getProductName()+" has only "+userProduct.getQuantity()+" items in stock you are trying to add "+quantityOperation+" items");
        }
        int newProductQuantity = byCartItemByCartIdAndProductId.getQuantity() + quantityOperation;
        if(newProductQuantity <0)
        {
            throw new ApiException("product "+userProduct.getProductName()+" is out of stock please wait for some days to get product back in stock");

        }
        if (newProductQuantity==0)
        {
            deleteProductFromCart(loggedInUserCartID,productID);
        }else {

            //after validation passed we have to get the cart items from the user cart
            // for updating products have to be in cartitems

            // so now if any exception didn't throw that we have a stock to update the product quantity in the cart

            byCartItemByCartIdAndProductId.setPrice(userProduct.getSpecialPrice());
            byCartItemByCartIdAndProductId.setDiscount(userProduct.getDiscount());
            byCartItemByCartIdAndProductId.setQuantity(
                    byCartItemByCartIdAndProductId.getQuantity() + quantityOperation
            );

            userCart.setTotalPrice(userCart.getTotalPrice() + (byCartItemByCartIdAndProductId.getPrice() * quantityOperation));
            Cart savedCart = cartRepositoryJPA.save(userCart);
        }
        CartItems savedCartItems = cartItemsRepositoryJPA.save(byCartItemByCartIdAndProductId);
        if(savedCartItems.getQuantity()==0)
        {
            cartItemsRepositoryJPA.deleteById(savedCartItems.getCartItemID());
        }

        CartResponseDTO cartResponseDTO = modelMapper.map(userCart, CartResponseDTO.class);

        List<CartItems> cartItems = userCart.getCartItems();
        Stream<ProductRequestDTO> productRequestDTOStream = cartItems.stream().map(eachcartItems ->
        {
            ProductRequestDTO productRequestDTO = modelMapper.map(eachcartItems.getProduct(), ProductRequestDTO.class);
            productRequestDTO.setQuantity(eachcartItems.getQuantity());
            return productRequestDTO;
        });

        //check if this way of for loop works  comment the line no 233 to 239 and uncomment below code
//        ProductRequestDTO productRequestDTO;
//        List<ProductRequestDTO> productRequestDTOList = null;
//        for (CartItems eachcartItems :cartItems)
//        {
//             productRequestDTO = modelMapper.map(eachcartItems.getProduct(), ProductRequestDTO.class);
//            productRequestDTO.setQuantity(eachcartItems.getQuantity());
//             productRequestDTOList = cartResponseDTO.getListOfProducts();
//
//        }
//
//        cartResponseDTO.setListOfProducts(productRequestDTOList);
        cartResponseDTO.setListOfProducts(productRequestDTOStream.toList());
        return cartResponseDTO;
    }

    /**
     * @param cartID
     * @param productID
     * @return
     */
    @Transactional
    @Override
    public MessageResponse deleteProductFromCart(Long cartID, Long productID) {

        Cart cart = cartRepositoryJPA.findById(cartID).orElseThrow(() -> new ResourceNotFoundException("Cart", "cartID", cartID));

        CartItems byCartItemByCartIdAndProductId = cartItemsRepositoryJPA.findByCartItemByCartIdAndProductId(cartID, productID);
        if (byCartItemByCartIdAndProductId == null) {
            throw new ResourceNotFoundException("CartItem", "productID " + productID + " in cartID ", cartID);
        }

        // before deleting we have to update the total price of the cart
        cart.setTotalPrice(cart.getTotalPrice() -(byCartItemByCartIdAndProductId.getPrice()* byCartItemByCartIdAndProductId.getQuantity()));

      cartItemsRepositoryJPA.deleteCartItemByCartIDAndProductID(cartID,productID);

        return new MessageResponse("product with prouct Name "
                +byCartItemByCartIdAndProductId.getProduct().getProductName()
                +" is removed from the cart successfully");
    }

    private Cart createCart() {
        Cart userCart=cartRepositoryJPA.findCartByEmail(authUtil.getLoggedInUserEmail());

        if(userCart!=null)
        {
            return userCart;
        }

        Cart cart=new Cart();
        cart.setTotalPrice(0.0);
        cart.setUser(authUtil.getLoggedInUser());
        Cart newCartToSave=cartRepositoryJPA.save(cart);
        return newCartToSave;
    }
}

package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;


    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 주문_잘되는가() throws Exception {

        Member member = createMember();
        Book book = createBook("숨", "테드창짱", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId() , book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals( OrderStatus.ORDER , getOrder.getStatus());
        assertEquals( 1, getOrder.getOrderItems().size());
        assertEquals(10000 * orderCount , getOrder.getTotalPrice());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과_에러가터지는가() throws Exception {
        Member member = createMember();
        Book book = createBook("아"  , "아" , 10000, 10);

        int orderCount = 11;

        orderService.order(member.getId(), book.getId(), orderCount);

        fail("예외 터지렴");
    }

    @Test
    public void 재고수량_원복되는가() throws Exception {
        Member member = createMember();
        Book item = createBook("아아아아" , "아아아아아아",10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId() , item.getId() , orderCount);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL , getOrder.getStatus());
        assertEquals(10 , item.getStockQuantity());
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("semi");
        member.setAddress(new Address("극락" , "천국" , "수세미"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, String author, int price, int stockQuantity){
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;

    }
}
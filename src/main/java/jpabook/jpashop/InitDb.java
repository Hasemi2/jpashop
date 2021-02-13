package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1(){
            Member member = createMember("userA", "극락", "1", "11");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 200);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1 , 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2 , 20000, 1);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member , delivery, orderItem1 , orderItem2);

            em.persist(order);

        }

        public void dbInit2(){
            Member member = createMember("userB", "극락2", "2", "222");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 10000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1 , 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2 , 10000, 2);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member , delivery, orderItem1 , orderItem2);

            em.persist(order);

        }

        private Book createBook(String name, int price, int quantity) {
            Book book2 = new Book();
            book2.setName(name);
            book2.setPrice(price);
            book2.setStockQuantity(quantity);
            return book2;
        }

        private Member createMember(String user, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(user);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}

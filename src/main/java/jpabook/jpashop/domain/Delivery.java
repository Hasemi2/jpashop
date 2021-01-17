package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "DELIVERY")
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    /**
     * 기본은 ORDINAl임
     * 숫자로 관리 되기 때문에 추후 변경점 최소화를 위해 String으로 하렴
     */
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP
}

package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;


@Getter
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    /**
     * JPA 스펙상 엔티티나 임베디드 타입은
     * 기본 생성자를 public 또는 protected로 설정해야한다.
     * JPA 구현 라이브러리가 객체를 생성 할 때 리플렉션 같은
     * 기술을 사용할 수 있도록 지원 해야 함
     */
    protected Address() {

    }

    /**
     * 값 타입은 생성할 때만 세팅,
     * 변경 점이 없어야 함 (Setter XX)
     * 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스로 만든다
     *
     * @param city
     * @param street
     * @param zipcode
     */
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}

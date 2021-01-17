package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String name;

    /**
     * 관계형 DB는
     * ManyToMany -> 중간테이블
     * <p>
     * CATEGORY --------------- CATEGORY_ITEM -------- ITEM
     * CATEGORY_ID(PK) ------- CATEGORY_ID(FK)-------- ITEM_ID(PK)
     * -------------------------- ITEM_ID(FK) --------------------
     * 실무에선 지양함
     * 중간 테이블에 컬럼을 추가 할수가 없고, 세밀하게 쿼리를 실행하기가 어렵대
     * 중간 엔티티를 생성하여 일대다, 대다일로 풀어내서 사용하는 것이 좋음
     */
    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    //양방향
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}

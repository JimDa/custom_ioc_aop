package org.arch_learn.ioc_aop_test.po;

/**
 * @author 应癫
 */
public class Account {

    private String card_no;
    private String name;
    private Integer money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    @Override
    public String toString() {
        return "Account{" +
                "card_no='" + card_no + '\'' +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}

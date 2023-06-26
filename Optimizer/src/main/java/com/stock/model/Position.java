package com.stock.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.AbstractPersistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

// @NamedQuery(name = "Position.findAll", query = "from position p where user = 1")

@Entity(name = "position")
@Table(name = "position", uniqueConstraints = { @UniqueConstraint(columnNames = "ID") })
public class Position extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 589693210398378079L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "USER")
    private String user;
    @Column(name = "SYMBOL")
    private String symbol;
    @Column(name = "SHARES")
    private int shares;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "COMMISSION")
    private BigDecimal commission;
    @Column(name = "BUY_DATE")
    private LocalDateTime buyDate;
    @Column(name = "SOLD_DATE")
    private LocalDateTime soldDate;
    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;

    public Position() {
	super();
    }

    public Position(Long id, String user, String symbol, int shares, BigDecimal price, BigDecimal commission,
	    LocalDateTime buyDate, LocalDateTime soldDate, LocalDateTime updatedOn) {
	super();
	this.id = id;
	this.user = user;
	this.symbol = symbol;
	this.shares = shares;
	this.price = price;
	this.commission = commission;
	this.buyDate = buyDate;
	this.soldDate = soldDate;
	this.updatedOn = updatedOn;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getUser() {
	return user;
    }

    public void setUser(String user) {
	this.user = user;
    }

    public String getSymbol() {
	return symbol;
    }

    public void setSymbol(String symbol) {
	this.symbol = symbol;
    }

    public int getShares() {
	return shares;
    }

    public void setShares(int shares) {
	this.shares = shares;
    }

    public BigDecimal getPrice() {
	return price;
    }

    public void setPrice(BigDecimal price) {
	this.price = price;
    }

    public BigDecimal getCommission() {
	return commission;
    }

    public void setCommission(BigDecimal commission) {
	this.commission = commission;
    }

    public LocalDateTime getBuyDate() {
	return buyDate;
    }

    public void setBuyDate(LocalDateTime buyDate) {
	this.buyDate = buyDate;
    }

    public LocalDateTime getSoldDate() {
	return soldDate;
    }

    public void setSoldDate(LocalDateTime soldDate) {
	this.soldDate = soldDate;
    }

    public LocalDateTime getUpdatedOn() {
	return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
	this.updatedOn = updatedOn;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    @Override
    public String toString() {
	return "Position [id=" + id + ", user=" + user + ", symbol=" + symbol + ", shares=" + shares + ", price="
		+ price + ", commission=" + commission + ", buyDate=" + buyDate + ", soldDate=" + soldDate
		+ ", updatedOn=" + updatedOn + "]";
    }
}

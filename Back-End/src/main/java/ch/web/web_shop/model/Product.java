package ch.web.web_shop.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "title")
	@NotEmpty(message = "Titel is mandatory")
	@NotNull(message = "Titel cannot be null")
	private String title;

	@Column(name = "description")
	@Size(min = 1, max = 50, message = "Description must be between 1 and 50 characters")
	@NotNull(message = "Description cannot be null")
	private String description;

	@Column(name = "content")
	private String content;

	@Column(name = "price")
	@Min(value = 0, message = "Age should not be less than 0")
	@NotNull(message = "price cannot be null")
	private int price;

	@Column(name = "stock")
	@Min(value = 1, message = "Age should not be less than 1")
	@NotNull(message = "Stock cannot be null")
	private int stock;

	@Column(name = "src")
	private String src;

	@Column(name = "published")
	private boolean published;

	// category 1:n
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	public Product() {

	}

	public Product(String title, String description, String content, int price, int stock, String src,
			boolean published, Category category) {
		this.title = title;
		this.description = description;
		this.content = content;
		this.price = price;
		this.stock = stock;
		this.src = src;
		this.published = published;
		this.category = category;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
	}

}

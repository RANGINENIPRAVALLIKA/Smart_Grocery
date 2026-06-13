# Smart Grocery – Database Schema

The application uses **Aiven MySQL cloud database** with JPA. Tables are created/updated automatically from entities. Below is the logical schema.

## Tables

### users
| Column    | Type         | Constraints        |
|-----------|--------------|--------------------|
| id        | BIGINT       | PK, AUTO_INCREMENT |
| name      | VARCHAR(255) | NOT NULL           |
| email     | VARCHAR(255) | NOT NULL, UNIQUE   |
| password  | VARCHAR(255) | NOT NULL           |
| phone     | VARCHAR(255) |                    |
| admin     | BOOLEAN      | NOT NULL, default false |

### products
| Column     | Type         | Constraints        |
|------------|--------------|--------------------|
| id         | BIGINT       | PK, AUTO_INCREMENT  |
| name       | VARCHAR(255) | NOT NULL           |
| description| VARCHAR(255) |                   |
| price      | DOUBLE       | NOT NULL           |
| category   | VARCHAR(255) | NOT NULL           |
| image_url  | VARCHAR(255) |                   |
| stock      | INT          | NOT NULL, default 0 |

### carts
| Column  | Type   | Constraints       |
|---------|--------|--------------------|
| id      | BIGINT  | PK, AUTO_INCREMENT |
| user_id | BIGINT  | NOT NULL, UNIQUE (FK → users.id) |

### cart_items
| Column     | Type   | Constraints      |
|------------|--------|------------------|
| id         | BIGINT | PK, AUTO_INCREMENT |
| cart_id    | BIGINT | NOT NULL (FK → carts.id) |
| product_id | BIGINT | NOT NULL (FK → products.id) |
| quantity   | INT    | NOT NULL, default 1 |

### wishlists
| Column  | Type   | Constraints       |
|---------|--------|--------------------|
| id      | BIGINT | PK, AUTO_INCREMENT |
| user_id | BIGINT | NOT NULL, UNIQUE (FK → users.id) |

### wishlist_items
| Column       | Type   | Constraints      |
|--------------|--------|------------------|
| id           | BIGINT | PK, AUTO_INCREMENT |
| wishlist_id  | BIGINT | NOT NULL (FK → wishlists.id) |
| product_id   | BIGINT | NOT NULL (FK → products.id) |

### orders
| Column          | Type        | Constraints      |
|-----------------|-------------|-------------------|
| id              | BIGINT      | PK, AUTO_INCREMENT |
| user_id         | BIGINT      | NOT NULL (FK → users.id) |
| delivery_address| VARCHAR(255)|                   |
| payment_method  | VARCHAR(255)|                   |
| status          | VARCHAR(255)|                   |
| total_amount    | DOUBLE      | NOT NULL         |
| created_at      | TIMESTAMP   | NOT NULL         |

### order_items
| Column        | Type   | Constraints      |
|---------------|--------|------------------|
| id            | BIGINT | PK, AUTO_INCREMENT |
| order_id      | BIGINT | NOT NULL (FK → orders.id) |
| product_id    | BIGINT | NOT NULL (FK → products.id) |
| quantity      | INT    | NOT NULL        |
| price_at_order| DOUBLE | NOT NULL        |

## Relationships

- **User** 1:1 **Cart**, 1:1 **Wishlist**, 1:N **Order**
- **Cart** N:1 **User**, 1:N **CartItem**
- **CartItem** N:1 **Cart**, N:1 **Product**
- **Wishlist** N:1 **User**, 1:N **WishlistItem**
- **WishlistItem** N:1 **Wishlist**, N:1 **Product**
- **Order** N:1 **User**, 1:N **OrderItem**
- **OrderItem** N:1 **Order**, N:1 **Product**

## Sample Data

On application startup, `DataLoader` inserts **150+ products** across categories: Fruits, Vegetables, Dairy, Snacks, Beverages, Staples. No default users; register via `/api/auth/register` or the frontend Register page.

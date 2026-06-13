# Smart Grocery System

A full-stack online grocery shopping platform built with **Spring Boot** (backend) and **React + Vite** (frontend). Suitable as a portfolio project for B.Tech students.

## Features

- **Authentication**: Login and registration (email, password, name, phone)
- **Home / Dashboard**: Search bar, categories (Fruits, Vegetables, Dairy, Snacks, Beverages, Staples), product grid
- **Product Catalog**: 150+ grocery products with images, price, category, description, stock
- **Product actions**: Add to cart, add to wishlist, quantity controls, view details
- **Wishlist**: Add/remove items, dedicated wishlist page
- **Shopping Cart**: List, quantity +/- , remove, total calculation
- **Checkout**: Delivery address, order summary, payment method (Cash on Delivery, UPI, Card simulation)
- **Order Success**: Confirmation page after checkout
- **Profile**: View/update profile, view previous orders
- **Orders**: List and detail pages for order history
- **Admin APIs**: CRUD products, list users, list orders (backend only; use Postman or similar)

## Tech Stack

- **Backend**: Java 17, Spring Boot 3.2, Spring Security (JWT), Spring Data JPA, Aiven MySQL (cloud DB)
- **Frontend**: React 19, Vite 8, React Router, Axios

## Project Structure

```
Smart_Grocery/
├── backend/                 # Spring Boot API
│   ├── src/main/java/com/smartgrocery/
│   │   ├── config/          # Security, CORS, DataLoader
│   │   ├── controller/       # REST controllers
│   │   ├── dto/              # Request/response DTOs
│   │   ├── exception/        # Global exception handler
│   │   ├── model/            # JPA entities
│   │   ├── repository/       # JPA repositories
│   │   ├── security/         # JWT, filter, UserDetails
│   │   └── service/           # Business logic
│   └── src/main/resources/
│       └── application.properties
├── frontend/                 # React Vite app
│   ├── src/
│   │   ├── api/              # Axios instance & API calls
│   │   ├── components/       # Navbar, ProductCard, ProtectedRoute
│   │   ├── context/          # AuthContext
│   │   └── pages/            # Login, Register, Home, Cart, etc.
│   └── package.json
└── README.md
```

## How to Run

### Backend

1. Install Java 17+ and Maven.
2. From project root:
   ```bash
   cd backend
   mvn spring-boot:run
   ```
3. API runs at **http://localhost:8080** using Aiven MySQL.

### Frontend

1. Install Node.js 18+.
2. From project root:
   ```bash
   cd frontend
   npm install
   npm run dev
   ```
3. App runs at **http://localhost:5173**. Open this URL; you will see the **Login** page first. Register a new account or log in, then you are redirected to the Home page.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register (name, email, password, phone) |
| POST | `/api/auth/login` | Login (email, password) → JWT |
| GET | `/api/products` | List products (optional: `search`, `category`) |
| GET | `/api/products/categories` | List categories |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/cart` | Get cart (auth) |
| POST | `/api/cart/items?productId=&quantity=` | Add to cart (auth) |
| PUT | `/api/cart/items/{productId}?quantity=` | Update quantity (auth) |
| DELETE | `/api/cart/items/{productId}` | Remove from cart (auth) |
| GET | `/api/wishlist` | Get wishlist (auth) |
| POST | `/api/wishlist/items?productId=` | Add to wishlist (auth) |
| DELETE | `/api/wishlist/items/{productId}` | Remove from wishlist (auth) |
| POST | `/api/orders/checkout` | Checkout (body: deliveryAddress, paymentMethod) (auth) |
| GET | `/api/orders` | My orders (auth) |
| GET | `/api/orders/{id}` | Order detail (auth) |
| GET | `/api/users/profile` | Get profile (auth) |
| PUT | `/api/users/profile` | Update profile (auth) |
| GET | `/api/admin/products` | List all products (admin) |
| POST | `/api/admin/products` | Add product (admin) |
| PUT | `/api/admin/products/{id}` | Update product (admin) |
| DELETE | `/api/admin/products/{id}` | Delete product (admin) |
| GET | `/api/admin/users` | List users (admin) |
| GET | `/api/admin/orders` | List all orders (admin) |

All authenticated requests must include header: `Authorization: Bearer <token>`.

## Database Schema (MySQL / JPA)

- **users**: id, name, email, password, phone, admin
- **products**: id, name, description, price, category, image_url, stock
- **carts**: id, user_id
- **cart_items**: id, cart_id, product_id, quantity
- **wishlists**: id, user_id
- **wishlist_items**: id, wishlist_id, product_id
- **orders**: id, user_id, delivery_address, payment_method, status, total_amount, created_at
- **order_items**: id, order_id, product_id, quantity, price_at_order

Schema is created automatically by Spring Data JPA (`ddl-auto=update`). Sample data: 150+ products are loaded on startup via `DataLoader`.

## Default Flow

1. Open http://localhost:5173 → **Login** page.
2. Click **Register** → enter Name, Email, Password, Phone → account created and logged in → redirect to **Home**.
3. Search or filter by category, add products to cart/wishlist, open product for details.
4. Go to **Cart** → **Proceed to Checkout** → enter address, choose payment → **Place Order** → **Order Successful**.
5. **Profile** → view/edit details and **Previous orders**.

## License

MIT. Use freely for learning and portfolio.

import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Navbar from '../components/Navbar';
import { cartAPI } from '../api/axios';
import './Cart.css';

export default function Cart() {
  const [cart, setCart] = useState({ items: [], totalPrice: 0 });
  const [loading, setLoading] = useState(true);
  const [updating, setUpdating] = useState(null);

  const loadCart = () => {
    cartAPI.get().then((res) => setCart(res.data)).catch(() => setCart({ items: [], totalPrice: 0 })).finally(() => setLoading(false));
  };

  useEffect(() => {
    loadCart();
  }, []);

  const updateQty = async (productId, quantity) => {
    if (quantity < 1) return;
    setUpdating(productId);
    try {
      await cartAPI.updateQuantity(productId, quantity);
      loadCart();
    } finally {
      setUpdating(null);
    }
  };

  const remove = async (productId) => {
    setUpdating(productId);
    try {
      await cartAPI.removeItem(productId);
      loadCart();
    } finally {
      setUpdating(null);
    }
  };

  const cartCount = cart.items?.reduce((s, i) => s + (i.quantity || 0), 0) || 0;

  return (
    <div className="home-page">
      <Navbar cartCount={cartCount} />
      <main className="cart-main">
        <h1>Shopping Cart</h1>
        {loading ? (
          <div className="loading-state"><div className="spinner" /> Loading cart...</div>
        ) : !cart.items?.length ? (
          <div className="cart-empty">
            <p>Your cart is empty.</p>
            <Link to="/" className="cart-cta">Browse products</Link>
          </div>
        ) : (
          <>
            <div className="cart-list">
              {cart.items.map((item) => (
                <div key={item.id} className="cart-item">
                  <div className="cart-item-image">
                    <img src={item.imageUrl || 'https://placehold.co/100x100/e2e8f0/64748b?text=Product'} alt={item.productName} />
                  </div>
                  <div className="cart-item-details">
                    <Link to={`/product/${item.productId}`} className="cart-item-name">{item.productName}</Link>
                    <p className="cart-item-price">₹{item.price?.toFixed(2)} each</p>
                    <div className="cart-item-actions">
                      <div className="quantity-controls">
                        <button type="button" onClick={() => updateQty(item.productId, item.quantity - 1)} disabled={updating === item.productId}>−</button>
                        <span>{item.quantity}</span>
                        <button type="button" onClick={() => updateQty(item.productId, item.quantity + 1)} disabled={updating === item.productId || item.quantity >= (item.stock || 99)}>+</button>
                      </div>
                      <button type="button" className="remove-btn" onClick={() => remove(item.productId)} disabled={updating === item.productId}>Remove</button>
                    </div>
                  </div>
                  <div className="cart-item-total">₹{(item.price * item.quantity).toFixed(2)}</div>
                </div>
              ))}
            </div>
            <div className="cart-summary">
              <p className="cart-total"><strong>Total: ₹{cart.totalPrice?.toFixed(2)}</strong></p>
              <Link to="/checkout" className="checkout-btn">Proceed to Checkout</Link>
            </div>
          </>
        )}
      </main>
    </div>
  );
}

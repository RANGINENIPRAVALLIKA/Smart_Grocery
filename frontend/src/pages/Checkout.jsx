import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import { cartAPI, ordersAPI } from '../api/axios';
import './Checkout.css';

const PAYMENT_OPTIONS = [
  { id: 'CASH_ON_DELIVERY', label: 'Cash on Delivery' },
  { id: 'UPI', label: 'UPI' },
  { id: 'CARD', label: 'Card (simulation)' },
];

export default function Checkout() {
  const navigate = useNavigate();
  const [cart, setCart] = useState({ items: [], totalPrice: 0 });
  const [address, setAddress] = useState('');
  const [paymentMethod, setPaymentMethod] = useState('CASH_ON_DELIVERY');
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    cartAPI.get().then((res) => setCart(res.data)).catch(() => setCart({ items: [], totalPrice: 0 })).finally(() => setLoading(false));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    if (!address.trim()) {
      setError('Please enter delivery address');
      return;
    }
    setSubmitting(true);
    try {
      const { data } = await ordersAPI.checkout({
        deliveryAddress: address.trim(),
        paymentMethod,
      });
      navigate('/order-success', { state: { order: data } });
    } catch (err) {
      setError(err.response?.data?.message || 'Checkout failed');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="home-page">
        <Navbar />
        <div className="page-loading"><div className="spinner" /> Loading...</div>
      </div>
    );
  }

  if (!cart.items?.length) {
    return (
      <div className="home-page">
        <Navbar />
        <main className="checkout-main">
          <p className="cart-empty">Your cart is empty. <button type="button" onClick={() => navigate('/')}>Go to shop</button></p>
        </main>
      </div>
    );
  }

  return (
    <div className="home-page">
      <Navbar />
      <main className="checkout-main">
        <h1>Checkout</h1>
        <form onSubmit={handleSubmit} className="checkout-form">
          {error && <div className="checkout-error">{error}</div>}
          <div className="checkout-section">
            <label>Delivery Address</label>
            <textarea
              placeholder="Enter full address..."
              value={address}
              onChange={(e) => setAddress(e.target.value)}
              rows={3}
              required
            />
          </div>
          <div className="checkout-section">
            <label>Payment Method</label>
            <div className="payment-options">
              {PAYMENT_OPTIONS.map((opt) => (
                <label key={opt.id} className="payment-option">
                  <input
                    type="radio"
                    name="payment"
                    value={opt.id}
                    checked={paymentMethod === opt.id}
                    onChange={() => setPaymentMethod(opt.id)}
                  />
                  <span>{opt.label}</span>
                </label>
              ))}
            </div>
          </div>
          <div className="checkout-summary">
            <h3>Order Summary</h3>
            <ul>
              {cart.items.map((i) => (
                <li key={i.id}>{i.productName} × {i.quantity} — ₹{(i.price * i.quantity).toFixed(2)}</li>
              ))}
            </ul>
            <p className="order-total">Total: ₹{cart.totalPrice?.toFixed(2)}</p>
          </div>
          <button type="submit" className="place-order-btn" disabled={submitting}>
            {submitting ? 'Placing order...' : 'Place Order'}
          </button>
        </form>
      </main>
    </div>
  );
}

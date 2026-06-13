import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Navbar from '../components/Navbar';
import { wishlistAPI } from '../api/axios';
import './Wishlist.css';

export default function Wishlist() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  const load = () => {
    wishlistAPI.get().then((res) => setItems(res.data || [])).catch(() => setItems([])).finally(() => setLoading(false));
  };

  useEffect(() => {
    load();
  }, []);

  const remove = async (productId) => {
    try {
      await wishlistAPI.remove(productId);
      setItems((prev) => prev.filter((i) => i.productId !== productId));
    } catch (_) {}
  };

  return (
    <div className="home-page">
      <Navbar />
      <main className="wishlist-main">
        <h1>Wishlist</h1>
        {loading ? (
          <div className="loading-state"><div className="spinner" /> Loading...</div>
        ) : items.length === 0 ? (
          <div className="wishlist-empty">
            <p>No items in your wishlist.</p>
            <Link to="/" className="cart-cta">Browse products</Link>
          </div>
        ) : (
          <div className="wishlist-grid">
            {items.map((item) => (
              <div key={item.id} className="wishlist-card">
                <Link to={`/product/${item.productId}`} className="wishlist-card-link">
                  <div className="wishlist-card-image">
                    <img src={item.imageUrl || 'https://placehold.co/400x300/e2e8f0/64748b?text=Product'} alt={item.productName} />
                  </div>
                  <h3>{item.productName}</h3>
                  <p className="wishlist-price">₹{item.price?.toFixed(2)}</p>
                </Link>
                <div className="wishlist-card-actions">
                  <button type="button" className="remove-wish" onClick={() => remove(item.productId)}>Remove</button>
                  <Link to={`/product/${item.productId}`} className="view-btn">View</Link>
                </div>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
}

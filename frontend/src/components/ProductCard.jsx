import { Link } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { cartAPI, wishlistAPI } from '../api/axios';
import { useAuth } from '../context/AuthContext';
import './ProductCard.css';

export default function ProductCard({ product, onCartUpdate, onWishlistUpdate }) {
  const { isAuth } = useAuth();
  const [quantity, setQuantity] = useState(1);
  const [inWishlist, setInWishlist] = useState(false);
  const [addingCart, setAddingCart] = useState(false);
  const [cartMessage, setCartMessage] = useState('');

  useEffect(() => {
    if (!isAuth || !product?.id) return;
    wishlistAPI.check(product.id).then((r) => setInWishlist(r.data === true)).catch(() => setInWishlist(false));
  }, [isAuth, product?.id]);

  const handleAddToCart = async () => {
    if (!isAuth) return;
    setAddingCart(true);
    setCartMessage('');
    try {
      await cartAPI.addItem(Number(product.id), quantity);
      onCartUpdate?.();
      setCartMessage('Added to cart');
      setTimeout(() => setCartMessage(''), 2000);
    } catch (err) {
      const status = err.response?.status;
      const data = err.response?.data;
      let msg = data?.message || data?.error || 'Failed to add to cart';
      if (status === 401 || status === 403) msg = 'Please log in again';
      setCartMessage(msg);
    } finally {
      setAddingCart(false);
    }
  };

  const handleWishlist = async () => {
    if (!isAuth) return;
    try {
      if (inWishlist) {
        await wishlistAPI.remove(product.id);
        setInWishlist(false);
      } else {
        await wishlistAPI.add(product.id);
        setInWishlist(true);
      }
      onWishlistUpdate?.();
    } catch (_) {}
  };


  return (
    <article className="product-card">
      <Link to={`/product/${product.id}`} className="product-card-link">
        <div className="product-card-copy">
          <h3 className="product-card-name">{product.name}</h3>
          <p className="product-card-category">{product.category || 'Fresh item'}</p>
          <p className="product-card-price">₹{product.price?.toFixed(2)}</p>
        </div>
      </Link>
      <div className="product-card-actions">
        <div className="quantity-controls">
          <button type="button" onClick={() => setQuantity((q) => Math.max(1, q - 1))} aria-label="Decrease">−</button>
          <span>{quantity}</span>
          <button type="button" onClick={() => setQuantity((q) => Math.min(product.stock || 99, q + 1))} aria-label="Increase">+</button>
        </div>
        <button
          type="button"
          className={`wishlist-btn ${inWishlist ? 'active' : ''}`}
          onClick={handleWishlist}
          title={inWishlist ? 'Remove from wishlist' : 'Add to wishlist'}
          disabled={!isAuth}
        >
          <svg width="20" height="20" viewBox="0 0 24 24" fill={inWishlist ? 'currentColor' : 'none'} stroke="currentColor" strokeWidth="2">
            <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z" />
          </svg>
        </button>
        <button
          type="button"
          className="add-cart-btn"
          onClick={handleAddToCart}
          disabled={!product.stock || addingCart || !isAuth}
        >
          {addingCart ? 'Adding...' : 'Add to Cart'}
        </button>
        {cartMessage && (
          <p className={`product-card-message ${cartMessage.startsWith('Added') ? 'success' : 'error'}`}>
            {cartMessage}
          </p>
        )}
      </div>
    </article>
  );
}

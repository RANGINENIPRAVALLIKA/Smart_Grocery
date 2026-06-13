import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import { productsAPI, cartAPI, wishlistAPI } from '../api/axios';
import { useAuth } from '../context/AuthContext';
import './ProductDetail.css';

export default function ProductDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { isAuth } = useAuth();
  const [product, setProduct] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const [inWishlist, setInWishlist] = useState(false);
  const [addingCart, setAddingCart] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    productsAPI.getById(id).then((res) => setProduct(res.data)).catch(() => setProduct(null)).finally(() => setLoading(false));
  }, [id]);

  useEffect(() => {
    if (!isAuth || !product?.id) return;
    wishlistAPI.check(product.id).then((r) => setInWishlist(r.data === true)).catch(() => setInWishlist(false));
  }, [isAuth, product?.id]);

  const handleWishlist = async () => {
    if (!isAuth) return;
    try {
      if (inWishlist) await wishlistAPI.remove(product.id);
      else await wishlistAPI.add(product.id);
      setInWishlist(!inWishlist);
    } catch (_) {}
  };

  const handleAddToCart = async () => {
    if (!isAuth) return;
    setAddingCart(true);
    try {
      await cartAPI.addItem(Number(product.id), quantity);
      navigate('/cart');
    } catch (_) {
      setAddingCart(false);
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
  if (!product) {
    return (
      <div className="home-page">
        <Navbar />
        <div className="detail-not-found">Product not found.</div>
      </div>
    );
  }

  return (
    <div className="home-page">
      <Navbar />
      <main className="detail-main">
        <div className="detail-card detail-card-no-image">
          <div className="detail-info">
            <p className="detail-category">{product.category}</p>
            <h1>{product.name}</h1>
            <p className="detail-price">₹{product.price?.toFixed(2)}</p>
            <p className="detail-desc">{product.description}</p>
            <p className="detail-stock">Stock: {product.stock} available</p>
            <div className="detail-actions">
              <div className="quantity-controls">
                <button type="button" onClick={() => setQuantity((q) => Math.max(1, q - 1))}>−</button>
                <span>{quantity}</span>
                <button type="button" onClick={() => setQuantity((q) => Math.min(product.stock || 99, q + 1))}>+</button>
              </div>
              <button type="button" className={`wishlist-btn ${inWishlist ? 'active' : ''}`} onClick={handleWishlist} disabled={!isAuth}>
                {inWishlist ? 'In Wishlist' : 'Add to Wishlist'}
              </button>
              <button type="button" className="add-cart-btn" onClick={handleAddToCart} disabled={!product.stock || addingCart || !isAuth}>
                {addingCart ? 'Adding...' : 'Add to Cart'}
              </button>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}

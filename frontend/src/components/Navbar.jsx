import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useState, useEffect } from 'react';
import { cartAPI } from '../api/axios';
import './Navbar.css';

export default function Navbar({ searchQuery, onSearchChange, onSearchSubmit, cartCount }) {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [count, setCount] = useState(cartCount ?? 0);

  useEffect(() => {
    if (user && cartCount === undefined) {
      cartAPI.get().then((res) => {
        const total = res.data?.items?.reduce((s, i) => s + (i.quantity || 0), 0) || 0;
        setCount(total);
      }).catch(() => setCount(0));
    } else if (cartCount !== undefined) {
      setCount(cartCount);
    }
  }, [user, cartCount]);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="nav-inner">
        <Link to="/" className="nav-logo">Smart Grocery</Link>
        <div className="nav-search">
          <input
            type="search"
            placeholder="Search products..."
            value={searchQuery || ''}
            onChange={(e) => onSearchChange?.(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === 'Enter') {
                e.preventDefault();
                onSearchSubmit?.();
              }
            }}
          />
        </div>
        <div className="nav-actions">
          <Link to="/" className="nav-icon" title="Home">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <path d="M3 9.5 12 3l9 6.5V21a1 1 0 0 1-1 1h-5v-7H9v7H4a1 1 0 0 1-1-1V9.5z" />
            </svg>
          </Link>
          <Link to="/wishlist" className="nav-icon" title="Wishlist">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z" />
            </svg>
          </Link>
          <Link to="/cart" className="nav-icon nav-cart" title="Cart">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <circle cx="9" cy="21" r="1" /><circle cx="20" cy="21" r="1" />
              <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6" />
            </svg>
            {count > 0 && <span className="cart-badge">{count}</span>}
          </Link>
          <div className="nav-profile">
            <button className="nav-icon profile-btn" title="Profile" onClick={() => navigate('/profile')}>
              <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
                <circle cx="12" cy="7" r="4" />
              </svg>
            </button>
            {user?.admin && (
              <button className="nav-logout" onClick={() => navigate('/admin')}>Admin</button>
            )}
            <span className="nav-user-name">{user?.name}</span>
            <button className="nav-logout" onClick={handleLogout}>Logout</button>
          </div>
        </div>
      </div>
    </nav>
  );
}

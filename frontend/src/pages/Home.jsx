import { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import ProductCard from '../components/ProductCard';
import { productsAPI, cartAPI } from '../api/axios';
import './Home.css';

const heroBanner = 'https://images.unsplash.com/photo-1583258292688-d0213dc5a3a8?auto=format&fit=crop&w=1600&q=80';

const heroBannerFallback = 'https://images.unsplash.com/photo-1542838132-92c53300491e?auto=format&fit=crop&w=1600&q=80';

const fallbackCategories = [
  'Fruits',
  'Vegetables',
  'Dairy',
  'Beverages',
  'Tea & Coffee',
  'Ice Creams',
  'Frozen Foods',
  'Snacks',
  'Biscuits & Cookies',
  'Cold Drinks',
  'Packaged Foods',
];

const heroHighlights = [
  { title: 'Fresh Products', text: 'Farm-fresh quality groceries', icon: '🌿' },
  { title: 'Fast Delivery', text: 'Delivered in minutes', icon: '🚚' },
  { title: 'Easy Returns', text: 'Hassle-free replacements', icon: '🔄' },
  { title: 'Secure Payments', text: 'Safe & smooth checkout', icon: '💳' },
];

const bottomFeatures = [
  { icon: '🔒', title: 'Secure Payments', text: 'Protected checkout for every order.' },
  { icon: '🥬', title: 'Quality Products', text: 'Fresh items sourced with care.' },
  { icon: '💬', title: '24/7 Support', text: 'Helpful guidance whenever you shop.' },
  { icon: '🚚', title: 'Fast & Reliable Delivery', text: 'Timely delivery to your doorstep.' },
];

const categoryIcons = {
  Fruits: '🍎',
  Vegetables: '🥬',
  Dairy: '🥛',
  Beverages: '🥤',
  'Tea & Coffee': '☕',
  'Ice Creams': '🍦',
  'Frozen Foods': '🧊',
  'Biscuits & Cookies': '🍪',
  'Cold Drinks': '🥤',
  'Packaged Foods': '📦',
};

export default function Home() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [searchInput, setSearchInput] = useState('');
  const [search, setSearch] = useState('');
  const [category, setCategory] = useState('');
  const [loading, setLoading] = useState(true);
  const [cartCount, setCartCount] = useState(0);

  const loadProducts = () => {
    setLoading(true);
    const params = {};
    if (search) params.search = search;
    if (category) params.category = category;
    productsAPI
      .getAll(params)
      .then((res) => {
        setProducts(res.data);
        setLoading(false);
      })
      .catch(() => setLoading(false));
  };

  const loadCategories = () => {
    productsAPI
      .getCategories()
      .then((res) => setCategories(res.data || []))
      .catch(() => setCategories([]));
  };

  const loadCartCount = () => {
    cartAPI
      .get()
      .then((res) => {
        const total = res.data?.items?.reduce((sum, item) => sum + (item.quantity || 0), 0) || 0;
        setCartCount(total);
      })
      .catch(() => {});
  };

  useEffect(() => {
    loadCategories();
  }, []);

  useEffect(() => {
    loadProducts();
  }, [search, category]);

  useEffect(() => {
    loadCartCount();
  }, []);

  const displayCategories = (categories.length ? categories : fallbackCategories).filter(
    (item, index, arr) => arr.indexOf(item) === index
  );

  return (
    <div className="home-page">
      <Navbar
        searchQuery={searchInput}
        onSearchChange={setSearchInput}
        onSearchSubmit={() => setSearch(searchInput.trim())}
        cartCount={cartCount}
      />
      <main className="home-main">
        <section className="hero-card">
          <div className="hero-grid">
            <article className="hero-copy">
              <p className="eyebrow">Fresh & Healthy</p>
              <h1>
                Fresh Groceries, <span>Delivered to You</span>
              </h1>
              <p className="hero-text">
                Shop fresh groceries, dairy, bakery items, snacks, beverages, and daily essentials — all
                in one place, delivered fresh and fast to your doorstep.
              </p>
              <div className="hero-actions">
                <button
                  type="button"
                  className="hero-cta"
                  onClick={() => document.getElementById('products-section')?.scrollIntoView({ behavior: 'smooth' })}
                >
                  Shop Now
                </button>
                <span className="hero-note">Fresh produce • dairy • pantry staples • easy checkout</span>
              </div>
              <div className="hero-badges" aria-label="hero highlights">
                <span>🌿 Freshly Picked</span>
                <span>🚚 Fast Delivery</span>
                <span>✅ Quality Assured</span>
              </div>
              <div className="feature-grid">
                {heroHighlights.map((item) => (
                  <article key={item.title} className="feature-card">
                    <span className="feature-icon">{item.icon}</span>
                    <div>
                      <strong>{item.title}</strong>
                      <p>{item.text}</p>
                    </div>
                  </article>
                ))}
              </div>
            </article>
            <aside className="hero-visual-card">
              <div className="hero-visual-topline">
                <span className="hero-visual-pill">Fresh picks for today</span>
                <span className="hero-visual-badge">New arrivals</span>
              </div>
              <div className="hero-photo-shell">
                <img
                  src={heroBanner}
                  onError={(e) => {
                    e.currentTarget.src = heroBannerFallback;
                  }}
                  alt="Fresh fruits, vegetables, dairy, bakery items and grocery essentials banner"
                  className="hero-banner-image"
                  loading="eager"
                  decoding="async"
                />
                <div className="hero-photo-overlay">
                  <strong>Seasonal savings</strong>
                  <span>Fresh produce, dairy & snacks in one place.</span>
                </div>
              </div>
            </aside>
          </div>
        </section>

        <section className="categories-section">
          <div className="section-heading-row">
            <div>
              <p className="eyebrow">Browse by category</p>
              <h2>Shop by Category</h2>
            </div>
            <button type="button" className="view-all-btn" onClick={() => setCategory('')}>
              View All
            </button>
          </div>
          <div className="category-grid">
            <button
              type="button"
              className={`category-card ${!category ? 'active' : ''}`}
              onClick={() => setCategory('')}
            >
              <span className="category-icon">🛒</span>
              <span>All</span>
            </button>
            {displayCategories.map((cat) => (
              <button
                key={cat}
                type="button"
                className={`category-card ${category === cat ? 'active' : ''}`}
                onClick={() => setCategory(cat)}
              >
                <span className="category-icon">{categoryIcons[cat] || '🛍️'}</span>
                <span>{cat}</span>
              </button>
            ))}
          </div>
        </section>

        <section id="products-section" className="products-section">
          <div className="section-heading-row">
            <div>
              <p className="eyebrow">Fresh picks</p>
              <h2>Featured Products</h2>
            </div>
            <p className="section-caption">Handpicked items with premium grocery quality.</p>
          </div>
          {loading ? (
            <div className="loading-state">
              <div className="spinner" /> Loading products...
            </div>
          ) : products.length === 0 ? (
            <div className="empty-state">No products found for this selection.</div>
          ) : (
            <div className="products-grid">
              {products.map((product) => (
                <ProductCard
                  key={product.id}
                  product={product}
                  onCartUpdate={loadCartCount}
                />
              ))}
            </div>
          )}
        </section>

        <section className="feature-strip">
          {bottomFeatures.map((item) => (
            <article key={item.title} className="feature-strip-card">
              <span className="feature-strip-icon">{item.icon}</span>
              <div>
                <h3>{item.title}</h3>
                <p>{item.text}</p>
              </div>
            </article>
          ))}
        </section>
      </main>
    </div>
  );
}

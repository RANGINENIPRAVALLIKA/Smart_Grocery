import { useEffect, useMemo, useState } from 'react';
import Navbar from '../components/Navbar';
import { adminAPI } from '../api/axios';
import { useAuth } from '../context/AuthContext';
import './Admin.css';

export default function Admin() {
  const { user } = useAuth();
  const isAdmin = !!user?.admin;

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState('');
  const [form, setForm] = useState({
    name: '',
    category: 'Fruits',
    price: '',
    stock: '',
    rating: '4.0',
    imageUrl: '',
    description: '',
  });

  const canSubmit = useMemo(() => {
    return form.name.trim() && form.category.trim() && Number(form.price) > 0;
  }, [form]);

  const load = () => {
    setLoading(true);
    adminAPI.getAllProducts()
      .then((res) => setProducts(res.data || []))
      .catch(() => setProducts([]))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    if (isAdmin) load();
  }, [isAdmin]);

  const addProduct = async (e) => {
    e.preventDefault();
    setMessage('');
    try {
      await adminAPI.addProduct({
        name: form.name.trim(),
        category: form.category.trim(),
        price: Number(form.price),
        stock: form.stock ? Number(form.stock) : 0,
        rating: form.rating ? Number(form.rating) : 4.0,
        imageUrl: form.imageUrl.trim() || null,
        description: form.description.trim() || null,
      });
      setMessage('Product added.');
      setForm((f) => ({ ...f, name: '', price: '', stock: '', imageUrl: '', description: '' }));
      load();
    } catch (err) {
      setMessage(err.response?.data?.message || 'Failed to add product');
    }
  };

  const deleteProduct = async (id) => {
    setMessage('');
    try {
      await adminAPI.deleteProduct(id);
      setProducts((prev) => prev.filter((p) => p.id !== id));
      setMessage('Product deleted.');
    } catch (err) {
      setMessage(err.response?.data?.message || 'Failed to delete product');
    }
  };

  if (!isAdmin) {
    return (
      <div className="home-page">
        <Navbar />
        <main className="admin-main">
          <h1>Admin</h1>
          <p className="admin-note">Access denied. This page is only for admin users.</p>
        </main>
      </div>
    );
  }

  return (
    <div className="home-page">
      <Navbar />
      <main className="admin-main">
        <h1>Admin</h1>
        <p className="admin-note">Add or delete products (simple).</p>

        {message && <div className="admin-message">{message}</div>}

        <section className="admin-card">
          <h2>Add Product</h2>
          <form className="admin-form" onSubmit={addProduct}>
            <div className="row">
              <label>Name</label>
              <input value={form.name} onChange={(e) => setForm((f) => ({ ...f, name: e.target.value }))} required />
            </div>
            <div className="grid">
              <div className="row">
                <label>Category</label>
                <select value={form.category} onChange={(e) => setForm((f) => ({ ...f, category: e.target.value }))}>
                  <option>Fruits</option>
                  <option>Vegetables</option>
                  <option>Dairy</option>
                  <option>Snacks</option>
                  <option>Beverages</option>
                  <option>Staples</option>
                </select>
              </div>
              <div className="row">
                <label>Price</label>
                <input type="number" step="0.01" value={form.price} onChange={(e) => setForm((f) => ({ ...f, price: e.target.value }))} required />
              </div>
              <div className="row">
                <label>Stock</label>
                <input type="number" value={form.stock} onChange={(e) => setForm((f) => ({ ...f, stock: e.target.value }))} />
              </div>
              <div className="row">
                <label>Rating (1–5)</label>
                <input type="number" min="1" max="5" step="0.1" value={form.rating} onChange={(e) => setForm((f) => ({ ...f, rating: e.target.value }))} />
              </div>
            </div>
            <div className="row">
              <label>Image URL (optional)</label>
              <input value={form.imageUrl} onChange={(e) => setForm((f) => ({ ...f, imageUrl: e.target.value }))} />
            </div>
            <div className="row">
              <label>Description (optional)</label>
              <textarea rows={3} value={form.description} onChange={(e) => setForm((f) => ({ ...f, description: e.target.value }))} />
            </div>
            <button className="admin-btn" type="submit" disabled={!canSubmit}>Add</button>
          </form>
        </section>

        <section className="admin-card">
          <h2>Delete Product</h2>
          {loading ? (
            <div className="loading-state"><div className="spinner" /> Loading...</div>
          ) : (
            <div className="admin-products">
              {products.slice(0, 30).map((p) => (
                <div key={p.id} className="admin-product">
                  <div className="info">
                    <strong>{p.name}</strong>
                    <span className="meta">{p.category} • ₹{p.price?.toFixed?.(2)}</span>
                  </div>
                  <button className="delete-btn" type="button" onClick={() => deleteProduct(p.id)}>Delete</button>
                </div>
              ))}
              {products.length > 30 && (
                <p className="admin-note">Showing first 30 products to keep it simple.</p>
              )}
            </div>
          )}
        </section>
      </main>
    </div>
  );
}


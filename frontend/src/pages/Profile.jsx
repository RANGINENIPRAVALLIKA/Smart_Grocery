import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Navbar from '../components/Navbar';
import { usersAPI, ordersAPI } from '../api/axios';
import './Profile.css';

export default function Profile() {
  const [profile, setProfile] = useState(null);
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [editing, setEditing] = useState(false);
  const [form, setForm] = useState({ name: '', email: '', phone: '', password: '' });
  const [saving, setSaving] = useState(false);
  const [message, setMessage] = useState('');

  useEffect(() => {
    usersAPI.getProfile().then((res) => {
      setProfile(res.data);
      setForm({
        name: res.data.name || '',
        email: res.data.email || '',
        phone: res.data.phone || '',
        password: '',
      });
    }).catch(() => setProfile(null)).finally(() => setLoading(false));

    ordersAPI.getMyOrders().then((res) => setOrders(res.data || [])).catch(() => setOrders([]));
  }, []);

  const handleSave = async (e) => {
    e.preventDefault();
    setSaving(true);
    setMessage('');
    try {
      const { data } = await usersAPI.updateProfile({
        name: form.name,
        email: form.email,
        phone: form.phone,
        ...(form.password ? { password: form.password } : {}),
      });
      setProfile(data);
      setEditing(false);
      setForm((f) => ({ ...f, password: '' }));
      setMessage('Profile updated.');
    } catch (err) {
      setMessage(err.response?.data?.message || 'Update failed');
    } finally {
      setSaving(false);
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

  return (
    <div className="home-page">
      <Navbar />
      <main className="profile-main">
        <h1>Profile</h1>
        <div className="profile-card">
          {message && <div className="profile-message">{message}</div>}
          {editing ? (
            <form onSubmit={handleSave}>
              <label>Name</label>
              <input value={form.name} onChange={(e) => setForm((f) => ({ ...f, name: e.target.value }))} required />
              <label>Email</label>
              <input type="email" value={form.email} onChange={(e) => setForm((f) => ({ ...f, email: e.target.value }))} required />
              <label>Phone</label>
              <input type="tel" value={form.phone} onChange={(e) => setForm((f) => ({ ...f, phone: e.target.value }))} />
              <label>New password (leave blank to keep)</label>
              <input type="password" value={form.password} onChange={(e) => setForm((f) => ({ ...f, password: e.target.value }))} placeholder="Optional" />
              <div className="profile-form-actions">
                <button type="submit" disabled={saving}>{saving ? 'Saving...' : 'Save'}</button>
                <button type="button" onClick={() => setEditing(false)}>Cancel</button>
              </div>
            </form>
          ) : (
            <>
              <p><strong>Name:</strong> {profile?.name}</p>
              <p><strong>Email:</strong> {profile?.email}</p>
              <p><strong>Phone:</strong> {profile?.phone || '—'}</p>
              <button type="button" className="edit-btn" onClick={() => setEditing(true)}>Edit profile</button>
            </>
          )}
        </div>
        <section className="profile-orders">
          <h2>Previous Orders</h2>
          {orders.length === 0 ? (
            <p className="empty-orders">No orders yet.</p>
          ) : (
            <ul className="orders-list">
              {orders.slice(0, 10).map((o) => (
                <li key={o.id} className="order-item">
                  <Link to={`/order/${o.id}`} className="order-link">
                    <span>Order #{o.id}</span>
                    <span>₹{o.totalAmount?.toFixed(2)}</span>
                    <span className="order-date">{o.createdAt ? new Date(o.createdAt).toLocaleDateString() : ''}</span>
                  </Link>
                </li>
              ))}
            </ul>
          )}
          {orders.length > 0 && <Link to="/orders" className="view-all-orders">View all orders</Link>}
        </section>
      </main>
    </div>
  );
}

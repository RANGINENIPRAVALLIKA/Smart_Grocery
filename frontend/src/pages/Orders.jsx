import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Navbar from '../components/Navbar';
import { ordersAPI } from '../api/axios';
import './Orders.css';

export default function Orders() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    ordersAPI.getMyOrders().then((res) => setOrders(res.data || [])).catch(() => setOrders([])).finally(() => setLoading(false));
  }, []);

  return (
    <div className="home-page">
      <Navbar />
      <main className="orders-main">
        <h1>My Orders</h1>
        {loading ? (
          <div className="loading-state"><div className="spinner" /> Loading...</div>
        ) : orders.length === 0 ? (
          <p className="empty-state">No orders yet.</p>
        ) : (
          <div className="orders-list">
            {orders.map((o) => (
              <Link key={o.id} to={`/order/${o.id}`} className="order-card">
                <div className="order-card-header">
                  <span>Order #{o.id}</span>
                  <span className="order-status">{o.status}</span>
                </div>
                <p className="order-date">Placed on {o.createdAt ? new Date(o.createdAt).toLocaleString() : ''}</p>
                <p className="order-total">Total: ₹{o.totalAmount?.toFixed(2)}</p>
              </Link>
            ))}
          </div>
        )}
      </main>
    </div>
  );
}

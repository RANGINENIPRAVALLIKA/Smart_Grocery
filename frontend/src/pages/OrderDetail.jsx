import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import Navbar from '../components/Navbar';
import { ordersAPI } from '../api/axios';
import './OrderDetail.css';

export default function OrderDetail() {
  const { id } = useParams();
  const [order, setOrder] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    ordersAPI.getById(id).then((res) => setOrder(res.data)).catch(() => setOrder(null)).finally(() => setLoading(false));
  }, [id]);

  if (loading) {
    return (
      <div className="home-page">
        <Navbar />
        <div className="page-loading"><div className="spinner" /> Loading...</div>
      </div>
    );
  }
  if (!order) {
    return (
      <div className="home-page">
        <Navbar />
        <main className="order-detail-main"><p>Order not found.</p></main>
      </div>
    );
  }

  return (
    <div className="home-page">
      <Navbar />
      <main className="order-detail-main">
        <Link to="/orders" className="back-link">← Back to orders</Link>
        <h1>Order #{order.id}</h1>
        <div className="order-detail-card">
          <p><strong>Status:</strong> <span className="status-badge">{order.status}</span></p>
          <p><strong>Date:</strong> {order.createdAt ? new Date(order.createdAt).toLocaleString() : ''}</p>
          <p><strong>Delivery address:</strong> {order.deliveryAddress}</p>
          <p><strong>Payment:</strong> {order.paymentMethod}</p>
          <h3>Items</h3>
          <ul className="order-detail-items">
            {order.items?.map((item, idx) => (
              <li key={idx} className="order-detail-item">
                <img src={item.imageUrl || 'https://placehold.co/60x60/e2e8f0/64748b'} alt="" />
                <div>
                  <span>{item.productName}</span>
                  <span>× {item.quantity} — ₹{(item.priceAtOrder * item.quantity).toFixed(2)}</span>
                </div>
              </li>
            ))}
          </ul>
          <p className="order-detail-total">Total: ₹{order.totalAmount?.toFixed(2)}</p>
        </div>
      </main>
    </div>
  );
}

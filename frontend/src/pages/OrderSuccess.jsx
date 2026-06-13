import { Link, useLocation } from 'react-router-dom';
import Navbar from '../components/Navbar';
import './OrderSuccess.css';

export default function OrderSuccess() {
  const location = useLocation();
  const order = location.state?.order;

  return (
    <div className="home-page">
      <Navbar />
      <main className="success-main">
        <div className="success-card">
          <div className="success-icon">✓</div>
          <h1>Order placed successfully</h1>
          <div className="success-actions">
            <Link to="/orders" className="success-btn primary">View Orders</Link>
            <Link to="/" className="success-btn">Continue Shopping</Link>
          </div>
        </div>
      </main>
    </div>
  );
}

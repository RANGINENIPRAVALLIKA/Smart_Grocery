import axios from 'axios';

const API_BASE = 'https://smart-grocery-backend-lp6q.onrender.com/api';

const api = axios.create({
  baseURL: API_BASE,
  headers: { 'Content-Type': 'application/json' },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

api.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(err);
  }
);

export const authAPI = {
  login: (data) => api.post('/auth/login', data),
  register: (data) => api.post('/auth/register', data),
};

export const productsAPI = {
  getAll: (params) => api.get('/products', { params }),
  getById: (id) => api.get(`/products/${id}`),
  getCategories: () => api.get('/products/categories'),
};

export const cartAPI = {
  get: () => api.get('/cart'),
  addItem: (productId, quantity = 1) =>
    api.post('/cart/items', null, {
      params: { productId: Number(productId), quantity: Number(quantity) || 1 },
    }),
  updateQuantity: (productId, quantity) =>
    api.put(`/cart/items/${productId}`, null, { params: { quantity: Number(quantity) } }),
  removeItem: (productId) => api.delete(`/cart/items/${productId}`),
};

export const wishlistAPI = {
  get: () => api.get('/wishlist'),
  add: (productId) => api.post('/wishlist/items', null, { params: { productId } }),
  remove: (productId) => api.delete(`/wishlist/items/${productId}`),
  check: (productId) => api.get(`/wishlist/check/${productId}`),
};

export const ordersAPI = {
  checkout: (data) => api.post('/orders/checkout', data),
  getMyOrders: () => api.get('/orders'),
  getById: (id) => api.get(`/orders/${id}`),
};

export const usersAPI = {
  getProfile: () => api.get('/users/profile'),
  updateProfile: (data) => api.put('/users/profile', data),
};

export const adminAPI = {
  addProduct: (data) => api.post('/admin/products', data),
  deleteProduct: (id) => api.delete(`/admin/products/${id}`),
  getAllProducts: () => api.get('/admin/products'),
};

export default api;

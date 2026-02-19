import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Dashboard.css';

function Dashboard() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserData = async () => {
      const token = localStorage.getItem('token');
      if (!token) {
        navigate('/login');
        return;
      }

      try {
        const response = await fetch('http://localhost:8083/api/user/me', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          credentials: 'include',
        });

        if (response.ok) {
          const userData = await response.json();
          setUser(userData);
        } else {
          // If token is invalid, redirect to login
          localStorage.removeItem('user');
          localStorage.removeItem('token');
          localStorage.removeItem('isLoggedIn');
          navigate('/login');
        }
      } catch (err) {
        console.error('Error fetching user data:', err);
        navigate('/login');
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, [navigate]);

  const handleLogout = async () => {
    try {
<<<<<<< Updated upstream
      await fetch('http://localhost:8080/api/auth/logout', {
=======
      const token = localStorage.getItem('token');
      await fetch('http://localhost:8083/api/auth/logout', {
>>>>>>> Stashed changes
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        credentials: 'include',
      });

      // Clear user data
      localStorage.removeItem('user');
      localStorage.removeItem('token');
      localStorage.removeItem('isLoggedIn');
      navigate('/login');
    } catch (err) {
      console.error('Logout error:', err);
    }
  };

  if (loading || !user) {
    return <div>Loading...</div>;
  }

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <div className="header-content">
          <h1>Dashboard</h1>
          <button className="btn-logout" onClick={handleLogout}>Logout</button>
        </div>
      </header>

      <main className="dashboard-main">
        <div className="welcome-section">
          <h2>Welcome, {user.name}!</h2>
          <p>You are now logged in to the application.</p>
        </div>

        <div className="user-info-preview">
          <h3>Your Profile Summary</h3>
          <div className="info-grid">
            <div className="info-item">
              <label>Name:</label>
              <p>{user.name}</p>
            </div>
            <div className="info-item">
              <label>Email:</label>
              <p>{user.email}</p>
            </div>
            <div className="info-item">
              <label>Member Since:</label>
              <p>{new Date(user.createdAt).toLocaleDateString()} at {new Date(user.createdAt).toLocaleTimeString()}</p>
            </div>
          </div>
        </div>

        <div className="dashboard-actions">
          <button className="btn-profile" onClick={() => navigate('/profile')}>
            View Full Profile
          </button>
        </div>
      </main>
    </div>
  );
}

export default Dashboard;

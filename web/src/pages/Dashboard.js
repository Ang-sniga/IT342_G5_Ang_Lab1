import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Dashboard.css';

function Dashboard() {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // Check if user is logged in
    const userString = localStorage.getItem('user');
    if (!userString) {
      navigate('/login');
      return;
    }
    
    setUser(JSON.parse(userString));
  }, [navigate]);

  const handleLogout = async () => {
    try {
      await fetch('http://localhost:8080/api/auth/logout', {
        method: 'POST',
        credentials: 'include',
      });

      // Clear user data
      localStorage.removeItem('user');
      localStorage.removeItem('isLoggedIn');
      navigate('/login');
    } catch (err) {
      console.error('Logout error:', err);
    }
  };

  if (!user) {
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
              <p>{new Date(user.createdAt).toLocaleDateString()}</p>
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

import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Home.css';

function Home() {
  const navigate = useNavigate();
  const isLoggedIn = localStorage.getItem('isLoggedIn');

  return (
    <div className="home-container">
      <header className="home-header">
        <div className="nav-bar">
          <h1 className="logo">Home Page</h1>
          <div className="nav-links">
            {isLoggedIn ? (
              <>
                <button className="nav-btn" onClick={() => navigate('/dashboard')}>Dashboard</button>
                <button className="nav-btn" onClick={() => navigate('/profile')}>Profile</button>
              </>
            ) : (
              <>
                <button className="nav-btn" onClick={() => navigate('/login')}>Login</button>
                <button className="nav-btn" onClick={() => navigate('/register')}>Register</button>
              </>
            )}
          </div>
        </div>
      </header>

      <main className="home-main">
        <div className="hero-section">
          <h2>Welcome to Home Page</h2>
          
          {!isLoggedIn ? (
            <div className="hero-buttons">
              <button 
                className="btn-primary" 
                onClick={() => navigate('/register')}
              >
                Get Started - Register
              </button>
              <button 
                className="btn-secondary" 
                onClick={() => navigate('/login')}
              >
                Already Have Account? Login
              </button>
            </div>
          ) : (
            <div className="hero-buttons">
              <button 
                className="btn-primary" 
                onClick={() => navigate('/dashboard')}
              >
                Go to Dashboard
              </button>
            </div>
          )}
        </div>

        <section className="features-section">
          <h3>Features</h3>
          <div className="features-grid">
            <div className="feature-card">
              <h4>User Registration</h4>
            </div>
            <div className="feature-card">
              <h4>Secure Login</h4>
            </div>
            <div className="feature-card">
              <h4>User Profile</h4>
            </div>
            <div className="feature-card">
              <h4>Session Management</h4>
            </div>
          </div>
        </section>
      </main>

    </div>
  );
}

export default Home;

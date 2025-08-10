import React, { useState } from 'react';
import axios from 'axios';

function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    try {
      const response = await axios.post('http://localhost:8080/auth/login', {
        email,
        password,
      });
      if (response.status === 200) {
        console.log(response.data);
        localStorage.setItem('token', response.data.token);
        window.location.href = '/dashboard';
      } else {
        setError('Erreur lors de la connexion');
      }
    } catch (error) {
      setError('Erreur lors de la connexion');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container d-flex align-items-center justify-content-center min-vh-100 bg-light">
      <div className="card shadow-lg border-0" style={{maxWidth: 400, width: '100%'}}>
        <div className="card-body p-5">
          <div className="d-flex flex-column align-items-center mb-4">
            <div className="rounded-circle bg-primary bg-opacity-10 d-flex align-items-center justify-content-center mb-3" style={{width: 56, height: 56}}>
              <svg width="32" height="32" fill="currentColor" className="text-primary" viewBox="0 0 16 16"><path d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0zm0 14.5A6.5 6.5 0 1 1 8 1.5a6.5 6.5 0 0 1 0 13z"/></svg>
            </div>
            <h2 className="fw-bold mb-1 text-primary">Connexion</h2>
            <p className="text-muted mb-0">Connectez-vous pour continuer</p>
          </div>
          {error && <div className="alert alert-danger text-center">{error}</div>}
          <form onSubmit={handleLogin}>
            <div className="mb-3">
              <label className="form-label">Email</label>
              <input
                type="email"
                className="form-control"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            <div className="mb-4">
              <label className="form-label">Mot de passe</label>
              <input
                type="password"
                className="form-control"
                placeholder="Mot de passe"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <button
              type="submit"
              className="btn btn-primary w-100 mb-3 fw-semibold"
              disabled={loading}
            >
              {loading ? 'Connexion...' : 'Se connecter'}
            </button>
          </form>
          <div className="text-center mb-2">
            <a href="/register" className="text-primary">Créer un compte</a>
          </div>
          <div className="text-center mt-3">
            <a href="http://localhost:8080/oauth2/authorization/google" className="btn btn-outline-secondary w-100 d-flex align-items-center justify-content-center gap-2" style={{fontWeight: 500}}>
              <svg width="20" height="20" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg"><g clipPath="url(#clip0_17_40)"><path d="M47.532 24.552c0-1.636-.146-3.2-.418-4.704H24.48v9.02h13.02c-.528 2.84-2.12 5.24-4.52 6.86v5.68h7.32c4.28-3.94 6.73-9.74 6.73-16.856z" fill="#4285F4"/><path d="M24.48 48c6.12 0 11.26-2.04 15.01-5.54l-7.32-5.68c-2.04 1.36-4.66 2.18-7.69 2.18-5.91 0-10.92-3.99-12.72-9.34H4.23v5.86C7.97 43.98 15.62 48 24.48 48z" fill="#34A853"/><path d="M11.76 29.62c-.48-1.36-.76-2.81-.76-4.32s.28-2.96.76-4.32v-5.86H4.23A23.97 23.97 0 0 0 0 24.48c0 3.98.96 7.75 2.66 11.06l7.1-5.92z" fill="#FBBC05"/><path d="M24.48 9.54c3.34 0 6.32 1.15 8.68 3.41l6.48-6.48C35.74 2.36 30.6 0 24.48 0 15.62 0 7.97 4.02 4.23 10.14l7.53 5.86c1.8-5.35 6.81-9.34 12.72-9.34z" fill="#EA4335"/></g><defs><clipPath id="clip0_17_40"><rect width="48" height="48" fill="white"/></clipPath></defs></svg>
              Se connecter avec Google
            </a>
          </div>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;

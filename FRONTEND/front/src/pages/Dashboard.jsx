import React, { useEffect } from 'react';
import { useLocation } from 'react-router-dom';

function Dashboard() {
  const location = useLocation();

  useEffect(() => {
    // Parse token from URL query params
    const params = new URLSearchParams(location.search);
    const token = params.get('token');
    if (token) {
      localStorage.setItem('token', token);
    }
  }, [location.search]);

  return (
    <div className="container py-5">
      <div className="card shadow-lg border-0 mx-auto" style={{maxWidth: 600}}>
        <div className="card-body p-5 text-center">
          <h1 className="mb-4 text-primary">Bienvenue sur le Dashboard</h1>
          <p className="lead">Vous êtes connecté !</p>
          <p className="text-muted">Votre token est stocké dans le localStorage.</p>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
